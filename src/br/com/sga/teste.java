package br.com.sga;

import java.io.IOException;
import java.util.List;

import br.com.sga.datehelper.DateHelper;
import br.com.sga.identidade.Endereco;
import br.com.sga.persistencia.GerenciadorAlunos;
import br.com.sga.pessoal.Aluno;

import br.com.sga.pessoal.Sexo;

public class teste {
  public static void main(String args[]) {
    GerenciadorAlunos ger = null;
    try {
      ger = new GerenciadorAlunos("database/Aluno.bin");
      
    } catch (IOException e) {
      System.out.println("Houve um erro ao abrir o arquivo informado!");
    }

    ger.criarAluno(new Aluno("nome", "1234-1234", Sexo.MASCULINO, "07026278363", new DateHelper("31/10/1999"), "em@ail", new Endereco(), 1));

    List<Aluno> alunos = ger.listarAlunos();

    System.out.println(alunos.size());

    for (Aluno aluno : alunos) {
      System.out.println(aluno);
    }

    ger.salvarDados();

    GerenciadorAlunos ger2 = null;
    try {
      ger2 = new GerenciadorAlunos("database/Aluno.bin");
      
    } catch (IOException e) {
      System.out.println("Houve um erro ao abrir o arquivo informado!");
    }

    alunos = ger2.listarAlunos();

    System.out.println(alunos.size());

    for (Aluno aluno : alunos) {
      System.out.println(aluno);
    }
  }
}
