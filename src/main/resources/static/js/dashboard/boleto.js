let usuarioIdBoleto = null;

export function inicializarSistemaBoletos(usuarioId) {
    usuarioIdBoleto = usuarioId;

    const modalBoletos = document.getElementById("modal-boletos");
    const modalFormBoleto = document.getElementById("modal-form-boleto");
    const btnAbrirBoletos = document.getElementById("btn-abrir-boletos");
    const btnFecharBoletos = document.getElementById("btn-fechar-boletos");
    const btnFecharFormBoleto = document.getElementById("btn-fechar-form-boleto");
    const btnNovoBoleto = document.getElementById("btn-novo-boleto");
    const tabelaBoletosBody = document.getElementById("tabela-boletos-body");
    const formBoleto = document.getElementById("form-boleto");
    const modalBoletoTitulo = document.getElementById("modal-boleto-titulo");

    if (!modalBoletos || !btnAbrirBoletos) return;

    btnAbrirBoletos.addEventListener("click", () => {
        modalBoletos.classList.add("active");
        carregarBoletos(tabelaBoletosBody);
    });

    btnFecharBoletos.addEventListener("click", () => modalBoletos.classList.remove("active"));
    btnFecharFormBoleto.addEventListener("click", () => modalFormBoleto.classList.remove("active"));

    btnNovoBoleto.addEventListener("click", () => {
        formBoleto.reset();
        document.getElementById("boleto-id").value = "";
        modalBoletoTitulo.innerText = "Cadastrar Boleto";
        modalFormBoleto.classList.add("active");
    });

    formBoleto.addEventListener("submit", async (e) => {
        e.preventDefault();

        const id = document.getElementById("boleto-id").value;
        const dadosBoleto = {
            titulo: document.getElementById("boleto-titulo").value,
            valor: parseFloat(document.getElementById("boleto-valor").value),
            vencimento: document.getElementById("boleto-vencimento").value,
            codigoBarras: document.getElementById("boleto-codigo").value
        };

        let res;
        if (id) {
            res = await editarBoleto(id, dadosBoleto);
        } else {
            res = await cadastrarBoleto(dadosBoleto, usuarioIdBoleto);
        }

        if (res.ok) {
            modalFormBoleto.classList.remove("active");
            formBoleto.reset();
            carregarBoletos(tabelaBoletosBody);

            window.dispatchEvent(new CustomEvent("atualizarDadosDashboard"));
        } else {
            alert("Erro ao salvar o boleto: " + res.data);
        }
    });
}

async function carregarBoletos(tabelaBody) {
    tabelaBody.innerHTML = "";
    try {
        const response = await listarBoletos(usuarioIdBoleto);
        if (response.ok) {
            const boletos = JSON.parse(response.data);

            if (boletos.length === 0) {
                tabelaBody.innerHTML = `<tr><td colspan="5" style="color: #cbd5ff; text-align: center;">Nenhum boleto cadastrado.</td></tr>`;
                return;
            }

            boletos.forEach(b => {
                const tr = document.createElement("tr");
                const dataVenc = b.vencimento ? b.vencimento.split("-").reverse().join("/") : "-";
                const statusTexto = b.pago ? "Pago" : "Pendente";
                const statusClasse = b.pago ? "texto-receita" : "texto-despesa";

                const valorFormatado = typeof formatarMoeda === "function"
                    ? formatarMoeda(b.valor)
                    : b.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

                tr.innerHTML = `
                    <td>${b.titulo}</td>
                    <td>${valorFormatado}</td>
                    <td>${dataVenc}</td>
                    <td class="${statusClasse}">${statusTexto}</td>
                    <td>
                        <div style="display: flex; gap: 5px;">
                            ${!b.pago ? `<button class="btn-acao" style="background-color: #16a34a; padding: 4px 8px; font-size: 11px;" data-pagar="${b.id}">Pagar</button>` : ''}
                            <button class="btn-acao" style="background-color: var(--cor-botao-azul); padding: 4px 8px; font-size: 11px;" data-editar='${JSON.stringify(b)}'>Editar</button>
                            <button class="btn-acao excluir" style="padding: 4px 8px; font-size: 11px;" data-excluir="${b.id}">Excluir</button>
                        </div>
                    </td>
                `;
                tabelaBody.appendChild(tr);
            });

            tabelaBody.querySelectorAll("[data-pagar]").forEach(btn =>
                btn.addEventListener("click", (e) => efetuarPagamento(e, tabelaBody))
            );
            tabelaBody.querySelectorAll("[data-editar]").forEach(btn =>
                btn.addEventListener("click", prepararEdicao)
            );
            tabelaBody.querySelectorAll("[data-excluir]").forEach(btn =>
                btn.addEventListener("click", (e) => removerBoleto(e, tabelaBody))
            );
        }
    } catch (error) {
        console.error("Erro ao carregar boletos:", error);
    }
}

async function efetuarPagamento(e, tabelaBody) {
    const id = e.target.getAttribute("data-pagar");
    if (confirm("Marcar este boleto como pago?")) {
        const res = await marcarBoletoComoPago(id);
        if (res.ok) {
            carregarBoletos(tabelaBody);
            window.dispatchEvent(new CustomEvent("atualizarDadosDashboard"));
        }
    }
}

function prepararEdicao(e) {
    const boleto = JSON.parse(e.currentTarget.getAttribute("data-editar"));

    document.getElementById("boleto-id").value = boleto.id;
    document.getElementById("boleto-titulo").value = boleto.titulo;
    document.getElementById("boleto-valor").value = boleto.valor;
    document.getElementById("boleto-vencimento").value = boleto.vencimento;
    document.getElementById("boleto-codigo").value = boleto.codigoBarras || "";

    document.getElementById("modal-boleto-titulo").innerText = "Editar Boleto";
    document.getElementById("modal-form-boleto").classList.add("active");
}

async function removerBoleto(e, tabelaBody) {
    const id = e.target.getAttribute("data-excluir");
    if (confirm("Deseja realmente excluir este boleto?")) {
        const res = await deletarBoleto(id);
        if (res.ok) {
            carregarBoletos(tabelaBody);
        } else {
            alert("Erro ao deletar boleto.");
        }
    }
}