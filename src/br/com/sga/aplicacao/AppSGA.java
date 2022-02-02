package br.com.sga.aplicacao;

import java.io.IOException;
import java.util.Scanner;

import br.com.sga.empresa.Usuario;
import br.com.sga.persistencia.GerenciadorEmpresa;
import br.com.sga.persistencia.GerenciadorUsuarios;

public class AppSGA {

     public static void main(String[] args) throws IOException {   
          /**
           * Configura os bancos de dados no diretório database.
           */
          inicializarBancos();

          /**
           * Cria o administrador do sistema caso não tenham usuários.
           */
          criarAdministrador();

          /**
           * Realizar login:
           */
          loginSistema();
          
          /**
           * Apresenta a tela de login ao usuário.
           */
          menuPrincipal();

          /**
           * Salvar os bancos.
           */
          salvarDados();
     }

     // ------------------------------------------------------------------------
     // Atributos Gerais da Aplicação
     // ------------------------------------------------------------------------

     private static GerenciadorUsuarios bancoUsuarios;
     private static GerenciadorEmpresa bancoEmpresa;
     private static Scanner leitor = new Scanner(System.in);
     private static Usuario usuarioAtual;
     private static int opcaoMenu;

     // ------------------------------------------------------------------------
     // Métodos do Back-end da Aplicação
     // ------------------------------------------------------------------------

     private static void inicializarBancos() {
          try{
               bancoUsuarios = new GerenciadorUsuarios("database/Usuarios.bin");
          }
          catch(IOException e) {
               System.out.println(e.getMessage());
          }
     }

     private static void salvarDados() {
          bancoUsuarios.salvarUsuarios();
     }

     private static void criarAdministrador() {
          if(bancoUsuarios.isVazio()) {
               System.out.println("Crie o usuário administrador do sistema.");
               while(true) {
                    try {
                         cabecalhoSGA();
                         System.out.print("Insira o login do usuário. Seu login deve ter no mínimo 5 caracteres:\n> ");
                         String login = leitor.next();
                         System.out.print("Insira a senha. Sua senha deve ter no mínimo 5 caracteres:\n> ");
                         String senha = leitor.next();
                         bancoUsuarios.criarUsuario(login, senha, true);
                         System.out.println("O usuário admnistrador do sistema foi criado! Pressione ENTER para continuar.");
                         esperarEnter();   
                         limparConsole(); 
                         break;
                    }
                    catch(Exception e) {
                         System.out.println(e.getMessage());
                    }
               }
          }
     }

     private static void loginSistema() {
          System.out.println("Faça login no sistema.");
          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o login do usuário:\n> ");
                    String login = leitor.next();
                    System.out.print("Insira a senha do usuário:\n> ");
                    String senha = leitor.next();
                    usuarioAtual = bancoUsuarios.obterUsuario(login, senha);
                    if (usuarioAtual == null) {
                         System.out.println("Dados inválidos! Pressione ENTER para tentar novamente.");
                         esperarEnter();
                    } else {
                         System.out.println("Seja bem vindo! Pressione ENTER para continuar.");
                         esperarEnter();
                         break;
                    }
               } catch (Exception e) {
                    System.out.println(e.getMessage());
               }
          }
     }

     // ------------------------------------------------------------------------
     // Métodos do Front-end da Aplicação
     // ------------------------------------------------------------------------

     private static void limparConsole() {
          System.out.print("\033[H\033[J");
          System.out.flush();
     }

     private static void cabecalhoSGA() {
          limparConsole();
          System.out.println("\t\033[1;94m------------------------------------------------------\033[0m\t");
          System.out.println("\t\033[1;93mSistema de Gerenciamento de Academias (SGA) versão 1.0\033[0m\t");
          System.out.println("\t\033[1;94m------------------------------------------------------\033[0m\t");
     }

     private static void menuPrincipal() throws IOException {
          cabecalhoSGA();

          System.out.print("1) Gerenciar Empresa\n" +
          "2) Gerenciar Clientes\n" +
          "3) Gerenciar Mensalidades\n" +
          "4) Sair\n> ");
          
          do {
               switch(opcaoMenu = leitor.nextInt()) {
                    case 1:
                         menuEmpresa();
                         break;
                    case 2:
                         menuClientes();
                         break;
                    case 3:
                         menuMensalidades();
                         break;
                    case 4:
                         System.out.println("Saindo do Sistema...");
                         break;
                    default:
                         System.out.println("Opção inválida! Tente novamente.");
               }

          } while(opcaoMenu != 4);
     }

     private static void menuEmpresa() throws IOException {
          if(!usuarioAtual.isAdministrador()) {
               System.out.println("Você não tem permissão para acessar o menu da empresa!\nPressione ENTER para continuar.");
               esperarEnter();
               return;
          }

          
          
     }

     private static void menuClientes() {
          
     }

     private static void menuMensalidades() {
          
     }

     private static void esperarEnter() throws IOException {
          System.in.read();    
     }

}