package br.com.sga.financeiro;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import br.com.sga.pessoal.Aluno;
import br.com.sga.datehelper.DateHelper;

public class Caixa {
    private Map<Integer, Boolean> mensalidade;
    private Map<DateHelper, Double> historicoCaixa;
    private List<Aluno> alunos;
    private DateHelper dataAtual;
    private Double caixaAtual;
    private Boolean caixaAberto;

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

    public boolean fazerPagamento(int matricula, Pagamento pagamento) {
        if (!this.caixaAberto) return false;
        this.caixaAtual += pagamento.getMensalidade();
        this.mensalidade.put(matricula, true);
        return true;
    }

    public boolean verificarMensalidade(int matricula) {
        Aluno aluno = obterAluno(matricula);
        if (aluno.getPagamentos().size() == 0) {
            return false;
        }

        Pagamento ultimoPagamento = aluno.getPagamentos().get(aluno.getPagamentos().size()-1);
        DateHelper dataPagamentoAluno = ultimoPagamento.getDataPagamento();

        dataPagamentoAluno.adicionarMeses(1);

        DateHelper hoje = new DateHelper(new Date());

        return (dataPagamentoAluno.comparar(hoje.getDate()) > 0  ? true : false);
    }

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
            if (aluno.getMatricula().equals(matricula)) {
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
        List<Aluno> alunosMensalidadeEmDia = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> e : mensalidade.entrySet()) {
            if (e.getValue()) {
                alunosMensalidadeEmDia.add(obterAluno(e.getKey()));
            }
        }
        return alunosMensalidadeEmDia;
    }

    public List<Aluno> listarAlunosMensalidadeAtrasada() {
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
}
