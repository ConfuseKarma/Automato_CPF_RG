function validar() {
    const input = document.getElementById("inputValue");
    const resultado = document.getElementById("resultado");
    const valor = input.value.trim();
    
    // Limpar estados anteriores
    resultado.classList.remove('valido', 'invalido');
    resultado.style.display = 'none'; // Esconder inicialmente
    
    if (!valor) {
        resultado.textContent = "Digite um CPF ou RG";
        resultado.classList.add('invalido');
        resultado.style.display = 'block';
        return;
    }

    resultado.textContent = "Validando...";
    resultado.style.display = 'block';

    fetch('/validate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ value: valor })
    })
    .then(response => response.text())
    .then(data => {
        resultado.textContent = data;
        resultado.classList.add(data.includes("Válido") ? 'valido' : 'invalido');
        resultado.style.display = 'block';
    })
    .catch(error => {
        resultado.textContent = "Erro na validação";
        resultado.classList.add('invalido');
        resultado.style.display = 'block';
    });
}

// Evento Enter
document.getElementById("inputValue").addEventListener('keypress', (e) => {
    if (e.key === 'Enter') validar();
});