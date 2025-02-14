package com.automato_rg_cpf;

import java.util.List;
import java.util.Map;

public class Automato {
    private final Map<Integer, Map<Character, Integer>> transicoes;
    public Automato(Map<Integer, Map<Character, Integer>> transicoes, List<Integer> estadosFinais) {
        this.transicoes = transicoes;
    }

    public boolean validarEntrada(String entrada) {
        int estadoAtual = 0;
        
        for (char c : entrada.toCharArray()) {
            if (!transicoes.containsKey(estadoAtual) || !transicoes.get(estadoAtual).containsKey(c)) {
                return false; // Se não há transição, a entrada é inválida
            }
            estadoAtual = transicoes.get(estadoAtual).get(c);
        }
        
        // A validação final (se é CPF ou RG) vai ser feita fora do automato
        return true;
    }
    
    
}
