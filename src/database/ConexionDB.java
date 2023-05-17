package database;

import java.sql.Connection;
import java.sql.SQLException;
import static java.sql.DriverManager.getConnection;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Clase para realizar la conexión con la base de datos
 */
public class ConexionDB {
    
    private static String bd = "tesis_prueba";
    private static String url = "jdbc:mysql://db4free.net/";
    private static String user = "aqua_admin";
    private static String password = "admin123";
    
//    private static final String bd = "diazmava_aquatech";
//    private static final String url = "jdbc:mysql://169.254.1.15/";
//    private static final String user = "diazmava_admin";
//    private static final String password = "Jere05032001.";
    
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private Connection cx;
    
    public Connection conectar(){
        try {
            Class.forName(driver);
            cx = getConnection(url+bd, user, password);
        
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
//            if(ex.getMessage().contains("Unknown database")){
//                showMessageDialog(null, 
//                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">No es posible hacer la conexión con la base de datos.</font><br>"+
//                                "<i>Causa: "+ex.getMessage()+".</i><br>"+
//                                "El programa no podrá iniciar hasta que se establesca la conexión</div></html>",
//                                "Error de conexión",
//                                ERROR_MESSAGE);
//                
//            } else if(ex.getMessage().contains("Communications link failure")){
//                showMessageDialog(null, 
//                                "<html><font color=\"#cc0000\">Algo falló con la conexión con la base de datos.</font><br>"+
//                                "Causas Probables:<br>"+
//                                "<ul><li>La conexión con la base de datos está apagado.</li>"+
//                                "<li>La dirección URL de la base de datos es incorrecta.</li></ul>"+
//                                "El programa no podrá iniciar hasta que se establesca la conexión</html>",
//                                "Error de conexión",
//                                ERROR_MESSAGE);
//                
//            } else if(ex.toString().contains("ClassNotFoundException")){
//                showMessageDialog(null, 
//                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">El driver para la conexión no fue encontrado.</font><br>"+
//                                "<i>Causa: "+ex.getMessage()+".</i><br>"+
//                                "El programa no podrá iniciar hasta que se establesca la conexión</div></html>",
//                                "Error de conexión",
//                                ERROR_MESSAGE);
//                
//            } else if(ex.toString().contains("Access denied for user")) {
//                showMessageDialog(null, 
//                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">El usuario para acceder a la base de datos es incorrecto.</font><br>"+
//                                "<i>Causa: "+ex.getMessage()+".</i></div></html>",
//                                "Error de conexión",
//                                javax.swing.JOptionPane.ERROR_MESSAGE);
//            } else{
//                showMessageDialog(null, 
//                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">Error de conexión no especificado.</font><br>"+
//                                "<i>Causa: "+ex.getMessage()+".</i><br>"+
//                                "El programa no podrá iniciar hasta que se establesca la conexión</div></html>",
//                                "Error de conexión",
//                                ERROR_MESSAGE);
//            }
        }
        return cx;
    }
    
    public void desconectar(){
        try {
            cx.close();
        } catch (SQLException ex) {
            showMessageDialog(null, 
                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">No se puedo desconectar con la base de datos, vuelvalo a intentar.</font><br>"+
                                "<i>Casua: "+ex+"</i></div></html>", 
                                "Error de conexión", 
                                ERROR_MESSAGE);
            
        }
    }
}