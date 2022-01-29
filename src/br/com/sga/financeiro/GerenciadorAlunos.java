package br.com.sga.financeiro;

import java.util.Map;
import java.util.Set;

import br.com.sga.pessoal.Aluno;

public class GerenciadorAlunos extends GerenciadorCaixa {
    private Map<Aluno, Boolean> mensalidades;

    public GerenciadorAlunos() {

    }

    public Boolean verificarMensalidade(int matricula) {
        return false;
    }

    public void adicionarAluno(Aluno aluno) {

    }

    public Aluno removerAluno(Aluno aluno) {
        return null;
    }

    public Aluno getAluno(int matricula) {
        return null;
    }

    public Set<Aluno> getAlunos() {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }
}
