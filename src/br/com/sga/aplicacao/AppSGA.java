package br.com.sga.aplicacao;

import java.util.Scanner;

import br.com.sga.empresa.Usuario;

public class AppSGA {

     public static Usuario usuario;
     public static Scanner leitor = new Scanner(System.in);

     public static void main(String[] args) {

          while(true) {
               header();
               if(usuario == null) {
                    criarUsuario();
               }
               else {
                    loginUsuario();
               }
               clear();
          }

     }

     public static void header() {
          System.out.println("----------------------------------------------------------------");
          System.out.println("------- \t Sistema de Controle de Academias v1.0 \t -------");
          System.out.println("----------------------------------------------------------------");
     }

     public static void clear() {
          System.out.print("\033[H\033[J");
          System.out.flush();
     }

     public static void loginUsuario() {
          while(true) {
               System.out.print("Insira o login: ");
               String login = leitor.next();
               System.out.print("Insira a senha: ");
               String senha = leitor.next();

               if(usuario.equals(new Usuario(login, senha))) {
                    System.out.println("Logado com sucesso!");
                    break;
               }
               else {
                    System.out.println("Dados inválidos. Tente novamente!");
               }
          }
     }

     public static void criarUsuario() {
          System.out.print("Insira o novo login: ");
          String login = leitor.next();
          System.out.print("Insira a nova senha: ");
          String senha = leitor.next();
          usuario = new Usuario(login, senha);
     }
}
