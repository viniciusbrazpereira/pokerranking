package sfingsolutions.persistencia.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.EstadoUsuarioEntity;
import sfingsolutions.dominio.EstadoUsuarioEntity_;
import sfingsolutions.dominio.PerfilEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.dominio.UsuarioEntity_;
import sfingsolutions.persistencia.MasterDao;
import sfingsolutions.persistencia.exception.DaoException;

/**
 * Objeto de acesso a dados da entidade UsuarioEntity.
 * @author Eduardo Galego.
 */
@Default
public class UsuarioDao extends MasterDao {

    private static final Logger log = LoggerFactory.getLogger(UsuarioDao.class);

    private static final String PORCENTO = "%";

    /**
     * UsuarioDao()
     */
    public UsuarioDao() {
        log.debug("__p UsuarioDao");
    }

    /**
     * Seleciona o usuário a partir do login.
     * 
     * @param login Texto representando o login do usuário.
     * @return UsuarioEntity entidade usuário.
     * @throws DaoException exceção lançada em caso de erros.
     */
    public UsuarioEntity obterPorLogin(String login) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioEntity> query = cb.createQuery(UsuarioEntity.class);

            Root<UsuarioEntity> root = query.from(UsuarioEntity.class);
            root.fetch(UsuarioEntity_.perfis, JoinType.LEFT);

            query.where(cb.like(root.get(UsuarioEntity_.userLogin), login));

            return entityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Seleciona a senha de um usuário a partir do login.
     * 
     * @param login Texto representando o login do usuário.
     * @return Senha encriptografa.
     * @throws DaoException exceção lançada em caso de erros.
     */
    public String obterSenhaPorLogin(String login) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<String> query = cb.createQuery(String.class);

            Root<UsuarioEntity> root = query.from(UsuarioEntity.class);
            query.where(cb.equal(root.get(UsuarioEntity_.userLogin), login));
            query.select(root.get(UsuarioEntity_.userPassword));

            return entityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Obtém a senha do CidadaoLogin a partir do e-mail.
     * 
     * @param email Endereço eletrônico do cidadão.
     * @return Senha encriptografada.
     */
    public String buscarSenhaPorEmail(String email) {
        String retorno = null;
        return retorno;
    }

    /**
     * Método que faz inclusão de usuáio no banco de dados.
     * @param usuarioEntity Usuário.
     * @return UsuarioEntity
     * @throws DaoException Erro.
     */
    public UsuarioEntity salvarUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            return this.update(usuarioEntity);
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Método que faz alteração de usuáio no banco de dados.
     * @param usuarioEntity Usuário.
     * @return UsuarioEntity
     * @throws DaoException Erro.
     */
    public UsuarioEntity updateUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            return this.update(usuarioEntity);
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Exclui o usuário da base de dados.
     * @param usuarioEntity Usuário.
     * @throws DaoException Erro.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteUsuario(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            usuarioEntity = this.find(UsuarioEntity.class, usuarioEntity.getId());
            this.delete(usuarioEntity);
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna a lista de usuários para o perfil passado.
     * @param perfilEntity Perfil.
     * @return List<UsuarioEntity>
     * @throws DaoException Erro.
     */
    public List<UsuarioEntity> listarUsuarioPorPerfil(PerfilEntity perfilEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioEntity> criteriaQuery = cb.createQuery(UsuarioEntity.class);

            Root<UsuarioEntity> tblusuarioEntity = criteriaQuery.from(UsuarioEntity.class);

            criteriaQuery.select(tblusuarioEntity).distinct(true);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            List<PerfilEntity> perfis = new ArrayList<PerfilEntity>();
            perfis.add(perfilEntity);

            predicateList.add(joinIn(tblusuarioEntity, UsuarioEntity_.perfis, perfis));

            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.orderBy(cb.asc(tblusuarioEntity.get(UsuarioEntity_.nome)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Busca usuário por ID com o local fisico.
     * @param usuarioEntity Usuário.
     * @return UsuarioEntity
     * @throws DaoException Erro.
     */
    public UsuarioEntity buscarUsuarioPorUsuario(UsuarioEntity usuarioEntity) throws DaoException {

        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioEntity> criteriaQuery = cb.createQuery(UsuarioEntity.class);

            Root<UsuarioEntity> tblusuarioEntity = criteriaQuery.from(UsuarioEntity.class);
            tblusuarioEntity.fetch(UsuarioEntity_.estadoUsuario, JoinType.LEFT);

            criteriaQuery.select(tblusuarioEntity);
            criteriaQuery.where(cb.equal(tblusuarioEntity.get(UsuarioEntity_.id), usuarioEntity.getId()));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Método que faz a comparação com atributos do tipo String..
     * @param cb
     * @param tblusuarioEntity
     * @param singularAttribute
     * @param valor
     * @return Predicate
     */
    private Predicate equalString(CriteriaBuilder cb, Root<UsuarioEntity> tblusuarioEntity,
        SingularAttribute<UsuarioEntity, String> singularAttribute, String valor) {
        return cb.like(tblusuarioEntity.get(singularAttribute), valor);
    }

    /**
     * Método que faz join e o valor tipo IN.
     * @param tblusuarioEntity
     * @param listAttribute
     * @param lista
     * @return Predicate
     */
    private <T> Predicate joinIn(Root<UsuarioEntity> tblusuarioEntity, ListAttribute<UsuarioEntity, T> listAttribute,
        List<T> lista) {
        return tblusuarioEntity.join(listAttribute).in(lista);
    }

    /**
     * Retorna a lista de usuários paginados.
     * @param first int.
     * @param pageSize int.
     * @return List<UsuarioEntity>
     * @throws DaoException Erro.
     */
    public List<UsuarioEntity> listarUsuario(int first, int pageSize) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioEntity> criteriaQuery = cb.createQuery(UsuarioEntity.class);

            Root<UsuarioEntity> tblUsuario = criteriaQuery.from(UsuarioEntity.class);
            tblUsuario.fetch(UsuarioEntity_.estadoUsuario, JoinType.LEFT);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            criteriaQuery.select(tblUsuario).distinct(true);

            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.orderBy(cb.asc(tblUsuario.get(UsuarioEntity_.nome)));

            TypedQuery<UsuarioEntity> query = entityManager().createQuery(criteriaQuery);

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
     * Quantidade de usuários na base de dados.
     * @return Long.
     * @throws DaoException Erro.
     */
    public Long rowlistaUsuario() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

            Root<UsuarioEntity> tblUsuario = criteriaQuery.from(UsuarioEntity.class);

            criteriaQuery.select(cb.count(tblUsuario));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Listar todos os estados do usuário.
     * @return List<EstadoUsuarioEntity>.
     * @throws DaoException Erro.
     */
    public List<EstadoUsuarioEntity> listarEstado() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<EstadoUsuarioEntity> criteriaQuery = cb.createQuery(EstadoUsuarioEntity.class);

            Root<EstadoUsuarioEntity> tblEstado = criteriaQuery.from(EstadoUsuarioEntity.class);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            criteriaQuery.select(tblEstado);
            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.orderBy(cb.asc(tblEstado.get(EstadoUsuarioEntity_.descricao)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Busca usuario por cpf.
     * 
     * @param cpf String.
     * @throws DaoException erro.
     */
    public UsuarioEntity buscarUsuariPorCPF(String cpf) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioEntity> query = cb.createQuery(UsuarioEntity.class);

            Root<UsuarioEntity> root = query.from(UsuarioEntity.class);

            query.select(root);
            query.where(cb.equal(root.get(UsuarioEntity_.id), cpf));

            return entityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna a lista de usu·rios por nome, login, email ou estado.
     * @param usuarioEntity Entity.
     * @return List<UsuarioEntity>
     * @throws DaoException Erro.
     */
    public List<UsuarioEntity> listarUsuarioPorNomeLoginEmailEstado(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioEntity> criteriaQuery = cb.createQuery(UsuarioEntity.class);

            Root<UsuarioEntity> tblusuarioEntity = criteriaQuery.from(UsuarioEntity.class);

            tblusuarioEntity.fetch(UsuarioEntity_.estadoUsuario, JoinType.LEFT);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            String nome = usuarioEntity.getNome().replace(PORCENTO, "");
            String userlogin = usuarioEntity.getUserLogin();
            String email = usuarioEntity.getEmail();
            EstadoUsuarioEntity estado = usuarioEntity.getEstadoUsuario();

            if (nome.length() > 0) {
                predicateList.add(cb.like(tblusuarioEntity.get(UsuarioEntity_.nome), nome + PORCENTO));
            }

            if (userlogin.length() > 0) {
                predicateList.add(cb.equal(tblusuarioEntity.get(UsuarioEntity_.userLogin), userlogin));
            }

            if (email.length() > 0) {
                predicateList.add(cb.equal(tblusuarioEntity.get(UsuarioEntity_.email), email));
            }

            if (usuarioEntity.getEstadoUsuario() != null && usuarioEntity.getEstadoUsuario().getId() > 0) {
                predicateList.add(cb.equal(tblusuarioEntity.get(UsuarioEntity_.estadoUsuario), estado));
            }

            criteriaQuery.select(tblusuarioEntity);
            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.orderBy(cb.asc(tblusuarioEntity.get(UsuarioEntity_.nome)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Retorna a lista de usuarios.
     * @param first int.
     * @param pageSize int.
     * @return List<UsuarioEntity>
     * @throws DaoException Erro.
     */
    public List<UsuarioEntity> listarAllUsuario(int first, int pageSize) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioEntity> criteriaQuery = cb.createQuery(UsuarioEntity.class);

            Root<UsuarioEntity> tblUsuarioEntity = criteriaQuery.from(UsuarioEntity.class);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            criteriaQuery.select(tblUsuarioEntity);
            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.orderBy(cb.asc(tblUsuarioEntity.get(UsuarioEntity_.nome)));

            TypedQuery<UsuarioEntity> typedQuery = entityManager().createQuery(criteriaQuery);

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
     * Retorna a quantidade de usu·rios por nome, login, email ou estado.
     * @param usuarioEntity Entity.
     * @return Long.
     * @throws DaoException Erro.
     */
    public Long rowCountUsuarioPorNomeLoginEmailEstado(UsuarioEntity usuarioEntity) throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

            Root<UsuarioEntity> tblusuarioEntity = criteriaQuery.from(UsuarioEntity.class);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            String nome = usuarioEntity.getNome().replace(PORCENTO, "");
            String userlogin = usuarioEntity.getUserLogin();
            String email = usuarioEntity.getEmail();
            EstadoUsuarioEntity estado = usuarioEntity.getEstadoUsuario();

            if (nome.length() > 0) {
                predicateList.add(cb.like(tblusuarioEntity.get(UsuarioEntity_.nome), nome + PORCENTO));
            }

            if (userlogin.length() > 0) {
                predicateList.add(cb.equal(tblusuarioEntity.get(UsuarioEntity_.userLogin), userlogin));
            }

            if (email.length() > 0) {
                predicateList.add(cb.equal(tblusuarioEntity.get(UsuarioEntity_.email), email));
            }

            if (usuarioEntity.getEstadoUsuario() != null && usuarioEntity.getEstadoUsuario().getId() > 0) {
                predicateList.add(cb.equal(tblusuarioEntity.get(UsuarioEntity_.estadoUsuario), estado));
            }

            criteriaQuery.select(cb.count(tblusuarioEntity));
            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));

            return entityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * Listar todos os usuário.
     * @return List<UsuarioEntity>.
     * @throws DaoException Erro.
     */
    public List<UsuarioEntity> listarUsuario() throws DaoException {
        try {
            CriteriaBuilder cb = entityManager().getCriteriaBuilder();
            CriteriaQuery<UsuarioEntity> criteriaQuery = cb.createQuery(UsuarioEntity.class);

            Root<UsuarioEntity> tblUsuarioEntity = criteriaQuery.from(UsuarioEntity.class);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            criteriaQuery.select(tblUsuarioEntity);
            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
            criteriaQuery.orderBy(cb.asc(tblUsuarioEntity.get(UsuarioEntity_.nome)));

            return entityManager().createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            log.error("Erro: {}-{}", e.getMessage(), e);
            throw new DaoException(e.getMessage());
        }
    }

}
