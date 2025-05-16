package com.example;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

                    LocalDate data = null;
                    while (data == null) {
                        System.out.print("Data (dd/mm/aaaa): ");
                        String dataString = scanner.nextLine();
                        try {
                            data = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            if (data.isBefore(LocalDate.now())) {
                                System.out.println("A data não pode ser no passado. Tente novamente.");
                                data = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Data Inválida! Tente novamente.");
                        }
                    }

                    LocalTime horario = null;
                    while (horario == null) {
                        System.out.print("Horario (HH:mm): ");
                        String horaString = scanner.nextLine();
                        try {
                            horario = LocalTime.parse(horaString, DateTimeFormatter.ofPattern("HH:mm"));
                            if (data.isEqual(LocalDate.now()) && horario.isBefore(LocalTime.now())) {
                                System.out.println("Horário não pode ser no passado para a data de hoje. Tente novamente.");
                                horario = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Horário Inválido! Tente novamente.");
                        }
                    }
                    
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
