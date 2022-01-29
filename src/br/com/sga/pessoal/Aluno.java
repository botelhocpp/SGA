package br.com.sga.pessoal;

import br.com.sga.identidade.Endereco;

public class Aluno extends Pessoa {

     public Aluno(String nome, String telefone, Sexo sexo, String cpf, String dataNascimento, String email, Endereco endereco) {
          super(nome, telefone, sexo, cpf, dataNascimento, email, endereco);
     }

     @Override
     public int compareTo(Pessoa outra) {
          return 0;
     }
     
}
