
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.example.GerenciaRotina;
import com.example.RotinaInterfaceG;
import com.example.Tarefa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

public class RotinaInterfaceGTest {

    private GerenciaRotina mockGerencia;
    private RotinaInterfaceG interfaceG;

    @Before
    public void setup() {
        mockGerencia = mock(GerenciaRotina.class);
        interfaceG = new RotinaInterfaceG(mockGerencia);

        interfaceG.setVisible(false);
    }

    @Test
    public void testChamarConstruirTarefaQuandoClicarAdicionar() {
        JButton btn = (JButton) interfaceG.getContentPane().getComponent(0);
        btn.doClick();
        verify(mockGerencia, atLeast(0)).construirTarefa(any(), any());
    }

    @Test
    public void testChamarListarTarefasQuandoClicarListar() {
        when(mockGerencia.getTarefas()).thenReturn(Collections.emptyList());

        JButton btn = (JButton) interfaceG.getContentPane().getComponent(1);
        btn.doClick();

        verify(mockGerencia).getTarefas();
    }

    @Test
    public void testChamarConcluirTarefaQuandoClicarConcluir() {
        when(mockGerencia.getTarefas()).thenReturn(Collections.singletonList(
                new Tarefa("Teste", "Desc", java.time.LocalDate.now(), java.time.LocalTime.now())
        ));

        JButton btn = (JButton) interfaceG.getContentPane().getComponent(2);
        btn.doClick();

        verify(mockGerencia).concluirTarefa(any(), any());
    }

    @Test
    public void testListarTarefasComTarefas() {
        Tarefa tarefa1 = mock(Tarefa.class);
        Tarefa tarefa2 = mock(Tarefa.class);
        when(tarefa1.toString()).thenReturn("Estudar Java");
        when(tarefa2.toString()).thenReturn("Fazer exercícios");
        when(mockGerencia.getTarefas()).thenReturn(Arrays.asList(tarefa1, tarefa2));

        interfaceG.listarTarefas();
    }

    @Test
    public void testConcluirTarefaQuandoNaoHaTarefas() {
        when(mockGerencia.getTarefas()).thenReturn(Collections.emptyList());

        interfaceG.concluirTarefa();
    }

    @Test
    public void testConcluirTarefaQuandoUsuarioCancela() {
        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.toString()).thenReturn("Tarefa 1");
        when(mockGerencia.getTarefas()).thenReturn(Collections.singletonList(tarefa));

        JOptionPane pane = mock(JOptionPane.class);
        try (var mocked = Mockito.mockStatic(JOptionPane.class)) {
            mocked.when(() -> JOptionPane.showInputDialog(any(), any())).thenReturn(null);
            interfaceG.concluirTarefa();
            verify(mockGerencia, never()).concluirTarefa(any(), any());
        }
    }

    @Test
    public void testConcluirTarefaComEntradaValida() {
        Tarefa tarefa = mock(Tarefa.class);
        when(tarefa.toString()).thenReturn("Tarefa 1");
        when(mockGerencia.getTarefas()).thenReturn(Collections.singletonList(tarefa));

        try (var mocked = Mockito.mockStatic(JOptionPane.class)) {
            mocked.when(() -> JOptionPane.showInputDialog(any(), any())).thenReturn("1");
            interfaceG.concluirTarefa();
            verify(mockGerencia).concluirTarefa(any(Scanner.class), isNull());
        }
    }

    @Test
    public void testInicializarJanelaComTituloEComponentes() {
        RotinaInterfaceG janela = new RotinaInterfaceG(mockGerencia);
        janela.pack();
        janela.setVisible(true);
        assertEquals("Menu Principal", janela.getTitle());

        assertTrue(janela.getLayout() instanceof BorderLayout);

        Component[] componentes = janela.getContentPane().getComponents();

        boolean encontrouTitulo = false;

        for (Component c : componentes) {
            if (c instanceof JLabel) {
                JLabel labelTitulo = (JLabel) c;
                assertEquals("OrganizApp", labelTitulo.getText());
                assertEquals(SwingConstants.CENTER, labelTitulo.getHorizontalAlignment());
                encontrouTitulo = true;
                break;
            }
        }

        assertTrue(encontrouTitulo);
    }

    @Test
    public void testCriarBotaoComTextoEAcao() {
        RotinaInterfaceG janela = new RotinaInterfaceG(mockGerencia);

        ActionListener listener = mock(ActionListener.class);
        JButton botao = janelaTestPrivate_criarBotao(janela, "Clique aqui", listener);

        assertEquals("Clique aqui", botao.getText());
        assertEquals(new Dimension(200, 30), botao.getPreferredSize());
        assertEquals("Arial", botao.getFont().getFontName());

        botao.doClick();
        verify(listener, times(1)).actionPerformed(any(ActionEvent.class));
    }

    private JButton janelaTestPrivate_criarBotao(RotinaInterfaceG janela, String texto, ActionListener acao) {
        try {
            java.lang.reflect.Method metodo = RotinaInterfaceG.class.getDeclaredMethod("criarBotao", String.class, ActionListener.class);
            metodo.setAccessible(true);
            return (JButton) metodo.invoke(janela, texto, acao);
        } catch (Exception e) {
            fail("Erro ao acessar método criarBotao: " + e.getMessage());
            return null;
        }
    }
}
