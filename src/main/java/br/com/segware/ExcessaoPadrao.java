package br.com.segware;

@SuppressWarnings("serial")
public class ExcessaoPadrao extends Exception{
	private String mensagem;
	
	public ExcessaoPadrao(Exception e, String mensagem) {
		this.mensagem = mensagem + "\n\n"+ e.getMessage();
	}
	
	@Override
	public String getMessage() {
		return mensagem;
	}
	
}
