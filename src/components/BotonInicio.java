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
 * Clase para la creación de los botones presentes en el inicio de la ventana
 * principal del programa
 */
public class BotonInicio extends JPanel implements properties.Constantes {

    /**
     * Constructor del botón para el inicio del programa
     *
     * @param type Tipo de botón
     */
    public BotonInicio(int type) {
        this.type = type;
        
        //PROPIEDADES BÁSICAS DEL BOTÓN
        btn.setFont(segoeSemibold(18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(JLabel.CENTER);
        btn.setVerticalAlignment(JLabel.CENTER);
        btn.setHorizontalTextPosition(JLabel.CENTER);
        btn.setVerticalTextPosition(JLabel.BOTTOM);
        btn.setForeground(properties.Colores.NEGRO);

        //PROPIEDADES ÚNICAS DEL BOTÓN
        setUniqueProperties();

        //LISTENERS PARA EL BOTÓN
        listener();

        //IMAGENES DEL BOTÓN
        try {
            //Gurdar las imágenes escaladas
            this.imgSmallButton = new ImageIcon(getButtomImage(64));
            this.imgSmallPress = new ImageIcon(getPressImage(64));
            this.imgSmallEntered = new ImageIcon(getEnterImage(64));

            this.imgLargeButton = new ImageIcon(getButtomImage(85));
            this.imgLargePress = new ImageIcon(getPressImage(85));
            this.imgLargeEntered = new ImageIcon(getEnterImage(85));

            //Colocar la imágen al botón
            btn.setIcon(imgLargeButton);

            btn.setIconTextGap(5);

        } catch (Exception e) {
            msjAdvertencia(
                    "No se encontró el ícono del botón " + imgName + " del inicio.\n"
                    + "El software seguirá ejecutandose normalmente sin el logo."
            );
        }

        //TAMAÑO DEL BOTÓN
        btn.setSize(btn.getPreferredSize().width+5, btn.getPreferredSize().height+5);

        //PROPIEDADES DEL PANEL
        this.setLayout(null);
        this.setOpaque(false);
        this.add(btn);

    }

    /**
     * Función para asignar las propiedades al botón, según su tipo, como el
     * nombre del a imagen, texto y tooltiptext
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
     *
     * @param largeSize TRUE si el botón debe ser de tamaño grande, FALSE en
     * caso de que sea pequeño
     */
    public void relocateComponents(boolean largeSize) {
        this.largeSize = largeSize;

        if(largeSize){
            setLargeImage();
        } else {
            setSmallImage();
        }
        
        int middleX = getWidth() / 2 - btn.getWidth() / 2;
        int middleY = getHeight() / 2 - btn.getHeight() / 2;
        
        btn.setLocation(middleX, middleY);
    }

    /**
     * Función para aplicar el mouse listener al botón creado
     */
    private void listener() {
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                //Comprobar que el botón NO está siendo presionado para colocar
                //la imagen del botón normal
                if (!largeSize && !btn.getIcon().equals(imgSmallPress)) {
                    btn.setIcon(imgSmallButton);

                } else if (largeSize && !btn.getIcon().equals(imgLargePress)) {
                    btn.setIcon(imgLargeButton);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //Comprobar que el botón NO esté siendo presionado para colocar
                //la imagen del botón cuando entre el mouse
                if (!btn.getIcon().equals(imgSmallPress) && !largeSize) {
                    btn.setIcon(imgSmallEntered);

                } else if (!btn.getIcon().equals(imgLargePress) && largeSize) {
                    btn.setIcon(imgLargeEntered);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Asignar el botón normal
                if (largeSize) {
                    btn.setIcon(imgLargeButton);
                } else {
                    btn.setIcon(imgSmallButton);
                }

                //Cambiar a la pestaña del botón, en caso de
                //que NO sea el botón del sitio web
                if (type != WEB) {
                    clickButton(type);
                } else {
                    abrirWeb();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (largeSize) {
                    btn.setIcon(imgLargePress);
                } else {
                    btn.setIcon(imgSmallPress);
                }
            }

        });
    }

    /**
     * Función para colocar, manualmente, el botón en su estado normal, sin ser
     * seleccionado o con el mouse dentro de él
     */
    public void setUnselectedStated() {
        //Asignar la imagen normal, según el tamaño del panel
        if (largeSize) {
            btn.setIcon(imgLargeButton);
        } else {
            btn.setIcon(imgSmallButton);
        }
    }

    /**
     * Función para obtener la imagen normal del botón
     *
     * @return Imagen del botón
     */
    private Image getButtomImage(int size) {
        //Obtener la imagen
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/inicio/" + imgName.toLowerCase() + ".png"));

        //Escalar la imagen
        return img.getImage().getScaledInstance(size, size, ESCALA_SUAVE);
    }

    /**
     * Función para obtener la imagen del botón presionado
     *
     * @return Imagen del botón
     */
    private Image getPressImage(int size) {
        //Obtener la imagen
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/inicio/" + imgName.toLowerCase() + "_pres.png"));

        //Escalar la imagen
        return img.getImage().getScaledInstance(size, size, ESCALA_SUAVE);
    }

    /**
     * Función para obtener la imagen del botón cuando el mouse esté dentro de
     * él
     *
     * @return Imagen del botón
     */
    private Image getEnterImage(int size) {
        //Obtener la imagen
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/inicio/" + imgName.toLowerCase() + "_ent.png"));

        //Escalar la imagen
        return img.getImage().getScaledInstance(size, size, ESCALA_SUAVE);
    }

    /**
     * Función para cargar las imágenes pequeñas
     */
    private void setSmallImage(){
        //Comprobar que el botón tenga la imagen grande
        if(btn.getIcon().equals(imgLargeButton)){
            btn.setIcon(imgSmallButton);
        }
        //Comprobar que el botón tenga la imagen grande 
        //cuando el mouse esté dentro
        if(btn.getIcon().equals(imgLargeEntered)){
            btn.setIcon(imgSmallEntered);
        }
        //Comprobar que el botón tenga la imagen grande
        //cuando el mouse lo presione
        if(btn.getIcon().equals(imgLargePress)){
            btn.setIcon(imgSmallPress);
        }
    }
    
    /**
     * Función para cargar las imágenes grandes
     */
    private void setLargeImage(){
        //Comprobar que el botón tenga la imagen grande
        if(btn.getIcon().equals(imgSmallButton)){
            btn.setIcon(imgLargeButton);
        }
        //Comprobar que el botón tenga la imagen grande 
        //cuando el mouse esté dentro
        if(btn.getIcon().equals(imgSmallEntered)){
            btn.setIcon(imgLargeEntered);
        }
        //Comprobar que el botón tenga la imagen grande
        //cuando el mouse lo presione
        if(btn.getIcon().equals(imgSmallPress)){
            btn.setIcon(imgLargePress);
        }
    }
    
    //ATRIBUTOS
    private final int type;
    private String imgName;
    private boolean largeSize = true;
    private ImageIcon imgSmallButton;
    private ImageIcon imgSmallEntered;
    private ImageIcon imgSmallPress;
    private ImageIcon imgLargeButton;
    private ImageIcon imgLargeEntered;
    private ImageIcon imgLargePress;
    private JLabel btn = new JLabel();
}
