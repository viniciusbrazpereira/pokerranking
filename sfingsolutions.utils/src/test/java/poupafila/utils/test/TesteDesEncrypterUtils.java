package poupafila.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import sfingsolutions.utils.DesEncrypterUtils;

/**
 * 
 * @author 804875
 *
 */
public class TesteDesEncrypterUtils {

    /**
     * 
     */
    @Test
    public void testarCriptografia() {
        // Given
        String mensagemOriginal = "abcABC123@$%счуѕ+!.;/?";
        String mensagemCriptografa = "";
        String retorno = "";

        // When
        mensagemCriptografa = DesEncrypterUtils.getInstance().encrypt(
                mensagemOriginal);
        retorno = DesEncrypterUtils.getInstance().decrypt(mensagemCriptografa);

        // Then
        assertNotNull(mensagemCriptografa);
        assertNotNull(retorno);
        assertEquals(mensagemOriginal, retorno);
    }

    /*
     * @Test public void encriptografar() { String senha = "11111"; String
     * mensagemCriptografa = DesEncrypterUtils.getInstance().encrypt(senha);
     * fail(mensagemCriptografa); }
     */

}
