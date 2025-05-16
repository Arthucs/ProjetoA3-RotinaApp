package com.example;
import java.util.Scanner;

public class RotinaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciaRotina manager = new GerenciaRotina("rotina.txt");

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Listar Tarefas");
            System.out.println("3. Concluir Tarefa");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Data (dd/mm/aaaa): ");
                    String data = scanner.nextLine();
                    System.out.print("Horário (hh:mm): ");
                    String horario = scanner.nextLine();
                    manager.adicionarTarefa(new Tarefa(titulo, descricao, data, horario));
                    break;
                case 2:
                    manager.listarTarefas();
                    break;
                case 3:
                    manager.listarTarefas();
                    System.out.print("Número da tarefa para concluir: ");
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    manager.concluirTarefa(index);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}