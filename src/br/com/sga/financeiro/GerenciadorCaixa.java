package br.com.sga.financeiro;

import br.com.sga.dateHelper.DateHelper;

public class GerenciadorCaixa {
     private DateHelper inicioCaixa;
     private DateHelper finalCaixa;
     private DateHelper dataAtual;

     public void atualizarCaixa() {

     }

     public boolean abrirCaixa() {

          return true;
     }

     public boolean fecharCaixa() {

          return true;
     }

     public DateHelper getInicioCaixa() {
          return this.inicioCaixa;
     }

     public DateHelper getFinalCaixa() {
          return this.finalCaixa;
     }

     public DateHelper getDataAtualCaixa() {
          return this.dataAtual;
     }

     @Override
     public String toString() {
          return "";
     }
}
