package database;

import static database.ReadDB.getPrecioVenta;
import static properties.Mensaje.msjInformativo;

/**
 * Clase contenedora de los métodos para intentar insertar datos nuevos a la
 * base de datos y retornar un booleano en caso de que se realicen con o sin
 * éxito
 */
public class CreateDB {

    /**
     * Función para crear un nuevo cliente en la base de datos
     *
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @return
     */
    public static boolean createCliente(int cedula, String nombre, String apellido, String telefono) {

//        Mensaje por si no se puede conectar con la base de datos
//        msjError("No se pudo crear el cliente en la base de datos."
//                + "\nPor favor, verifique su conexión.");
//        
//        Mensaje por si el usuario no coincide
//        msjError("<html>Su contraseña es incorrecta. <b>No se creará el cliente</b> "
//        + "hasta que valide sus datos.</html>");
        return true;
    }

    /**
     * Función para crear un nuevo proveedor en la base de datos
     *
     * @param rif
     * @param nombre
     * @param telefono
     * @return
     */
    public static boolean createProveedor(String rif, String nombre, String telefono) {

        return true;
    }
    
    /**
     * Función para crear un usuario operativo nuevo en el sistema 
     * 
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @param correo
     * @param clave
     * @param rol
     * 
     * @return 
     */
    public static boolean createUsuario(int cedula, String nombre, String apellido, String telefono, String correo, String clave, int rol){
     
        //Crear un cliente, con los mismos datos del usuario, en el sistema
        if(createCliente(cedula, nombre, apellido, telefono)){
            
        }
        
        return true;
    }

    /**
     * Función para validar los datos con la base de datos y registrar un
     * trasvaso de botellones
     *
     * @param entregados Cantidad de botellones trasvasados
     * @param pagados Cantidad de botellones pagados por el cliente
     * @param tipoPago Tipo de pago realizado por el cliente
     * @param delivery Si se hizo en delivery o no
     * @param cedula Cédula del cliente
     * @return
     */
    public static boolean createTravaso(int entregados, int pagados, String tipoPago, boolean delivery, String cedula) {

        //Obtener el precio de los botellones
        double precio = getPrecioVenta();

        if (precio > 0) {

            msjInformativo("Se hizo el registro del trasvaso exitosamente!");
        }

        return true;
    }

    /**
     * Función para validar los datos con la base de datos y registrar una venta
     * de botellones
     *
     * @param cantidad Cantidad de botellones vendidos
     * @param tipoPago Tipo de pago realizado por el cliente
     * @param delivery Si se hizo en delivery o no
     * @param cedula Cédula del cliente
     * @return
     */
    public static boolean createVenta(int cantidad, String tipoPago, boolean delivery, String cedula) {
        //Obtener el precio de los botellones
        double precio = getPrecioVenta();

        //Realizar el registro SOLO si el precio es mayor a 0
        if (precio > 0) {
            double total = cantidad * precio;

            msjInformativo("Se hizo el registro de la venta exitosamente!");
        }

        return true;
    }

    /**
     * Función para registrar una compra de botellones en la base de datos
     *
     * @param cantidad Cantidad de botellones comprados
     * @param precio Precio de cada uno de los botellones
     * @param rif RIF del proveedor
     * @return
     */
    public static boolean createRecarga(int cantidad, double precio, String rif) {

        msjInformativo("Se hizo el registro de la recarga exitosamente!");

        return true;
    }

    /**
     * Función para registrar una recarga de botellones en la base de datos
     *
     * @param cantidad Cantidad de los botellones recargados
     * @param precio Precio de cada una de las recargas
     * @param rif RIF del proveedor
     * @return
     */
    public static boolean createCompra(int cantidad, double precio, String rif) {

        msjInformativo("Se hizo el registro de la compra exitosamente!");

        return true;
    }

}
