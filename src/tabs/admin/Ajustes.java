package tabs.admin;

import components.PanelInfo;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Clase para la creación del panel administrador de los ajustes generales
 */
public class Ajustes extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del panel de los ajustes
     */
    public Ajustes() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        this.add(informacion);
        this.add(ajustes);
        
        ajustes.addTab("General", general);
        ajustes.addTab("Sucursales", sucursales);
        ajustes.addTab("Dispositivos", dispositivos);
    }

    /**
     * Función para aplicar los listener a los componentes
     */
    private void listeners() {

    }

    protected void relocateComponents(int width, int height) {
        //Guardar los atributos
        Ajustes.width = width;
        Ajustes.height = height;

        //Posicion de la información fija
        informacion.setLocation(padding, padding);

        //Validar el tamaño del panel contenedor
        if (width < 700) {
            pequenio();
        } else {
            this.setPreferredSize(new Dimension(width, height));
            grande();
        }
        
        //Reposicionar los componentes
        informacion.relocateComponents(CUALQUIER);
        
    }

    private void pequenio() {
        //Ancho para todos los componentes
        int w = width - padding * 2;
        
        //Determinar la altura del panel informativo según el ancho del panel
        int infoH;
        if(w < 329){
            infoH = 390;
        } else if (w < 358){
            infoH = 360;
        } else if (w < 508){
            infoH = 340;
        } else if (w < 520){
            infoH = 320;
        } else if (w < 567){
            infoH = 290;
        } else{ 
            infoH = 270;
        }
        informacion.setSize(w, infoH);
        
        int y = padding * 2 + infoH;
        int ajustesH = height - padding*2;
        ajustes.setBounds(padding, y, w, ajustesH);
        
        int absoluteHeight = infoH + ajustesH + padding*3;
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    private void grande() {
        //Altura para todos los componentes
        int h = height + 15 - padding * 2;

        //Determinar el límite de anchura para el panel informativo
        int w = 300;
        
        //Asignar el tamaño al panel informativo
        informacion.setSize(w, h);
        
        int x = padding*2 + w;
        w = width - w - padding * 3;
        ajustes.setBounds(x, padding, w, h);
    }

    /**
     * Función para vaciar todos los campos
     */
    protected static void vaciarCampos() {
    }

    //ATRIBUTOS
    private static final int padding = 20;
    private static int width, height;

    //COMPONENTES
    private static final PanelInfo informacion = new PanelInfo(ADMIN_AJUSTES);
    private static final JTabbedPane ajustes = new JTabbedPane();
    private static final AjustesDispositivos general = new AjustesDispositivos();
    private static final AjustesSucursales sucursales = new AjustesSucursales();
    private static final AjustesGenerales dispositivos = new AjustesGenerales();
}
