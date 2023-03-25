package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import static java.awt.Cursor.HAND_CURSOR;
import static properties.Fuentes.segoe;
import static properties.Fuentes.segoeSemibold;

/**
 * Clase para la creación de los labels que se usarán en el software
 */
public class Label extends JLabel implements properties.Colores, properties.Constantes{

    //Atributos
    private final int type;
    private final int fontSize;

    /**
     * Constructor para la creación de los labels de forma sencilla.
     *
     * @param texto String que visualizará el label
     * @param type Tipo de label
     * @param fontSize Tamaño de la fuente de letra
     */
    public Label(String texto, int type, int fontSize) {
        
        //Asignar el texto
        this.setText(texto);
        
        //Guardar el tipo de label
        this.type = type;
        
        //Guardar el tamaño de la fuente
        this.fontSize = fontSize;
        
        //Asignar la fuente de letra, según su tipo
        this.setFont(fuenteLetra());

        //Asignar el color de letra, según su tipo
        this.setForeground(colorLetra());

        //Validar si el texto es de tipo link
        if (type == LINK) {
            
            //Asignarle el cursor de la mano para resaltar
            //que se puede presionar el label
            this.setCursor(new Cursor(HAND_CURSOR));

            //Asignarle un oyente de mouse para cuando sea
            //presionado y sea suelto el label
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    //Colocar el color celeste normal cuando
                    //sea suelto el label
                    setForeground(CELESTE);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //Colocarle el color celeste presionado de
                    //fuente de letra cuando sea presionado el label
                    setForeground(CELESTE_PRESS);
                }
            });
        }
        
        //Asignarle el tamaño ajustado al contenido
        this.setSize(this.getPreferredSize());
    }

    public Label(String texto, int type, int fontSize, boolean info){
        
        if(info){
            texto = "<html>"+texto+" <style='font-family:Monotype Corsiva;font-size:16px;'><b>i</b></style></html>";
        }

        //Asignar el texto
        this.setText(texto);
        
        //Guardar el tipo de label
        this.type = type;
        
        //Guardar el tamaño de la fuente
        this.fontSize = fontSize;
        
        //Asignar la fuente de letra, según su tipo
        this.setFont(fuenteLetra());

        //Asignar el color de letra, según su tipo
        this.setForeground(colorLetra());

        //Asignarle el tamaño ajustado al contenido
        int width = this.getPreferredSize().width + 5;
        this.setSize(width, this.getPreferredSize().height);
        
        
        //Validar si el texto es de tipo link
        if (type == LINK) {
            
            //Asignarle el cursor de la mano para resaltar
            //que se puede presionar el label
            this.setCursor(new Cursor(HAND_CURSOR));

            //Asignarle un oyente de mouse para cuando sea
            //presionado y sea suelto el label
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    //Colocar el color celeste normal cuando
                    //sea suelto el label
                    setForeground(CELESTE);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //Colocarle el color celeste presionado de
                    //fuente de letra cuando sea presionado el label
                    setForeground(CELESTE_PRESS);
                }
            });
        }
        
    }
    
    /**
     * Función para determinar, mediante un Switch-case, el color que tendrá la
     * fuente de letra dependiendo del tipo de label.
     *
     * @return
     * <ul>
     * <li><b>Blanco</b> en caso de que el label sea parte del menú superior
     * principal.</li>
     *
     * <li><b>Celeste</b> en caso de que el label contenga un hipervínculo.</li>
     *
     * <li><b>Negro</b> en cualquier caso que no sea ninguno de los
     * anteriores.</li>
     * </ul>
     */
    private Color colorLetra() {
        switch (type) {
            case NORMAL_BLANCO:
                return BLANCO;

            case LINK:
                return CELESTE;

            default:
                return NEGRO;
        }
    }

    /**
     * Función para determinar, mediante un Switch-case, la fuente de letra que
     * tendrá el label dependiendo de su tipo.
     *
     * @return
     * <ul>
     * <li><b>Segoe Semibold</b> en caso de que el label sea para los
     * títulos.</li>
     *
     * <li><b>Segoe Bold</b> en caso de que el label sea un mensaje que resalte
     * en negrita.</li>
     *
     * <li><b>Segoe Plain</b> en cualquier caso que no sea ninguno de los
     * anteriores.</li>
     * </ul>
     */
    private Font fuenteLetra() {
        switch (type) {
            case NORMAL_BLANCO:
            case TITULO:
                return segoeSemibold(fontSize);

            case GRUESA:
                return segoe(fontSize, BOLD);

            default:
                return segoe(fontSize, PLAIN);
        }
    }

}
