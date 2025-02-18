package com.automato_rg_cpf;
import java.util.List;
import java.util.Map;

public class Automato {
    private final Map<Integer, Map<Character, Integer>> transicoes;
    private final List<Integer> estadosFinais;

    public Automato(Map<Integer, Map<Character, Integer>> transicoes, List<Integer> estadosFinais) {
        this.transicoes = transicoes;
        this.estadosFinais = estadosFinais;
    }

    public boolean validarEntrada(String entrada) {
        int estadoAtual = 0;
        for (char c : entrada.toCharArray()) {
            if (!transicoes.containsKey(estadoAtual) || 
                !transicoes.get(estadoAtual).containsKey(c)) {
                return false;
            }
            estadoAtual = transicoes.get(estadoAtual).get(c);
        }
        return estadosFinais.contains(estadoAtual);
    }
}
