package sfingsolutions.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import sfingsolutions.constantes.RequestDispatcherConstants;
import sfingsolutions.constantes.SessionConstants;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.utils.ObjectUtils;
/**
 * 
 * @author 806217
 *
 */
@WebFilter(filterName = "SessionRedirectUserLogin", urlPatterns = {"/pages/public/login.jsf"})
public class SessionRedirectUserLogin implements Filter {

    private ServletContext servletContext;

    @Override
    public void init(FilterConfig config) throws ServletException {
        servletContext = config.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
        ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        UsuarioEntity usuario = (UsuarioEntity) request.getSession().getAttribute(SessionConstants.USUARIO);

        if (ObjectUtils.isReferencia(usuario)) {
            servletContext.getRequestDispatcher(RequestDispatcherConstants.PAGES_PUBLIC_HOME).forward(req, resp);
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}
