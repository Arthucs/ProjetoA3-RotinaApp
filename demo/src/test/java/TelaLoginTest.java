import org.junit.Test;

import com.example.TelaLogin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TelaLoginTest {
    @Test
    public void testContemComponentesEssenciais() {
        TelaLogin tela = new TelaLogin();

        assertEquals("Login", tela.getTitle());

        assertTrue(tela.getContentPane().getLayout() instanceof BorderLayout);

        assertEquals(JFrame.EXIT_ON_CLOSE, tela.getDefaultCloseOperation());
    }

    private List<Component> encontrarComponentes(Class<?> tipo, Container container) {
        List<Component> encontrados = new ArrayList<>();

        for (Component comp : container.getComponents()) {
            if (tipo.isInstance(comp)) {
                encontrados.add(comp);
            }

            if (comp instanceof Container childContainer) {
                encontrados.addAll(encontrarComponentes(tipo, childContainer));
            }
        }

        return encontrados;
    }

    @Test
    public void testTerCampoUsuario() {
        TelaLogin tela = new TelaLogin();
        List<Component> camposTexto = encontrarComponentes(JTextField.class, tela.getContentPane());

        assertFalse("Campo de usuário não encontrado.", camposTexto.isEmpty());
    }

    @Test
    public void testTerCampoSenha() {
        TelaLogin tela = new TelaLogin();
        List<Component> camposSenha = encontrarComponentes(JPasswordField.class, tela.getContentPane());

        assertFalse("Campo de senha não encontrado.", camposSenha.isEmpty());
    }
}
