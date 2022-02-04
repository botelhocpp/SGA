package br.com.sga.financeiro;

import br.com.sga.datehelper.DateHelper;
import java.io.Serializable;

public class Pagamento implements Comparable<Pagamento>, Serializable {
  private DateHelper dataPagamento;
  private Double mensalidade;

  public Pagamento(DateHelper dataPagamento, Double mensalidade) {
    this.dataPagamento = dataPagamento;
    this.mensalidade = mensalidade;
  }

  public DateHelper getDataPagamento() {
    return this.dataPagamento;
  }

  public void setDataPagamento(DateHelper dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public Double getMensalidade() {
    return this.mensalidade;
  }

  @Override
  public String toString() {
    return "[" + this.mensalidade + ", " + this.dataPagamento.toString() + "]";
  }

  @Override
  public int compareTo(Pagamento other) {
    return this.dataPagamento.comparar(other.getDataPagamento().getDate());
  }
}