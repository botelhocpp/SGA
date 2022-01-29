package br.com.sga.pessoal;

import br.com.sga.empresa.Usuario;
import br.com.sga.identidade.Endereco;

public class Funcionario extends Pessoa {

     private Usuario usuario;

     public Funcionario(String nome, String telefone, Sexo sexo, String cpf, String dataNascimento, String email, Endereco endereco, String login, String senha) {
          super(nome, telefone, sexo, cpf, dataNascimento, email, endereco);
          this.usuario = new Usuario(login, senha);
     }

     public Usuario getUsuario() {
          return this.usuario;
     }

     @Override
     public int compareTo(Pessoa outra) {
          int comparaNome = this.nome.compareToIgnoreCase(outra.nome);
          if(comparaNome != 0) {
               return comparaNome;
          }
          return this.cpf.compareTo(outra.cpf);
     }
}