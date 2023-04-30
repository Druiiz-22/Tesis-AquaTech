package login.registro;

import components.Boton;
import components.CampoTexto;
import components.Label;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static login.Frame.replacePanel;
import static login.Registro.getContentSize;
import static login.Registro.replaceContainer;
import static login.Registro.setDatosPersonales;
import static properties.Mensaje.msjError;
import static properties.ValidarTexto.formatoNombre;
import static properties.ValidarTexto.formatoTelefono;

/**
 * Clase para la creación del registro de los datos personales del usuario.
 */
public class DatosPersonales extends javax.swing.JPanel implements properties.Colores, properties.Constantes {

    // ========== BACKEND ==========
    
    /**
     * Función para guardar los datos del usuario
     */
    private void guardarDatos() {
        //Validar los campos y sus datos
        if (validarCampos()) {
            if (validarDatos()) {

                //Guardar los datos
                setDatosPersonales(nombre, apellido, Integer.parseInt(cedula), telefono);

                //Avanzar hacia el correo
                replaceContainer(CORREO);
            }
        }
    }

    /**
     * Función para validar que los campos no estén vacíos
     *
     * @return TRUE en caso de que no estén vacíos
     */
    private boolean validarCampos() {
        String msj = "\nPor favor, ingrese sus datos.";
        nombre = txtNombre.getText().trim().toUpperCase();
        apellido = txtApellido.getText().trim().toUpperCase();
        telefono = txtTelefono.getText().trim();
        cedula = txtCedula.getText().trim();

        //Validar que los campos NO estén vacíos
        if (!nombre.isEmpty()) {
            if (!apellido.isEmpty()) {
                if (!telefono.isEmpty()) {
                    if (!cedula.isEmpty()) {

                        return true;

                    } else {
                        msj = "La cédula no puede estar vacío." + msj;
                    }
                } else {
                    msj = "El teléfono no puede estar vacío." + msj;
                }
            } else {
                msj = "El apellido no puede estar vacío." + msj;
            }
        } else {
            msj = "El nombre no puede estar vacío." + msj;
        }

        msjError(msj);

        return false;
    }

    /**
     * Función para comprobar que los datos ingresados sean válidos
     *
     * @return TRUE en caso de que los datos sean validos
     */
    private boolean validarDatos() {
        String msj = "\nPor favor, revise sus datos.";

        //Validar que cumpla con el formato de un nombre
        if (formatoNombre(nombre)) {
            if (formatoNombre(apellido)) {
                if (formatoTelefono(telefono)) {

                    //Validar que la cédula se pueda convertir a un entero
                    //y que esté dentro del rango correcto
                    try {
                        int ci = Integer.parseInt(cedula);
                        if (ci >= 1 && ci <= 99999999) {

                            return true;

                        } else {
                            throw new NumberFormatException();
                        }

                    } catch (NumberFormatException ex) {
                        msj = "El valor de la cédula es incorrecto.\nDebe estar entre 1 y 99.999.999";
                    }

                } else {
                    msj = "El teléfono ingresado no cumple con el\n"
                            + "formato de un número telefónico." + msj;
                }
            } else {
                msj = "El apellido solo puede llevar letras, entre 2 a 25." + msj;
            }
        } else {
            msj = "El nombre solo puede llevar letras, entre 2 a 25." + msj;
        }

        msjError(msj);

        return false;
    }

    //ATRIBUTOS
    private static String nombre, apellido, telefono, cedula;

    // ========== FRONTEND ==========
    
    /**
     * Constructor para la creación del panel de registro de los datos
     * personales.
     */
    public DatosPersonales() {
        this.setLayout(null);
        this.setOpaque(false);

        //listener de los componentes
        listeners();
    }

    /**
     * Función para iniciar los componentes.
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
        int fieldWidth = middleX - paddingStart - 10;
        Dimension fieldSize = new Dimension(fieldWidth, fieldHeight);

        //LABEL PARA LA INFORMACIÓN DEL PANEL
        String info = "<html><b>Ingrese sus datos personales.</b>"
                + " Tome en cuenta<br>que será identificado mediante su "
                + "cédula, por lo<br>que su usuario será único e irrepetible.</html>";
        lblInfo.setText(info);
        lblInfo.setLocation(paddingStart, 0);
        lblInfo.setSize(lblInfo.getPreferredSize());

        //CAMPOS DE TEXTO DEL PANEL
        //Localización del label y campo de texto del nombre
        int nombreY = lblInfo.getHeight() + 40;
        int txtNombreY = nombreY + lblNombre.getHeight() + 5;
        lblNombre.setLocation(paddingStart, nombreY);
        txtNombre.setLocation(paddingStart, txtNombreY);
        txtNombre.setSize(fieldSize);

        //Localización del label y campo de texto del apellido
        int apellidoX = middleX + 10;
        lblApellido.setLocation(apellidoX, nombreY);
        txtApellido.setLocation(apellidoX, txtNombreY);
        txtApellido.setSize(fieldSize);

        //Localización del label y campo de texto de la cédula
        int cedulaY = txtNombreY + txtNombre.getHeight() + 20;
        int txtCedulaY = cedulaY + lblCedula.getHeight() + 5;
        lblCedula.setLocation(paddingStart, cedulaY);
        txtCedula.setLocation(paddingStart, txtCedulaY);
        txtCedula.setSize(fieldSize);

        //localización del label y campo de texto del teléfono
        lblTelefono.setLocation(apellidoX, cedulaY);
        txtTelefono.setLocation(apellidoX, txtCedulaY);
        txtTelefono.setSize(fieldSize);

        //LABEL PARA RETROCEDER
        int iniciarY = this.getHeight() - paddingBottom - lblIniciar.getHeight();
        int iniciarX = middleX - (lblIniciar.getWidth() + btnIniciar.getWidth()) / 2;
        lblIniciar.setLocation(iniciarX, iniciarY);
        btnIniciar.setLocation(iniciarX + lblIniciar.getWidth(), iniciarY);
        btnIniciar.setToolTipText("Regresar al comienzo para iniciar sesión");

        //BOTON PARA AVANZAR
        int btnY = iniciarY - fieldHeight - 5;
        int btnWidth = this.getWidth() - paddingStart * 2;
        btnSiguiente.setBounds(paddingStart, btnY, btnWidth, fieldHeight);

        //Agregar los componentes
        this.add(lblInfo);
        this.add(lblNombre);
        this.add(txtNombre);
        this.add(lblApellido);
        this.add(txtApellido);
        this.add(lblCedula);
        this.add(txtCedula);
        this.add(lblTelefono);
        this.add(txtTelefono);
        this.add(btnSiguiente);
        this.add(lblIniciar);
        this.add(btnIniciar);
    }

    /**
     * Función que contiene los listener de los componentes del panel
     */
    private void listeners() {
        btnIniciar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Ir al inicio
                replacePanel(INICIO);
            }
        });

        btnSiguiente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guardarDatos();
            }
        });
    }

    /**
     * Función para vaciar los campos de textos del panel.
     */
    public void vaciarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
    }

    //COMPONENTES
    private static final Label lblInfo = new Label("", PLANO, 16);
    private static final Label lblNombre = new Label("Nombre", PLANO, 16);
    private static final CampoTexto txtNombre = new CampoTexto("Ingrese su nombre", NOMBRE);
    private static final Label lblApellido = new Label("Apellido", PLANO, 16);
    private static final CampoTexto txtApellido = new CampoTexto("Ingrese su apellido", NOMBRE);
    private static final Label lblCedula = new Label("Cédula", PLANO, 16);
    private static final CampoTexto txtCedula = new CampoTexto("Ingrese su cédula", NUMERO);
    private static final Label lblTelefono = new Label("Teléfono", PLANO, 16);
    private static final CampoTexto txtTelefono = new CampoTexto("Ingrese su teléfono", NUMERO);
    private static final Boton btnSiguiente = new Boton("Siguiente", CELESTE);
    private static final Label lblIniciar = new Label("¿Ya tienes tu cuenta? ", PLANO, 16);
    private static final Label btnIniciar = new Label("Inicia sesión", LINK, 16);
}
