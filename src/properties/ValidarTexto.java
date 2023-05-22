package properties;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <p>
 * Clase para las funciones que permiten validar textos, según la condición
 * deseada, mediante el uso de expresiones regulares y funciones de string.</p>
 */
public class ValidarTexto {

    //VALIDAR POR CARÁCTER PRESIONADO
    /**
     * <p>
     * Función para validar que un <b>carácter</b> cumpla con los siguientes
     * requisitos:</p>
     * <ul>
     * <li>Debe ser 1 letra (mayuscula o minuscula).</li>
     * <li>Puede ser un backspace (borrar).</li>
     * <li>Puede ser un espacio.</li>
     * </ul>
     * <p>
     * Esta función se utiliza para hacer que un campo de texto SOLO permita el
     * ingreso de letras. Para realizar esto, se comprueba que la tecla
     * presionada sea una letra, un espacio o la tecla de borrar; en caso
     * contrario, se está presionando un carácter inválido para el campo y la
     * función retornará FALSE.
     * </p>
     *
     * @param key Carácter que será validado.
     * @return <b>TRUE</b> en caso de que cumpla con los requisitos.
     */
    public static boolean esUnaLetra(char key) {
        return Character.isLetter(key) || key == TECLA_BORRAR || key == TECLA_ESPACIO || key == TECLA_SUPRIMIR;
    }

    /**
     * <p>
     * Función para validar que un <b>carácter</b> cumpla con los siguientes
     * requisitos:</p>
     * <ul>
     * <li>Debe ser un dígito.</li>
     * <li>Puede ser un backspace.</li>
     * </ul>
     * <p>
     * Esta función se utiliza para hacer que un campo de texto SOLO permita el
     * ingreso de dígitos. Para realizar esto, se comprueba que la tecla
     * presionada sea un dígito o la tecla de borrar; en caso contrario, se está
     * presionando un carácter inválido para el campo y la función retornará
     * FALSE.
     * </p>
     *
     * @param key Carácter que será validado
     * @return <b>TRUE</b> en caso de que cumpla con los requisitos.
     */
    public static boolean esUnNumero(char key) {
        return Character.isDigit(key) || key == TECLA_BORRAR || key == TECLA_SUPRIMIR;
    }

    /**
     * <p>
     * Función para validar que un <b>carácter</b> cumpla con los siguientes
     * requisitos:</p>
     * <ul>
     * <li>Dígito.</li>
     * <li>Punto.</li>
     * <li>Tecla de borrar.</li>
     * <li>NO tener más de 1 punto.</li>
     * </ul>
     * <p>
     * Esta función se utiliza para hacer que un campo de texto SOLO permita el
     * ingreso de números decimales. Para realizar esto, se comprueba que la
     * tecla presionada sea un dígito, un punto o la tecla de borrar; en caso
     * contrario, se está presionando un carácter inválido para el campo y la
     * función retornará FALSE.
     * </p>
     *
     * @param number número completo dentro del campo.
     * @param key tecla presionada.
     * @return <b>TRUE</b> en caso de que cumpla con los requisitos.
     */
    public static boolean esUnDecimal(String number, char key) {
        //Validar que la tecla presionada esté dentro del rango
        if (Character.isDigit(key) || key == TECLA_PUNTO || key == TECLA_BORRAR || key == TECLA_SUPRIMIR) {

            //Validar que el número contenga 1 punto y no se pueda agregar más
            if (number.contains(".")) {

                //Si contiene un punto, NO se debe permitir ingresar otro
                return key != TECLA_PUNTO;

            } else {
                //Si no tiene un punto, se puede presionar cualquier tecla
                //(dentro del rango correcto)
                return true;
            }

        } else {
            //Si no está dentro del rango, no se puede escribir
            return false;
        }

    }

    /**
     * <p>
     * Función para validar que un <b>carácter</b> cumpla con los siguientes
     * requisitos:</p>
     * <ul>
     * <li>Debe ser un dígito.</li>
     * <li>Debe ser un guión ('-').</li>
     * <li>Puede ser un backspace.</li>
     * </ul>
     * <p>
     * Esta función se utiliza para hacer que un campo de texto SOLO permita el
     * ingreso de fechas. Para realizar esto, se comprueba que la tecla
     * presionada sea un dígito, guión o la tecla de borrar; en caso contrario,
     * se está presionando un carácter inválido para el campo y la función
     * retornará FALSE.
     * </p>
     *
     * @param key Carácter que será validado
     * @return <b>TRUE</b> en caso de que cumpla con los requisitos.
     */
    public static boolean esUnaFecha(char key) {
        return Character.isDigit(key) || key == TECLA_BORRAR || key == TECLA_GUION || key == TECLA_SUPRIMIR;
    }

    /**
     * <p>
     * Función para validar que un <b>carácter</b> cumpla con los siguientes
     * requisitos:</p>
     * <ul>
     * <li>Puede ser una letra o dígito.</li>
     * <li>Puede ser un guión, guión bajo, punto o arroba.</li>
     * </ul>
     * <p>
     * Esta función se utiliza para hacer que un campo de texto permita el
     * ingreso de correos. Para realizar esto, se comprueba que la tecla
     * presionada sea un carácter mencionado anteriormente; en caso contrario,
     * se está presionando un carácter inválido para el campo y la función
     * retornará FALSE.
     * </p>
     *
     * @param key Carácter que será validado
     * @return <b>TRUE</b> en caso de que cumpla con los requisitos.
     */
    public static boolean esUnCorreo(char key) {
        return key == TECLA_BORRAR
                || key == TECLA_GUION
                || key == TECLA_PUNTO
                || key == TECLA_SUPRIMIR
                || key == '_'
                || key == '@'
                || Character.isLetterOrDigit(key);
    }

    //VALIDAR TEXTOS SEGÚN UN FORMATO
    /**
     * Función para validar la cronologia entre dos fechas, para ello, se valida
     * que la fecha final NO sea menor que la fecha inicial.
     *
     * @param inicio fecha inicial
     * @param fin fecha final
     * @return <b>TRUE</b> en caso de que la fecha final sea igual o mayor a la
     * fecha inicial.<br>
     * <b>FALSE</b> en el caso de que la fecha final sea menor a la fecha de
     * inicio.
     */
    public static boolean cronologia(String inicio, String fin) {        
        return fin.compareTo(inicio) >= 0;
    }

    /**
     * <p>
     * Función para validar que una fecha esté dentro del rango correcto</p>
     * <ul>
     * <li>Mayor o igual que 01-01-1900.</li>
     * <li>Menor que 01-01-2100.</li>
     * </ul>
     *
     * @param fecha Fecha que será validada
     * @return TRUE si la fecha está dentro del rango correcto
     */
    public static boolean rangoFecha(String fecha) {
        try {
            //Formato de las fechas
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            //Hacer la conversión de String a fecha, de forma estricta.
            formatoFecha.setLenient(false);

            //Intentar convertir el String a fecha
            java.util.Date date = formatoFecha.parse(fecha);
            
            //Rango de fechas mínima y máxima
            java.util.Date min = formatoFecha.parse("1900-01-01");
            java.util.Date max = formatoFecha.parse("2100-01-01");

            //Retornar el rango de la fecha
            return date.compareTo(min) >= 0 && date.compareTo(max) < 0;

        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * <p>
     * Función para validar que un <b>número</b> cumpla con el formato:
     * </p>
     * <ul>
     * <li>Contener de 1 o varios números enteros.</li>
     * <li>Puede contener, o no, 1 punto.</li>
     * <li>Puede contener de 1 a 2 números seguidos de un punto.</li>
     * </ul>
     * <p>
     * Esta función se utiliza, en los campos de textos números, para validar
     * que el valor ingresado sea un <b>número racional (entero o decimal)</b>.
     * Para ello, se comprueba el valor con expresiones regulares con un formato
     * establecido.
     * </p>
     *
     * @param number número que será validado.
     * @return <b>TRUE</b> en caso de cumplir con el formato.
     */
    public static boolean formatoNumerico(String number) {
        return number.matches("^[0-9]+([\\.][0-9]{1,2})?$");
    }

    /**
     * Función para validar que un <b>texto</b> cumpla con el formato de un
     * nombre:
     * <ul>
     * <li>Solo letras.</li>
     * <li>Todas las letras en mayúscula.</li>
     * <li>Se permiten acentos y comillas simples</li>
     * <li>Máximo 25 letras.</li>
     * </ul>
     *
     * @param name texto que será validado.
     * @return <b>TRUE</b> en caso de cumplir con el formato.
     */
    public static boolean formatoNombre(String name) {
        return name.matches("^[A-ZÀ-Ü']{2,25}$");
    }

    /**
     * Función para validar que un texto cumpla con el formato de un nombre de
     * empresa:
     * <ul>
     * <li>Solo letras.</li>
     * <li>Todas las letras en mayúscula.</li>
     * <li>Se permiten acentos y comillas simples</li>
     * </ul>
     *
     * @param name
     * @return
     */
    public static boolean formatoNombreEmpresa(String name) {
        return name.matches("^[A-Z À-Ü]+$");
    }

    /**
     * Función para validar que un <b>texto</b> cumpla con el formato básico de
     * un correo:
     * <ul>
     * <li>Debe comenzar con uno o más letras, números, guión, guión bajo o
     * puntos.</li>
     * <li>Debe contener un arroba.</li>
     * <li>Seguido del arroba, debe contener uno o más letras, números, guión o
     * guión bajo.</li>
     * <li>Debe contener de uno a dos puntos.</li>
     * <li>Después de cada punto, debe contener entre 2 a 5 letras.</li>
     * </ul>
     *
     * @param mail Correo que será validado.
     * @return <b>TRUE</b> en caso de cumplir con el formato básico de un
     * correo.
     */
    public static boolean formatoCorreo(String mail) {
        return mail.matches("^[\\w-.]+@[\\w-]+(\\.[a-zA-Z]{2,5}){1,2}$");
    }

    /**
     * Función para validar una <b>fecha</b> según el siguiente formato:
     * <ul>
     * <li>El año debe ser mayor a cero.</li>
     * <li>El mes debe estar entre 1-12.</li>
     * <li>
     * Dependiendo del mes que sea, el día debe estar dentro de los límites
     * válidos. Los meses que tienen 31 días son 1, 3, 5, 7, 8, 10 y 12. Los
     * meses de 30 días son 4, 6, 9 y 11. El mes de 28 días es 2, excepto en un
     * año bisiesto, que es de 29 días.
     * </li>
     * </ul>
     *
     * @param fecha Fecha que será validada
     * @return <b>TRUE</b> en caso de que la fecha cumpla con el formato
     * correcto.
     */
    public static boolean formatoFecha(String fecha) {
        try {
            //Formato de las fechas
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            //Hacer la conversión de String a fecha, de forma estricta.
            formatoFecha.setLenient(false);

            //Intentar convertir el String a Date
            formatoFecha.parse(fecha);

            return true;

        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Función para validar un <b>número</b> según el siguiente formato:
     * <ul>
     * <li>Debe empezar con 0261 o 261.</li>
     * <li>Debe empezar con 04 o 4, seguidamente de un 12, 14, 16, 24 o 26.</li>
     * <li>Después de cuaquiera de lo anterior, debe seguir con 7 números
     * cualquiera.</li>
     * </ul>
     *
     * @param telefono Número de teléfono que será validado
     * @return <b>TRUE</b> en caso de que el número cumpla con el formato de un
     * teléfono correctamente.
     */
    public static boolean formatoTelefono(String telefono) {
        String regex = "^((04|4){1}(12|14|16|24|26){1}|(0261|261){1})[0-9]{7}?";
        return telefono.matches(regex);
    }

    /**
     * Función para validar un RIF según el siguiente formato:
     *
     * @param rif
     * @return
     */
    public static boolean formatoRIF(String rif) {
        String regex = "";
        return true;

    }

    /**
     * Función para comprobar que el texto ingresado es válido para ser
     * convertido en un número entero menor a 8.388.606. Esto se realiza dentro
     * de los campos de venta, compra, recarga y trasvasos,
     * <b>cuando una tecla sea suelta</b>.
     *
     * @param txt Texto del campo
     * @return Texto convertido en entero
     */
    public static int teclaSuelta(String txt) {
        //Validar que el texto NO sea null y sea menor de 8 dígitos
        if (!txt.isEmpty() && txt.length() <= 7) {
            try {

                //Intentar convertir el campo en entero
                int cantidad = Integer.valueOf(txt);

                //Validar que el número esté dentro del rango correcto
                if (cantidad <= 8388606) {
                    return cantidad;

                } else {
                    return 0;
                }

            } catch (NumberFormatException ex) {
                return 0;
            }

        } else {
            return 0;
        }
    }

    /**
     * Función para comprobar que el texto ingresado es válido para ser
     * convertido en un número doble menor a 10.000.000.000. Esto se realiza
     * dentro de los campos de venta, compra, recarga y trasvasos,
     * <b>cuando una tecla sea suelta</b>.
     *
     * @param txt Texto del campo
     * @return Texto convertido en entero
     */
    public static double teclaSueltaDoble(String txt) {
        //Validar que el texto NO sea null
        if (!txt.isEmpty()) {
            try {

                //Intentar convertir el campo en entero
                double cantidad = Double.valueOf(txt);

                //Obtener el número límite
                double limit = Double.valueOf("10000000000");

                //Validar que el número esté dentro del rango correcto
                if (cantidad <= limit) {
                    return cantidad;

                } else {
                    return 0;
                }

            } catch (NumberFormatException ex) {
                return 0;
            }

        } else {
            return 0;
        }
    }

    //CONSTANTES EXTRAÍDOS
    /**
     * Valor de la tecla borrar, extraído de la clase java.awt.event.KeyEvent
     */
    private static final int TECLA_BORRAR = '\b';
    /**
     * Valor de la tecla del guión, extraído de la clase java.awt.event.KeyEvent
     */
    private static final int TECLA_GUION = 0x2D;
    /**
     * Valor de la tecla del espacio, extraído de la clase
     * java.awt.event.KeyEvent
     */
    private static final int TECLA_ESPACIO = 0x20;
    /**
     * Valor de la tecla del punto, extraído de la clase java.awt.event.KeyEvent
     */
    private static final int TECLA_PUNTO = 0x2E;

    private static final int TECLA_SUPRIMIR = 0x7F;
}
