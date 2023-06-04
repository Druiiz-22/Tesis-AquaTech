package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.Logo;
import database.AdminDB;
import database.CreateDB;
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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import main.Frame;
import main.Run;
import static properties.Colores.VERDE;
import static properties.Constantes.ADMINISTRADOR;
import static properties.Constantes.OPERADOR;
import static properties.Fuentes.segoe;
import properties.Mensaje;
import static properties.Mensaje.msjAdvertencia;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;
import static properties.Colores.NARANJA;

public class NuevoEmpleado extends JDialog implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
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
                if (crearEmpleado) {
                    crear();
                } else {
                    actualizar();
                }
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

    private void crear() {
        new Thread() {
            @Override
            public void run() {
                glass.setVisible(true);
                if (validarCampos()) {
                    if (validarDatos()) {
                        //Mensaje de confirmación
                        if (msjYesNo("¿Está seguro de contratar el nuevo empleado?")) {
                            //Confirmar cuenta de administrador
                            if (AdminDB.validateAdminUser()) {
                                if (CreateDB.createEmpleado(cedula, cargo, id_sucursal, rol)) {
                                    //Ya que puede dar muchos problemas el alterar o agregar
                                    //un dato a una tabla (y no a su Model), la forma de 
                                    //visualizar los cambios será actualizando los datos 
                                    //de la tabla con la base de datos
                                    Empleados.actualizarDatos();

                                    dispose();
                                    vaciarCampos();
                                }
                            }
                        }
                        glass.setVisible(false);
                    }
                }
            }
        }.start();
    }

    private void actualizar() {
        new Thread() {
            @Override
            public void run() {
                glass.setVisible(true);
                if (validarCampos()) {
                    if (validarDatos()) {
                        //Mensaje de confirmación
                        if (msjYesNo("¿Está seguro de actualizar los datos del empleado?")) {
                            if (validarEmpleado()) {
                                if (UpdateDB.updateEmpleado(id_empleado, cargo, id_sucursal, rol)) {
                                    //Ya que puede dar muchos problemas el alterar o agregar
                                    //un dato a una tabla (y no a su Model), la forma de 
                                    //visualizar los cambios será actualizando los datos 
                                    //de la tabla con la base de datos
                                    Empleados.actualizarDatos();

                                    Mensaje.msjInformativo("Se actualizó el empleado con éxito.");

                                    dispose();
                                    vaciarCampos();
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
        cargo = txtCargo.getText().trim().toUpperCase();
        rol = boxRol.getSelectedIndex();
        int index = boxSucursal.getSelectedIndex();
        
        if (!ci.isEmpty()) {
            if (!cargo.isEmpty()) {
                if (rol > 0 && rol < 4) {
                    if (index > 0) {                       
                        
                        return true;

                    } else {
                        msj = "Debe seleccionar una sucursal válida." + msj;
                    }
                } else {
                    msj = "Debe seleccionar un rol válido." + msj;
                }
            } else {
                msj = "El cargo no puede estar vacío." + msj;
            }
        } else {
            msj = "La cédula no puede estar vacía." + msj;
        }

        //Validar que los campos NO estén vacíos
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
        String msj;

        //Validar el rol del usuario
        int userRol = Frame.getUserRol();

        //Validar que el usuario obtenido tenga los roles permitidos
        if (userRol == ADMINISTRADOR || userRol == OPERADOR) {
            //Validar que un operador NO asigne un administrador
            if (userRol == OPERADOR && rol == ADMINISTRADOR) {
                msj = "Su usuario no cuenta con el permiso de agregar a un "
                        + "administrador.\nSolo un administrador puede agregar,"
                        + " modificar o eliminar otro administrador.";
                //Validar que un operador NO modifique un administrador
            } else {
                try {
                    //Intentar convertir la cédula y validar su rango
                    cedula = Integer.parseInt(txtCedula.getText());
                    if (cedula > 0 && cedula <= 99999999) {
                        
                        //Intentar obtener el ID de la sucursal seleccionada
                        Object sucursal = boxSucursal.getSelectedItem();
                        String id = ((ComboItem)sucursal).getValue();
                        
                        //Validar que el id obtenido NO sea nulo
                        if(!id.isEmpty()){
                            //Convertir de String a Entero y validar su valor 
                            id_sucursal = Integer.valueOf(id);
                            if(id_sucursal > 0){
                                    
                                return true;
                                
                            } else {
                                msj = "El ID de la sucursal seleccionada es "
                                        + "inválida.\nPor favor, actualice el"
                                        + "programa y valide la existencia de"
                                        + "la sucursal.";
                            }
                        } else {
                            msj = "No se pudo obtener el ID de la sucursal "
                                    + "seleccionada.";
                        }
                    } else {
                        throw new NumberFormatException();
                    }

                } catch (Exception e) {
                    msj = "El valor de la cédula es incorrecto.\nDebe estar entre 1 y 99.999.999";
                }
            }
        } else {
            msjError("Su usuario no cuenta con los permisos para "
                    + "realizar esta acción.\nPor seguridad, el "
                    + "programa se cerrará.");
            //Cerrar el programa
            Run.cerrarPrograma();

            //Terminar de ejecutar el programa
            System.exit(0);

            return false;
        }

        //Ocultar el glassPane
        glass.setVisible(false);
        //Mostrar mensaje de errorr
        msjError(msj);

        return false;
    }

    /**
     * Función para validar la existencia del empleado en la base de datos
     * mediante el uso de la cédula del usuario seleccionado para su edición.
     *
     * @return
     */
    private boolean validarEmpleado() {
        String msj;
        id_empleado = AdminDB.getEmpleadoID(cedula);
        rol_empleado = AdminDB.getEmpleadoRol(cedula);
        int userRol = Frame.getUserRol();

        //Validar que el id del empleado sea mayor a 0
        if (id_empleado > 0) {
            //Validar que se obtuvo el rol del empleado
            if (rol_empleado > 0) {
                //Validar que el usuario TENGA el permiso de modificar el empleado
                if (userRol == OPERADOR && rol_empleado == ADMINISTRADOR) {
                    //Si el usuario es un operador y el empleado es un
                    //administrador, el usuario NO tendrá permitido modificarlo
                    msj = "Su usuario no cuenta con el permiso de modificar a un "
                        + "administrador.\nSolo un administrador puede agregar,"
                        + " modificar o eliminar otro administrador.";
                    
                } else {
                    return true;
                }
            } else {
                msj = "No se encontró el rol del empleado ingresado.\n"
                        + "Por favor, actualice la tabla de los empleados registrados\n"
                        + "y verifique su registro en la tabla.";
            }
        } else {
            msj = "La cédula ingresada del empleado, no se encuentra registrada.\n"
                    + "Por favor, actualice la tabla de los empleados y\n"
                    + "verifique su registro en la tabla.";
        }

        //Ocultar el glassPane
        glass.setVisible(false);
        //Mostrar mensaje de errorr
        msjError(msj);

        return false;
    }

    //ATRIBUTOS
    private static boolean crearEmpleado;
    private static String cargo;
    private static int id_empleado, cedula, rol, rol_empleado, id_sucursal;

    // ========== FRONTEND ==========
    /**
     * Constructor de la ventana para agregar o editar un empleado
     */
    public NuevoEmpleado() {
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

        //Fuente de letra para el combo box
        boxRol.setFont(segoe(16, PLANO));
        boxSucursal.setFont(segoe(16, PLANO));

        this.add(logo);
        this.add(lblTitulo);
        this.add(lblCedula);
        this.add(txtCedula);
        this.add(lblCargo);
        this.add(txtCargo);
        this.add(lblRol);
        this.add(boxRol);
        this.add(lblSucursal);
        this.add(boxSucursal);
        this.add(btnGuardar);
        this.add(btnCancelar);
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
     * Función para reposicionar y redimensionar los componentes
     */
    private void relocateComponents() {
        int width = paneSize.width;
        int height = paneSize.height;

        int gapV = 2;
        int paddingH = 40;
        int paddingV = 20;
        int fieldH = 40;
        int fieldW = width / 2 - paddingH - paddingV / 2;

        //Posición del logo
        int x = width / 2 - logo.getWidth() / 2;
        logo.setLocation(x, paddingV);

        //Posición y tamaño del título
        int y = paddingV * 2 + logo.getHeight();
        int w = width - paddingH * 2;
        lblTitulo.setLocation(paddingH, y);

        //Label y campo de texto de la cédula
        int lblY = y + lblTitulo.getHeight() + paddingV / 2;
        int txtY = lblY + lblCedula.getHeight() + gapV;
        lblCedula.setLocation(paddingH, lblY);
        txtCedula.setBounds(paddingH, txtY, fieldW, fieldH);

        //Label y combobox de los roles
        x = width - paddingH - fieldW;
        lblRol.setLocation(x, lblY);
        boxRol.setBounds(x, txtY, fieldW, fieldH);

        //Labels y campo de texto del cargo laboral
        lblY = txtY + fieldH + paddingV / 2;
        txtY = lblY + lblCargo.getHeight() + gapV;
        lblCargo.setLocation(paddingH, lblY);
        txtCargo.setBounds(paddingH, txtY, w, fieldH);

        //Labels y campo de texto del número
        lblY = txtY + fieldH + paddingV / 2;
        txtY = lblY + lblCargo.getHeight() + gapV;
        lblSucursal.setLocation(paddingH, lblY);
        boxSucursal.setBounds(paddingH, txtY, w, fieldH);

        //Botones
        y = height - paddingH - fieldH;
        btnCancelar.setBounds(paddingH, y, fieldW, fieldH);
        btnGuardar.setBounds(x, y, fieldW, fieldH);
    }

    /**
     * Función para agregar un nuevo cliente
     */
    protected void agregar() {
        crearEmpleado = true;

        //Label para el título
        lblTitulo.setText("Ingrese los datos para contratar un "
                + "empleado.");
        lblTitulo.setSize(lblTitulo.getPreferredSize());

        //Logo para la ventana
        logo.setText("Contratar Empleado");
        logo.setSize(logo.getPreferredSize());

        //Preparar los campos
        vaciarCampos();

        //Propiedades de la ventana
        this.setTitle("Agregar un cliente - AquaTech");
        this.relocateComponents();
        this.setVisible(true);
    }

    protected void contratar(String cedula) {
        crearEmpleado = true;

        //Label para el título
        lblTitulo.setText("Ingrese los datos para contratar un "
                + "empleado.");
        lblTitulo.setSize(lblTitulo.getPreferredSize());

        //Logo para la ventana
        logo.setText("Contratar Empleado");
        logo.setSize(logo.getPreferredSize());

        //Preparar los campos
        vaciarCampos();
        txtCedula.setText(cedula);

        //Propiedades de la ventana
        this.setTitle("Agregar un cliente - AquaTech");
        this.relocateComponents();
        this.setVisible(true);
    }

    /**
     * Función para editar un cliente registrado
     *
     * @param cedula
     * @param cargo
     * @param rol
     * @param sucursal
     */
    protected void editar(String cedula, String cargo, String rol, String sucursal) {
        crearEmpleado = false;

        vaciarCampos();

        //Label para el título
        lblTitulo.setText("Ingrese los datos del empleado "
                + "para actualizarlo.");
        lblTitulo.setSize(lblTitulo.getPreferredSize());

        //Logo para la ventana
        logo.setText("Editar Empleado");
        logo.setSize(logo.getPreferredSize());

        //Sobreescribir los campos
        txtCedula.setText(cedula);
        txtCargo.setText(cargo);

        //Determinar el index del rol
        int index = (rol.equals("ADMINISTRADOR")) ? 3
                : (rol.equals("OPERADOR")) ? 2 : 1;
        boxRol.setSelectedIndex(index);

        //Seleccionar el índice 0
        boxSucursal.setSelectedItem(0);
        //Capitalizar la sucursal enviada
        sucursal = sucursal.substring(0, 1).toUpperCase() + sucursal.substring(1).toLowerCase();

        //Buscar la sucursal ingresada y enfocar en el comboBox
        for (int i = 0; i < sucursales.length; i++) {
            //Obtener la sucursal actual y capitalizarla
            String capitalize = sucursales[i][1].toString();
            capitalize = capitalize.substring(0, 1).toUpperCase() + capitalize.substring(1).toLowerCase();

            //Comprobar que la sucursal enviada tenga la MISMA
            //descripción de algúna sucursal registrada.
            if (sucursal.equals(capitalize)) {
                boxSucursal.setSelectedIndex(i + 1);
                break;
            }
        }

        this.setTitle("Editar un cliente - AquaTech");
        this.relocateComponents();
        this.setVisible(true);
    }

    /**
     * Función para vaciar los campos de la ventana
     */
    protected void vaciarCampos() {
        //Vaciar los campos
        txtCedula.setText("");
        txtCargo.setText("");
        boxRol.setSelectedIndex(0);
        boxSucursal.setSelectedIndex(0);

        //Vaciar los atributos
//        id = 0;
//        cedula = 0;
//        cedulaVieja = null;
//        nombre = null;
//        apellido = null;
//        telefono = null;
    }

    protected boolean actualizarDatos() {
        //Cargar los sucursales
        sucursales = ReadDB.getSucursales();
        //Retornar falso si las sucursales están vacías
        if (sucursales == null) {
            return false;
        }

        //Vaciar el comboBox de las sucursales
        boxSucursal.removeAllItems();
        //Agregar el primer index
        boxSucursal.addItem("Seleccionar");

        //Guardar todas las sucursales
        for (Object[] sucursal : sucursales) {
            //Capitalizar las sucursales
            String capitalize = sucursal[1].toString();
            capitalize = capitalize.substring(0, 1).toUpperCase() + capitalize.substring(1).toLowerCase();
            //Agregar las sucursales
            boxSucursal.addItem(
                    new ComboItem(
                            capitalize,
                            sucursal[0].toString()
                    )
            );
        }
        //Retornar true al final
        return true;
    }

    /**
     * Función para vaciar los componentes y cerrar la ventana
     */
    private void salir() {
        if (msjYesNo("¿Está seguro de cancelar la operación?")) {
            vaciarCampos();
            dispose();
            crearEmpleado = false;
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
    private static final Dimension paneSize = new Dimension(450, 450);
    private static final Logo logo = new Logo(SwingConstants.HORIZONTAL);
    private static final Label lblTitulo = new Label("", TITULO, 16);

    private static final Label lblCedula = new Label("Cedula", PLANO, 16);
    private static final CampoTexto txtCedula = new CampoTexto("Cedula del usuario", NUMERO);

    private static final Label lblCargo = new Label("Cargo laboral", PLANO, 16);
    private static final CampoTexto txtCargo = new CampoTexto("Cargo laboral", NOMBRE);

    private static final Label lblRol = new Label("Rol del empleado", PLANO, 16);
    private static final String roles[] = {"Seleccionar", "Empleado - 1", "Operador - 2", "Administrador - 3"};
    private static final JComboBox boxRol = new JComboBox(roles);

    private static Object[][] sucursales;
    private static final Label lblSucursal = new Label("Sucursal asociado", PLANO, 16);
    private static final JComboBox boxSucursal = new JComboBox();

    private static final Boton btnGuardar = new Boton("Guardar", VERDE);
    private static final Boton btnCancelar = new Boton("Cancelar", NARANJA);

    private class ComboItem {

        private String key;
        private String value;

        public ComboItem(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}