package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.Tabla;
import database.AdminDB;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import properties.Colores;
import properties.Constantes;
import static properties.Constantes.CUALQUIER;
import static properties.Constantes.NUMERO;
import static properties.Constantes.NOMBRE;
import static properties.Constantes.PLANO;
import static properties.Constantes.TITULO;
import static properties.Constantes.TODAS_SUCURSALES;
import properties.Mensaje;
import properties.ValidarTexto;

public class AjustesSucursales extends JPanel {

    // ========== BACKEND ==========
    private void guardar() {
        new Thread() {
            @Override
            public void run() {
                main.Frame.openGlass(0);

                //Obtener el rol del usuario
                int rol = database.ReadDB.getUserRol(main.Frame.getUserIdentified());

                if (rol == Constantes.ADMINISTRADOR) {
                    if (validarCampos()) {
                        if (validarDatos()) {
                            if (crearSucursal) {
                                if (Mensaje.msjYesNo("¿Está seguro de registrar la nueva sucursal?")) {
                                    AdminDB.createSucursal(descripcion, telefono, direccion, botellones);
                                }
                            } else {
                                if (Mensaje.msjYesNo("¿Está seguro de modificar la sucursal?")) {
                                    AdminDB.updateSucursal(id_sucursal, descripcion, telefono, direccion, botellones);
                                }
                            }
                        }
                    }
                } else {
                    Mensaje.msjError("Su usuario no cuenta con los permisos para"
                            + " realizar esta acción");
                }

                main.Frame.closeGlass();
            }
        }.start();
    }

    private boolean validarDatos() {
        String msj;

        if (ValidarTexto.formatoNombreEmpresa(descripcion)) {
            if (ValidarTexto.formatoTelefono(telefono)) {
                if (ValidarTexto.formatoDireccion(direccion)) {
                    try {
                        botellones = Integer.parseInt(txtBotellones.getText());
                        if (botellones >= 0) {
                            if (botellones > 99) {
                                return Mensaje.msjYesNoWarning("La cantidad de "
                                        + "botellones, en la sucursal, es muy "
                                        + "elevada.\n¿Está seguro de asignar esa"
                                        + "cantidad?");
                            } else {
                                return true;
                            }
                        } else {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        msj = "La cantidad de botellones es inválida.\nPor favor, verifique sus datos.";
                    }
                } else {
                    msj = "La dirección de la sucursal es inválida.\nPor favor, verifique sus datos.";
                }
            } else {
                msj = "El formato del teléfono es incorrecto.\nPor favor, verifique sus datos.";
            }
        } else {
            msj = "El formato del nombre es incorrecto.\nPor favor, verifique sus datos.";

        }

        Mensaje.msjError(msj);

        return false;
    }

    private boolean validarCampos() {
        String msj;
        descripcion = txtDescripción.getText().trim().toUpperCase();
        direccion = txtCoordenadas.getText().trim();
        telefono = txtTelefono.getText().trim();
        String bots = txtBotellones.getText().trim();

        if (!descripcion.isEmpty()) {
            if (!direccion.isEmpty()) {
                if (!telefono.isEmpty()) {
                    if (!bots.isEmpty()) {

                        return true;

                    } else {
                        msj = "La cantidad de botellones de la sucursal no puede estár vacío.\nPor favor, ingrese los datos.";
                    }
                } else {
                    msj = "El teléfono de la sucursal no puede estár vacío.\nPor favor, ingrese los datos.";
                }
            } else {
                msj = "La dirección no puede estár vacío.\nPor favor, ingrese los datos.";
            }
        } else {
            msj = "La descripción no puede estár vacío.\nPor favor, ingrese los datos.";
        }

        Mensaje.msjError(msj);

        return false;
    }

    private void listeners() {
        cancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                vaciarCampos();
                crearSucursal = true;
            }
        });

        guardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                guardar();
            }
        });
    }

    //ATRIBUTOS BACKEND
    private static boolean crearSucursal = true;
    private static int id_sucursal, botellones;
    private static String descripcion, direccion, telefono;

    // ========== FRONTEND ==========
    public AjustesSucursales() {
        this.setBackground(Colores.BLANCO);
        this.setLayout(new BorderLayout());

        initComponents();
        listeners();
    }

    private void initComponents() {
        // ========== ZONA NORTE ==========
        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);

        JPanel titulo = new JPanel(new GridBagLayout());
        titulo.setOpaque(false);

        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.insets = new Insets(20, 0, 0, 0);
        titulo.add(lblTitulo, bag);

        // ==========  COMPONENTES ==========
        int fieldH = 35;
        guardar.setPreferredSize(new Dimension(guardar.getPreferredSize().width, fieldH));
        cancelar.setPreferredSize(new Dimension(cancelar.getPreferredSize().width, fieldH));

        lblDescripción.setHorizontalAlignment(JLabel.LEADING);
        txtDescripción.setPreferredSize(new Dimension(txtDescripción.getPreferredSize().width, fieldH));

        lblTelefono.setHorizontalAlignment(JLabel.LEADING);
        txtTelefono.setPreferredSize(new Dimension(txtTelefono.getPreferredSize().width, fieldH));

        lblCoordenadas.setHorizontalAlignment(JLabel.LEADING);
        txtCoordenadas.setPreferredSize(new Dimension(txtCoordenadas.getPreferredSize().width, fieldH));

        lblBotellones.setHorizontalAlignment(JLabel.LEADING);
        txtBotellones.setPreferredSize(new Dimension(txtBotellones.getPreferredSize().width, fieldH));

        // ========== ZONA CENTRAL ==========
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.weightx = 1.0;
        bag.weighty = 1.0;
        bag.fill = GridBagConstraints.BOTH;
        bag.insets = new Insets(0, PADDING, PADDING, PADDING);
        centro.add(tabla, bag);

        norte.add(titulo, BorderLayout.NORTH);
        norte.add(datos, BorderLayout.CENTER);
        this.add(norte, BorderLayout.NORTH);
        this.add(centro, BorderLayout.CENTER);
    }

    protected void relocateComponents(int parentWidth, int parentHeight) {
        //Determinar si el contenedor central tendrá dos o cuatro filas
        if (parentWidth <= 470) {
            // ========== PRIMERA FILA ==========
            //Label de la descripción
            datos.setOpaque(false);
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 0;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, 0, 0);
            datos.add(lblDescripción, bag);
            //Campo de texto de la descripción
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 1;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.5;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING / 4, 0);
            datos.add(txtDescripción, bag);

            //Label de las coordenadas
            bag = new GridBagConstraints();
            bag.gridx = 1;
            bag.gridy = 0;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, 0, PADDING);
            datos.add(lblCoordenadas, bag);
            //Campo de texto de las coordenadas
            bag = new GridBagConstraints();
            bag.gridx = 1;
            bag.gridy = 1;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.5;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING / 4, PADDING);
            datos.add(txtCoordenadas, bag);

            // ========== SEGUNDA FILA ==========
            //Label del número de teléfono
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 2;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, 0, 0);
            datos.add(lblTelefono, bag);
            //Campo de texto del número de teléfono
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 3;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.5;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING, 0);
            datos.add(txtTelefono, bag);

            //Label de la cantidad de botellones
            bag = new GridBagConstraints();
            bag.gridx = 1;
            bag.gridy = 2;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, 0, PADDING);
            datos.add(lblBotellones, bag);
            //Campo de texto de la cantidad de botellones
            bag = new GridBagConstraints();
            bag.gridx = 1;
            bag.gridy = 3;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.5;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING, PADDING);
            datos.add(txtBotellones, bag);

            // ========== TERCERA FILA ==========
            //Botón de aceptar
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 4;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.5;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING, 0);
            datos.add(guardar, bag);

            //Botón de cancelar
            bag = new GridBagConstraints();
            bag.gridx = 1;
            bag.gridy = 4;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.5;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING, PADDING);
            datos.add(cancelar, bag);

        } else {
            // ========== PRIMERA FILA ==========
            //Label de la descripción
            datos.setOpaque(false);
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 0;
            bag.gridwidth = 2;
            bag.gridheight = 1;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, 0, 0);
            datos.add(lblDescripción, bag);
            //Campo de texto de la descripción
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 1;
            bag.gridwidth = 2;
            bag.gridheight = 1;
            bag.weightx = 0.5;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, 0, 0);
            datos.add(txtDescripción, bag);

            //Label de las coordenadas
            bag = new GridBagConstraints();
            bag.gridx = 2;
            bag.gridy = 0;
            bag.gridwidth = 2;
            bag.gridheight = 1;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, 0, PADDING);
            datos.add(lblCoordenadas, bag);
            //Campo de texto de las coordenadas
            bag = new GridBagConstraints();
            bag.gridx = 2;
            bag.gridy = 1;
            bag.gridwidth = 2;
            bag.gridheight = 1;
            bag.weightx = 0.5;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, 0, PADDING);
            datos.add(txtCoordenadas, bag);

            // ========== SEGUNDA FILA ==========
            // ========== PRIMERA COLUMNA ==========
            //Label del número de teléfono
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 2;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(PADDING / 2, PADDING, 0, 0);
            datos.add(lblTelefono, bag);
            //Campo de texto del número de teléfono
            bag = new GridBagConstraints();
            bag.gridx = 0;
            bag.gridy = 3;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.30;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING, 0);
            datos.add(txtTelefono, bag);

            // ========== SEGUNDA COLUMNA ==========
            //Label de la cantidad de botellones
            bag = new GridBagConstraints();
            bag.gridx = 1;
            bag.gridy = 2;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(PADDING / 2, PADDING, 0, 0);
            datos.add(lblBotellones, bag);
            //Campo de texto de la cantidad de botellones
            bag = new GridBagConstraints();
            bag.gridx = 1;
            bag.gridy = 3;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.23;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING, 0);
            datos.add(txtBotellones, bag);

            // ========== TERCER COLUMNA ==========
            //Botón de aceptar
            bag = new GridBagConstraints();
            bag.gridx = 2;
            bag.gridy = 3;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.24;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING, 0);
            datos.add(guardar, bag);

            // ========== CUARTA COLUMNA ==========
            //Botón de cancelar
            bag = new GridBagConstraints();
            bag.gridx = 3;
            bag.gridy = 3;
            bag.gridwidth = 1;
            bag.gridheight = 1;
            bag.weightx = 0.23;
            bag.fill = GridBagConstraints.HORIZONTAL;
            bag.insets = new Insets(0, PADDING, PADDING, PADDING);
            datos.add(cancelar, bag);
        }
    }

    protected boolean actualizarDatos() {
        tabla.actualizarDatos();
        return true;
    }

    public static void vaciarCampos() {
        txtDescripción.setText("");
        txtTelefono.setText("");
        txtCoordenadas.setText("");
        txtBotellones.setText("");
    }

    public static void editSucursal(String id, String descripcion, String telefono, String cantidad, String coords) {
        crearSucursal = false;
        id_sucursal = Integer.parseInt(id);
        txtDescripción.setText(descripcion);
        txtTelefono.setText(telefono);
        txtBotellones.setText(cantidad);
        txtCoordenadas.setText(coords);
    }

    protected void habilitarComponents(boolean estado) {

    }

    //COMPONENTES
    private static GridBagConstraints bag;
    private static final int PADDING = 20;
    private static final JPanel datos = new JPanel(new GridBagLayout());
    private static final Label lblTitulo = new Label("Ajustes Sucursales", TITULO, 24);
    private static final Tabla tabla = new Tabla(TODAS_SUCURSALES);

    private static final Label lblDescripción = new Label("Descripción", PLANO, 16);
    private static final CampoTexto txtDescripción = new CampoTexto("Descripción de la sucursal", NOMBRE);

    private static final Label lblTelefono = new Label("Teléfono", PLANO, 16);
    private static final CampoTexto txtTelefono = new CampoTexto("Teléfono", NUMERO);

    private static final Label lblCoordenadas = new Label("Dirección", PLANO, 16);
    private static final CampoTexto txtCoordenadas = new CampoTexto("Coordenadas de la sucursal", CUALQUIER);

    private static final Label lblBotellones = new Label("Botellones", PLANO, 16);
    private static final CampoTexto txtBotellones = new CampoTexto("Cantidad", NUMERO);

    private static final Boton guardar = new Boton("Guardar", properties.Colores.VERDE);
    private static final Boton cancelar = new Boton("Cancelar", properties.Colores.NARANJA);
}
