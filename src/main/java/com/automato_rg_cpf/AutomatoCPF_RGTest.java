package com.automato_rg_cpf;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class AutomatoCPF_RGTest {

    @Test
    public void testCriarAutomatoCPF_EntradaValida() {
        Automato automato = AutomatoCPF_RG.criarAutomatoCPF();
        assertTrue(automato.validarEntrada("12345678909"));
    }

    @Test
    public void testCriarAutomatoCPF_EntradaInvalidaTamanho() {
        Automato automato = AutomatoCPF_RG.criarAutomatoCPF();
        assertFalse(automato.validarEntrada("12345"));
    }

    @Test
    public void testCriarAutomatoCPF_CaractereInvalido() {
        Automato automato = AutomatoCPF_RG.criarAutomatoCPF();
        assertFalse(automato.validarEntrada("123a5678909"));
    }

    @Test
    public void testCriarAutomatoRG_Entrada7Digitos() {
        Automato automato = AutomatoCPF_RG.criarAutomatoRG();
        assertTrue(automato.validarEntrada("1234567"));
    }

    @Test
    public void testCriarAutomatoRG_Entrada8Digitos() {
        Automato automato = AutomatoCPF_RG.criarAutomatoRG();
        assertTrue(automato.validarEntrada("12345678"));
    }

    @Test
    public void testCriarAutomatoRG_Entrada9CaracteresComX() {
        Automato automato = AutomatoCPF_RG.criarAutomatoRG();
        assertTrue(automato.validarEntrada("12345678X"));
    }

    @Test
    public void testCriarAutomatoRG_EntradaInvalidaTamanho() {
        Automato automato = AutomatoCPF_RG.criarAutomatoRG();
        assertFalse(automato.validarEntrada("123456"));
        assertFalse(automato.validarEntrada("1234567890"));
    }

    @Test
    public void testCriarAutomatoRG_XNoMeio() {
        Automato automato = AutomatoCPF_RG.criarAutomatoRG();
        assertFalse(automato.validarEntrada("123X5678"));
    }
}