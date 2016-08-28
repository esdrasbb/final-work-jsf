package pos.fa7.cursoweb.dao;

import java.util.ArrayList;
import java.util.List;

import pos.fa7.cursoweb.model.BaseModel;

/**
 * Implementa metodos genericos de persistencia.
 * 
 * @author Fabio Barros
 * @param <T>
 *            Tipo de entidade a qual a persistencia sera realizada.
 */
public abstract class SimpleDaoGenerico<T extends BaseModel> {

	private final List<T> dados = new ArrayList<T>();

	/**
	 * Insere a entidade na base de dados.
	 * 
	 * @param entidade
	 *            Entidade a ser persistida.
	 */
	protected void salvarEntidade(T entidade) {
		if (entidade.getId() == null || entidade.getId() <= 0) {
			entidade.setId(proximoId());
			dados.add(entidade);
		} else {
			int pos = dados.indexOf(entidade);
			dados.set(pos, entidade);
		}
	}

	/**
	 * Exclui a entidade na base de dados.
	 * 
	 * @param entidade
	 *            entidade a ser excluida
	 */
	protected void excluirEntidade(T entidade) {
		dados.remove(entidade);
	}

	/**
	 * Seleciona todos os registros aramzenados de um entidade {@link T}.
	 * 
	 * @return lista de {@link T}
	 */
	protected List<T> selecionarTodos() {
		return dados;
	}

	/**
	 * Seleciona as entidades que possuem atributos que correspondam aos
	 * atributos setados no entidade exemplo.
	 * 
	 * @param entidade
	 *            Entidade contendo os parametros da consulta.
	 * @return a entidade que corresponde ao parametro da consulta.
	 */
	protected T selecionar(T entidade) {
		if (dados.contains(entidade)) {
			return dados.get(dados.indexOf(entidade));
		}
		return null;
	}

	/**
	 * Recupera o proximo valor de ID possivel.
	 * 
	 * @param dados
	 *            Colecao de {@link BaseModel}.
	 * @return proximo valor de ID
	 */
	private Long proximoId() {
		Long proximo = 1L;
		for (BaseModel dado : dados) {
			if (dado.getId().compareTo(proximo) >= 0) {
				proximo = dado.getId() + 1;
			}
		}
		return proximo;
	}
}
