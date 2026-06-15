import { inicializarSistemaBoletos } from './boleto.js';

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
    const modalTitulo = document.getElementById("modal-titulo");
    const lancamentoTipo = document.getElementById("lancamento-tipo");
    const btnFecharModal = document.getElementById("btn-fechar-modal");

    const btnNovaReceita = document.getElementById("btn-nova-receita");
    const btnNovaDespesa = document.getElementById("btn-nova-despesa");

    const dataAtual = new Date();
    let mesSelecionado = dataAtual.getMonth() + 1;
    const USUARIO_ID = localStorage.getItem("usuarioID") || 1;
    let idLancamentoEdicao = null;
    let lancamentosCarregados = [];

    inicializarSistemaBoletos(USUARIO_ID);

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

                lancamentosCarregados = lancamentosDoMes;

                if (lancamentosDoMes.length === 0) {
                    tabelaBody.innerHTML = `<tr><td colspan="4" style="color: #cbd5ff;">Nenhum lançamento neste mês.</td></tr>`;
                } else {
                    lancamentosDoMes.forEach(item => {
                        const tr = document.createElement("tr");
                        const dataFormatada = item.data ? item.data.split("-").reverse().join("/") : "-";
                        const classeValor = item.tipo.toLowerCase() === "receita" ? "texto-receita" : "texto-despesa";
                        const sinal = item.tipo.toLowerCase() === "receita" ? "" : "- ";

                        tr.innerHTML = `
                            <td>${dataFormatada}</td>
                            <td>${item.nome}</td>
                            <td class="${classeValor}">${sinal}${formatarMoeda(item.valor)}</td>
                            <td>
                                <button class="btn-acao editar" data-id="${item.id}">Editar</button>
                                <button class="btn-acao excluir" data-id="${item.id}">Excluir</button>
                            </td>
                        `;
                        tabelaBody.appendChild(tr);
                    });

                    document.querySelectorAll(".editar").forEach(btn => {
                        btn.addEventListener("click", prepararEdicao);
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

    function prepararEdicao(e) {
        const id = parseInt(e.target.getAttribute("data-id"));
        const lancamento = lancamentosCarregados.find(item => item.id === id);

        if (lancamento) {
            idLancamentoEdicao = id;
            abrirModal(lancamento.tipo, lancamento);
        }
    }

    function abrirModal(tipo, lancamento = null) {
        lancamentoTipo.value = tipo;

        if (lancamento) {
            modalTitulo.innerText = lancamento.tipo.toLowerCase() === "receita" ? "Editar Receita" : "Editar Despesa";
            document.getElementById("lancamento-nome").value = lancamento.nome;
            document.getElementById("lancamento-valor").value = lancamento.valor;
            document.getElementById("lancamento-data").value = lancamento.data;
            document.getElementById("lancamento-categoria").value = lancamento.categoria || "";
            document.getElementById("lancamento-recorrencia").checked = lancamento.recorrencia;
            document.getElementById("lancamento-descricao").value = lancamento.descricao || "";
        } else {
            idLancamentoEdicao = null;
            modalTitulo.innerText = tipo === "receita" ? "Adicionar Receita" : "Adicionar Despesa";
            document.getElementById("form-lancamento").reset();
            document.getElementById("lancamento-data").value = new Date().toISOString().split('T')[0];
        }

        modal.classList.add("active");
    }

    btnNovaReceita.addEventListener("click", () => abrirModal("receita"));
    btnNovaDespesa.addEventListener("click", () => abrirModal("despesa"));
    btnFecharModal.addEventListener("click", () => modal.classList.remove("active"));

    document.getElementById("form-lancamento").addEventListener("submit", async (e) => {
        e.preventDefault();

        const dadosLancamento = {
            nome: document.getElementById("lancamento-nome").value,
            valor: parseFloat(document.getElementById("lancamento-valor").value),
            data: document.getElementById("lancamento-data").value,
            categoria: document.getElementById("lancamento-categoria").value,
            tipo: lancamentoTipo.value,
            recorrencia: document.getElementById("lancamento-recorrencia").checked,
            descricao: document.getElementById("lancamento-descricao").value,
            usuario: { id: USUARIO_ID }
        };

        let res;

        if (idLancamentoEdicao) {
            res = await editarLancamento(idLancamentoEdicao, dadosLancamento);
        } else {
            res = await salvarLancamento(dadosLancamento);
        }

        if (res.ok) {
            modal.classList.remove("active");
            document.getElementById("form-lancamento").reset();
            idLancamentoEdicao = null;
            carregarDados();
        } else {
            alert("Erro ao salvar lançamento. Verifique as restrições de validação.");
        }
    });

    carregarDados();

    const btnExportar = document.getElementById("btn-exportar-relatorio");

    btnExportar.addEventListener("click", async () => {
        try {
            const url = `http://localhost:8080/api/relatorio/exportar?usuarioId=${USUARIO_ID}&mes=${mesSelecionado}`;
            window.open(url, '_blank');
        } catch (error) {
            console.error("Erro ao baixar o relatório:", error);
            alert("Não foi possível baixar o relatório. Tente novamente.");
        }
    });
});