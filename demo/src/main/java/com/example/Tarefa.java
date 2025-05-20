package com.example;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Tarefa implements Serializable {
    private String titulo;
    private String descricao;
    private LocalDate data;
    private LocalTime horario;
    private boolean concluida;
    private boolean notificada;

    public Tarefa(String titulo, String descricao, LocalDate data, LocalTime horario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.horario = horario;
        this.concluida = false;
        this.notificada = false;
    }

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    public void desmarcarComoConcluida() {
        this.concluida = false;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public boolean foiNotificada() {
        return notificada;
    }

    public void setNotificada(boolean notificada) {
        this.notificada = notificada;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    @Override
    public String toString() {
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");
        return (concluida ? "[X] " : "[ ] ") + titulo + " - " + data.format(dataFormat) + " " + horario.format(horaFormat) +
               "\nDescrição: " + descricao;
    }
}
