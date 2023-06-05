package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import database.AdminDB;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static javax.swing.BorderFactory.createEtchedBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import properties.Colores;
import properties.Constantes;
import static properties.Fuentes.segoe;
import properties.Mensaje;
import properties.ValidarTexto;
import tabs.compras.Compras;
import tabs.ventas.Ventas;

/**
 *
 * @author diego
 */
public class AjustesGenerales extends JScrollPane implements properties.Constantes {

    // ========== BACKEND ==========
    private void guardar(boolean oeste) {
        new Thread() {
            @Override
            public void run() {
                main.Frame.openGlass(0);

                //Obtener el rol del usuario
                int rol = database.ReadDB.getUserRol(main.Frame.getUserIdentified());

                if (oeste) {
                    if (validarCampos(true)) {
                        if (validarDatos(true)) {
                            if(Mensaje.msjYesNo("¿Está seguro de guardar los cambios?")){
                                if(AdminDB.validateAdminUser()){
                                    if(AdminDB.updatePrecios(trasvasos, ventas)){
                                        Ventas.actualizarDatos();
                                        Compras.actualizarDatos();
                                    }
                                }
                            }
                        }
                    }

                } else if (rol == Constantes.ADMINISTRADOR) {
                    if (validarCampos(false)) {
                        if (validarDatos(false)) {
                            if(Mensaje.msjYesNo("¿Está seguro de guardar los cambios?")){
                                if(AdminDB.validateAdminUser()){
                                    AdminDB.updateBanco(banco, cuenta, telefono);
                                }
                            }
                        }
                    }
                } else {
                    Mensaje.msjError("Usted no cuenta con los permisos para "
                            + "realizar\ncambios en los datos bancarios de la franquicia.");
                }

                main.Frame.closeGlass();
            }
        }.start();
    }

    private boolean validarDatos(boolean oeste) {
        String msj;

        if (oeste) {
            try {
                trasvasos = Double.parseDouble(txtTrasvaso.getText());
                if (trasvasos > 0) {
                    try {
                        ventas = Double.parseDouble(txtVenta.getText());
                        if (ventas > 0) {

                            return true;

                        } else {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        msj = "El precio de las ventas es inválido.\nPor favor, "
                                + "verifique sus datos.";
                    }
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                msj = "El precio de los trasvasos es inválido.\nPor favor, "
                        + "verifique sus datos.";
            }

        } else {
            if (ValidarTexto.formatoNombre(banco)) {
                if (ValidarTexto.formatoTelefono(telefono)) {
                    if (ValidarTexto.formatoCuentaBancaria(cuenta)) {
                        
                        return true;
                        
                    } else {
                        msj = "El número de cuenta bancaria es inválido.\nPor favor,"
                                + "verifique sus datos.";
                    }
                } else {
                    msj = "El número de teléfono es inválido.\nPor favor,"
                            + "verifique sus datos.";
                }
            } else {
                msj = "El nombre del banco es inválido.\nPor favor,"
                        + "verifique sus datos.";
            }
        }

        main.Frame.closeGlass();
        Mensaje.msjError(msj);

        return false;
    }

    private boolean validarCampos(boolean oeste) {
        String msj;

        if (oeste) {
            String trasv = txtTrasvaso.getText();
            String vent = txtVenta.getText();

            if (!trasv.isEmpty()) {
                if (!vent.isEmpty()) {

                    return true;

                } else {
                    msj = "El precio de venta no puede estar vacío.\nPor favor,"
                            + "ingrese los datos.";
                }
            } else {
                msj = "El precio de trasvaso no puede estar vacío.\nPor favor,"
                        + "ingrese los datos.";
            }

        } else {
            banco = txtBanco.getText().toUpperCase().trim();
            cuenta = txtNumero.getText();
            telefono = txtTelefono.getText();

            if (!banco.isEmpty()) {
                if (!cuenta.isEmpty()) {
                    if (!telefono.isEmpty()) {

                        return true;

                    } else {
                        msj = "El número de teléfono no puede estar vacío.\nPor favor,"
                                + "ingrese los datos.";
                    }
                } else {
                    msj = "El número de cuenta no puede estar vacío.\nPor favor,"
                            + "ingrese los datos.";
                }
            } else {
                msj = "El nombre del banco no puede estar vacío.\nPor favor,"
                        + "ingrese los datos.";
            }
        }

        main.Frame.closeGlass();
        Mensaje.msjError(msj);

        return false;
    }

    private void listeners() {
        guardarPrecios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guardar(true);
            }
        });

        guardarBanco.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guardar(false);
            }
        });
    }

    //ATRIBUTOS BACKEND
    private static double trasvasos, ventas;
    private static String banco, cuenta, telefono;

    // ========== FRONTEND ==========
    public AjustesGenerales() {
        this.setOpaque(false);
        this.setBorder(null);
        this.getVerticalScrollBar().setUnitIncrement(8);
        this.getViewport().setOpaque(false);
        this.setViewportView(contenedor);

        initComponents();
        listeners();
    }

    private void initComponents() {
        contenedor.setBackground(Colores.BLANCO);

        //========== ZONA SUPERIOR ==========
        JPanel norte = new JPanel(new GridBagLayout());
        norte.setOpaque(false);

        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.insets = new Insets(PADDING, 0, PADDING / 2, 0);
        norte.add(titulo, bag);
        contenedor.add(norte, BorderLayout.NORTH);

        //========== ZONA DEL MEDIO ==========
        grid.setColumns(2);
        grid.setRows(1);
        JPanel contenedor_centro = new JPanel(grid);
        contenedor_centro.setOpaque(false);
        contenedor.add(contenedor_centro, BorderLayout.CENTER);

        //========== ZONA OESTE ==========
        contenedor_oeste.setOpaque(false);
        //Añadir el contenedor del oeste, al contenedor centrado inferior
        contenedor_centro.add(contenedor_oeste);

        //Posición centrada, maximizada y con un margin interno
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 0;
        bag.gridheight = 0;
        bag.weightx = 1.0;
        bag.weighty = 1.0;
        bag.fill = GridBagConstraints.BOTH;
        bag.insets = new Insets(0, PADDING, PADDING, 10);

        //========== PANEL DEL OESTE ==========
        oeste.setOpaque(false);
        //Añadir al contenedor del oeste, el panel de componentes del oeste
        contenedor_oeste.add(oeste, bag);
        oeste.setBorder(
                createTitledBorder(
                        createEtchedBorder(),
                        "Ajuste de Precios",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        segoe(16, TITULO)
                )
        );
        //Panel del sur, del panel del oeste
        JPanel oeste_sur = new JPanel(new FlowLayout(FlowLayout.RIGHT, PADDING, PADDING));
        oeste_sur.setOpaque(false);
        //Tamaño preferido del botón
        guardarPrecios.setPreferredSize(new Dimension(guardarPrecios.getPreferredSize().width + 100, 40));
        //agregar el botón de guardar
        oeste_sur.add(guardarPrecios);

        //Panel del centro, del panel del oeste
        JPanel oeste_centro = new JPanel(new GridBagLayout());
        oeste_centro.setOpaque(false);

        //Añadir ambos paneles al panel de componentes del oeste
        oeste.add(oeste_sur, BorderLayout.SOUTH);
        oeste.add(oeste_centro, BorderLayout.CENTER);

        //========== COMPONENTES DEL PANEL DEL OESTE ==========
        //Propiedades del Label de trasvaso
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.insets = new Insets(0, PADDING, PADDING, PADDING / 2);
        oeste_centro.add(lblTrasvaso, bag);

        //Propiedades del campo de texto de trasvasos
        bag = new GridBagConstraints();
        bag.gridx = 1;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridwidth = 1;
        bag.weightx = 1.0;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, 0, PADDING, PADDING);
        txtTrasvaso.setPreferredSize(new Dimension(txtTrasvaso.getPreferredSize().width, 40));
        oeste_centro.add(txtTrasvaso, bag);

        //Propiedades del label de ventas
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 1;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, PADDING, 0, PADDING / 2);
        lblVenta.setHorizontalAlignment(JLabel.TRAILING);
        oeste_centro.add(lblVenta, bag);

        //Propiedades del campo de texto de ventas
        bag = new GridBagConstraints();
        bag.gridx = 1;
        bag.gridy = 1;
        bag.gridwidth = 1;
        bag.gridwidth = 1;
        bag.weightx = 1.0;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, 0, 0, PADDING);
        txtVenta.setPreferredSize(new Dimension(txtVenta.getPreferredSize().width, 40));
        oeste_centro.add(txtVenta, bag);

        //========== ZONA ESTE ==========
        contenedor_este.setOpaque(false);
        //Añadir el contenedor del este, al contenedor centrado inferior
        contenedor_centro.add(contenedor_este);

        //Posición centrada, maximizada y con un margin interno
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 0;
        bag.gridheight = 0;
        bag.weightx = 1.0;
        bag.weighty = 1.0;
        bag.fill = GridBagConstraints.BOTH;
        bag.insets = new Insets(0, 10, PADDING, PADDING);

        //========== PANEL DEL ESTE ==========
        este.setOpaque(false);
        //Añadir al contenedor del este, el panel de componentes del este
        contenedor_este.add(este, bag);
        este.setBorder(
                createTitledBorder(
                        createEtchedBorder(),
                        "Datos Bancarios",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        segoe(16, TITULO)
                )
        );

        //Panel del sur, del panel del oeste
        JPanel este_sur = new JPanel(new FlowLayout(FlowLayout.RIGHT, PADDING, PADDING));
        este_sur.setOpaque(false);
        //Tamaño preferido del botón
        guardarBanco.setPreferredSize(new Dimension(guardarBanco.getPreferredSize().width + 100, 40));
        //agregar el botón de guardar
        este_sur.add(guardarBanco);

        //Panel del centro, del panel del oeste
        JPanel este_centro = new JPanel(new GridBagLayout());
        este_centro.setOpaque(false);

        //Añadir ambos paneles al panel de componentes del oeste
        este.add(este_sur, BorderLayout.SOUTH);
        este.add(este_centro, BorderLayout.CENTER);

        //========== COMPONENTES DEL PANEL DEL ESTE ==========
        //Propiedades del Label del teléfono
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 2;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.insets = new Insets(PADDING, PADDING, 0, PADDING / 2);
        lblTelefono.setHorizontalAlignment(JLabel.TRAILING);
        este_centro.add(lblTelefono, bag);
        //Propiedades del campo de texto del teléfono
        bag = new GridBagConstraints();
        bag.gridx = 1;
        bag.gridy = 2;
        bag.gridwidth = 1;
        bag.gridwidth = 1;
        bag.weightx = 1.0;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(PADDING, 0, 0, PADDING);
        txtTelefono.setPreferredSize(new Dimension(txtTelefono.getPreferredSize().width, 40));
        este_centro.add(txtTelefono, bag);

        //Propiedades del Label del número de cuenta
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 1;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(PADDING, PADDING, 0, PADDING / 2);
        lblNumero.setHorizontalAlignment(JLabel.TRAILING);
        este_centro.add(lblNumero, bag);

        //Propiedades del campo de texto del número de cuenta
        bag = new GridBagConstraints();
        bag.gridx = 1;
        bag.gridy = 1;
        bag.gridwidth = 1;
        bag.gridwidth = 1;
        bag.weightx = 1.0;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(PADDING, 0, 0, PADDING);
        txtNumero.setPreferredSize(new Dimension(txtNumero.getPreferredSize().width, 40));
        este_centro.add(txtNumero, bag);

        //Propiedades del Label del nombre de banco
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, PADDING, 0, PADDING / 2);
        lblBanco.setHorizontalAlignment(JLabel.TRAILING);
        este_centro.add(lblBanco, bag);

        //Propiedades del campo de texto del banco
        bag = new GridBagConstraints();
        bag.gridx = 1;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridwidth = 1;
        bag.weightx = 1.0;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, 0, 0, PADDING);
        txtBanco.setPreferredSize(new Dimension(txtBanco.getPreferredSize().width, 40));
        este_centro.add(txtBanco, bag);
    }

    protected void relocateComponents(int parentWidth, int parentHeight) {
        //Determinar si el contenedor central tendrá una o dos columnas
        if (parentWidth <= 710) {
            //Asignar una columna y dos filas
            grid.setColumns(1);
            grid.setRows(2);

            //Propiedades del gridbag del oeste
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 0;
            bag.gridwidth = 0;
            bag.gridheight = 0;
            bag.weightx = 1.0;
            bag.weighty = 1.0;
            bag.fill = GridBagConstraints.BOTH;
            bag.insets = new Insets(0, PADDING, PADDING, PADDING);
            //Agregar el panel al contenedor con los límites
            contenedor_oeste.add(oeste, bag);

            //Propiedades del gridbag del este
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 0;
            bag.gridwidth = 0;
            bag.gridheight = 0;
            bag.weightx = 1.0;
            bag.weighty = 1.0;
            bag.fill = GridBagConstraints.BOTH;
            bag.insets = new Insets(0, PADDING, PADDING, PADDING);
            //Agregar el panel al contenedor con los límites
            contenedor_este.add(este, bag);

            if (parentHeight < 730) {
                contenedor.setSize(new Dimension(contenedor.getPreferredSize().width, 730));
            }

        } else {
            grid.setColumns(2);
            grid.setRows(1);

            //Propiedades del gridbag del oeste
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 0;
            bag.gridwidth = 0;
            bag.gridheight = 0;
            bag.weightx = 1.0;
            bag.weighty = 1.0;
            bag.fill = GridBagConstraints.BOTH;
            bag.insets = new Insets(0, PADDING, PADDING, PADDING / 2);
            //Agregar el panel al contenedor con los límites
            contenedor_oeste.add(oeste, bag);

            //Propiedades del gridbag del este
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 0;
            bag.gridwidth = 0;
            bag.gridheight = 0;
            bag.weightx = 1.0;
            bag.weighty = 1.0;
            bag.fill = GridBagConstraints.BOTH;
            bag.insets = new Insets(0, PADDING / 2, PADDING, PADDING);

        }
    }

    protected boolean actualizarDatos() {
        Object[] datos = database.AdminDB.getAjustes();

        if (datos != null) {
            if (datos.length > 0) {
                txtTrasvaso.setText(datos[0].toString());
                txtVenta.setText(datos[1].toString());
                txtBanco.setText(datos[2].toString());
                txtNumero.setText(datos[3].toString());
                txtTelefono.setText(datos[4].toString());

            } else {
                txtTrasvaso.setText("");
                txtVenta.setText("");
                txtBanco.setText("");
                txtNumero.setText("");
                txtTelefono.setText("");

            }
            return true;

        } else {
            return false;
        }
    }

    protected void vaciarCampos() {
        txtTrasvaso.setText("");
        txtVenta.setText("");
        txtBanco.setText("");
        txtNumero.setText("");
        txtTelefono.setText("");
    }

    protected void habilitarComponents(boolean estado) {
        txtTrasvaso.setEnabled(estado);
        txtVenta.setEnabled(estado);
        txtBanco.setEnabled(estado);
        txtNumero.setEnabled(estado);
        txtTelefono.setEnabled(estado);
    }

    //ATRIBUTOS FRONTEND
    private static final int PADDING = 20;
    private static final GridLayout grid = new GridLayout();

    //COMPONENTES
    private static GridBagConstraints bag = new GridBagConstraints();
    private static final JPanel contenedor = new JPanel(new BorderLayout());
    private static final Label titulo = new Label("Ajustes Generales", TITULO, 24);

    //Componentes para los precios
    private static final JPanel contenedor_oeste = new JPanel(new GridBagLayout());
    private static final JPanel oeste = new JPanel(new BorderLayout());
    private static final Label lblTrasvaso = new Label("Precio Trasvaso", PLANO, 16);
    private static final CampoTexto txtTrasvaso = new CampoTexto("Precio del trasvaso (Bs)", DECIMAL);
    private static final Label lblVenta = new Label("Precio Venta", PLANO, 16);
    private static final CampoTexto txtVenta = new CampoTexto("Precio de Venta (Bs)", DECIMAL);
    private static final Boton guardarPrecios = new Boton("Guardar", properties.Colores.VERDE);

    //Componentes de los datos bancarios
    private static final JPanel contenedor_este = new JPanel(new GridBagLayout());
    private static final JPanel este = new JPanel(new BorderLayout());
    private static final Label lblBanco = new Label("Banco", PLANO, 16);
    private static final CampoTexto txtBanco = new CampoTexto("Nombre del banco", NOMBRE);
    private static final Label lblNumero = new Label("N° Cuenta", PLANO, 16);
    private static final CampoTexto txtNumero = new CampoTexto("Numero de la cuenta", NUMERO);
    private static final Label lblTelefono = new Label("Teléfono", PLANO, 16);
    private static final CampoTexto txtTelefono = new CampoTexto("Teléfono afiliado", NUMERO);
    private static final Boton guardarBanco = new Boton("Guardar", properties.Colores.CELESTE);
}
