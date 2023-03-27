package properties;

import javax.swing.JOptionPane;

/**
 * Clase con métodos estáticos para el uso de mensajes JOptionPane
 */
public class Mensaje extends JOptionPane {

    /**
     * <h2>Función para mostrar un mensaje en caso de error.</h2>
     *
     * @param msj Mensaje que será mostrado.
     */
    public static void msjError(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Error", ERROR_MESSAGE);
    }

    /**
     * <h2>Función para mostrar un mensaje en caso de error.</h2>
     *
     * @param msj Mensaje que será mostrado.
     */
    public static void msjAdvertencia(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Advertencia", WARNING_MESSAGE);
    }

    /**
     * <h2>Función para mostrar un mensaje informativo.</h2>
     *
     * @param msj Mensaje que será mostrado.
     */
    public static void msjInformativo(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Mensaje informativo", INFORMATION_MESSAGE);
    }

    /**
     * <h2>Función para mostrar un mensaje con un dialogo confirmativo.</h2>
     *
     * @param msj Mensaje que será mostrado.
     * @return 
     */
    public static boolean msjYesNo(String msj) {
        
        return JOptionPane.showConfirmDialog(
                null, msj, "Confirmar", YES_NO_OPTION, QUESTION_MESSAGE
        ) == YES_OPTION;
    }
    
    /**
     * <h2>Función para mostrar un mensaje advertivo con un dialogo confirmativo.</h2>
     *
     * @param msj Mensaje que será mostrado.
     * @return 
     */
    public static boolean msjYesNoWarning(String msj) {
        return JOptionPane.showConfirmDialog(
                null, msj, "Confirmar", YES_NO_OPTION, WARNING_MESSAGE
        ) == YES_OPTION;
    }

    /**
     * <h2>Función para mostrar un mensaje en caso de error.</h2>
     *
     * @param msj Mensaje que será mostrado.
     * @return 
     */
    public static int msjOkCancel(String msj) {
        return JOptionPane.showConfirmDialog(
                null, msj, "Confirmar", OK_CANCEL_OPTION, QUESTION_MESSAGE
        );
    }

}
