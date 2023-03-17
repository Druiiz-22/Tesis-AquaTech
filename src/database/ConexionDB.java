package database;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class ConexionDB {
    private static String bd = "aquatech";
    private static String url = "jdbc:mysql://localhost/";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String user;
    private static String password;
    private static java.sql.Connection cx;

    public ConexionDB(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    public static java.sql.Connection conectar(){
        
        try {
            Class.forName(driver);
            cx = java.sql.DriverManager.getConnection(url+bd, user, password);
        
        } catch (ClassNotFoundException | java.sql.SQLException ex) {
            if(ex.getMessage().contains("Unknown database")){
                showMessageDialog(null, 
                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">No es posible hacer la conexión con la base de datos.</font><br>"+
                                "<i>Causa: "+ex.getMessage()+".</i><br>"+
                                "El programa no podrá iniciar hasta que se establesca la conexión</div></html>",
                                "Error de conexión",
                                ERROR_MESSAGE);
                
            } else if(ex.getMessage().contains("Communications link failure")){
                showMessageDialog(null, 
                                "<html><font color=\"#cc0000\">Algo falló con la conexión con la base de datos.</font><br>"+
                                "Causas Probables:<br>"+
                                "<ul><li>La conexión con la base de datos está apagado.</li>"+
                                "<li>La dirección URL de la base de datos es incorrecta.</li></ul>"+
                                "El programa no podrá iniciar hasta que se establesca la conexión</html>",
                                "Error de conexión",
                                ERROR_MESSAGE);
                
            } else if(ex.toString().contains("ClassNotFoundException")){
                showMessageDialog(null, 
                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">El driver para la conexión no fue encontrado.</font><br>"+
                                "<i>Causa: "+ex.getMessage()+".</i><br>"+
                                "El programa no podrá iniciar hasta que se establesca la conexión</div></html>",
                                "Error de conexión",
                                ERROR_MESSAGE);
                
            } else if(ex.toString().contains("Access denied for user")) {
                showMessageDialog(null, 
                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">El usuario para acceder a la base de datos es incorrecto.</font><br>"+
                                "<i>Causa: "+ex.getMessage()+".</i></div></html>",
                                "Error de conexión",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
            } else{
                showMessageDialog(null, 
                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">Error de conexión no especificado.</font><br>"+
                                "<i>Causa: "+ex.getMessage()+".</i><br>"+
                                "El programa no podrá iniciar hasta que se establesca la conexión</div></html>",
                                "Error de conexión",
                                ERROR_MESSAGE);
            }
        }
        return cx;
    }
    
    public static void desconectar(){
        try {
            cx.close();
        } catch (java.sql.SQLException ex) {
            showMessageDialog(null, 
                                "<html><div style=\"text-align:center;\"><font color=\"#cc0000\">No se puedo desconectar con la base de datos, vuelvalo a intentar.</font><br>"+
                                "<i>Casua: "+ex+"</i></div></html>", 
                                "Error de conexión", 
                                ERROR_MESSAGE);
            
        }
    }
}