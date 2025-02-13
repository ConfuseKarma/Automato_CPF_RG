package com.automato_rg_cpf;

public class ValidaRG {

    public static boolean validarRG(String rg) {
        rg = rg.replaceAll("[^0-9X]", ""); // Remove pontos e traços

        if (rg.length() < 8 || rg.length() > 9) {
            return false; // RG deve ter entre 8 e 9 caracteres
        }

        int soma = 0, peso = rg.length();
        for (int i = 0; i < rg.length() - 1; i++) {
            soma += Character.getNumericValue(rg.charAt(i)) * peso--;
        }

        int resto = soma % 11;
        int digitoEsperado = (resto == 10) ? 0 : resto;

        char ultimoChar = rg.charAt(rg.length() - 1);
        int ultimoNumero = (ultimoChar == 'X') ? 10 : Character.getNumericValue(ultimoChar);

        return digitoEsperado == ultimoNumero;
    }

}
