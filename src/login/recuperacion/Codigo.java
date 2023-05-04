package login.recuperacion;

import components.Boton;
import components.CampoTexto;
import components.Label;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import static login.Recuperacion.getContentSize;
import static login.Recuperacion.replaceContainer;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjInformativo;

/**
 * Clase para el panel de ingreso del código del usuario
 */
public class Codigo extends javax.swing.JPanel implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    /**
     * Función para comprobar el código y cambiar el panel
     */
    private void verificarCodigo() {
        //Validar que el campo no esté vacío y 
        //la coincidencia del código de seguridad
        if (validarFechas()) {
            if (validarCampo()) {

                msjInformativo("El código se verificó con éxito.");

                //Avanzar a la pestaña del cambio de contraseña
                replaceContainer(CLAVE);
            }
        } else {
            //Retroceder hacia el panel de los datos personales
            replaceContainer(CORREO);
            vaciarCampos();
        }

    }

    /**
     * Función para validar el campo de código
     *
     * @return TRUE en caso de que no esté vacío el campo
     */
    private boolean validarCampo() {

        codigoUsuario = txtCodigo.getText().trim();

        //Validar que el campo NO esté vacío
        if (!codigoUsuario.isEmpty()) {

            //Validar la coincidencia con el código de seguridad generado
            if (codigoUsuario.equals(String.valueOf(codigoSeguridad))) {

                return true;

            } else {
                msjError("El código ingresado es inválido."
                        + "\nPor favor, revise sus datos.");
            }
        } else {
            msjError("El código no puede estár vacío.\n"
                    + "Por favor, ingrese el código.");
        }

        txtCodigo.requestFocus();
        //Retornar falso, en caso de no retornar true antes.
        return false;
    }

    /**
     * Función para validar la fecha actual con la fecha de salida del código y
     * el tiempo de expiración del mismo.
     *
     * @return
     */
    private boolean validarFechas() {
        //Obtener la fecha actual
        Calendar fechaActual = new java.util.GregorianCalendar();
        fechaActual.setTime(new java.util.Date());

        //Validar que la fecha actual NO sea menor a la fecha de salida del código
        if (fechaActual.compareTo(fechaSalida) >= 0) {
            //Validar que la fecha actual no supere a la fecha de expiración
            if (fechaActual.compareTo(fechaExpiracion) < 0) {

                return true;

            } else {
                msjError("El código ha superado el tiempo de expiracion (30 min)."
                        + "\nPor favor, vuelva a solicitar un nuevo código.");
            }
        } else {
            msjError("La fecha actual es menor a la fecha de generación de "
                    + "código.\nPor favor, ajuste su calendario a la fecha actual.");
        }

        return false;
    }

    //ATRIBUTO
    private static String codigoUsuario;
    private static int codigoSeguridad;
    private static Calendar fechaSalida;
    private static Calendar fechaExpiracion;

    // ========== FRONTEND ==========
    /**
     * Constructor para la creación del panel para validar el código de
     * seguridad.
     */
    public Codigo() {
        this.setLayout(null);
        this.setOpaque(false);

        //Listener de los botones
        listeners();
    }

    /**
     * Función para iniciarlizar los componentes.
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
        int codigoY = this.getHeight() / 2 - fieldHeight;
        int labelY = codigoY - lblCodigo.getHeight() - 5;
        lblCodigo.setLocation(paddingStart, labelY);
        txtCodigo.setLocation(paddingStart, codigoY);
        txtCodigo.setSize(fieldSize);

        //LABEL PARA LA INFORMACIÓN DEL PANEL
        lblInfo.setLocation(paddingStart, 0);
        lblInfo.setSize(fieldWidth, labelY);
        lblInfo.setVerticalAlignment(javax.swing.JLabel.TOP);

        //LABEL PARA RETROCEDER
        int iniciarY = this.getHeight() - paddingBottom - lblReenviar.getHeight();
        int iniciarX = middleX - (lblReenviar.getWidth() + btnReenviar.getWidth()) / 2;
        lblReenviar.setLocation(iniciarX, iniciarY);
        btnReenviar.setLocation(iniciarX + lblReenviar.getWidth(), iniciarY);
        btnReenviar.setToolTipText("Regresar hacia atrás para verificar el correo y reenviar el código");

        //BOTÓN PARA AVANZAR
        int btnY = iniciarY - fieldHeight - 5;
        btnConfirmar.setLocation(paddingStart, btnY);
        btnConfirmar.setSize(fieldSize);

        //Agregar los componentes
        this.add(lblInfo);
        this.add(lblCodigo);
        this.add(txtCodigo);
        this.add(btnConfirmar);
        this.add(lblReenviar);
        this.add(btnReenviar);
    }

    /**
     * Función para vaciar los campos de textos del panel.
     */
    public void vaciarCampos() {
        txtCodigo.setText("");
    }

    /**
     * Función que contiene los listener de los componentes del panel.
     */
    private void listeners() {
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    verificarCodigo();
                }
            }
        });
        btnReenviar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Retroceder hacia el panel de los datos personales
                replaceContainer(CORREO);
                vaciarCampos();
            }
        });
        btnConfirmar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                verificarCodigo();
            }
        });
    }

    /**
     * Función para asignar el correo ingresado por el usuario, el código de
     * seguridad enviado y la fecha de expiración del código.
     *
     * @param correo Correo electrónico.
     * @param codigoSeguridad Código de seguridad enviado.
     * @param fechaSalida
     * @param fechaExpiracion
     */
    public static void setDatos(String correo, int codigoSeguridad, Calendar fechaSalida, Calendar fechaExpiracion) {
        Codigo.codigoSeguridad = codigoSeguridad;
        Codigo.fechaSalida = fechaSalida;
        Codigo.fechaExpiracion = fechaExpiracion;
        
        String info
                = "<html>"
                + "<p><b>Se envió un código al correo:</b></p>"
                + "<p><i>" + correo + "</i></p>"
                + "<p style='margin-top:5px;'>"
                + "Ingrese el código de seguridad enviado para confirmar su "
                + "correo y restaurar su contraseña."
                + "</p>"
                + "</html>";
        lblInfo.setText(info);
    }

    //COMPONENTES
    private static final Label lblInfo = new Label("", PLANO, 14);
    private static final Label lblCodigo = new Label("Código de seguridad", PLANO, 15);
    private static final CampoTexto txtCodigo = new CampoTexto("Ingrese el código", NUMERO);
    private static final Boton btnConfirmar = new Boton("Confirmar código", CELESTE);
    private static final Label lblReenviar = new Label("¿No recibió el código? ", PLANO, 16);
    private static final Label btnReenviar = new Label("Reenviar código", LINK, 16);
}
