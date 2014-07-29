package sfingsolutions;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.constantes.RequestDispatcherConstants;
import sfingsolutions.constantes.SessionConstants;
import sfingsolutions.dominio.MenuEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.negocio.UsuarioBC;
import sfingsolutions.negocio.exception.BCException;
import sfingsolutions.utils.DesEncrypterUtils;
import sfingsolutions.utils.ObjectUtils;
import sfingsolutions.utils.StringUtils;

/**
 * Classe responsável pelos eventos relacionados ao login de usuários.
 * 
 * @author viniciusbrazpereira.
 */
@ViewScoped
@ManagedBean(name = "loginUsuarioMBean")
public class LoginUsuarioMBean implements Serializable {

    private static final long serialVersionUID = 906316091520317165L;
    private static final Logger logger = LoggerFactory.getLogger(LoginUsuarioMBean.class);

    @EJB
    private UsuarioBC usuarioBC;

    private String username;
    private String password;

    private String confirmPassword;
    private String currentPassword;

    private static final String DEFAULT_PASS = "pokerpoker";
    private static final int MINIMO = Integer.parseInt("5");
    private static final int MAXIMO = Integer.parseInt("30");

    /**
     * Construtor default.
     */
    public LoginUsuarioMBean() {
        logger.debug("... LoginUsuarioMBean()");
    }

    /***
     * Rotina de login no sistema.
     */
    public void login() {
        Boolean isValido = Boolean.TRUE;
        FacesContext context = getFacesContext();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        String mensagemErro = "";
        if (request.getUserPrincipal() != null) {
            mensagemErro = MessageFormat.format("Usuário {0} já autentificado pelo sistema.", username);
            isValido = Boolean.FALSE;
        }

        if (StringUtils.isVazio(mensagemErro) && (StringUtils.isVazio(username, password))) {
            mensagemErro = "Usuário ou senha não informado.";
            isValido = Boolean.FALSE;
        }

        UsuarioEntity usuario = null;
        if (StringUtils.isVazio(mensagemErro)) {
            try {
                usuario = usuarioBC.buscarUsuarioPorLogin(username.toLowerCase());
            } catch (BCException e) {
                mensagemErro = MessageFormat.format("Erro ao obter usuário: %s.", username);
                isValido = Boolean.FALSE;
            }
            if (ObjectUtils.isNull(usuario)) {
                mensagemErro = MessageFormat.format("Usuário não identificado.", username);
                isValido = Boolean.FALSE;
            }
        }

        if (!StringUtils.isVazio(mensagemErro)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagemErro, null));
        }

        if (isValido) {
            callSenhaPadrao(usuario);
        }
    }

    private void callSenhaPadrao(UsuarioEntity usuario) {

        if (!ObjectUtils.isNull(usuario) && password.equals(DEFAULT_PASS)) {

            getRequestContext().execute("dlgTrocarSenhaDialog.show()");
        } else {
            userAcess(usuario);
        }

    }

    private void userAcess(UsuarioEntity usuarioEntity) {
        FacesContext context = getFacesContext();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(username, password);

        } catch (ServletException e) {
            if (e.getMessage().startsWith("Failed to authenticate a principal")) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha inválida.", null));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível fazer sua identificação no sistema.", null));
            }
            return;
        }

        try {
            List<MenuEntity> listagemMenu = this.usuarioBC.listarMenuPorUsuario(usuarioEntity);
            List<MenuEntity> listagemMenuSistema = this.usuarioBC.listarMenuSistemaPorUsuario(usuarioEntity);
            
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            session.setAttribute(SessionConstants.USUARIO, usuarioEntity);            
            session.setAttribute(SessionConstants.MENU, listagemMenu);            
            session.setAttribute(SessionConstants.MENU_SISTEMA, listagemMenuSistema);
       
            context.getExternalContext().redirect(
                context.getExternalContext().getRequestContextPath()
                    + RequestDispatcherConstants.PAGES_PUBLIC_HOME);
       
            
        } catch (Exception e) {
            String mensagemErro = MessageFormat.format("Erro ao redirecionar para index: %s.", username);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagemErro, "");
            getFacesContext().addMessage(null, message);
        }
    }

    /**
     * Encerra a sessão do usuário no sistema.
     * 
     * @return the String
     */
    public String logout() {

        FacesContext context = (FacesContext) FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        logger.info("logout {}", request.getUserPrincipal());
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
            logger.info("session.invalidate() {}", request.getUserPrincipal());
        }
        return RequestDispatcherConstants.PAGES_PUBLIC_HOME;
    }

    public void logoutButton() {
        try {
            FacesContext context = (FacesContext) FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

            logger.info("logoutButton {}", request.getUserPrincipal());
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

            if (session != null) {
                session.invalidate();
                logger.info("session.invalidate() {}", request.getUserPrincipal());
            }

            context.getExternalContext().redirect(
                context.getExternalContext().getRequestContextPath() + RequestDispatcherConstants.PAGES_PUBLIC_LOGIN);
        } catch (Exception e) {
            String mensagemErro = MessageFormat.format("Erro ao redirecionar para index: %s.", username);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagemErro, "");
            getFacesContext().addMessage(null, message);
        }
    }

    /**
     * @param summary to summary to set
     */
    public static void addMessage(String summary) {
        addMessage("Logout ok.");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Método que altera a senha padrão.
     */
    public void alterarSenha() {
        Boolean isAlterar = Boolean.TRUE;

        if (currentPassword.length() == 0) {
            lancarExcecao("Senha atual é obrigatório.");
            isAlterar = Boolean.FALSE;
        }

        if (password.length() < MINIMO || password.length() > MAXIMO) {
            lancarExcecao("Quantidade de caracteres deve ser de 5 a 20.");
            isAlterar = Boolean.FALSE;
        }

        if (confirmPassword.length() == 0) {
            lancarExcecao("Confirmar senha é obrigatório.");
            isAlterar = Boolean.FALSE;
        }

        if (!currentPassword.equals(DEFAULT_PASS)) {
            lancarExcecao("Senha atual invalida.");
            isAlterar = Boolean.FALSE;
        }

        if (password.equals(currentPassword)) {
            lancarExcecao("Senha deve ser diferente da senha atual.");
            isAlterar = Boolean.FALSE;
        }

        if (!confirmPassword.equals(password)) {
            lancarExcecao("Senha não confere.");
            isAlterar = Boolean.FALSE;
        }

        Matcher matcher = Pattern.compile("^[a-zA-Z0-9_]*$").matcher(password);

        if (!matcher.find()) {
            lancarExcecao("Campo senha deve ser alfanumérico.");
            isAlterar = Boolean.FALSE;
        }

        if (isAlterar) {
            UsuarioEntity usuario = atualizarUsuario();
            getRequestContext().execute("dlgTrocarSenhaDialog.hide()");
            userAcess(usuario);
        }
    }

    private UsuarioEntity atualizarUsuario() {
        try {
            UsuarioEntity usuarioEntity = usuarioBC.buscarUsuarioPorLogin(username);
            String crypterPassword = DesEncrypterUtils.getInstance().encrypt(password);

            usuarioEntity.setUserPassword(crypterPassword);
            usuarioBC.atualizarUsuario(usuarioEntity);
            return usuarioEntity;
        } catch (BCException e) {
            String mensagemErro = MessageFormat.format("Erro ao alterar usuário: %s.", username);
            lancarExcecao(mensagemErro);
            return null;
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método que retorna o FacesContext
     * 
     * @return FacesContext
     */
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Método que retorna o RequestContext
     * 
     * @return RequestContext
     */
    public RequestContext getRequestContext() {
        return RequestContext.getCurrentInstance();
    }

    /**
     * Método responsável por lançar exceção ao usuário.
     * 
     * @param mensagem Texto a ser informado.
     */
    public void lancarExcecao(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        getFacesContext().addMessage(null, message);
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * @param currentPassword the currentPassword to set
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

}
