
import com.automato_rg_cpf.ValidaRG;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ValidaRGTest {

    @Test
    public void testRGValido() {
        assertTrue(ValidaRG.validarRG("123456782"));
    }

    @Test
    public void testRGDigitoVerificadorInvalido() {
        assertFalse(ValidaRG.validarRG("123456789"));
    }

    @Test
    public void testRGComXNoFinalInvalido() {
        // Exemplo onde o dígito esperado é 0, mas o último caractere é 'X'
        assertFalse(ValidaRG.validarRG("21093994X"));
    }

    @Test
    public void testRGTamanhoInvalido() {
        assertFalse(ValidaRG.validarRG("12345"));
    }

    @Test
    public void testRGXNoMeio() {
        assertFalse(ValidaRG.validarRG("12X345678"));
    }
}