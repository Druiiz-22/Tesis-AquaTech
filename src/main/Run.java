package main;

import database.ConexionDB;
import database.ReadDB;

public class Run {

    /**
     * Componente para el login del software
     */
    private static login.Frame login;
    private static main.Frame mainFrame;

    public static void main(String[] args) {
        
//        System.out.println("Conectar con la bdd...\n");
//        
//        
//        try {
//            String query = "SELECT * FROM Cliente";
//            ResultSet result = bdd.createStatement().executeQuery(query);
//            
//            String datos[] = new String[5];
//            
//            while(result.next()){
//                datos[0] = result.getString(1);
//                datos[1] = result.getString(2);
//                datos[2] = result.getString(3);
//                datos[3] = result.getString(4);
//                datos[4] = result.getString(5);
//                System.out.println("Datos = "+Arrays.toString(datos));
//            }
//            
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        
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
