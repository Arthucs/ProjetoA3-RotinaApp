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

    private String lerEntrada(Scanner scanner, String prompt) {
    System.out.print(prompt);
    String entrada = scanner.nextLine();
    if (entrada.equalsIgnoreCase("<")) {
        throw new CancelarOperacaoException();
    }
    return entrada;
    }


    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        salvarTarefas();
    }

    public void construirTarefa(Scanner scanner) {
        System.out.println("Digite '<' a qualquer momento para voltar ao menu.");

        try {
            String titulo = lerEntrada(scanner, "Título: ");
            String descricao = lerEntrada(scanner, "Descrição: ");

            LocalDate data = null;
            while (data == null) {
                String dataStr = lerEntrada(scanner, "Data (dd/MM/yyyy): ");
                try {
                    data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (data.isBefore(LocalDate.now())) {
                        System.out.println("A data não pode ser no passado. Tente novamente.");
                        data = null;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Data inválida! Tente novamente.");
                }
            }

            LocalTime horario = null;
            while (horario == null) {
                String horaStr = lerEntrada(scanner, "Horário (HH:mm): ");
                try {
                    horario = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
                    if (data.isEqual(LocalDate.now()) && horario.isBefore(LocalTime.now())) {
                        System.out.println("Horário não pode ser no passado para hoje. Tente novamente.");
                        horario = null;
                    }
                } catch (DateTimeParseException e) {
                System.out.println("Horário inválido! Tente novamente.");
                }
            }

            Tarefa novaTarefa = new Tarefa(titulo, descricao, data, horario);
            adicionarTarefa(novaTarefa);
            System.out.println("Tarefa adicionada com sucesso.");

        } catch (CancelarOperacaoException e) {
            System.out.println(e.getMessage());
        }
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
