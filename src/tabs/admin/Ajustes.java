package tabs.admin;

import javax.swing.JPanel;

/**
 * Clase para la creación del panel administrador de los ajustes generales
 */
public class Ajustes extends JPanel implements properties.Constantes, properties.Colores{
    
    /**
     * Constructor del panel de los ajustes
     */
    public Ajustes(){
        this.setLayout(null);
        this.setOpaque(false);
        
        initComponents();
        listeners();
    }
    
    /**
     * Función para iniciar los componentes
     */
    private void initComponents(){
        
    }
    
    /**
     * Función para aplicar los listener a los componentes
     */
    private void listeners(){
        
    }
    
    /**
     * Función para vaciar todos los campos
     */ 
    protected void vaciarCampos(){
    }
    
    //COMPONENTES
}
