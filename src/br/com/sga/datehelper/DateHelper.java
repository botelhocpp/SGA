package br.com.sga.datehelper;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateHelper {

     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

     private GregorianCalendar gregorianCalendar;
     private Date date;

     public DateHelper(Date date) {
          this.gregorianCalendar = new GregorianCalendar();
          this.gregorianCalendar.setTime(date);
          this.date = date;
     }

     public DateHelper(String date) {
          try {
               this.gregorianCalendar = new GregorianCalendar();
               this.gregorianCalendar.setTime(formatter.parse(date));
          } catch (ParseException e) {
               System.out.println(e.getMessage());
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
}