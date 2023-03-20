package login;

import main.Run;
import components.Boton;
import components.CampoClave;
import components.CampoTexto;
import components.Label;
import components.Logo;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static javax.swing.SwingConstants.VERTICAL;
import static login.Frame.getParentSize;
import static login.Frame.replacePanel;
import static properties.Mensaje.msjError;

/**
 * Clase extendida del JPanel para representar el panel de inicio en el Login.
 *
 */
public final class Inicio extends JPanel implements properties.Colores, properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para verificar la existencia del usuario en la base de datos
     *
     * @return TRUE en caso de que exista coincidencia.
     */
    private boolean validarUsuario() {
        return true;
    }

    /**
     * Función para validar los datos y realizar el logeo
     */
    private void logear() {
        //Validar que los campos estén correctos
        if (validarCampos()) {

            //Validar que el usuario realmente exista
            //en la base de datos
            if (validarUsuario()) {

                //Iniciar el programa con el nombre del usuario y
                //su nivel de rol
                Run.iniciarPrograma(userDB, rolDB);

                //Vaciar los campos del inicio
                vaciarCampos();

                //Cerrar el login
                Run.cerrarLogin();
            }
        }
    }

    /**
     * Función para validar los campos de texto del inicio
     *
     * @return TRUE en caso de que estén correctos
     */
    private boolean validarCampos() {
        //Obtener el usuario
        userField = txtUsuario.getText().trim();

        //Obtener la contraseña
        passField = String.valueOf(txtClave.getPassword()).trim();

        //Validar que el usuario NO esté vacío
        if (!userField.isEmpty()) {

            //Validar que la contraseña NO esté vacío
            if (!passField.isEmpty()) {

                return true;

            } else {
                msjError(
                        "La contraseña no puede estár vacía.\n"
                        + "Por favor, ingrese sus datos."
                );
                //Enfocar la clave
                txtClave.requestFocus();
            }
        } else {
            msjError(
                    "El usuario no puede estár vacío.\n"
                    + "Por favor, ingrese sus datos."
            );
            //Enfocar el usuario
            txtUsuario.requestFocus();
        }

        //Siempre retornará false, si no retorna true antes.
        return false;
    }

    //ATRIBUTOS
    private static String userField, passField;
    private static String userDB = "DIEGO RUIZ";
    private static int rolDB = EMPLEADO;

    // ========== FRONTEND ==========
    /**
     * Constructor para el panel principal del inicio del Login.
     */
    public Inicio() {
        this.setLayout(null);
        this.setBackground(BLANCO);

        listeners();
    }

    /**
     * Función para inicializar, dimensionar, posicionar y agregar los
     * componentes de la clase al panel.
     */
    protected void initComponents() {
        this.setSize(getParentSize());

        //Obtener el punto medio del panel
        int middleX = this.getWidth() / 2;
        //Asignar el padding (margen interno) 
        int paddingStart = 40;
        int paddingEnd = this.getWidth() - paddingStart;
        int paddingTop = 20;
        int paddingBottom = 50;
        //Asignar la altura de los campos de texto
        int fieldWidth = this.getWidth() - paddingStart * 2;
        int fieldHeight = 40;

        //Obtener el punto medio del panel y restarle la mitad
        //del tamaño del label (para que quede centrado).
        int logoX = middleX - lblLogo.getWidth() / 2;
        lblLogo.setLocation(logoX, paddingTop);

        //Posicionar el label 30 pixeles debajo del logo
        int iniciarY = lblLogo.getY() + lblLogo.getHeight() + 40;
        lblIniciar.setLocation(paddingStart, iniciarY);

        //Posicionar el campo de texto 20 pixeles debajo del label Inicio
        int userY = lblIniciar.getY() + lblIniciar.getHeight() + 20;
        txtUsuario.setBounds(paddingStart, userY, fieldWidth, fieldHeight);

        //Posicionar el campo de contraseña 20 pixeles debajo del campo
        //de texto del usuario
        int passY = txtUsuario.getY() + txtUsuario.getHeight() + 20;
        txtClave.setBounds(paddingStart, passY, fieldWidth, fieldHeight);

        //posicionar el label de restauración en el lado derecho del panel
        int restX = paddingEnd - lblRecuperar.getWidth();
        //Posicionar el label de restauración 5 pixeles debajo del campo de contraseña
        int restY = txtClave.getY() + txtClave.getHeight() + 5;
        lblRecuperar.setLocation(restX, restY);
        lblRecuperar.setToolTipText("Dirigirse a la pestaña de recuperación de contraseña");

        //Posicionar el label y botón de registro en la parte inferior
        int registroY = this.getHeight() - paddingBottom - lblRegistro.getHeight();
        //Obtener el punto medio del panel con la suma del ancho del 
        //label de registro y el ancho botón de registro
        int registroX = middleX - (lblRegistro.getWidth() + btnRegistro.getWidth()) / 2;
        lblRegistro.setLocation(registroX, registroY);
        btnRegistro.setLocation(registroX + lblRegistro.getWidth(), registroY);
        btnRegistro.setToolTipText("Dirigirse a la pestaña para el registro de usuario");

        //Posicionar el botón por encima del label de registro
        int btnY = registroY - fieldHeight - 5;
        btnLogin.setBounds(paddingStart, btnY, fieldWidth, fieldHeight);

        //Añadir los componentes al panel
        this.add(lblLogo);
        this.add(lblIniciar);
        this.add(txtUsuario);
        this.add(txtClave);
        this.add(lblRecuperar);
        this.add(btnLogin);
        this.add(btnRegistro);
        this.add(lblRegistro);

    }

    /**
     * Función para asignar los listener a los componentes del inicio
     */
    private void listeners() {
        //Listener para el botón de login
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                logear();
            }
        });

        //Listener para el campo de usuario
        txtUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    txtClave.requestFocus();
                }
            }
        });

        //Listener para el campo de contraseña
        txtClave.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    logear();
                }
            }
        });

        //Listener para el botón de registro
        btnRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(REGISTRO);
            }
        });

        //Listener para el botón de recuperar contraseña
        lblRecuperar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(RECUPERACION);
            }
        });
    }

    /**
     * Función para vaciar los campos de textos del panel del inicio.
     */
    public void vaciarCampos() {
        txtUsuario.setText("");
        txtClave.setText("");
    }

    //COMPONENTES
    private static final Logo lblLogo = new Logo(VERTICAL);
    private static final Label lblIniciar = new Label("Iniciar Sesion", TITULO, 20);
    private static final CampoTexto txtUsuario = new CampoTexto("Cédula o correo", CORREO);
    private static final CampoClave txtClave = new CampoClave("Ingrese su contraseña");
    private static final Label lblRecuperar = new Label("¿Olvidaste tu contraseña?", LINK, 14);
    private static final Boton btnLogin = new Boton("Iniciar sesión", CELESTE);
    private static final Label lblRegistro = new Label("¿No tienes tu cuenta? ", PLANO, 16);
    private static final Label btnRegistro = new Label("Regístrate", LINK, 16);
}
