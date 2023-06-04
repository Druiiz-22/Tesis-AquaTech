package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static java.awt.Font.BOLD;
import static javax.swing.BorderFactory.createLineBorder;
import static properties.Mensaje.msjAdvertencia;
import static properties.Fuentes.segoe;

/**
 * Clase para la creación de los botones que se usarán durante el software.
 */
public class Boton extends JLabel implements properties.Colores, properties.Constantes{

    //Atributos
    private String text;
    private Color bgColor;
    private Color pressColor;
    private int type;

    /**
     * Constructor para la creación de los botones básicos que se usarán
     * durante el software.
     *
     * @param text Texto que mostrará el botón
     * @param bgColor Color de fondo del botón
     */
    public Boton(String text, Color bgColor) {
        this.text = text;
        this.bgColor = bgColor;

        propiedadesBasicas();

        backgroundColor();
    }

    /**
     * Propiedades básicas de todos los botones del software
     */
    private void propiedadesBasicas() {

        this.setText(text);
        this.setOpaque(true);
        this.setForeground(BLANCO);

        this.setFont(segoe(20, BOLD));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Alineación del contenido
        this.setHorizontalAlignment(CENTER);
        this.setVerticalAlignment(CENTER);
        //Alineación del texto
        this.setHorizontalTextPosition(RIGHT);
        this.setVerticalTextPosition(CENTER);

    }

    /**
     * Función para validar que el color del botón sea verde, rojo o celeste y
     * colocarle sus respectivos colores cuando estos sean presionados y
     * sueltos.
     */
    private void backgroundColor() {

        //Validar si el color elegido es el color VERDE
        if (bgColor.equals(VERDE)) {
            pressColor = VERDE_PRESS;

            //Validar si el color elegido es el color CELESTE
        } else if (bgColor.equals(CELESTE)) {
            pressColor = CELESTE_PRESS;

            //Validar si el color elegido es el color ROJO
        } else if (bgColor.equals(NARANJA)) {
            pressColor = NARANJA_PRESS;

            //En caso de NO ser ninguno de los anteriores,
            //asginar gris claro al color del fondo y gris cuando
            //sea presionado.
        } else {
            bgColor = Color.LIGHT_GRAY;
            pressColor = Color.GRAY;
        }

        //Asignar el color de fondo normal
        setBackground(bgColor);

        //Listener del mouse sobre el componente para
        //cuando el mouse esté presionado y cuando el
        //mouse esté suelto.
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                //Cuando el mouse sea suelto, asignar
                //el color de fondo normal
                setBackground(bgColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //Cuando el mouse sea presionado, asignar
                //el color de fondo oscuro
                setBackground(pressColor);
            }
        });
    }

    /**
     * Constructor para la creación de los botones del menú lateral
     * @param type Tipo del botón del menú lateral
     */
    public Boton(int type) {

        this.type = type;
        
        //Asignar las propiedades básicas de los botones
        propiedadesBasicas();

        //Color del fondo
        this.setBackground(NEGRO);

        //Asignar el listener
        mouseListener();

        //Asignar el texto
        this.setText(getTypeText());
        
        //Colocar el icon
        try {
            //Colocar la imágen
            this.setIcon(getIconImage());

            this.setIconTextGap((type == ASIGNAR_PROV)? 15 : 10);

        } catch (Exception e) {
            msjAdvertencia(
                    "No se encontró el ícono del botón " + getText() + " del menú lateral.\n"
                    + "El software seguirá ejecutandose normalmente sin el logo."
            );
        }
    }

    /**
     * Función para asignar el MouseListener a los botones del menú lateral
     * para aplicarle un hoover cuando el mouse entre y salga de ellos.
     */
    private void mouseListener() {
       
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                //Si el color del fondo es gris, significa
                //que el botón no ha sido presionado, pero está el mouse
                //dentro del botón; por lo tanto, se le coloca el color
                //negro cuando el mouse salga del botón.
                if (getBackground().equals(GRIS_HOOVER)) {
                    setBackground(NEGRO);
                }

                //En caso de que el color del fondo sea azúl, significa
                //que el botón sí fue presionado; por lo que, mantendrá ese
                //color aunque el mouse entre o salga del botón.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //Si el color del fondo es negro, significa
                //que el botón no ha sido presionado; por lo tanto,
                //se le coloca el color gris cuando el mouse 
                //entre del botón, a modo de Hoover.

                if (getBackground().equals(NEGRO)) {
                    setBackground(GRIS_HOOVER);
                }

                //En caso de que el color del fondo sea azúl, significa
                //que el botón sí fue presionado; por lo que, mantendrá ese
                //color aunque el mouse entre o salga del botón.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Comprobar si el botón al que se le soltó el mouse,
                //es de tipo de selección de cliente o proveedor
                if(type == SELECT_CLIENTE || type == ASIGNAR_PROV){
                    setBackground(BLANCO);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //Comprobar si el botón al que se le presionó el mouse,
                //es de tipo de selección de cliente o proveedor
                if(type == SELECT_CLIENTE || type == ASIGNAR_PROV){
                    setBackground(GRIS);
                }
            }
        
            
        });
    }

    /**
     * Función para obtener el nombre del botón del menú lateral, 
     * según su tipo.
     * @param type Tipo de botón escogido
     * @return Nombre del botón
     */
    private String getTypeText() {
        switch (type) {

            case INICIO:
                return "Inicio";

            case CLIENTES:
                return "Clientes";

            case VENTAS:
                return "Vender";

            case COMPRAS:
                return "Comprar";

            case HISTORIAL:
                return "Historial";

            case PROVEEDOR:
                return "Proveedores";

            case MANUAL:
                return "Manual";

            case ADMIN:
                return "Admin";

            case SELECT_CLIENTE:
                this.setToolTipText("Ir al registro de clientes para seleccionarlo");
                return "Seleccionar Cliente";
                
            case ASIGNAR_PROV:
                this.setToolTipText("Ir al registro de proveedores para seleccionarlo");
                return "Asignar Proveedor";
            default:
                return "";
        }
    }
    
    /**
     * Función para asignar el ícono del botón del menú lateral,
     * según su tipo.
     * @param type
     * @return 
     */
    private ImageIcon getIconImage() {
        String imgName;
        int imgSize = 32;
        
        switch (type) {

            case INICIO:
                imgName = "home";
                break;

            case CLIENTES:
                imgName = "customers";
                break;

            case VENTAS:
                imgName = "sell";
                break;

            case COMPRAS:
                imgName = "buy";
                break;

            case HISTORIAL:
                imgName = "clock";
                break;

            case PROVEEDOR:
                imgName = "provider";
                break;

            case MANUAL:
                imgName = "guide";
                break;

            case ADMIN:
                imgName = "admin";
                break;
                
            case SELECT_CLIENTE:
            case ASIGNAR_PROV:
                //Propiedades únicas de los botones de 
                //seleción de cliente y proveedor
                this.setBorder(createLineBorder(GRIS));
                this.setBackground(BLANCO);
                this.setForeground(NEGRO);
                //Nombre del icon y su altura
                imgName = "add_client";
                imgSize = 58;
                break;
                
            default:
                imgName = "";
                break;
        }
        
        //Obtener la imágen
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/" + imgName + ".png"));

        //Retornar la imágen escalada
        return new ImageIcon(img.getImage().getScaledInstance(imgSize, imgSize, ESCALA_SUAVE));
    }

}
