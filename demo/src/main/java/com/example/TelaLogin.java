package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {

    private final Autenticador autenticador;

    public TelaLogin() {
        this.autenticador = new Autenticador();
        configurarInterface();
    }

    private void configurarInterface() {
        setTitle("Login");
        setSize(300, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel labelTitulo = new JLabel("Acesso ao OrganizApp", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(labelTitulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField campoUsuario = new JTextField();
        JPasswordField campoSenha = new JPasswordField();

        painelCampos.add(new JLabel("Usuário:"));
        painelCampos.add(campoUsuario);
        painelCampos.add(new JLabel("Senha:"));
        painelCampos.add(campoSenha);
        add(painelCampos, BorderLayout.CENTER);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.addActionListener((ActionEvent e) -> {
            String usuario = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            autenticarEEntrar(usuario, senha);
        });

        JPanel painelBotao = new JPanel();
        painelBotao.add(botaoEntrar);
        add(painelBotao, BorderLayout.SOUTH);
    }

    public void autenticarEEntrar(String usuario, String senha) {
        if (autenticador.autenticar(usuario, senha)) {
            dispose();
            GerenciaRotina gerencia = new GerenciaRotina("rotina_" + usuario + ".txt");
            new RotinaInterfaceG(gerencia).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
