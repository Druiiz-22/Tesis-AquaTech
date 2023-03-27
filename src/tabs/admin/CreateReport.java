package tabs.admin;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author diego
 */
public class CreateReport {
    
    
    private static void cabecera(){
        
    }
    private static void informacion(){
        
    }
    private static void cuerpo(){
        
    }
    
    /**
     * Función para obtener el nombre por defecto de todos los reportes
     * 
     * @return Formato: REPORTE_año_mes_dia_hora_minuto_segundo.pdf
     */
    private static String getDefaultName(){
        //Objeto tipo date para obtener la fecha actual
        Date date = new java.util.Date();
        //Objeto de tipo calendario para obtener los datos de la fehca
        Calendar calendario = new java.util.GregorianCalendar();
        //Asignar la fecha actual
        calendario.setTime(date);

        //Dividir la información de la fecha
        int year = calendario.get(java.util.Calendar.YEAR);
        int month = calendario.get(java.util.Calendar.MONTH);
        int day = calendario.get(java.util.Calendar.DAY_OF_MONTH);
        int hour = calendario.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendario.get(java.util.Calendar.MINUTE);
        int second = calendario.get(java.util.Calendar.SECOND);

        //Validar que los datos sean de uno o dos dígitos.
        //En caso de ser de un dígito, se le coloca un cero antes del 
        //valor. Ex: 9 -> "09"
        String mes = (month < 10) ? ("0" + month) : String.valueOf(month);
        String dia = (day < 10) ? ("0" + day) : String.valueOf(day);
        String hora = (hour < 10) ? ("0" + hour) : String.valueOf(hour);
        String minuto = (minute < 10) ? ("0" + minute) : String.valueOf(minute);
        String segundo = (second < 10) ? ("0" + second) : String.valueOf(second);

        //Nombre predeterminado de las base de datos
        String name = "\\REPORTE_";
        //Fecha cuando se realizó el respaldo
        String fecha = year + "_" + mes + "_" + dia + "_" + hora + "_" + minuto + "_" + segundo + ".pdf";

        //Retornar el nombre con la fecha
        return name + fecha;
    }
    
    /**
     * Función para la creación de reportes
     * @param type
     * @param path
     * @param initialDate
     * @param finalDate 
     */
    protected static void crearReporte(int type, String path, String initialDate, String finalDate){
        CreateReport.type = type;
        CreateReport.path = path + getDefaultName();
        CreateReport.initialDate = initialDate;
        CreateReport.finalDate = finalDate;
        
        try {
            //(La documentación no sirve)
            
            //PdfWriter permite crear un fichero PDF real en una ruta determinada
            pdfWriter = new PdfWriter(CreateReport.path);
            
            //PdfDocument representa un documento PDF para iText, este no
            //representa un PDF real terminado, sino que es el medio de iText
            //para crear un documento dinámico que será convertido a PDF
            pdfDoc = new PdfDocument(pdfWriter);
            //Añadir la primera página en blanco
            pdfDoc.addNewPage();
            
            //El Document es el elemento principal para la creación del documento,
            //ya que es en este donde se puede establecer las propiedades del PDF,
            //como el tamaño de hojas, rotación, elementos, etc. Este no contiene
            //los conceptos y sintaxis de un PDF real, solo es el lugar donde 
            //se crea el contenido de un documento que será convertido a PDF.
            documento = new Document(pdfDoc);

            cabecera();
            informacion();
            cuerpo();
            
            documento.close();
            pdfDoc.close();
            pdfWriter.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //ATRIBUTOS
    private static PdfDocument pdfDoc;
    private static PdfWriter pdfWriter;
    private static Document documento;
    private static int type;
    private static String path;
    private static String initialDate;
    private static String finalDate;
    //TIPOS DE REPORTES
    private static final int REP_TRASVASOS = 1;
    private static final int REP_DEUDAS = 2;
    private static final int REP_RECARGAS = 3;
    private static final int REP_COMPRAS = 4;
    private static final int REP_VENTAS = 5;
    private static final int REP_CLIENTES = 6;
    private static final int REP_PROVEEDORES = 7;
}
