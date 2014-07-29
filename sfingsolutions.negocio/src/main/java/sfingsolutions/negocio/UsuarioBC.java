package sfingsolutions.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.New;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.EstadoUsuarioEntity;
import sfingsolutions.dominio.MenuEntity;
import sfingsolutions.dominio.PerfilEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.negocio.exception.BCException;
import sfingsolutions.persistencia.dao.MenuDao;
import sfingsolutions.persistencia.dao.PerfilDao;
import sfingsolutions.persistencia.dao.UsuarioDao;
import sfingsolutions.persistencia.exception.DaoException;

/**
 * Classe que possui as regras de negócio associadas ao Usuário.
 * 
 * @author Viniciusbrazpereira.
 */
@Stateless
public class UsuarioBC {

    private static final Logger log = LoggerFactory.getLogger(UsuarioBC.class);

    @Inject
    @New
    private UsuarioDao usuarioDao;

    @Inject
    @New
    private MenuDao menuDao;

    @Inject
    @New
    private PerfilDao perfilDao;

    /**
     * Construtor default.
     */
    public UsuarioBC() {
        log.info("__| UsuarioBC");
    }

    /**
     * Obtém um usuário a partir do login.
     * 
     * @param login Nome de usuário.
     * @return entidade Usuario.
     * @throws BCException exceção lançada em caso de erro.
     */
    public UsuarioEntity buscarUsuarioPorLogin(String login) throws BCException {
        UsuarioEntity usuario = null;
        try {
            usuario = usuarioDao.obterPorLogin(login);
        } catch (DaoException e) {
            log.error("Erro ao obter um usuario por login: {}", e.getMessage());
            throw new BCException(e);
        }
        return usuario;
    }

    /**
     * Obtém a senha do UsuarioLogin a partir do login.
     * 
     * @param login login do usuário.
     * @return Senha encriptografa.
     * @throws BCException exceção lançada em caso de erro.
     */
    public String buscarSenhaPorLogin(String login) throws BCException {
        String retorno = null;
        try {
            retorno = usuarioDao.obterSenhaPorLogin(login);
        } catch (DaoException e) {
            log.error("Erro ao obter a senha de um usuario por login: {}", e.getMessage());
            throw new BCException(e);
        }
        return retorno;
    }

    /**
     * @param usuarioEntity the usuarioEntity to set
     * @return the List<MenuEntity> retorno
     * @throws BCException to BCException
     */
    public List<MenuEntity> listarMenuPorUsuario(UsuarioEntity usuarioEntity) throws BCException {
        List<MenuEntity> retorno = null;
        try {
            retorno = menuDao.obterListaMenuPorUsuario(usuarioEntity);
        } catch (DaoException e) {
            log.error("Erro ao obter a menu: {}", e.getMessage());
            throw new BCException(e);
        }
        return retorno;
    }

    /**
     * @param usuarioEntity the usuarioEntity to set
     * @return the List<MenuEntity> retorno
     * @throws BCException to BCException
     */
    public List<MenuEntity> listarMenuSistemaPorUsuario(UsuarioEntity usuarioEntity) throws BCException {
        List<MenuEntity> retorno = null;
        try {
            retorno = menuDao.obterListaMenuSistemaPorUsuario(usuarioEntity);
        } catch (DaoException e) {
            log.error("Erro ao obter a menu: {}", e.getMessage());
            throw new BCException(e);
        }
        return retorno;
    }

    /**
     * Atualiza o usu·rio.
     * 
     * @param usuarioEntity Usu·rio.
     * @return UsuarioEntity
     * @throws BCException Erro.
     */
    public UsuarioEntity atualizarUsuario(UsuarioEntity usuarioEntity) throws BCException {
        try {
            return usuarioDao.updateUsuario(usuarioEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista os menus que s„o pais(menu principal) para o perfil informado.
     * 
     * @param usuarioEntity Entity.
     * @return List<MenuEntity>.
     * @throws BCException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiPorUsuario(UsuarioEntity usuarioEntity) throws BCException {
        try {
            return menuDao.buscarListaMenuPaiPorUsuario(usuarioEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista os menus que s„o pais(menu principal) para o perfil informado
     * incluindo a restricao.
     * 
     * @param usuarioEntity Entity.
     * @return List<MenuEntity>.
     * @throws BCException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiPorUsuarioFull(UsuarioEntity usuarioEntity) throws BCException {
        try {
            return menuDao.buscarListaMenuPaiPorUsuarioFull(usuarioEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista os menus filhos por usuario.
     * @param usuarioEntity Entity.
     * @return List<MenuEntity>.
     * @throws BCException Erro.
     */
    public List<MenuEntity> listarMenuFilhoPorUsuario(UsuarioEntity usuarioEntity) throws BCException {
        try {
            return menuDao.listarMenuFilhoPorUsuario(usuarioEntity);
        } catch (DaoException e) {
            log.error("Erro ao obter um usuario por pesquisa: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * @return the List<MenuEntity> retorno
     * @throws BCException the BCException
     */
    public List<MenuEntity> listarMenu() throws BCException {
        try {
            return menuDao.selectAll();
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Retorna a quantidade de menu.
     * @return Long
     * @throws BCException Erro.
     */
    public Long rowCountMenu() throws BCException {
        try {
            return menuDao.getRowCount(MenuEntity.class);
        } catch (DaoException e) {
            log.error("Erro ao obter a menu: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Lista os menus que s„o pais(menu principal).
     * @return List<MenuEntity>.
     * @throws BCException Erro.
     */
    public List<MenuEntity> buscarListaMenuPai() throws BCException {
        try {
            return menuDao.buscarListaMenuPai();
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Buscar menu por nome de menu.
     * @param menuEntity Entity.
     * @return MenuEntity.
     * @throws BCException Erro.
     */
    public MenuEntity buscarMenuPorNome(MenuEntity menuEntity) throws BCException {
        try {
            return menuDao.buscarMenuPorNome(menuEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo buscarMenuPorNome da class UsuarioBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Lista os menus que s„o pais(menu principal) para o perfil informado.
     * @param usuarioEntity Entity.
     * @param first int.
     * @param pageSize int.
     * @return List<MenuEntity>.
     * @throws BCException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiPorUsuario(UsuarioEntity usuarioEntity, int first, int pageSize)
        throws BCException {
        try {
            return menuDao.buscarListaMenuPaiPorUsuario(usuarioEntity, first, pageSize);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista os menus principais paginando.
     * @param first int.
     * @param pageSize int.
     * @return List<MenuEntity>.
     * @throws BCException Erro.
     */
    public List<MenuEntity> buscarListaMenuPaiPorUsuario(int first, int pageSize) throws BCException {
        try {
            return menuDao.buscarListaMenuPaiPorUsuario(first, pageSize);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Quantidade de registros.
     * @return Long.
     * @throws BCException erro.
     */
    public Long rowListaMenuPaiPorUsuario() throws BCException {
        try {
            return menuDao.rowListaMenuPaiPorUsuario();
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Buscar menu por id.
     * @param entity Entity.
     * @return MenuEntity.
     * @throws BCException Erro.
     */
    public MenuEntity buscarMenuPorId(MenuEntity entity) throws BCException {
        try {
            return menuDao.find(MenuEntity.class, entity.getId());
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista os menus por id do menu pai.
     * @param idMenuPai Integer.
     * @return List<MenuEntity>.
     * @throws BCException Erro.
     */
    public List<MenuEntity> buscarMenuFilhoPorUsuarioEIdPai(Integer idMenuPai) throws BCException {
        try {
            return menuDao.buscarMenuFilhoPorUsuarioEIdPai(idMenuPai);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Quantidade de registros de filhos do menu pai.
     * @param usuarioEntity entity.
     * @param idMenuPai Integer.
     * @return Long.
     * @throws BCException erro.
     */
    public Long rowMenuFilhoPorUsuarioEIdPai(UsuarioEntity usuarioEntity, Integer idMenuPai) throws BCException {
        try {
            return menuDao.rowMenuFilhoPorUsuarioEIdPai(usuarioEntity, idMenuPai);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista os menus por id do menu pai.
     * @param usuarioEntity Entity.
     * @param idMenuPai Integer.
     * @return List<MenuEntity>.
     * @throws BCException Erro.
     */
    public List<MenuEntity> buscarMenuFilhoPorUsuarioEIdPai(UsuarioEntity usuarioEntity, Integer idMenuPai)
        throws BCException {
        try {
            return menuDao.buscarMenuFilhoPorUsuarioEIdPai(usuarioEntity, idMenuPai);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Atualiza menu.
     * @param menuEntity Entity.
     * @return MenuEntity Entity.
     * @throws BCException Erro.
     */
    public MenuEntity atualizarMenu(MenuEntity menuEntity) throws BCException {
        try {
            return menuDao.update(menuEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Salvar menu.
     * @param menuEntity Entity.
     * @return MenuEntity Entity.
     * @throws BCException Erro.
     */
    public MenuEntity salvarMenu(MenuEntity menuEntity) throws BCException {
        try {
            return menuDao.persist(menuEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Remover menu.
     * @param menuEntity Entity.
     * @throws BCException Erro.
     */
    public void deleteMenu(MenuEntity menuEntity) throws BCException {
        try {
            menuEntity = menuDao.find(MenuEntity.class, menuEntity.getId());
            menuDao.delete(menuEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista o perfil por menu.
     * @param menuEntity Entity.
     * @return List<PerfilEntity>
     * @throws BCException Error.
     */
    public List<PerfilEntity> listarPerfilPorMenu(MenuEntity menuEntity) throws BCException {
        try {
            return perfilDao.listarPorMenu(menuEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo listarPerfilPorMenu da class UsuarioBC: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * ObtÈm um usu·rio a partir do cpf.
     * 
     * @param cpf String.
     * @return UsuarioEntity.
     * @throws BCException erro..
     */
    public UsuarioEntity buscarUsuarioPorCPF(String cpf) throws BCException {
        try {
            return usuarioDao.buscarUsuariPorCPF(cpf);
        } catch (DaoException e) {
            log.error("Erro ao obter um usuario por cpf: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Obtem lista de perfil por usuario.
     * @param usuarioEntity Usu·rio
     * @return List<PerfilEntity>
     * @throws BCException Erro.
     */
    public List<PerfilEntity> listarPerfilPorUsuario(UsuarioEntity usuarioEntity) throws BCException {
        try {
            return perfilDao.listarPorUsuario(usuarioEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Retorna a lista de usu·rios por nome, login, email ou estado.
     * @param usuarioEntity Entity.
     * @return List<UsuarioEntity>
     * @throws BCException Erro.
     */
    public List<UsuarioEntity> listarUsuarioPorNomeLoginEmailEstado(UsuarioEntity usuarioEntity) throws BCException {
        try {
            return usuarioDao.listarUsuarioPorNomeLoginEmailEstado(usuarioEntity);
        } catch (DaoException e) {
            log.error("Erro ao obter um usuario por pesquisa: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna a lista de usuarios.
     * @param first int.
     * @param pageSize int.
     * @return List<UsuarioEntity>
     * @throws BCException Erro.
     */
    public List<UsuarioEntity> listarAllUsuario(int first, int pageSize) throws BCException {
        try {
            return usuarioDao.listarAllUsuario(first, pageSize);
        } catch (DaoException e) {
            log.error("Erro ao obter um usuario por pesquisa: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna a quantidade de usu·rios por nome, login, email ou estado.
     * @param usuarioEntity Entity.
     * @return Long.
     * @throws BCException Erro.
     */
    public Long rowCountUsuarioPorNomeLoginEmailEstado(UsuarioEntity usuarioEntity) throws BCException {
        try {
            return usuarioDao.rowCountUsuarioPorNomeLoginEmailEstado(usuarioEntity);
        } catch (DaoException e) {
            log.error("Erro ao obter um usuario por pesquisa: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Busca usuario com local fÌsico por ID atravÈs do objeto.
     * @param usuarioEntity usu·rio.
     * @return UsuarioEntity
     * @throws BCException Erro.
     */
    public UsuarioEntity buscarUsuarioPorUsuario(UsuarioEntity usuarioEntity) throws BCException {
        try {
            return usuarioDao.buscarUsuarioPorUsuario(usuarioEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Cadastra o usu·rio.
     * @param usuarioEntity Usu·rio.
     * @return UsuarioEntity
     * @throws BCException Erro.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UsuarioEntity salvarUsuario(UsuarioEntity usuarioEntity) throws BCException {
        try {
            preparePerfilList(usuarioEntity);
            return usuarioDao.salvarUsuario(usuarioEntity);
        } catch (DaoException e) {
            log.error("Erro no metodo salvarUsuario da class UsuarioBC:", e.getMessage());
            throw new BCException(e);
        }
    }

    private void preparePerfilList(UsuarioEntity usuarioEntity) throws DaoException {
        List<PerfilEntity> newPerfilList = new ArrayList<PerfilEntity>();

        for (PerfilEntity perfiltemp : usuarioEntity.getPerfis()) {
            newPerfilList.add(perfilDao.find(PerfilEntity.class, perfiltemp.getId()));
        }

        usuarioEntity.setPerfis(newPerfilList);
    }

    /**
     * Listar todos os estados do usu·rio.
     * @return List<EstadoUsuarioEntity>.
     * @throws BCException Erro.
     */
    public List<EstadoUsuarioEntity> listarEstado() throws BCException {
        try {
            return usuarioDao.listarEstado();
        } catch (DaoException e) {
            log.error("Erro no metodo listarEstado da class UsuarioBC:", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Busca perfil por id de perfil.
     * @param perfilEntity Perfil
     * @return PerfilEntity
     * @throws BCException Erro.
     */
    public PerfilEntity buscarPerfilPorPerfilId(PerfilEntity perfilEntity) throws BCException {
        try {
            return perfilDao.buscarPorPerfilId(perfilEntity);
        } catch (DaoException e) {
            log.error("Erro ao obter a lista de menu: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Retorna a lista de usuarios.
     * @param usuarioEntity Entity.
     * @return List<UsuarioEntity>
     * @throws BCException Erro.
     */
    public List<UsuarioEntity> listarUsuario() throws BCException {
        try {
            return usuarioDao.listarUsuario();
        } catch (DaoException e) {
            log.error("Erro ao obter um usuario por pesquisa: {}", e.getMessage());
            throw new BCException(e);
        }
    }

    /**
     * Cadastra o perfil.
     * @param perfilEntity Perfil.
     * @return PerfilEntity
     * @throws BCException Erro.
     */
    public PerfilEntity salvarPerfil(PerfilEntity perfilEntity) throws BCException {
        try {
            return perfilDao.salvarPerfil(perfilEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Atualizar o perfil.
     * @param perfilEntity Perfil.
     * @return PerfilEntity
     * @throws BCException Erro.
     */
    public PerfilEntity atualizarPerfil(PerfilEntity perfilEntity) throws BCException {
        try {
            return perfilDao.updatePerfil(perfilEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Deletar o perfil.
     * @param perfilEntity Perfil.
     * @throws BCException Erro.
     */
    public void deletePerfil(PerfilEntity perfilEntity) throws BCException {
        try {
            perfilDao.deletePerfil(perfilEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Retorna a lista de usu·rios para o perfil passado.
     * @param perfilEntity Perfil.
     * @return List<UsuarioEntity>
     * @throws BCException Erro.
     */
    public List<UsuarioEntity> listarUsuarioPorPerfil(PerfilEntity perfilEntity) throws BCException {
        try {
            return usuarioDao.listarUsuarioPorPerfil(perfilEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista todos os perfieis com paginaÁ„o.
     * @param first int.
     * @param pageSize int.
     * @return List<PerfilEntity>.
     * @throws BCException Erro.
     */
    public List<PerfilEntity> listarTodosPerfil(int first, int pageSize) throws BCException {
        try {
            return perfilDao.listarTodos(first, pageSize);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista perfil por nome de perfil.
     * @param perfilEntity Perfil
     * @param first Primeiro index.
     * @param pageSize N˙mero de paginas.
     * @return List<PerfilEntity>
     * @throws BCException Erro.
     */
    public List<PerfilEntity> listarPerfilPorNome(PerfilEntity perfilEntity, int first, int pageSize)
        throws BCException {
        try {
            return perfilDao.listarPorNome(perfilEntity, first, pageSize);
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * Lista quantidade de todos os perfieis.
     * @return Long.
     * @throws BCException Erro.
     */
    public Long rowlistarTodosPerfil() throws BCException {
        try {
            return perfilDao.rowlistarTodos();
        } catch (DaoException e) {
            throw new BCException(e);
        }
    }

    /**
     * MÈtodo que traz quantidade de perfil.
     * @param perfilEntity Perfil.
     * @return Long
     * @throws BCException Erro.
     */
    public Long rowCountPerfilPorUsuario(PerfilEntity perfilEntity) throws BCException {
        Long rowCount = Long.valueOf(0);
        try {
            rowCount = perfilDao.rowCountPorNome(perfilEntity);
        } catch (DaoException e) {
            throw new BCException(e);
        }
        return rowCount;
    }

    /**
     * Lista os menus para o perfil informado.
     * @param perfilEntity Perfil
     * @return List<MenuEntity>
     * @throws BCException Erro.
     */
    public List<MenuEntity> listarMenuPorPerfil(PerfilEntity perfilEntity) throws BCException {
        List<MenuEntity> retorno = null;
        try {
            retorno = menuDao.listarPorPerfil(perfilEntity);
        } catch (DaoException e) {
            log.error("Erro ao obter a lista de menu: {}", e.getMessage());
            throw new BCException(e);
        }
        return retorno;
    }

}
