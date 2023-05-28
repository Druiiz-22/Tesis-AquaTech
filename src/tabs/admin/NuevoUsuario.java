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
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import static javax.swing.SwingConstants.HORIZONTAL;
import main.Frame;
import properties.Encript;
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
public class NuevoUsuario extends JDialog implements properties.Constantes, properties.Colores {

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
            public void windowOpened(WindowEvent e) {
                //Obtener el tamaño del panel contenedor + los bordes de la 
                //ventana una vez que la ventana sea abierta, para asignar el 
                //tamaño al Dialog
                int w = paneSize.width + getInsets().left + getInsets().right;
                int h = paneSize.height + getInsets().top + getInsets().bottom;

                //Asignar el tamaño y centrar el Dialog
                setSize(w, h);
                setLocation(centerLocation());
            }

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
        new Thread() {
            @Override
            public void run() {
                glass.setVisible(true);
                if (validarCampos()) {
                    if (validarDatos()) {
                        //Mensaje de confirmación
                        if (msjYesNo("¿Está seguro de realizar el registro del nuevo usuario?")) {
                            //Confirmar cuenta de administrador
                            if (AdminDB.validateAdminUser()) {
                                //Intentar crear el cliente en la base de datos
                                if (CreateDB.createUsuario(cedula, nombre, apellido, telefono, correo, claveFinal)) {

                                    //Ya que puede dar muchos problemas el alterar o agregar
                                    //un dato a una tabla (y no a su Model), la forma de 
                                    //visualizar los cambios será actualizando los datos 
                                    //de la tabla con la base de datos
                                    Usuarios.actualizarDatos();

                                    dispose();
                                    vaciarCampos();

                                    Frame.closeGlass();
                                }

                            }
                        }
                        //Cerrar el glassPane, se registre el usuario o no
                        glass.setVisible(false);
                    }
                }
            }
        }.start();

    }

    /**
     * Función para actualizar un usuario en la base de datos
     */
    private void actualizar() {
        new Thread() {
            @Override
            public void run() {
                glass.setVisible(true);
                //Validar que los campos no estén vacíos
                if (validarCampos()) {
                    //Validar que los datos estén correctos
                    if (validarDatos()) {
                        //Mensaje de confirmación
                        if (msjYesNo("¿Está seguro de actualizar los datos del usuario?")) {
                            //Validar la existencia del usuario
                            if (validarUsuario()) {
                                //Confirmar cuenta de administrador 
                                if (AdminDB.validateAdminUser()) {
                                    //Intentar editar el proveedor en la base de datos
                                    if (UpdateDB.updateUsuario(id_usuario, id_cliente, cedula, nombre, apellido, telefono, correo)) {
                                        //Ya que puede dar muchos problemas el alterar o agregar
                                        //un dato a una tabla (y no a su Model), la forma de 
                                        //visualizar los cambios será actualizando los datos 
                                        //de la tabla con la base de datos
                                        Usuarios.actualizarDatos();

                                        vaciarCampos();
                                        dispose();
                                    }

                                }
                            }
                        }
                        glass.setVisible(false);
                    }
                }
            }
        }.start();
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

        //Ocultar el glassPane
        glass.setVisible(false);
        //Mostrar mensaje de errorr
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

        } else if (!formatoNombre(nombre)) {
            msj = "El nombre solo puede llevar letras, entre 2 a 25." + msj;

        } else if (!formatoNombre(apellido)) {
            msj = "El apellido solo puede llevar letras, entre 2 a 25." + msj;

        } else if (!formatoTelefono(telefono)) {
            msj = "El teléfono ingresado no cumple con el\n"
                    + "formato de un número telefónico." + msj;
        } else {

            //Asignar un '0' al comienzo del teléfono si este 
            //NO empieza con cero.
            if (!telefono.startsWith("0")) {
                telefono = "0" + telefono;
            }

            try {
                //Validar que la cédula se pueda convertir a un entero y esté
                //dentro del rango correcto
                cedula = Integer.parseInt(txtCedula.getText());
                if (cedula >= 1 && cedula <= 99999999) {

                    //Validar si se está creando un usuario o editando
                    if (crearUsuario) {

                        //Validar que las contraseñas tengan mínimo 8 carácteres
                        char[] nueva = txtClave.getPassword();
                        char[] repetida = txtClaveRepetida.getPassword();

                        if (nueva.length >= 8 && repetida.length >= 8) {

                            //Validar que las contraseñas sean las mismas
                            //estando convertidas en HashCode
                            String claveNueva = Encript.encriptar(nueva);
                            claveFinal = Encript.encriptar(repetida);

                            if (claveNueva.equals(claveFinal)) {

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

        //Ocultar el glassPane
        glass.setVisible(false);
        //Mostrar mensaje de errorr
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
        String msj;
        id_usuario = ReadDB.getUserID(cedulaVieja);
        id_cliente = ReadDB.getClienteID(cedulaVieja);

        //Validar que el id de usuario y el id de cliente, sea mayor a 0
        if (id_usuario > 0 && id_cliente > 0) {

            return true;

        } else {
            msj = "No se encontró registro del usuario a editar en la base de datos.\n"
                    + "Por favor, actualice la tabla de los clientes registrados\n"
                    + "y verifique su registro en la tabla.";
        }

        //Ocultar el glassPane
        glass.setVisible(false);
        //Mostrar mensaje de errorr
        msjError(msj);

        return false;
    }

    //ATRIBUTOS BACKEND
    private static Boolean crearUsuario;
    private static int cedula;
    private static String cedulaVieja;
    private static String correo;
    private static String claveFinal;
    private static String nombre;
    private static String apellido;
    private static String telefono;
    private static int id_usuario, id_cliente;

    // ========== FRONTEND ==========
    /**
     * Constructor de la ventana para agregar o editar un proveedor
     */
    public NuevoUsuario() {
        super(main.Run.getFrameRoot(), true);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setIconTitle();
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setSize(paneSize);
        this.getContentPane().setBackground(BLANCO);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes de la ventana
     */
    private void initComponents() {
        //GlassPane para cargar
        this.setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                //Pinter un fondo oscuro en el contenedor
                g.setColor(new java.awt.Color(0, 0, 0, 0.1f));
                g.fillRect(0, 0, getContentPane().getWidth(), getContentPane().getHeight());
            }
        });
        //Obtener el glassPane
        glass = (Container) (this.getGlassPane());
        glass.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        glass.addMouseListener(new MouseAdapter() {
        });

        //Propiedades del logo
        logo.setForeground(CELESTE);
        logo.setSize(logo.getPreferredSize());

        //Propiedades del título
        lblTitulo.setVerticalAlignment(javax.swing.JLabel.TOP);

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
        int width = paneSize.width;
        int height = paneSize.height;

        int gap = 2;
        int margin = 40;
        int padding = 10;
        int fieldH = 40;
        int fieldW = (width - margin * 2 - padding * 2) / 3;

        //Posición del logo
        int x = width / 2 - logo.getWidth() / 2;
        logo.setLocation(x, padding * 2);

        //Posición del título
        int y = padding * 4 + logo.getHeight();
        lblTitulo.setLocation(margin, y);

        //Label y campo de texto del correo
        int lblY = y + lblTitulo.getHeight() + padding;
        int txtY = lblY + lblCorreo.getHeight() + gap;
        int w = width - margin * 2;
        lblCorreo.setLocation(margin, lblY);
        txtCorreo.setBounds(margin, txtY, w, fieldH);

        //Label y campo de texto del nombre
        lblY = txtY + fieldH + padding;
        txtY = lblY + lblNombre.getHeight() + gap;
        lblNombre.setLocation(margin, lblY);
        txtNombre.setBounds(margin, txtY, fieldW, fieldH);

        //Label y campo de texto del apellido
        x = margin + fieldW + padding;
        lblApellido.setLocation(x, lblY);
        txtApellido.setBounds(x, txtY, fieldW, fieldH);

        //Label y campo de texto de la cédula
        x += fieldW + padding;
        lblCedula.setLocation(x, lblY);
        txtCedula.setBounds(x, txtY, fieldW, fieldH);

        //Label y campo de texto del teléfono
        lblY = txtY + fieldH + padding;
        txtY = lblY + lblTelefono.getHeight() + gap;
        lblTelefono.setLocation(margin, lblY);
        txtTelefono.setBounds(margin, txtY, fieldW, fieldH);

        //Label y campo de la clave nueva
        x = margin + fieldW + padding;
        lblClave.setLocation(x, lblY);
        txtClave.setBounds(x, txtY, fieldW, fieldH);

        //Label y campo de la clave repetida
        x += fieldW + padding;
        lblClaveRepetida.setLocation(x, lblY);
        txtClaveRepetida.setBounds(x, txtY, fieldW, fieldH);

        //Botones
        fieldH = 40;
        fieldW = 120;
        y = height - padding * 2 - fieldH;
        x = width / 2 - (fieldW * 2 + padding) / 2;
        btnCancelar.setBounds(x, y, fieldW, fieldH);
        x += fieldW + padding * 2;
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
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtClave.setText("");
        txtClave.hidePassword();
        txtClaveRepetida.setText("");
        txtClaveRepetida.hidePassword();

        //Vaciar los atributos
        id_usuario = 0;
        id_cliente = 0;

        cedula = 0;
        cedulaVieja = null;

        correo = null;

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

        //Label para el título
        lblTitulo.setText("Ingrese los datos necesarios para registrar un "
                + "nuevo usuario al sistema.");
        lblTitulo.setSize(lblTitulo.getPreferredSize());

        //Logo para la ventana
        logo.setText("Agregar Usuario");
        logo.setSize(logo.getPreferredSize());

        //Preparar los campos
        vaciarCampos();
        txtClave.setEnabled(true);
        txtClaveRepetida.setEnabled(true);

        //Propiedades de la ventana
        this.setTitle("Agregar un usuario - AquaTech");
        this.relocateComponents();
        this.setVisible(true);
    }

    /**
     * Función para editar un usuario registrado
     *
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @param correo
     */
    protected void editar(String cedula, String nombre, String apellido, String telefono, String correo) {
        //Vaciar todos los campos antes de iniciar
        vaciarCampos();

        //Atributos
        crearUsuario = false;
        NuevoUsuario.cedulaVieja = cedula;

        //Label para el título
        lblTitulo.setText("Ingrese los datos necesarios para registrar un "
                + "nuevo usuario al sistema.");
        lblTitulo.setSize(lblTitulo.getPreferredSize());
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

        txtClave.setEnabled(false);
        txtClaveRepetida.setEnabled(false);

        //Propiedades de la ventana
        this.setTitle("Editar un proveedor - AquaTech");
        this.relocateComponents();

        try {
            this.setVisible(true);
        } catch (Exception e) {
            System.out.println("Luego de set visible.\n" + e);
        }
    }

    /**
     * Función para vaciar los componentes y cerrar la ventana
     */
    private void salir() {
        if (msjYesNo("¿Está seguro de cancelar la operación?")) {
            vaciarCampos();
            crearUsuario = null;
            dispose();
        }
    }

    /**
     * Función para obtener las coordenadas necesarias para posicionar la
     * ventana en el centro de la pantalla
     *
     * @return
     */
    private java.awt.Point centerLocation() {

        //Obtener el tamaño de la pantalla
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        //Posición en X del frame
        int x = screen.width / 2 - this.getWidth() / 2;
        //Posición en Y del frame
        int y = screen.height / 2 - this.getHeight() / 2;

        //Retornar el tamaño mínimo
        return new java.awt.Point(x, y);
    }

    //COMPONENTES
    private static Container glass;
    private static final Dimension paneSize = new Dimension(640, 450);
    private static final Logo logo = new Logo(HORIZONTAL);
    private static final Label lblTitulo = new Label("", TITULO, 16);

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

    private static final Label lblClave = new Label("Clave nueva", PLANO, 16);
    private static final CampoClave txtClave = new CampoClave("Clave del usuario");

    private static final Label lblClaveRepetida = new Label("Repita la clave", PLANO, 16);
    private static final CampoClave txtClaveRepetida = new CampoClave("Clave repetida");

    private static final Boton btnGuardar = new Boton("Guardar", VERDE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);
}
