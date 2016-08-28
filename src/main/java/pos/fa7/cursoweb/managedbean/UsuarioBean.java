package pos.fa7.cursoweb.managedbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pos.fa7.cursoweb.business.UsuarioBusiness;
import pos.fa7.cursoweb.model.Usuario;

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
		return "usuarios";
	}

	public String excluir() {
		usuarioBusiness.excluirUsuario(usuario);
		return "usuarios";
	}

	public void exibir() {
		usuario = usuarioBusiness.selecionar(usuario);
	}
}
