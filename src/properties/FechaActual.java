package properties;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.AM_PM;
import static java.util.Calendar.AM;

/**
 * <p>
 * Clase para las funciones que permiten obtener la fecha y hora actual (en el
 * momento que sean llamadas las funciones).
 * </p>
 * <h2>Se utiliza únicamente para la generación de reportes.</h2>
 */
public class FechaActual {

    /**
     * Función para obtener la fecha actual.
     *
     * @return String de la fecha actual.
     */
    public static String getDiaActual() {
        //Obtener la fecha actual con su formato
        Date fecha = new Date();
        Calendar calendario = new GregorianCalendar();
        calendario.setTime(fecha);

        //Obtener el año, mes, día del mes y día de la semana actual
        int anio = calendario.get(YEAR);
        int mes = calendario.get(MONTH);
        int diaMes = calendario.get(DAY_OF_MONTH);
        int diaSemana = calendario.get(DAY_OF_WEEK);

        //Guardar la fecha en un String para ser retornado con el formato:
        //XXXXX, 99 de XXXXXX del 9999
        String diaActual = getDia(diaSemana) + ", " + diaMes + " de " + getMes(mes) + " del " + anio;

        //Retornar el día actual
        return diaActual;
    }

    /**
     * Función para obtener la hora actual.
     *
     * @return String de la hora actual.
     */
    public static String getHoraActual() {

        //Obtener la fecha actual con su formato
        Date fecha = new Date();
        Calendar calendario = new GregorianCalendar();
        calendario.setTime(fecha);

        //Obtener la hora, minuto y segnudo actual
        int hora = calendario.get(HOUR);
        int minuto = calendario.get(MINUTE);
        int segundo = calendario.get(SECOND);

        //Validar si la hora es AM o PM
        String am_pm = (calendario.get(AM_PM) == AM) ? "AM" : "PM";

        //Validar que el minuto actual contenga 1 dígito, en caso de tenerlo, se le
        //agregará un cero a la izquierda (ej: '09'). En caso contrario, solo
        //convertir el minuto actual en String.
        String minutos = (minuto < 10) ? ("0" + String.valueOf(minuto)) : String.valueOf(minuto);

        //Validar que el segundo actual contenga 1 dígito, en caso de tenerlo, se le
        //agregará un cero a la izquierda (ej: '05'). En caso contrario, solo
        //convertir el segundo actual en String.
        String seconds = (segundo < 10) ? ("0" + String.valueOf(segundo)) : String.valueOf(segundo);

        //Guardar la hora actual en un String para ser retornado con el formato:
        // 99:99:99 XX
        String horaActual = "Hora " + hora + ":" + minutos + ":" + seconds + " " + am_pm;

        //Retornar la hora actual.
        return horaActual;
    }

    /**
     * Función para obtener el nombre del mes actual.
     *
     * @param month Número del mes actual.
     * @return
     */
    private static String getMes(int month) {
        switch (month) {
            case Calendar.JANUARY:
                return "Enero";
            case Calendar.FEBRUARY:
                return "Febrero";
            case Calendar.MARCH:
                return "Marzo";
            case Calendar.APRIL:
                return "Abril";
            case Calendar.MAY:
                return "Mayo";
            case Calendar.JUNE:
                return "Junio";
            case Calendar.JULY:
                return "Julio";
            case Calendar.AUGUST:
                return "Agosto";
            case Calendar.SEPTEMBER:
                return "Septiembre";
            case Calendar.OCTOBER:
                return "Octubre";
            case Calendar.NOVEMBER:
                return "Noviembre";
            case Calendar.DECEMBER:
                return "Diciembre";
            default:
                return "ERROR";
        }
    }

    /**
     * Función para obtener el nombre del día de la semana actual.
     *
     * @param day Número del día del mes
     * @return
     */
    private static String getDia(int day) {
        switch (day) {
            case Calendar.MONDAY:
                return "Lunes";
            case Calendar.TUESDAY:
                return "Martes";
            case Calendar.WEDNESDAY:
                return "Miercoles";
            case Calendar.THURSDAY:
                return "Jueves";
            case Calendar.FRIDAY:
                return "Viernes";
            case Calendar.SATURDAY:
                return "Sabado";
            case Calendar.SUNDAY:
                return "Domingo";
            default:
                return "ERROR";
        }
    }
}
