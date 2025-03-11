package com.automato_rg_cpf;

public class ValidaCPF {

    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; // Verifica se tem 11 dígitos e não é uma sequência repetitiva
        }

        int[] numeros = cpf.chars().map(c -> c - '0').toArray();

        // Calcula e compara os dígitos verificadores
        return calcularDigitoVerificador(numeros, 10) == numeros[9] &&
               calcularDigitoVerificador(numeros, 11) == numeros[10];
    }

    private static int calcularDigitoVerificador(int[] numeros, int pesoInicial) {
        int soma = 0, peso = pesoInicial;
        for (int i = 0; i < pesoInicial - 1; i++) { // Usa pesoInicial - 1 para percorrer os números corretos
            soma += numeros[i] * peso--;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}
