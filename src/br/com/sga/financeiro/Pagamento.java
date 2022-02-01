package br.com.sga.financeiro;

import br.com.sga.datehelper.DateHelper;

public class Pagamento implements Comparable<Pagamento> {
  private DateHelper dataPagamento;
  private String mesReferencia;

  public Pagamento(DateHelper dataPagamento, String mesReferencia) {
    this.dataPagamento = dataPagamento;
    this.mesReferencia = mesReferencia;
  }

  public DateHelper getDataPagamento() {
    return this.dataPagamento;
  }

  public String getMesReferencia() {
    return this.mesReferencia;
  }

  public void setDataPagamento(DateHelper dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public void setMesReferencia(String mesReferencia) {
    this.mesReferencia = mesReferencia;
  }

  @Override
  public int compareTo(Pagamento other) {
    return this.dataPagamento.comparar(other.getDataPagamento().getDate());
  }
}