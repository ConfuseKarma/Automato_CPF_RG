package com.automato_rg_cpf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutomatoCPF_RG {

    public static Automato criarAutomatoGenerico(int numeroDeDigitos, List<Character> caracteresValidos, List<Integer> estadosFinais) {
        Map<Integer, Map<Character, Integer>> transicoes = new HashMap<>();

        // Configura a transição para cada caractere válido
        for (int i = 0; i < numeroDeDigitos; i++) {
            transicoes.put(i, new HashMap<>());
            for (char c : caracteresValidos) {
                transicoes.get(i).put(c, i + 1);
            }
        }

        return new Automato(transicoes, estadosFinais);
    }

    public static Automato criarAutomatoCPF() {
        // Para CPF, temos 11 dígitos numéricos.
        // O autômato gera estados de 0 a 10, com a transição do estado 10 indo para 11.
        // Assim, um CPF válido (11 dígitos) terminará no estado 11.
        List<Character> caracteresValidosCPF = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        return criarAutomatoGenerico(11, caracteresValidosCPF, List.of(11));
    }

    public static Automato criarAutomatoRG() {
        List<Character> digitos = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Map<Integer, Map<Character, Integer>> transicoes = new HashMap<>();

        // Cria transições para os estados 0 a 7 (para os dígitos iniciais)
        for (int i = 0; i < 8; i++) {
            transicoes.put(i, new HashMap<>());
            for (char c : digitos) {
                transicoes.get(i).put(c, i + 1);
            }
        }
        // Estado 8: a partir dele, se ler um dígito ou 'X', vai para o estado 9.
        transicoes.put(8, new HashMap<>());
        for (char c : digitos) {
            transicoes.get(8).put(c, 9);
        }
        transicoes.get(8).put('X', 9);

        // Estados finais:
        // - 7: quando a entrada possui 7 dígitos;
        // - 8: quando a entrada possui 8 dígitos (todos numéricos);
        // - 9: quando a entrada possui 9 dígitos ou 8 dígitos seguidos de 'X'
        return new Automato(transicoes, List.of(7, 8, 9));
    }
}
