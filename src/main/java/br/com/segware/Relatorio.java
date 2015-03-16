package br.com.segware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class Relatorio {
	ArrayList<RegistroRelatório> relatorio;

	public Relatorio(ArrayList<RegistroRelatório> relatorio) {
		this.relatorio = relatorio;
	}
	
	/**
     * Total de eventos agrupados por cliente.<br/>
     * <ul>
     * <li>key: Codigo do cliente</li>
     * <li>value: Total de eventos</li>
     * </ul>
     *
     * @return Total de eventos agrupados por cliente.
     */
	public Map<String, Integer> getTotalEventosCliente() {
		Map<String, Integer> mapa = new HashMap<String, Integer>();
		for (RegistroRelatório registro: relatorio){
			Integer numeroDeRegistrosDoCliente = mapa.get(registro.obterCodigoCliente());
			if (numeroDeRegistrosDoCliente == null){
				mapa.put(registro.obterCodigoCliente(), 1);
			}else{
				numeroDeRegistrosDoCliente++;
				mapa.replace(registro.obterCodigoCliente(), numeroDeRegistrosDoCliente);
			}
		}
		return mapa;
	}
	
	/**
     * Tempo medio de atendimento por atendente.<br/>
     * O tempo de atendimento e definido por
     *
     * <pre>
     * Data Fim - Data Inicio
     * </pre>
     * <ul>
     * <li>key: Codigo do atendente</li>
     * <li>value: Tempo medio (em segundos)</li>
     * </ul>
     *
     * @return O tempo medio de atendimento por atendente, em segundos.
     */
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		Map<String, Double> mapaTempoMedio = new HashMap<String, Double>();
		Map<String, Double> mapaNumeroDeAtendimentos = new HashMap<String, Double>();
		for (RegistroRelatório registro: relatorio){
			if (mapaTempoMedio.get(registro.obterCodigoDoAtendente()) == null){
				mapaNumeroDeAtendimentos.put(registro.obterCodigoDoAtendente(), 1.0);
				Duration duracao = new Duration(registro.obterDataInicio(), registro.obterDataFim());
				mapaTempoMedio.put(registro.obterCodigoDoAtendente(), new Long(duracao.getStandardSeconds()).doubleValue());
			}else{
				Double tempoMedioAtendente = mapaTempoMedio.get(registro.obterCodigoDoAtendente()).doubleValue();
				Double numeroDeAtendimentosDoAtendente  = mapaNumeroDeAtendimentos.get(registro.obterCodigoDoAtendente());
				Double duracaoAntiga =tempoMedioAtendente*numeroDeAtendimentosDoAtendente;
				numeroDeAtendimentosDoAtendente++;
				mapaNumeroDeAtendimentos.replace(registro.obterCodigoDoAtendente(), numeroDeAtendimentosDoAtendente);
				Duration duracaoAtual = new Duration(registro.obterDataInicio(), registro.obterDataFim());
				Double novaDuracaoMedia = (duracaoAntiga + duracaoAtual.getStandardSeconds())/numeroDeAtendimentosDoAtendente;
				mapaTempoMedio.replace(registro.obterCodigoDoAtendente(), novaDuracaoMedia);
			}
		}
		Set<String> chaves = mapaTempoMedio.keySet();
		Map<String, Long> mapaTempoMedioRetorno = new HashMap<String, Long>();
		for (String chave: chaves){
			mapaTempoMedioRetorno.put(chave, mapaTempoMedio.get(chave).longValue());
		}
		return mapaTempoMedioRetorno;
	}
	
	/**
     * Retorna uma lista de tipos, ordenado de forma decrescente pela quantidade de eventos.
     *
     * @return Lista de tipos.
     */
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		Map<Tipo, Integer> mapaDeTipos = new HashMap<Tipo, Integer>();
		List<Tipo> tiposMaisOcorrentes = new ArrayList<Tipo>();
		for (RegistroRelatório registro: relatorio){
			Integer numeroDeRegistrosDoCliente = mapaDeTipos.get(registro.obterTipoDoEvento());
			if (numeroDeRegistrosDoCliente == null){
				mapaDeTipos.put(registro.obterTipoDoEvento(), 1);
			}else{
				numeroDeRegistrosDoCliente++;
				mapaDeTipos.replace(registro.obterTipoDoEvento(), numeroDeRegistrosDoCliente);
			}
		}
		Set<Tipo> tipos = mapaDeTipos.keySet();
		int contador = tipos.size();
		
		while (contador != 0){
			Tipo maiorTipo = null;
			Integer maiorNumero = 0;
			for(Tipo tipo:tipos){
				if (mapaDeTipos.get(tipo) > maiorNumero && !verificaSeTipoEstaNaListaDeMaisOcorrentes(tipo, tiposMaisOcorrentes)){
					maiorNumero = mapaDeTipos.get(tipo);
					maiorTipo = tipo;
				}
			}
			tiposMaisOcorrentes.add(maiorTipo);
			contador--;
		}
		return tiposMaisOcorrentes;
	}
	
	private boolean verificaSeTipoEstaNaListaDeMaisOcorrentes(Tipo tipo, List<Tipo> lista){
		for (Tipo tipoDaLista:lista){
			if (tipoDaLista == tipo){
				return true;
			}
		}
		return false;
	}
	
	/**
     * Retorna o codigo sequencial de um evento de desarme que tenha ocorrido apos alarme.<br/>
     * Importante notar que este tipo de evento so pode ser considerado quando o desarme ocorrer em ate 5 minutos apos o alarme.<br/>
     * Caso tenha excedido este periodo, nao devera ser reportado.<br/>
     * O tempo a ser considerado e sempre com base na data/hora inicial dos eventos comparados.
     *
     * @return Lista de codigos sequenciais de eventos com desarme apos o alarme.
     */
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		DateTime horarioDoUltimoAlarmeDisparado = null;
		List<Integer> listaDeCodigos = new ArrayList<Integer>();
		for (RegistroRelatório registro: relatorio){
			if (registro.obterTipoDoEvento() == Tipo.ALARME){
				horarioDoUltimoAlarmeDisparado = registro.obterDataInicio();
			}
			if (registro.obterTipoDoEvento() == Tipo.DESARME && horarioDoUltimoAlarmeDisparado != null){
				Duration duracao = new Duration(horarioDoUltimoAlarmeDisparado, registro.obterDataInicio());
				horarioDoUltimoAlarmeDisparado = null;
				if (duracao.getStandardSeconds() <= 300){
					listaDeCodigos.add(registro.obterCodigoSequencial());
					break;
				}
			}
		}
		return listaDeCodigos;
	}

}
