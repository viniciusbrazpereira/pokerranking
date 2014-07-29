package sfingsolutions.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.New;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.NivelEntity;
import sfingsolutions.dominio.PokerEventEntity;
import sfingsolutions.dominio.UsuarioPokerEventEntity;
import sfingsolutions.negocio.exception.BCException;
import sfingsolutions.persistencia.dao.EventDao;
import sfingsolutions.persistencia.dao.NivelDao;
import sfingsolutions.persistencia.exception.DaoException;

/**
 * Classe que possui as regras de negócio associadas ao evento.
 * 
 * @author Viniciusbrazpereira.
 */
@Stateless
public class EventBC {

    private static final Logger log = LoggerFactory.getLogger(EventBC.class);

    @Inject
    @New
    private EventDao eventDao;

    @Inject
    @New
    private NivelDao nivelDao;

    /**
     * Construtor default.
     */
    public EventBC() {
        log.info("__| EventBC");
    }

    /**
     * Retorna todos os eventos.
     * @return List<PokerEventEntity>.
     * @throws BCException Erro.
     */
    public List<PokerEventEntity> listarAllPokerEvent() throws BCException {
        try {
            return eventDao.listarAllPokerEvent();
        } catch (DaoException e) {
            log.error("Erro no metodo listarAllPokerEvent da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna todos os eventos.
     * @param first int.
     * @param pageSize int.
     * @return List<PokerEventEntity>.
     * @throws BCException Erro.
     */
    public List<PokerEventEntity> listarAllPokerEvent(int first, int pageSize) throws BCException {
        try {
            return eventDao.listarAllPokerEvent(first, pageSize);
        } catch (DaoException e) {
            log.error("Erro no metodo listarAllPokerEvent da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Método que faz inclusão de PokerEventEntity no banco de dados.
     * @param pokerEventEntity Entity.
     * @return PokerEventEntity
     * @throws DaoException Erro.
     */
    public PokerEventEntity salvarPokerEventEntity(PokerEventEntity pokerEventEntity) throws BCException {
        try {
            return eventDao.update(pokerEventEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo salvarPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Método que faz exclusao de PokerEventEntity no banco de dados.
     * @param pokerEventEntity Entity.
     * @return PokerEventEntity
     * @throws DaoException Erro.
     */
    public void deletarPokerEventEntity(PokerEventEntity pokerEventEntity) throws BCException {
        try {
            pokerEventEntity = eventDao.find(PokerEventEntity.class, pokerEventEntity.getId());
            eventDao.delete(pokerEventEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo deletarPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna a quantidade de todos os eventos.
     * @return Long.
     * @throws BCException Erro.
     */
    public Long rowCountAllPokerEvent() throws BCException {
        try {
            return eventDao.rowCountAllPokerEvent();
        } catch (DaoException e) {
            log.error("Erro no metodo rowCountAllPokerEvent da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Método que faz busca de PokerEventEntity no banco de dados.
     * @param pokerEventEntity Entity.
     * @return PokerEventEntity
     * @throws DaoException Erro.
     */
    public PokerEventEntity buscarPokerEventEntity(PokerEventEntity pokerEventEntity) throws BCException {
        try {
            return eventDao.find(PokerEventEntity.class, pokerEventEntity.getId());
        } catch (DaoException e) {
            log.error("Erro no metodo buscarPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Salvar salvarUsuarioPokerEventEntity.
     * @param usuarioPokerEventEntity entity.
     * @return salvarUsuarioPokerEventEntity
     * @throws BCException
     */
    public UsuarioPokerEventEntity salvarUsuarioPokerEventEntity(UsuarioPokerEventEntity usuarioPokerEventEntity)
        throws BCException {
        try {
            return eventDao.update(usuarioPokerEventEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo salvarUsuarioPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Excluir salvarUsuarioPokerEventEntity.
     * @param usuarioPokerEventEntity entity.
     * @throws BCException
     */
    public void deleteUsuarioPokerEventEntity(UsuarioPokerEventEntity usuarioPokerEventEntity) throws BCException {
        try {
            usuarioPokerEventEntity = eventDao.selectUsuarioPokerEventEntity(usuarioPokerEventEntity);
            eventDao.delete(usuarioPokerEventEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo deleteUsuarioPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna o usuario e evento.
     * @param usuarioPokerEventEntity entity.
     * @return UsuarioPokerEventEntity.
     * @throws BCException Erro.
     */
    public UsuarioPokerEventEntity selectUsuarioPokerEventEntity(UsuarioPokerEventEntity usuarioPokerEventEntity)
        throws BCException {

        try {
            return eventDao.selectUsuarioPokerEventEntity(usuarioPokerEventEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo deleteUsuarioPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna todos os usuarios e eventos com o id do evento.
     * @param usuarioPokerEventEntity entity.
     * @return List<UsuarioPokerEventEntity>.
     * @throws BCException Erro.
     */
    public List<UsuarioPokerEventEntity> selectAllUsuarioPokerEventEntity(
        UsuarioPokerEventEntity usuarioPokerEventEntity) throws BCException {

        try {
            List<UsuarioPokerEventEntity> entities = eventDao.selectAllUsuarioPokerEventEntity(usuarioPokerEventEntity);
            for (UsuarioPokerEventEntity entity : entities) {
                entity.getUsuarioPokerEventEntityId();
            }
            return entities;
        } catch (DaoException e) {
            log.error("Erro no metodo selectAllUsuarioPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna todos os usuarios e eventos com o id do usuario.
     * @param usuarioPokerEventEntity entity.
     * @return List<UsuarioPokerEventEntity>.
     * @throws BCException Erro.
     */
    public List<UsuarioPokerEventEntity> selectAllPokerEventEntity(UsuarioPokerEventEntity usuarioPokerEventEntity)
        throws BCException {

        try {
            List<UsuarioPokerEventEntity> entities = eventDao.selectAllPokerEventEntity(usuarioPokerEventEntity);
            for (UsuarioPokerEventEntity entity : entities) {
                entity.getUsuarioPokerEventEntityId();
            }
            return entities;
        } catch (DaoException e) {
            log.error("Erro no metodo selectAllUsuarioPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna todos os usuarios e eventos com o id do evento.
     * @param pokerEventEntity entity.
     * @return List<UsuarioPokerEventEntity>.
     * @throws DaoException Erro.
     */
    public List<UsuarioPokerEventEntity> selectAllUsuarioPokerEventEntity(PokerEventEntity pokerEventEntity)
        throws BCException {

        try {
            List<UsuarioPokerEventEntity> entities = eventDao.selectAllUsuarioPokerEventEntity(pokerEventEntity);
            for (UsuarioPokerEventEntity entity : entities) {
                entity.getUsuarioPokerEventEntityId();
            }
            return entities;
        } catch (DaoException e) {
            log.error("Erro no metodo selectAllUsuarioPokerEventEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Método que faz inclusão de NivelEntity no banco de dados.
     * @param NivelEntity Entity.
     * @return NivelEntity
     * @throws DaoException Erro.
     */
    public NivelEntity salvarNivelEntity(NivelEntity nivelEntity) throws BCException {
        try {
            return nivelDao.update(nivelEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo salvarNivelEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Método que faz exclusao de NivelEntity no banco de dados.
     * @param NivelEntity Entity.
     * @return NivelEntity
     * @throws DaoException Erro.
     */
    public void deletarNivelEntity(NivelEntity nivelEntity) throws BCException {
        try {
            nivelEntity = nivelDao.find(NivelEntity.class, nivelEntity.getId());
            nivelDao.delete(nivelEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo deletarNivelEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Método que faz busca de NivelEntity no banco de dados.
     * @param NivelEntity Entity.
     * @return NivelEntity
     * @throws DaoException Erro.
     */
    public NivelEntity buscarNivelEntity(NivelEntity nivelEntity) throws BCException {
        try {
            return nivelDao.find(NivelEntity.class, nivelEntity.getId());
        } catch (DaoException e) {
            log.error("Erro no metodo buscarNivelEntity da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna todos os niveis.
     * @return List<NivelEntity>.
     * @throws DaoException Erro.
     */
    public List<NivelEntity> listarAllNivel() throws BCException {
        try {
            return nivelDao.listarAllNivel();
        } catch (DaoException e) {
            log.error("Erro no metodo listarAllNivel da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna todos os niveis.
     * @param first int.
     * @param pageSize int.
     * @return List<NivelEntity>.
     * @throws DaoException Erro.
     */
    public List<NivelEntity> listarAllNivel(int first, int pageSize) throws BCException {
        try {
            return nivelDao.listarAllNivel(first, pageSize);
        } catch (DaoException e) {
            log.error("Erro no metodo listarAllNivel da class EventBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }
}
