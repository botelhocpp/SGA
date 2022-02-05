package br.com.sga.aplicacao;

import java.io.IOException;
import java.util.Scanner;

import br.com.sga.empresa.Usuario;
import br.com.sga.persistencia.GerenciadorHistoricoCaixa;
import br.com.sga.persistencia.GerenciadorEmpresa;
import br.com.sga.persistencia.GerenciadorUsuarios;
import br.com.sga.persistencia.GerenciadorAlunos;
import br.com.sga.financeiro.Caixa;

import static br.com.sga.aplicacao.MenuUsuario.*;
import static br.com.sga.aplicacao.MenuEmpresa.*;
import static br.com.sga.aplicacao.MenuPagamento.*;
import static br.com.sga.aplicacao.MenuAluno.*;

public class AppSGA {

     public static void main(String[] args) throws IOException {

          inicializarBancos();

          if (bancoEmpresa.bancoVazio()) {
               cabecalhoSGA();
               System.out.println("Olá! Obrigado por adquirir o SGA!\n"
                         + "Iremos agora realizar as configurações iniciais da sua empresa.\n"
                         + "Pressione \033[1;32mENTER\033[0m para configurarmos os dados da sua empresa.");
               esperarEnter();
               inicializarEmpresa();
          }

          if (bancoUsuarios.isVazio()) {
               cabecalhoSGA();
               System.out.println("Agora vamos criar o usuário administrador do sistema." + 
               "\nPressione \033[1;32mENTER\033[0m para configurarmos o usuário.");
               esperarEnter();
               criarAdministrador();
          }

          loginSistema();

          menuPrincipal();

          salvarDados();

          System.out.println("Volte sempre!");
     }

     // ------------------------------------------------------------------------
     // Atributos Gerais da Aplicação
     // ------------------------------------------------------------------------

     public static Scanner leitor = new Scanner(System.in);
     public static GerenciadorHistoricoCaixa bancoHistoricoCaixa;
     public static GerenciadorUsuarios bancoUsuarios;
     public static GerenciadorEmpresa bancoEmpresa;
     public static GerenciadorAlunos bancoAlunos;
     public static Usuario usuarioAtual;
     public static Caixa caixa;

     // ------------------------------------------------------------------------
     // Métodos do Back-end da Aplicação
     // ------------------------------------------------------------------------

     public static void inicializarBancos() {
          try {
               bancoHistoricoCaixa = new GerenciadorHistoricoCaixa("database/HistoricoCaixa.bin");
               bancoUsuarios = new GerenciadorUsuarios("database/Usuarios.bin");
               bancoEmpresa = new GerenciadorEmpresa("database/Empresa.bin");
               bancoAlunos = new GerenciadorAlunos("database/Aluno.bin");

               caixa = new Caixa(bancoAlunos.listarAlunos(), bancoHistoricoCaixa.getHistoricoCaixa());
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }
     }

     public static void salvarDados() {
          bancoHistoricoCaixa.salvarDados();
          bancoUsuarios.salvarDados();
          bancoEmpresa.salvarDados();
          bancoAlunos.salvarDados();
     }

     public static void loginSistema() {
          System.out.println("Faça login no sistema.");
          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o login do usuário:\n> ");
                    String login = leitor.next();

                    limparBuffer();
                    cabecalhoSGA();
                    System.out.print("Insira a senha do usuário:\n> ");
                    String senha = leitor.next();
                    usuarioAtual = bancoUsuarios.obterUsuario(login, senha);
                    
                    limparBuffer();
                    cabecalhoSGA();
                    if (usuarioAtual == null) {
                         System.out.println("Dados inválidos! Pressione \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
                    }
                    else {
                         System.out.println("Seja bem vindo! Pressione \033[1;32mENTER\033[0m para continuar.");
                         esperarEnter();
                         break;
                    }
               }
               catch (Exception e) {
                    System.out.println(e.getMessage());
               }
          }
     }

     // ------------------------------------------------------------------------
     // Métodos do Front-end da Aplicação
     // ------------------------------------------------------------------------

     public static void limparConsole() {
          System.out.print("\033[H\033[J");
          System.out.flush();
     }

     public static void limparBuffer() {
          if(leitor.hasNextLine()) {
               leitor.nextLine();
          }
     }

     public static void cabecalhoSGA() {
          limparConsole();
          System.out.println("\t\033[1;94m------------------------------------------------------\033[0m\t");
          System.out.println("\t\033[1;93mSistema de Gerenciamento de Academias (SGA) versão 1.0\033[0m\t");
          System.out.println("\t\033[1;94m------------------------------------------------------\033[0m\t\n");
     }

     public static void esperarEnter() throws IOException {
          System.in.read();
     }

     public static void menuPrincipal() throws IOException {
          int opcaoMenu;
          do {
               cabecalhoSGA();

               System.out.print("\033[1;94m1)\033[0m \033[0;94mGerenciar Empresa\033[0m\n" +
                         "\033[1;94m2)\033[0m \033[0;94mGerenciar Usuários\033[0m\n" +
                         "\033[1;94m3)\033[0m \033[0;94mGerenciar Alunos\033[0m\n" +
                         "\033[1;94m4)\033[0m \033[0;94mGerenciar Pagamentos\033[0m\n" +
                         "\033[1;94m5)\033[0m \033[0;94mDados da Empresa\033[0m\n" +
                         "\033[1;94m6)\033[0m \033[0;94mSair do Sistema\033[0m\n\033[1;97m>\033[0m ");
                         
               opcaoMenu = leitor.nextInt();
               limparBuffer();           
               switch (opcaoMenu) {
                    case 1:
                         menuEmpresa();
                         break;
                    case 2:
                         menuUsuario();
                         break;
                    case 3:
                         menuAluno();
                         break;
                    case 4:
                         menuPagamentos();
                         break;
                    case 5:
                         dadosEmpresa();
                         break;
                    case 6:
                         cabecalhoSGA();
                         System.out.println("Finalizando o Sistema. Aguarde...");
                         caixa.fecharCaixa();
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }

          } while (opcaoMenu != 6);
     }
}

