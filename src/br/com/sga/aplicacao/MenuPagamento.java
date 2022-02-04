package br.com.sga.aplicacao;

import static br.com.sga.aplicacao.AppSGA.bancoAlunos;
import static br.com.sga.aplicacao.AppSGA.cabecalhoSGA;
import static br.com.sga.aplicacao.AppSGA.caixa;
import static br.com.sga.aplicacao.AppSGA.esperarEnter;
import static br.com.sga.aplicacao.AppSGA.leitor;
import static br.com.sga.aplicacao.AppSGA.limparBuffer;
import static br.com.sga.aplicacao.AppSGA.limparConsole;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import br.com.sga.datehelper.DateHelper;
import br.com.sga.financeiro.Pagamento;
import br.com.sga.pessoal.Aluno;

public class MenuPagamento {
     
     public static void menuPagamentos() throws IOException {
          int opcaoMenu = 0;
          do {
               cabecalhoSGA();
               System.out.print("\033[1;94m1) \033[0;94mAbrir caixa do dia\033[0m\n" + 
                              "\033[1;94m2) \033[0;94mFechar caixa do dia\033[0m\n" +
                              "\033[1;94m3) \033[0;94mFazer pagamento da mensalidade do Aluno\033[0m\n" +
                              "\033[1;94m4) \033[0;94mListar alunos com mensalidade em dia\033[0m\n" + 
                              "\033[1;94m5) \033[0;94mListar alunos com mensalidade atrasada\033[0m\n" + 
                              "\033[1;94m6) \033[0;94mBuscar caixa por data\033[0m\n" + 
                              "\033[1;94m7) \033[0;94mRetornar\n\033[1;97m>\033[0m ");

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
                              
          } while (opcaoMenu != 7);
     }

     public static void abrirCaixaDoDia() throws IOException {
          cabecalhoSGA();
          if (caixa.abrirCaixa()) {
               System.out.println("Caixa aberto com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("Caixa já está aberto!\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          esperarEnter();
          limparConsole(); 
     }

     public static void fecharCaixaDoDia() throws IOException {
          cabecalhoSGA();
          if (caixa.fecharCaixa()) {
               System.out.println("Caixa fechado com sucesso!\nPressione \033[1;32mENTER\033[0m para voltar.");
          } else {
               System.out.println("Caixa já está fechado!\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          esperarEnter();
          limparConsole(); 
     }

     public static void fazerPagamentoAluno() throws IOException {
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
     
     public static void listarAlunosMensalidadeEmDia() throws IOException {
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

     public static void listarAlunosMensalidadeAtrasada() throws IOException {
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

     public static void buscarSaldoDiarioPorData() throws IOException {
          DateHelper data;
          while(true) {
               try {
                    cabecalhoSGA();
                    System.out.print("Insira uma data no seguinte formato (dd/MM/aaaa):\n> ");
                    data = new DateHelper(leitor.nextLine());
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
          DecimalFormat decimalFormat = new DecimalFormat("##.##");
          Double saldoDiario = caixa.buscarCaixaPorData(data);

          caixa.ver();

          if(saldoDiario == null) {
               System.out.println("Saldo inexistente para a data informada.\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          else {
               System.out.println("Saldo diário: R$" + decimalFormat.format(saldoDiario) + ".\nPressione \033[1;32mENTER\033[0m para voltar.");
          }
          esperarEnter();
          limparConsole();
     }
}
