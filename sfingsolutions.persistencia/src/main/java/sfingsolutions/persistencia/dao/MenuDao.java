package sfingsolutions.persistencia.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Default;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
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
 * Objeto de acesso a dados da entidade MenuEntity.
 * 
 * @author Viniciusbrazpereira.
 */
@Default
public class MenuDao extends MasterDao {

    private static final Logger log = LoggerFactory.getLogger(MenuDao.class);

    /**
     * UsuarioDao()
     */
    public MenuDao() {
        log.debug("__p MenuDao");
    }

    /**
     * Lista os menus para o usuário informado.
     * @param usuarioEntity to usuarioEntity to set
     * @return the List<MenuEntity>
     * @throws DaoException to DaoException
     */
    public List<MenuEntity> obterListaMenuPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);
            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);
            criteriaQuery.where(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            criteriaQuery.select(menu).distinct(true);

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus para o usuário informado.
     * @param usuarioEntity to usuarioEntity to set
     * @return the List<MenuEntity>
     * @throws DaoException to DaoException
     */
    public List<MenuEntity> obterListaMenuSistemaPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);
            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(perfil.get(PerfilEntity_.restricao), 1));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(menu).distinct(true);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus para o usuário informado.
     * @param usuarioEntity to usuarioEntity to set
     * @param first int.
     * @param pageSize int.
     * @return the List<MenuEntity>
     * @throws DaoException to DaoException
     */
    public List<MenuEntity> obterListaMenuPorUsuario(UsuarioEntity usuarioEntity, int first, int pageSize)
        throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);
            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);
            criteriaQuery.where(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            criteriaQuery.select(menu).distinct(true);

            TypedQuery<MenuEntity> query = entityManager().createQuery(criteriaQuery);

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
     * Lista os menus para o perfil informado.
     * @param perfilEntity Perfil.
     * @return the List<MenuEntity>
     * @throws DaoException to DaoException
     */
    public List<MenuEntity> listarPorPerfil(PerfilEntity perfilEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<PerfilEntity> criteriaQuery = cb.createQuery(PerfilEntity.class);
            Root<PerfilEntity> tblPerfil = criteriaQuery.from(PerfilEntity.class);
            criteriaQuery.select(tblPerfil);
            tblPerfil.fetch(PerfilEntity_.listaMenu);
            criteriaQuery.where(cb.equal(tblPerfil.get(PerfilEntity_.id), perfilEntity.getId()));
            PerfilEntity perfilEntityReturn = entityManager().createQuery(criteriaQuery).getSingleResult();
            return perfilEntityReturn.getListaMenu();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus que são pais(menu principal) para o perfil informado.
     * @param usuarioEntity Entity.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(menu.get(MenuEntity_.idPai), 0));
            clausulaWhere.add(cb.equal(perfil.get(PerfilEntity_.restricao), 0));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(menu).distinct(true);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus que são pais(menu principal) para o perfil informado.
     * @param usuarioEntity Entity.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiSistemaPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(menu.get(MenuEntity_.idPai), 0));
            clausulaWhere.add(cb.equal(perfil.get(PerfilEntity_.restricao), 1));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(menu).distinct(true);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus que são pais(menu principal).
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarListaMenuPai() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<MenuEntity> tblMenu = criteriaQuery.from(MenuEntity.class);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(tblMenu.get(MenuEntity_.idPai), 0));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(tblMenu);
            criteriaQuery.where(clausulaWhere.toArray(p));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Quantidade de registros.
     * @param usuarioEntity entity.
     * @return Long.
     * @throws DaoException erro.
     */
    public Long rowListaMenuPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            criteriaQuery.where(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            criteriaQuery.select(cb.count(menu));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus que são pais(menu principal) para o perfil informado.
     * @param usuarioEntity Entity.
     * @param first int.
     * @param pageSize int.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiPorUsuario(UsuarioEntity usuarioEntity, int first, int pageSize)
        throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(menu.get(MenuEntity_.idPai), 0));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(menu);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));
            criteriaQuery.orderBy(cb.asc(menu.get(MenuEntity_.sequencia)));

            TypedQuery<MenuEntity> query = entityManager().createQuery(criteriaQuery);

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
     * Lista os menus principais paginando.
     * @param first int.
     * @param pageSize int.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiPorUsuario(int first, int pageSize) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<MenuEntity> tblMenu = criteriaQuery.from(MenuEntity.class);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(tblMenu.get(MenuEntity_.idPai), 0));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(tblMenu);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));
            criteriaQuery.orderBy(cb.asc(tblMenu.get(MenuEntity_.sequencia)));

            TypedQuery<MenuEntity> query = entityManager().createQuery(criteriaQuery);

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
     * Quantidade de registros.
     * @param usuarioEntity entity.
     * @return Long.
     * @throws DaoException erro.
     */
    public Long rowListaMenuPaiPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(menu.get(MenuEntity_.idPai), 0));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(cb.count(menu));
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Quantidade de registros.
     * @return Long.
     * @throws DaoException erro.
     */
    public Long rowListaMenuPaiPorUsuario() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

            Root<MenuEntity> tblMenu = criteriaQuery.from(MenuEntity.class);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(tblMenu.get(MenuEntity_.idPai), 0));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(cb.count(tblMenu));
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus por id do menu pai.
     * @param usuarioEntity Entity.
     * @param idMenuPai Integer.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarMenuFilhoPorUsuarioEIdPai(UsuarioEntity usuarioEntity, Integer idMenuPai)
        throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(menu.get(MenuEntity_.idPai), idMenuPai));
            clausulaWhere.add(cb.equal(perfil.get(PerfilEntity_.restricao), 0));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(menu).distinct(true);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));
            criteriaQuery.orderBy(cb.asc(menu.get(MenuEntity_.sequencia)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus por id do menu pai.
     * @param usuarioEntity Entity.
     * @param idMenuPai Integer.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarMenuFilhoSistemaPorUsuarioEIdPai(UsuarioEntity usuarioEntity, Integer idMenuPai)
        throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(menu.get(MenuEntity_.idPai), idMenuPai));
            clausulaWhere.add(cb.equal(perfil.get(PerfilEntity_.restricao), 1));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(menu).distinct(true);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));
            criteriaQuery.orderBy(cb.asc(menu.get(MenuEntity_.sequencia)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus por id do menu pai.
     * @param idMenuPai Integer.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarMenuFilhoPorUsuarioEIdPai(Integer idMenuPai) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<MenuEntity> tbleMenu = criteriaQuery.from(MenuEntity.class);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(tbleMenu.get(MenuEntity_.idPai), idMenuPai));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(tbleMenu);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));
            criteriaQuery.orderBy(cb.asc(tbleMenu.get(MenuEntity_.sequencia)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Quantidade de registros de filhos do menu pai.
     * @param usuarioEntity entity.
     * @param idMenuPai Integer.
     * @return Long.
     * @throws DaoException erro.
     */
    public Long rowMenuFilhoPorUsuarioEIdPai(UsuarioEntity usuarioEntity, Integer idMenuPai) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(menu.get(MenuEntity_.idPai), idMenuPai));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(cb.count(menu));
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus que s„o pais(menu principal) para o perfil informado.
     * @param usuarioEntity Entity.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiPorUsuarioFull(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            clausulaWhere.add(cb.equal(menu.get(MenuEntity_.isSubMenu), true));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(menu).distinct(true);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Lista os menus filhos por usuario.
     * @param usuarioEntity Entity.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> listarMenuFilhoPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<UsuarioEntity> usuario = criteriaQuery.from(UsuarioEntity.class);

            Join<UsuarioEntity, PerfilEntity> perfil = usuario.join(UsuarioEntity_.perfis);
            Join<PerfilEntity, MenuEntity> menu = perfil.join(PerfilEntity_.listaMenu);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(usuario.get(UsuarioEntity_.id), usuarioEntity.getId()));
            // clausulaWhere.add(cb.equal(menu.get(MenuEntity_.sistema),
            // sistema));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(menu).distinct(true);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));
            criteriaQuery.orderBy(cb.asc(menu.get(MenuEntity_.sequencia)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna todos os menus.
     * @return List<MenuEntity>.
     * @throws DaoException Erro.
     */
    public List<MenuEntity> selectAll() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<MenuEntity> tblMenuEntity = criteriaQuery.from(MenuEntity.class);

            criteriaQuery.select(tblMenuEntity);
            criteriaQuery.orderBy(cb.desc(tblMenuEntity.get(MenuEntity_.isSubMenu)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Buscar menu por nome de menu.
     * @param menuEntity Entity.
     * @return MenuEntity.
     * @throws DaoException Erro.
     */
    public MenuEntity buscarMenuPorNome(MenuEntity menuEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<MenuEntity> criteriaQuery = cb.createQuery(MenuEntity.class);

            Root<MenuEntity> tblMenu = criteriaQuery.from(MenuEntity.class);

            List<Predicate> clausulaWhere = new ArrayList<Predicate>();
            clausulaWhere.add(cb.equal(tblMenu.get(MenuEntity_.nome), menuEntity.getNome()));

            Predicate[] p = new Predicate[clausulaWhere.size()];

            criteriaQuery.select(tblMenu);
            criteriaQuery.where(cb.and(clausulaWhere.toArray(p)));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }
}
