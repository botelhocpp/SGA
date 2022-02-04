package br.com.sga.pessoal;

import java.util.List;
import java.util.ArrayList;

import br.com.sga.identidade.Endereco;
import br.com.sga.financeiro.Pagamento;

public class Aluno extends Pessoa {
     private Integer matricula;
     private List<Pagamento> pagamentos;

     public Aluno() {
          this.pagamentos = new ArrayList<>();
     }

     public Aluno(String nome, String telefone, Sexo sexo, String cpf, String dataNascimento, String email, Endereco endereco, Integer matricula) {
          super(nome, telefone, sexo, cpf, dataNascimento, email, endereco);
          this.pagamentos = new ArrayList<>();
          this.matricula = matricula;
     }

     public void adicionarPagamento(Pagamento pagamento) {
          pagamentos.add(pagamento);
     }

     public List<Pagamento> getPagamentos() {
          return this.pagamentos;
     }

     public Integer getMatricula() {
          return this.matricula;
     }

     public void setMatricula(Integer matricula) {
          this.matricula = matricula;
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
