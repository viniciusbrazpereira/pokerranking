package sfingsolutions.persistencia.dao;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.MenuEntity;
import sfingsolutions.dominio.MenuEntity_;
import sfingsolutions.dominio.PerfilEntity;
import sfingsolutions.dominio.PerfilEntity_;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.dominio.UsuarioEntity_;
import sfingsolutions.persistencia.MasterDao;
import sfingsolutions.persistencia.exception.DaoException;

/**
 * Objeto de acesso a dados da entidade PerfilEntity.
 * @author Vinicius Braz.
 */
@Default
public class PerfilDao extends MasterDao {

    private static final Logger log = LoggerFactory.getLogger(PerfilDao.class);

    /**
     * UsuarioDao()
     */
    public PerfilDao() {
        log.debug("__p PerfilDao");
    }

    /**
     * Lista o perfil por usuário.
     * @param usuarioEntity usuário.
     * @return List<PerfilEntity>
     * @throws DaoException Error.
     */
    public List<PerfilEntity> listarPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PerfilEntity> criteriaQuery = cb.createQuery(PerfilEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);
            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            criteriaQuery.select(perfil);
            criteriaQuery.where(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            criteriaQuery.orderBy(cb.asc(perfil.get(PerfilEntity_.nome)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Busca perfil por nome de perfil
     * @param perfilEntity Perfil
     * @param first Primeiro index.
     * @param pageSize Número de paginas.
     * @return List<PerfilEntity>
     * @throws DaoException Erro ao acessar o banco.
     */
    public List<PerfilEntity> listarPorNome(PerfilEntity perfilEntity, int first, int pageSize) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PerfilEntity> criteriaQuery = cb.createQuery(PerfilEntity.class);
            Root<PerfilEntity> tblPerfil = criteriaQuery.from(PerfilEntity.class);

            criteriaQuery.select(tblPerfil);
            criteriaQuery.where(cb.like(tblPerfil.get(PerfilEntity_.nome), perfilEntity.getNome() + "%"));

            TypedQuery<PerfilEntity> query = entityManager().createQuery(criteriaQuery);

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
     * Retorna o número de registro para a consulta.
     * @param perfilEntity Perfil.
     * @return Long
     * @throws DaoException Erro.
     */
    public Long rowCountPorNome(PerfilEntity perfilEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
            Root<PerfilEntity> tblPerfil = criteriaQuery.from(PerfilEntity.class);

            criteriaQuery.select(cb.count(tblPerfil));
            criteriaQuery.where(cb.like(tblPerfil.get(PerfilEntity_.nome), perfilEntity.getNome() + "%"));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Busca perfil por id de perfil
     * @param perfilEntity Perfil
     * @return PerfilEntity
     * @throws DaoException Erro.
     */
    public PerfilEntity buscarPorPerfilId(PerfilEntity perfilEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PerfilEntity> criteriaQuery = cb.createQuery(PerfilEntity.class);
            Root<PerfilEntity> tblPerfil = criteriaQuery.from(PerfilEntity.class);

            criteriaQuery.select(tblPerfil);
            criteriaQuery.where(cb.equal(tblPerfil.get(PerfilEntity_.id), perfilEntity.getId()));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Cadastrar Perfil
     * @param perfilEntity Perfil
     * @return PerfilEntity
     * @throws DaoException Erro.
     */
    public PerfilEntity salvarPerfil(PerfilEntity perfilEntity) throws DaoException {
        try {
            return this.persist(perfilEntity);
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Atualiza Perfil
     * @param perfilEntity Perfil
     * @return PerfilEntity
     * @throws DaoException Erro.
     */
    public PerfilEntity updatePerfil(PerfilEntity perfilEntity) throws DaoException {
        try {
            return this.update(perfilEntity);
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Deletar Perfil
     * @param perfilEntity Perfil.
     * @throws DaoException Erro.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deletePerfil(PerfilEntity perfilEntity) throws DaoException {
        try {
            perfilEntity = this.find(PerfilEntity.class, perfilEntity.getId());
            this.delete(perfilEntity);
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Listar perfil
     * @return List<PerfilEntity>
     * @throws DaoException Erro.
     */
    public List<PerfilEntity> listarAll() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PerfilEntity> criteriaQuery = cb.createQuery(PerfilEntity.class);

            Root<PerfilEntity> perfils = criteriaQuery.from(PerfilEntity.class);
            criteriaQuery.select(perfils);
            criteriaQuery.orderBy(cb.asc(perfils.get(PerfilEntity_.nome)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista todos os perfieis com paginação.
     * @param first int.
     * @param pageSize int.
     * @return List<PerfilEntity>.
     * @throws DaoException Erro.
     */
    public List<PerfilEntity> listarTodos(int first, int pageSize) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PerfilEntity> criteriaQuery = cb.createQuery(PerfilEntity.class);

            Root<PerfilEntity> perfils = criteriaQuery.from(PerfilEntity.class);
            criteriaQuery.select(perfils);
            criteriaQuery.orderBy(cb.asc(perfils.get(PerfilEntity_.nome)));

            TypedQuery<PerfilEntity> query = entityManager().createQuery(criteriaQuery);

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
     * Lista quantidade de todos os perfieis.
     * @return Long.
     * @throws DaoException Erro.
     */
    public Long rowlistarTodos() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

            Root<PerfilEntity> perfils = criteriaQuery.from(PerfilEntity.class);
            criteriaQuery.select(cb.count(perfils));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista o perfil por menu.
     * @param menuEntity Entity.
     * @return List<PerfilEntity>
     * @throws DaoException Error.
     */
    public List<PerfilEntity> listarPorMenu(MenuEntity menuEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PerfilEntity> criteriaQuery = cb.createQuery(PerfilEntity.class);

            Root<PerfilEntity> tblPerfil = criteriaQuery.from(PerfilEntity.class);
            Join<PerfilEntity, MenuEntity> tblMenu = tblPerfil.join(PerfilEntity_.listaMenu);

            criteriaQuery.select(tblPerfil);
            criteriaQuery.where(cb.equal(tblMenu.get(MenuEntity_.id), menuEntity.getId()));
            criteriaQuery.orderBy(cb.asc(tblPerfil.get(PerfilEntity_.nome)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }
}
