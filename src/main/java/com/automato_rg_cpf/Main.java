package com.automato_rg_cpf;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new StaticFileHandler("index.html"));
        server.createContext("/script.js", new StaticFileHandler("script.js"));
        server.createContext("/validate", new ValidationHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:8080/");
    }

    static class StaticFileHandler implements HttpHandler {
        private final String filePath;

        public StaticFileHandler(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File("src/main/resources/static/" + filePath);
            if (!file.exists()) {
                exchange.sendResponseHeaders(404, 0);
                return;
            }

            @SuppressWarnings("resource")
            byte[] fileBytes = new FileInputStream(file).readAllBytes();
            exchange.sendResponseHeaders(200, fileBytes.length);
            exchange.getResponseBody().write(fileBytes);
            exchange.close();
        }
    }

    static class ValidationHandler implements HttpHandler {
    private static final Automato automatoCPF = AutomatoCPF_RG.criarAutomatoCPF();
    private static final Automato automatoRG = AutomatoCPF_RG.criarAutomatoRG();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Lê o corpo da requisição
            String requestBody = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));

            // Converte o corpo da requisição para JSON
            JSONObject json = new JSONObject(requestBody);
            String originalInput = json.getString("value").toUpperCase();

            // Sanitização diferenciada para CPF e RG
            String cpfInput = originalInput.replaceAll("[^0-9]", ""); // Remove tudo que não for número
            String rgInput = originalInput.replaceAll("[^0-9X]", ""); // Remove tudo que não for número ou 'X'
            rgInput = rgInput.replaceAll("X(?!$)", ""); // Remove 'X' que não estão no final

            // Garantir que o 'X' só esteja no final do RG
            if (rgInput.length() > 0 && rgInput.charAt(rgInput.length() - 1) == 'X') {
                rgInput = rgInput.replace("X", "") + "X"; // Move 'X' para o final, se houver
            }

            // Verifica se a entrada é um CPF ou RG
            boolean isCPF = cpfInput.length() == 11;
            String responseMessage;
            String sanitizedInput;

            if (isCPF) {
                sanitizedInput = cpfInput;
                if (automatoCPF.validarEntrada(sanitizedInput)) {
                    responseMessage = ValidaCPF.validarCPF(sanitizedInput) 
                        ? "CPF Válido" : "CPF Inválido (Dígitos verificadores incorretos)";
                } else {
                    responseMessage = "CPF Inválido (Formato incorreto)";
                }
            } else {
                sanitizedInput = rgInput;
                // Verifica se o tamanho do RG está entre 7 e 9 caracteres
                if (sanitizedInput.length() < 7 || sanitizedInput.length() > 9) {
                    responseMessage = "RG Inválido (Formato incorreto)";
                } else if (automatoRG.validarEntrada(sanitizedInput)) {
                    responseMessage = ValidaRG.validarRG(sanitizedInput) 
                        ? "RG Válido" : "RG Inválido (Dígito verificador incorreto)";
                } else {
                    responseMessage = "RG Inválido (Formato incorreto)";
                }
            }

            // Envia a resposta
            sendResponse(exchange, responseMessage);
        }
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
    
}
