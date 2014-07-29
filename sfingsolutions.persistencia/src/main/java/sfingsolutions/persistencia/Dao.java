package sfingsolutions.persistencia;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import sfingsolutions.dominio.defaultobject.IDefaultObject;
import sfingsolutions.persistencia.exception.DaoException;

/**
 * Contrato DAO.
 * 
 * @author viniciusbrazpereira.
 * 
 */
public interface Dao {
	
	/**
     * Metodo de inclus„o da Entity no provider especifico.
     *
     * @param <T> Type da classe de entity.
     * @param entity a ser inserida
     * @return Entity inserida
     * @throws DaoException Erro de processamento.
     */
    public <T extends IDefaultObject<?>> T persist(T entity) throws DaoException;
    
    /**
     * Metodo de atualizaÁ„o da Entity no provider especifico.
     *
     * @param <T> Type da classe de entity.
     * @param entity Entity a ser atualizada
     * @return Entity atualizada
     * @throws DaoException Erro de processamento.
     */
    public <T extends IDefaultObject<?>> T update(T entity) throws DaoException;
    
    /**
    * MÈtodo de exclus„o da Entity no provider especifico.
    *
    * @param <T> Type da classe de entity.
    * @param entity Entitty a ser excluida
    * @throws DaoException Erro de processamento.
    */
   public <T extends IDefaultObject<?>> void delete(T entity) throws DaoException;

	/**
	 * Metodo de selecao generico do provider.
	 * 
	 * @param <T>
	 *            Type da classe de entity.
	 * @param clazz
	 *            Entity class para selecao
	 * @param namedQuery
	 *            NamedQuery usado na selecao
	 * @param map
	 *            Mapa com os parametros de consulta
	 * @return Lista de Entitys selecionadas
	 * @throws DaoException
	 *             Erro de processamento.
	 */
	public <T extends Serializable> List<T> select(Class<T> clazz,
			String namedQuery, Map<String, Object> map) throws DaoException;

	/**
	 * Metodo de selecao unica.
	 * 
	 * @param clazz
	 *            Entity class para selecao.
	 * @param namedQuery
	 *            NamedQuery usado na selecao.
	 * @param map
	 *            Mapa com os parametros de consulta.
	 * @return Entity selecionada.
	 * @throws DaoException
	 *             Erro de processamento.
	 */
	public <T extends Serializable> T find(Class<T> clazz, String namedQuery,
			Map<String, Object> map) throws DaoException;

	/**
	 * Metodo de selecao unica.
	 * 
	 * @param clazz
	 *            Entity class para selecao.
	 * @param pk
	 *            Long.
	 * @return Entity selecionada.
	 * @throws DaoException
	 *             Erro de processamento.
	 */
	public <T extends Serializable> T find(Class<T> clazz, Long pk)
			throws DaoException;
}
