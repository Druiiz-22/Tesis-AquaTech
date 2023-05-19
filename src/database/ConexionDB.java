package database;

import java.sql.Connection;
import java.sql.SQLException;
import static java.sql.DriverManager.getConnection;
import java.sql.ResultSet;
import login.IniciarPrograma;
import properties.Mensaje;

/**
 * Clase para realizar la conexión con la base de datos
 */
public class ConexionDB {

    private static final String bd = "diazmava_aquatech";
    private static final String url = "jdbc:mysql://diazmavarez.site/";
    private static final String user = "diazmava_admin";
    private static final String password = "Jere05032001.";
    
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static int showErrorCount = 0;
    private Connection cx;

    public void conectar() {
        try {
            Class.forName(driver);
            cx = getConnection(url + bd, user, password);
            

        } catch (ClassNotFoundException | SQLException ex) {
            showErrorCount++;
            
            //Mostrar un mensaje cuando NO se esté mostrando la ventana de carga
            //y que el mensaje de error NO se haya mostrado más de una vez
            if(!IniciarPrograma.isActivated() && showErrorCount <= 1){
                //Cuando el programa esté iniciado, mostrar mensaje de error
                Mensaje.msjError("No se pudo realizar la conexión con la base de datos."
                    + "\nPor favor, verifique que su conexión a internet sea\n"
                    + "estable y vuelva a intentarlo.");
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

    public ResultSet ejecutarQuery(String query) {
        try {
            if (cx != null) {
                ResultSet result = cx.createStatement().executeQuery(query);
                return result;

            } else {
                return null;
            }

        } catch (SQLException e) {
            Mensaje.msjError("No se pudo ejecutar al sentencia SQL.\nError: " + e);
            return null;
        }
    }
    
    public static void resetErrorCount(){
        showErrorCount = 0;
    }
}
