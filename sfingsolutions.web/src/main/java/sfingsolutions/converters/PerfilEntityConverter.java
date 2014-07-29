package sfingsolutions.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import sfingsolutions.dominio.PerfilEntity;
import sfingsolutions.helper.EJB;
import sfingsolutions.negocio.UsuarioBC;
import sfingsolutions.negocio.exception.BCException;

/**
 * Converter para PerfilEntity (PickList).
 * 
 * @author Vinicius Braz
 */
@FacesConverter("PerfilEntityConverter")
public class PerfilEntityConverter implements Converter {

    private UsuarioBC usuarioBC = EJB.lookup(UsuarioBC.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        PerfilEntity perfilEntity = new PerfilEntity();
        perfilEntity.setId(Integer.valueOf(value));

        try {
            return usuarioBC.buscarPerfilPorPerfilId(perfilEntity);
        } catch (BCException e) {
            return null;

        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((PerfilEntity) value).getId());
    }

}
