package sfingsolutions.filter;

import java.io.IOException;
import java.util.List;

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
import sfingsolutions.dominio.MenuEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.utils.ObjectUtils;

/**
 * PermissaoPaginasPorUsuarioFilter.
 * @author vinicius braz
 */
@WebFilter(filterName = "PermissaoPaginasPorUsuarioFilter", urlPatterns = {"/pages/protegido/*"})
public class PermissaoPaginasPorUsuarioFilter implements Filter {

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

        if (!ObjectUtils.isNull(usuario) && !isPathValid(request)) {
            servletContext.getRequestDispatcher(RequestDispatcherConstants.PAGES_PUBLIC_HOME).forward(req, resp);
            return;
        }

        chain.doFilter(req, resp);
    }

    private Boolean isPathValid(HttpServletRequest request) {
        String path = request.getServletPath();

        List<MenuEntity> listagemMenu = (List<MenuEntity>) request.getSession().getAttribute(SessionConstants.MENU);

        if (listagemMenu != null) {
            for (MenuEntity menu : listagemMenu) {
                if (menu.getUrl().equals(path)) {
                    return Boolean.TRUE;
                }
            }
        }

        return Boolean.FALSE;
    }

    @Override
    public void destroy() {
    }
}
