package com.automato_rg_cpf;

public class ValidaRG {

     public static boolean validarRG(String rg) {
        rg = rg.replaceAll("[^0-9X]", "");
        if (rg.length() < 7 || rg.length() > 9) {
            return false;
        }
    
        // Verifica se 'X' aparece em qualquer posição que não seja a última
        for (int i = 0; i < rg.length() - 1; i++) {
            if (rg.charAt(i) == 'X') {
                return false; // 'X' só pode estar no último dígito
            }
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
