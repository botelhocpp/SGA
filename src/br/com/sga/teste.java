package br.com.sga;

import br.com.sga.identidade.ValidacaoDadosPessoais;

public class teste {
  public static void main(String args[]) {
    String teste = "12345664251";
    System.out.println(ValidacaoDadosPessoais.validarRegistroNacional(teste));
  }
}