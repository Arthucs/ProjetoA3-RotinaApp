package com.example;

import java.util.Scanner;

public class RotinaController {
    private final Scanner scanner;
    private final GerenciaRotina manager;

    public RotinaController(Scanner scanner, GerenciaRotina manager) {
        this.scanner = scanner;
        this.manager = manager;
    }

    public void executar() {
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
                    manager.construirTarefa(scanner);;
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