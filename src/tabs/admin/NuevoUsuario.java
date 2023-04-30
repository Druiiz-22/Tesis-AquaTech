package tabs.admin;

import components.Boton;
import components.CampoClave;
import components.CampoTexto;
import components.Label;
import components.Logo;
import database.AdminDB;
import database.CreateDB;
import database.EmailCode;
import database.ReadDB;
import database.UpdateDB;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import static javax.swing.SwingConstants.HORIZONTAL;
import static properties.Mensaje.msjAdvertencia;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjInformativo;
import static properties.ValidarTexto.formatoCorreo;
import static properties.ValidarTexto.formatoNombre;
import static properties.ValidarTexto.formatoTelefono;

/**
 *
 * @author diego
 */
public class NuevoUsuario extends JFrame implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    /**
     * Función para agregar los listeners a los componentes
     */
    private void listeners() {
        //BOTONES
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (crearUsuario) {
                    crear();
                } else {
                    actualizar();
                }
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                salir();
            }
        });

        //WINDOW LISTENER
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir();
            }
        });
    }

    /**
     * Función para crear un nuevo usuario en la base de datos
     */
    private void crear() {
        if (validarCampos()) {
            if (validarDatos()) {
                //Mensaje de confirmación
                if (msjYesNo("¿Está seguro de realizar el registro del nuevo usuario?")) {
                    //Confirmar cuenta de administrador
                    if (AdminDB.validateAdminUser()) {
                        //Enviar el código de seguridad al correo
                        if (codigoSeguridad()) {
                            //Intentar crear el cliente en la base de datos
                            if (CreateDB.createUsuario(cedula, nombre, apellido, telefono, correo, claveFinal, rol)) {

                                //Ya que puede dar muchos problemas el alterar o agregar
                                //un dato a una tabla (y no a su Model), la forma de 
                                //visualizar los cambios será actualizando los datos 
                                //de la tabla con la base de datos
                                Usuarios.actualizarDatos();

                                msjInformativo("Se creó el nuevo usuario con éxito.");

                                dispose();
                                vaciarCampos();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Función para actualizar un usuario en la base de datos
     */
    private void actualizar() {
        //Validar la existencia del proveedor
        if (validarUsuario()) {
            //Validar que los campos no estén vacíos
            if (validarCampos()) {
                //Validar que los datos estén correctos
                if (validarDatos()) {
                    //Mensaje de confirmación
                    if (msjYesNo("¿Está seguro de actualizar los datos del usuario?")) {
                        //Confirmar cuenta de administrador 
                        if (AdminDB.validateAdminUser()) {
                            //Confirmar código de seguridad
                            if (codigoSeguridad()) {
                                //Intentar editar el proveedor en la base de datos
                                if (UpdateDB.updateUsuario(id_usuario, id_cliente, cedula, nombre, apellido, telefono, correo, rol)) {

                                    //Ya que puede dar muchos problemas el alterar o agregar
                                    //un dato a una tabla (y no a su Model), la forma de 
                                    //visualizar los cambios será actualizando los datos 
                                    //de la tabla con la base de datos
                                    Usuarios.actualizarDatos();

                                    msjInformativo("Se actualizaron los datos del usuario con éxito.");

                                    dispose();
                                    vaciarCampos();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Función para validar que los campos NO estén vacíos
     *
     * @return TRUE si los campos no están vacíos
     */
    private boolean validarCampos() {

        String msj = "\nPor favor, ingrese los datos.";

        String ci = txtCedula.getText().trim();
        correo = txtCorreo.getText().trim().toUpperCase();
        nombre = txtNombre.getText().trim().toUpperCase();
        apellido = txtApellido.getText().trim().toUpperCase();
        telefono = txtTelefono.getText().trim();
        rol = boxRoles.getSelectedIndex();

        //Validar que los campos NO estén vacíos
        if (correo.isEmpty()) {
            msj = "El correo no puede estár vacío." + msj;
        } else if (nombre.isEmpty()) {
            msj = "El nombre no puede estár vacío." + msj;

        } else if (apellido.isEmpty()) {
            msj = "El apellido no puede estár vacío." + msj;

        } else if (telefono.isEmpty()) {
            msj = "El teléfono no puede estár vacío." + msj;

        } else if (ci.isEmpty()) {
            msj = "La cédula no puede estár vacía." + msj;

        } else if (rol <= 0) {
            msj = "Debe seleccionar un rol para el usuario." + msj;

        } else if (crearUsuario) {

            //Validar las claves
            String nueva = String.valueOf(txtClave.getPassword());
            if (!nueva.isEmpty()) {

                String nuevaRep = String.valueOf(txtClaveRepetida.getPassword());
                if (!nuevaRep.isEmpty()) {

                    return true;

                } else {
                    msj = "La contraseña repetida no puede estár vacía." + msj;
                }
            } else {
                msj = "La contraseña nueva no puede estár vacía." + msj;
            }

        } else {
            //Si todos los campos (excepto las claves) tienen datos y NO 
            //se está creando un usuario nuevo, se retorna true, ya que 
            //no se validan las claves
            return true;
        }

        msjError(msj);

        return false;
    }

    /**
     * Función para comprobar que los datos ingresados sean válidos
     *
     * @return TRUE en caso de que los datos sean válidos
     */
    private boolean validarDatos() {
        String msj = "\nPor favor, revise sus datos.";

        //Validar lso formatos de los campos
        if (!formatoCorreo(correo)) {
            msj = "El correo ingresado es inválido." + msj;

        } else if (rol < 1 || rol > 3) {
            msj = "El rol seleccionado es inválido." + msj;

        } else if (!formatoNombre(nombre)) {
            msj = "El nombre solo puede llevar letras, entre 2 a 25." + msj;

        } else if (!formatoNombre(apellido)) {
            msj = "El apellido solo puede llevar letras, entre 2 a 25." + msj;

        } else if (formatoTelefono(telefono)) {
            msj = "El teléfono ingresado no cumple con el\n"
                    + "formato de un número telefónico." + msj;
        } else {
            try {
                //Validar que la cédula se pueda convertir a un entero y esté
                //dentro del rango correcto
                cedula = Integer.parseInt(txtCedula.getText());
                if (cedula >= 1 && cedula <= 99999999) {

                    //Validar si se está creando un usuario o editando
                    if (crearUsuario) {

                        //Validar que las contraseñas tengan mínimo 8 carácteres
                        String nueva = String.valueOf(txtClave.getPassword());
                        String nuevaRep = String.valueOf(txtClaveRepetida.getPassword());

                        if (nueva.length() >= 8 && nuevaRep.length() >= 8) {

                            //Validar que las contraseñas sean las mismas
                            //estando convertidas en HashCode
                            int nuevaCode = nueva.hashCode();
                            int nuevaCodeRep = nuevaRep.hashCode();

                            if (nuevaCode == nuevaCodeRep) {
                                //Guardar la clave nueva
                                claveFinal = String.valueOf(nuevaCodeRep);

                                return true;

                            } else {
                                msj = "Las claves no coinciden." + msj;
                            }
                        } else {
                            msj = "Las claves deben tener mínimo 8 carácteres." + msj;
                        }
                    } else {
                        //Si todos los campos (excepto las claves) tienen datos 
                        //y NO se está creando un usuario nuevo, se retorna true,
                        //ya que no se validan las claves
                        return true;
                    }
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                msj = "El valor de la cédula es incorrecto.\nDebe estar entre 1 y 99.999.999";
            }
        }

        msjError(msj);

        return false;
    }

    /**
     * Función para validar la existencia del usuario en la base de datos
     * mediante el uso de la cédula del usuario seleccionado para su edición.
     *
     * @return
     */
    private boolean validarUsuario() {

        id_usuario = ReadDB.getUserID(cedulaVieja);
        id_cliente = ReadDB.getClienteID(cedulaVieja);

        //Validar que el id de usuario y el id de cliente, sea mayor a 0
        if (id_usuario > 0 && id_cliente > 0) {

            return true;

        } else {
            msjError("No se encontró registro del usuario a editar en la base de datos.\n"
                    + "Por favor, actualice la tabla de los clientes registrados\n"
                    + "y verifique su registro en la tabla.");
        }

        return false;
    }

    /**
     * Función para enviar un código de seguridad al correo del usuario
     * ingresado, sea para agregar o para editar un usuario.
     *
     * @return
     */
    private boolean codigoSeguridad() {
        //Validar si se le hizo algún cambio al correo ingresado del usuario
        if (crearUsuario || !correo.equals(correoViejo)) {
            String msj;
            String[] botones = {"Volver a enviar", "Cancelar"};

            //Ciclo para enviar el código de seguridad hasta que se complete
            while (true) {

                //Generar un número aleatorio de 6 dígitos
                int codigoSeguridad = (int) (Math.random() * (999999 - 100000) + 100000);

                //Enviar el código al correo
                if (EmailCode.comprobarCorreo(correo, codigoSeguridad)) {

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

                    //Pedir el código de seguridad al usuario
                    msj = "Se envió un código de seguridad al correo:\n" + correo
                            + "\n\nPor favor, ingrese el código enviado.";
                    String codigo = JOptionPane.showInputDialog(null, msj, "Verificación del correo", JOptionPane.QUESTION_MESSAGE);

                    //Comprobar que se presionó el botón de "Aceptar"
                    if (codigo != null) {

                        //Obtener la fecha en que se presionó el botón
                        Calendar fechaActual = new java.util.GregorianCalendar();
                        fechaActual.setTime(new java.util.Date());

                        //Comprobar que la fecha actual NO sea menor que la fecha
                        //de generación del código
                        if (fechaActual.compareTo(fechaSalida) >= 0) {
                            //Comprobar que la fecha actual sea menor a la fecha
                            //de expiración del código
                            if (fechaActual.compareTo(fechaExpiracion) < 0) {
                                //Comprobar que el campo NO esté vacío
                                if (!codigo.isEmpty()) {

                                    //Comprobar que el código ingresado, sea el mismo 
                                    //código enviado al correo
                                    if (codigo.equals(String.valueOf(codigoSeguridad))) {

                                        //Mensaje de éxito y retornar true
                                        msjInformativo("Se comprobó el código de seguridad con éxito.");
                                        return true;

                                    } else {
                                        //Mensaje de error
                                        msj = "El código ingresado es inválido.\nVuelva a intentarlo "
                                                + "con un nuevo código o cancele la operación";
                                        //Mensaje de error con opción de salir
                                        int opcion = JOptionPane.showOptionDialog(
                                                null,
                                                msj,
                                                "Error de conexión",
                                                0,
                                                JOptionPane.ERROR_MESSAGE,
                                                null,
                                                botones,
                                                botones[0]);

                                        //Si se da cualquier opción que NO sea "aceptar", romper el ciclo
                                        if (opcion != 0) {
                                            break;
                                        }
                                    }

                                } else {
                                    //Mensaje de error
                                    msj = "El campo no puede estar vacío.";
                                    //Mensaje de error con opción de salir
                                    int opcion = JOptionPane.showOptionDialog(
                                            null,
                                            msj,
                                            "Error de conexión",
                                            0,
                                            JOptionPane.ERROR_MESSAGE,
                                            null,
                                            botones,
                                            botones[0]);

                                    //Si se da cualquier opción que NO sea "aceptar", romper el ciclo
                                    if (opcion != 0) {
                                        break;
                                    }
                                }
                            } else {
                                msjError("El código ha superado el tiempo de expiracion (30 min)."
                                        + "\nPor favor, vuelva a solicitar un nuevo código.");
                                //Romper el ciclo si la fecha es inválida
                                break;
                            }
                        } else {
                            msjError("La fecha actual es menor a la fecha de generación de "
                                    + "código.\nPor favor, ajuste su calendario a la fecha actual.");
                            //Romper el ciclo si la fecha es inválida
                            break;
                        }
                    } else {
                        //Si se da cualquier opción que NO sea "aceptar", 
                        //romper el ciclo
                        break;
                    }
                } else {
                    //Mensaje de error con opción de salir
                    msj = "No se pudo enviar el código de seguridad al"
                            + "correo del usuario.\nPor favor, revise su "
                            + "conexión internet y con la base de datos, y vuelva "
                            + "a intentarlo.";
                    int opcion = JOptionPane.showOptionDialog(
                            null,
                            msj,
                            "Error de conexión",
                            0,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            botones,
                            botones[0]);

                    //Si se da cualquier opción que NO sea "aceptar", romper el ciclo
                    if (opcion != 0) {
                        break;
                    }
                }
            }
            return false;

        } else {
            return true;
        }
    }

    //ATRIBUTOS BACKEND
    private static Boolean crearUsuario;
    private static int cedula;
    private static String cedulaVieja;
    private static String correo, correoViejo;
    private static String claveFinal;
    private static String nombre;
    private static String apellido;
    private static String telefono;
    private static int id_usuario, id_cliente, rol = -99;

    // ========== FRONTEND ==========
    /**
     * Constructor de la ventana para agregar o editar un proveedor
     */
    public NuevoUsuario() {
        this.setLayout(null);
        this.setSize(450, 610);
        this.setResizable(false);
        this.getContentPane().setBackground(BLANCO);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setIconTitle();

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes de la ventana
     */
    private void initComponents() {
        logo.setForeground(CELESTE);
        logo.setSize(logo.getPreferredSize());

        lblTitulo.setVerticalAlignment(javax.swing.JLabel.TOP);

        boxRoles.setFont(properties.Fuentes.segoe(16, java.awt.Font.PLAIN));

        this.add(logo);
        this.add(lblTitulo);
        this.add(lblCedula);
        this.add(txtCedula);
        this.add(lblCorreo);
        this.add(txtCorreo);
        this.add(lblNombre);
        this.add(txtNombre);
        this.add(lblApellido);
        this.add(txtApellido);
        this.add(lblTelefono);
        this.add(txtTelefono);
        this.add(lblRol);
        this.add(boxRoles);
        this.add(lblClave);
        this.add(txtClave);
        this.add(lblClaveRepetida);
        this.add(txtClaveRepetida);
        this.add(btnGuardar);
        this.add(btnCancelar);
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     */
    private void relocateComponents() {
        int gapV = 2;
        int paddingH = 40;
        int paddingV = 20;
        int height = this.getContentPane().getHeight();
        int width = this.getContentPane().getWidth();
        int middleX = width / 2;
        int fieldH = 30;
        int fieldW = middleX - paddingH - (paddingV / 2);

        //Posición del logo
        int x = middleX - logo.getWidth() / 2;
        logo.setLocation(x, paddingV);

        //Posición y tamaño del título
        int y = paddingV * 2 + logo.getHeight();
        int w = width - paddingH * 2;
        lblTitulo.setLocation(paddingH, y);
        lblTitulo.setSize(w, paddingV * 5 / 2);

        //Label y campo de texto del correo
        int lblY = y + lblTitulo.getHeight() + paddingV / 2;
        int txtY = lblY + lblCorreo.getHeight() + gapV;
        lblCorreo.setLocation(paddingH, lblY);
        txtCorreo.setLocation(paddingH, txtY);
        txtCorreo.setSize(w, fieldH);

        //Label y campo de texto del rol del usuario
        lblY = txtY + fieldH + paddingV / 4;
        txtY = lblY + lblRol.getHeight() + gapV;
        lblRol.setLocation(paddingH, lblY);
        boxRoles.setBounds(paddingH, txtY, w, fieldH);

        //Label y campo de texto del nombre
        lblY = txtY + fieldH + paddingV / 4;
        txtY = lblY + lblNombre.getHeight() + gapV;
        lblNombre.setLocation(paddingH, lblY);
        txtNombre.setBounds(paddingH, txtY, fieldW, fieldH);

        //Label y campo de texto del apellido
        x = width - paddingH - fieldW;
        lblApellido.setLocation(x, lblY);
        txtApellido.setBounds(x, txtY, fieldW, fieldH);

        //Label y campo de texto de la cédula
        lblY = txtY + fieldH + paddingV / 4;
        txtY = lblY + lblCedula.getHeight() + gapV;
        lblCedula.setLocation(paddingH, lblY);
        txtCedula.setBounds(paddingH, txtY, fieldW, fieldH);

        //Label y campo de texto del teléfono
        lblTelefono.setLocation(x, lblY);
        txtTelefono.setBounds(x, txtY, fieldW, fieldH);

        //Label y campo de la clave nueva
        lblY = txtY + fieldH + paddingV / 4;
        txtY = lblY + lblClave.getHeight() + gapV;
        lblClave.setLocation(paddingH, lblY);
        txtClave.setBounds(paddingH, txtY, fieldW, fieldH);

        //Label y campo de la clave repetida
        lblClaveRepetida.setLocation(x, lblY);
        txtClaveRepetida.setBounds(x, txtY, fieldW, fieldH);

        //Botones
        fieldH += 10;
        y = height - paddingH - fieldH;
        btnCancelar.setBounds(paddingH, y, fieldW, fieldH);
        btnGuardar.setBounds(x, y, fieldW, fieldH);
    }

    /**
     * Función para colocarle el ícono del software al frame.
     */
    private void setIconTitle() {
        try {
            //Cargar el ícono del frame
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo.png"));
            this.setIconImage(img.getScaledInstance(32, 32, ESCALA_SUAVE));

        } catch (Exception ex) {
            msjAdvertencia(
                    "No se pudo encontrar el ícono del software.\n"
                    + "El software seguirá ejecutandose normalmente sin el ícono."
            );
        }
    }

    /**
     * Función para vaciar los campos de la ventana
     */
    protected void vaciarCampos() {
        //Vaciar los campos
        txtCedula.setText("");
        txtCorreo.setText("");
        txtClave.setText("");
        txtClave.hidePassword();
        txtClaveRepetida.setText("");
        txtClaveRepetida.hidePassword();
        boxRoles.setSelectedIndex(0);

        //Vaciar los atributos
        crearUsuario = null;
        id_usuario = 0;
        id_cliente = 0;
        
        cedula = 0;
        cedulaVieja = null;
        
        correo = null;
        correoViejo = null;
        rol = -99;
        
        nombre = null;
        apellido = null;
        telefono = null;
        
        claveFinal = null;
    }

    /**
     * Función para agregar un nuevo usuario
     */
    protected void agregar() {
        //Atributo
        crearUsuario = true;

        //Propiedades de la ventana
        this.setTitle("Agregar un usuario - AquaTech");
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        //Titulo para la ventana
        lblTitulo.setText("<html>Ingrese los datos necesarios para registrar un "
                + "nuevo usuario al sistema.</html>");
        //Logo para la ventana
        logo.setText("Agregar Usuario");
        logo.setSize(logo.getPreferredSize());

        vaciarCampos();
        txtClave.setEnabled(true);
        txtClaveRepetida.setEnabled(true);

        //Redimensionar los componentes
        relocateComponents();
    }

    /**
     * Función para editar un usuario registrado
     *
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @param correo
     * @param rol
     */
    protected void editar(String cedula, String rol, String nombre, String apellido, String telefono, String correo) {
        crearUsuario = false;

        //Propiedades de la ventana
        this.setTitle("Editar un proveedor - AquaTech");
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        //Atributos
        NuevoUsuario.cedulaVieja = cedula;
        NuevoUsuario.correoViejo = correo;
        switch (rol.toUpperCase()) {
            case "EMPLEADO":
                NuevoUsuario.rol = 2;
                break;
            case "ADMIN":
                NuevoUsuario.rol = 3;
                break;
            default:
                NuevoUsuario.rol = 1;
                break;
        }

        //Titulo para la ventana
        lblTitulo.setText("<html>Ingrese los datos necesarios para registrar un "
                + "nuevo usuario al sistema.</html>");
        //Logo para la ventana
        logo.setText("Agregar Usuario");
        logo.setSize(logo.getPreferredSize());

        //Sobreescribir los campos
        txtCorreo.setText(correo);
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtCedula.setText(cedula);
        txtTelefono.setText(telefono);

        txtClave.setText("");
        txtClave.hidePassword();
        txtClaveRepetida.setText("");
        txtClaveRepetida.hidePassword();

        boxRoles.setSelectedIndex(NuevoUsuario.rol);

        txtClave.setEnabled(false);
        txtClaveRepetida.setEnabled(false);

        //Redimensionar los componentes
        relocateComponents();
    }

    /**
     * Función para vaciar los componentes y cerrar la ventana
     */
    private void salir() {
        if (msjYesNo("¿Está seguro de cancelar la operación?")) {
            vaciarCampos();
            dispose();
        }
    }

    //COMPONENTES
    private static final Logo logo = new Logo(HORIZONTAL);
    private static final Label lblTitulo = new Label("", PLANO, 16);

    private static final Label lblCedula = new Label("Cédula", PLANO, 16);
    private static final CampoTexto txtCedula = new CampoTexto("Cédula del usuario", NUMERO);

    private static final Label lblCorreo = new Label("Correo", PLANO, 16);
    private static final CampoTexto txtCorreo = new CampoTexto("Correo del usuario", CORREO);

    private static final Label lblNombre = new Label("Nombre", PLANO, 16);
    private static final CampoTexto txtNombre = new CampoTexto("Nombre del usuario", NOMBRE);

    private static final Label lblApellido = new Label("Apellido", PLANO, 16);
    private static final CampoTexto txtApellido = new CampoTexto("Apellido del usuario", NOMBRE);

    private static final Label lblTelefono = new Label("Telefono", PLANO, 16);
    private static final CampoTexto txtTelefono = new CampoTexto("Telefono del usuario", NUMERO);

    private static final Label lblRol = new Label("Rol del usuario", PLANO, 16);
    private static final String[] opciones = {"Seleccionar", "Cliente", "Empleado", "Administrador"};
    private static final JComboBox boxRoles = new JComboBox(opciones);

    private static final Label lblClave = new Label("Clave nueva", PLANO, 16);
    private static final CampoClave txtClave = new CampoClave("Clave del usuario");

    private static final Label lblClaveRepetida = new Label("Repita la clave", PLANO, 16);
    private static final CampoClave txtClaveRepetida = new CampoClave("Clave repetida");

    private static final Boton btnGuardar = new Boton("Guardar", VERDE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);
}
