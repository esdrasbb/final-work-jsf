package pos.fa7.cursoweb.managedbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pos.fa7.cursoweb.business.UsuarioBusiness;
import pos.fa7.cursoweb.business.exception.UsuarioInvalidoException;
import pos.fa7.cursoweb.model.Usuario;
import pos.fa7.cursoweb.util.MessageHelper;

@ManagedBean
@SessionScoped
public class LoginBean {

	/** Referencia para a camada de regras de negocio */
	@ManagedProperty("#{usuarioBusiness}")
	private UsuarioBusiness usuarioBusiness;

	/** Usuario autenticado na aplicacao */
	private Usuario usuarioAutenticado;
	private String cpf;
	private String senha;

	/** Gerar get's e set's */
	public String autenticar() {
		try {
			usuarioAutenticado = usuarioBusiness.autenticarUsuario(cpf, senha);
			return "index?faces-redirect=true";
		} catch (UsuarioInvalidoException e) {
			FacesContext.getCurrentInstance().addMessage("loginForm", MessageHelper
					.createMessage(FacesMessage.SEVERITY_ERROR, "bean.loginBean.usuarioInvalidoException"));
			return null;
		} finally {
			cpf = null;
			senha = null;
		}
	}

	public Usuario getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setUsuarioBusiness(UsuarioBusiness usuarioBusiness) {
		this.usuarioBusiness = usuarioBusiness;
	}

}