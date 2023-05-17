package database;

import java.sql.Connection;
import java.sql.SQLException;
import static java.sql.DriverManager.getConnection;
import java.sql.ResultSet;
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
    private Connection cx;
    
    public void conectar(){
        try {
            Class.forName(driver);
            cx = getConnection(url+bd, user, password);
        
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            
            Mensaje.msjError(
                    "<html>"
                    + "<p>No se pudo realizar la conexión con la base de datos.</p>"
                    + "<p>Error: "+ex.toString()+"</p>"
                    + "</html>"
            );
        }
    }
    
    public void desconectar(){
        try {
            if(cx != null){
                cx.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Mensaje.msjError(
                    "<html>"
                    + "<p>No se pudo desconectar la base de datos.</p>"
                    + "<p>Error: "+ex.toString()+"</p>"
                    + "</html>"
            );
        }
    }
    
    public ResultSet ejecutarQuery(String query){
        try {
            if(cx != null){
                ResultSet result = cx.createStatement().executeQuery(query);
                return result;
                
            } else {
                return null;
            }
            
        } catch (SQLException e) {
            Mensaje.msjError("No se pudo ejecutar al sentencia SQL.\nError: "+e);
            return null;
        }
    }
}