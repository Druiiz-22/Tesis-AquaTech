package login.recuperacion;

import components.Boton;
import components.CampoTexto;
import components.Label;
import database.EmailCode;
import database.ReadDB;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import login.Recuperacion;
import static login.Frame.replacePanel;
import static login.Recuperacion.getContentSize;
import static login.Recuperacion.replaceContainer;
import static properties.Mensaje.msjError;
import static properties.ValidarTexto.formatoCorreo;

/**
 * Clase para el panel de ingreso del correo del usuario
 */
public class Correo extends javax.swing.JPanel implements properties.Colores, properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para enviar el código al correo y cambiar el panel
     */
    private void enviarCodigo() {

        //Validar el campo de texto
        if (validarCampo()) {

            //Validar que el correo SÍ exista en la base de datos
            if (ReadDB.emailExists(correoUsuario)) {

                //Objeto para generar un número aleatorio
                Random aleatorio = new Random();
                int codigoSeguridad = aleatorio.nextInt(999999);

                //Validar si se pudo enviar el correo o no
                if (EmailCode.recuperarCuenta(correoUsuario, codigoSeguridad)) {
                    //Guardar el correo en la clase de registro
                    Recuperacion.setCorreo(correoUsuario);

                    //Mostrar el código en la pestaña del código
                    Codigo.setCorreo(correoUsuario, codigoSeguridad);

                    //Avanzar a la pestaña de validación de código
                    replaceContainer(CODIGO);
                }
            } else {
                msjError("El correo no se encuentra registrado."
                        + "\nPor favor, revise sus datos.");
            }
        }
    }

    /**
     * Función para validar el campo y el correo
     *
     * @return TRUE en caso de que el correo sea válido.
     */
    private boolean validarCampo() {
        correoUsuario = txtCorreo.getText().trim();

        //Validar que el campo no esté vacío
        if (!correoUsuario.isEmpty()) {

            //Validar que el correo cumpla con el formato correcto
            if (formatoCorreo(correoUsuario)) {

                return true;

            } else {
                msjError(
                        "El correo es inválido.\n"
                        + "Por favor, revise sus datos."
                );
                txtCorreo.requestFocus();
            }

        } else {
            msjError(
                    "El correo no puede estár vacío.\n"
                    + "Por favor, ingrese sus datos."
            );
            txtCorreo.requestFocus();
        }

        //Retornar falso, en caso de no retornar true antes.
        return false;
    }

    //Atributos
    private static String correoUsuario;

    // ========== FRONTEND ==========
    /**
     * Constructor para la creación del panel para registrar el correo.
     */
    public Correo() {
        this.setLayout(null);
        this.setOpaque(false);

        //Listener para los componentes
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    public void initComponents() {
        this.setSize(getContentSize());
        //Punto medio
        int middleX = this.getWidth() / 2;
        //Margen interno al comienzo (izquierda)
        int paddingStart = 40;
        //Margen interno al final (abajo)
        int paddingBottom = 50;
        //Tamaño de los campos de textos y botones
        int fieldHeight = 40;
        int fieldWidth = this.getWidth() - paddingStart * 2;
        Dimension fieldSize = new Dimension(fieldWidth, fieldHeight);

        //LABEL PARA LA INFORMACIÓN DEL PANEL
        String info = "<html>¿Has perdido tu contraseña? <b>ingrese su correo<br>"
                + "electrónico</b> afiliado a su cuenta. Usted recibirá<br>un "
                + "código con el que podrá asignar una nueva<br>contraseña "
                + "a su cuenta.</html>";
        lblInfo.setText(info);
        lblInfo.setLocation(paddingStart, 0);
        lblInfo.setSize(lblInfo.getPreferredSize());

        //CAMPO DE TEXTO DEL CORREO
        int correoY = this.getHeight() / 2 - fieldHeight;
        int labelY = correoY - lblCorreo.getHeight() - 5;
        lblCorreo.setLocation(paddingStart, labelY);
        txtCorreo.setLocation(paddingStart, correoY);
        txtCorreo.setSize(fieldSize);

        //LABEL PARA IR AL INICIO
        int iniciarY = this.getHeight() - paddingBottom - lblIniciar.getHeight();
        int iniciarX = middleX - (lblIniciar.getWidth() + btnIniciar.getWidth()) / 2;
        lblIniciar.setLocation(iniciarX, iniciarY);
        btnIniciar.setLocation(iniciarX + lblIniciar.getWidth(), iniciarY);
        btnIniciar.setToolTipText("Regresar al comienzo para iniciar sesión");

        //BOTÓN PARA AVANZAR
        int btnY = iniciarY - fieldHeight - 5;
        btnEnviar.setLocation(paddingStart, btnY);
        btnEnviar.setSize(fieldSize);

        //Agregar los componentes
        this.add(lblInfo);
        this.add(lblCorreo);
        this.add(txtCorreo);
        this.add(btnEnviar);
        this.add(lblIniciar);
        this.add(btnIniciar);
    }

    /**
     * Función para vaciar los campos de textos del panel
     */
    public void vaciarCampos() {
        txtCorreo.setText("");
    }

    /**
     * Función que contiene los listener de los componentes del panel
     */
    private void listeners() {
        btnIniciar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Ir hacia el panel de inicio
                replacePanel(INICIO);
                replaceContainer(CORREO);
            }
        });
        btnEnviar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                enviarCodigo();
            }
        });
    }

    //COMPONENTES
    private static final Label lblInfo = new Label("", PLANO, 16);
    private static final Label lblCorreo = new Label("Correo electrónico", PLANO, 16);
    private static final CampoTexto txtCorreo = new CampoTexto("Ingrese su correo", CORREO);
    private static final Boton btnEnviar = new Boton("Enviar código", CELESTE);
    private static final Label lblIniciar = new Label("¿Ya recuperaste tu clave? ", PLANO, 16);
    private static final Label btnIniciar = new Label("Inicia sesión", LINK, 16);
}
