package br.com.segware;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class RegistroRelatório {
	private Integer codigoSequencial;
	private String codigoCliente;
	private String codigoEvento;
	private Tipo tipoDoEvento;
	private DateTime dataInicio;
	private DateTime dataFim;
	private String codigoDoAtendente;
	
	public RegistroRelatório(Integer CodigoSequencial, String codigoCliente,String codigoEvento,Tipo tipoDoEvento,String stringDataInicio,String stringDataFim,String codigoDoAtendente) {
		this.codigoSequencial = CodigoSequencial;
		this.codigoCliente = codigoCliente;
		this.codigoEvento = codigoEvento;
		this.tipoDoEvento = tipoDoEvento;
		this.codigoDoAtendente = codigoDoAtendente;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		this.dataInicio = formatter.parseDateTime(stringDataInicio);
		this.dataFim = formatter.parseDateTime(stringDataFim);
	}

	public Integer obterCodigoSequencial() {
		return codigoSequencial;
	}

	public String obterCodigoCliente() {
		return codigoCliente;
	}

	public String obterCodigoEvento() {
		return codigoEvento;
	}

	public Tipo obterTipoDoEvento() {
		return tipoDoEvento;
	}

	public DateTime obterDataInicio() {
		return dataInicio;
	}

	public DateTime obterDataFim() {
		return dataFim;
	}

	public String obterCodigoDoAtendente() {
		return codigoDoAtendente;
	}
	
	@Override
	public String toString() {
		return codigoSequencial + " , "+codigoCliente+" , "+codigoEvento+" , "+codigoEvento+" , " +tipoDoEvento+" , "+" , "+codigoDoAtendente+" , "+dataInicio+" , "+dataFim;
	}
	
	
}
