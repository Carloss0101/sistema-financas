const form = document.getElementById("cadastro-form");
const message = document.getElementById("message");

form.addEventListener("submit", async (event) => {

    event.preventDefault();

    const usuario = {
        nome: document.getElementById("nome").value,
        cpf: document.getElementById("cpf").value,
        email: document.getElementById("email").value,
        senha: document.getElementById("senha").value
    };

    try {
        const response = await cadastrar(usuario);

        if (response.ok) {
            message.className = "message success";
            message.innerText = response.data;

            form.reset();

        } else {
            message.className = "message error";
            message.innerText = response.data;

        }
    } catch (error) {
        console.error(error);
        message.className = "message error";
        message.innerText = "Erro ao conectar com servidor.";
    }
});