package br.com.sga.pessoal;

import java.util.List;
import java.util.ArrayList;

import br.com.sga.identidade.Endereco;
import br.com.sga.financeiro.Pagamento;

public class Aluno extends Pessoa {
     private String matricula;
     private List<Pagamento> pagamentos;

     public Aluno(String nome, String telefone, Sexo sexo, String cpf, String dataNascimento, String email, Endereco endereco) {
          super(nome, telefone, sexo, cpf, dataNascimento, email, endereco);
          pagamentos = new ArrayList<>();
     }

     public List<Pagamento> getPagamentos() {
          return this.pagamentos;
     }

     public void fazerPagamento(Pagamento pagamento) {
          pagamentos.add(pagamento);
     }

     public String getMatricula() {
          return this.matricula;
     }

     public void setMatricula(String matricula) {
          if (matricula == null || matricula.isEmpty()) {
               throw new IllegalArgumentException("Matrícula inválida!");
          } else {
               this.matricula = matricula;
          }
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
