package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.PanelInfo;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import static javax.swing.BorderFactory.createLineBorder;

/**
 * Clase para la creación del panel de respaldo de bases de datos, en el administrador
 */
public class Respaldo extends JPanel implements properties.Constantes, properties.Colores{
    /**
     * Constructor del panel de los ajustes
     */
    public Respaldo(){
        this.setLayout(null);
        this.setOpaque(false);
        
        initComponents();
        listeners();
    }
    
    /**
     * Función para iniciar los componentes
     */
    private void initComponents(){
        
        //Propiedades de los paneles
        panelExporte.setBackground(BLANCO);
        panelExporte.setBorder(createLineBorder(GRIS));
        
        panelImporte.setBackground(BLANCO);
        panelImporte.setBorder(createLineBorder(GRIS));
        
        //Añadir los componentes
        panelExporte.add(lblTituloExp);
        panelExporte.add(lblUbicacion);
        panelExporte.add(txtUbicacion);
        panelExporte.add(lblNombre);
        panelExporte.add(txtNombre);
        panelExporte.add(btnUbicacionExp);
        panelExporte.add(btnExportar);
        
        panelImporte.add(lblTituloImp);
        panelImporte.add(lblArchivo);
        panelImporte.add(txtArchivo);
        panelImporte.add(btnUbicacionImp);
        panelImporte.add(btnImportar);
        
        this.add(informacion);
        this.add(panelExporte);
        this.add(panelImporte);
        
    }
    
    /**
     * Función para aplicar los listener a los componentes
     */
    private void listeners(){
        
    }
    
    /**
     * Función para reposicionar los componentes
     * @param width Ancho del parent content
     * @param height Alto del parent content
     */
    protected void relocateComponents(int width, int height){
        Respaldo.width = width;
        infoHeight = height - padding*2;
        
        //Tamaño para los paneles
        expH = (int)(height * 0.55 - padding*3/2);
        impH = (int)(height * 0.45 - padding*3/2);
        
        informacion.setLocation(padding, padding);
        
        if(width < 700){
            panelPequenio();
            
        } else if (width >= 700){
            this.setPreferredSize(new Dimension(width, height));
            panelGrande();
        }
        
        informacion.relocateComponents(CUALQUIER);
        relocateExporte();
        relocateImporte();
    }
    
    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño menor a 600 px
     */
    private void panelPequenio(){
        expH += padding;
        int panelW = width - padding*2;
        
        informacion.setSize(panelW, 400);
        
        int y = padding*2 + informacion.getHeight();
        panelExporte.setBounds(padding, y, panelW, expH);
        
        y = y + expH + padding;
        panelImporte.setBounds(padding, y, panelW, impH);
        
        int absoluteHeight = padding * 4 + informacion.getHeight() + expH + impH;
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }
    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 600px
     */
    private void panelGrande(){
        width += 15;
        impH += 8;
        expH += 7;
        infoHeight += 15;
        
        int halfWidth = width/2 - padding*2;
        int infoWidth = (halfWidth < infoMaxWidth) ? halfWidth : infoMaxWidth;
        informacion.setSize(infoWidth, infoHeight);
        
        int x = infoWidth + padding * 2;
        int w = width - x - padding;
        panelExporte.setBounds(x, padding, w, expH);
        
        int y = padding * 2 + expH;
        panelImporte.setBounds(x, y, w, impH);
    }
    
    /**
     * Función para reposicionar los componentes dentro del panel de exportación
     */
    private void relocateExporte(){
        lblTituloExp.setLocation(padding, padding);
        
        int expW = panelExporte.getWidth();
        int gap = 5;
        int h = 35;
        int w = expW - padding*2;
        
        txtNombre.setSize(w, h);
        btnUbicacionExp.setSize(h, h);
        w = expW - padding *2 - h - gap;
        txtUbicacion.setSize(w, h);
        
        int y = padding*3/2 + lblTituloExp.getHeight();
        lblUbicacion.setLocation(padding, y);
        
        y += lblUbicacion.getHeight() + gap;
        txtUbicacion.setLocation(padding, y);
        
        int x = expW - padding - h;
        btnUbicacionExp.setLocation(x, y);
        
        y += gap + h;
        lblNombre.setLocation(padding, y);
        
        y += lblNombre.getHeight() + gap;
        txtNombre.setLocation(padding, y);
        
        w = btnExportar.getPreferredSize().width + 30;
        x = expW - w - padding;
        y = panelExporte.getHeight() - padding - h;
        btnExportar.setBounds(x, y, w, h);
    }
    
    /**
     * Función para reposicionar los componentes dentro del panel de importación
     */
    private void relocateImporte(){
        
        lblTituloImp.setLocation(padding, padding);
        
        int gap = 5;
        int h = 35;
        int w = panelImporte.getWidth() - padding*2 - h - gap;
        btnUbicacionImp.setSize(h, h);
        txtArchivo.setSize(w, h);
        
        int y = padding*2 + lblTituloImp.getHeight();
        lblArchivo.setLocation(padding, y);
        
        y += lblArchivo.getHeight() + gap;
        txtArchivo.setLocation(padding, y);
        
        int x = panelImporte.getWidth() - padding - h;
        btnUbicacionImp.setLocation(x, y);
        
        w = btnImportar.getPreferredSize().width + 30;
        x = panelImporte.getWidth() - w - padding; 
        y = panelImporte.getHeight() - padding - h;
        btnImportar.setBounds(x, y, w, h);
    }
    
    protected void vaciarCampos(){
        txtUbicacion.setText("");
        txtNombre.setText("");
        txtArchivo.setText("");
    }
    
    //ATRIBUTOS
    private static int width, infoHeight, expH, impH;
    private static final int padding = 20;
    private static final int infoMaxWidth = 300;
    
    //COMPONENTES
    private static final PanelInfo informacion = new PanelInfo(ADMIN_RESPALDO);
    
    private static final JPanel panelExporte = new JPanel(null);
    private static final Label lblTituloExp = new Label("Exportar BDD", TITULO, 22);
    private static final Label lblUbicacion = new Label("Ubicación", PLANO, 16, true);
    private static final CampoTexto txtUbicacion = new CampoTexto("Seleccionar la ubicación", CUALQUIER);
    private static final Label lblNombre = new Label("Nombre", PLANO, 16, true);
    private static final CampoTexto txtNombre = new CampoTexto("Nombre del archivo", CUALQUIER);
    private static final BotonDirectory btnUbicacionExp = new BotonDirectory();
    private static final Boton btnExportar = new Boton("Guardar respaldo", VERDE);
    
    private static final JPanel panelImporte = new JPanel(null);
    private static final Label lblTituloImp = new Label("Importar BDD", TITULO, 22);
    private static final Label lblArchivo = new Label("Archivo Seleccionado", PLANO, 16, true);
    private static final CampoTexto txtArchivo = new CampoTexto("Nombre del archivo", CLAVE);
    private static final BotonDirectory btnUbicacionImp = new BotonDirectory();
    private static final Boton btnImportar = new Boton("Importar respaldo", CELESTE);
}

/**
 * Clase para los botones de selección de archivo o ruta
 */
class BotonDirectory extends JPanel implements properties.Constantes, properties.Colores{
    
    /**
     * Constructor de los botones de selección de archivo o ruta
     */
    public BotonDirectory(){
        this.setLayout(new BorderLayout());
        this.setBackground(BLANCO);
        this.setBorder(createLineBorder(GRIS_OSCURO));
        
        lblPuntos.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        lblPuntos.setVerticalAlignment(javax.swing.JLabel.CENTER);
        lblPuntos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(lblPuntos, BorderLayout.CENTER);
        
        //Mouse Listener para el color del panel cuando se presione y 
        //se suelte el mouse
        lblPuntos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(BLANCO);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(GRIS);
            }
        });
    }
    
    //COMPONENTES
    private final Label lblPuntos = new Label(". . .", PLANO, 14);
}