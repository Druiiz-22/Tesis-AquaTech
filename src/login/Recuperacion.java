package login;

import components.Logo;
import java.awt.Dimension;
import javax.swing.JPanel;
import login.recuperacion.Correo;
import login.recuperacion.ClaveNueva;
import login.recuperacion.Codigo;
import static javax.swing.SwingConstants.HORIZONTAL;
import static login.Frame.getParentSize;

/**
 * Clase contenedora del apartado de recuperación de contraseña
 */
public class Recuperacion extends JPanel implements properties.Colores, properties.Constantes{
    
    // ========== BACKEND ==========
    
    /**
     * Función para cambiar la clave en la base de datos
     */
    public static void cambiarClave(){
        
    }
    
    /**
     * Función para guardar el correo en la clase de recuperación
     * @param correo Correo ingresado por el usuario
     */
    public static void setCorreo(String correo){
        Recuperacion.correo = correo;
    }

    /**
     * Función para guardar la clave nueva en la clase de recuperación
     * @param claveNueva Clave nueva por el usuario
     */
    public static void setClaveNueva(String claveNueva) {
        Recuperacion.claveNueva = claveNueva;
    }
    
    //ATRIBUTOS
    private static String correo;
    private static String claveNueva;
    
    // ========== FRONTEND ==========
    
    /**
     * Constructor del constructor de la recuperación de contraseña
     */
    public Recuperacion() {
        this.setLayout(null);
        this.setBackground(BLANCO);
        
    }
    
    /**
     * Función para iniciar los componentes
     */
    protected void initComponents(){
        this.setSize(getParentSize());
        
        //Punto medio del panel
        int middleX = this.getWidth() / 2;
        
        //Asignarle el texto al logo
        logo.setText("Recuperar Clave");
        logo.setForeground(CELESTE);
        logo.setSize(logo.getPreferredSize());
        
        //Posicionar el logo a mitad del panel
        int logoX = middleX - logo.getWidth() / 2;
        int logoMarginTop = 30;
        int logoMarginBottom = 37;
        logo.setLocation(logoX, logoMarginTop);
        
        //Posicionar el contenedor debajo del logo.
        int contenedorY = logoMarginTop + logo.getHeight() + logoMarginBottom;
        //Asignarle de tamaño, al contenedor, el espacio restante debajo del logo
        int contenedorHeight = this.getHeight() - contenedorY;
        panelContenedor.setBounds(0, contenedorY, this.getWidth(), contenedorHeight);
        
        //Hacer que el panel sea transparente
        panelContenedor.setOpaque(false);
        
        //Iniciar los paneles
        panelCorreo.initComponents();
        panelCodigo.initComponents();
        panelClave.initComponents();
        
        
        //Mostrar el panel del correo
        panelContenedor.add(panelCorreo);
        
        //Añadir el logo y el contenedor al panel de registro
        this.add(logo);
        this.add(panelContenedor);
    }
    
    /**
     * Función para obtener el tamaño del panel contenedor
     * @return Dimensiones del panel contenedor
     */
    public static Dimension getContentSize(){
        return panelContenedor.getSize();
    }
    
    /**
     * Función para reemplazar el panel contenedor
     * 
     * @param type Panel que será mostrado
     */
    public static void replaceContainer(int type){
        //Eliminar el contenido actual del panel
        panelContenedor.removeAll();
        
        switch (type) {
            case CORREO:
                panelContenedor.add(panelCorreo);
                break;
                
            case CODIGO:
                panelContenedor.add(panelCodigo);
                break;
                
            case CLAVE:
                panelContenedor.add(panelClave);
                break;
        }
        
        //Actualizar el panel
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }
    
    /**
     * Función para vaciar todos los campos de textos
     */
    public static void vaciarCampos(){
        panelCorreo.vaciarCampos();
        panelCodigo.vaciarCampos();
        panelClave.vaciarCampos();
    }
    
    //COMPONENTES
    private static final Logo logo = new Logo(HORIZONTAL);
    private static final JPanel panelContenedor = new JPanel(null);
    private static final Correo panelCorreo = new Correo();
    private static final Codigo panelCodigo = new Codigo();
    private static final ClaveNueva panelClave = new ClaveNueva();
}
