package database;

/**
 * Clase contenedora de funciones estáticos para la obtención de datos de la
 * base de datos
 */
public class ReadDB {

    /**
     * Función para obtener el precio actual de los trasvasos
     *
     * @return Precio de los trasvasos
     */
    public static double getPrecioTrasvaso() {
        return 7.5;
    }

    /**
     * Función para obtener el precio de venta actual de los botellones
     *
     * @return Precio de venta de los botellones
     */
    public static double getPrecioVenta() {
        return 100;
    }

    /**
     * Función para obtener la cantidad de botellones que tiene el local
     *
     * @return Cantidad de botellones que tiene el local
     */
    public static int getBotellonesAlmacen() {
        return 25;
    }

    /**
     * Función para obtener la cantidad de clientes que deben pagar por
     * trasvasos
     *
     * @return Cantidad de clientes que deben pagar
     */
    public static int getClientesPagar() {
        return 4;
    }

    /**
     * Función para obtener la cantidad de clientes que la empresa les debe
     * recargar botellones
     *
     * @return Cantidad de clientes que se le deben recargar
     */
    public static int getClientesEntregar() {
        return 2;
    }

    /**
     * Función para obtener la cantidad actual de botellones llenos en el local
     *
     * @return Cantidad de botellones llenos
     */
    public static int getBotellonesLlenos() {
        return 20;
    }

    /**
     * Función para obtener la cantidad actual de botellones vacíos en el local
     *
     * @return Cantidad de botellones vacíos
     */
    public static int getBotellonesVacios() {
        return 5;
    }
    
    /**
     * Función para obtener todos los clientes registrados en el sistema
     * 
     * @return Clientes registrados
     */
    public static Object[][] getClientes() {
        //Cedula, Nombre, Apellido, Telefono, Direccion
        
        Object[][] clientes = {
            {"27909011", "DIEGO", "RUIZ", "04120268484", "SUR AMÉRICA"},
            {"30445134", "JESUS", "GONZALES", "04246451278", "SAN FELIPE"},
            {"25451987", "PEDRO", "ALVAREZ", "04142601212", "SIERRA MAESTRA"},
            {"20154005", "HECTOR", "LOPEZ", "04160884512", "SABANA GRANDE"},
            {"27909011", "DIEGO", "RUIZ", "04120268484", "SUR AMÉRICA"},
            {"30445134", "JESUS", "GONZALES", "04246451278", "SAN FELIPE"},
            {"25451987", "PEDRO", "ALVAREZ", "04142601212", "SIERRA MAESTRA"},
            {"20154005", "HECTOR", "LOPEZ", "04160884512", "SABANA GRANDE"}
        };

        return clientes;
    }
    
    /**
     * Función para obtener todos los proveedores registrados en el sistema
     *
     * @return Proveedores registrados
     */
    public static Object[][] getProveedores() {
        //RIF, Nombre, Telefono, Direccion
        
        Object[][] proveedores = {
            {"J-516513", "AGUA RICA", "04120268754", "SUR AMERICA"},
            {"J-516510", "AGUA BLANCA", "04166484512", "LOS ROBLES"},
            {"J-516671", "LA CATEDRAL", "04241349765", "HATICOS"},
            {"J-671164", "MANANTIAL", "041231645077", "SIERRA MESTRA"}
        };

        return proveedores;
    }

    /**
     * Función para obtener el historial completo de los trasvasos
     *
     * @return Historial de los trasvasos
     */
    public static Object[][] getTrasvasos() {
        //ID, Cedula, Pagados, Entregados, Pago, Delivery, Monto total, Fecha
        
        Object[][] historial = {
            {"1", "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {"2", "20154005", "10", "10", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {"3", "30445134", "4", "4", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {"4", "25451987", "6", "6", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"}
        };

        return historial;
    }
    
    /**
     * Función para obtener el historial completo de las recargas
     *
     * @return Historial de las recargas
     */
    public static Object[][] getRecargas() {
        //ID, RIF, Proveedor, Cantidad, Monto Total, Fecha
        
        Object[][] historial = {
            {"1", "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {"2", "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {"3", "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {"4", "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"}
        };

        return historial;
    }
    
    /**
     * Función para obtener el historial completo de las ventas de botellones
     *
     * @return Historial de las ventas
     */
    public static Object[][] getVentas() {
        //ID, Cedula, Cantidad, Tipo pago, Monto Total, Fecha
        
        Object[][] historial = {
            {"1", "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {"2", "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
        };

        return historial;
    }
    
    /**
     * Función para obtener el historial completo de las compras de botellones
     *
     * @return Historial de las compras
     */
    public static Object[][] getCompras() {
        //ID, RIF, Proveedor, Cantidad, Monto Total, Fecha
        
        Object[][] historial = {
            {"1", "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {"2", "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
        };

        return historial;
    }
}
