package com.automato_rg_cpf;

public class ValidaCPF {

    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove pontos e traços

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; // Verifica se tem 11 números e não é repetitivo (ex: 111.111.111-11)
        }

        int[] numeros = cpf.chars().map(c -> c - '0').toArray();
        return calcularDigitoVerificador(numeros, 9) == numeros[9] &&
               calcularDigitoVerificador(numeros, 10) == numeros[10];
    }

    private static int calcularDigitoVerificador(int[] numeros, int posicao) {
        int soma = 0, peso = posicao + 1;
        for (int i = 0; i < posicao; i++) {
            soma += numeros[i] * peso--;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}
