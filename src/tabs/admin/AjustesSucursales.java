package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.Tabla;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import properties.Colores;
import static properties.Constantes.CUALQUIER;
import static properties.Constantes.DECIMAL;
import static properties.Constantes.PLANO;
import static properties.Constantes.TITULO;
import static properties.Constantes.TODAS_SUCURSALES;

public class AjustesSucursales extends JPanel {

    // ========== BACKEND ==========
    private void listeners() {

    }

    //ATRIBUTOS BACKEND
    private static boolean crearSucursal = true;
    private static int id_sucursal;

    // ========== FRONTEND ==========
    public AjustesSucursales() {
        this.setBackground(Colores.BLANCO);
        this.setLayout(new BorderLayout());

        initComponents();
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
            bag.insets = new Insets(0, PADDING, PADDING/4, 0);
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
            bag.insets = new Insets(0, PADDING, PADDING/4, PADDING);
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
    private static final CampoTexto txtDescripción = new CampoTexto("Descripción de la sucursal", DECIMAL);

    private static final Label lblTelefono = new Label("Teléfono", PLANO, 16);
    private static final CampoTexto txtTelefono = new CampoTexto("Teléfono", DECIMAL);

    private static final Label lblCoordenadas = new Label("Dirección", PLANO, 16);
    private static final CampoTexto txtCoordenadas = new CampoTexto("Coordenadas de la sucursal", CUALQUIER);

    private static final Label lblBotellones = new Label("Botellones", PLANO, 16);
    private static final CampoTexto txtBotellones = new CampoTexto("Cantidad", CUALQUIER);

    private static final Boton guardar = new Boton("Guardar", properties.Colores.VERDE);
    private static final Boton cancelar = new Boton("Cancelar", properties.Colores.NARANJA);
}
