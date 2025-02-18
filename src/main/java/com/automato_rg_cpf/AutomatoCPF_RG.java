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
    List<Character> digitos = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    Map<Integer, Map<Character, Integer>> transicoes = new HashMap<>();

    // Estados 0-8: aceitam dígitos
    for (int i = 0; i < 9; i++) {
        transicoes.put(i, new HashMap<>());
        for (char c : digitos) {
            transicoes.get(i).put(c, i + 1);
        }
    }

    // Estado 9: último dígito (pode ser 'X' para RG de 9 caracteres)
    transicoes.put(9, new HashMap<>());
    for (char c : digitos) {
        transicoes.get(9).put(c, 10);
    }
    transicoes.get(9).put('X', 10);

    // Estados finais: 7, 8, 9, 10 (para 8, 9, 10, 11 caracteres?)
    // Ajuste conforme a regra real do RG (ex: 7-9 dígitos + 'X' opcional)
    return new Automato(transicoes, List.of(7, 8, 9, 10)); // Exemplo ajustado
}
}
