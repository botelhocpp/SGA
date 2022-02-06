package br.com.sga.datehelper;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.io.Serializable;

/**
 * Realiza a interface de datas entre outras
 * classes de datas, facilitando a utilização.
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class DateHelper implements Comparable<DateHelper>, Serializable {

     private SimpleDateFormat formatter;
     private GregorianCalendar gregorianCalendar;
     private Date date;

     public DateHelper(Date date) {
          date = obterApenasData(date);
          this.formatter = new SimpleDateFormat("dd/MM/yyyy");
          this.gregorianCalendar = new GregorianCalendar();
          this.gregorianCalendar.setTime(date);
          this.date = date;
     }

     public DateHelper(String date) {
          this.formatter = new SimpleDateFormat("dd/MM/yyyy");
          try {
               this.gregorianCalendar = new GregorianCalendar();
               this.gregorianCalendar.setTime(formatter.parse(date));
               this.date = obterApenasData(this.gregorianCalendar.getTime());
          } catch (ParseException e) {
               System.out.println("Data informada inválida!");
          }
     }

     public void adicionarDias(int quantidade){
          adicionar(quantidade, Calendar.DAY_OF_MONTH);
     }

     public void adicionarMeses(int quantidade){
          adicionar(quantidade, Calendar.MONTH);
     }

     private void adicionar(int quantidade, int tipoCampo) {
          this.gregorianCalendar.add(tipoCampo, quantidade);
          this.date = gregorianCalendar.getTime();
     }

     private Date obterApenasData(Date aDate){       
          final Calendar myCalendar = Calendar.getInstance();
          myCalendar.setTime(aDate);
          myCalendar.set(Calendar.HOUR_OF_DAY, 0);
          myCalendar.set(Calendar.MINUTE, 0);
          myCalendar.set(Calendar.SECOND, 0);
          myCalendar.set(Calendar.MILLISECOND, 0);
          return myCalendar.getTime();
      }

     /**
      * Retorna o objeto Date
      * 
      * @param
      * @return Date
      */
     public Date getDate() {
          return this.date;
     }

     /**
      * Compara duas datas
      * Retorna 0 para datas iguais
      * Retorna negativo se o argumento vem depois
      * Retorna positivo se o argumento vem antes
      * Retorna nulo caso a data seja inválida
      * 
      * @param date
      * @return Integer
      */
     public Integer comparar(String date) {
          try {
               return comparar(formatter.parse(date));
          } catch (Exception e) {
               System.out.println(e.getMessage());
          }
          return null;
     }

     /**
      * Compara duas datas
      * Retorna 0 para datas iguais
      * Retorna negativo se o argumento vem depois
      * Retorna positivo se o argumento vem antes
      *
      * @param date
      * @return int
      */
     public int comparar(Date date) {
          GregorianCalendar anotherGregorianCalendar = new GregorianCalendar();
          anotherGregorianCalendar.setTime(date);
          return this.gregorianCalendar.compareTo(anotherGregorianCalendar);
     }

     /**
      * Retorna a data no formato padrão dd/MM/yyyy
      *
      * @param
      * @return String
      */
     public String toString() {
          return formatter.format(date);
     }

     @Override
     public int compareTo(DateHelper o) {
          return this.comparar(o.getDate());
     }

     @Override
     public int hashCode() {
          final int prime = 31;
          int result = 1;
          result = prime * result + ((date == null) ? 0 : date.hashCode());
          result = prime * result + ((formatter == null) ? 0 : formatter.hashCode());
          result = prime * result + ((gregorianCalendar == null) ? 0 : gregorianCalendar.hashCode());
          return result;
     }

     @Override
     public boolean equals(Object obj) {
          if (this == obj)
               return true;
          if (obj == null)
               return false;
          if (getClass() != obj.getClass())
               return false;
          DateHelper other = (DateHelper) obj;
          if (date == null) {
               if (other.date != null)
                    return false;
          } else if (!date.equals(other.date))
               return false;
          if (formatter == null) {
               if (other.formatter != null)
                    return false;
          } else if (!formatter.equals(other.formatter))
               return false;
          if (gregorianCalendar == null) {
               if (other.gregorianCalendar != null)
                    return false;
          } else if (!gregorianCalendar.equals(other.gregorianCalendar))
               return false;
          return true;
     }
}