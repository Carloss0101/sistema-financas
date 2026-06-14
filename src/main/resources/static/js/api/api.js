const API_BASE_URL = "http://localhost:8080";

async function apiRequest(endpoint, method, body = null) {
    const config = {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include"
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

async function obterDadosDashboard(usuarioId, mes) {
    return await apiRequest(
        `/api/dashboard?usuarioId=${usuarioId}&mes=${mes}`,
        "GET"
    );
}

async function salvarLancamento(lancamento) {
    const userId = parseInt(lancamento.usuario.id, 10)
    return await apiRequest(
        `/lancamentos/${userId}`,
        "POST",
        lancamento
    );
}

async function listarLancamentos(userId) {
    return await apiRequest(
        `/lancamentos/listar/${userId}`,
        "GET"
    )
}

async function cadastrarBoleto(boleto, usuarioId) {
    return await apiRequest(`/api/boletos?usuarioId=${usuarioId}`, "POST", boleto);
}

async function listarBoletos(usuarioId) {
    return await apiRequest(`/api/boletos?usuarioId=${usuarioId}`, "GET");
}

async function editarBoleto(id, boleto) {
    return await apiRequest(`/api/boletos/${id}`, "PUT", boleto);
}

async function deletarBoleto(id) {
    return await apiRequest(`/api/boletos/${id}`, "DELETE");
}

async function marcarBoletoComoPago(id) {
    return await apiRequest(`/api/boletos/${id}/pagar`, "PATCH");
}

async function deletarLancamento(launchId) {
    const launchIdInt = parseInt(launchId, 10)
    return await apiRequest(
        `/lancamentos/${launchIdInt}`,
        "DELETE"
    )
}