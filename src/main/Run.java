package main;


public class Run {

    /**
     * Componente para el login del software
     */
    private static login.Frame login;
    private static main.Frame mainFrame;

    public static void main(String[] args) {
        iniciarLogin();
        
//        iniciarPrograma("DIEGO", 1);
    }

    /**
     * Iniciar login
     */
    public static void iniciarLogin() {
        login = new login.Frame();
    }

    /**
     * Función para iniciarMain la ventana del login
     */
    public static void cerrarLogin() {
        if (login != null) {
            login.dispose();
        }
    }

    /**
     * Función para iniciar el programa principal
     *
     * @param identificacion Identificación del usuario
     * @param rol Rol del usuario
     * @param nombre Nombre del usuario
     */
    public static void iniciarPrograma(String identificacion, int rol, String nombre) {
        mainFrame = new main.Frame(identificacion, rol, nombre);
    }

    public static boolean actualizarPrograma() {
        if (mainFrame != null) {
            return mainFrame.actualizarPrograma();

        } else {
            return false;
        }
    }

    public static void setFrameVisible(boolean visible) {
        if (mainFrame != null) {
            mainFrame.setVisible(visible);
        }
    }
    
    /**
     * Función para cerrar el frame del programa principal
     */
    public static void cerrarPrograma() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
    }

    /**
     * Función para asignar el titulo de la ventana del programa principal
     *
     * @param tab Nombre de la pestaña abierta
     */
    public static void setFrameTitle(String tab) {
        if (mainFrame != null) {
            mainFrame.setTitle("AquaTech - " + tab);
        }
    }

    /**
     * Función para actualizar la interfaz gráfica del Frame principal del
     * programa
     */
    public static void repaintFrame() {
        if (mainFrame != null) {
            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }

    /**
     * Función para obtener el Frame del programa principal
     *
     * @return
     */
    public static java.awt.Frame getFrameRoot() {
        return mainFrame;
    }

}
