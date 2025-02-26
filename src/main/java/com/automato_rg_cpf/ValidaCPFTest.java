package com.automato_rg_cpf;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ValidaCPFTest {

    @Test
    public void testCPFValido() {
        assertTrue(ValidaCPF.validarCPF("52998224725"));
    }

    @Test
    public void testCPFInvalidoDigitosVerificadores() {
        assertFalse(ValidaCPF.validarCPF("12345678909"));
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