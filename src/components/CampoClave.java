package components;

import java.awt.Cursor;
import java.awt.Dimension;
import static java.awt.Font.PLAIN;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import properties.PlaceHolder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import static javax.swing.BorderFactory.createLineBorder;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjAdvertencia;

/**
 * Clase para la creación de los campos de textos de tipo contraseña que se
 * usará durante el software
 */
public class CampoClave extends JPanel implements properties.Colores, properties.Constantes {

    //MÉTODOS DE CONSTRUCCIÓN DEL BOTÓN
    /**
     * Constructor para la creación del campo de clave personalizado
     *
     * @param placeHolder Texto falso que mostrará el campo
     */
    public CampoClave(String placeHolder) {
        //Layout null
        this.setLayout(null);
        //Color de fondo
        this.setBackground(BLANCO);
        //Bordes grises
        this.setBorder(createLineBorder(GRIS_OSCURO));

        initComponents();

        //Asignar el borde según el enfoque
        listeners();

        //Placeholder al campo de texto
        new PlaceHolder(placeHolder, passField);
    }

    /**
     * Función para iniciar los componentes del panel
     */
    private void initComponents() {

        //Propiedades del campo de texto
        passField.setBorder(null);
        passField.setBackground(BLANCO);
        passField.setEchoChar('•');
        passField.setFont(segoe(16, PLAIN));

        //Propiedades del botón
        try {
            //Obtener la imágen
            ImageIcon img;
            img = new ImageIcon(getClass().getResource("/icons/open_eye.png"));
            imgShow = new ImageIcon(img.getImage().getScaledInstance(24, 24, ESCALA_SUAVE));

            img = new ImageIcon(getClass().getResource("/icons/close_eye.png"));
            imgHide = new ImageIcon(img.getImage().getScaledInstance(24, 24, ESCALA_SUAVE));

            //Asignar la imágen
            btnShow.setIcon(imgHide);

        } catch (Exception e) {
            //Mensaje de error
            msjAdvertencia(
                    "No se encontró el ícono del botón para mostrar la contraseña.\n"
                    + "El software seguirá ejecutandose normalmente sin el logo."
            );

        } finally {
            //Asignar el cursor de mano, en cualquier caso
            btnShow.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnShow.setVerticalAlignment(JLabel.CENTER);
            btnShow.setHorizontalAlignment(JLabel.CENTER);
            btnShow.setBackground(BLANCO);
        }

        this.add(passField);
        this.add(btnShow);
    }

    /**
     * Función para asignar los listener a los componentes
     */
    private void listeners() {
        passField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                //Borde gris cuando pierda el enfoque
                setBorder(createLineBorder(GRIS_OSCURO));
            }

            @Override
            public void focusGained(FocusEvent e) {
                //Borde celeste cuando obtenga el enfoque
                setBorder(createLineBorder(CELESTE));
            }
        });

        btnShow.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //Asignar el fondo gris cuando sea presionado, en caso de que
                //NO se tengan los íconos
                if (imgShow == null || imgHide == null) {
                    btnShow.setBackground(GRIS_CLARO);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Validar que las imágenes NO sea null
                if (imgShow != null && imgHide != null) {

                    //Validar si el botón está mostrando la clave
                    if (btnShow.getIcon().equals(imgShow)) {
                        //Ocultar la clave
                        btnShow.setIcon(imgHide);
                        passField.setEchoChar('•');

                    } else {
                        //Mostrar la clave
                        btnShow.setIcon(imgShow);
                        passField.setEchoChar((char) 0);
                    }

                } else {
                    btnShow.setBackground(BLANCO);
                }
            }
        });
    }

    //MÉTODOS PÚBLICOS
    /**
     * Función para obtener la contraseña escrita en el campo de texto
     * @return Cadena de carácteres
     */
    public char[] getPassword() {
        return passField.getPassword();
    }
    
    /**
     * Función para colocar un texto en el campo
     * @param text Texto que será mostrado
     */
    public void setText(String text) {
        passField.setText(text);
    }
    
    /**
     * Función para ocultar la contraseña
     */
    public void hidePassword() {
        //Ocultar la clave
        btnShow.setIcon(imgHide);
        passField.setEchoChar('•');
    }
    
    
    //MÉTODOS SOBREESCRITOS
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);

        int x = width - height;
        btnShow.setBounds(x, 0, height, height);
        int gap = 4;
        passField.setBounds(gap * 2, gap, x - gap * 2, height - 4 * 2);
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);

        int w = (int) (d.getWidth());
        int h = (int) (d.getHeight());
        int x = w - h;

        btnShow.setBounds(x, 0, h, h);
        int gap = 4;
        passField.setBounds(gap * 2, gap, x - gap * 2, h - 4 * 2);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        x = width - height;
        btnShow.setBounds(x, 0, height, height);
        int gap = 4;
        passField.setBounds(gap * 2, gap, x - gap * 2, height - 4 * 2);
    }
    
    @Override
    public void requestFocus() {
        passField.requestFocus();
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        passField.addKeyListener(l);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled); 
        
        passField.setEnabled(enabled);
        btnShow.setEnabled(enabled);
    }
    
    
    
    //COMPONENTES
    private final JPasswordField passField = new JPasswordField();
    private final JLabel btnShow = new JLabel();
    private ImageIcon imgShow;
    private ImageIcon imgHide;
}
