const form = document.getElementById("login-form");
const message = document.getElementById("message");

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const usuario = {
        email: document.getElementById("email").value,
        senha: document.getElementById("senha").value
    };

    try {

        const response = await login(usuario);

        if (response.ok) {

            message.className = "message success";
            message.innerText = response.data;

            console.log("Login realizado!");

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