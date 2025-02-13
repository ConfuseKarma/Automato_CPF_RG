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

        // Chamada da página HTML
        server.createContext("/", new StaticFileHandler("index.html"));

        // Chamada do JavaScript
        server.createContext("/script.js", new StaticFileHandler("script.js"));

        // Endpoint para validação
        server.createContext("/validate", new ValidationHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:8080/");
    }

    // Manipulador para arquivos estáticos (HTML e JS)
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

            byte[] fileBytes = new FileInputStream(file).readAllBytes();
            exchange.sendResponseHeaders(200, fileBytes.length);
            exchange.getResponseBody().write(fileBytes);
            exchange.close();
        }
    }

    // Manipulador para validação de CPF/RG
    static class ValidationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                        .lines().collect(Collectors.joining("\n"));
    
                JSONObject json = new JSONObject(requestBody);
                String input = json.getString("value");
    
                boolean isCPF = input.replaceAll("\\D", "").length() == 11;
                String responseMessage;
    
                if (isCPF) {
                    // Valida CPF (Chama o método de validar CPF)
                    boolean isValidCPF = ValidaCPF.validarCPF(input);
                    responseMessage = isValidCPF ? "CPF Válido" : "CPF Inválido";
                } else {
                    // Valida RG (Chama o método de validar RG)
                    boolean isValidRG = ValidaRG.validarRG(input);
                    responseMessage = isValidRG ? "RG Válido" : "RG Inválido";
                }
    
                // Enviar resposta com o cabeçalho Content-Type como texto simples
                exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                byte[] responseBytes = responseMessage.getBytes(StandardCharsets.UTF_8);
    
                exchange.sendResponseHeaders(200, responseBytes.length); // Envia o cabeçalho com o tamanho correto
                exchange.getResponseBody().write(responseBytes);  // Escreve a resposta
                exchange.close();
            }
        }
    }
    
   

}
