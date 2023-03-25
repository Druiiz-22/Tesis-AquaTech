package tabs.clientes;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.Logo;
import database.CreateDB;
import database.ReadDB;
import database.UpdateDB;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import static javax.swing.SwingConstants.HORIZONTAL;
import static properties.Constantes.ESCALA_SUAVE;
import static properties.Mensaje.msjAdvertencia;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;
import static properties.ValidarTexto.formatoNombre;
import static properties.ValidarTexto.formatoTelefono;

/**
 * Clase para la creación de la ventana para agregar o editar un cliente
 */
public class NuevoCliente extends JFrame implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    /**
     * Función para agregar los listeners a los componentes
     */
    private void listeners() {

        //MOUSE LISTENERS
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                salir();
            }
        });

        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (crearCliente) {
                    crear();
                } else {
                    actualizar();
                }
            }
        });

        //KEY LISTENERS
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    txtApellido.requestFocus();
                }
            }
        });
        txtApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    txtCedula.requestFocus();
                }
            }
        });
        txtCedula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    txtTelefono.requestFocus();
                }
            }
        });
        txtTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    txtDireccion.requestFocus();
                }
            }
        });
        txtDireccion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    if (crearCliente) {
                        crear();
                    } else {
                        actualizar();
                    }
                }
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
     * Función para crear un nuevo cliente en la base de datos
     */
    private void crear() {
        if (validarCampos()) {
            if (validarDatos()) {
                //Mensaje de confirmación
                if (msjYesNo("¿Está seguro de realizar el registro del nuevo cliente?")) {

                    //Intentar crear el cliente en la base de datos
                    if (CreateDB.createCliente(cedula, nombre, apellido, telefono, direccion)) {

                        //Agregar el nuevo cliente a la tabla en el panel de clientes
                        PanelClientes.addCliente(new String[]{cedula, nombre, apellido, telefono, direccion});
                        
                        vaciarCampos();
                    }
                }
            }
        }
    }

    /**
     * Función para actualizar un cliente en la base de datos
     */
    private void actualizar() {
        //Validar la existencia del cliente
        if (validarCliente()) {
            //Validar que los campos no estén vacíos
            if (validarCampos()) {
                //Validar que los datos estén correctos
                if (validarDatos()) {
                    //Mensaje de confirmación
                    if (msjYesNo("¿Está seguro de realizar el registro del nuevo cliente?")) {
                        //Intentar editar el cliente en la base de datos
                        if (UpdateDB.editCliente(String.valueOf(id), cedula, nombre, apellido, telefono, direccion)) {

                            //Agregar el nuevo cliente a la tabla en el panel de clientes
                            PanelClientes.addCliente(new String[]{cedula, nombre, apellido, telefono, direccion});
                            vaciarCampos();
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

        nombre = txtNombre.getText().trim().toUpperCase();
        apellido = txtApellido.getText().trim().toUpperCase();
        telefono = txtTelefono.getText().trim();
        cedula = txtCedula.getText().trim();
        direccion = txtDireccion.getText().trim().toUpperCase();

        //Validar que los campos NO estén vacíos
        if (!nombre.isEmpty()) {
            if (!apellido.isEmpty()) {
                if (!telefono.isEmpty()) {
                    if (!cedula.isEmpty()) {
                        if (!direccion.isEmpty()) {

                            return true;

                        } else {
                            msj = "La direccion no puede estar vacío." + msj;
                        }
                    } else {
                        msj = "La cédula no puede estar vacía." + msj;
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
     * @return TRUE en caso de que los datos sean válidos
     */
    private boolean validarDatos() {
        String msj = "\nPor favor, revise sus datos.";

        //Validar los formatos de los datos
        if (formatoNombre(nombre)) {
            if (formatoNombre(apellido)) {
                if (formatoTelefono(telefono)) {
                    if (direccion.length() >= 2 && direccion.length() <= 255) {

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
                        msj = "La dirección debe tener un rango de 2 a 255 letras." + msj;
                    }
                } else {
                    msj = "El teléfono ingresado no cumple con el\n"
                            + "formato de un número telefónico." + msj;
                }
            } else {
                msj = "El apellido debe tener un rango de 2 a 25 letras." + msj;
            }
        } else {
            msj = "El nombre debe tener un rango de 2 a 25 letras." + msj;
        }

        msjError(msj);

        return false;
    }

    /**
     * Función para validar la existencia del cliente en la base de datos
     * mediante el uso de la cédula del cliente seleccionado para su edición.
     *
     * @return
     */
    private boolean validarCliente() {
        
        id = ReadDB.getClienteID(cedulaVieja);
        
        //Validar que el ID sea mayor a 0
        if(id > 0){
            
            return true;
        
        } else {
            msjError("No se encontró registro del cliente a editar en la base de datos.\n"
                    + "Por favor, actualice la tabla de los clientes registrados\n"
                    + "y verifique su registro en la tabla.");
        }
        
        return false;
    }

    //ATRIBUTOS BACKEND
    private static int id;
    private static String cedulaVieja;
    private static Boolean crearCliente;
    private static String nombre, apellido, cedula, telefono, direccion;

    // ========== FRONTEND ==========
    /**
     * Constructor de la ventana para agregar o editar un cliente
     */
    public NuevoCliente() {
        this.setLayout(null);
        this.setSize(450, 550);
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
        
        this.add(logo);
        this.add(lblTitulo);
        this.add(lblNombre);
        this.add(txtNombre);
        this.add(lblApellido);
        this.add(txtApellido);
        this.add(lblCedula);
        this.add(txtCedula);
        this.add(lblTelefono);
        this.add(txtTelefono);
        this.add(lblDireccion);
        this.add(txtDireccion);
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
        int fieldH = 40;
        int fieldW = middleX - paddingH - paddingV / 2;

        //Posición del logo
        int x = middleX - logo.getWidth() / 2;
        logo.setLocation(x, paddingV);

        //Posición y tamaño del título
        int y = paddingV * 2 + logo.getHeight();
        int w = width - paddingH * 2;
        lblTitulo.setLocation(paddingH, y);
        lblTitulo.setSize(w, paddingV * 5 / 2);

        //Labels y campo de texto del nombre
        int lblY = y + lblTitulo.getHeight() + paddingV;
        int txtY = lblY + lblNombre.getHeight() + gapV;
        lblNombre.setLocation(paddingH, lblY);
        txtNombre.setBounds(paddingH, txtY, fieldW, fieldH);

        //Labels y campo de texto del apellido
        x = width - paddingH - fieldW;
        lblApellido.setLocation(x, lblY);
        txtApellido.setBounds(x, txtY, fieldW, fieldH);

        //Labels y campo de texto de la cédula
        lblY = txtY + fieldH + paddingV / 2;
        txtY = lblY + lblCedula.getHeight() + gapV;
        lblCedula.setLocation(paddingH, lblY);
        txtCedula.setBounds(paddingH, txtY, fieldW, fieldH);

        //Labels y campo de texto del número
        lblTelefono.setLocation(x, lblY);
        txtTelefono.setBounds(x, txtY, fieldW, fieldH);

        //Labels y campo de texto de la dirección
        lblY = txtY + fieldH + paddingV / 2;
        txtY = lblY + lblDireccion.getHeight() + gapV;
        lblDireccion.setLocation(paddingH, lblY);
        txtDireccion.setBounds(paddingH, txtY, w, fieldH);

        //Botones
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
     * Función para agregar un nuevo cliente
     */
    protected void agregar() {
        this.setTitle("Agregar un cliente - AquaTech");
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        crearCliente = true;
        lblTitulo.setText("<html>Ingrese los datos necesarios para registrar un "
                + "nuevo cliente al sistema.</html>");

        logo.setText("Agregar Cliente");
        logo.setSize(logo.getPreferredSize());

        relocateComponents();
    }

    /**
     * Función para editar un cliente registrado
     *
     * @param nombre
     * @param apellido
     * @param cedula
     * @param telefono
     * @param direc
     */
    protected void editar(String cedula, String nombre, String apellido, String telefono, String direc) {
        this.setTitle("Editar un cliente - AquaTech");
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        cedulaVieja = cedula;
        
        crearCliente = false;
        lblTitulo.setText("<html>Ingrese los nuevos datos del cliente "
                + "seleccionado que desea actualizar.</html>");

        logo.setText("Editar Cliente");
        logo.setSize(logo.getPreferredSize());

        txtCedula.setText(cedula);
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtTelefono.setText(telefono);
        txtDireccion.setText(direc);

        relocateComponents();
    }

    /**
     * Función para vaciar los campos de la ventana
     */
    protected void vaciarCampos() {
        //Vaciar los campos
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        
        //Vaciar los atributos
        id = 0;
        cedulaVieja = null;
        nombre = null;
        apellido = null;
        cedula = null;
        telefono = null;
        direccion = null;
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

    private static final Label lblNombre = new Label("Nombre", PLANO, 16);
    private static final CampoTexto txtNombre = new CampoTexto("Nombre del cliente", NOMBRE);

    private static final Label lblApellido = new Label("Apellido", PLANO, 16);
    private static final CampoTexto txtApellido = new CampoTexto("Apellido del cliente", NOMBRE);

    private static final Label lblCedula = new Label("Cédula", PLANO, 16);
    private static final CampoTexto txtCedula = new CampoTexto("Cédula del cliente", NUMERO);

    private static final Label lblTelefono = new Label("Teléfono", PLANO, 16);
    private static final CampoTexto txtTelefono = new CampoTexto("Teléfono del cliente", NUMERO);

    private static final Label lblDireccion = new Label("Direccion", PLANO, 16);
    private static final CampoTexto txtDireccion = new CampoTexto("Direccion del cliente", CUALQUIER);

    private static final Boton btnGuardar = new Boton("Guardar", VERDE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);
}
