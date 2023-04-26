package login.registro;

import components.Boton;
import components.CampoTexto;
import components.Label;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static login.Registro.getContentSize;
import static login.Registro.replaceContainer;
import properties.Mensaje;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjInformativo;

/**
 * Clase para la creación del panel del registro del código de seguridad del
 * usuario.
 */
public class Codigo extends javax.swing.JPanel implements properties.Colores, properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para comprobar el código y cambiar el panel
     */
    private void verificarCodigo() {
        //Validar que el campo no esté vacío y 
        //la coincidencia del código de seguridad
        if (validarCampo()) {

            msjInformativo("El código se verificó con éxito.");
            
            //Avanzar a la pestaña del cambio de contraseña
            replaceContainer(CLAVE);
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
            if(codigoUsuario.equals(String.valueOf(codigoSeguridad))){
                
                return true;
                
            } else {
                msjError("El código ingresado es inválido."
                        + "\nPor favor, revise sus datos.");
            }
        } else {
            msjError("El código no puede estár vacío.\nPor favor, ingrese "
                    + "el código de seguridad.");
        }

        txtCodigo.requestFocus();
        //Retornar falso, en caso de no retornar true antes.
        return false;
    }

    //ATRIBUTO
    private static String codigoUsuario;
    private static int codigoSeguridad;

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
        int paddingBottom = 50;
        //Tamaño de los campos de textos y botones
        int fieldHeight = 40;
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
        lblInfo.setSize(txtCodigo.getWidth(), lblCodigo.getY());
        lblInfo.setVerticalAlignment(javax.swing.JLabel.TOP);

        //LABEL PARA RETROCEDER
        int iniciarY = this.getHeight() - paddingBottom - lblReenviar.getHeight();
        int iniciarX = middleX - (lblReenviar.getWidth() + btnReenviar.getWidth()) / 2;
        lblReenviar.setLocation(iniciarX, iniciarY);
        btnReenviar.setLocation(iniciarX + lblReenviar.getWidth(), iniciarY);
        btnReenviar.setToolTipText("Regresar hacia atrás para verificar el correo y reenviar el código");

        //BOTÓN PARA RETROCEDER
        int btnY = iniciarY - fieldHeight - 5;
        btnVolver.setLocation(paddingStart, btnY);
        btnVolver.setSize(btnVolver.getPreferredSize().width + 30, fieldHeight);

        //BOTÓN PARA AVANZAR
        int enviarX = paddingStart + btnVolver.getWidth() + 10;
        int enviarWidth = this.getWidth() - paddingStart - enviarX;
        btnConfirmar.setLocation(enviarX, btnY);
        btnConfirmar.setSize(enviarWidth, fieldHeight);

        //Agregar los componentes
        this.add(lblInfo);
        this.add(lblCodigo);
        this.add(txtCodigo);
        this.add(btnVolver);
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
                //Avanzar hacia el panel del la contraseña nueva
                verificarCodigo();
            }
        });

        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Retroceder hacia el panel de los datos personales
                replaceContainer(CORREO);
                vaciarCampos();
            }
        });
    }

    /**
     * Función para asignar el correo ingresado por el usuario y el código de 
     * seguridad enviado al correo.
     *
     * @param correo Correo electrónico
     * @param codigoSeguridad Código de seguridad enviado
     */
    public static void setCorreo(String correo, int codigoSeguridad) {
        Codigo.codigoSeguridad = codigoSeguridad;
        
        String info
                = "<html>"
                + "<p><b>Se envió un código al correo:</b></p>"
                + "<p><i>" + correo + "</i></p>"
                + "<p style='margin-top:5px;'>"
                + "Ingrese el código enviado para confirmar su<br>correo. Si no "
                + "encuentra el correo, revise en su<br>bandeja de spam o no deseado."
                + "</p>"
                + "</html>";
        lblInfo.setText(info);        
    }

    //COMPONENTES
    private static final Label lblInfo = new Label("", PLANO, 16);
    private static final Label lblCodigo = new Label("Código de seguridad", PLANO, 15);
    private static final CampoTexto txtCodigo = new CampoTexto("Ingrese el código", CUALQUIER);
    private static final Boton btnConfirmar = new Boton("Confirmar código", CELESTE);
    private static final Boton btnVolver = new Boton("Volver", ROJO_OSCURO);
    private static final Label lblReenviar = new Label("¿No recibió el código? ", PLANO, 16);
    private static final Label btnReenviar = new Label("Reenviar código", LINK, 16);
}
