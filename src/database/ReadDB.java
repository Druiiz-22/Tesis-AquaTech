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
     * Función para comprobar la existencia de un correo en la base de datos
     *
     * @param correo Correo que será validado
     *
     * @return
     */
    public static boolean emailExists(String correo) {

        return true;
    }

    /**
     * Función para comprobar la existencia y validez de un usuario
     *
     * @param user
     * @param pass
     * @return
     */
    public static boolean getUser(String user, int pass) {

        return !(user.isEmpty() || pass == 0);
    }
    
    //========== HISTORIAL ==========
    /**
     * Función para obtener todos los clientes registrados en el sistema
     *
     * @return Clientes registrados
     */
    public static Object[][] getClientes() {
        //ID, Cedula, Nombre, Apellido, Telefono, Direccion

        //Cédula minima y máxima
        int ci_min = 5000000;
        int ci_max = 35000000;
        int id = 1;

        Object[][] clientes = {
            {id++, "27909011", "DIEGO", "RUIZ", "04120268484"},
            {id++, "30445134", "JESUS", "GONZALES", "04246451278"},
            {id++, "20154005", "HECTOR", "LOPEZ", "04160884512"},
            {id++, "25451987", "MARIA", "JIMENEZ", "04146451277"},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "ANGEL", "DE LA CRUZ", "0412" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "CARLOS", "ALVAREZ", "0424" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)},
            {id++, (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), "OSCAR", "MEDINA", "0416" + (int) (Math.random() * (ci_max - ci_min + 1) + ci_min)}
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

        int id = 1;

        Object[][] proveedores = {
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},
            {id++, "J-516513", "AGUA RICA", "04120268754"},
            {id++, "J-516510", "AGUA BLANCA", "04166484512"},
            {id++, "J-516671", "LA CATEDRAL", "04241349765"},
            {id++, "J-671164", "MANANTIAL", "041231645077"},};

        return proveedores;
    }

    /**
     * Función para obtener el historial completo de los trasvasos
     *
     * @return Historial de los trasvasos
     */
    public static Object[][] getTrasvasos() {
        //ID, Cedula, Pagados, Entregados, Pago, Delivery, Monto total, Fecha

        int i = 0;

        Object[][] historial = {
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {974, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {984, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {990, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},
            {i++, "27909011", "2", "2", "EFECT", "NO", "28.0", "16-3-2023 10:08 AM"},
            {i++, "20154005", "1", "2", "EFECT", "SI", "28.0", "16-3-2023 10:24 AM"},
            {i++, "30445134", "4", "0", "DOLAR", "NO", "28.0", "16-3-2023 12:19 PM"},
            {i++, "25451987", "0", "4", "TRNSF", "SI", "28.0", "17-3-2023 3:57 PM"},};

        return historial;
    }

    /**
     * Función para obtener las deudas registradas en el sistema
     *
     * @return
     */
    public static Object[][] getDeudas() {
        //ID, Factura, Cedula, Debe Pagar, Debemos Dar, Fecha

        Object[][] historial = {
            {"7", "974", "20154005", "1", "0", "16-3-2023 10:24 AM"},
            {"13", "984", "30445134", "0", "4", "16-3-2023 12:19 PM"},
            {"15", "990", "25451987", "4", "0", "17-3-2023 3:57 PM"}
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
            {"2", "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},};

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
            {"2", "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},};

        return historial;
    }

    /**
     * Función para obtener todos los pedidos registrados en el sistema
     *
     * @return
     */
    public static Object[][] getPedidos() {
        //ID, cedula, servicio, cantidad, tipo_pago, fecha, latitud, longitud

        int rows = 15;
        Object lista[][] = new Object[rows][8];

        //Variables para las fechas
        int dia = 1;
        int hora = 9;
        int minuto = 0;
        int mes = 3;
        int anio = 2023;

        //Cédula minima y máxima
        int ci_min = 5000000;
        int ci_max = 35000000;

        double posiciones[][] = {
            {10.585293809414747, -71.65681002383909},
            {10.586411214531502, -71.6600759296157},
            {10.587085202950155, -71.65502369968503},
            {10.583644299163073, -71.64740926727356},
            {10.587635034534275, -71.65475304444573},
            {10.582846145861863, -71.65919178937048},
            {10.58265104140613, -71.6564671939436},
            {10.588007500522133, -71.6574235090139},
            {10.5879542911241, -71.6590113527064},
            {10.585617897584934, -71.65977969023554},
            {10.581527153778518, -71.64728569103546},
            {10.583549146810844, -71.64609480818609},
            {10.583134169541635, -71.65784095293495},
            {10.585253703446991, -71.6576966035375},
            {10.587116043330223, -71.65775073450976}
        };

        lista[0] = new Object[]{1, 27909011, "COMPRA", 2, "TRNSF", "27-2-2023 12:08", 10.585293809414747, -71.65681002383909};
        lista[1] = new Object[]{2, 30445134, "RECARGA", 4, "TRNSF", "27-2-2023 12:57", 10.586411214531502, -71.6600759296157};
        lista[2] = new Object[]{3, 20154005, "RECARGA", 2, "TRNSF", "27-2-2023 14:22", 10.587085202950155, -71.65502369968503};
        lista[3] = new Object[]{4, 25451987, "COMPRA", 1, "TRNSF", "27-2-2023 16:12", 10.583644299163073, -71.64740926727356};

        for (int i = 4; i < rows; i++) {
            int servicio = (int) (Math.random() * (10 - 1 + 1) + 1);
            int cantidad = (int) (Math.random() * (30 - 1 + 1) + 1);
            int tipoPago = (int) (Math.random() * (3 - 1 + 1) + 1);

            //Los clientes vienen entre 2 a 30 minutos de diferencia
            minuto += (int) (Math.random() * (30 - 2 + 1) + 2);

            //Si los minutos superan los 59min
            if (minuto > 59) {
                //Aumentar una hora
                hora++;
                //Obtener los minutos de esa siguiente hora
                minuto = minuto - 59;

                //Si la hora supera las 19 (7 pm)
                if (hora > 19) {
                    //Aumentar un día
                    dia++;
                    //Reiniciar la hora
                    hora = 9;

                    //Si el día supera los 30 días
                    if (dia > 30) {
                        //Avanzar de mes
                        mes++;
                        //Reinicar los días
                        dia = 1;

                        //Si los meses superan el mes 12
                        if (mes > 12) {

                            //Avanzar de año
                            anio++;
                            //Reiniciar el mes
                            mes = 1;
                        }
                    }
                }
            }
            //Obtener la feha
            String fecha = dia + "-" + mes + "-" + anio + " " + hora + ":" + minuto;

            int ci = (int) (Math.random() * (ci_max - ci_min + 1) + ci_min);

            lista[i] = new Object[]{
                i + 1,
                ci,
                ((servicio == 10) ? "COMPRA" : "RECARGA"),
                cantidad,
                ((tipoPago == 1) ? "EFECT" : (tipoPago == 2) ? "TRNSF" : "DOLAR"),
                fecha,
                posiciones[i][0],
                posiciones[i][1]
            };
        }

        return lista;
    }

    public static Object[][] getTransferencias() {
        //ID, Pedido, Referencia, Banco, Fecha
        
        Object[][] transf = {
            {157, 1, (int) (Math.random() * (999999999 - 10000 + 1) + 10000), "BANESCO", "27-2-2023 12:06"},
            {156, 2, (int) (Math.random() * (999999999 - 10000 + 1) + 10000), "PROVINCIAL", "27-2-2023 12:54"},
            {158, 3, (int) (Math.random() * (999999999 - 10000 + 1) + 10000), "MERCANTIL", "27-2-2023 14:15"},
            {159, 4, (int) (Math.random() * (999999999 - 10000 + 1) + 10000), "VENEZUELA", "27-2-2023 16:06"}
        };

        return transf;
    }

    /**
     * Función para validar la existencia de un cliente en la base de datos,
     * mediante el uso de su cédula.
     *
     * @param cedula Cédula del cliente a buscar
     * @return ID del cliente
     */
    public static int getClienteID(String cedula) {
        return 1;
    }

    /**
     * Función para validar la existencia de un proveedor en la base de datos,
     * mediante el uso de su RIF.
     *
     * @param rif RIF del proveedor a buscar
     * @return ID del proveedor
     */
    public static int getProveedorID(String rif) {
        return 1;
    }

    // ========== ADMINISTRADOR ==========
    /**
     * Función para obtener la lista de los usuarios registrados en el sistema
     *
     * @return
     */
    public static Object[][] getUsers() {
        //ID, Cedula, Rol, Correo

        Object[][] usuarios = {
            {1, "25467109", "ADMIN", "ADMIN", "ADMIN", "0", "administrador@aquatech.com"},
            {2, "21545987", "EMPLEADO", "DIEGO", "RUIZ", "04120268484", "segundo_correo@aquatech.com"},
            {3, "12445781", "ADMIN", "KEVIN", "DUARTE", "04245620012", "kevinduarte59@gmail.com"},};

        return usuarios;
    }

    /**
     * Función para obtener el id de un usuario mediante su cédula
     *
     * @param cedula
     * @return
     */
    public static int getUserID(String cedula) {
        return 1;
    }

    // ========== REPORTES ==========
    /**
     * Función para obtener el historial de las deudas, en una fecha determinada
     *
     * @param initDate Fecha de inicio
     * @param finalDate Fecha final
     * @return Historial de las deudas
     */
    public static Object[][] getDeudas(String initDate, String finalDate) {

        int id = 1;

        //Cédula minima y máxima
        int ci_min = 5000000;
        int ci_max = 35000000;

        String[] columnas = new String[]{"ID", "Factura", "Cedula", "Debe pagar", "Debemos dar", "Fecha"};
        Object[][] historial = {
            columnas,
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"}
        };

        return historial;
    }

    /**
     * Función para obtener el registro de los trasvasos, en una fecha
     * determninada
     *
     * @param initDate Fecha inicial
     * @param finalDate Fecha final
     *
     * @return Registro de los trasvasos
     */
    public static Object[][] getTrasvasos(String initDate, String finalDate) {
        //#, ID, Cedula, Pago, Entr, TipoPago, Delivery, MontoPago, MontoEntr, Fecha
        String[] header = new String[]{"#", "ID", "Cedula", "Pagos", "Entregados", "Tipo pago", "Delivery", "Monto pago", "Monto entre", "Fecha"};

        int rows = 41;
        //numero del id
        int id = 5403;

        //Variables para las fechas
        int dia = 1;
        int hora = 9;
        int minuto = 0;
        int mes = 3;
        int anio = 2023;

        //Cédula minima y máxima
        int min = 5000000;
        int max = 35000000;

        //Lista de trasvasos que será retornado
        Object[][] trasvasos = new Object[rows + 1][header.length];

        //Cabecera
        trasvasos[0] = header;
        //Variable para agregar cada fila
        Object[] row;

        //Ciclo del tamaño de la lista de trasvasos
        for (int i = 1; i < trasvasos.length; i++) {

            //Obtener numeros aleatorios
            int cedula = (int) (Math.random() * (max - min + 1) + min);
            int tipoPago = (int) (Math.random() * 3 + 1);
            int delivery = (int) (Math.random() * 2 + 1);

            //Los clientes vienen entre 2 a 30 minutos de diferencia
            minuto += (int) (Math.random() * (30 - 2 + 1) + 2);

            //Si los minutos superan los 59min
            if (minuto > 59) {
                //Aumentar una hora
                hora++;
                //Obtener los minutos de esa siguiente hora
                minuto = minuto - 59;

                //Si la hora supera las 19 (7 pm)
                if (hora > 19) {
                    //Aumentar un día
                    dia++;
                    //Reiniciar la hora
                    hora = 9;

                    //Si el día supera los 30 días
                    if (dia > 30) {
                        //Avanzar de mes
                        mes++;
                        //Reinicar los días
                        dia = 1;

                        //Si los meses superan el mes 12
                        if (mes > 12) {

                            //Avanzar de año
                            anio++;
                            //Reiniciar el mes
                            mes = 1;
                        }
                    }
                }
            }
            //Obtener la feha
            String fecha = dia + "-" + mes + "-" + anio + " " + hora + ":" + minuto;

            int entregados = (int) (Math.random() * 12 + 1);
            int montoEntregados = entregados * 7;
            int pagos;
            int montoPago;

            //Un cliente hará un pago distinto al monto entregado con una
            //probabilidad de 1/10
            if (((int) (Math.random() * 10 + 1)) == 10) {

                pagos = (int) (Math.random() * 20 + 1);
                montoPago = pagos * 7;

            } else {
                pagos = entregados;
                montoPago = montoEntregados;
            }

            //Guardar el trasvaso
            //#, ID, Cedula, Pago, Entr, TipoPago, Delivery, MontoPago, MontoEntr, Fecha
            row = new Object[]{
                i,
                id,
                cedula,
                pagos,
                entregados,
                (tipoPago == 1) ? "EFECT" : (tipoPago == 2) ? "TRNSF" : "DOLAR",
                (delivery == 1) ? "SI" : "NO",
                montoPago,
                montoEntregados,
                fecha
            };

            //Agregar el trasvaso
            trasvasos[i] = row;

            //Aumentar el ID
            id++;
        }

        //Retornar la lista
        return trasvasos;
    }

    /**
     * Función para obtener el registro de las recargas, en una fecha
     * determninada
     *
     * @param initDate Fecha inicial
     * @param finalDate Fecha final
     *
     * @return Registro de las recargas
     */
    public static Object[][] getRecargas(String initDate, String finalDate) {
        //ID, RIF, Proveedor, Cantidad, Monto Total, Fecha

        String[] header = new String[]{"#", "ID", "RIF", "Proveedor", "Cantidad", "Monto Total", "Fecha"};

        int i = 1;
        int x = 4897;

        Object[][] historial = {
            header,
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"}
        };

        return historial;
    }

    /**
     * Función para obtener el registro de las ventas, en una fecha determninada
     *
     * @param initDate Fecha inicial
     * @param finalDate Fecha final
     *
     * @return Registro de las ventas
     */
    public static Object[][] getVentas(String initDate, String finalDate) {
        //ID, Cedula, Cantidad, Tipo pago, Monto Total, Fecha

        String[] header = new String[]{"#", "ID", "Cedula", "Cantidad", "Tipo Pago", "Monto Total", "Fecha"};

        int i = 1;
        int x = 1098;

        Object[][] historial = {
            header,
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"}
        };

        return historial;
    }

    /**
     * Función para obtener el registro de las compras, en una fecha
     * determninada
     *
     * @param initDate Fecha inicial
     * @param finalDate Fecha final
     *
     * @return Registro de las compras
     */
    public static Object[][] getCompras(String initDate, String finalDate) {
        //ID, RIF, Proveedor, Cantidad, Monto Total, Fecha

        String[] header = new String[]{"#", "ID", "RIF", "Proveedor", "Cantidad", "Monto Total", "Fecha"};

        int i = 1;
        int x = 1098;

        Object[][] historial = {
            header,
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"}
        };

        return historial;
    }
}
