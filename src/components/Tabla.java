package components;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tabla extends JScrollPane implements properties.Constantes{
    
    
    public Tabla(int type){
        
        //Comprobar el tipo de tabla que se creará
        switch (type) {
            case CLIENTES:
                
                break;
        }
    }
    
    //Video
    //https://www.youtube.com/watch?v=Psoe3tfM_Ss
    
    private String[] encabezado;
    private JTable tabla;
    private DefaultTableModel modelo;
}
