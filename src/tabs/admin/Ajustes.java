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

        ajustes.addTab("Generales", generales);
        ajustes.addTab("Sucursales", sucursales);
        ajustes.addTab("Franquicia", franquicia);
    }

    /**
     * Función para aplicar los listener a los componentes
     */
    private void listeners() {
        ajustes.addChangeListener((e) -> {
            relocateComponents(Ajustes.width, Ajustes.height);
            Admin.setAjustesPreferredSize();
        });
    }

    protected void relocateComponents(int width, int height) {
        //Guardar los atributos
        Ajustes.width = width;
        Ajustes.height = height;
        
        //Posicion de la información fija
        informacion.setLocation(padding, padding);
        
        //Validar el tamaño del panel contenedor
        if (width < 750) {
            pequenio();
        } else {
            this.setPreferredSize(new Dimension(width, height));
            grande();
        }

        //Reposicionar los componentes
        informacion.relocateComponents(CUALQUIER);
        
        //precios.relocateComponents();
        generales.relocateComponents(ajustes.getWidth(), ajustes.getHeight());
        sucursales.relocateComponents(ajustes.getWidth(), ajustes.getHeight());
    }

    private void pequenio() {
        //Ancho para todos los componentes
        int w = width - padding * 2;

        //Determinar la altura del panel informativo según el ancho del panel
        int infoH;
        if (w < 358) {
            infoH = 320;
        } else if (w < 508) {
            infoH = 300;
        } else if (w < 520) {
            infoH = 280;
        } else if (w < 567) {
            infoH = 270;
        } else {
            infoH = 250;
        }
        informacion.setSize(w, infoH);

        int y = padding * 2 + infoH;
        
        int ajustesH;
        switch (ajustes.getSelectedIndex()) {
            case 0:
                ajustesH = 730;
                break;
                
            case 1:
                ajustesH = 550;
                break;
                
            default:
                ajustesH = 400;
                break;
        }
        
        ajustes.setBounds(padding, y, w, ajustesH);

        int absoluteHeight = infoH + ajustesH + padding * 3;
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    private void grande() {
        //Altura para todos los componentes
        int h = height + 15 - padding * 2;

        //Determinar el límite de anchura para el panel informativo
        int w = 270;

        //Asignar el tamaño al panel informativo
        informacion.setSize(w, h);

        int x = padding * 2 + w;
        w = width - w - padding * 3 + 15;
        ajustes.setBounds(x, padding, w, h);
    }

    protected static boolean actualizarDatos(){
        return generales.actualizarDatos()
                && sucursales.actualizarDatos()
                && franquicia.actualizarDatos();
    }
    
    /**
     * Función para vaciar todos los campos
     */
    protected static void vaciarCampos() {

    }

    protected void habilitarComponents(boolean estado) {
        sucursales.habilitarComponents(estado);
        franquicia.habilitarComponents(estado);
    }

    //ATRIBUTOS
    private static final int padding = 20;
    private static int width, height;

    //COMPONENTES
    private static final PanelInfo informacion = new PanelInfo(ADMIN_AJUSTES);
    private static final JTabbedPane ajustes = new JTabbedPane();
    private static final AjustesSucursales sucursales = new AjustesSucursales();
    private static final AjustesFranquicia franquicia = new AjustesFranquicia();
    private static final AjustesGenerales generales = new AjustesGenerales();
}
