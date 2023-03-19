package database;

import static database.ReadDB.*;
import static properties.Mensaje.msjInformativo;

/**
 * Clase para las funciones que realizarán los registros
 * de compra, venta, recarga y trasvaso con las bases de datos
 */
public class RegistrarDB {
    
    /**
     * Función para validar los datos con la base de datos 
     * y registrar una venta de botellones
     * @param cantidad Cantidad de botellones vendidos
     * @param tipoPago Tipo de pago realizado por el cliente
     * @param delivery Si se hizo en delivery o no
     * @param cedula Cédula del cliente
     */
    public static void venta(int cantidad, String tipoPago, boolean delivery, String cedula){
        
        //Obtener el precio de los botellones
        double precio = getPrecioVenta();
        
        //Realizar el registro SOLO si el precio es mayor a 0
        if(precio > 0){
            double total = cantidad * precio;
            
            
            
            
            msjInformativo("Se hizo el registro de la venta exitosamente!");
        }
    }
    
    /**
     * Función para validar los datos con la base de datos 
     * y registrar un trasvaso de botellones
     * @param entregados Cantidad de botellones trasvasados
     * @param pagados Cantidad de botellones pagados por el cliente
     * @param tipoPago Tipo de pago realizado por el cliente
     * @param delivery Si se hizo en delivery o no
     * @param cedula Cédula del cliente
     */
    public static void trasvaso(int entregados, int pagados, String tipoPago, boolean delivery, String cedula){
        //Obtener el precio de los botellones
        double precio = getPrecioVenta();
        
        if(precio > 0){
            
            
            msjInformativo("Se hizo el registro de la venta exitosamente!");
        }
    }
    
    /**
     * Función para registrar una compra de botellones 
     * en la base de datos
     * @param cantidad Cantidad de botellones comprados
     * @param precio Precio de cada uno de los botellones
     * @param rif RIF del proveedor
     */
    public static void compra(int cantidad, double precio, String rif){
        
        msjInformativo("Se hizo el registro de la venta exitosamente!");
        
    }
    
    /**
     * Función para registrar una recarga de botellones en
     * la base de datos
     * @param cantidad Cantidad de los botellones recargados
     * @param precio Precio de cada una de las recargas
     * @param rif RIF del proveedor
     */
    public static void recarga(int cantidad, double precio, String rif){
        
        msjInformativo("Se hizo el registro de la venta exitosamente!");
        
    }    
}
