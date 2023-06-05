package login.recuperacion;

import components.Boton;
import components.CampoClave;
import components.Label;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import login.Frame;
import static login.Frame.replacePanel;
import login.Recuperacion;
import static login.Recuperacion.getContentSize;
import static login.Recuperacion.replaceContainer;
import properties.Encript;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;

/**
 * Clase para el panel de ingreso de la nueva contraseña del usuario
 */
public class ClaveNueva extends javax.swing.JPanel implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    /**
     * Función para guardar la clave nueva
     */
    private void cambiarClave() {
        new Thread(){
            @Override
            public void run() {
                Frame.openGlass(true);
                if (validarCampos()) {                    
                    //Guardar la contraseña del usuario
                    Recuperacion.setClaveNueva(String.valueOf(claveRepetida));

                    //Crear el usuario
                    Recuperacion.cambiarClave();
                    
                    //Cerrar el GlassPane
                    Frame.openGlass(false);
                }
            }
        }.start();
        
    }

    /**
     * Función para validar los campos y la coincidencia entre ambos
     *
     * @return TRUE en caso de que los campos estén correctos
     */
    private boolean validarCampos() {

        String msj;
        
        //Obtener la contraseña nueva y repetida
        char nueva[] = txtNueva.getPassword();
        char repetida[] = txtRepetida.getPassword();

        //Validar que ningún campo tenga menos de 8 carácteres
        if (nueva.length >= 8) {
            if (repetida.length >= 8) {

                //Convertir la contraseña en hashCode
                String claveNueva = Encript.encriptar(nueva);

                //Convertir la contraseña en hashCode
                claveRepetida = Encript.encriptar(repetida);

                //Validar que las contraseñas sean iguales
                if (claveNueva.equals(claveRepetida)) {

                    return true;

                } else {
                    msj = "Las claves no coindicen.\nPor favor, revise sus datos.";
                }
            } else {
                msj = "La clave repetida debe tener mínimo 8 carácteres.\n"
                        + "Por favor, revise sus datos.";
            }
        } else {
            msj = "La clave nueva debe tener mínimo 8 carácteres.\nPor favor, "
                    + "revise sus datos.";
        }

        //Cerrar el glassPane
        Frame.openGlass(false);
        //Mostrar mensaje de error
        msjError(msj);
        
        return false;
    }

    //ATRIBUTOS
    private static String claveRepetida;

    // ========== FRONTEND ==========
    /**
     * Constructor para la creación del panel para la clave nueva del usuario.
     */
    public ClaveNueva() {
        this.setLayout(null);
        this.setOpaque(false);

        //Asignar los listener a los componentes
        listeners();
    }

    /**
     * Función para iniciarlizar los componentes
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
        
        //LABEL PARA LA INFORMACIÓN DEL PANEL
        String info = "<html><b>Ingrese la nueva contraseña.</b>"
                + " En caso de perderla,<br>podrá recuperarla mediante "
                + "su correo electrónico.</html>";
        lblInfo.setText(info);
        lblInfo.setVerticalAlignment(javax.swing.JLabel.TOP);
        lblInfo.setLocation(paddingStart, 0);
        lblInfo.setSize(fieldWidth, lblInfo.getPreferredSize().height);
        
        //CAMPOS DE TEXTO DEL PANEL
        //Localización del label y campo de texto del nombre        
        int nuevaY = lblInfo.getHeight() + 40;
        int txtNuevaY = nuevaY + lblNueva.getHeight() + 2;
        lblNueva.setLocation(paddingStart, nuevaY);
        txtNueva.setLocation(paddingStart, txtNuevaY);
        txtNueva.setSize(fieldSize);

        //Localización del label y campo de texto de la cédula
        int repetidaY = txtNuevaY + fieldHeight + 10;
        int txtRepetidaY = repetidaY + lblRepetida.getHeight() + 2;
        lblRepetida.setLocation(paddingStart, repetidaY);
        txtRepetida.setLocation(paddingStart, txtRepetidaY);
        txtRepetida.setSize(fieldSize);

        //LABEL PARA RETROCEDER
        int iniciarY = this.getHeight() - paddingBottom - lblIniciar.getHeight();
        int iniciarX = middleX - (lblIniciar.getWidth() + btnIniciar.getWidth()) / 2;
        lblIniciar.setLocation(iniciarX, iniciarY);
        btnIniciar.setLocation(iniciarX + lblIniciar.getWidth(), iniciarY);
        btnIniciar.setToolTipText("Regresar al comienzo para iniciar sesión");

        //BOTÓN PARA AVANZAR
        int btnWidth = this.getWidth() - paddingStart * 2;
        int btnY = iniciarY - fieldHeight - 5;
        btnCambiar.setBounds(paddingStart, btnY, btnWidth, fieldHeight);

        //Agregar los componentes
        this.add(lblInfo);
        this.add(lblNueva);
        this.add(txtNueva);
        this.add(lblRepetida);
        this.add(txtRepetida);
        this.add(btnCambiar);
        this.add(lblIniciar);
        this.add(btnIniciar);
    }

    /**
     * Función para vaciar los campos de textos del panel
     */
    public void vaciarCampos() {
        txtNueva.setText("");
        txtRepetida.setText("");
        claveRepetida = null;
    }

    /**
     * Función que contiene los listener de los componentes del panel
     */
    private void listeners() {
        txtNueva.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    txtRepetida.requestFocus();
                }
            }
        });
        txtRepetida.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    cambiarClave();
                }
            }
        });
        btnIniciar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String mensaje = "¿Está seguro de que quiere volver?\n"
                        + "Perderá todo el progreso que ha realizado.";

                //Mostrar mensaje para afirmar que se quiere devolver al 
                //inicio y borrar todo el progreso que ha realizado
                if (msjYesNo(mensaje)) {
                    replacePanel(INICIO);
                    replaceContainer(CORREO);
                }
            }
        });
        btnCambiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                cambiarClave();
            }
        });
    }

    public void habilitarComponentes(boolean estado){
        txtNueva.setEnabled(estado);
        txtRepetida.setEnabled(estado);
    }
    
    //COMPONENTES
    private static final Label lblInfo = new Label("", PLANO, 14);
    private static final Label lblNueva = new Label("Contraseña nueva", PLANO, 16);
    private static final CampoClave txtNueva = new CampoClave("Ingrese su contraseña");
    private static final Label lblRepetida = new Label("Repita su contraseña", PLANO, 16);
    private static final CampoClave txtRepetida = new CampoClave("Ingrese su contraseña");
    private static final Boton btnCambiar = new Boton("Cambiar Contraseña", CELESTE);
    private static final Label lblIniciar = new Label("¿Ya recuperaste tu contraseña? ", PLANO, 16);
    private static final Label btnIniciar = new Label("Inicia sesión", LINK, 16);
}
