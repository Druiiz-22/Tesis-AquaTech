package main;

import tabs.admin.CrearReporte;

public class Run {

    /**
     * Componente para el login del software
     */
    private static login.Frame login;
    private static main.Frame mainFrame;

    public static void main(String[] args) {
        //Iniciar el login
        iniciarLogin();
        
        //iniciarPrograma("DIEGO", 1);
        
//        CrearReporte.crear(1, "F:\\Main Files\\Escritorio\\Reportes", "05-03-2023", "31-03-2023");
//        CrearReporte.crear(2, "F:\\Main Files\\Escritorio\\Reportes", "05-03-2023", "31-03-2023");
//        CrearReporte.crear(3, "F:\\Main Files\\Escritorio\\Reportes", "05-03-2023", "31-03-2023");
//        CrearReporte.crear(4, "F:\\Main Files\\Escritorio\\Reportes", "05-03-2023", "31-03-2023");
//        CrearReporte.crear(5, "F:\\Main Files\\Escritorio\\Reportes", "05-03-2023", "31-03-2023");
//        CrearReporte.crear(6, "F:\\Main Files\\Escritorio\\Reportes");
//        CrearReporte.crear(7, "F:\\Main Files\\Escritorio\\Reportes");
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
     * @param nombreUsuario Nombre del usuario
     * @param rolUsuario Rol del usuario
     */
    public static void iniciarPrograma(String nombreUsuario, int rolUsuario) {

        mainFrame = new main.Frame(nombreUsuario, rolUsuario);

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

    public static void repaintFrame() {
        if (mainFrame != null) {
            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }

}
