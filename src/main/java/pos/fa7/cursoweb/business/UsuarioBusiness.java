package pos.fa7.cursoweb.business;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pos.fa7.cursoweb.business.exception.UsuarioInvalidoException;
import pos.fa7.cursoweb.dao.UsuarioDao;
import pos.fa7.cursoweb.model.Usuario;

@ApplicationScoped
@ManagedBean(name = "usuarioBusiness")
public class UsuarioBusiness {

	@ManagedProperty("#{usuarioDao}")
	private UsuarioDao usuarioDao;

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	/**
	 * Efetua autenticacao do usuario cujos dados foram passados como parametro.
	 * 
	 * @param cpf
	 *            CPF do usuario
	 * @param senha
	 *            Senha do usuario
	 * @return {@link Usuario} caso a autenticacao seja bem sucedida
	 * @throws UsuarioInvalidoException
	 *             caso a autenticacao seja mal sucedida
	 */
	public Usuario autenticarUsuario(String cpf, String senha) throws UsuarioInvalidoException {
		Usuario usuario = usuarioDao.selecionar(cpf);
		if (usuario == null || !usuario.getSenha().equals(senha)) {
			throw new UsuarioInvalidoException();
		}
		return usuario;
	}

	/**
	 * Insere o estado na base de dados.
	 * 
	 * @param usuario
	 *            Usuario a ser persistido.
	 */
	public void excluirUsuario(Usuario usuario) {
		usuarioDao.excluirUsuario(usuario);
	}

	/**
	 * Exclui o estado na base de dados.
	 * 
	 * @param estado
	 *            estado a ser excluido
	 */
	public void salvarUsuario(Usuario usuario) {
		usuarioDao.salvarUsuario(usuario);
	}

	/**
	 * Seleciona todos os registros aramzenados de um usuario {@link Usuario}.
	 * 
	 * @return lista de {@link Usuario}
	 */
	public Usuario selecionar(Usuario usuario) {
		return usuarioDao.selecionar(usuario);
	}

	/**
	 * Seleciona os usuarios que possuem atributos que correspondam aos
	 * atributos setados no usuario exemplo.
	 * 
	 * @param usuario
	 *            Usuario contendo os parametros da consulta.
	 * @return a usuario que corresponde ao parametro da consulta.
	 */
	public List<Usuario> selecionarTodos() {
		return usuarioDao.selecionarTodos();
	}

}
