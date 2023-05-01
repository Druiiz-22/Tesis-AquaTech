package login;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static properties.Mensaje.msjAdvertencia;

/**
 * Clase para la creación del Frame del login.
 */
public class Frame extends JFrame implements properties.Constantes {

    //Atributo
    private static final Dimension DIMENSION = new Dimension(450, 600);

    /**
     * Constructor para la creación del Frame del Login.
     */
    public Frame() {

        //Tema de Windows
        this.WindowsTheme();

        //Propiedades básicas
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Login - AquaTech");
        this.setIconTitle();
        this.setMinimumSize(DIMENSION);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        //Asignarle al panel contenedor el tamaño ajustado al frame
        panelContenedor.setSize(this.getContentPane().getSize());

        //Iniciar los componentes de los paneles
        panelInicio.initComponents();
        panelRegistro.initComponents();
        panelRecuperacion.initComponents();

        //Añadir el inicio
        panelContenedor.add(panelInicio);

        //Agregar el panel contenedor como panel principal al frame
        this.setContentPane(panelContenedor);

        //Recargar el panel
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    /**
     * Función para obtener el tamaño del panel contenedor.
     *
     * @return Dimensión del panel contenedor.
     */
    protected static Dimension getParentSize() {
        return panelContenedor.getSize();
    }

    /**
     * Función para cambiar el panel contenedor del frame del login.
     * @param type Tipo de pestaña que será mostrada .
     * (INICIO, REGISTRO o RECUPERACIÓN).
     */
    public static void replacePanel(int type) {
        //Eliminar el contenido actual del panel
        panelContenedor.removeAll();

        switch (type) {
            case INICIO:
                panelRegistro.vaciarCampos();
                panelRecuperacion.vaciarCampos();
                panelContenedor.add(panelInicio);
                break;
                
            case REGISTRO:
                panelInicio.vaciarCampos();
                panelRecuperacion.vaciarCampos();
                panelContenedor.add(panelRegistro);
                break;
                
            case RECUPERACION:
                panelInicio.vaciarCampos();
                panelRegistro.vaciarCampos();
                panelContenedor.add(panelRecuperacion);
                break;
        }

        //Actualizar el panel
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    /**
     * Función para colocarle el estilo de Windows al Frame.
     */
    private void WindowsTheme() {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            msjAdvertencia(
                    "No se pudo cargar el estilo de Windows para el software.\n"
                    + "El software seguirá ejecutandose normalmente con el estilo predeterminado."
            );
        }
    }

    /**
     * Función para colocarle el ícono del software al frame.
     */
    private void setIconTitle() {
        try {
            //Cargar el ícono del frame
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo.png"));
            this.setIconImage(img.getScaledInstance(32, 32, ESCALA_SUAVE));

        } catch (Exception ex) {
            msjAdvertencia(
                    "No se pudo encontrar el ícono del software.\n"
                    + "El software seguirá ejecutandose normalmente sin el ícono."
            );
        }
    }
    
    //COMPONENTES
    private static final JPanel panelContenedor = new JPanel(null);
    private static final Inicio panelInicio = new Inicio();
    private static final Registro panelRegistro = new Registro();
    private static final Recuperacion panelRecuperacion = new Recuperacion();
}
