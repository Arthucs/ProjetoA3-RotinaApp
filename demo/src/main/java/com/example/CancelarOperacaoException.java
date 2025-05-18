package com.example;

public class CancelarOperacaoException extends RuntimeException {
    public CancelarOperacaoException() {
        super("Operação cancelada pelo usuário.");
    }
}