package com.example;
import java.io.Serializable;

public class Tarefa implements Serializable {
    private String titulo;
    private String descricao;
    private String data;
    private String horario;
    private boolean concluida;

    public Tarefa(String titulo, String descricao, String data, String horario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.horario = horario;
        this.concluida = false;
    }

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    public boolean isConcluida() {
        return concluida;
    }

    @Override
    public String toString() {
        return (concluida ? "[X] " : "[ ] ") + titulo + " - " + data + " " + horario +
               "\nDescrição: " + descricao;
    }
}
