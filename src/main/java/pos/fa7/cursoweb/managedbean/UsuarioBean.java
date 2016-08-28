package pos.fa7.cursoweb.managedbean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pos.fa7.cursoweb.business.UsuarioBusiness;
import pos.fa7.cursoweb.model.Usuario;
import pos.fa7.cursoweb.util.MessageHelper;

@ManagedBean
@RequestScoped
public class UsuarioBean {

	/** Referencia para a camada de regras de negocio */
	@ManagedProperty("#{usuarioBusiness}")
	private UsuarioBusiness usuarioBusiness;

	/** Usuario a serusado no form. */
	private Usuario usuario = new Usuario();

	public void setUsuarioBusiness(UsuarioBusiness usuarioBusiness) {
		this.usuarioBusiness = usuarioBusiness;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() {
		return usuarioBusiness.selecionarTodos();
	}

	public String novo() {
		usuario = new Usuario();
		return "usuariosEditar";
	}

	public String salvar() {
		usuarioBusiness.salvarUsuario(usuario);
		FacesContext.getCurrentInstance().addMessage("usuariosForm", MessageHelper
				.createMessage(FacesMessage.SEVERITY_INFO, "bean.usuarioBean.success.save"));

		return "usuarios";
	}

	public String excluir() {
		usuarioBusiness.excluirUsuario(usuario);
		FacesContext.getCurrentInstance().addMessage("usuariosForm", MessageHelper
				.createMessage(FacesMessage.SEVERITY_INFO, "bean.usuarioBean.success.delete"));
		return "usuarios";
	}

	public void exibir() {
		usuario = usuarioBusiness.selecionar(usuario);
	}
}
