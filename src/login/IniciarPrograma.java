package login;

import components.Boton;
import components.Label;
import components.Logo;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import main.Run;
import properties.Constantes;
import static properties.Constantes.PLANO;
import properties.Fuentes;
import properties.Mensaje;

public class IniciarPrograma extends JFrame {

    //ATRIBUTOS
    private static String identificacion, nombre;
    private static int rol, empleado_sucursal, id_sucursal;
    private static Object[][] sucursales;
    private static boolean activo, iniciando = false;
    private static Dimension DIMENSION = new Dimension(480, 430);

    public IniciarPrograma(String identificacion, int rol, String nombre, Object[][] sucursales, int empleado_sucursal) {
        //Atributos
        IniciarPrograma.identificacion = identificacion;
        IniciarPrograma.rol = rol;
        IniciarPrograma.nombre = nombre;
        IniciarPrograma.sucursales = sucursales;
        IniciarPrograma.empleado_sucursal = empleado_sucursal;

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

        //Propiedades del comboBox de las sucursales
        boxSucursales.setFont(Fuentes.segoe(16, PLANO));
        //Vaciar el comboBox
        boxSucursales.removeAllItems();
        //Guardar todas las sucursales
        for (Object[] sucursal : sucursales) {
            //Capitalizar las sucursales
            String capitalize = sucursal[1].toString();
            capitalize = capitalize.substring(0, 1).toUpperCase() + capitalize.substring(1).toLowerCase();
            //Agregar las sucursales
            boxSucursales.addItem(
                    new ComboItem(
                            capitalize,
                            sucursal[0].toString()
                    )
            );
        }

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

        this.add(logo);
        this.add(lblSucursales);
        this.add(boxSucursales);
        this.add(aceptar);
        this.add(cargando);

        //Hacer visible a los componentes
        lblSucursales.setVisible(true);
        boxSucursales.setVisible(true);
        aceptar.setVisible(true);
        cargando.setVisible(false);

        boxSucursales.setEnabled(true);
        aceptar.setEnabled(true);
    }

    private void relocate() {
        int width = this.getContentPane().getWidth();
        int height = this.getContentPane().getHeight();
        int padding = 20;

        int x = width / 2 - logo.getWidth() / 2 - 10;
        logo.setLocation(x, padding);

        x = width / 2 - cargando.getWidth() / 2;
        int y = logo.getHeight() + padding * 2;
        cargando.setLocation(x, y);

        padding = 40;
        int sucursalesHeight = lblSucursales.getHeight() + 45;
        y = height / 2 - sucursalesHeight / 2;
        lblSucursales.setLocation(padding, y);

        y += lblSucursales.getHeight() + 5;
        int w = width - padding * 2;
        boxSucursales.setBounds(padding, y, w, 40);

        y = height - padding - 40;
        w = aceptar.getPreferredSize().width + 150;
        x = width / 2 - w / 2;
        aceptar.setBounds(x, y, w, 40);
    }

    private void listener() {

        aceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!iniciando) {
                    iniciando = true;
                    validarSucursal();
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == Constantes.TECLA_ENTER && !iniciando) {
                    iniciando = true;
                    validarSucursal();
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (Mensaje.msjYesNo("¿Está seguro de salir?")) {
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

    private void validarSucursal() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //Intentar obtener el ID de la sucursal seleccionada
                    Object sucursal = boxSucursales.getSelectedItem();
                    id_sucursal = Integer.valueOf(((ComboItem) sucursal).getValue());

                    //Validar que el ID sea válido
                    if (id_sucursal > 0) {
                        //Si el ID de la sucursal seleccionada es DISTINTO al
                        //sucursal afiliado del empleado, mostrar mensaje de
                        //advertencia
                        if (empleado_sucursal != id_sucursal) {

                            //Comprobar si el empleado que accedió, tiene los
                            //permisos de interactuar en otras sucursales
                            if (rol == Constantes.OPERADOR || rol == Constantes.ADMINISTRADOR) {
                                String msj = "<html>"
                                        + "<p>La sucursal seleccionada no le corresponde a usted.</p>"
                                        + "<p>Cualquier accion realizada dentro del "
                                        + "programa,</p>"
                                        + "<p style='color:rgb(180,0,0);margin-top:0px'>"
                                        + "será reflejado en la sucursal seleccionada."
                                        + "</p>"
                                        + "<p style='margin-top:8px;'>¿Está seguro "
                                        + "de continuar con la sucursal escogida?</p>"
                                        + "</html>";
                                if (Mensaje.msjYesNoWarning(msj)) {
                                    iniciar();
                                }
                            } else {
                                Mensaje.msjError("Usted no posee los permisos para "
                                        + "acceder a una sucursal que no le corresponde.");
                            }
                        } else {
                            iniciar();
                        }
                    } else {
                        throw new NumberFormatException();
                    }

                } catch (NumberFormatException ex) {
                    Mensaje.msjError("No se pudo obtener la información de la "
                            + "sucursal seleccionada.\nPor favor, vuelva a "
                            + "intentarlo más tarde.");
                } finally {
                    iniciando = false;
                }
            }
        }.start();
    }

    private void iniciar() {
        //Ocultar los componentes
        lblSucursales.setVisible(false);
        boxSucursales.setVisible(false);
        aceptar.setVisible(false);

        boxSucursales.setEnabled(false);
        aceptar.setEnabled(false);

        cargando.setVisible(true);

        //Iniciar el programa SIN cargar los datos
        Run.instanciarMain(identificacion, rol, nombre, id_sucursal);

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

    /**
     * Función para saber si la ventana de carga está activa.
     *
     * @return ventana de carga activa TRUE, programa iniciado FALSE.
     */
    public static boolean isActivated() {
        return activo;
    }

    public static void setPercent(int percent) {
        IniciarPrograma.percent += percent;
        cargando.setText(IniciarPrograma.percent + "%");
    }

    //COMPONENTES
    private static int percent;
    private final Boton aceptar = new Boton("Aceptar", properties.Colores.CELESTE);
    private final Label lblSucursales = new Label("Seleccione la sucursal donde se encuentra", PLANO, 16);
    private final JComboBox boxSucursales = new JComboBox();
    private final Logo logo = new Logo(SwingConstants.HORIZONTAL);
    private static final JLabel cargando = new JLabel("0%");

    private class ComboItem {

        private String key;
        private String value;

        public ComboItem(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
