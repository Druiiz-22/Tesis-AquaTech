package components;

import static java.awt.Font.BOLD;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static properties.Fuentes.segoe;
import static properties.Fuentes.segoeBlack;
import static properties.Mensaje.msjAdvertencia;

/**
 * Clase para la creación del label del login que contiene el logo del software
 */
public class Logo extends JLabel implements properties.Constantes{

    /**
     * Constructor para crear un label con el logo y el nombre del software
     * @param type Tipo de logo (HORIZONTAL, VERTICAL o LOGO_MENU).
     */
    public Logo(int type) {
        
        switch (type) {
            case HORIZONTAL:
                horizontal();
                break;
                
            case VERTICAL:
                vertical();
                break;
                
            case LOGO_MENU:
                logoMenu();
                break;
        }

    }

    /**
     * Función para cargar el logo principal del login
     */
    private void vertical() {
        //Logo con el nombre de la empresa
        String nombre = "<html><nobr><font color='#1BA2E6'>Aqua</font><font color='#2AD9FF'>Tech</font></nobr></html>";
        this.setText(nombre);
        this.setFont(segoe(40, BOLD));
        this.setIconTextGap(0);  //Separación del texto con el icon
        this.setHorizontalTextPosition(CENTER); //Alineación horizontal del texto   
        this.setVerticalTextPosition(BOTTOM);   //Alineación vertical del texto
        this.setHorizontalAlignment(CENTER); //Alineación Horizontal
        this.setVerticalAlignment(CENTER);   //Alineación Vertical

        //Cargar la imagen
        try {
            ImageIcon imgLogo = new ImageIcon(getClass().getResource("/icons/logo.png"));
            this.setIcon(
                    new ImageIcon(imgLogo.getImage().getScaledInstance(100, 100, ESCALA_SUAVE))
            );
            this.setSize(getPreferredSize());

        } catch (Exception e) {
            this.setSize(this.getPreferredSize().width, 215);
            msjAdvertencia(
                    "No se encontró el logo del software.\n"
                    + "El software seguirá ejecutandose normalmente sin el logo."
            );
        }
    }

    /**
     * Función para cargar el logo pequeño del login y para reportes
     */
    private void horizontal() {
        //Logo con el nombre de la empresa
        String nombre = "<html><nobr><font color='#1BA2E6'>Aqua</font><font color='#2AD9FF'>Tech</font></nobr></html>";
        this.setText(nombre);
        this.setFont(segoe(32, BOLD));
        this.setIconTextGap(0);  //Separación del texto con el icon
        this.setHorizontalAlignment(CENTER); //Alineación Horizontal
        this.setVerticalAlignment(CENTER);   //Alineación Vertical
        this.setHorizontalTextPosition(TRAILING); //Alineación horizontal del texto   
        this.setVerticalTextPosition(CENTER);   //Alineación vertical del texto

        //Cargar la imagen
        try {
            ImageIcon imgLogo = new ImageIcon(getClass().getResource("/icons/logo.png"));
            this.setIcon(
                    new ImageIcon(imgLogo.getImage().getScaledInstance(64, 64, ESCALA_SUAVE))
            );
            this.setSize(getPreferredSize());

        } catch (Exception e) {
            this.setSize(this.getPreferredSize().width, 64);
            msjAdvertencia(
                    "No se encontró el logo del software.\n"
                    + "El software seguirá ejecutandose normalmente sin el logo."
            );
        }
    }

    /**
     * Función para cargar el logo blanco y negro para el menú superior del
     * software.
     */
    private void logoMenu() {
        this.setText("AquaTech");
        this.setFont(segoeBlack(32));
        this.setForeground(properties.Colores.BLANCO);
        this.setIconTextGap(0);  //Separación del texto con el icon
        this.setHorizontalAlignment(CENTER); //Alineación Horizontal
        this.setVerticalAlignment(CENTER);   //Alineación Vertical
        this.setHorizontalTextPosition(RIGHT); //Alineación horizontal del texto   
        this.setVerticalTextPosition(CENTER);   //Alineación vertical del texto
        
        //Cargar la imagen
        try {
            ImageIcon imgLogo = new ImageIcon(getClass().getResource("/icons/logo_blanco.png"));
            this.setIcon(
                    new ImageIcon(imgLogo.getImage().getScaledInstance(50, 50, ESCALA_SUAVE))
            );
            this.setSize(this.getPreferredSize());

        } catch (Exception e) {
            this.setSize(this.getPreferredSize().width, 64);
            msjAdvertencia(
                    "No se encontró el logo del software.\n"
                    + "El software seguirá ejecutandose normalmente sin el logo."
            );
        }
    }
}
