package sfingsolutions.persistencia;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.defaultobject.IDefaultObject;
import sfingsolutions.persistencia.exception.DaoException;

/**
 * Classe Base de implementação para Dao.
 * 
 * @author Vinicius Braz.
 * @param <T>
 *            Entity.
 */
public class MasterDao implements Dao {

	@PersistenceContext(name = "sfingsolutionsDS")
	private EntityManager eManager;

	private static final Logger log = LoggerFactory.getLogger(MasterDao.class);

	private int count = 0;
	protected static final int FLUSH_COUNT = 5000;
	protected static final int CLEAN_COUNT = (FLUSH_COUNT * 10);

	/** {@inheritDoc} */
	@Override
	public <T extends IDefaultObject<?>> T persist(T entity)
			throws DaoException {
		try {
			if (++count % FLUSH_COUNT == 0) {
				entityManager().flush();
				if (count % CLEAN_COUNT == 0) {
					entityManager().clear();
				}
			}

			entityManager().persist(entity);
			return entity;
		} catch (Exception e) {
			log.error("Error: {}", entity, e);
			throw new DaoException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IDefaultObject<?>> T update(T entity) throws DaoException {
		try {
			return entityManager().merge(entity);
		} catch (Exception e) {
			log.error("Error: {}", entity, e);
			throw new DaoException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <T extends IDefaultObject<?>> void delete(T entity)
			throws DaoException {
		try {
			entityManager().remove(entity);
		} catch (Exception e) {
			log.error("Error: {}", entity, e);
			throw new DaoException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Serializable> T find(Class<T> clazz, String namedQuery,
			Map<String, Object> map) throws DaoException {
		return (T) buildTypedQuery(clazz, namedQuery, map).getResultList();
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Serializable> List<T> select(Class<T> clazz,
			String namedQuery, Map<String, Object> map) throws DaoException {
		return buildTypedQuery(clazz, namedQuery, map).getResultList();
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Serializable> T find(Class<T> clazz, Long pk)
			throws DaoException {
		try {
			return entityManager().find(clazz, pk);
		} catch (Exception e) {
			log.error("Error: {} : {} - {}", new Object[] { clazz.getName(),
					pk, e });
			throw new DaoException(e);
		}
	}

	/**
	 * Obtém um objeto do contexto de persistência, utilizando o id.
	 * 
	 * @param clazz
	 *            Tipo da classe.
	 * @param pk
	 *            Id do objeto.
	 * @param <T>
	 *            Tipo parametrizado;
	 * @return objeto.
	 * @throws DaoException
	 *             Exceção lançada no caso de erro.
	 */
	public <T extends Serializable> T find(Class<T> clazz, Object pk)
			throws DaoException {
		try {
			return eManager.find(clazz, pk);
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Obtém um objeto do contexto de persistência, locando-o.
	 * 
	 * @param clazz
	 *            Tipo da classe.
	 * @param pk
	 *            Id do objeto.
	 * @param tipoLock
	 *            Tipo de lock (otimista ou pessimista).
	 * @param <T>
	 *            Tipo parametrizado;
	 * @return Objeto gerenciado pelo contexto de persistência.
	 * @throws DaoException
	 *             Exceção lançada.
	 */
	public <T extends Serializable> List<T> find(Class<T> clazz, Object pk,
			LockModeType tipoLock) throws DaoException {
		try {
			log.info(String.format(
					"Lock efetuado para a classe %s id %d tipo %s.",
					clazz.getSimpleName(), pk, tipoLock.toString()));
			return (java.util.List<T>) eManager.find(clazz, pk, tipoLock);
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Atualiza entidade com o que está no banco de dados.
	 * 
	 * @param <T>
	 * @param entidade
	 *            Entidade.
	 * @param <T>
	 *            Tipo parametrizado;
	 */
	public <T extends Serializable> void refresh(T entidade) {
		eManager.refresh(entidade);
	}

	/**
	 * Atualiza entidade com o que está no banco de dados, forçando um lock.
	 * 
	 * @param entidade
	 *            Entidade.
	 * @param tipoLock
	 *            Tipo de lock (otimista ou pessimista).
	 * @param <T>
	 *            Tipo parametrizado;
	 */
	public <T extends Serializable> void refresh(T entidade,
			LockModeType tipoLock) {
		eManager.refresh(entidade, tipoLock);
	}

	/**
	 * Obtém uma Lista de qualquer Objeto
	 * 
	 * @param clazz
	 *            Tipo da classe.
	 * @param <T>
	 *            Tipo parametrizado.
	 * @return lista de Objetos gerenciado pelo contexto de persistência.
	 * @throws DaoException
	 *             Exceção lançada.
	 */
	public <T extends Serializable> List<T> selectAll(Class<T> clazz)
			throws DaoException {
		try {
			CriteriaBuilder cb = entityManager().getCriteriaBuilder();
			CriteriaQuery<T> c = cb.createQuery(clazz);

			c.from(clazz);

			return entityManager().createQuery(c).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error("Erro: {}-{}", e.getMessage(), e);
			throw new DaoException(e.getMessage());
		}
	}

	/**
	 * Obtém uma Lista de qualquer Objeto
	 * 
	 * @param clazz
	 *            Tipo da classe.
	 * @param first
	 *            Início da paginação.
	 * @param pageSize
	 *            limite de registros obtidos do banco.
	 * @param <T>
	 *            Tipo parametrizado;
	 * @return lista de Objetos gerenciado pelo contexto de persistência.
	 * @throws DaoException
	 *             Exceção lançada.
	 */
	public <T extends Serializable> List<T> selectAll(Class<T> clazz,
			int first, int pageSize) throws DaoException {
		try {
			CriteriaBuilder cb = entityManager().getCriteriaBuilder();
			CriteriaQuery<T> c = cb.createQuery(clazz);
			c.from(clazz);
			return entityManager().createQuery(c).setFirstResult(first)
					.setMaxResults(pageSize).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erro: {}-{}", e.getMessage(), e);
			throw new DaoException(e.getMessage());
		}
	}

	/**
	 * Obtém uma Lista de qualquer Objeto
	 * 
	 * @param clazz
	 *            Tipo da classe.
	 * @param <T>
	 *            Tipo parametrizado;
	 * @return lista de Objetos gerenciado pelo contexto de persistência.
	 * @throws DaoException
	 *             Exceção lançada.
	 */
	public <T extends Serializable> Long getRowCount(Class<T> clazz)
			throws DaoException {
		try {
			CriteriaBuilder cb = entityManager().getCriteriaBuilder();
			CriteriaQuery<Long> c = cb.createQuery(Long.class);
			c.select(cb.count(c.from(clazz)));
			return entityManager().createQuery(c).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error("Erro: {}-{}", e.getMessage(), e);
			throw new DaoException(e.getMessage());
		}
	}

	/**
	 * Método para execução de query nativa.
	 * 
	 * @param nativeQuery
	 *            String.
	 * @return List lista de objetos.
	 * @throws DaoException
	 *             erro.
	 */
	public List nativeQueryExecute(String nativeQuery) throws DaoException {
		try {
			Query query = entityManager().createNativeQuery(nativeQuery);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error("Erro: {}-{}", e.getMessage(), e);
			throw new DaoException(e.getMessage());
		}
	}

	/**
	 * Método para execução de query nativa.
	 * 
	 * @param nativeQuery
	 *            String.
	 * @param first
	 *            int .
	 * @param pageSize
	 *            int.
	 * @return List lista de objetos.
	 * @throws DaoException
	 *             erro.
	 */
	public List nativeQueryExecute(String nativeQuery, int first, int pageSize)
			throws DaoException {
		try {
			Query query = entityManager().createNativeQuery(nativeQuery);
			query.setFirstResult(first);
			query.setMaxResults(pageSize);

			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error("Erro: {}-{}", e.getMessage(), e);
			throw new DaoException(e.getMessage());
		}
	}

	/**
	 * @return EntityManager EntityManager injetado pelo contexto.
	 */
	public EntityManager entityManager() {
		return eManager;
	}

	/**
	 * Cria um TypedQuery a partir da named associada ao entity, e carrega os
	 * parametros do mapa.
	 * 
	 * @param <T>
	 *            Type da classe do entity.
	 * @param clazz
	 *            classe do entity
	 * @param namedQuery
	 *            Named query para contrucao do TypedQuery
	 * @param map
	 *            Mapa com parametros para Named.
	 * @return TypedQuery TypedQuery relacionada a Entity
	 * @throws DaoException
	 *             Erro de criacao.
	 */
	protected <T extends Serializable> TypedQuery<T> buildTypedQuery(
			Class<T> clazz, String namedQuery, Map<String, Object> map)
			throws DaoException {

		TypedQuery<T> typed = entityManager().createNamedQuery(namedQuery,
				clazz);
		if (typed == null) {
			log.error("__¢ query not found: {}", namedQuery);
			throw new DaoException("query not found: " + namedQuery);
		}

		Set<Parameter<?>> paramSet = typed.getParameters();
		if (paramSet.size() > 0 && map == null) {
			log.error("__¢ no room (map) for paramSet.size: {}",
					paramSet.size());
			throw new DaoException("no room (map) for paramSet.size: "
					+ paramSet.size());
		}

		for (Iterator<Parameter<?>> iter = paramSet.iterator(); iter.hasNext();) {
			Parameter<?> param = iter.next();
			String paramName = param.getName();
			log.debug("___¢ paramName: {}", paramName);
			if (!map.containsKey(paramName)) {
				log.error("__¢ parameter {} not found in Map.", paramName);
				throw new DaoException("parameter '" + paramName
						+ "' not found in Map.");
			}
			Object obj = map.get(paramName);
			if (!obj.getClass().getName()
					.equals(param.getParameterType().getName())) {
				log.error("__¢ paramName '{}' is '{}' but i need '{}'",
						paramName, new Object[] { obj.getClass().getName(),
								param.getClass().getName() });
				throw new DaoException("paramName '" + paramName
						+ "' has wrong type '" + param.getClass().getName()
						+ "'.");
			}
			typed.setParameter(paramName, map.get(paramName));
		}
		log.debug("__¢ typedQuery: {}", typed);
		return typed;
	}
}
