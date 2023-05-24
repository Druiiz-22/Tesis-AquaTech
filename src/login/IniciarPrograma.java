package login;

import components.Logo;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import main.Run;
import properties.Fuentes;
import properties.Mensaje;

public class IniciarPrograma extends JFrame {

    //ATRIBUTOS
    private static boolean activo;
    private static Dimension DIMENSION = new Dimension(480, 430);

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

        IniciarPrograma.activo = true;

        initComponents();
        relocate();
        listener();
        
        //Actualizar el frame
        this.revalidate();
        this.repaint();
        
        //Iniciar el programa SIN cargar los datos
        Run.instanciarMain(identificacion, rol, nombre);

        //Intentar actualizar TODOS los datos del software        
        if (Run.actualizarPrograma()) {
            //Cerrar la ventana de carga
            dispose();

            //Hacer visible el programa
            Run.setFrameVisible();

        } else {
            Mensaje.msjError("No se pudo establecer la conexión con el programa.\n"
                    + "Por favor, verifique su conexión a internet y vuelva a intentarlo.");
            
            //Cerrar la ventana de carga
            dispose();
            
            //Regresar al Login
            Run.iniciarLogin();
        }

        IniciarPrograma.activo = false;
        IniciarPrograma.percent = 0;
        IniciarPrograma.cargando.setText("0%");
    }

    private void initComponents() {
        //Propiedades básicas del logo
        logo.setText("Iniciando el programa");
        logo.setForeground(properties.Colores.CELESTE);
        logo.setSize(logo.getPreferredSize());

        //Propiedades básicas del gif de cargando
        cargando.setHorizontalTextPosition(JLabel.CENTER);
        cargando.setHorizontalAlignment(JLabel.CENTER);
        cargando.setVerticalTextPosition(JLabel.CENTER);
        cargando.setVerticalAlignment(JLabel.CENTER);
        cargando.setForeground(properties.Colores.CELESTE);
        cargando.setFont(Fuentes.segoeSemibold(48));
        
        //Buscar la imágen
        try {
            //Buscar el gif de carga
            cargando.setIcon(new ImageIcon(getClass().getResource("/icons/cargando.gif")));
            //Redimensionar el label
            cargando.setSize(cargando.getPreferredSize());

        } catch (Exception e) {
            Mensaje.msjAdvertencia("No se encontró la imagen del cargando.\n"
                    + "El software seguirá ejecutandose normalmente sin el ícono.");

            cargando.setSize(256, 256);
        }

        this.add(cargando);
        this.add(logo);
    }

    private void relocate() {
        int width = this.getContentPane().getWidth();
        int padding = 20;

        int x = width / 2 - logo.getWidth() / 2 - 10;
        logo.setLocation(x, padding);

        x = width / 2 - cargando.getWidth() / 2;
        int y = logo.getHeight() + padding * 2;
        cargando.setLocation(x, y);
    }

    private void listener(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(Mensaje.msjYesNo("¿Está seguro de salir?")){
                    dispose();
                    Run.cerrarPrograma();
                    Run.cerrarLogin();
                    //Terminar de ejecutar el programa
                    System.exit(0);
                }
            }
        });
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
    
    
    public static void setPercent(int percent){
        IniciarPrograma.percent += percent;
        cargando.setText(IniciarPrograma.percent+"%");
    }
    
    //COMPONENTES
    private static int percent;
    private static final JLabel cargando = new JLabel("0%");
    private static final Logo logo = new Logo(SwingConstants.HORIZONTAL);
}
