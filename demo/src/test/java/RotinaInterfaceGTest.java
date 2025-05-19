
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.example.GerenciaRotina;
import com.example.RotinaInterfaceG;
import com.example.Tarefa;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

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

}
