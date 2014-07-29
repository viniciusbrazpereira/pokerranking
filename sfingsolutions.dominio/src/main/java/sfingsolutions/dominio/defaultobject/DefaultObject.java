package sfingsolutions.dominio.defaultobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

/**
 * 
 * @author viniciusbrazpereira
 *
 * @param <T>
 */
public abstract class DefaultObject<T extends Integer> implements IDefaultObject<T> {
	private static final long serialVersionUID = 1L;

    @Transient
    private List<String> msg;
    
    /**
     * Construtor.
     */
    public DefaultObject() {
        msg = new ArrayList<String>();
    }

    /**
     * MÈtodo para criaÁ„o de hash code.
     * @return hash code da Entity
     */
    @Override public int hashCode() {
        int hash = 0;
        hash += getId() != null ? getId().hashCode() : 0;
        return hash;
    }

    /**
     * MÈtodo de comparaÁ„o de Entitys, baseado em IDs.
     * @param object para testar a igualdade
     * @return true se object È iqual a Entity
     */
    @Override public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (object == this) {
            return true;
        }

        if (!(object instanceof DefaultObject)) {
            return false;
        }

        DefaultObject other = (DefaultObject) object;
        if (getId() == null &&  other.getId() != null) {
            return false;
        }
        if (getId() != null &&  getId() != other.getId()) {
            return false;
        }
        if (getId() == other.getId()) {
            return true;
        }

        return false;
    }

    /** {@inheritDoc} */
    @Override public List<String> getMsg() {
        return msg;
    }

    /** {@inheritDoc} */
    @Override public boolean isDirty() {
        return !msg.isEmpty();
    }
}
