package sfingsolutions.negocio.security;

import java.security.acl.Group;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.negocio.UsuarioBC;
import sfingsolutions.negocio.exception.BCException;
import sfingsolutions.utils.DesEncrypterUtils;
import sfingsolutions.utils.StringUtils;

public class SfingySolutionsCustomLoginModule extends
		UsernamePasswordLoginModule {

	private static final Logger log = LoggerFactory
			.getLogger(SfingySolutionsCustomLoginModule.class);

	private UsuarioBC usuarioBC;
	private String jndi = "java:app/sfingsolutions.negocio-1.0.1/UsuarioBC!sfingsolutions.negocio.UsuarioBC";

	/**
	 * MÈtodo construtor da classe TesteNomeSenhaLoginModule.java..
	 */
	public SfingySolutionsCustomLoginModule() {
		super();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map sharedState, Map options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		loadEjb();
	}

	/**
	 * Carrega do EJB via JNDI.
	 */
	private void loadEjb() {
		try {
			InitialContext ic = new InitialContext();
			usuarioBC = (UsuarioBC) ic.lookup(jndi);
		} catch (NamingException e) {
			e.printStackTrace();
			log.error("Nao foi possivel conectar o LoginModule a camada BC. "
					+ e.getMessage());
		}
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		if ("cidadaoPrimeiroCadastroAtivacaoCodigo".equals(getUsername())) {
			return "";
		}
		String login = getUsername();
		String password;
		try {
			password = usuarioBC.buscarSenhaPorLogin(login);
		} catch (BCException e) {
			throw new LoginException(e.getMessage());
		}
		if (StringUtils.isVazio(password)) {
			throw new LoginException("Invalid password to user " + login);
		}
		return password;
	}

	@Override
	protected boolean validatePassword(String inputPassword,
			String expectedPassword) {
		String senhaCripto = DesEncrypterUtils.getInstance().encrypt(
				inputPassword);
		return StringUtils.isStringsIguais(expectedPassword, senhaCripto);
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		SimpleGroup userRoles = new SimpleGroup("Roles");
		Group[] roleSets = new Group[1];
		try {
			userRoles.addMember(createIdentity("Admin"));
			roleSets[0] = userRoles;
		} catch (Exception erro) {
			erro.printStackTrace();
		}
		return roleSets;
	}

}
