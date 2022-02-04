package br.com.sga.financeiro;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.Date;

import br.com.sga.pessoal.Aluno;
import br.com.sga.datehelper.DateHelper;

public class GerenciadorCaixa {
    private Map<Integer, Boolean> mensalidade;
    private List<Aluno> alunos;

    public GerenciadorCaixa(List<Aluno> alunos) {
        mensalidade = new TreeMap<>();
        this.alunos = alunos;
    }

    public void caixaDiario() {
        for (Aluno aluno : alunos) {
            Integer matricula = aluno.getMatricula();
            mensalidade.put(matricula, verificarMensalidade(matricula));
        }
    }

    public Boolean verificarMensalidade(Integer matricula) {
        Aluno aluno = getAluno(matricula);
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
    }

    public Aluno removerAluno(Integer matricula) {
        Aluno aluno = getAluno(matricula);
        alunos.remove(aluno);
        return aluno;
    }

    public Aluno getAluno(Integer matricula) {
        for (int index = 0; index < alunos.size(); index++) {
            if (alunos.get(index).getMatricula().equals(matricula)) {
                return alunos.get(index);
            }
        }
        return null;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
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
