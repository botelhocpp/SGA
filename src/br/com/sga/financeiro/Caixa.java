package br.com.sga.financeiro;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import br.com.sga.pessoal.Aluno;
import br.com.sga.datehelper.DateHelper;

/**
 * Modela o caixa do sistema, realizando o controle
 * da parte monetária do sistema. O banco de dados
 * comporta o histórico de caixas, porém esta classe
 * é inicializada por meio de vários dados de vários
 * objetos, sendo muito poderosa no controle do caixa!
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class Caixa {
    /**
     * Mapeia os números de matrículas dos alunos e informa se
     * a mensalidade foi, ou não, paga.
     */
    private Map<Integer, Boolean> mensalidade;

    /**
     * O histórico de valores pagos, mapeando uma data a um
     * valor (referência externa)
     */
    private Map<DateHelper, Double> historicoCaixa;

    /**
     * Lista dos alunos do sistema. (referência externa)
     */
    private List<Aluno> alunos;

    /**
     * Data do caixa atual.
     */
    private DateHelper dataAtual;

    /**
     * O montante atual do caixa, antes de ser fechado.
     */
    private Double caixaAtual;

    /**
     * Informa se o caixa está, ou não, aberto.
     */
    private Boolean caixaAberto;

    /**
     * Inicializa o caixa com referências externas.
     * 
     * @param alunos Referência à lista de alunos do sistema.
     * @param historicoCaixa Referência ao mapeamento do histórico do caixa
     */
    public Caixa(List<Aluno> alunos, Map<DateHelper, Double> historicoCaixa) {
        this.mensalidade = new TreeMap<>();
        this.historicoCaixa = historicoCaixa;
        this.alunos = alunos;
        this.caixaAberto = false;
    }

    public boolean abrirCaixa() {
        if (this.caixaAberto) return false;
        this.caixaAberto = true;
        this.caixaAtual = 0.0;
        this.dataAtual = new DateHelper(new Date());
        for (Aluno aluno : alunos) {
            this.mensalidade.put(aluno.getMatricula(), verificarMensalidade(aluno.getMatricula()));
        }
        return true;
    }

    public boolean fecharCaixa() {
        if (!this.caixaAberto) return false;
        this.caixaAberto = false;
        this.historicoCaixa.put(this.dataAtual, this.caixaAtual);
        mensalidade.clear();
        return true;
    }

    /**
     * Faz o pagamento da mensalidade do usuário informado.
     */
    public boolean fazerPagamento(int matricula, Pagamento pagamento) {
        if (!this.caixaAberto) return false;
        this.caixaAtual += pagamento.getMensalidade();
        this.mensalidade.put(matricula, true);
        return true;
    }

    /**
     * Verifica se a mensalidade do aluno informado
     * está em dias. 
     */
    public boolean verificarMensalidade(int matricula) {
        Aluno aluno = obterAluno(matricula);
        if (aluno.getPagamentos().size() == 0) {
            return false;
        }

        Pagamento ultimoPagamento = aluno.getPagamentos().get(aluno.getPagamentos().size()-1);
        DateHelper dataPagamentoAluno = ultimoPagamento.getDataPagamento();
        DateHelper validadeMensalidade = new DateHelper(dataPagamentoAluno.getDate());

        validadeMensalidade.adicionarMeses(1);

        DateHelper hoje = new DateHelper(new Date());

        return (validadeMensalidade.comparar(hoje.getDate()) > 0  ? true : false);
    }

    /**
     * Adiciona um aluno informado ao sistema de caixa.
     */
    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
        verificarMensalidade(aluno.getMatricula());
    }

    public Aluno removerAluno(int matricula) {
        Aluno aluno = obterAluno(matricula);
        if (aluno != null) {
            alunos.remove(aluno);
        }
        return aluno;
    }

    public Aluno obterAluno(int matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() == matricula) {
                return aluno;
            }
        }
        return null;
    }

    public boolean obterEstadoCaixa() {
        return this.caixaAberto;
    }

    public Double buscarCaixaPorData(DateHelper date) {
        return historicoCaixa.get(date);
    }

    public List<Aluno> listarAlunosMensalidadeEmDia() {
        for (Aluno aluno : alunos) {
            this.mensalidade.put(aluno.getMatricula(), verificarMensalidade(aluno.getMatricula()));
        }
        List<Aluno> alunosMensalidadeEmDia = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> e : mensalidade.entrySet()) {
            if (e.getValue()) {
                alunosMensalidadeEmDia.add(obterAluno(e.getKey()));
            }
        }
        return alunosMensalidadeEmDia;
    }

    public List<Aluno> listarAlunosMensalidadeAtrasada() {
        for (Aluno aluno : alunos) {
            this.mensalidade.put(aluno.getMatricula(), verificarMensalidade(aluno.getMatricula()));
        }
        List<Aluno> alunosMensalidadeAtrasada = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> e : mensalidade.entrySet()) {
            if (!e.getValue()) {
                alunosMensalidadeAtrasada.add(obterAluno(e.getKey()));
            }
        }
        return alunosMensalidadeAtrasada;
    }

    @Override
    public String toString() {
        String ret =  "Alunos {";

        int cnt = 0;
        for (Aluno aluno : alunos) {
            ret += aluno;
            cnt++;
            if (cnt != alunos.size()) {
                ret += "\n";
            }
        }

        return ret;
    }

    /**
     * Retorna uma string com todos os caixas salvos.
     */
    public String listarCaixa() {
        StringBuilder acc = new StringBuilder();
        for(Map.Entry<DateHelper, Double> entrada : this.historicoCaixa.entrySet()) {
            acc.append("Data: " + entrada.getKey() + ", Valor: R$" + entrada.getValue() + "\n");
        }
        return acc.toString();
    }
}
