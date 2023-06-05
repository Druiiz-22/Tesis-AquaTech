package database;

import java.sql.Connection;
import java.sql.SQLException;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import login.IniciarPrograma;
import properties.Mensaje;

/**
 * Clase para realizar la conexión con la base de datos
 */
public class ConexionDB {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static final String bd = "diazmava_aquatech";
    private static final String host = "jdbc:mysql://diazmavarez.site/";
    private static final String user = "diazmava_admin";
    private static final String password = "Jere05032001.";
    private Connection cx;

    public void conectar() {
        try {
            Class.forName(DRIVER);
            cx = getConnection(host + bd, user, password);

        } catch (ClassNotFoundException | SQLException ex) {
            //Validar que NO esté activa la ventana de inicio de programa (no se
            //mostrará este mensaje de error en esa ventana)
            System.out.println("Conexion ex = "+ex);
            if (!IniciarPrograma.isActivated()) {
                
                if(ex.getMessage().toUpperCase().contains("COMMUNICATIONS LINK FAILURE")){
                    //Mostrar mensaje de error por conexión
                    Mensaje.msjError("No se pudo realizar la conexión con la base de datos."
                            + "\nPor favor, verifique que su conexión a internet sea\n"
                            + "estable y vuelva a intentarlo.");
                } else{
                    Mensaje.msjError("No se pudo realizar la conexión con la base"
                            + "de datos.\nError: "+ex);
                }
            }
        }
    }

    public void desconectar() {
        try {
            if (cx != null) {
                cx.close();
            }
        } catch (SQLException ex) {
            Mensaje.msjError("No se pudo desconectar la base de datos.\nError: "
                    + ex.toString());
        }
    }

    public ResultSet selectQuery(String query) {
        try {
            if (cx != null) {
                ResultSet result = cx.createStatement().executeQuery(query);
                return result;
            }
        } catch (SQLException e) {
            //Si no, mostrar con detalles el error
            Mensaje.msjError("No se pudo ejecutar la sentencia SQL en la base de"
                    + " datos.\nError: " + e);
        }
        return null;
    }

    public int executeQuery(String sql) {
        try {
            if (cx != null) {
                PreparedStatement pst = cx.prepareStatement(sql);
                return pst.executeUpdate();
            }
        } catch (SQLException e) {
            String msj = e.getMessage().toUpperCase();

            //Comprobar si el error fue por un dato duplicado
            if (msj.contains("DUPLICATE ENTRY")) {
                return properties.Constantes.DUPLICATE_ERROR;
            }

            //Si no, mostrar con detalles el error
            Mensaje.msjError("No se pudo ejecutar la sentencia SQL en la base de"
                    + " datos.\nError: " + e);
        }
        return properties.Constantes.ERROR_VALUE;
    }
    
    public static String getUserBD(){
        return user;
    }
    
    public static String getPassBD(){
        return password;
    }
    
    public static String getHostName(){
        String name = host.replace("jdbc:mysql://", "");
        name = name.replace("/", "");
        return name;
    }
    
    public static String getNameDB(){
        return bd;
    }
}
