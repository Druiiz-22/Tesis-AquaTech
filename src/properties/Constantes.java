package properties;

public interface Constantes {

    //CONSTANTES PROPIOS
    /**
     * Constante para representar el panel de Inicio en el login y en el menú.
     */
    static final int INICIO = 0;
    /**
     * Constante para representar el panel de registro en el login.
     */
    static final int REGISTRO = 1;
    /**
     * Constante para representar el panel de los datos personales, dentro del
     * registro en el login.
     */
    static final int DATOS = 2;
    /**
     * Constante para representar el panel del correo electrónico, dentro del
     * registro y recuperación de contraseña en el login.
     *
     * <br><br>
     *
     * Además, para designar los campos de textos que solo permiten el ingreso
     * de correos electrónicos, admitiendo letras, números, puntos, guiones,
     * guiones bajos y arrobas.
     */
    static final int CORREO = 3;
    /**
     * Constante para representar el panel de confirmación del código de
     * seguridad, dentro del registro y recuperación de contraseña en el login.
     */
    static final int CODIGO = 4;
    /**
     * Constante para representar el panel de nueva contraseña, dentro del
     * registro y recuperación de contraseña en el login.
     */
    static final int CLAVE = 5;
    /**
     * Constante para representar el panel de recuperación de contraseña en el
     * login.
     */
    static final int RECUPERACION = 6;
    /**
     * Constante para representar la pestaña de los clientes registrados en el
     * sistema, además de designar el tipo de botón de clientes en el menú
     * lateral.
     */
    static final int CLIENTES = 7;
    /**
     * Constante para representar la pestaña en donde se realizan los trasvases,
     * ventas y pedidos a domicilio, además de designar el tipo de botón de las
     * ventas en el menú lateral.
     */
    static final int VENTAS = 8;
    /**
     * Constante para representar la pestaña en donde se realizan los trasvases
     * dentro de las ventas.
     */
    static final int VENTAS_TRASVASO = 9;
    /**
     * Constante para representar la pestaña en donde se realizan las ventas de
     * botellones dentro de las ventas.
     */
    static final int VENTAS_BOTELLON = 10;
    /**
     * Constante para representar la pestaña en donde se realizan los pedidos a
     * domicilio dentro de las ventas.
     */
    static final int VENTAS_PEDIDOS = 11;
    /**
     * Constante para representar la pestaña en donde se realizan las recargas y
     * compras, además de designar el tipo de botón de las compras en el menú
     * lateral.
     */
    static final int COMPRAS = 12;
    /**
     * Constante para representar la pestaña en donde se realizan los las
     * recargas dentro de las compras.
     */
    static final int COMPRAS_RECARGA = 13;
    /**
     * Constante para representar la pestaña en donde se realizan los las
     * compras de botellones dentro de las compras.
     */
    static final int COMPRAS_BOTELLON = 14;
    /**
     * Constante para representar la pestaña del historial, además de designar
     * el tipo de botón del historial en el menú lateral.
     */
    static final int HISTORIAL = 15;
    /**
     * Constante para representar la pestaña en donde se visualizará el
     * historial de los trasvasos realizados.
     */
    static final int HISTORIAL_TRASVASO = 16;
    /**
     * Constante para representar la pestaña en donde se visualizará el
     * historial de las recargas realizadas.
     */
    static final int HISTORIAL_RECARGA = 17;
    /**
     * Constante para representar la pestaña en donde se visualizará el
     * historial de las ventas de botellones realizadas.
     */
    static final int HISTORIAL_VENTA = 18;
    /**
     * Constante para representar la pestaña en donde se visualizará el
     * historial de los trasvasos realizados.
     */
    static final int HISTORIAL_COMPRA = 19;
    /**
     * Constante para representar la pestaña de los proveedores registrados en
     * el sistema, además de designar el tipo de botón de proveedores en el menú
     * lateral.
     */
    static final int PROVEEDOR = 20;
    /**
     * Constante para representar el botón del manual de usuario en el menú
     * lateral.
     */
    static final int MANUAL = 21;
    /**
     * Constante para representar la pestaña para administradores del sistema,
     * además de designar el tipo de botón de admin en el menú lateral.
     */
    static final int ADMIN = 22;
    /**
     * Constante para representar la pestaña de ajustes de administrador.
     */
    static final int ADMIN_AJUSTES = 23;
    /**
     * Constante para representar la pestaña para la generación de reportes.
     */
    static final int ADMIN_REPORTES = 24;
    /**
     * Constante para representar la pestaña de los usuarios registrados en el
     * sistema.
     */
    static final int ADMIN_USUARIOS = 25;
    /**
     * Constante para representar la pestaña para realizar respaldo de la base
     * de datos.
     */
    static final int ADMIN_RESPALDO = 26;
    /**
     * Constante para designar los <b>campos de texto que sólo permiten el
     * ingreso de nombres</b>, admitiendo los carácteres de letras en
     * mayúsculas, minúsculas y acentos.
     */
    public static final int NOMBRE = 27;
    /**
     * Constante para designar los <b>campos de texto que sólo permiten el
     * ingreso de números</b>, admitiendo únicamente dígitos
     */
    public static final int NUMERO = 28;
    /**
     * Constante para designar los <b>campos de texto que sólo permiten el
     * ingreso de números decimales</b>, admitiendo únicamente dígitos y puntos
     */
    public static final int DECIMAL = 29;
    /**
     * Constante para designar los <b>campos de texto que sólo permiten el
     * ingreso de fechas</b>, admitiendo únicamente dígitos y guiones.
     */
    public static final int FECHA = 30;
    /**
     * Constante para designar los <b>labels de tipo normal</b>, son aquellos
     * labels que cuentan con la fuente predeterminada del software.
     */
    public static final int PLANO = 31;
    /**
     * Constante para designar los <b>labels de tipo negrita</b>, son aquellos
     * labels que cuentan con la fuente predeterminada del software, pero en su
     * versión negrita (gruesa).
     */
    public static final int GRUESA = 32;
    /**
     * Constante para designar los <b>labels para los títulos</b>, son aquellos
     * labels que cuentan con la fuente predeterminada del software, pero en su
     * versión SemiBold (semi negrita), es usado para los títulos dentro del
     * software.
     */
    public static final int TITULO = 33;
    /**
     * Constante para designar los <b>labels con hipervínculos</b>, son aquellos
     * labels con la capacidad de ser presionados y navegar por el software.
     * Cuentan con la fuente predetermianda del software, pero de color celeste
     * para resaltar su enlace.
     */
    public static final int LINK = 34;
    /**
     * Constante para designar el <b>label para las páginas</b>, es aquél que se
     * utiliza para resaltar (en el menú superior) la pestaña que se encuentra
     * abierta y visualizada. Cuenta con la fuente predeterminada del software,
     * pero de color blanco.
     */
    public static final int NORMAL_BLANCO = 35;
    /**
     * Constante para designar el <b>logo del menú superior</b> del software.
     */
    public static final int LOGO_MENU = 36;
    /**
     * Constante para representar el apartado de las deudas, dentro del panel de
     * los clientes registrados en el sistema
     */
    public static final int DEUDAS = 37;
    /**
     * Constante para determinar que el botón presionado va a dirigirse hacia el
     * sitio web de la empresa
     */
    public static final int WEB = 38;
    /**
     * Constante para determinar el botón de selección de clientes, en el
     * apartado de ventas y trasvasos de botellones
     */
    public static final int SELECT_CLIENTE = 39;
    /**
     * Constante para determinar el botón de selección de proveedores, en el
     * apartado de compra y recarga de botellones
     */
    public static final int ASIGNAR_PROV = 40;
    /**
     * Constante para determinar qué el <b>ancho del panel de información</b>,
     * siendo el tamaño mediano cuando éste esté dentro de un contenedor menor a
     * 600 px o cuando el panel mida 300 px de ancho
     */
    public static final int PANEL_MEDIANO = 41;
    /**
     * Constante para determinar qué el <b>ancho del panel de información</b>,
     * siendo el tamaño grande cuando éste esté dentro de un contenedor mayor a
     * 600 px y menor a 900 px
     */
    public static final int PANEL_GRANDE = 42;
    /**
     * Constante para determinar que un campo de texto admite cualquier tipo de
     * carácter de entrada
     */
    public static final int CUALQUIER = 43;
    
    public static final int ADMIN_EMPLEADOS = 44;
    public static final int TODAS_SUCURSALES = -97;
    
    
    /**
     * Constante para determinar cuando una busqueda de un número en la base de 
     * datos, pueda ser erronea y devolver un valor que represente este error
     */
    public static final int ERROR_VALUE = -99;
    
    public static final int DUPLICATE_ERROR = -98;
    
    /**
     * Constante con el valor del rol de cliente para el usuario
     */
    public static final int CLIENTE = 0;
    /**
     * Constante con el valor del rol de empleado para el usuario
     */
    public static final int EMPLEADO = 1;
    /**
     * Constante con el valor del rol de administrador para el usuario
     */
    public static final int OPERADOR = 2;
    /**
     * Constante con el valor del rol de administrador para el usuario
     */
    public static final int ADMINISTRADOR = 3;

    //CONSTANTES EXTRAÍDOS
    /**
     * Escalar una imagen de forma suave, extraído de java.awt.Image
     */
    public static final int ESCALA_SUAVE = 4;
    /**
     * Valor de la tecla enter, extraído de la clase java.awt.event.KeyEvent
     */
    public static final int TECLA_ENTER = '\n';
    /**
     * Valor de la tecla suprimir/delete, extraído de la clase
     * java.awt.event.KeyEvent
     */
    public static final int TECLA_SUPR = 0x7F;

}
