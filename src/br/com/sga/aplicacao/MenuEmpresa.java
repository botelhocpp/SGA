package br.com.sga.aplicacao;

import static br.com.sga.aplicacao.AppSGA.*;

import java.io.IOException;

import br.com.sga.identidade.Endereco;
import br.com.sga.identidade.Estado;

public class MenuEmpresa {
     
     public static void menuEmpresa() throws IOException {
          if (!usuarioAtual.isAdministrador()) {
               cabecalhoSGA();
               System.out.println("Você não tem permissão para acessar o menu da empresa!\nPressione \033[1;32mENTER\033[0m para continuar.");
               esperarEnter();
               return;
          }
          int opcaoMenu;
          do {
               cabecalhoSGA();
               System.out.print("\033[1;94m1) \033[0;94mModificar o Nome da Empresa\033[0m\n" +
                         "\033[1;94m2) \033[0;94mMudar o CNPJ da Empresa\033[0m\n" +
                         "\033[1;94m3) \033[0;94mTrocar o e-mail da Empresa\033[0m\n" +
                         "\033[1;94m4) \033[0;94mModificar o endereço da Empresa\033[0m\n" +
                         "\033[1;94m5) \033[0;94mRetornar\033[0m\n\033[1;97m>\033[0m ");

               opcaoMenu = leitor.nextInt();
               limparBuffer();           
               switch (opcaoMenu) {
                    case 1:
                         configurarNomeEmpresa();
                         break;
                    case 2:
                         configurarCnpjEmpresa();
                         break;
                    case 3:
                         configurarEmailEmpresa();
                         break;
                    case 4:
                         configurarEnderecoEmpresa();
                         break;
                    case 5:
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }

          } while (opcaoMenu != 5);
     }

     public static void inicializarEmpresa() throws IOException {
          
          configurarNomeEmpresa();

          configurarCnpjEmpresa();

          configurarEmailEmpresa();

          configurarEnderecoEmpresa();

          cabecalhoSGA();
          System.out.println("A empresa foi devidamente configurada! Pressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole();
     }

     public static void configurarNomeEmpresa() throws IOException {
          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o novo nome da sua academia:\n> ");
                    bancoEmpresa.configurarNome(leitor.nextLine());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               }
          }
     }

     public static void configurarCnpjEmpresa() throws IOException {
          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o CNPJ da sua empresa, apenas os números:\n> ");
                    bancoEmpresa.configurarCnpj(leitor.next());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparBuffer();
                    limparConsole(); 
               }
          }
     }

     public static void configurarEmailEmpresa() throws IOException {
          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o e-mail da sua empresa:\n> ");
                    bancoEmpresa.configurarEmail(leitor.next());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparBuffer();
                    limparConsole(); 
               }
          }
     }

     public static void configurarEnderecoEmpresa() throws IOException {

          Endereco enderecoEmpresa = new Endereco();

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe qual o logradouro da sua empresa:\n> ");
                    enderecoEmpresa.setLogradouro(leitor.nextLine());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               }
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe o número (sem letras adjacentes):\n> ");
                    enderecoEmpresa.setNumero(leitor.nextInt());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparBuffer();
                    limparConsole(); 
               }
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe o Bairro:\n> ");
                    enderecoEmpresa.setBairro(leitor.nextLine());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               }
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe a Cidade:\n> ");
                    enderecoEmpresa.setCidade(leitor.nextLine());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               } 
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe o Estado (apenas a sigla):\n> ");
                    enderecoEmpresa.setEstado(Estado.valueOf(leitor.nextLine().toUpperCase()));
                    break;
               }
               catch (Exception e) {
                    System.out.println("Informe uma sigla de estado válida!\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparConsole(); 
               } 
          }

          while (true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Informe o CEP:\n> ");
                    enderecoEmpresa.setCep(leitor.next());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
               finally {
                    limparBuffer();
                    limparConsole(); 
               }
          }

          bancoEmpresa.configurarEndereco(enderecoEmpresa);
          limparConsole(); 
     }

     public static void dadosEmpresa() throws IOException {
          cabecalhoSGA();
          System.out.println("\033[1;32mInformações da Empresa\033[0m:\n");
          System.out.println(bancoEmpresa.obterEmpresa());
          System.out.println("\nPressione \033[1;32mENTER\033[0m para voltar ao menu.");
          esperarEnter();
          limparConsole(); 
     }

}
