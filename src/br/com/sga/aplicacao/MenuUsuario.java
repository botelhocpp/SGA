package br.com.sga.aplicacao;

import static br.com.sga.aplicacao.AppSGA.*;

/**
 * <p>
 * Encapsula as operações relacionadas ao menu dos usuários.
 * </p>
 * 
 * @author Daniel Vitor (Aluno)
 * @author Pedro Botelho (Aluno)
 * @author Atílio G. Luiz (Orientador)
 * @since 05/02/2022
 */
public class MenuUsuario {
     
     /**
      * Mostra o menu para a gerencia de usuários,
      * para a realização do CRUD.
      */
     public static void menuUsuario() {
          if (!usuarioAtual.isAdministrador()) {
               cabecalhoSGA();
               System.out.println("Você não tem permissão para acessar o menu de usuários!\nPressione \033[1;32mENTER\033[0m para continuar.");
               esperarEnter();
               return;
          }

          int opcaoMenu;
          do {
               cabecalhoSGA();
               System.out.print("\033[1;94m1) \033[0;94mAdicionar um Usuário\033[0m\n" +
                         "\033[1;94m2) \033[0;94mListar os Usuários\033[0m\n" +
                         "\033[1;94m3) \033[0;94mAtualizar o Login de um Usuário\033[0m\n" +
                         "\033[1;94m4) \033[0;94mAtualizar a Senha de um Usuário\033[0m\n" +
                         "\033[1;94m5) \033[0;94mAtualizar a Permissão de um Usuário\033[0m\n" +
                         "\033[1;94m6) \033[0;94mRemover um Usuário\033[0m\n" +
                         "\033[1;94m7) \033[0;94mRetornar\033[0m\n\033[1;97m>\033[0m ");

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

     /**
      * Cria um usuário administrador! Utilizado no
      * primeiro start-up do sistema.
      */
     public static void criarAdministrador() {
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

     /**
      * Cria um usuário do sistema, pedindo
      * para informar LOGIN, SENHA e se
      * terá permissões de administrador.
      */
     public static void criarUsuario() {
          String login;
          String senha;
          String permissaoAdministrador;

          while(true) {
               cabecalhoSGA();
               System.out.print("Insira o login do usuário. Deve ter no mínimo 5 caracteres:\n> ");
               login = leitor.nextLine();
               if(login.isEmpty() || login.length() < 5) {
                    cabecalhoSGA();
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
                    cabecalhoSGA();
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
                    cabecalhoSGA();
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

     /**
      * Todos os usuários do sistema são
      * listados.
      */
     public static void listarUsuario() {
          cabecalhoSGA();
          System.out.println("\033[1;32mLista de Usuários\033[0m:\n");
          bancoUsuarios.listarUsuario();
          System.out.println("\nPressione \033[1;32mENTER\033[0m para continuar.");
          esperarEnter();
          limparConsole(); 
     }
     
     /**
      * Realiza a atualização do login de um usuário
      * de ID fornecido pelo usuário atual.
      */
     public static void atualizarLoginUsuario() {

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

     /**
      * Realiza a atualização da senha de um usuário
      * de ID fornecido pelo usuário atual.
      */
     public static void atualizarSenhaUsuario() {

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

     /**
      * Realiza a atualização da permissão de um usuário
      * de ID fornecido pelo usuário atual.
      */
     public static void atualizarPermissaoUsuario() {
          
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

     /**
      * Realiza a remoção do usuário requisitado,
      * ou seja, por meio do ID fornecido pelo
      * usuário atual.
      */
     public static void removerUsuario() {
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

}
