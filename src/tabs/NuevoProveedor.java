package tabs;

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
import static java.awt.Font.BOLD;
import static javax.swing.SwingConstants.HORIZONTAL;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjAdvertencia;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjInformativo;
import static properties.ValidarTexto.formatoNombreEmpresa;
import static properties.ValidarTexto.formatoRIF;
import static properties.ValidarTexto.formatoTelefono;
import tabs.compras.Compras;

/**
 *
 * @author diego
 */
public class NuevoProveedor extends JFrame implements properties.Constantes, properties.Colores {

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
                if (crearProveedor) {
                    crear();
                } else {
                    actualizar();
                }
            }
        });

        //KEY LISTENERS
        txtRif.addKeyListener(new KeyAdapter() {
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
                    txtNombre.requestFocus();
                }
            }
        });
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == TECLA_ENTER) {
                    if (crearProveedor) {
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
                if (msjYesNo("¿Está seguro de realizar el registro del nuevo proveedor?")) {

                    //Intentar crear el cliente en la base de datos
                    if (CreateDB.createProveedor(rif, nombre, telefono)) {

                        //Ya que puede dar muchos problemas el alterar o agregar
                        //un dato a una tabla (y no a su Model), la forma de 
                        //visualizar los cambios será actualizando los datos 
                        //de la tabla con la base de datos
                        Proveedores.actualizarDatos();

                        msjInformativo("Se creó el nuevo proveedor con éxito.");

                        dispose();
                        vaciarCampos();

                        Compras.vaciarCampos();
                    }
                }
            }
        }
    }

    /**
     * Función para actualizar un cliente en la base de datos
     */
    private void actualizar() {
        //Validar la existencia del proveedor
        if (validarProveedor()) {
            //Validar que los campos no estén vacíos
            if (validarCampos()) {
                //Validar que los datos estén correctos
                if (validarDatos()) {
                    //Mensaje de confirmación
                    if (msjYesNo("¿Está seguro de actualizar los datos del proveedor?")) {
                        //Intentar editar el proveedor en la base de datos
                        if (UpdateDB.updateProveedor(id, rif, nombre, telefono)) {

                            //Ya que puede dar muchos problemas el alterar o agregar
                            //un dato a una tabla (y no a su Model), la forma de 
                            //visualizar los cambios será actualizando los datos 
                            //de la tabla con la base de datos
                            Proveedores.actualizarDatos();

                            msjInformativo("Se actualizaron los datos del proveedor con éxito.");

                            dispose();
                            vaciarCampos();

                            Compras.vaciarCampos();
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
        telefono = txtTelefono.getText().trim();
        rif = txtRif.getText().trim();

        //Validar que los campos NO estén vacíos
        if (!nombre.isEmpty()) {
            if (!telefono.isEmpty()) {
                if (!rif.isEmpty()) {

                    return true;

                } else {
                    msj = "El RIF no puede estar vacío." + msj;
                }
            } else {
                msj = "El teléfono no puede estar vacío." + msj;
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
        if (formatoNombreEmpresa(nombre)) {
            if (formatoTelefono(telefono)) {
                if (formatoRIF(rif)) {

                    return true;

                } else {
                    msj = "El RIF ingreso no cumple con el formato de un RIF." + msj;
                }
            } else {
                msj = "El teléfono ingresado no cumple con el\n"
                        + "formato de un número telefónico." + msj;
            }
        } else {
            msj = "El nombre debe tener un rango de 2 a 25 letras." + msj;
        }

        msjError(msj);

        return false;
    }

    /**
     * Función para validar la existencia del proveedor en la base de datos
     * mediante el uso del RIF del proveedor seleccionado para su edición.
     *
     * @return
     */
    private boolean validarProveedor() {

        id = ReadDB.getProveedorID(rifViejo);

        //Validar que el ID sea mayor a 0
        if (id > 0) {

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
    private static String rifViejo;
    private static Boolean crearProveedor;
    private static String nombre, rif, telefono;

    // ========== FRONTEND ==========
    /**
     * Constructor de la ventana para agregar o editar un proveedor
     */
    public NuevoProveedor() {
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
        logo.setFont(segoe(32, BOLD));

        lblTitulo.setVerticalAlignment(javax.swing.JLabel.TOP);

        this.add(logo);
        this.add(lblTitulo);
        this.add(lblNombre);
        this.add(txtNombre);
        this.add(lblRif);
        this.add(txtRif);
        this.add(lblTelefono);
        this.add(txtTelefono);
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
        int y = paddingV * 5/2 + logo.getHeight();
        int w = width - paddingH * 2;
        lblTitulo.setLocation(paddingH, y);
        lblTitulo.setSize(w, paddingV * 5 / 2);

        //Labels y campo de texto del rif
        int lblY = y + lblTitulo.getHeight() + paddingV * 3/2;
        int txtY = lblY + lblNombre.getHeight() + gapV;
        lblRif.setLocation(paddingH, lblY);
        txtRif.setBounds(paddingH, txtY, fieldW, fieldH);

        //Labels y campo de texto del teléfono
        x = width - paddingH - fieldW;
        lblTelefono.setLocation(x, lblY);
        txtTelefono.setBounds(x, txtY, fieldW, fieldH);

        //Labels y campo de texto del nombre de la empresa
        lblY = txtY + fieldH + paddingV / 2;
        txtY = lblY + lblNombre.getHeight() + gapV;
        lblNombre.setLocation(paddingH, lblY);
        txtNombre.setBounds(paddingH, txtY, w, fieldH);

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
     * Función para agregar un nuevo proveedor
     */
    protected void agregar() {
        //Propiedades de la ventana 
        this.setTitle("Agregar un proveedor - AquaTech");
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        //Atributo
        crearProveedor = true;

        //Titulo para la ventana
        lblTitulo.setText("<html>Ingrese los datos necesarios para registrar un "
                + "nuevo proveedor al sistema.</html>");

        //Logo para la ventana
        logo.setText("Agregar Proveedor");
        logo.setSize(logo.getPreferredSize());

        //Redimensionar los componentes
        relocateComponents();
    }

    /**
     * Función para editar un proveedor registrado
     *
     * @param nombre
     * @param rif
     * @param telefono
     */
    protected void editar(String rif, String nombre, String telefono) {
        //Propiedades de la ventana
        this.setTitle("Editar un proveedor - AquaTech");
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        //Atributos
        rifViejo = rif;
        crearProveedor = false;

        //Titulo para la ventana
        lblTitulo.setText("<html>Ingrese los nuevos datos del proveedor "
                + "que desea actualizar.</html>");
        //Logo para la ventana
        logo.setText("Editar Proveedor");
        logo.setSize(logo.getPreferredSize());

        //Sobreescribir los campos
        txtRif.setText(rif);
        txtNombre.setText(nombre);
        txtTelefono.setText(telefono);

        //Redimensionar los componentes
        relocateComponents();
    }

    /**
     * Función para vaciar los campos de la ventana
     */
    protected void vaciarCampos() {
        //Vaciar los campos
        txtNombre.setText("");
        txtRif.setText("");
        txtTelefono.setText("");

        //Vaciar los atributos
        id = 0;
        rif = null;
        rifViejo = null;
        nombre = null;
        telefono = null;
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

    private static final Label lblRif = new Label("RIF", PLANO, 16);
    private static final CampoTexto txtRif = new CampoTexto("Rif del proveedor", NUMERO);

    private static final Label lblTelefono = new Label("Teléfono", PLANO, 16);
    private static final CampoTexto txtTelefono = new CampoTexto("Teléfono", NUMERO);

    private static final Label lblNombre = new Label("Nombre", PLANO, 16);
    private static final CampoTexto txtNombre = new CampoTexto("Nombre del proveedor", NOMBRE);

    private static final Boton btnGuardar = new Boton("Guardar", VERDE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);
}
