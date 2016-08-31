package pos.fa7.cursoweb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pos.fa7.cursoweb.managedbean.LoginBean;
import pos.fa7.cursoweb.model.Cep;
import pos.fa7.cursoweb.model.Usuario;

/**
 * DAO da entidade Usuario.
 * 
 * @author Fabio Barros
 */
@ApplicationScoped
@ManagedBean(name = "usuarioDao")
public class UsuarioDao {

	private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@PostConstruct
	public void init() {
		DBConnection.initDB();
		Calendar dataNascimento = Calendar.getInstance();

		Usuario usuario = new Usuario();
		usuario.setNomeReduzido("Pedro de Alcantara");
		usuario.setNomeCompleto(
				"Pedro de Alcantara Francisco Antanio " + "Joao Carlos Xavier de Paula Miguel Rafael Joaquim Jose "
						+ "Gonzaga Pascoal Cipriano Serafim de Braganca e Bourbon");
		usuario.setEmail("pedro.alcantara@gmail.com");
		usuario.setSenha("teste");
		usuario.setCpf("11111111111");
		dataNascimento.set(1798, 9, 12);
		usuario.setDataNascimento(dataNascimento.getTime());
		usuario.setSenha("teste");
		usuario.setCep(new Cep("12345", "123"));
		this.salvarUsuario(usuario);

		usuario = new Usuario();
		usuario.setNomeReduzido("Santos Dumont");
		usuario.setNomeCompleto("Alberto Santos Dumont");
		usuario.setEmail("santos.dumont@gmail.com");
		usuario.setSenha("teste");
		usuario.setCpf("22222222222");
		dataNascimento.set(1873, 6, 20);
		usuario.setDataNascimento(dataNascimento.getTime());
		usuario.setCep(new Cep("12345", "456"));
		this.salvarUsuario(usuario);

		usuario = new Usuario();
		usuario.setNomeReduzido("Isabel de Braganca");
		usuario.setNomeCompleto(
				"Isabel Cristina Leopoldina Augusta " + "Micaela Gabriela Rafaela Gonzaga de Braganca e Bourbon");
		usuario.setEmail("maria@gmail.com");
		usuario.setSenha("teste");
		usuario.setCpf("33333333333");
		dataNascimento.set(1846, 6, 29);
		usuario.setDataNascimento(dataNascimento.getTime());
		usuario.setCep(new Cep("12345", "789"));
		this.salvarUsuario(usuario);

	}

	/**
	 * Insere o usuario na base de dados.
	 * 
	 * @param usuario
	 *            Usuario a ser persistida.
	 */
	public void salvarUsuario(Usuario usuario) {

		StringBuilder sql = new StringBuilder();

		if (usuario.getId() != null && usuario.getId() > 0L) {
			sql.append(" UPDATE user SET ").append("cep = '").append(usuario.getCep().show()).append("', ")
					.append("cpf = '").append(usuario.getCpf()).append("', ").append("email = '")
					.append(usuario.getEmail()).append("', ").append("nome_completo = '")
					.append(usuario.getNomeCompleto()).append("', ").append("nome_reduzido = '")
					.append(usuario.getNomeReduzido()).append("', ").append("senha = '").append(usuario.getSenha())
					.append("', ").append("data_nascimento = ")
					.append(" to_date('" + getTimeStamp(usuario.getDataNascimento()) + "', 'yyyy/mm/dd hh24:mi:ss')")
					.append(" WHERE id = ").append(usuario.getId());
		} else {
			sql.append(
					"INSERT INTO user (cep, cpf, email, nome_completo, nome_reduzido, senha, data_nascimento) VALUES ( ")
					.append("'").append(usuario.getCep().show()).append("', ").append("'").append(usuario.getCpf())
					.append("', ").append("'").append(usuario.getEmail()).append("', ").append("'")
					.append(usuario.getNomeCompleto()).append("', ").append("'").append(usuario.getNomeReduzido())
					.append("', ").append("'").append(usuario.getSenha()).append("', ")
					.append("to_date('" + getTimeStamp(usuario.getDataNascimento()) + "', 'yyyy/mm/dd hh24:mi:ss')")
					.append(") ");
		}

		try (Connection connection = DBConnection.getConnection(); Statement stmt = connection.createStatement();) {
			stmt.executeUpdate(sql.toString());
		} catch (SQLException e) {
			logger.error("Error save or update user", e);
		}
	}

	/**
	 * Exclui o usuario na base de dados.
	 * 
	 * @param usuario
	 *            usuario a ser excluido
	 */
	public void excluirUsuario(Usuario usuario) {
		try (Connection connection = DBConnection.getConnection(); Statement stmt = connection.createStatement();) {
			stmt.execute("DELETE FROM user WHERE id = " + usuario.getId().toString());
		} catch (SQLException e) {
			logger.error("Error delete user", e);
		}
	}

	/**
	 * Seleciona todos os registros aramzenados de um usuario {@link Usuario}.
	 * 
	 * @return lista de {@link Usuario}
	 */
	public Usuario selecionar(Usuario usuario) {

		Usuario newUsuario = null;
		try (Connection connection = DBConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE id = " + usuario.getId().toString())) {

			while (rs.next()) {
				newUsuario = new Usuario();
				String cep = rs.getString("cep");
				newUsuario.setCep(new Cep(cep.substring(0, 5), cep.substring(5, 8)));
				newUsuario.setCpf(rs.getString("cpf"));
				newUsuario.setDataNascimento(rs.getDate("data_nascimento"));
				newUsuario.setEmail(rs.getString("email"));
				newUsuario.setId(rs.getLong("id"));
				newUsuario.setNomeCompleto(rs.getString("nome_completo"));
				newUsuario.setNomeReduzido(rs.getString("nome_reduzido"));
				newUsuario.setSenha(rs.getString("senha"));
			}
		} catch (SQLException e) {
			logger.error("Error getting user", e);
		}

		return newUsuario;
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

		List<Usuario> usuarios = Collections.emptyList();
		try (Connection connection = DBConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM user")) {

			usuarios = new ArrayList<>();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				String cep = rs.getString("cep");
				usuario.setCep(new Cep(cep.substring(0, 5), cep.substring(5, 8)));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setDataNascimento(rs.getDate("data_nascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setId(rs.getLong("id"));
				usuario.setNomeCompleto(rs.getString("nome_completo"));
				usuario.setNomeReduzido(rs.getString("nome_reduzido"));
				usuario.setSenha(rs.getString("senha"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			logger.error("Error getting users", e);
		}

		return usuarios;
	}

	/**
	 * Seleciona o usuario que possue o CPF que corresponda ao parametro.
	 * 
	 * @param cpf
	 *            CPF do usuario desejado
	 * @return {@link Usuario} correspondente ou null caso nao seja encontrado.
	 */
	public Usuario selecionar(String cpf) {
		List<Usuario> usuarios = selecionarTodos();
		for (Usuario usuarioLista : usuarios) {
			if (usuarioLista.getCpf().equals(cpf)) {
				return usuarioLista;
			}
		}

		return null;
	}

	private String getTimeStamp(Date date) {
		return dateFormat.format(date.getTime());
	}
}
