package br.com.sga.identidade;

/**
 * Contém todas as siglas dos estados brasileiros, incluso
 * o Distrito Federal.
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public enum Estado {
     AC("AC"),
     AL("AL"),
     AP("AP"),
     AM("AM"),
     BA("BA"),
     CE("CE"),
     DF("DF"),
     ES("ES"),
     GO("GO"),
     MA("MA"),
     MT("MT"),
     MS("MS"),
     MG("MG"),
     PA("PA"),
     PB("PB"),
     PR("PR"),
     PE("PE"),
     PI("PI"),
     RJ("RJ"),
     RN("RN"),
     RS("RS"),
     RO("RO"),
     RR("RR"),
     SC("SC"),
     SP("SP"),
     SE("SE"),
     TO("TO");

     private String uf;

     private Estado(String uf) {
          this.uf = uf;
     }

     public String toString() {
          return uf;
     }
}