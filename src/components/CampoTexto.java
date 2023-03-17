package components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import properties.PlaceHolder;
import static java.awt.Font.PLAIN;
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createLineBorder;
import static javax.swing.BorderFactory.createEmptyBorder;
import static properties.Fuentes.segoe;
import static properties.ValidarTexto.esUnaLetra;
import static properties.ValidarTexto.esUnNumero;
import static properties.ValidarTexto.esUnDecimal;
import static properties.ValidarTexto.esUnCorreo;
import static properties.ValidarTexto.esUnaFecha;

/**
 * Clase para la creación de TextField personalizados que se utilizarán en el
 * software.
 */
public class CampoTexto extends JTextField implements properties.Colores, properties.Constantes {

    /**
     * Constructor de los campos de textos utilizados en el software.
     *
     * @param placeHolder Texto falso que mostrará el campo de texto.
     * @param type Tipo de dato que permitirá ingresar el campo de texto.
     */
    public CampoTexto(String placeHolder, int type) {
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

        //Asignar el tipo de texto 
        textType(type);

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

    /**
     * Función que permite determinar el tipo de dato que portará el campo de
     * texto, ya sea: Nombres, enteros, decimales, correos, etc.
     * <br><br>
     * Para realizar esto, la función ingresa un listener en el componente para
     * las teclas presionadas, comprueba el tipo de campo determinado y valida
     * la tecla presionada; en caso de que la tecla presionada NO corresponda al
     * tipo de carácteres que permite el campo, este se anulará y no será
     * ingresada en el campo de texto.
     *
     * @param type Tipo de campo de texto
     */
    private void textType(int type) {
        
        //Validar que el campo NO permita cualquier carácter
        if (type != CUALQUIER) {
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    //Switch-case para determinar el tipo de campo
                    //de texto creado.
                    switch (type) {
                        case NOMBRE:
                            //Si el campo es de tipo "nombre", entonces
                            //solo se permitirá ingresar letras, espacios
                            //y presionar el backspace (borrar).
                            if (!esUnaLetra(e.getKeyChar())) {

                                //En caso de NO ser válido, se anulará la
                                //tecla presionada
                                e.consume();
                            }
                            break;

                        case NUMERO:
                            //Si el campo es de tipo "numero", entonces
                            //solo se permitirá ingresar números enteros
                            //y presionar el backspace.

                            if (!esUnNumero(e.getKeyChar())) {

                                //En caso de NO ser válido, se anulará la
                                //tecla presionada
                                e.consume();
                            }
                            break;

                        case DECIMAL:
                            //Si el campo es de tipo "decimal", entonces
                            //solo se permitirá ingresar dítigos, un punto
                            //y presionar el backspace.
                            if (!esUnDecimal(getText(), e.getKeyChar())) {

                                //En caso de NO ser válido, se anulará la
                                //tecla presionada
                                e.consume();
                            }
                            break;

                        case CORREO:
                            //Si el campo es de tipo "correo", entonces
                            //solo se permitirá ingresar los carácteres
                            //básicos de un correo
                            if (!esUnCorreo(e.getKeyChar())) {

                                //En caso de NO ser válido, se anulará la
                                //tecla presionada
                                e.consume();
                            }
                            break;

                        case FECHA:
                            //Si el campo es de tipo "decimal", entonces
                            //solo se permitirá ingresar dítigos, guión
                            //y presionar el backspace.
                            if (!esUnaFecha(e.getKeyChar())) {

                                //En caso de NO ser válido, se anulará la
                                //tecla presionada
                                e.consume();
                            }
                            break;

                    }
                }
            });
        }
    }
}
