
import com.automato_rg_cpf.ValidaCPF;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class ValidaCPFTest {

    @Test
    public void testCPFValido() {
        assertTrue(ValidaCPF.validarCPF("52998224725"));
    }

    @Test
    public void testCPFInvalidoDigitosVerificadores() {
        assertFalse(ValidaCPF.validarCPF("12245678909"));
        //Antes era 12345678909 que realmente [e válido]
    }

    @Test
    public void testCPFTodosDigitosIguais() {
        assertFalse(ValidaCPF.validarCPF("11111111111"));
    }

    @Test
    public void testCPFTamanhoInvalido() {
        assertFalse(ValidaCPF.validarCPF("123"));
    }
}