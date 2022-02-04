package br.com.sga.aplicacao;

import java.io.IOException;
import java.util.Scanner;

import java.util.List;
import java.util.Map;
import java.util.Date;

import java.text.DecimalFormat;

import br.com.sga.datehelper.DateHelper;
import br.com.sga.empresa.Usuario;
import br.com.sga.identidade.Endereco;
import br.com.sga.identidade.Estado;
import br.com.sga.identidade.ValidacaoDadosPessoais;
import br.com.sga.persistencia.GerenciadorHistoricoCaixa;
import br.com.sga.persistencia.GerenciadorEmpresa;
import br.com.sga.persistencia.GerenciadorUsuarios;
import br.com.sga.persistencia.GerenciadorAlunos;
import br.com.sga.financeiro.Caixa;
import br.com.sga.financeiro.Pagamento;
import br.com.sga.pessoal.Aluno;
import br.com.sga.pessoal.Sexo;

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

     private static GerenciadorHistoricoCaixa bancoHistoricoCaixa;
     private static GerenciadorUsuarios bancoUsuarios;
     private static GerenciadorEmpresa bancoEmpresa;
     private static GerenciadorAlunos bancoAlunos;
     private static Scanner leitor = new Scanner(System.in);
     private static Usuario usuarioAtual;
     private static Caixa caixa;

     // ------------------------------------------------------------------------
     // Métodos do Back-end da Aplicação
     // ------------------------------------------------------------------------

     private static void inicializarBancos() {
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

     private static void salvarDados() {
          bancoHistoricoCaixa.salvarDados();
          bancoUsuarios.salvarDados();
          bancoEmpresa.salvarDados();
          bancoAlunos.salvarDados();
          System.out.println("Banco de dados devidamente salvo!");
     }

     private static void loginSistema() {
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

     private static void limparConsole() {
          System.out.print("\033[H\033[J");
          System.out.flush();
     }

     private static void limparBuffer() {
          if(leitor.hasNextLine()) {
               leitor.nextLine();
          }
     }

     private static void cabecalhoSGA() {
          limparConsole();
          System.out.println("\t\033[1;94m------------------------------------------------------\033[0m\t");
          System.out.println("\t\033[1;93mSistema de Gerenciamento de Academias (SGA) versão 1.0\033[0m\t");
          System.out.println("\t\033[1;94m------------------------------------------------------\033[0m\t\n");
     }

     private static void esperarEnter() throws IOException {
          System.in.read();
     }

     private static void menuPrincipal() throws IOException {
          int opcaoMenu;
          do {
               cabecalhoSGA();

               System.out.print("\033[1;94m1)\033[0m \033[0;94mGerenciar Empresa\033[0m\n" +
                         "\033[1;94m2)\033[0m \033[0;94mGerenciar Usuários\033[0m\n" +
                         "\033[1;94m3)\033[0m \033[0;94mGerenciar Alunos\033[0m\n" +
                         "\033[1;94m4)\033[0m \033[0;94mGerenciar Pagamentos\033[0m\n" +
                         "\033[1;94m5)\033[0m \033[0;94mDados da Empresa\033[0m\n" +
                         "\033[1;94m6)\033[0m \033[0;94mSair\033[0m\n\033[1;97m>\033[0m ");
                         
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
                         limparConsole();
                         System.out.println("Finalizando o Sistema. Aguarde...");
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }

          } while (opcaoMenu != 6);
     }

     private static void menuEmpresa() throws IOException {
          if (!usuarioAtual.isAdministrador()) {
               System.out.println("Você não tem permissão para acessar o menu da empresa!\nPressione \033[1;32mENTER\033[0m para continuar.");
               esperarEnter();
               return;
          }
          int opcaoMenu;

          do {
               cabecalhoSGA();
               System.out.print("1) Modificar o Nome da Empresa\n" +
                         "2) Mudar o CNPJ da Empresa\n" +
                         "3) Trocar o e-mail da Empresa\n" +
                         "4) Modificar o endereço da Empresa\n" +
                         "5) Retornar\n> ");

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

     private static void menuUsuario() throws IOException {
          if (!usuarioAtual.isAdministrador()) {
               System.out.println("Você não tem permissão para acessar o menu de usuários!\nPressione \033[1;32mENTER\033[0m para continuar.");
               esperarEnter();
               return;
          }
          int opcaoMenu;
          do {
               cabecalhoSGA();
               System.out.print("1) Adicionar um Usuário\n" +
                         "2) Listar os Usuários\n" +
                         "3) Atualizar o Login de um Usuário\n" +
                         "4) Atualizar a Senha de um Usuário\n" +
                         "5) Atualizar a Permissão de um Usuário\n" +
                         "6) Remover um Usuário\n" +
                         "7) Retornar\n> ");

               opcaoMenu = leitor.nextInt();
               limparBuffer();
               switch (opcaoMenu) {
                    case 1:
                         criarUsuario();
                         break;
                    case 2:
                         listarUsuario();
                         break;
                    case 3:
                         atualizarLoginUsuario();
                         break;
                    case 4:
                         atualizarSenhaUsuario();
                         break;
                    case 5:
                         atualizarPermissaoUsuario();
                         break;
                    case 6:
                         removerUsuario();
                         break;
                    case 7:
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }

          } while (opcaoMenu != 7);

     }

     private static void menuAluno() throws IOException {
          int opcaoMenu;
          do {
               cabecalhoSGA();
               System.out.print("1) Adicionar um Aluno\n" +
                         "2) Listar os Alunos\n" +
                         "3) Atualizar um Aluno\n" +
                         "4) Remover um Aluno\n" +
                         "5) Retornar\n> ");

               opcaoMenu = leitor.nextInt();
               limparBuffer();
               switch (opcaoMenu) {
                    case 1:
                         adicionarAluno();
                         break;
                    case 2:
                         listarAlunos();
                         break;
                    case 3:
                         atualizarAluno();
                         break;
                    case 4:
                         removerAluno();
                         break;
                    case 5:
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }

          } while (opcaoMenu != 5);
     }

     private static void menuPagamentos() throws IOException {
          int opcaoMenu = 0;
          do {
               cabecalhoSGA();
               System.out.println("1) Abrir caixa do dia" + 
                              "2) Fechar caixa do dia" +
                              "3) Fazer pagamento da mensalidade do Aluno\n" +
                              "4) Listar alunos com mensalidade em dia\n" + 
                              "5) Listar alunos com mensalidade atrasada\n" + 
                              "6) Buscar caixa por data\n" + 
                              "7) Retornar\n>");

               opcaoMenu = leitor.nextInt();
               limparBuffer();           
               switch (opcaoMenu) {
                    case 1:
                         abrirCaixaDoDia();
                         break;
                    case 2:
                         fecharCaixaDoDia();
                         break;
                    case 3:
                         fazerPagamentoAluno();
                         break;
                    case 4:
                         listarAlunosMensalidadeEmDia();
                         break;
                    case 5:
                         listarAlunosMensalidadeAtrasada();
                         break;
                    case 6:
                         buscarSaldoDiarioPorData();
                         break;
                    case 7:
                         break;
                    default:
                         System.out.println("Opção inválida! Aperte \033[1;32mENTER\033[0m para tentar novamente.");
                         esperarEnter();
               }
                              
          } while (opcaoMenu != 5);
     }

     // ------------------------------------------------------------------------
     // Métodos de Configuração dos Usuários
     // ------------------------------------------------------------------------

     private static void criarAdministrador() throws IOException {
          String login;
          String senha;
          while(true) {
               cabecalhoSGA();
               System.out.print("Insira o login do usuário. Deve ter no mínimo 5 caracteres:\n> ");
               login = leitor.nextLine();
               if(login.isEmpty() || login.length() < 5) {
                    System.out.println("Login inválido! O login do usuário deve ter no mínimo 5 caracteres!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparConsole();
               break;
          }
          while(true) {
               cabecalhoSGA();
               System.out.print("Insira a senha. Deve ter no mínimo 5 caracteres:\n> ");
               senha = leitor.nextLine();
               if(senha.isEmpty() || senha.length() < 5) {
                    System.out.println("Senha inválida! A senha do usuário deve ter no mínimo 5 caracteres!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparConsole();
               break;
          }
          cabecalhoSGA();
          bancoUsuarios.criarUsuario(login, senha, true);
          System.out.println("O usuário admnistrador do sistema foi criado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole(); 
     }

     private static void criarUsuario() throws IOException {
          String login;
          String senha;
          String permissaoAdministrador;

          while(true) {
               cabecalhoSGA();
               System.out.print("Insira o login do usuário. Deve ter no mínimo 5 caracteres:\n> ");
               login = leitor.nextLine();
               if(login.isEmpty() || login.length() < 5) {
                    System.out.println("Login inválido! O login do usuário deve ter no mínimo 5 caracteres!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparConsole();
               break;
          }

          while(true) {
               cabecalhoSGA();
               System.out.print("Insira a senha. Deve ter no mínimo 5 caracteres:\n> ");
               senha = leitor.nextLine();
               if(senha.isEmpty() || senha.length() < 5) {
                    System.out.println("Senha inválida! A senha do usuário deve ter no mínimo 5 caracteres!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparConsole();
               break;
          }

          while(true) {
               cabecalhoSGA();
               System.out.print("Esse usuário terá permissões de administrador? (S/N)\n> ");
               permissaoAdministrador = leitor.next().toUpperCase();
               if(permissaoAdministrador.isEmpty() || (!permissaoAdministrador.equals("S") && !permissaoAdministrador.equals("N"))) {
                    System.out.println("Escolha inválida!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    limparBuffer();
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparBuffer();
               limparConsole();
               break;
          }

          cabecalhoSGA();
          bancoUsuarios.criarUsuario(login, senha, (permissaoAdministrador.equals("S")) ? true : false);
          System.out.println("O usuário foi criado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole(); 
     }

     private static void listarUsuario() throws IOException {
          cabecalhoSGA();
          System.out.println("\033[1;32mLista de Usuários\033[0m:\n");
          bancoUsuarios.listarUsuario();
          System.out.println("\nPressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole(); 
     }
     
     private static void atualizarLoginUsuario() throws IOException {

          int id;
          String login;
          
          while(true) {
               cabecalhoSGA();
               System.out.print("Primeiramente informe o ID do usuário a ser atualizado:\n> ");
               id = leitor.nextInt();
               if(id < 1) {
                    System.out.println("ID inválido! O ID do usuário deve ter ser no mínimo 1!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    limparBuffer();
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparBuffer();
               limparConsole(); 
               break;
          }


          while(true) {
               cabecalhoSGA();
               System.out.print("Insira o novo login do usuário. Deve ter no mínimo 5 caracteres:\n> ");
               login = leitor.nextLine();
               if(login.isEmpty() || login.length() < 5) {
                    System.out.println("Login inválido! O login do usuário deve ter no mínimo 5 caracteres!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparConsole(); 
               break;
          }
          
          cabecalhoSGA();
          if(bancoUsuarios.atualizarLogin(id, login)) {
               System.out.println("O usuário foi atualizado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          }
          else {
               System.out.println("Não existe um usuário com o ID informado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          }
          esperarEnter();
          limparConsole(); 
     }

     private static void atualizarSenhaUsuario() throws IOException {

          int id;
          String senha;
          
          while(true) {
               cabecalhoSGA();
               System.out.print("Primeiramente informe o ID do usuário a ser atualizado:\n> ");
               id = leitor.nextInt();
               if(id < 1) {
                    System.out.println("ID inválido! O ID do usuário deve ter ser no mínimo 1!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    limparBuffer();
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparBuffer();
               limparConsole(); 
               break;
          }


          while(true) {
               cabecalhoSGA();
               System.out.print("Insira a nova senha do usuário. Deve ter no mínimo 5 caracteres:\n> ");
               senha = leitor.nextLine();
               if(senha.isEmpty() || senha.length() < 5) {
                    System.out.println("Senha inválida! A senha do usuário deve ter no mínimo 5 caracteres!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparConsole(); 
               break;
          }
          
          cabecalhoSGA();
          if(bancoUsuarios.atualizarSenha(id, senha)) {
               System.out.println("O usuário foi atualizado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          }
          else {
               System.out.println("Não existe um usuário com o ID informado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          }
          esperarEnter();
          limparConsole(); 
     }

     private static void atualizarPermissaoUsuario() throws IOException {
          
          int id;
          String permissaoAdministrador;
          
          while(true) {
               cabecalhoSGA();
               System.out.print("Primeiramente informe o ID do usuário a ser atualizado:\n> ");
               id = leitor.nextInt();
               if(id < 1) {
                    System.out.println("ID inválido! O ID do usuário deve ter ser no mínimo 1!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparBuffer();
                    limparConsole();
                    continue;
               }
               limparBuffer();
               limparConsole(); 
               break;
          }

          while(true) {
               cabecalhoSGA();
               System.out.print("Esse usuário deverá ter permissões de administrador? (S/N)\n> ");
               permissaoAdministrador = leitor.next().toUpperCase();
               if(permissaoAdministrador.isEmpty() || (!permissaoAdministrador.equals("S") && !permissaoAdministrador.equals("N"))) {
                    System.out.println("Escolha inválida!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparBuffer();
                    limparConsole();
                    continue;
               }
               limparBuffer();
               limparConsole();
               break;
          }
          
          cabecalhoSGA();
          if(bancoUsuarios.atualizarPermissao(id, (permissaoAdministrador.equals("S")) ? true : false)) {
               System.out.println("O usuário foi atualizado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          }
          else {
               System.out.println("Não existe um usuário com o ID informado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          }
          esperarEnter();
          limparConsole(); 
     }

     private static void removerUsuario() throws IOException {
          int id;
          
          while(true) {
               cabecalhoSGA();
               System.out.print("Primeiramente informe o ID do usuário a ser removido:\n> ");
               id = leitor.nextInt();
               if(id < 1) {
                    System.out.println("ID inválido! O ID do usuário deve ter ser no mínimo 1!" +
                    "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                    limparBuffer();
                    esperarEnter();
                    limparConsole();
                    continue;
               }
               limparBuffer();
               limparConsole(); 
               break;
          }

          cabecalhoSGA();
          if(bancoUsuarios.removerUsuario(id)) {
               System.out.println("O usuário foi deletado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          }
          else {
               System.out.println("Não existe um usuário com o ID informado!\nPressione \033[1;32mENTER\033[0m para continuar.");
          }
          esperarEnter();
          limparConsole(); 
     }

     // ------------------------------------------------------------------------
     // Métodos de Configuração da Empresa
     // ------------------------------------------------------------------------

     private static void inicializarEmpresa() throws IOException {
          
          configurarNomeEmpresa();

          configurarCnpjEmpresa();

          configurarEmailEmpresa();

          configurarEnderecoEmpresa();

          cabecalhoSGA();
          System.out.println("A empresa foi devidamente configurada! Pressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole();
     }

     private static void configurarNomeEmpresa() throws IOException {
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

     private static void configurarCnpjEmpresa() throws IOException {
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

     private static void configurarEmailEmpresa() throws IOException {
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

     private static void configurarEnderecoEmpresa() throws IOException {

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

     private static void dadosEmpresa() throws IOException {
          cabecalhoSGA();
          System.out.println("\033[1;32mInformações da Empresa\033[0m:\n");
          System.out.println(bancoEmpresa.obterEmpresa());
          System.out.println("\nPressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole(); 
     }

     // ------------------------------------------------------------------------
     // Métodos de Configuração dos Alunos
     // ------------------------------------------------------------------------]

     private static void adicionarAluno() throws IOException {
          Aluno aluno = new Aluno();

          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o nome do aluno:\n> ");
                    aluno.setNome(leitor.nextLine());
                    if(aluno.getNome().isEmpty()) {
                         System.out.println("Nome inválido! O nome do aluno deve ter no mínimo 1 caractere!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }  

          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o telefone do aluno:\n> ");
                    aluno.setTelefone(leitor.nextLine());
                    if(ValidacaoDadosPessoais.validarTelefone(aluno.getTelefone())) {
                         System.out.println("Telefone inválido!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }  

          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o sexo do aluno (1 - Feminino, 2 - Masculino, 3 - Outro):\n> ");
                    int numeroSexo = leitor.nextInt();
                    if(numeroSexo != 1 && numeroSexo != 2 && numeroSexo != 3) {
                         System.out.println("Dígito inválido! Deve digitar 1, 2 ou 3!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    switch (numeroSexo) {
                         case 1:
                              aluno.setSexo(Sexo.MASCULINO);
                              break;
                         case 2;
                              aluno.setSexo(Sexo.FEMININO);
                              break;
                         case 3:
                              aluno.setSexo(Sexo.OUTRO);
                              break;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          } 


          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o cpf do aluno:\n> ");
                    aluno.setTelefone(leitor.nextLine());
                    if(ValidacaoDadosPessoais.validarTelefone(aluno.getTelefone())) {
                         System.out.println("Telefone inválido!" +
                         "\nPressione \033[1;32mENTER\033[0m para tentar de novo.");
                         esperarEnter();
                         limparConsole();
                         continue;
                    }
                    limparConsole();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }

          cabecalhoSGA();
          System.out.println("O aluno foi criado com sucesso!\nPressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole(); 
     }

     private static void listarAlunos() throws IOException {
          List<Aluno> alunosListar = bancoAlunos.listarAlunos();
          System.out.println("Alunos:");
          for (Aluno aluno : alunosListar) {
               System.out.println("- Matrícula: " + aluno.getMatricula() + ", Nome: " + aluno.getNome());
          }
          limparConsole();
          cabecalhoSGA();
          System.out.println("Pressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole(); 
     }

     private static void atualizarAluno() throws IOException {

     }

     private static void removerAluno() throws IOException {
          int matricula = 0;

          while(true) {
               try {
                    System.out.print("Insira o número da matrícula do Aluno:\n> ");
                    matricula = leitor.nextInt();
                    Aluno aluno = bancoAlunos.obterAluno(matricula);
                    if (aluno == null) {
                         throw new Exception("Aluno não existente");
                    }
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
               }
          }     

          limparConsole();
          cabecalhoSGA();
          if (bancoAlunos.removerAluno(matricula)) {
               System.out.println("Aluno removido com sucesso! Pressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("O aluno não foi removido, rente novamente! Pressione \033[1;32mENTER\033[0m para voltar.");
          }

          esperarEnter();
          limparConsole(); 
     }

     // ------------------------------------------------------------------------
     // Métodos do Menu de Pagamentos
     // ------------------------------------------------------------------------

     private static void abrirCaixaDoDia() throws IOException {
          cabecalhoSGA();
          if (caixa.abrirCaixa()) {
               System.out.println("Caixa aberto com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("Caixa já está aberto!\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          System.out.println("Caixa aberto com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole(); 
     }

     private static void fecharCaixaDoDia() throws IOException {
          cabecalhoSGA();
          if (caixa.fecharCaixa()) {
               System.out.println("Caixa fechado com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("Caixa já está fechado!\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          System.out.println("Caixa fechado com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole(); 
     }

     private static void fazerPagamentoAluno() throws IOException {
          if (!caixa.obterEstadoCaixa()) {
               cabecalhoSGA();
               System.out.println("Caixa está fechado!\nPressione \033[1;32mENTER\033[0m para voltar.");
               esperarEnter();
               limparConsole(); 
               return;
          }

          int matricula = 0;
          Double mensalidade;
          DateHelper dataAtual = new DateHelper(new Date());

          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira o número da matrícula do Aluno:\n> ");
                    matricula = leitor.nextInt();
                    Aluno aluno = bancoAlunos.obterAluno(matricula);
                    if (aluno == null) {
                         throw new Exception("Aluno não existente");
                    }
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }

          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Digite o valor da mensalidade do Aluno:\n> ");
                    mensalidade = leitor.nextDouble();
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }
          
          cabecalhoSGA();

          Pagamento pagamento = new Pagamento(dataAtual, mensalidade);

          if (caixa.fazerPagamento(matricula, pagamento)) {
               System.out.println("Pagamento feito com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("Falha ao fazer pagamento! Motivo: Caixa fechado, espere o próximo dia\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          
          esperarEnter();
          limparConsole(); 
     }
     
     private static void listarAlunosMensalidadeEmDia() throws IOException {
          cabecalhoSGA();
          List<Aluno> alunosMensalidadeEmDia = caixa.listarAlunosMensalidadeEmDia();
          System.out.println("Alunos com mensalidade em dia:");
          for (Aluno aluno : alunosMensalidadeEmDia) {
               System.out.println("- " + aluno.getNome());
          }
          System.out.println("Pressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole(); 
     }

     private static void listarAlunosMensalidadeAtrasada() throws IOException {
          cabecalhoSGA();
          List<Aluno> alunosMensalidadeAtrasada = caixa.listarAlunosMensalidadeAtrasada();
          System.out.println("Alunos com mensalidade atrasada:");
          for (Aluno aluno : alunosMensalidadeAtrasada) {
               System.out.println("- " + aluno.getNome());
          }
          System.out.println("Pressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole(); 
     }

     private static void buscarSaldoDiarioPorData() throws IOException {
          DateHelper data;
          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira uma data no seguinte formato (dd/MM/aaaa):\n> ");
                    data = new DateHelper(leitor.nextLine());
                    break;
               }
               catch (Exception e) {
                    System.out.println(e.getMessage() + " Pressione \033[1;32mENTER\033[0m para tentar de novo.");
                    esperarEnter();
                    limparConsole();
               }
          }

          limparConsole();
          cabecalhoSGA();
          DecimalFormat decimalFormat = new DecimalFormat("##.##");
          Double saldoDiario = caixa.buscarCaixaPorData(data);
          System.out.println("Saldo diário:" + decimalFormat.format(saldoDiario) + "!\nPressione \033[1;32mENTER\033[0m para voltar.");
          esperarEnter();
          limparConsole();
     }
}

