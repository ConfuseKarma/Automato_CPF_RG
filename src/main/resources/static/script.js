function validar() {
    let valor = document.getElementById("inputValue").value;
    console.log("Valor digitado:", valor);  // Adicionando log de depuração

    // Requisição POST para o servidor
    fetch('/validate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ value: valor })
    })
    .then(response => response.text())  // Recebe o conteúdo da resposta como texto
    .then(data => {
        console.log("Resposta do servidor:", data);  // Verificando a resposta do servidor no console
        document.getElementById("resultado").innerText = data;  // Exibe a resposta na tela
    })
    .catch(error => {
        console.error("Erro ao validar:", error);
        document.getElementById("resultado").innerText = "Erro ao validar!";  // Exibe erro em caso de falha
    });
}
