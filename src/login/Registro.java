package login;

import components.Logo;
import database.CreateDB;
import java.awt.Dimension;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.HORIZONTAL;
import login.registro.DatosPersonales;
import login.registro.ClaveNueva;
import login.registro.Codigo;
import login.registro.Correo;
import static login.Frame.getParentSize;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjInformativo;

/**
 * Clase para el panel del registro de usuario.
 */
public class Registro extends JPanel implements properties.Colores, properties.Constantes {

    //ATRIBUTOS DE LA CLASE
    private static String nombre;
    private static String apellido;
    private static int cedula;
    private static String telefono;
    private static String correo;
    private static String clave;

    /**
     * Constructor para la creación del panel del registro de los usuarios.
     */
    public Registro() {
        this.setLayout(null);
        this.setBackground(BLANCO);

    }

    /**
     * Función para iniciar los componentes
     */
    public void initComponents() {

        this.setSize(getParentSize());

        //Punto medio del panel
        int middleX = this.getWidth() / 2;

        //Asignarle el texto al logo
        logo.setText("Registrarse");
        logo.setForeground(CELESTE);
        logo.setSize(logo.getPreferredSize());

        //Posicionar el logo a mitad del panel
        int logoX = middleX - logo.getWidth() / 2;
        int logoMarginTop = 30;
        int logoMarginBottom = 37;
        logo.setLocation(logoX, logoMarginTop);

        //Posicionar el contenedor debajo del logo.
        int contenedorY = logoMarginTop + logo.getHeight() + logoMarginBottom;
        //Asignarle de tamaño, al contenedor, el espacio restante debajo del logo
        int contenedorHeight = this.getHeight() - contenedorY;
        panelContenedor.setBounds(0, contenedorY, this.getWidth(), contenedorHeight);

        //Hacer el contenedor transparente
        panelContenedor.setOpaque(false);

        //Inicializar los paneles
        panelDatosPersonales.initComponents();
        panelCorreo.initComponents();
        panelCodigo.initComponents();
        panelClaveNueva.initComponents();

        //Añadirle el panel de los datos personales
        panelContenedor.add(panelDatosPersonales);

        //Añadir el logo y el contenedor al panel de registro
        this.add(logo);
        this.add(panelContenedor);
    }

    /**
     * Función para vaciar todos los campos del registro de usuarios y los datos
     * de usuarios almacenados.
     */
    public void vaciarCampos() {
        nombre = null;
        apellido = null;
        cedula = 0;
        telefono = null;
        correo = null;
        clave = null;

        panelDatosPersonales.vaciarCampos();
        panelCorreo.vaciarCampos();
        panelCodigo.vaciarCampos();
        panelClaveNueva.vaciarCampos();
    }

    /**
     * Función para obtener el tamaño del panel contenedor.
     *
     * @return Dimensión del panel contenedor
     */
    public static Dimension getContentSize() {
        return panelContenedor.getSize();
    }

    /**
     * Función para cambiar el panel contenedor del registro.
     *
     * @param type Tipo de ventana que será mostrada (DATOS, CORREO, CODIGO o
     * CLAVE).
     */
    public static void replaceContainer(int type) {
        //Eliminar el contenido actual del panel
        panelContenedor.removeAll();

        switch (type) {
            case DATOS:
                panelContenedor.add(panelDatosPersonales);
                break;

            case CORREO:
                panelContenedor.add(panelCorreo);
                break;

            case CODIGO:
                panelContenedor.add(panelCodigo);
                break;

            case CLAVE:
                panelContenedor.add(panelClaveNueva);
                break;
        }

        //Actualizar el panel
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    /**
     * Función para guardar los datos personales del usuario en la clase de
     * registro.
     *
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param cedula Cédula del usuario.
     * @param telefono Teléfono del usuario.
     */
    public static void setDatosPersonales(String nombre, String apellido, int cedula, String telefono) {
        Registro.nombre = nombre;
        Registro.apellido = apellido;
        Registro.cedula = cedula;
        Registro.telefono = telefono;
    }

    /**
     * Función para guardar el correo electrónico del usuario en la clase de
     * registro, luego de ser confirmado.
     *
     * @param correo Correo del usuario
     */
    public static void setCorreo(String correo) {
        Registro.correo = correo;
    }

    /**
     * Función para guardar la contraseña del usuario en la clase de registro.
     *
     * @param clave Contraseña del usuario.
     */
    public static void setClave(String clave) {
        Registro.clave = clave;
    }

    /**
     * Función para crear la cuenta nueva, según los datos ingresados.
     */
    public static void crearCuenta() {
        String error = "\nPor favor, vuelva a intentarlo.";

        //Validar que ninguno de los datos personales esté vacío
        if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty() && cedula > 0) {
            //Validar que el correo y clave no estén vacíos
            if (!correo.isEmpty()) {
                if (!clave.isEmpty()) {

                    //Intentar crear el usuario
                    if (CreateDB.createUsuario(cedula, nombre, apellido, telefono, correo, clave, EMPLEADO)) {
                        //Mensaje de éxito
                        msjInformativo("El usuario se ha creado exitosamente.");

                        //Volver al inicio
                        login.Frame.replacePanel(INICIO);
                        replaceContainer(DATOS);
                    }

                } else {
                    msjError("El usuario no tiene una clave asignada." + error);
                }
            } else {
                msjError("El usuario no tiene un correo asignado." + error);
            }
        } else {
            msjError("El usuario no tiene alguno de sus datos personales asignado." + error);
        }
    }

    //COMPONENTES
    private static final Logo logo = new Logo(HORIZONTAL);
    private static final JPanel panelContenedor = new JPanel(null);
    private static final DatosPersonales panelDatosPersonales = new DatosPersonales();
    private static final ClaveNueva panelClaveNueva = new ClaveNueva();
    private static final Codigo panelCodigo = new Codigo();
    private static final Correo panelCorreo = new Correo();

}
