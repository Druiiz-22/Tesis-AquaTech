package components;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static properties.Fuentes.segoeSemibold;
import static properties.Mensaje.msjAdvertencia;
import static main.MenuLateral.clickButton;
import static main.MenuSuperior.abrirWeb;

/**
 * Clase para la creación de los botones presentes en el inicio de
 * la ventana principal del programa
 */
public class BotonInicio extends JPanel implements properties.Constantes {
    
    /**
     * Constructor del botón para el inicio del programa
     * @param type Tipo de botón
     */
    public BotonInicio(int type) {
        this.type = type;

        //PROPIEDADES BÁSICAS DEL BOTÓN
        btn.setFont(segoeSemibold(20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(JLabel.CENTER);
        btn.setVerticalAlignment(JLabel.CENTER);
        btn.setHorizontalTextPosition(JLabel.CENTER);
        btn.setVerticalTextPosition(JLabel.BOTTOM);
        btn.setForeground(properties.Colores.BLANCO);

        //PROPIEDADES ÚNICAS DEL BOTÓN
        setUniqueProperties();

        //LISTENERS PARA EL BOTÓN
        listener();
        
        //IMAGENES DEL BOTÓN
        try {
            //Gurdar las imágenes escaladas
            this.imgButtom = new ImageIcon(getButtomImage());
            //this.imgPressBtn = new ImageIcon(getPressImage());
            //this.imgEnterbtn = new ImageIcon(getEnterImage());
            
            //Colocar la imágen al botón
            btn.setIcon(imgButtom);

            btn.setIconTextGap(5);
            
        } catch (Exception e) {
            msjAdvertencia(
                    "No se encontró el ícono del botón " + imgName + " del inicio.\n"
                    + "El software seguirá ejecutandose normalmente sin el logo."
            );
        }
        
        //TAMAÑO DEL BOTÓN
        btn.setSize(btn.getPreferredSize());
        
        //PROPIEDADES DEL PANEL
        this.setLayout(null);
        this.setOpaque(false);
        this.add(btn);
        
    }
    
    /**
     * Función para asignar las propiedades al botón, según su tipo,
     * como el nombre del a imagen, texto y tooltiptext
     */
    private void setUniqueProperties() {
        switch (type) {
            case VENTAS_TRASVASO:
                this.imgName = "Trasvasar";
                btn.setText(imgName);
                btn.setToolTipText("Realizar un trasvaso a un cliente");
                break;
            case COMPRAS_RECARGA:
                this.imgName = "Recargas";
                btn.setText(imgName);
                btn.setToolTipText("Registrar una recarga con los proveedores");
                break;
            case VENTAS_PEDIDOS:
                this.imgName = "Pedidos";
                btn.setText(imgName);
                btn.setToolTipText("Ver las entregas a domicilio pendientes");
                break;
            case DEUDAS:
                this.imgName = "Deudas";
                btn.setText(imgName);
                btn.setToolTipText("Ver las deudas actuales con los clientes");
                break;
            case CLIENTES:
                this.imgName = "Clientes";
                btn.setText(imgName);
                btn.setToolTipText("Ver el registro de los clientes");
                break;
            case VENTAS_BOTELLON:
                this.imgName = "Vender";
                btn.setText(imgName);
                btn.setToolTipText("Realizar la venta de un botellón a un cliente");
                break;
            case COMPRAS_BOTELLON:
                this.imgName = "Compras";
                btn.setText(imgName);
                btn.setToolTipText("Registrar una compra de botellones con los proveedores");
                break;
            case HISTORIAL:
                this.imgName = "Historial";
                btn.setText(imgName);
                btn.setToolTipText("Ver el historial de los distintos movimientos");
                break;
            case PROVEEDOR:
                this.imgName = "Proveedores";
                btn.setText(imgName);
                btn.setToolTipText("Ver el registro de los proveedores");
                break;
            case WEB:
                this.imgName = "Sitio Web";
                btn.setText(imgName);
                btn.setToolTipText("Abrir el sitio web de la empresa");
                break;
        }
    }
    
    /**
     * Función para posicionar el botón en el centro del panel
     */
    public void relocateComponents(){
        int middleX = getWidth()/2 - btn.getWidth()/2;
        int middleY = getHeight()/2 - btn.getHeight()/2;

        btn.setLocation(middleX, middleY);
    }
    
    /**
     * Función para aplicar el mouse listener al botón creado
     */
    private void listener(){
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                //Comprobar que el botón NO está siendo presionado
                //para colocar su imagen normal, cuando salga el mouse
                //del botón
                if(!btn.getIcon().equals(imgPressBtn)){
                    btn.setIcon(imgButtom);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //setIcon(imgEnterbtn);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Obtener el tamaño del botón
                int maxX = getWidth();
                int maxY = getHeight();
                
                //Obtener la posición X y Y del cursor con respecto al botón
                int pointX = e.getPoint().x;
                int pointY = e.getPoint().y;
                
                //Validar que el cursor esté DENTRO del botón
                if(pointX >= 0 && pointX <= maxX && pointY >= 0 && pointY <= maxY){
                    //Asignar la imagen de que está dentro del botón
                    //setIcon(imgEnterbtn);
                    
                } else{
                    //Asignar la imagen del botón normal
                    btn.setIcon(imgButtom);
                }
                
                //Cambiar a la pestaña del botón, en caso de
                //que NO sea el botón del sitio web
                if(type != WEB){
                    clickButton(type);
                } else{
                    abrirWeb();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //setIcon(imgPressBtn);
            }
            
        });
    }
    
    /**
     * Función para obtener la imagen normal del botón
     * @return Imagen del botón
     */
    private Image getButtomImage() {
        //Obtener la imagen
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/inicio/"+imgName.toLowerCase()+".png"));
        
        //Escalar la imagen
        return img.getImage().getScaledInstance(64, 64, ESCALA_SUAVE);
    } 
    
    /**
     * Función para obtener la imagen del botón presionado
     * @return Imagen del botón 
     */
    private Image getPressImage() {
        //Obtener la imagen
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/inicio/"+imgName.toLowerCase()+"_press.png"));
        
        //Escalar la imagen
        return img.getImage().getScaledInstance(56, 56, ESCALA_SUAVE);
    } 
    
    /**
     * Función para obtener la imagen del botón cuando el
     * mouse esté dentro de él
     * @return Imagen del botón
     */
    private Image getEnterImage() {
        //Obtener la imagen
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/inicio/"+imgName.toLowerCase()+"_enter.png"));
        
        //Escalar la imagen
        return img.getImage().getScaledInstance(64, 64, ESCALA_SUAVE);
    }

    //ATRIBUTOS
    private final int type;
    private String imgName;
    private ImageIcon imgButtom;
    private ImageIcon imgEnterbtn;
    private ImageIcon imgPressBtn;
    private JLabel btn = new JLabel();
}
