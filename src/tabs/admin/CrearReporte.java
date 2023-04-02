package tabs.admin;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import database.ReadDB;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class CrearReporte {

    // ========== BACKEND ==========
    private static void cuerpo() {

        int columnas = datos[0].length;
        Object[][] datosSinHeader = new Object[datos.length - 1][columnas];
        int filas = datosSinHeader.length;
        System.arraycopy(datos, 1, datosSinHeader, 0, filas);

        int primerLimite = (type == REP_CLIENTES || type == REP_PROVEEDORES)? 42 : 40;
        int limiteMaximo = 50;

        Table tabla;

        if (filas <= primerLimite) {
            //Una sola hoja
            tabla = getTable(datosSinHeader);
            documento.add(tabla);

        } else {

            int filasRestantes = filas - primerLimite;
            int hojasCompletas = filasRestantes / limiteMaximo;
            int ultimasFilas = filasRestantes - hojasCompletas * limiteMaximo;
            boolean divisible = filasRestantes % limiteMaximo == 0;
            
            int cantidadHojas;
            if((type == REP_CLIENTES || type == REP_PROVEEDORES) && divisible){
                cantidadHojas = hojasCompletas + 1;
            } else{
                cantidadHojas = hojasCompletas + 2;
            }
            

            //Primera hoja
            Object[][] listaDatos = new Object[primerLimite][columnas];
            System.arraycopy(datosSinHeader, 0, listaDatos, 0, primerLimite);
            tabla = getTable(listaDatos);

            Paragraph enumeracion = new Paragraph("Pagina 1 de " + cantidadHojas);
            enumeracion.addStyle(titleStyle);
            Table footer = new Table(1).addCell(customCell(enumeracion, TextAlignment.CENTER));
            float x = documento.getLeftMargin();
            float y = documento.getBottomMargin();
            float w = defaultSize.getWidth() - x * 2;
            footer.setFixedPosition(x, y, w);

            documento.add(tabla);
            documento.add(footer);
            documento.add(new AreaBreak());

            int filaActual = primerLimite;
            int hojaActual = 2;
            for (int i = 0; i < hojasCompletas; i++) {

                //Hojas completas
                if (divisible && i == hojasCompletas - 1 && type != REP_CLIENTES && type != REP_PROVEEDORES) {
                    limiteMaximo -= 2;
                    ultimasFilas += 2;
                }

                listaDatos = new Object[limiteMaximo][columnas];
                System.arraycopy(datosSinHeader, filaActual, listaDatos, 0, limiteMaximo);
                tabla = getTable(listaDatos);

                enumeracion = new Paragraph("Pagina " + hojaActual + " de " + cantidadHojas);
                enumeracion.addStyle(titleStyle);
                footer = new Table(1).addCell(customCell(enumeracion, TextAlignment.CENTER));
                footer.setFixedPosition(x, y, w);

                documento.add(tabla);
                documento.add(footer);
                
                if(type != REP_CLIENTES && type != REP_PROVEEDORES || !divisible){
                    documento.add(new AreaBreak());
                } 

                filaActual += limiteMaximo;
                hojaActual++;
            }

            if(ultimasFilas > 0){
                //Ultima hoja
                listaDatos = new Object[ultimasFilas][columnas];
                System.arraycopy(datosSinHeader, filaActual, listaDatos, 0, ultimasFilas);
                tabla = getTable(listaDatos);

                enumeracion = new Paragraph("Pagina " + hojaActual + " de " + cantidadHojas);
                enumeracion.addStyle(titleStyle);
                footer = new Table(1).addCell(customCell(enumeracion, TextAlignment.CENTER));
                footer.setFixedPosition(x, y, w);

                documento.add(tabla);
                documento.add(footer);
            }

        }

        if (type != REP_CLIENTES && type != REP_PROVEEDORES) {
            Table total = getTotalTable();
            documento.add(total);
        }
    }

    private static Table getTable(Object[][] listaDatos) {
        int columnsCount = datos[0].length;
        Table tabla = new Table(columnsCount);
        tabla.setWidth(UnitValue.createPercentValue(100));

        //CABECERA
        Style headerStyle = new Style(titleStyle)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(0)
                .setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(azul, 1));
        for (Object nombre : datos[0]) {
            tabla.addCell(
                    new Cell().add(
                            new Paragraph(nombre.toString())
                    ).addStyle(headerStyle)
            );
        }

        //CARGAR LOS DATOS
        Style dataStyle = new Style()
                .setFont(segoe)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(0)
                .setBorder(Border.NO_BORDER);
        Cell celda;

        for (int i = 0; i < listaDatos.length; i++) {
            for (int j = 0; j < columnsCount; j++) {

                celda = new Cell().addStyle(dataStyle);
                celda.add(new Paragraph(listaDatos[i][j].toString()));

                if ((i + 1) % 2 == 0) {
                    celda.setBackgroundColor(gris);
                }

                tabla.addCell(celda);
            }
        }

        return tabla;
    }

    private static Table getTotalTable() {
        Style totalStyle = new Style()
                .setFont(segoeBlack)
                .setFontSize(14)
                .setFontColor(blanco);

        Cell total = new Cell()
                .add(new Paragraph("TOTAL").addStyle(totalStyle))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(0)
                .setBackgroundColor(azul)
                .setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(azul, 1));

        Table tabla = new Table(2);
        tabla.setWidth(UnitValue.createPercentValue(100));
        tabla.addCell(total);

        Paragraph parrafo = new Paragraph();

        switch (type) {
            case REP_RECARGAS:
            case REP_COMPRAS:
                int cantidad = 0;
                float ganancias = 0;

                for (int i = 1; i < datos.length; i++) {
                    cantidad += Float.valueOf(datos[i][4].toString());
                    ganancias += Float.valueOf(datos[i][5].toString());
                }

                String titulo = (type == REP_RECARGAS) ? "Recargados" : "Comprados";
                parrafo.add(new Text("Botellones " + titulo + "\t\t").addStyle(titleStyle));
                parrafo.add(
                        new Text(cantidad + "\t (" + ganancias + " Bs)")
                                .addStyle(titleStyle)
                                .setFontColor(rojo)
                );

                tabla.addCell(
                        customCell(parrafo, TextAlignment.CENTER)
                                .setBorderTop(new SolidBorder(azul, 1))
                );
                break;

            case REP_VENTAS:
                cantidad = 0;
                ganancias = 0;

                for (int i = 1; i < datos.length; i++) {
                    cantidad += Float.valueOf(datos[i][3].toString());
                    ganancias += Float.valueOf(datos[i][5].toString());
                }

                parrafo.add(new Text("Botellones Vendidos\t\t").addStyle(titleStyle));
                parrafo.add(
                        new Text(cantidad + "\t (" + ganancias + " Bs)")
                                .addStyle(titleStyle)
                                .setFontColor(verde)
                );

                tabla.addCell(
                        customCell(parrafo, TextAlignment.CENTER)
                                .setBorderTop(new SolidBorder(azul, 1))
                );
                break;

            case REP_TRASVASOS:
                int cantidadPagada = 0;
                int cantidadEntregada = 0;
                int gananciasPago = 0;
                int gananciasEntrega = 0;

                for (int i = 1; i < datos.length; i++) {
                    cantidadPagada += Integer.valueOf(datos[i][3].toString());
                    cantidadEntregada += Integer.valueOf(datos[i][4].toString());

                    gananciasPago += Float.valueOf(datos[i][7].toString());
                    gananciasEntrega += Float.valueOf(datos[i][8].toString());
                }

                Text pagados = new Text(cantidadPagada + "\t (" + gananciasPago + " Bs)");
                pagados.addStyle(titleStyle).setFontColor(verde);

                Text entregados = new Text(cantidadEntregada + "\t (" + gananciasEntrega + " Bs)");
                entregados.addStyle(titleStyle).setFontColor(verde);

                if (cantidadEntregada > cantidadPagada) {
                    pagados.setFontColor(rojo);
                    entregados.setFontColor(rojo);
                }

                Table tablaInterna = new Table(1);
                tablaInterna.setHorizontalAlignment(HorizontalAlignment.CENTER);

                parrafo.add(new Text("Botellones pagados\t\t").addStyle(titleStyle));
                parrafo.add(pagados);
                tablaInterna.addCell(customCell(parrafo, TextAlignment.RIGHT));

                parrafo = new Paragraph();
                parrafo.add(new Text("Botellones Entregados\t\t").addStyle(titleStyle));
                parrafo.add(entregados);
                tablaInterna.addCell(customCell(parrafo, TextAlignment.RIGHT));

                tabla.addCell(
                        customCell(tablaInterna, TextAlignment.RIGHT)
                                .setBorderTop(new SolidBorder(azul, 1))
                );
                break;
        }

        return tabla;
    }

    private static boolean getDatos() {
        switch (type) {
            case REP_TRASVASOS:
                datos = ReadDB.getTrasvasos(initialDate, finalDate);
                break;
            case REP_DEUDAS:
                datos = ReadDB.getDeudas(initialDate, finalDate);
                break;
            case REP_RECARGAS:
                datos = ReadDB.getRecargas(initialDate, finalDate);
                break;
            case REP_COMPRAS:
                datos = ReadDB.getCompras(initialDate, finalDate);
                break;
            case REP_VENTAS:
                datos = ReadDB.getVentas(initialDate, finalDate);
                break;
            case REP_CLIENTES:
                String[] campos = new String[]{"#", "Cedula", "Nombre", "Apellido", "Telefono", "Direccion"};
                Object[][] lista = ReadDB.getClientes();

                datos = new Object[lista.length + 1][campos.length];
                datos[0] = campos;

                for (int i = 0; i < lista.length; i++) {
                    datos[i + 1][0] = i+1;
                    
                    System.arraycopy(lista[i], 0, datos[i + 1], 1, lista[0].length);
                }
                break;
            case REP_PROVEEDORES:
                campos = new String[]{"#", "RIF", "Nombre", "Telefono", "Direccion"};
                lista = ReadDB.getProveedores();

                datos = new Object[lista.length + 1][campos.length];
                datos[0] = campos;

                for (int i = 0; i < lista.length; i++) {
                    datos[i + 1][0] = i+1;
                    
                    System.arraycopy(lista[i], 0, datos[i + 1], 1, lista[0].length);
                }
                break;
        }

        return datos != null;
    }

    //ATRIBUTOS BACKEND
    private static Object[][] datos;

    // ========== FRONTEND ==========
    private static void cabecera() {
        Paragraph nombre = new Paragraph(getReportName());
        nombre.setMultipliedLeading(1);
        nombre.setFontSize(24);
        nombre.setFont(segoeBlack);

        Paragraph empresa = new Paragraph("AQUATECH");
        empresa.setMultipliedLeading(1);
        empresa.setFontSize(18);
        empresa.setFontColor(azul);
        empresa.setFont(segoeBlack);

        Table tabla = new Table(2).setWidth(UnitValue.createPercentValue(100));
        tabla.addCell(customCell(nombre, TextAlignment.LEFT));
        tabla.addCell(customCell(empresa, TextAlignment.RIGHT));

        documento.add(tabla);
        documento.add(new Paragraph("\n"));
    }

    private static void informacion() {

        int columnas;
        switch (type) {
            case REP_CLIENTES:
            case REP_PROVEEDORES:
                columnas = 1;
                break;

            case REP_DEUDAS:
                columnas = 2;
                break;

            default:
                columnas = 3;
        }

        Table tabla = new Table(columnas);
        tabla.setWidth(UnitValue.createPercentValue(100));

        Paragraph proposito = getPurpose();
        tabla.addCell(customCell(proposito, TextAlignment.LEFT));

        if (type != REP_CLIENTES && type != REP_PROVEEDORES) {
            if (type != REP_DEUDAS) {
                Paragraph ganancias = getProfits();
                tabla.addCell(customCell(ganancias, TextAlignment.CENTER));
            }

            Table fechas = getDates();
            tabla.addCell(customCell(fechas, TextAlignment.RIGHT));
        }

        documento.add(tabla);
        documento.add(new Paragraph("\n"));
    }

    private static Table getDates() {
        Table tabla = new Table(2);
        tabla.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        tabla.setWidth(180);

        Text titulo = new Text("Fecha inicial\n").addStyle(titleStyle);
        Text fecha = new Text(initialDate).addStyle(plainStyle);

        Paragraph parrafo = new Paragraph();
        parrafo.add(titulo);
        parrafo.add(fecha);
        parrafo.setMultipliedLeading(1);

        tabla.addCell(customCell(parrafo, TextAlignment.CENTER));

        titulo = new Text("Fecha reporte\n").addStyle(titleStyle);
        fecha = new Text(getActualDate()).addStyle(plainStyle);

        parrafo = new Paragraph();
        parrafo.add(titulo);
        parrafo.add(fecha);
        parrafo.setMultipliedLeading(1);

        tabla.addCell(customCell(parrafo, TextAlignment.CENTER));

        titulo = new Text("Fecha final\n").addStyle(titleStyle);
        fecha = new Text(finalDate).addStyle(plainStyle);

        parrafo = new Paragraph();
        parrafo.add(titulo);
        parrafo.add(fecha);
        parrafo.setMultipliedLeading(1);

        tabla.addCell(customCell(parrafo, TextAlignment.CENTER));

        tabla.addCell(customCell(new Paragraph(""), TextAlignment.CENTER));

        return tabla;
    }

    private static Paragraph getProfits() {

        String title = (type == REP_COMPRAS || type == REP_RECARGAS) ? "Invertido" : "Ganancias";
        Text titulo = new Text(title + "\n").addStyle(titleStyle);

        Paragraph parrafo = new Paragraph().add(titulo);
        parrafo.setMultipliedLeading(1);

        switch (type) {
            case REP_RECARGAS:
            case REP_COMPRAS:
                int cantidad = 0;
                float ganancias = 0;

                for (int i = 1; i < datos.length; i++) {
                    cantidad += Float.valueOf(datos[i][4].toString());
                    ganancias += Float.valueOf(datos[i][5].toString());
                }

                parrafo.add(
                        new Text(cantidad + "\n" + ganancias + " Bs")
                                .addStyle(plainStyle)
                                .setFontColor(rojo)
                );

                return parrafo;

            case REP_VENTAS:
                cantidad = 0;
                ganancias = 0;

                for (int i = 1; i < datos.length; i++) {
                    cantidad += Float.valueOf(datos[i][3].toString());
                    ganancias += Float.valueOf(datos[i][5].toString());
                }

                parrafo.add(
                        new Text(cantidad + "\n" + ganancias + " Bs")
                                .addStyle(plainStyle)
                                .setFontColor(verde)
                );

                return parrafo;

            case REP_TRASVASOS:
                int cantidadPagada = 0;
                int cantidadEntregada = 0;
                int gananciasPago = 0;
                int gananciasEntrega = 0;

                for (int i = 1; i < datos.length; i++) {
                    cantidadPagada += Integer.valueOf(datos[i][3].toString());
                    cantidadEntregada += Integer.valueOf(datos[i][4].toString());

                    gananciasPago += Float.valueOf(datos[i][7].toString());
                    gananciasEntrega += Float.valueOf(datos[i][8].toString());
                }

                Text pagados = new Text(cantidadPagada + " (" + gananciasPago + " Bs)");
                pagados.addStyle(plainStyle).setFontColor(verde);

                Text entregados = new Text(cantidadEntregada + " (" + gananciasEntrega + " Bs)");
                entregados.addStyle(plainStyle).setFontColor(verde);

                if (cantidadEntregada > cantidadPagada) {
                    pagados.setFontColor(rojo);
                    entregados.setFontColor(rojo);
                }

                parrafo.add(new Text("Botellones pagados\n").addStyle(plainStyle).setFontColor(azul));
                parrafo.add(pagados);
                parrafo.add(new Text("\nBotellones Entregados\n").addStyle(plainStyle).setFontColor(azul));
                parrafo.add(entregados);

                return parrafo;
            default:
                return new Paragraph("");
        }
    }

    private static Paragraph getPurpose() {
        Text titulo = new Text("Proposito\n").addStyle(titleStyle);

        Text prop = new Text(getReportPrupose()).addStyle(plainStyle);

        Paragraph proposito = new Paragraph();
        proposito.setMultipliedLeading(1);

        switch (type) {
            case REP_DEUDAS:
                proposito.setWidth(200);
                break;

            case REP_TRASVASOS:
                proposito.setWidth(120);
                break;

            case REP_RECARGAS:
                proposito.setWidth(140);
                break;

            case REP_CLIENTES:
            case REP_PROVEEDORES:
                proposito.setWidth(250);
                break;

            default:
                proposito.setWidth(150);
                break;
        }

        proposito.add(titulo);
        proposito.add(prop);

        return proposito;
    }

    private static Cell customCell(IBlockElement elemento, TextAlignment alignment) {
        Cell celda = new Cell().add(elemento);
        celda.setPadding(0);
        celda.setTextAlignment(alignment);
        celda.setBorder(Border.NO_BORDER);
        return celda;
    }

    private static String getReportPrupose() {
        switch (type) {
            case REP_TRASVASOS:
                return "Reporte para visualizar el registro de los trasvasos "
                        + "realizados a los clientes, en una fecha determinada";
            case REP_DEUDAS:
                return "Reporte para visualizar el registro de las deudas "
                        + "pendientes con los clientes, en una fecha determinada";
            case REP_RECARGAS:
                return "Reporte para visualizar el registro de las recargas "
                        + "realizadas con los proveedores, en una fecha determinada";
            case REP_COMPRAS:
                return "Reporte para visualizar el registro de las compras de "
                        + "botellones realizadas con los proveedores, en una "
                        + "fecha determinada";
            case REP_VENTAS:
                return "Reporte para visualizar el registro de las ventas de "
                        + "botellones realizadas a los clientes, en una fecha"
                        + " determinada";
            case REP_CLIENTES:
                return "Reporte para visualizar a todos los clientes registrados"
                        + "en el sistema";
            case REP_PROVEEDORES:
                return "Reporte para visualizar a todos los proveedores"
                        + "registrados en el sistema";
            default:
                return "";
        }
    }

    private static String getReportName() {
        switch (type) {
            case REP_TRASVASOS:
                return "TRASVASOS";
            case REP_DEUDAS:
                return "DEUDAS";
            case REP_RECARGAS:
                return "RECARGAS";
            case REP_COMPRAS:
                return "COMPRAS";
            case REP_VENTAS:
                return "VENTAS";
            case REP_CLIENTES:
                return "CLIENTES";
            case REP_PROVEEDORES:
                return "PROVEEDORES";
            default:
                return "";
        }
    }

    private static String getActualDate() {
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

        //Fecha actual
        String fecha = year + "-" + mes + "-" + dia + "\n" + hora + ":" + minuto + ":" + segundo;

        //Retornar la fecha
        return fecha;
    }

    /**
     * Función para obtener el nombre por defecto de todos los reportes
     *
     * @return Formato: REPORTE_año_mes_dia_hora_minuto_segundo.pdf
     */
    private static String getDefaultName() {
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
        String name = "\\" + getReportName() + "_";
        //Fecha cuando se realizó el respaldo
        String fecha = year + "_" + mes + "_" + dia + "_" + hora + "_" + minuto + "_" + segundo + ".pdf";

        //Retornar el nombre con la fecha
        return name + fecha;
    }

    private static void crearPDF() {
        try {
            //PdfWriter permite crear un fichero PDF real en una ruta determinada
            pdfWriter = new PdfWriter(path);

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

            float inch = 2.54f;
            int unitPerInch = 72;
            float marginH = (1.5f / inch) * unitPerInch;
            float marginV = (1f / inch) * unitPerInch;
            documento.setMargins(marginV, marginH, marginV, marginH);

            defaultSize = pdfDoc.getDefaultPageSize();

            segoe = PdfFontFactory.createFont("src/fonts/segoeui.ttf");
            segoeBlack = PdfFontFactory.createFont("src/fonts/segoeui_black.ttf");

            titleStyle = new Style()
                    .setFont(segoeBlack)
                    .setFontColor(azul)
                    .setFontSize(10);

            plainStyle = new Style()
                    .setFont(segoe)
                    .setFontSize(10);

            cabecera();

            informacion();

            cuerpo();

            documento.close();
            pdfDoc.close();
            pdfWriter.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void crear(int type, String path) {
        CrearReporte.type = type;
        CrearReporte.path = path + getDefaultName();

        if (getDatos()) {
            crearPDF();
        }
    }

    public static void crear(int type, String path, String initialDate, String finalDate) {
        CrearReporte.type = type;
        CrearReporte.path = path + getDefaultName();
        CrearReporte.initialDate = initialDate;
        CrearReporte.finalDate = finalDate;

        if (getDatos()) {
            crearPDF();
        }
    }

    //ATRIBUTOS FRONTEND
    private static int type;
    private static String path;
    private static String initialDate;
    private static String finalDate;
    private static PageSize defaultSize;
    private static PdfDocument pdfDoc;
    private static PdfWriter pdfWriter;
    private static Document documento;
    private static PdfFont segoe;
    private static PdfFont segoeBlack;
    private static Style titleStyle;
    private static Style plainStyle;
    private static final DeviceRgb azul = new DeviceRgb(22, 17, 186);
    private static final DeviceRgb verde = new DeviceRgb(0, 175, 45);
    private static final DeviceRgb rojo = new DeviceRgb(155, 0, 0);
    private static final DeviceRgb blanco = new DeviceRgb(255, 255, 255);
    private static final DeviceRgb gris = new DeviceRgb(238, 238, 238);
    //TIPOS DE REPORTES
    private static final int REP_TRASVASOS = 1;
    private static final int REP_DEUDAS = 2;
    private static final int REP_RECARGAS = 3;
    private static final int REP_COMPRAS = 4;
    private static final int REP_VENTAS = 5;
    private static final int REP_CLIENTES = 6;
    private static final int REP_PROVEEDORES = 7;
}
