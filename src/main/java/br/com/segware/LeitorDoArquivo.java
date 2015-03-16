package br.com.segware;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class LeitorDoArquivo {
	private static final String [] mapeamentoDoArquivo = {"codigoSequencial","codigoCliente","codigoEvento","tipoDoEvento","dataInicio","dataFim","codigoDoAtendente"};
	
	public Relatorio obterRelatorio(String caminho) throws ExcessaoPadrao{
		FileReader leitorDeArquivo = null;
		CSVParser interpretadorDeCsv = null;
		
		CSVFormat formatoDoCSV = CSVFormat.DEFAULT.withHeader(mapeamentoDoArquivo);
		ArrayList<RegistroRelatório> relatorio = new ArrayList<RegistroRelatório>();
		try {
			leitorDeArquivo = new FileReader(caminho);
			interpretadorDeCsv = new CSVParser(leitorDeArquivo, formatoDoCSV);
            List<CSVRecord> relatorioCSV = interpretadorDeCsv.getRecords(); 
            for (int i = 0; i < relatorioCSV.size(); i++) {
            	CSVRecord registroCSV = relatorioCSV.get(i);
            	Tipo tipoDoEvento = obterTipoAPartirDeString(registroCSV.get("tipoDoEvento"));
            	RegistroRelatório registro = new RegistroRelatório(Integer.parseInt(registroCSV.get("codigoSequencial")), registroCSV.get("codigoCliente"), 
            			registroCSV.get("codigoEvento"), tipoDoEvento, registroCSV.get("dataInicio"), registroCSV.get("dataFim"), registroCSV.get("codigoDoAtendente"));
            	relatorio.add(registro);	
			}
        } 
        catch (Exception e) {
        	throw new ExcessaoPadrao(e, "Erro ao ler CSV.");
        } finally {
            try {
            	leitorDeArquivo.close();
            	interpretadorDeCsv.close();
            } catch (IOException e) {
            	throw new ExcessaoPadrao(e, "Erro ao fechar CSV.");
            }
        }
		return new Relatorio(relatorio);
	}
	
	public Tipo obterTipoAPartirDeString(String stringTipo){
		switch (stringTipo){
			case "ALARME":
				return Tipo.ALARME;
			case "ARME":
				return Tipo.ARME;
			case "TESTE":
				return Tipo.TESTE;
			case "DESARME":
				return Tipo.DESARME;
			default:
				return null;
		}
	}

}
