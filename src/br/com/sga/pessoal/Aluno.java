package br.com.sga.pessoal;

import java.util.List;
import java.util.ArrayList;

import br.com.sga.identidade.Endereco;
import br.com.sga.financeiro.Pagamento;
import br.com.sga.datehelper.DateHelper;

public class Aluno extends Pessoa {
     private Integer matricula;
     private List<Pagamento> pagamentos;

     public Aluno() {
          this.pagamentos = new ArrayList<>();
     }

     public Aluno(String nome, String telefone, Sexo sexo, String cpf, DateHelper dataNascimento, String email, Endereco endereco, Integer matricula) {
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
     public String toString() {
          return String.format("Nome: %s%n" + 
          "Telefone: %s%n" + 
          "Sexo: %s%n" + 
          "CPF: %s%n" + 
          "Data de Nascimento: %s%n" +
          "Endereço de E-mail: %s%n" +  
          "Endereço: %s%n" +
          "Matrícula: %s%n", this.nome,
          this.telefone, this.sexo, this.cpf,
          this.dataNascimento.toString(), this.email, this.endereco, this.matricula);
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
