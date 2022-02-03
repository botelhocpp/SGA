package br.com.sga.financeiro;

import br.com.sga.datehelper.DateHelper;

public class Pagamento implements Comparable<Pagamento> {
  private DateHelper dataPagamento;

  public Pagamento(DateHelper dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public DateHelper getDataPagamento() {
    return this.dataPagamento;
  }

  public void setDataPagamento(DateHelper dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  @Override
  public int compareTo(Pagamento other) {
    return this.dataPagamento.comparar(other.getDataPagamento().getDate());
  }
}