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
    private static int showErrorCount = 0;
    
    private String bd = "diazmava_aquatech";
    private String url = "jdbc:mysql://diazmavarez.site/";
    private String user = "diazmava_admin";
    private String password = "Jere05032001.";
    private Connection cx;

    public ConexionDB(boolean web){
        //Determinar si se conectará a la base de datos en la nube o local
        if(web){
            bd = "diazmava_aquatech";
            url = "jdbc:mysql://diazmavarez.site/";
            user = "diazmava_admin";
            password  = "Jere05032001.";
        } else {
            
        }
    }
    
    public void conectar() {
        try {
            Class.forName(DRIVER);
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

    public ResultSet selectQuery(String query) {
        try {
            if (cx != null) {
                ResultSet result = cx.createStatement().executeQuery(query);
                return result;
            }
        } catch (SQLException e) {
            Mensaje.msjError("No se pudo ejecutar la sentencia SQL para leer la "
                    + "base de datos.\nError: " + e);
        }
        return null;
    }
    
    public int executeQuery(String sql){
        try {
            if(cx != null){
                PreparedStatement pst = cx.prepareStatement(sql);
                return pst.executeUpdate();
            }
        } catch (SQLException e) {
            String msj = e.getMessage().toUpperCase();
            
            //Comprobar si el error fue por un dato duplicado
            if(msj.contains("DUPLICATE ENTRY")){
                return properties.Constantes.DUPLICATE_ERROR;
            }
            
            //Si no, mostrar con detalles el error
            Mensaje.msjError("No se pudo ejecutar la sentencia SQL en la base de"
                    + " datos.\nError: " + e);
        }
        return properties.Constantes.ERROR_VALUE;
    }
    
    
    
    public static void resetErrorCount(){
        showErrorCount = 0;
    }
}
