package pruebas;

public class AjustesPrecios extends javax.swing.JPanel implements properties.Colores, properties.Constantes{

    public AjustesPrecios() {
        initComponents();
        //initCustomComponents();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelCampos = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();

        setBackground(BLANCO);
        setLayout(new java.awt.BorderLayout());

        panelTitulo.setToolTipText("");
        panelTitulo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        jLabel1.setText("jLabel1");
        panelTitulo.add(jLabel1);

        add(panelTitulo, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        panelCampos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Precios", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        panelCampos.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("Precio Trasvaso");
        jLabel4.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 5, 20);
        panelCampos.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Precio Venta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 5, 20);
        panelCampos.add(jLabel5, gridBagConstraints);

        jTextField3.setText("jTextField3");
        jTextField3.setPreferredSize(new java.awt.Dimension(70, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 20, 20);
        panelCampos.add(jTextField3, gridBagConstraints);

        jTextField4.setText("jTextField4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        panelCampos.add(jTextField4, gridBagConstraints);

        jPanel1.add(panelCampos);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel panelCampos;
    private javax.swing.JPanel panelTitulo;
    // End of variables declaration//GEN-END:variables
}
