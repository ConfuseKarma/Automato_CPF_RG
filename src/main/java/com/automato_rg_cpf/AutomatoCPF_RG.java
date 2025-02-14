package com.automato_rg_cpf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutomatoCPF_RG {

    public static Automato criarAutomatoGenerico(int numeroDeDigitos, List<Character> caracteresValidos, List<Integer> estadosFinais) {
        Map<Integer, Map<Character, Integer>> transicoes = new HashMap<>();

        // Configura a transição para cada dígito válido
        for (int i = 0; i < numeroDeDigitos; i++) {
            transicoes.put(i, new HashMap<>());
            for (char c : caracteresValidos) {
                transicoes.get(i).put(c, i + 1);
            }
        }

        return new Automato(transicoes, estadosFinais);
    }

    public static Automato criarAutomatoCPF() {
        // Para CPF, temos 11 dígitos numéricos
        List<Character> caracteresValidosCPF = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        return criarAutomatoGenerico(11, caracteresValidosCPF, List.of(10)); // Estado final é o 10 (11° dígito)
    }

    public static Automato criarAutomatoRG() {
        // Para RG, temos entre 7 a 9 dígitos numéricos, com 'X' no último dígito
        List<Character> caracteresValidosRG = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Map<Integer, Map<Character, Integer>> transicoes = new HashMap<>();

        // Configura a transição para os primeiros dígitos (0 a 8)
        for (int i = 0; i < 9; i++) {
            transicoes.put(i, new HashMap<>());
            for (char c : caracteresValidosRG) {
                transicoes.get(i).put(c, i + 1);
            }
        }

        // Configura a transição para o último dígito (estado 9)
        transicoes.put(9, new HashMap<>());
        for (char c : caracteresValidosRG) {
            transicoes.get(9).put(c, 10); // Transição para o estado final
        }
        transicoes.get(9).put('X', 10); // Permite 'X' no último dígito

        // Estados finais são 7, 8 e 9 (para RGs com 7, 8 ou 9 dígitos)
        return new Automato(transicoes, List.of(7, 8, 9));
    }
}