package login;

import components.Logo;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import main.Run;
import properties.Mensaje;

public class IniciarPrograma extends JFrame {

    //ATRIBUTOS
    private static boolean activo;
    private static Dimension DIMENSION = new Dimension(450, 450);

    public IniciarPrograma(String identificacion, int rol, String nombre) {
        //Propiedades básicas
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Iniciando el programa - AquaTech");
        this.setIconTitle();
        this.setMinimumSize(DIMENSION);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.getContentPane().setBackground(properties.Colores.BLANCO);

        this.activo = true;

        initComponents();
        relocate();
        
        //Iniciar el programa SIN cargar los datos
        Run.iniciarPrograma(identificacion, rol, nombre);

        //Intentar actualizar TODOS los datos del software        
        if (Run.actualizarPrograma()) {
            
            
            //Cerrar la ventana de carga
            dispose();

            //Hacer visible el programa
            Run.setFrameVisible(true);

        } else {
            Mensaje.msjError("No se pudo establecer la conexión con el programa.\n"
                    + "Por favor, verifique su conexión a internet y vuelva a intentarlo.");
            
            //Cerrar la ventana de carga
            dispose();
            
            //Regresar al Login
            Run.iniciarLogin();
        }

        this.activo = false;
    }

    private void initComponents() {
        //Propiedades básicas del logo
        logo.setText("Cargando programa");
        logo.setForeground(properties.Colores.CELESTE);
        logo.setSize(logo.getPreferredSize());

        //Propiedades básicas del gif de cargando
        gif.setHorizontalAlignment(JLabel.CENTER);
        gif.setVerticalAlignment(JLabel.CENTER);
        gif.setToolTipText("Cargando el programa...");

        //Buscar la imágen
        try {
            //Buscar el gif de carga
            gif.setIcon(new ImageIcon(getClass().getResource("/icons/cargando.gif")));
            //Redimensionar el label
            gif.setSize(gif.getPreferredSize());

        } catch (Exception e) {
            Mensaje.msjAdvertencia("No se encontró la imagen del cargando.\n"
                    + "El software seguirá ejecutandose normalmente sin el ícono.");

            gif.setSize(256, 256);
        }

        this.add(gif);
        this.add(logo);
    }

    private void relocate() {
        int width = this.getContentPane().getWidth();
        int padding = 20;

        int x = width / 2 - logo.getWidth() / 2;
        logo.setLocation(x, padding);

        x = width / 2 - gif.getWidth() / 2;
        int y = logo.getHeight() + padding * 2;
        gif.setLocation(x, y);
    }

    /**
     * Función para colocarle el ícono del software al frame.
     */
    private void setIconTitle() {
        try {
            //Cargar el ícono del frame
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo.png"));
            this.setIconImage(img.getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        } catch (Exception ex) {
            Mensaje.msjAdvertencia(
                    "No se pudo encontrar el ícono del software.\n"
                    + "El software seguirá ejecutandose normalmente sin el ícono."
            );
        }
    }

    /**
     * Función para saber si la ventana de carga está activa.
     *
     * @return ventana de carga activa TRUE, programa iniciado FALSE.
     */
    public static boolean isActivated() {
        return activo;
    }

    //COMPONENTES
    private final JLabel gif = new JLabel();
    private final Logo logo = new Logo(SwingConstants.HORIZONTAL);
}
