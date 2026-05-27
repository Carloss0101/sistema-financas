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

            const dados = JSON.parse(response.data);

            message.className = "message success";
            console.log(dados)
            message.innerText = dados.messagem

            localStorage.setItem("token", dados.AccessToken);
            localStorage.setItem("usuario", dados.usuario_nome);
            localStorage.setItem("usuarioID", dados.usuario_id);
            console.log("Login realizado!");

            setTimeout(() => {
                window.location.href = "index.html";
            }, 1500);
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