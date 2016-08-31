package pos.fa7.cursoweb.model;

public class Cep {

	private String regiao;
	private String sufixo;

	public Cep(String regiao, String sufixo) {
		super();
		this.regiao = regiao;
		this.sufixo = sufixo;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getSufixo() {
		return sufixo;
	}

	public void setSufixo(String sufixo) {
		this.sufixo = sufixo;
	}

	public String show() {
		return regiao + sufixo;
	}

}
