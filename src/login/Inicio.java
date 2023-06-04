package login;

import main.Run;
import components.Boton;
import components.CampoClave;
import components.CampoTexto;
import components.Label;
import components.Logo;
import database.ReadDB;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static javax.swing.SwingConstants.VERTICAL;
import static login.Frame.getParentSize;
import static login.Frame.replacePanel;
import properties.Encript;
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
        //Validar la existencia del usuario
        Object[] cuenta = ReadDB.getUser(identificacion, clave);

        //Comprobar la existencia del usuario
        if (cuenta != null) {

            Inicio.nombre = cuenta[1].toString();
            Inicio.rol = Integer.parseInt(cuenta[0].toString());
            Inicio.empleado_sucursal = Integer.parseInt(cuenta[2].toString());

            //Validar que el usuario tenga los permisos para acceder al software
            switch (rol) {
                case EMPLEADO:
                case OPERADOR:
                case ADMINISTRADOR:
                    return true;
                    
                default:
                    msjError(
                            "Usted no cuenta con los permisos necesarios para "
                                    + "acceder al programa."
                    );
                    break;
            }
        }

        //Sumar un intento fallido
        intentos++;

        if (intentos == 3) {
            //Mostrar un mensaje de error
            msjError(
                    "Ha superado el límite de intentos de inicio.\n"
                    + "El programa se cerrará."
            );

            //Cerrar la ventana del login
            Run.cerrarLogin();

            //Terminar de ejecutar el programa
            System.exit(0);
        }

        //Cerrar el GlassPane
        Frame.openGlass(false);

        return false;
    }

    /**
     * Función para validar los datos y realizar el logeo
     */
    private void logear() {
        new Thread() {
            @Override
            public void run() {
                Frame.openGlass(true);
                //Validar que los campos estén correctos
                if (validarCampos()) {
                    //Validar que el usuario realmente exista
                    //en la base de datos
                    if (validarUsuario()) {
                        //Obtener las sucursales de la franquicia
                        Object[][] sucursales = database.ReadDB.getSucursales();
                        
                        //Validar que se hayan obtenido
                        if(sucursales != null && sucursales.length > 0){
                            //Cerrar el login
                            Run.cerrarLogin();

                            //Vaciar los campos del inicio
                            vaciarCampos();
                            
                            //Abrir el splash screen
                            IniciarPrograma iniciar = new IniciarPrograma(identificacion, rol, nombre, sucursales, empleado_sucursal);
                            
                        } else {
                            msjError("No se pudo realizar la conexión con la base"
                                    + "de datos.\nPor favor, intentelo más tarde.");
                        }
                    }
                }
                //Cerrar el glasPane
                Frame.openGlass(false);

                //Determinar que ya NO se está logeando
                logeando = false;
            }
        }.start();
    }

    /**
     * Función para validar los campos de texto del inicio
     *
     * @return TRUE en caso de que estén correctos
     */
    private boolean validarCampos() {
        //Mensaje de error
        String msj;

        //Obtener el usuario
        identificacion = txtUsuario.getText().trim().toUpperCase();

        //Encriptar la contraseña
        clave = Encript.encriptar(txtClave.getPassword());
        
        //Validar que el usuario NO esté vacío
        if (!identificacion.isEmpty()) {

            //Validar que la contraseña NO esté vacío
            if (!clave.isEmpty()) {

                return true;

            } else {
                //Mensaje de error
                msj = "La contraseña no puede estár vacía.\nPor favor, ingrese sus datos.";
            }
        } else {
            //Mensaje de error
            msj = "El usuario no puede estár vacío.\nPor favor, ingrese sus datos.";
        }

        //Cerrar el GlassPane
        Frame.openGlass(false);
        //Mostrar mensaje de error
        msjError(msj);

        //Siempre retornará false, si no retorna true antes.
        return false;
    }

    //ATRIBUTOS
    private static int intentos = 0;
    private static String identificacion;
    private static String nombre;
    private static String clave;
    private static int empleado_sucursal;
    private static int rol;
    private static boolean logeando = false;

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
        int paddingBottom = 25;
        //Asignar la altura de los campos de texto
        int fieldWidth = this.getWidth() - paddingStart * 2;
        int fieldHeight = 35;

        //Obtener el punto medio del panel y restarle la mitad
        //del tamaño del label (para que quede centrado).
        int logoX = middleX - lblLogo.getWidth() / 2;
        lblLogo.setLocation(logoX, paddingTop);

        //Posicionar el label 15 pixeles debajo del logo
        int iniciarY = lblLogo.getY() + lblLogo.getHeight() + 15;
        lblIniciar.setLocation(paddingStart, iniciarY);

        //Posicionar el campo de texto 20 pixeles debajo del label Inicio
        int userY = iniciarY + lblIniciar.getHeight() + 15;
        txtUsuario.setBounds(paddingStart, userY, fieldWidth, fieldHeight);

        //Posicionar el campo de contraseña 20 pixeles debajo del campo
        //de texto del usuario
        int passY = userY + fieldHeight + 20;
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
                //Validar que se haya presionado la tecla enter y que NO se
                //esté logeando al momento de presionar la tecla (para evitar
                //peticones masivas)
                if (e.getKeyChar() == TECLA_ENTER && !logeando) {
                    logeando = true;
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
        txtClave.hidePassword();
    }

    protected void habilitarComponentes(boolean estado) {
        txtUsuario.setEnabled(estado);
        txtClave.setEnabled(estado);
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
