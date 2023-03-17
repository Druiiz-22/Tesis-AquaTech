package login.recuperacion;

import components.Boton;
import components.CampoClave;
import components.CampoTexto;
import components.Label;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static javax.swing.JOptionPane.YES_OPTION;
import static login.Frame.replacePanel;
import static login.Recuperacion.getContentSize;
import static login.Recuperacion.replaceContainer;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjInformativo;

/**
 * Clase para el panel de ingreso de la nueva contraseña del usuario
 */
public class ClaveNueva extends javax.swing.JPanel implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    
    /**
     * Función para guardar la clave nueva
     */
    private void cambiarClave() {
        if (validarCampos()) {

            //Mensaje de éxito
            msjInformativo("La contraseña ha sido cambiada exitosamente.");

            //Volver al inicio
            replacePanel(INICIO);
            replaceContainer(CORREO);
        }
    }

    /**
     * Función para validar los campos y la coincidencia entre ambos
     *
     * @return TRUE en caso de que los campos estén correctos
     */
    private boolean validarCampos() {

        //Obtener la contraseña nueva y repetida
        char nueva[] = txtNueva.getPassword();
        char repetida[] = txtRepetida.getPassword();

        //Validar que ningún campo esté vacío
        if (nueva.length > 0) {

            if (repetida.length > 0) {

                //Convertir la contraseña en hashCode
                int claveNueva = String.valueOf(nueva).hashCode();

                //Convertir la contraseña en hashCode
                claveRepetida = String.valueOf(repetida).hashCode();

                //Validar que las contraseñas sean iguales
                if (claveNueva == claveRepetida) {

                    return true;

                } else {
                    msjError(
                            "Las claves no coindicen.\n"
                            + "Por favor, revise sus datos."
                    );
                }
            } else {
                msjError(
                        "La clave repetida no puede estár vacío.\n"
                        + "Por favor, revise sus datos."
                );
                txtRepetida.requestFocus();
            }
        } else {
            msjError(
                    "La clave nueva no puede estár vacío.\n"
                    + "Por favor, revise sus datos."
            );
            txtNueva.requestFocus();
        }

        return false;
    }

    //ATRIBUTOS
    private static int claveRepetida;

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
        int paddingBottom = 50;
        //Tamaño de los campos de textos y botones
        int fieldHeight = 40;
        int fieldWidth = this.getWidth() - paddingStart * 2;
        Dimension fieldSize = new Dimension(fieldWidth, fieldHeight);

        //LABEL PARA LA INFORMACIÓN DEL PANEL
        String info = "<html><b>Ingrese la nueva contraseña.</b>"
                + " En caso de<br>perderla, podrá recuperarla mediante "
                + "su correo<br>electrónico.</html>";
        lblInfo.setText(info);
        lblInfo.setLocation(paddingStart, 0);
        lblInfo.setSize(lblInfo.getPreferredSize());

        //CAMPOS DE TEXTO DEL PANEL
        //Localización del label y campo de texto del nombre
        int nuevaY = lblInfo.getHeight() + 40;
        int txtNuevaY = nuevaY + lblNueva.getHeight() + 5;
        lblNueva.setLocation(paddingStart, nuevaY);
        txtNueva.setLocation(paddingStart, txtNuevaY);
        txtNueva.setSize(fieldSize);

        //Localización del label y campo de texto de la cédula
        int repetidaY = txtNuevaY + txtNueva.getHeight() + 20;
        int txtRepetidaY = repetidaY + lblRepetida.getHeight() + 5;
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
    }

    /**
     * Función que contiene los listener de los componentes del panel
     */
    private void listeners() {
        btnIniciar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String mensaje = "¿Está seguro de que quiere volver?\n"
                        + "Perderá todo el progreso que ha realizado.";

                //Mostrar mensaje para afirmar que se quiere devolver al 
                //inicio y borrar todo el progreso que ha realizado
                if (msjYesNo(mensaje) == YES_OPTION) {
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

    //COMPONENTES
    private static final Label lblInfo = new Label("", NORMAL, 16);
    private static final Label lblNueva = new Label("Contraseña nueva", NORMAL, 16);
    private static final CampoClave txtNueva = new CampoClave("Ingrese su contraseña");
    private static final Label lblRepetida = new Label("Repita su contraseña", NORMAL, 16);
    private static final CampoClave txtRepetida = new CampoClave("Ingrese su contraseña");
    private static final Boton btnCambiar = new Boton("Cambiar Contraseña", CELESTE);
    private static final Label lblIniciar = new Label("¿Ya recuperaste tu contraseña? ", NORMAL, 16);
    private static final Label btnIniciar = new Label("Inicia sesión", LINK, 16);
}
