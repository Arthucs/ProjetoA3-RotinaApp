package com.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciaRotina {
    private List<Tarefa> tarefas;
    private final String arquivo;

    public GerenciaRotina(String arquivo) {
        this.arquivo = arquivo;
        this.tarefas = carregarTarefas();
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        salvarTarefas();
    }

    public void construirTarefa(Scanner scanner) {
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

        Tarefa novaTarefa = new Tarefa(titulo, descricao, data, horario);
        adicionarTarefa(novaTarefa);
    }

    public void listarTarefas() {
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println(i + 1 + ". " + tarefas.get(i));
        }
    }

    public void concluirTarefa(int indice) {
        if (indice >= 0 && indice < tarefas.size()) {
            tarefas.get(indice).marcarComoConcluida();
            salvarTarefas();
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private void salvarTarefas() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(tarefas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Tarefa> carregarTarefas() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Tarefa>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }
}
