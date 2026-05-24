const API_BASE_URL = "http://localhost:8080";

async function apiRequest(endpoint, method, body = null) {
    const config = {
        method: method,
        headers: {
            "Content-Type": "application/json"
        }
    };

    if (body) {
        config.body = JSON.stringify(body);
    }

    const response = await fetch(
        `${API_BASE_URL}${endpoint}`,
        config
    );

    const data = await response.text();

    return {
        ok: response.ok,
        status: response.status,
        data: data
    };
}

async function login(usuario) {
    return await apiRequest(
        "/auth/login",
        "POST",
        usuario
    );
}

async function cadastrar(usuario) {
    return await apiRequest(
        "/auth/cadastro",
        "POST",
        usuario
    );
}