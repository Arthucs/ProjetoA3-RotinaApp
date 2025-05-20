import org.junit.Test;
import org.junit.Before;

import com.example.Autenticador;
import com.example.TelaLogin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TelaLoginTest {
    private Autenticador mockAutenticador;
    private TelaLogin tela;
    
    @Before
    public void setUp() {
        mockAutenticador = mock(Autenticador.class);
        tela = spy(new TelaLogin());
    }

    @Test
    public void testContemComponentesEssenciais() {

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
        List<Component> camposTexto = encontrarComponentes(JTextField.class, tela.getContentPane());

        assertFalse("Campo de usuário não encontrado.", camposTexto.isEmpty());
    }

    @Test
    public void testTerCampoSenha() {
        List<Component> camposSenha = encontrarComponentes(JPasswordField.class, tela.getContentPane());

        assertFalse("Campo de senha não encontrado.", camposSenha.isEmpty());
    }

    @Test
    public void testAbrirMenuPrincipalComAutenticacaoValida() {
        when(mockAutenticador.autenticar("user", "abcd")).thenReturn(true);

        tela.autenticarEEntrar("user", "abcd");

        verify(tela).dispose();
    }
}
