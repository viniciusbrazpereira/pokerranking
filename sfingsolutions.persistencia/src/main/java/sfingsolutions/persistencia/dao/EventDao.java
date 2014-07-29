package sfingsolutions.persistencia.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Default;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.PokerEventEntity;
import sfingsolutions.dominio.PokerEventEntity_;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.dominio.UsuarioPokerEventEntity;
import sfingsolutions.dominio.UsuarioPokerEventEntity_;
import sfingsolutions.persistencia.MasterDao;
import sfingsolutions.persistencia.exception.DaoException;

/**
 * Objeto de acesso a dados da entidade PokerEventEntity.
 * 
 * @author Viniciusbrazpereira.
 */
@Default
public class EventDao extends MasterDao {

    private static final Logger log = LoggerFactory.getLogger(MenuDao.class);

    /**
     * UsuarioDao()
     */
    public EventDao() {
        log.debug("EventDao");
    }

    /**
     * Retorna todos os eventos.
     * @return List<PokerEventEntity>.
     * @throws DaoException Erro.
     */
    public List<PokerEventEntity> listarAllPokerEvent() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PokerEventEntity> criteriaQuery = cb.createQuery(PokerEventEntity.class);

            Root<PokerEventEntity> tblPokerEventEntity = criteriaQuery.from(PokerEventEntity.class);

            criteriaQuery.select(tblPokerEventEntity);
            criteriaQuery.orderBy(cb.asc(tblPokerEventEntity.get(PokerEventEntity_.dataInclusao)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna todos os eventos.
     * @param first int.
     * @param pageSize int.
     * @return List<PokerEventEntity>.
     * @throws DaoException Erro.
     */
    public List<PokerEventEntity> listarAllPokerEvent(int first, int pageSize) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PokerEventEntity> query = cb.createQuery(PokerEventEntity.class);

            Root<PokerEventEntity> tblPokerEventEntity = query.from(PokerEventEntity.class);
            query.select(tblPokerEventEntity);
            query.orderBy(cb.asc(tblPokerEventEntity.get(PokerEventEntity_.dataInclusao)));

            TypedQuery<PokerEventEntity> typedQuery = entityManager().createQuery(query);

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

    /**
     * Retorna a quantidade de todos os eventos.
     * @return Long.
     * @throws DaoException Erro.
     */
    public Long rowCountAllPokerEvent() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<PokerEventEntity> tblPokerEventEntity = query.from(PokerEventEntity.class);
            query.select(cb.count(tblPokerEventEntity));

            return entityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna o usuario e evento.
     * @param usuarioPokerEventEntity entity.
     * @return UsuarioPokerEventEntity.
     * @throws DaoException Erro.
     */
    public UsuarioPokerEventEntity selectUsuarioPokerEventEntity(UsuarioPokerEventEntity usuarioPokerEventEntity)
        throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioPokerEventEntity> query = cb.createQuery(UsuarioPokerEventEntity.class);

            PokerEventEntity pokerEventEntity = usuarioPokerEventEntity.getUsuarioPokerEventEntityId()
                .getPokerEventEntity();
            UsuarioEntity usuarioEntity = usuarioPokerEventEntity.getUsuarioPokerEventEntityId().getUsuarioEntity();

            Root<UsuarioPokerEventEntity> tblUsuarioPokerEventEntity = query.from(UsuarioPokerEventEntity.class);
            tblUsuarioPokerEventEntity.fetch(UsuarioPokerEventEntity_.usuarioEntity, JoinType.LEFT);
            tblUsuarioPokerEventEntity.fetch(UsuarioPokerEventEntity_.pokerEventEntity, JoinType.LEFT);

            List<Predicate> predicateList = new ArrayList<Predicate>();
            predicateList.add(cb.equal(tblUsuarioPokerEventEntity.get(UsuarioPokerEventEntity_.pokerEventEntity),
                pokerEventEntity));
            predicateList.add(cb.equal(tblUsuarioPokerEventEntity.get(UsuarioPokerEventEntity_.usuarioEntity),
                usuarioEntity));

            query.select(tblUsuarioPokerEventEntity);
            query.where(predicateList.toArray(new Predicate[predicateList.size()]));

            return entityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna todos os usuarios e eventos com o id do evento.
     * @param usuarioPokerEventEntity entity.
     * @return List<UsuarioPokerEventEntity>.
     * @throws DaoException Erro.
     */
    public List<UsuarioPokerEventEntity> selectAllUsuarioPokerEventEntity(
        UsuarioPokerEventEntity usuarioPokerEventEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioPokerEventEntity> query = cb.createQuery(UsuarioPokerEventEntity.class);

            PokerEventEntity pokerEventEntity = usuarioPokerEventEntity.getUsuarioPokerEventEntityId()
                .getPokerEventEntity();

            Root<UsuarioPokerEventEntity> tblUsuarioPokerEventEntity = query.from(UsuarioPokerEventEntity.class);
            tblUsuarioPokerEventEntity.fetch(UsuarioPokerEventEntity_.usuarioEntity, JoinType.LEFT);
            tblUsuarioPokerEventEntity.fetch(UsuarioPokerEventEntity_.pokerEventEntity, JoinType.LEFT);

            List<Predicate> predicateList = new ArrayList<Predicate>();
            predicateList.add(cb.equal(tblUsuarioPokerEventEntity.get(UsuarioPokerEventEntity_.pokerEventEntity),
                pokerEventEntity));

            query.select(tblUsuarioPokerEventEntity);
            query.where(predicateList.toArray(new Predicate[predicateList.size()]));

            return entityManager().createQuery(query).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna todos os usuarios e eventos com o id do evento.
     * @param pokerEventEntity entity.
     * @return List<UsuarioPokerEventEntity>.
     * @throws DaoException Erro.
     */
    public List<UsuarioPokerEventEntity> selectAllUsuarioPokerEventEntity(PokerEventEntity pokerEventEntity)
        throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioPokerEventEntity> query = cb.createQuery(UsuarioPokerEventEntity.class);

            Root<UsuarioPokerEventEntity> tblUsuarioPokerEventEntity = query.from(UsuarioPokerEventEntity.class);
            tblUsuarioPokerEventEntity.fetch(UsuarioPokerEventEntity_.usuarioEntity, JoinType.LEFT);
            tblUsuarioPokerEventEntity.fetch(UsuarioPokerEventEntity_.pokerEventEntity, JoinType.LEFT);

            List<Predicate> predicateList = new ArrayList<Predicate>();
            predicateList.add(cb.equal(tblUsuarioPokerEventEntity.get(UsuarioPokerEventEntity_.pokerEventEntity),
                pokerEventEntity));

            query.select(tblUsuarioPokerEventEntity);
            query.where(predicateList.toArray(new Predicate[predicateList.size()]));

            return entityManager().createQuery(query).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna todos os usuarios e eventos com o id do usuario.
     * @param usuarioPokerEventEntity entity.
     * @return List<UsuarioPokerEventEntity>.
     * @throws DaoException Erro.
     */
    public List<UsuarioPokerEventEntity> selectAllPokerEventEntity(UsuarioPokerEventEntity usuarioPokerEventEntity)
        throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioPokerEventEntity> query = cb.createQuery(UsuarioPokerEventEntity.class);

            UsuarioEntity usuario = usuarioPokerEventEntity.getUsuarioPokerEventEntityId().getUsuarioEntity();

            Root<UsuarioPokerEventEntity> tblUsuarioPokerEventEntity = query.from(UsuarioPokerEventEntity.class);
            tblUsuarioPokerEventEntity.fetch(UsuarioPokerEventEntity_.usuarioEntity, JoinType.LEFT);
            tblUsuarioPokerEventEntity.fetch(UsuarioPokerEventEntity_.pokerEventEntity, JoinType.LEFT);

            List<Predicate> predicateList = new ArrayList<Predicate>();
            predicateList
                .add(cb.equal(tblUsuarioPokerEventEntity.get(UsuarioPokerEventEntity_.usuarioEntity), usuario));

            query.select(tblUsuarioPokerEventEntity);
            query.where(predicateList.toArray(new Predicate[predicateList.size()]));

            return entityManager().createQuery(query).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

}
