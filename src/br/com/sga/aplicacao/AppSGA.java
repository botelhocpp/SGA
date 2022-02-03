package br.com.sga.aplicacao;

import java.io.IOException;
import java.util.Scanner;

import br.com.sga.empresa.Usuario;
import br.com.sga.identidade.Estado;
import br.com.sga.persistencia.GerenciadorEmpresa;
import br.com.sga.persistencia.GerenciadorUsuarios;

public class AppSGA {

     public static void main(String[] args) throws IOException {  
          /**
           * Configura os bancos de dados no diretório database.
           */
          inicializarBancos();

          inicializarEmpresa();

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
               bancoEmpresa = new GerenciadorEmpresa("database/Empresa.bin");
          }
          catch(IOException e) {
               System.out.println(e.getMessage());
          }
     }

     private static void salvarDados() {
          bancoUsuarios.salvarUsuarios();
          bancoEmpresa.salvarEmpresa();
     }

     private static void inicializarEmpresa() throws IOException {
          if(bancoEmpresa.obterEmpresa() == null) {  
               cabecalhoSGA();
               System.out.println("Olá! Obrigado por adquirir o SGA!\n"
               + "Iremos agora realizar as configurações iniciais da sua empresa.\n"
               + "Pressione ENTER para configurarmos os dados da sua empresa.");

               esperarEnter();
               while(true) {
                    try {
                         cabecalhoSGA();
                         System.out.print("Primeiramente insira o nome da sua academia.\n> ");
                         String nome = leitor.nextLine();
                         System.out.print("Agora, insira o CNPJ da sua empresa, apenas os números:\n> ");
                         String cnpj = leitor.next();
                         System.out.print("Insira o e-mail da sua empresa:\n> ");
                         String email = leitor.next();
                         bancoEmpresa.configurarEmpresa(nome, cnpj, email);
                         
                         // Limpar o buffer
                         leitor.nextLine();

                         System.out.println("Vamos agora configurar o endereço da sua academia.");
                         System.out.print("Logradouro:\n> ");
                         String logradouro = leitor.nextLine();
                         System.out.print("Número (sem letras adjacentes):\n> ");
                         int numero = leitor.nextInt();

                         // Limpar o buffer
                         leitor.nextLine();

                         System.out.print("Bairro:\n> ");
                         String bairro = leitor.nextLine();
                         System.out.print("Cidade:\n> ");
                         String cidade = leitor.nextLine();
                         System.out.print("Estado (apenas a sigla):\n> ");
                         String estado = leitor.nextLine();
                         System.out.print("CEP:\n> ");
                         String cep = leitor.nextLine();
                         bancoEmpresa.configurarEndereco(logradouro, numero, bairro, cidade, Estado.valueOf(estado.toUpperCase()), cep);
                         
                         System.out.println("A empresa foi devidamente configurada! Pressione ENTER para continuar.");
                         esperarEnter();   
                         limparConsole(); 
                         break;
                    }
                    catch(Exception e) {
                         System.out.println(e.getMessage() + " Pressione ENTER para tentar de novo.");
                         esperarEnter();   
                         leitor.nextLine();
                    }
               }
          }
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
          do {
               cabecalhoSGA();
     
               System.out.print("1) Gerenciar Empresa\n" +
               "2) Gerenciar Usuários" +
               "3) Gerenciar Clientes\n" +
               "4) Gerenciar Mensalidades\n" +
               "5) Sair\n> ");

               switch(opcaoMenu = leitor.nextInt()) {
                    case 1:
                         menuEmpresa();
                         break;
                    case 2:
                         menuUsuario();
                         break;
                    case 3:
                         menuClientes();
                         break;
                    case 4:
                         menuMensalidades();
                         break;
                    case 5:
                         System.out.println("Saindo do Sistema...");
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte ENTER para tentar novamente.");
                         esperarEnter();
               }

          } while(opcaoMenu != 4);
     }

     private static void menuEmpresa() throws IOException {
          if(!usuarioAtual.isAdministrador()) {
               System.out.println("Você não tem permissão para acessar o menu da empresa!\nPressione ENTER para continuar.");
               esperarEnter();
               return;
          }
          
          do {
               cabecalhoSGA();
               System.out.print("1) Modificar o Nome da Empresa\n" +
               "2) Mudar o CNPJ da Empresa\n" +
               "3) Trocar o e-mail da Empresa\n" +
               "4) Modificar o endereço da Empresa\n" +
               "5) Retornar\n> ");

               switch(opcaoMenu = leitor.nextInt()) {
                    case 1:
                         // Modificar o Nome da Empresa
                         break;
                    case 2:
                         // Mudar o CNPJ da Empresa
                         break;
                    case 3:
                         // Trocar o e-mail da Empresa
                         break;
                    case 4:
                         // Modificar o endereço da Empresa
                         break;
                    case 5:
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte ENTER para tentar novamente.");
                         esperarEnter();
               }

          } while(opcaoMenu != 6); 
     }

     private static void menuUsuario() throws IOException {
          if(!usuarioAtual.isAdministrador()) {
               System.out.println("Você não tem permissão para acessar o menu de usuários!\nPressione ENTER para continuar.");
               esperarEnter();
               return;
          }
          
          do {
               cabecalhoSGA();
               System.out.print("1) Adicionar um Usuário\n" +
               "2) Listar os Usuários\n" +
               "3) Atualizar um Usuário\n" +
               "4) Remover um Usuário\n"+
               "5) Retornar\n> ");

               switch(opcaoMenu = leitor.nextInt()) {
                    case 1:
                         // Adicionar um Usuário 
                         break;
                    case 2:
                         // Listar os Usuários
                         break;
                    case 3:
                         // Atualizar um Usuário
                         break;
                    case 4:
                         // Remover um Usuário
                         break;
                    case 5:
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte ENTER para tentar novamente.");
                         esperarEnter();
               }

          } while(opcaoMenu != 5); 
          
     }

     private static void menuClientes() {
          
     }

     private static void menuMensalidades() {
          
     }

     private static void esperarEnter() throws IOException {
          System.in.read();    
     }

}