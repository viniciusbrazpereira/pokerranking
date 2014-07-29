package sfingsolutions.persistencia.dao;

import java.util.List;

import javax.enterprise.inject.Default;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.NivelEntity;
import sfingsolutions.dominio.NivelEntity_;
import sfingsolutions.persistencia.MasterDao;
import sfingsolutions.persistencia.exception.DaoException;

/**
 * Objeto de acesso a dados da entidade NivelEntity.
 * 
 * @author Viniciusbrazpereira.
 */
@Default
public class NivelDao extends MasterDao {

    private static final Logger log = LoggerFactory.getLogger(NivelDao.class);

    /**
     * UsuarioDao()
     */
    public NivelDao() {
        log.debug("__p NivelDao");
    }

    /**
     * Retorna todos os niveis.
     * @return List<NivelEntity>.
     * @throws DaoException Erro.
     */
    public List<NivelEntity> listarAllNivel() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<NivelEntity> criteriaQuery = cb.createQuery(NivelEntity.class);

            Root<NivelEntity> tblNivelEntity = criteriaQuery.from(NivelEntity.class);

            criteriaQuery.select(tblNivelEntity);
            criteriaQuery.orderBy(cb.asc(tblNivelEntity.get(NivelEntity_.numberNivel)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna todos os niveis.
     * @param first int.
     * @param pageSize int.
     * @return List<NivelEntity>.
     * @throws DaoException Erro.
     */
    public List<NivelEntity> listarAllNivel(int first, int pageSize) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<NivelEntity> criteriaQuery = cb.createQuery(NivelEntity.class);

            Root<NivelEntity> tblNivelEntity = criteriaQuery.from(NivelEntity.class);

            criteriaQuery.select(tblNivelEntity);
            criteriaQuery.orderBy(cb.asc(tblNivelEntity.get(NivelEntity_.numberNivel)));

            TypedQuery<NivelEntity> typedQuery = entityManager().createQuery(criteriaQuery);

            typedQuery.setFirstResult(first);
            typedQuery.setMaxResults(pageSize);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

}
