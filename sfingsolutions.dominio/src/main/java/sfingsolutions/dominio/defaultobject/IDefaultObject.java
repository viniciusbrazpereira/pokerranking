package sfingsolutions.dominio.defaultobject;

import java.io.Serializable;
import java.util.List;

/**
 * Objecto generico.
 * 
 * @author viniciusbrazpereira
 * 
 */
public interface IDefaultObject<T extends Integer> extends Serializable {
	/**
     * MÈtodo geral para leitura de IDs.
     * @return Id Entity
     */
    T getId();

    /**
     * MÈtodo geral para escrita de IDs.
     * @param pId Id da Entity
     */
    void setId(T pId);
    
    /**
     * Retorna lista de mensagens de tratamento.
     *
     * @return List<String>  Lista de mensagens.
     */
    List<String> getMsg();

    /**
     * Verifica se a entity est· com problemas de processamento.
     *
     * @return boolean Verifica se existe mensagens de erro associadas ao Entity
     */
    boolean isDirty();
}
