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
                @SuppressWarnings("resource")
                String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                        .lines().collect(Collectors.joining("\n"));
        
                JSONObject json = new JSONObject(requestBody);
                String input = json.getString("value").replaceAll("\\D", ""); // Remove caracteres não numéricos
                
                boolean isCPF = input.length() == 11; // CPF tem 11 caracteres numéricos
                String responseMessage = "";
        
                // Verificando se a entrada é válida com o automato
                if (isCPF) {
                    if (automatoCPF.validarEntrada(input)) {
                        // Agora, validamos o CPF com a lógica específica
                        responseMessage = ValidaCPF.validarCPF(input) ? "CPF Válido" : "CPF Inválido";
                    } else {
                        responseMessage = "CPF Inválido (Formato incorreto)";
                    }
                } else {
                    if (automatoRG.validarEntrada(input)) {
                        // Agora, validamos o RG com a lógica específica
                        responseMessage = ValidaRG.validarRG(input) ? "RG Válido" : "RG Inválido";
                    } else {
                        responseMessage = "RG Inválido (Formato incorreto)";
                    }
                }
        
                exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                byte[] responseBytes = responseMessage.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                exchange.getResponseBody().write(responseBytes);
                exchange.close();
            }
        }
        

    }
    
}
