package br.com.sga.financeiro;

import java.util.Map;
import java.util.List;
import java.util.Date;

import br.com.sga.pessoal.Aluno;
import br.com.sga.datehelper.DateHelper;

public class Gerenciador {
    private Map<Aluno, Boolean> mensalidade;
    private List<Aluno> alunos;

    public Gerenciador() {

    }

    public void caixaDiario() {
        for (int index = 0; index < alunos.size(); index++) {
            Aluno aluno = alunos.get(index);
            mensalidade.put(aluno, verificarMensalidade(aluno.getMatricula()));
        }
    }

    public Boolean verificarMensalidade(String matricula) {
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

    public Aluno removerAluno(String matricula) {
        Aluno aluno = getAluno(matricula);
        alunos.remove(aluno);
        return aluno;
    }

    public Aluno getAluno(String matricula) {
        Aluno aluno = null;
        for (int index = 0; index < alunos.size(); index++) {
            if (alunos.get(index).getMatricula().equals(matricula)) {
                aluno = alunos.get(index);
                break;
            }
        }
        return aluno;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    @Override
    public String toString() {
        String ret =  "Alunos {";

        int cnt = 0;
        for (Aluno aluno : alunos) {
            ret += aluno;
            cnt++;
            if (cnt != alunos.size()) {
                ret += ",\n";
            }
        }

        return ret;
    }
}
