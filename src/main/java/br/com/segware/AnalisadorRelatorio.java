package br.com.segware;

import java.util.List;
import java.util.Map;

public class AnalisadorRelatorio implements IAnalisadorRelatorio{
	private Relatorio relatorio;
	
	public AnalisadorRelatorio(String caminho) throws ExcessaoPadrao {
		LeitorDoArquivo leitorDoRelatorio = new LeitorDoArquivo();
		relatorio = leitorDoRelatorio.obterRelatorio(caminho);
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		return relatorio.getTotalEventosCliente();
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		return relatorio.getTempoMedioAtendimentoAtendente();
	}

	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		return relatorio.getTiposOrdenadosNumerosEventosDecrescente();
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		return relatorio.getCodigoSequencialEventosDesarmeAposAlarme();
	}

}
