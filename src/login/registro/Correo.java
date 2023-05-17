package login.registro;

import components.Boton;
import components.CampoTexto;
import components.Label;
import database.EmailCode;
import database.ReadDB;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import login.Registro;
import static login.Frame.replacePanel;
import static login.Registro.getContentSize;
import static login.Registro.replaceContainer;
import static properties.Constantes.TECLA_ENTER;
import static properties.Mensaje.msjError;
import static properties.ValidarTexto.formatoCorreo;

/**
 * Clase para crear la pestaña del registro del correo.
 */
public class Correo extends javax.swing.JPanel implements properties.Colores, properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para enviar el código al correo y cambiar el panel
     */
    private void enviarCodigo() {

        //Validar el campo de texto
        if (validarCampo()) {

            //Validar que el correo NO exista en la base de datos
            if (ReadDB.emailExists(correoUsuario) == 0) {

                //Generar un número aleatorio de 6 dígitos
                int codigoSeguridad = (int) (Math.random() * (999999 - 100000) + 100000);

                //Validar si se pudo enviar el correo o no
                if (EmailCode.comprobarCorreo(correoUsuario, codigoSeguridad)) {

                    //Obtener la fecha actual
                    java.util.Date actual = new java.util.Date();
                    Calendar fechaSalida = new java.util.GregorianCalendar();
                    fechaSalida.setTime(actual);
                    //Sumar 10 minutos a la fecha
                    fechaSalida.add(Calendar.MINUTE, 30);

                    //Obtener la fecha máxima de validación del código de seguridad
                    Calendar fechaExpiracion = new java.util.GregorianCalendar();
                    fechaExpiracion.setTime(fechaSalida.getTime());

                    //Reasignar la fecha actual
                    fechaSalida.setTime(actual);

                    //Guardar el correo en la clase de registro
                    Registro.setCorreo(correoUsuario);

                    //Enviar el correo y el código a la pestaña siguiente
                    Codigo.setDatos(correoUsuario, codigoSeguridad, fechaSalida, fechaExpiracion);

                    //Avanzar a la pestaña de validación de código
                    replaceContainer(CODIGO);
                }
            } else {
                msjError("El correo ya se encuentra en uso.\nPor favor, "
                        + "revise sus datos");
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
                msjError("<html>"
                        + "<p>El formato del correo es inválido. Ej: <i>example@email.com</i></p>"
                        + "<p>Por favor, revise sus datos</p>"
                        + "</html>");
                txtCorreo.requestFocus();
            }

        } else {
            msjError(
                    "El correo no puede estár vacío.\n"
                    + "Por favor, ingrese su correo."
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
        int paddingBottom = 25;
        //Tamaño de los campos de textos y botones
        int fieldHeight = 35;
        int fieldWidth = this.getWidth() - paddingStart * 2;
        Dimension fieldSize = new Dimension(fieldWidth, fieldHeight);

        //CAMPO DE TEXTO DEL CORREO
        int correoY = this.getHeight() / 2 - fieldHeight;
        int labelY = correoY - lblCorreo.getHeight() - 5;
        lblCorreo.setLocation(paddingStart, labelY);
        txtCorreo.setLocation(paddingStart, correoY);
        txtCorreo.setSize(fieldSize);

        //LABEL PARA LA INFORMACIÓN DEL PANEL
        String info = "<html><b>Ingrese su correo electrónico.</b>"
                + " Se le enviará un código de seguridad para "
                + "verificar el correo electrónico ingresado.</html>";
        lblInfo.setText(info);
        lblInfo.setVerticalAlignment(javax.swing.JLabel.TOP);
        lblInfo.setLocation(paddingStart, 0);
        lblInfo.setSize(fieldWidth, labelY);
        
        //LABEL PARA IR AL INICIO
        int iniciarY = this.getHeight() - paddingBottom - lblIniciar.getHeight();
        int iniciarX = middleX - (lblIniciar.getWidth() + btnIniciar.getWidth()) / 2;
        lblIniciar.setLocation(iniciarX, iniciarY);
        btnIniciar.setLocation(iniciarX + lblIniciar.getWidth(), iniciarY);
        btnIniciar.setToolTipText("Regresar al comienzo para iniciar sesión");

        //BOTÓN PARA RETROCEDER
        int btnY = iniciarY - fieldHeight - 5;
        btnVolver.setLocation(paddingStart, btnY);
        btnVolver.setSize(btnVolver.getPreferredSize().width + 30, fieldHeight);

        //BOTÓN PARA AVANZAR
        int enviarX = paddingStart + btnVolver.getWidth() + 10;
        int enviarWidth = this.getWidth() - paddingStart - enviarX;
        btnEnviar.setLocation(enviarX, btnY);
        btnEnviar.setSize(enviarWidth, fieldHeight);

        //Agregar los componentes
        this.add(lblInfo);
        this.add(lblCorreo);
        this.add(txtCorreo);
        this.add(btnVolver);
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
        txtCorreo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    enviarCodigo();
                }
            }
        });
        btnIniciar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Ir hacia el panel de inicio
                replacePanel(INICIO);
                replaceContainer(DATOS);
            }
        });
        btnEnviar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Enviar el código al correo
                enviarCodigo();
            }
        });
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Retroceder hacia el panel de los datos personales
                replaceContainer(DATOS);
            }
        });
    }

    //COMPONENTES
    private static final Label lblInfo = new Label("", PLANO, 14);
    private static final Label lblCorreo = new Label("Correo electrónico", PLANO, 16);
    private static final CampoTexto txtCorreo = new CampoTexto("Ingrese su correo", CORREO);
    private static final Boton btnEnviar = new Boton("Enviar código", CELESTE);
    private static final Boton btnVolver = new Boton("Volver", ROJO_OSCURO);
    private static final Label lblIniciar = new Label("¿Ya tienes tu cuenta? ", PLANO, 16);
    private static final Label btnIniciar = new Label("Inicia sesión", LINK, 16);
}
