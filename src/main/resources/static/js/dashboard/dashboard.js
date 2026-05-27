document.addEventListener("DOMContentLoaded", () => {
    const mesAtualEl = document.getElementById("mesAtual");
    const btnMesAnterior = document.getElementById("mesAnterior");
    const btnProximoMes = document.getElementById("proximoMes");

    const receitaEl = document.getElementById("receita");
    const despesaEl = document.getElementById("despesa");
    const saldoEl = document.getElementById("saldo");
    const tabelaBody = document.getElementById("tabela-lancamentos-body");
    const feedbackMsg = document.getElementById("dashboard-message");

    const modal = document.getElementById("modal-lancamento");
    const formLancamento = document.getElementById("form-form-lancamento");
    const modalTitulo = document.getElementById("modal-titulo");
    const lancamentoTipo = document.getElementById("lancamento-tipo");
    const btnFecharModal = document.getElementById("btn-fechar-modal");

    const btnNovaReceita = document.getElementById("btn-nova-receita");
    const btnNovaDespesa = document.getElementById("btn-nova-despesa");

    const dataAtual = new Date();
    let mesSelecionado = dataAtual.getMonth() + 1;
    const USUARIO_ID = localStorage.getItem("idUser") || 1;
    console.log("Usuario id index ", USUARIO_ID)
    const nomesMeses = [
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    ];

    function formatarMoeda(valor) {
        return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(valor);
    }

    function atualizarTextoMes() {
        mesAtualEl.innerText = nomesMeses[mesSelecionado - 1];
    }

    async function carregarDados() {
        feedbackMsg.innerText = "";
        atualizarTextoMes();

        try {
            const responseDash = await obterDadosDashboard(USUARIO_ID, mesSelecionado);
            if (responseDash.ok) {
                if (responseDash.data === "Nenhum lancamento este mes") {
                    receitaEl.innerText = formatarMoeda(0);
                    despesaEl.innerText = formatarMoeda(0);
                    saldoEl.innerText = formatarMoeda(0);
                } else {
                    const dados = JSON.parse(responseDash.data);
                    receitaEl.innerText = formatarMoeda(dados.totalReceita);
                    despesaEl.innerText = formatarMoeda(dados.totalDespesa);
                    saldoEl.innerText = formatarMoeda(dados.saldo);
                }
            }

            const responseLista = await listarLancamentos(USUARIO_ID);
            tabelaBody.innerHTML = "";

            if (responseLista.ok) {
                const listaCompleta = JSON.parse(responseLista.data);

                const lancamentosDoMes = listaCompleta.filter(item => {
                    if (!item.data) return false;
                    const mesItem = parseInt(item.data.split("-")[1]);
                    return mesItem === mesSelecionado;
                });

                if (lancamentosDoMes.length === 0) {
                    tabelaBody.innerHTML = `<tr><td colspan="4" style="color: #cbd5ff;">Nenhum lançamento neste mês.</td></tr>`;
                } else {
                    lancamentosDoMes.forEach(item => {
                        const tr = document.createElement("tr");

                        // Formata a data vinda do banco (yyyy-mm-dd para dd/mm/yyyy)
                        const dataFormatada = item.data ? item.data.split("-").reverse().join("/") : "-";
                        const classeValor = item.tipo.toLowerCase() === "receita" ? "texto-receita" : "texto-despesa";
                        const sinal = item.tipo.toLowerCase() === "receita" ? "" : "- ";

                        tr.innerHTML = `
                            <td>${dataFormatada}</td>
                            <td>${item.nome}</td>
                            <td class="${classeValor}">${sinal}${formatarMoeda(item.valor)}</td>
                            <td>
                                <button class="btn-acao excluir" data-id="${item.id}">Excluir</button>
                            </td>
                        `;
                        tabelaBody.appendChild(tr);
                    });

                    document.querySelectorAll(".excluir").forEach(btn => {
                        btn.addEventListener("click", deletarItem);
                    });
                }
            }
        } catch (error) {
            console.error("Erro ao processar atualização da tela:", error);
        }
    }

    async function deletarItem(e) {
        const id = e.target.getAttribute("data-id");
        if (confirm("Deseja realmente remover este lançamento?")) {
            const res = await deletarLancamento(id);
            if (res.ok) {
                carregarDados();
            } else {
                alert("Erro ao deletar lançamento.");
            }
        }
    }

    btnMesAnterior.addEventListener("click", () => {
        mesSelecionado = mesSelecionado === 1 ? 12 : mesSelecionado - 1;
        carregarDados();
    });

    btnProximoMes.addEventListener("click", () => {
        mesSelecionado = mesSelecionado === 12 ? 1 : mesSelecionado + 1;
        carregarDados();
    });

    function abrirModal(tipo) {
        lancamentoTipo.value = tipo;
        modalTitulo.innerText = tipo === "receita" ? "Adicionar Receita" : "Adicionar Despesa";
        modal.classList.add("active");

        document.getElementById("lancamento-data").value = new Date().toISOString().split('T')[0];
    }

    btnNovaReceita.addEventListener("click", () => abrirModal("receita"));
    btnNovaDespesa.addEventListener("click", () => abrirModal("despesa"));
    btnFecharModal.addEventListener("click", () => modal.classList.remove("active"));

    document.getElementById("form-lancamento").addEventListener("submit", async (e) => {
        e.preventDefault();

        const novoLancamento = {
            nome: document.getElementById("lancamento-nome").value,
            valor: parseFloat(document.getElementById("lancamento-valor").value),
            data: document.getElementById("lancamento-data").value,
            categoria: document.getElementById("lancamento-categoria").value,
            tipo: lancamentoTipo.value,
            recorrencia: false,
            descricao: "",
            usuario: { id: USUARIO_ID }
        };

        const res = await salvarLancamento(novoLancamento);
        if (res.ok) {
            modal.classList.remove("active");
            document.getElementById("form-lancamento").reset();
            carregarDados();
        } else {
            alert("Erro ao salvar lançamento. Verifique as restrições de validação.");
        }
    });

    carregarDados();
});