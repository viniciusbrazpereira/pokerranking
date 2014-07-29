package sfingsolutions.negocio.exception;

import javax.ejb.ApplicationException;

/**
 * Classe base de Exception para o projeto.
 * @author Vinicius Braz
 */
@ApplicationException(rollback = true)
public class BCException extends Exception {

    /**
     * Construtor.
     * @param message Mensagem de erro.
     */
    public BCException(String message) {
        super(message);
    }

    /**
     * Construtor.
     * @param e Exception original.
     */
    public BCException(Exception e) {
        super(e);
    }
}
