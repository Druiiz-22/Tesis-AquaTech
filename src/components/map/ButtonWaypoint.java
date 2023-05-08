package components.map;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonWaypoint extends JButton {

    public ButtonWaypoint() {
        //Propiedades básicas
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(new Dimension(33, 33));
        
        //Cargar la imágen
        ImageIcon punto = new ImageIcon(getClass().getResource("/icons/ubicacion.png"));
        setIcon(new ImageIcon(punto.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
    }
}
