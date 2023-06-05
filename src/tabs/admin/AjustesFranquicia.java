package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import properties.Colores;
import properties.Constantes;
import static properties.Constantes.NUMERO;
import static properties.Constantes.NOMBRE;
import static properties.Constantes.PLANO;
import static properties.Constantes.TITULO;
import static properties.Constantes.CUALQUIER;
import properties.Mensaje;
import properties.ValidarTexto;

public class AjustesFranquicia extends JPanel {

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
                            if (Mensaje.msjYesNo("¿Está seguro de aplicar los cambios a la franquicia?")) {
                                if(database.AdminDB.validateAdminUser()){
                                    database.AdminDB.updateFranquicia(nombre, rif, nit);
                                }
                            }
                            main.Frame.closeGlass();
                        }
                    }
                } else {
                    main.Frame.closeGlass();
                    Mensaje.msjError("Usted no cuenta con los permisos para\n"
                            + "realizar cambios en la franquicia.");
                }
            }
        }.start();
    }

    private boolean validarDatos() {
        String msj;

        if (ValidarTexto.formatoNombreEmpresa(nombre)) {
            if (ValidarTexto.formatoRIF(rif)) {
                if(ValidarTexto.formatoNIT(nit)){
                    
                    return true;
                    
                } else {
                    msj = "El formato del NIT es incorecto.\nPor favor, verifique sus datos.";
                }
            } else {
                msj = "El formato del RIF es incorrecto.\nPor favor, verifique sus datos.";
            }
        } else {
            msj = "El formato del nombre es incorrecto.\nPor favor, verifique sus datos.";

        }

        main.Frame.closeGlass();
        Mensaje.msjError(msj);

        return false;
    }

    private boolean validarCampos() {
        String msj;
        nombre = txtNombre.getText().toUpperCase();
        rif = txtRIF.getText().toUpperCase();
        nit = txtNIT.getText();

        if (!nombre.isEmpty()) {
            if (!rif.isEmpty()) {
                if (!nit.isEmpty()) {

                    return true;

                } else {
                    msj = "El NIT no puede estar vacío.\nPor favor, ingrese los datos.";
                }
            } else {
                msj = "El RIF no puede estar vacío.\nPor favor, ingrese los datos.";
            }
        } else {
            msj = "El nombre no puede estar vacío.\nPor favor, ingrese los datos.";
        }

        main.Frame.closeGlass();
        Mensaje.msjError(msj);

        return false;
    }

    private void listeners() {
        cancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                vaciarCampos();
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
    private static String nombre, rif, nit;

    // ========== FRONTEND ==========
    public AjustesFranquicia() {
        this.setBackground(Colores.BLANCO);
        this.setLayout(new BorderLayout());

        initComponents();
        listeners();
    }

    private void initComponents() {
        // ========== ZONA NORTE ==========
        JPanel norte = new JPanel(new GridBagLayout());
        norte.setOpaque(false);
        this.add(norte, BorderLayout.NORTH);

        GridBagConstraints bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.insets = new Insets(PADDING, 0, 0, 0);
        norte.add(titulo, bag);

        // ========== ZONA SUR ==========
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.TRAILING, PADDING, PADDING));
        this.add(sur, BorderLayout.SOUTH);
        sur.add(cancelar);
        sur.add(guardar);
        sur.setOpaque(false);

        // ========== ZONA CENTRAL ==========
        JPanel centro = new JPanel(new GridBagLayout());
        this.add(centro, BorderLayout.CENTER);
        centro.setOpaque(false);

        // ========== COMPONENTES ==========
        int fieldH = 40;
        guardar.setPreferredSize(new Dimension(guardar.getPreferredSize().width + 100, fieldH));
        cancelar.setPreferredSize(new Dimension(cancelar.getPreferredSize().width + 100, fieldH));

        lblNombre.setHorizontalAlignment(JLabel.TRAILING);
        txtNombre.setPreferredSize(new Dimension(txtNombre.getPreferredSize().width, fieldH));

        lblRIF.setHorizontalAlignment(JLabel.TRAILING);
        txtRIF.setPreferredSize(new Dimension(txtRIF.getPreferredSize().width, fieldH));

        lblNIT.setHorizontalAlignment(JLabel.TRAILING);
        txtNIT.setPreferredSize(new Dimension(txtNIT.getPreferredSize().width, fieldH));

        // ========== AGREGAR LOS COMPONENTES ==========
        //Propiedades para el label del nombre
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.insets = new Insets(0, PADDING * 2, PADDING, PADDING / 2);
        centro.add(lblNombre, bag);

        //Propiedades para el label del RIF
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 1;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, PADDING * 2, PADDING, PADDING / 2);
        centro.add(lblRIF, bag);

        //Propiedades para el label del NIT
        bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 2;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, PADDING * 2, 0, PADDING / 2);
        centro.add(lblNIT, bag);

        //Propiedades para el campo de texto del nombre
        bag = new GridBagConstraints();
        bag.gridx = 1;
        bag.gridy = 0;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.weightx = 1.0;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, 0, PADDING, PADDING * 2);
        centro.add(txtNombre, bag);

        //Propiedades para el campo de texto del nombre
        bag = new GridBagConstraints();
        bag.gridx = 1;
        bag.gridy = 1;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.weightx = 1.0;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, 0, PADDING, PADDING * 2);
        centro.add(txtRIF, bag);

        //Propiedades para el campo de texto del nombre
        bag = new GridBagConstraints();
        bag.gridx = 1;
        bag.gridy = 2;
        bag.gridwidth = 1;
        bag.gridheight = 1;
        bag.weightx = 1.0;
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, 0, 0, PADDING * 2);
        centro.add(txtNIT, bag);
    }

    protected boolean actualizarDatos() {
        Object[] datos = database.AdminDB.getFranquicia();

        if (datos != null) {
            if (datos.length > 0) {
                txtNombre.setText(datos[0].toString());
                txtRIF.setText(datos[1].toString());
                txtNIT.setText(datos[2].toString());

            } else {
                txtNombre.setText("");
                txtRIF.setText("");
                txtNIT.setText("");
            }
            return true;

        } else {
            return false;
        }
    }

    protected void vaciarCampos() {
        txtNombre.setText("");
        txtRIF.setText("");
        txtNIT.setText("");
    }

    protected void habilitarComponents(boolean estado) {
        txtNombre.setEnabled(estado);
        txtRIF.setEnabled(estado);
        txtNIT.setEnabled(estado);
    }

    //ATRIBUTOS FRONTEND
    private static final int PADDING = 20;
    private static final Label titulo = new Label("Ajustes Franquicia", TITULO, 24);

    private static final Label lblNombre = new Label("Nombre", PLANO, 16);
    private static final CampoTexto txtNombre = new CampoTexto("Nombre de la franquicia", NOMBRE);

    private static final Label lblRIF = new Label("RIF", PLANO, 16);
    private static final CampoTexto txtRIF = new CampoTexto("RIF de la franquicia", CUALQUIER);

    private static final Label lblNIT = new Label("NIT", PLANO, 16);
    private static final CampoTexto txtNIT = new CampoTexto("NIT de la franquicia", NUMERO);

    private static final Boton guardar = new Boton("Guardar", properties.Colores.VERDE);
    private static final Boton cancelar = new Boton("Cancelar", properties.Colores.NARANJA);
    //COMPONENTES

}
