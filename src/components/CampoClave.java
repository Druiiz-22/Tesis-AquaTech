package components;

import static java.awt.Font.PLAIN;
import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import properties.PlaceHolder;
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createLineBorder;
import static properties.Fuentes.segoe;

/**
 * Clase para la creación de los campos de textos de tipo contraseña
 * que se usará durante el software
 */
public class CampoClave extends JPasswordField implements properties.Colores{

    /**
     * Constructor para la creación del campo de clave personalizado
     * @param placeHolder Texto falso que mostrará el campo
     */
    public CampoClave(String placeHolder) {
        //Color de fondo
        this.setBackground(BLANCO);
        //Color de letra
        this.setForeground(NEGRO);
        //Fuente de letra
        this.setFont(segoe(16, PLAIN));

        this.setBorder(createCompoundBorder(
                createLineBorder(GRIS_OSCURO),
                createEmptyBorder(1, 8, 1, 8)
        ));

        //Asignar el borde según el enfoque
        enfoque();

        //Placeholder al campo de texto
        new PlaceHolder(placeHolder, this);
    }

    /**
     * Función para asignar el listener de enfoque al campo de texto, teniendo
     * este la función para poner el <b>borde de color gris cuando pierda el
     * enfoque y de color celeste cuando obtenga el enfoque</b>.
     */
    private void enfoque() {

        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                //Borde gris cuando pierda el enfoque
                setBorder(createCompoundBorder(
                        createLineBorder(GRIS_OSCURO),
                        createEmptyBorder(1, 8, 1, 8)
                ));
            }

            @Override
            public void focusGained(FocusEvent e) {
                //Borde celeste cuando obtenga el enfoque
                setBorder(createCompoundBorder(
                        createLineBorder(CELESTE),
                        createEmptyBorder(1, 8, 1, 8)
                ));
            }

        });
    }
}
