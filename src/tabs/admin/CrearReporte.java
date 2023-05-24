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
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import properties.Constantes;
import properties.Mensaje;

public class CrearReporte {

    // ========== BACKEND ==========
    /**
     * Función para la creación del cuerpo del documento que contiene la tabla
     * con los datos y el total, según el tipo de reporte
     */
    private static void cuerpo() {

        //Número de columnas según la cabecera
        int columnas = datos[0].length;
        //Lista de datos sin la cabecera
        Object[][] datosSinHeader = new Object[datos.length - 1][columnas];
        //Cantidad de registros en la tabla
        int filas = datosSinHeader.length;
        //Copiar los registros en la nueva lista sin la cabecera
        System.arraycopy(datos, 1, datosSinHeader, 0, filas);

        //Asignar el primer límite de registros, según el tipo de reporte
        int primerLimite = (type == REP_CLIENTES || type == REP_PROVEEDORES) ? 42 : 40;
        //Asignar el límite para la segunda hoja en adelante
        int limiteMaximo = 50;

        //Declaración de la tabla de los datos
        Table tabla;

        //Validar si la cantidad de registros NO supera el primer límite
        if (filas <= primerLimite) {
            //Crear la tabla con los datos
            tabla = getTable(datosSinHeader);
            //Añadir la única tabla al documento
            documento.add(tabla);

        } else {
            //Si supera el límite, entonces el documento tendrá de dos a más hojas
            //Calcular la cantidad de registros DESPUÉS de la primera hoja
            int filasRestantes = filas - primerLimite;
            //Cantidad de hojas con 50 registros (límite máximo)
            int hojasCompletas = filasRestantes / limiteMaximo;
            //Comprobar que la última hoja terminó con 50 datos. En ese caso, 
            //la división entre las hojas restantes y el límite máximo, sería
            //divisible
            boolean divisible = filasRestantes % limiteMaximo == 0;
            //Cantidad de registros en la última hoja (puede ser 0)
            int ultimasFilas = filasRestantes - hojasCompletas * limiteMaximo;

            //Variable para contar la cantidad de hojas en total
            int cantidadHojas;

            if ((type == REP_CLIENTES || type == REP_PROVEEDORES) && divisible) {
                //Si el reporte es de clientes o proveedores Y es divisible,
                //implica que la cantidad de hojas será la primera hoja MÁS la
                //cantidad de hojas completas
                cantidadHojas = hojasCompletas + 1;
            } else {

                /**
                 * Si el reporte es de otro tipo, la cantidad de hojas será la
                 * primera hoja, más las hojas completas, más la última hoja.
                 * Aunque la cantidad de hojas completas PUEDA SER divisible, la
                 * última NUNCA llegaría a los 50 datos; sino que se partiría la
                 * tabla en el dato 48, para mostrar el total en una hoja
                 * siguiente con los dos últimos datos, para NO sobrepasar el
                 * margen del documento con el total.
                 */
                cantidadHojas = hojasCompletas + 2;
            }

            //PRIMERA HOJA
            //Lista de los datos de la primera hoja
            Object[][] listaDatos = new Object[primerLimite][columnas];
            //Copiar los primeros datos de la tabla
            System.arraycopy(datosSinHeader, 0, listaDatos, 0, primerLimite);
            //Obtener la tabla para la primera hoja
            tabla = getTable(listaDatos);

            //Parrafo para la enumeración de las páginas
            Paragraph enumeracion = new Paragraph("Pagina 1 de " + cantidadHojas);
            //Asignar el estílo de los títulos
            enumeracion.addStyle(titleStyle);
            //Tabla para contener la enumeración centrada y al final del documento
            Table footer = new Table(1).addCell(customCell(enumeracion, TextAlignment.CENTER));
            //Posiciones absolutas para la tabla
            float x = documento.getLeftMargin();
            float y = documento.getBottomMargin();
            //Ancho de la tabla, restando los márgenes
            float w = defaultSize.getWidth() - x * 2;
            //Asignar la posición absoluta al final del documento
            footer.setFixedPosition(x, y, w);

            //Añadir la primera tabla a la hoja
            documento.add(tabla);
            //Añadir la enumeración
            documento.add(footer);
            //Añadir un salto de página
            documento.add(new AreaBreak());

            //Número del registro actual
            int filaActual = primerLimite;
            //Número de la hoja actual
            int hojaActual = 2;

            //Ciclo que recorerrá la cantidad de hojas completas calculadas
            for (int i = 0; i < hojasCompletas; i++) {

                if (divisible && i == hojasCompletas - 1 && type != REP_CLIENTES && type != REP_PROVEEDORES) {
                    /**
                     * Si la cantidad de hojas comletas es divisible, i equivale
                     * a la última hoja completa y el reporte es distinto de
                     * clientes y proveedores; entonces el límite máximo se
                     * reduce en dos registros, para pasar estos dos últimos
                     * datos a una hoja siguiente y mostrar el total
                     */
                    limiteMaximo -= 2;
                    ultimasFilas = 2;
                }

                //Instanciar una nueva lista de datos para el resto de hojas
                listaDatos = new Object[limiteMaximo][columnas];
                //Copiar los datos a la nueva lista
                System.arraycopy(datosSinHeader, filaActual, listaDatos, 0, limiteMaximo);
                //Crear una nueva tabla con los nuevos datos
                tabla = getTable(listaDatos);

                //Instanciar una nueva enumeración con la hoja actual y la cantidad
                //de hojas en total
                enumeracion = new Paragraph("Pagina " + hojaActual + " de " + cantidadHojas);
                //Asignar el estilo de títulos
                enumeracion.addStyle(titleStyle);
                //Instanciar una nueva tabla para la nueva enumeración
                footer = new Table(1).addCell(customCell(enumeracion, TextAlignment.CENTER));
                //Posicionar la tabla al final del documento
                footer.setFixedPosition(x, y, w);

                //Añadir la tabla actual y la enumeración actual al documento
                documento.add(tabla);
                documento.add(footer);

                //En caso de que el reporte sea de cliente o proveedores, NO 
                //siempre se le agregará un salto de línea
                if (type == REP_CLIENTES || type == REP_PROVEEDORES) {

                    if (!(divisible && i == hojasCompletas - 1)) {
                        /**
                         * Si la cantidad de hojas completas ES divisible Y la
                         * hoja actual es la ÚLTIMA hoja completa, entonces NO
                         * se le agrega un salto de línea. En caso contrario, SÍ
                         * se le agrega un salto de línea.
                         */
                        documento.add(new AreaBreak());
                    }

                } else {
                    //En otro caso, se agregará un salto de línea al final
                    documento.add(new AreaBreak());
                }

                //Obtener el índice del registro actual
                filaActual += limiteMaximo;
                //Sumar la hoja actual
                hojaActual++;
            }

            //Validar que las últimas filas sean mayor a 0
            if (ultimasFilas > 0) {
                //ÚLTIMA HOJA
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

    /**
     * Función obtener una tabla con una cantidad determinada de datos
     *
     * @param listaDatos Matriz con una cantidad de datos determinada
     *
     * @return Tabla con los datos
     */
    private static Table getTable(Object[][] listaDatos) {
        //Numero de columnas, según la cabecera
        int columnsCount = datos[0].length;
        //Creación de la tabla
        Table tabla = new Table(columnsCount);
        //Asignar un ancho del 100% del documento
        tabla.setWidth(UnitValue.createPercentValue(100));

        //CABECERA
        //Estilo para la cabecera
        Style headerStyle = new Style(titleStyle)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(0)
                .setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(azul, 1));
        //Ciclo para añadir las de para la cabecera con su estilo
        for (Object campo : datos[0]) {
            tabla.addCell(
                    new Cell().add(
                            new Paragraph(campo.toString())
                    ).addStyle(headerStyle)
            );
        }

        //CARGAR LOS DATOS
        //Estilo para los datos
        Style dataStyle = new Style()
                .setFont(segoe)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(0)
                .setBorder(Border.NO_BORDER);
        //Declaración de la celda para los datos
        Cell celda;

        //Ciclo que iterará la cantidad de datos obtenidos
        for (int i = 0; i < listaDatos.length; i++) {
            //Ciclo que iterará la cantidad de campos de la tabla
            for (int j = 0; j < columnsCount; j++) {

                //Instanciar una nueva celda, con su estilo
                celda = new Cell().addStyle(dataStyle);
                //Añadir un parrafo con el dato actual
                celda.add(new Paragraph(listaDatos[i][j].toString()));

                //Si la celda es par, asignar un fondo gris
                if ((i + 1) % 2 == 0) {
                    celda.setBackgroundColor(gris);
                }

                //Añadir la celda del dato a la tabla
                tabla.addCell(celda);
            }
        }

        return tabla;
    }

    /**
     * Función para crear la fila de los resultados, según el tipo de reporte
     *
     * @return Tabla con el total
     */
    private static Table getTotalTable() {
        //Estilo para el título
        Style totalStyle = new Style()
                .setFont(segoeBlack)
                .setFontSize(14)
                .setFontColor(blanco);

        //Estilo para la celda del total
        Cell total = new Cell()
                .add(new Paragraph("TOTAL").addStyle(totalStyle))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(0)
                .setBackgroundColor(azul)
                .setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(azul, 1));

        //Tabla para mostrar los datos totales
        Table tabla = new Table(2);
        //Asignar un ancho del 100% del documento
        tabla.setWidth(UnitValue.createPercentValue(100));
        //Asignar la celda con el título del total
        tabla.addCell(total);

        //Parrafo para los datos
        Paragraph parrafo = new Paragraph();

        //Determinar el tipo de reporte
        switch (type) {
            //Los reportes de recargas y compras, utilizan el mismo procedimiento
            case REP_RECARGAS:
            case REP_COMPRAS:
                //Variable para la cantidad de compras o recargas
                int cantidad = 0;
                //Variable para las ganancias (o inversiones)
                float ganancias = 0;

                //Ciclo para recorrer los datos de la tabla
                for (int i = 1; i < datos.length; i++) {
                    //Sumar la cantidad de compras o recargas
                    cantidad += Integer.valueOf(datos[i][4].toString());
                    //Sumar los montos de compras o recargas
                    ganancias += Float.valueOf(datos[i][5].toString());
                }

                //Titulo, según si es de recargas o de compras
                String titulo = (type == REP_RECARGAS) ? "Recargados:" : "Comprados:";
                //Añadir el título al parrafo
                parrafo.add(new Text("Botellones " + titulo + "\t\t").addStyle(titleStyle));
                //Añadir los datos al parrafo
                parrafo.add(
                        new Text(cantidad + "\t (" + ganancias + " Bs)")
                                .addStyle(titleStyle)
                                .setFontColor(rojo)
                );
                //Añadir el parrafo a la tabla
                tabla.addCell(
                        customCell(parrafo, TextAlignment.CENTER)
                                .setBorderTop(new SolidBorder(azul, 1))
                );
                break;

            case REP_DEUDAS:
                //Variables para la cantidad de botellones que se deben pagar y entregar
                int cant_pagar = 0;
                int cant_entregar = 0;

                //Ciclo para recorrer todos los datos de la tabla
                for (int i = 1; i < datos.length; i++) {
                    //Sumar todas las cantidades de ventas realizados
                    cant_pagar += Integer.valueOf(datos[i][3].toString());
                    //Sumar todos los precios de venta asignados
                    cant_entregar += Integer.valueOf(datos[i][4].toString());
                }

                //Tabla interna para mostrar un dato arriba del otro
                Table tablaInterna = new Table(1);
                //Posicionar la tabla interna en el centro de la tabla
                tablaInterna.setHorizontalAlignment(HorizontalAlignment.CENTER);

                //Vaciar el parrafo instanciando un nuevo parrafo
                parrafo = new Paragraph();
                //Añadir el título para los botellones que se deben pagar
                parrafo.add(
                        new Text("Botellones que deben pagar:\t\t" + cant_pagar)
                                .addStyle(titleStyle)
                                .setFontColor((cant_pagar > cant_entregar) ? rojo : verde)
                );
                //Añadir el parrafo con los botellones pagados
                tablaInterna.addCell(customCell(parrafo, TextAlignment.RIGHT));

                //Vaciar el parrafo instanciando un nuevo parrafo
                parrafo = new Paragraph();
                //Añadir el título para los botellones pagados
                parrafo.add(
                        new Text("Botellones que se deben entregar:\t\t" + cant_entregar)
                                .addStyle(titleStyle)
                                .setFontColor((cant_pagar > cant_entregar) ? rojo : verde)
                );
                //Añadir el parrafo con los botellones pagados
                tablaInterna.addCell(customCell(parrafo, TextAlignment.RIGHT));

                //Añadir la tabla interna con la alineación hacia la derecha
                tabla.addCell(
                        customCell(tablaInterna, TextAlignment.RIGHT)
                                .setBorderTop(new SolidBorder(azul, 1))
                );
                break;

            case REP_VENTAS:
                //Vaciar las variables para las cantidades y ganancias
                cantidad = 0;
                ganancias = 0;

                //Ciclo para recorrer todos los datos de la tabla
                for (int i = 1; i < datos.length; i++) {
                    //Sumar todas las cantidades de ventas realizados
                    cantidad += Integer.valueOf(datos[i][3].toString());
                    //Sumar todos los precios de venta asignados
                    ganancias += Float.valueOf(datos[i][5].toString());
                }

                //Añadir el título de los botellones vendidos
                parrafo.add(new Text("Botellones Vendidos:\t\t").addStyle(titleStyle));
                //Añadir los datos al parrafo
                parrafo.add(
                        new Text(cantidad + "\t (" + ganancias + " Bs)")
                                .addStyle(titleStyle)
                                .setFontColor(verde)
                );
                //Añadir el parrafo a la tabla
                tabla.addCell(
                        customCell(parrafo, TextAlignment.CENTER)
                                .setBorderTop(new SolidBorder(azul, 1))
                );
                break;

            case REP_TRASVASOS:
                //Variable para la cantidad de botellones 
                //pagados por los clientes
                int cantidadPagada = 0;
                //Variable para la cantidad de botellones 
                //entregados a los clientes
                int cantidadEntregada = 0;
                //Variable para el monto total
                int montoTotal = 0;

                //Ciclo para recorrer todos los datos de la tabla
                for (int i = 1; i < datos.length; i++) {
                    //Sumar la cantidad de botellones pagados
                    cantidadPagada += Integer.valueOf(datos[i][3].toString());
                    //Sumar la cantidad de botellones entergados
                    cantidadEntregada += Integer.valueOf(datos[i][4].toString());

                    //Monto total por los botellones
                    montoTotal += Float.valueOf(datos[i][7].toString());
                }

                //Texto para los botellones pagados
                Text pagados = new Text(cantidadPagada + "\t (" + montoTotal + " Bs)");
                pagados.addStyle(titleStyle).setFontColor(verde);

                //Texto para los botellones entregados
                Text entregados = new Text(String.valueOf(cantidadEntregada));
                entregados.addStyle(titleStyle).setFontColor(verde);

                //Validar si se entregaron MAS botellones de los que se pagaron
                if (cantidadEntregada > cantidadPagada) {
                    pagados.setFontColor(rojo);
                    entregados.setFontColor(rojo);
                }

                //Tabla interna para mostrar un dato encima del otro
                tablaInterna = new Table(1);
                //Posicionar la tabla interna en el centro de la tabla
                tablaInterna.setHorizontalAlignment(HorizontalAlignment.CENTER);

                //Vaciar el parrafo instanciando un nuevo parrafo
                parrafo = new Paragraph();
                //Añadir el título para los botellones pagados
                parrafo.add(new Text("Botellones pagados:\t\t").addStyle(titleStyle));
                //Añadir los datos de botellones pagados
                parrafo.add(pagados);
                //Añadir el parrafo con los botellones pagados
                tablaInterna.addCell(customCell(parrafo, TextAlignment.RIGHT));

                //Vaciar el parrafo instanciando un nuevo parrafo
                parrafo = new Paragraph();
                //Añadir el título para los botellones pagados
                parrafo.add(new Text("Botellones Entregados:\t\t").addStyle(titleStyle));
                //Añadir los datos de botellones pagados
                parrafo.add(entregados);
                //Añadir el parrafo con los botellones pagados
                tablaInterna.addCell(customCell(parrafo, TextAlignment.RIGHT));

                //Añadir la tabla interna con la alineación hacia la derecha
                tabla.addCell(
                        customCell(tablaInterna, TextAlignment.RIGHT)
                                .setBorderTop(new SolidBorder(azul, 1))
                );
                break;
        }

        //Retornar la tabla del total
        return tabla;
    }

    /**
     * Función para buscar los datos en la base de datos, según el tipo de
     * reporte y comprobar de que realmente se obtuvieron los datos
     *
     * @return Matriz con los datos y cabecera
     */
    private static int getDatos() {
        switch (type) {
            case REP_TRASVASOS:
                datos = ReadDB.getTrasvasos(initialDate, finalDate, sucursal);
                break;

            case REP_RECARGAS:
                datos = ReadDB.getRecargas(initialDate, finalDate, sucursal);
                break;

            case REP_COMPRAS:
                datos = ReadDB.getCompras(initialDate, finalDate, sucursal);
                break;

            case REP_VENTAS:
                datos = ReadDB.getVentas(initialDate, finalDate, sucursal);
                break;

            case REP_DEUDAS:
                //Crear la cabecera para las deudas
                Object[] header = {"ID", "Factura", "Cedula",
                    "Debe Pagar", "Debemos Entregar", "Fecha"};
                //Lista para obtener las deudas
                Object[][] tabla = ReadDB.getDeudas();

                //Validar que la respuesta NO sea nula
                if (tabla != null) {
                    //Instanciar la lista que tendrá todas las deudas
                    datos = new Object[tabla.length + 1][header.length];
                    //Asignar la primera fila como la cabecera
                    datos[0] = header;

                    //Ciclo que iterará la cantidad de deudas obtenidos.
                    for (int i = 0; i < tabla.length; i++) {
                        //Copiar la fila actual de deudas obtenidas a la tabla
                        System.arraycopy(tabla[i], 0, datos[i + 1], 0, tabla[0].length);
                    }
                } else {
                    datos = new Object[][]{{header}};
                }
                break;

            case REP_CLIENTES:
                //Crear la cabecera para los clientes
                header = new String[]{"ID", "Cedula", "Nombre", "Apellido", "Telefono"};
                //Lista para obtener los clientes
                tabla = ReadDB.getClientes();

                //Validar que la respuesta NO sea nula
                if (tabla != null) {
                    //Instanciar la tabla que tendrá todos los clientes
                    datos = new Object[tabla.length + 1][header.length];
                    //Asignar la primera fila como la cabecera
                    datos[0] = header;

                    //Ciclo que iterará la cantidad de clientes obtenidos.
                    for (int i = 0; i < tabla.length; i++) {
                        //Copiar la fila actual de clientes obtenidos a la tabla
                        System.arraycopy(tabla[i], 0, datos[i + 1], 0, tabla[0].length);
                    }
                } else {
                    datos = new Object[][]{{header}};
                }
                break;

            case REP_PROVEEDORES:
                //Crear la cabecera para los clientes
                header = new String[]{"ID", "RIF", "Nombre", "Telefono"};
                //Lista para obtener los proveedores
                tabla = ReadDB.getProveedores();

                //Validar que la respuesta NO sea nula
                if (tabla != null) {
                    //Instanciar la tabla que tendrá todos los proveedores
                    datos = new Object[tabla.length + 1][header.length];
                    //Asignar la primera fila como la cabecera
                    datos[0] = header;

                    //Ciclo que iterará la cantidad de proveedores obtenidos.
                    for (int i = 0; i < tabla.length; i++) {
                        //Copiar la fila actual de proveedores obtenidos a la tabla
                        System.arraycopy(tabla[i], 0, datos[i + 1], 0, tabla[0].length);
                    }
                } else {
                    datos = new Object[][]{{header}};
                }
                break;
        }

        //Comprobar si los datos NO están vacíos y retornar el resultado
        return (datos == null) ? Constantes.ERROR_VALUE : datos.length;
    }

    //ATRIBUTOS BACKEND
    private static Object[][] datos;
    private static String initialDate;
    private static String finalDate;
    private static int sucursal;

    // ========== FRONTEND ==========
    /**
     * Función para crear la cabecera de los reportes, siendo estos todos
     * iguales
     */
    private static void cabecera() {
        //Parrafo para el nombre del reporte
        Paragraph nombre = new Paragraph(getReportName());
        nombre.setMultipliedLeading(1);
        nombre.setFontSize(24);
        nombre.setFont(segoeBlack);

        //Parrafo para el nombre de la empresa
        Paragraph empresa = new Paragraph("AQUATECH");
        empresa.setMultipliedLeading(1);
        empresa.setFontSize(18);
        empresa.setFontColor(azul);
        empresa.setFont(segoeBlack);

        //Tabla para colocar los parrafos a la izquierda y derecha, en la misma
        //línea.
        Table tabla = new Table(2).setWidth(UnitValue.createPercentValue(100));
        tabla.addCell(customCell(nombre, TextAlignment.LEFT));
        tabla.addCell(customCell(empresa, TextAlignment.RIGHT));

        //Agregar la tabla y un parrafo con un salto de línea.
        documento.add(tabla);
        documento.add(new Paragraph("\n"));
    }

    /**
     * Función para crear la información principal del reporte, según su tipo.
     * Constando este del propósito, fecha de generación, ganancias, fecha de
     * inicio y fecha de fin.
     */
    private static void informacion() {
        //Numero de columnas que tendrá la tabla de la información, según
        //el tipo de reporte seleccionado
        int columnas;

        //Determinar el tipo de reporte
        switch (type) {
            case REP_CLIENTES:
            case REP_PROVEEDORES:
            case REP_DEUDAS:
                columnas = 2;
                break;

            default:
                columnas = 3;
        }

        //Creación de la tabla para contener la información del reporte
        Table tabla = new Table(columnas);
        //Asignar un ancho del 100% a la tabla
        tabla.setWidth(UnitValue.createPercentValue(100));

        //Parrafo para el propósito del reporte
        Paragraph proposito = getPurpose();
        //Agregar el propósito a la tabla
        tabla.addCell(customCell(proposito, TextAlignment.LEFT));

        //Validar si el reporte llevará fecha de filtro
        if (type != REP_CLIENTES && type != REP_PROVEEDORES && type != REP_DEUDAS) {
            //Parrafo para las ganancias
            Paragraph ganancias = getProfits();
            tabla.addCell(customCell(ganancias, TextAlignment.CENTER));

            //Tabla para las fechas de filtros
            Table fechas = getDates();
            tabla.addCell(customCell(fechas, TextAlignment.RIGHT));

        } else {
            //Si el reporte no tiene fechas de filtro, solo mostrará
            //la fecha en que fue generado el reporte
            Paragraph fecha = getDate();
            tabla.addCell(customCell(fecha, TextAlignment.CENTER));
        }

        //Añadir la tabla de información al documento
        documento.add(tabla);
        //Parrafo en blanco con un salto de línea
        documento.add(new Paragraph("\n"));
    }

    /**
     * Función para crear una tabla que contenga la fecha de generación del
     * reporte, fecha de inicio y fecha final.
     *
     * @return Tabla con las fechas
     */
    private static Table getDates() {
        //Tabla para las fechas
        Table tabla = new Table(2);
        //Alinear la tabla hacia la derecha
        tabla.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        //Asignar un ancho de 180 UnitValue
        tabla.setWidth(180);

        //FECHA INICIAL
        //Titulo de la fecha
        Text titulo = new Text("Fecha inicial\n").addStyle(titleStyle);
        //Texto para la fecha
        Text fecha = new Text(initialDate).addStyle(plainStyle);

        //Parrafo para el título y la fecha
        Paragraph parrafo = new Paragraph();
        parrafo.setMultipliedLeading(1);
        parrafo.add(titulo);
        parrafo.add(fecha);

        //Asignar el párrafo actual a la tabla
        tabla.addCell(customCell(parrafo, TextAlignment.CENTER));

        //FECHA DE GENERACIÓN DEL REPORTE
        //Titulo de la fecha
        titulo = new Text("Fecha reporte\n").addStyle(titleStyle);
        //Texto para la fecha
        fecha = new Text(getActualDate()).addStyle(plainStyle);

        //Parrafo para el título y la fecha
        parrafo = new Paragraph();
        parrafo.setMultipliedLeading(1);
        parrafo.add(titulo);
        parrafo.add(fecha);

        //Asignar el párrafo actual a la tabla
        tabla.addCell(customCell(parrafo, TextAlignment.CENTER));

        //FECHA FINAL
        //Titulo de la fecha
        titulo = new Text("Fecha final\n").addStyle(titleStyle);
        //Texto para la fecha
        fecha = new Text(finalDate).addStyle(plainStyle);

        //Parrafo para el título y la fecha
        parrafo = new Paragraph();
        parrafo.setMultipliedLeading(1);
        parrafo.add(titulo);
        parrafo.add(fecha);
        //Asignar el párrafo actual a la tabla
        tabla.addCell(customCell(parrafo, TextAlignment.CENTER));

        //Espacio en blanco en la cuarta celda
        tabla.addCell(customCell(new Paragraph(""), TextAlignment.CENTER));

        //Retornar la tabla con las fechas
        return tabla;
    }

    /**
     * Función para obtener un párrafo con la fecha en la que se generó el
     * reporte
     *
     * @return Parrafo con la fecha actual
     */
    private static Paragraph getDate() {
        Text titulo = new Text("Fecha reporte\n").addStyle(titleStyle);
        Text fecha = new Text(getActualDate()).addStyle(plainStyle);

        Paragraph fechaActual = new Paragraph();
        fechaActual.setMultipliedLeading(1);
        fechaActual.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        fechaActual.add(titulo);
        fechaActual.add(fecha);

        return fechaActual;
    }

    /**
     * Función para obtener el parrafo con las ganancias calculadas
     *
     * @return Parrafo con las ganancias
     */
    private static Paragraph getProfits() {
        //Titulo para las ganancias o inversiones, según el tipo
        String title = (type == REP_COMPRAS || type == REP_RECARGAS) ? "Invertido" : "Ganancias";
        //Objeto tipo texto para el título de las ganancias, con el estilo de títulos
        Text titulo = new Text(title + "\n").addStyle(titleStyle);

        //Parrafo que tendrá el título y las ganancias
        Paragraph parrafo = new Paragraph().add(titulo);
        //Espaciado entre línea ajustado (x1)
        parrafo.setMultipliedLeading(1);

        //Determinar el tipo de reporte para mostrar las ganancias
        switch (type) {
            //Los reportes de compras y recargas, utilizan el mismo procedimiento
            case REP_RECARGAS:
            case REP_COMPRAS:
                //Variable para la cantidad de ventas o recargas
                int cantidad = 0;
                //Variable para las ganancias (o inversiones)
                float ganancias = 0;

                //Ciclo para recorrer los datos de la tabla
                for (int i = 1; i < datos.length; i++) {

                    //Sumar todas las cantidades de recargas o compras
                    cantidad += Float.valueOf(datos[i][4].toString());

                    //Sumar todos los montos de recarga o compra
                    ganancias += Float.valueOf(datos[i][5].toString());
                }

                //Añadir las ganancias al parrafo, mostrando la cantidad de 
                //recargas o compras realizadas y, debajo, las ganancias en Bs
                String msj = cantidad
                        + (((type == REP_COMPRAS) ? " Compras" : " Recargas"))
                        + "\n(" + ganancias + " Bs)";
                parrafo.add(
                        new Text(msj)
                                .addStyle(plainStyle)
                                .setFontColor(rojo)
                );

                //Retornar el parrafo de las inversiones
                return parrafo;

            case REP_VENTAS:
                //Vaciar las variables para las cantidades y ganancias
                cantidad = 0;
                ganancias = 0;

                //Ciclo para recorrer todos los datos de la tabla
                for (int i = 1; i < datos.length; i++) {
                    //Sumar todas las cantidades de ventas realizados
                    cantidad += Float.valueOf(datos[i][3].toString());
                    //Sumar todos los precios de venta asignados
                    ganancias += Float.valueOf(datos[i][6].toString());
                }

                //Añadir las ganancias al párrafo, mostrando la cantidad de
                //ventas realizados y, debajo, las ganancias en Bs
                parrafo.add(
                        new Text(cantidad + " Ventas\n(" + ganancias + " Bs)")
                                .addStyle(plainStyle)
                                .setFontColor(verde)
                );

                //Retornar el parrafo con las ganancias
                return parrafo;

            case REP_TRASVASOS:
                //Variable para la cantidad de botellones 
                //pagados por los clientes
                int cantidadPagada = 0;
                //Variable para la cantidad de botellones 
                //entregados a los clientes
                int cantidadEntregada = 0;
                //Variable para el monto total
                int ganacias = 0;

                //Ciclo para recorrer los datos de la tabla
                for (int i = 1; i < datos.length; i++) {

                    //Sumar la cantidad de botellones pagados
                    cantidadPagada += Integer.valueOf(datos[i][3].toString());
                    //Sumar la cantidad de botellones entergados
                    cantidadEntregada += Integer.valueOf(datos[i][4].toString());

                    //Monto total por los botellones pagados
                    ganacias += Float.valueOf(datos[i][7].toString());
                }

                //Texto para mostrar los botellones pagados
                Text pagados = new Text(cantidadPagada + " (" + ganacias + " Bs)");
                pagados.addStyle(plainStyle).setFontColor(verde);

                //Texto para mostrar los botellones entregados
                Text entregados = new Text(String.valueOf(cantidadEntregada));
                entregados.addStyle(plainStyle).setFontColor(verde);

                //Validar si se entregaron MAS de lo que pagaron
                if (cantidadEntregada > cantidadPagada) {
                    //Mostrar las ganancias en pérdidas
                    pagados.setFontColor(rojo);
                    entregados.setFontColor(rojo);
                }

                //Añadir los datos al parrafo de las ganancias
                parrafo.add(new Text("Botellones pagados\n").addStyle(plainStyle).setFontColor(azul));
                parrafo.add(pagados);
                parrafo.add(new Text("\nBotellones Entregados\n").addStyle(plainStyle).setFontColor(azul));
                parrafo.add(entregados);

                return parrafo;
            default:
                return new Paragraph("");
        }
    }

    /**
     * Fucnión para obtener el parrafo que contendrá el propósito del reporte
     *
     * @return Parrafo con el propósito
     */
    private static Paragraph getPurpose() {
        //Asignar el título en el parrafo y asignar el estilo de títulos
        Text titulo = new Text("Proposito\n").addStyle(titleStyle);
        //Obtener el propósito del reporte y asigar el estilo normal
        Text prop = new Text(getReportPrupose()).addStyle(plainStyle);

        //Objeto parrafo que tendrá el título y el propósito
        Paragraph proposito = new Paragraph();
        //Espaciado entre líneas ajustado (x1)
        proposito.setMultipliedLeading(1);

        //Determinar el tipo de reporte para asignar un ancho en específico al
        //parrafo del propósito
        switch (type) {

            case REP_TRASVASOS:
                proposito.setWidth(120);
                break;

            case REP_RECARGAS:
                proposito.setWidth(140);
                break;

            case REP_DEUDAS:
            case REP_CLIENTES:
            case REP_PROVEEDORES:
                proposito.setWidth(250);
                break;

            default:
                proposito.setWidth(150);
                break;
        }

        //Asignar los textos al parrafo
        proposito.add(titulo);
        proposito.add(prop);

        return proposito;
    }

    /**
     * Función para crear una celda customizada, con un elemento en específico,
     * alineamiento determinado, con un padding de 0 y sin bordes.
     *
     * @param elemento Elemento dentro de la celda
     * @param alignment Alineación del elemento
     *
     * @return Celda customizada
     */
    private static Cell customCell(IBlockElement elemento, TextAlignment alignment) {
        Cell celda = new Cell().add(elemento);
        celda.setPadding(0);
        celda.setTextAlignment(alignment);
        celda.setBorder(Border.NO_BORDER);
        return celda;
    }

    /**
     * Función para obtener el propósito del reporte, según su tipo
     *
     * @return Propósito del reporte.
     */
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
                        + "botellones realizadas a los clientes, en una fecha "
                        + "determinada";
            case REP_CLIENTES:
                return "Reporte para visualizar a todos los clientes registrados "
                        + "en el sistema";
            case REP_PROVEEDORES:
                return "Reporte para visualizar a todos los proveedores "
                        + "registrados en el sistema";
            default:
                return "";
        }
    }

    /**
     * Función para obtener el nombre del tipo de reporte generado: Trasvaso,
     * deuda, recarga, compra, venta, cliente o proveedores
     *
     * @return
     */
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

    /**
     * Función para obtener la fecha en el momento actual
     *
     * @return <b>Formato:</b><br> AAAA-MM-DD <br> hh : mm : ss
     */
    private static String getActualDate() {
        //Objeto tipo date para obtener la fecha actual
        Date date = new java.util.Date();
        //Objeto de tipo calendario para obtener los datos de la fehca
        Calendar calendario = new java.util.GregorianCalendar();
        //Asignar la fecha actual
        calendario.setTime(date);

        //Dividir la información de la fecha
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH) + 1;
        int day = calendario.get(Calendar.DAY_OF_MONTH);
        int hour = calendario.get(Calendar.HOUR_OF_DAY);
        int minute = calendario.get(Calendar.MINUTE);
        int second = calendario.get(Calendar.SECOND);

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
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH) + 1;
        int day = calendario.get(Calendar.DAY_OF_MONTH);
        int hour = calendario.get(Calendar.HOUR_OF_DAY);
        int minute = calendario.get(Calendar.MINUTE);
        int second = calendario.get(Calendar.SECOND);

        //Validar que los datos sean de uno o dos dígitos.
        //En caso de ser de un dígito, se le coloca un cero antes del 
        //valor. Ex: 9 -> "09"
        String mes = (month < 10) ? ("0" + month) : String.valueOf(month);
        String dia = (day < 10) ? ("0" + day) : String.valueOf(day);
        String hora = (hour < 10) ? ("0" + hour) : String.valueOf(hour);
        String minuto = (minute < 10) ? ("0" + minute) : String.valueOf(minute);
        String segundo = (second < 10) ? ("0" + second) : String.valueOf(second);

        //Nombre predeterminado de las base de datos
        String name = getReportName() + "_";
        //Fecha cuando se realizó el respaldo
        String fecha = year + "-" + mes + "-" + dia + "-" + hora + "_" + minuto + "-" + segundo + ".pdf";

        //Retornar el nombre con la fecha
        return name + fecha;
    }

    /**
     * Función para la creación del documento PDF
     */
    private static void crearPDF() {
        try {
            //PdfWriter permite crear un fichero PDF real en una ruta determinada
            pdfWriter = new PdfWriter(absolutePath);

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

            //Asignar los márgenes del documento, teniendo en cuenta que iText
            //maneja el tamaño en UnitValu, siendo 72 UnitValue = 1 Inch.
            //Para asignar el margen a centímetros, se aplicó la formula:
            //margin = (cm /inch) * UnitsPerInch.
            float inch = 2.54f;
            int unitPerInch = 72;
            float marginH = (1.5f / inch) * unitPerInch;
            float marginV = (1f / inch) * unitPerInch;
            documento.setMargins(marginV, marginH, marginV, marginH);

            //Obtener el tamaño del documento
            defaultSize = pdfDoc.getDefaultPageSize();

            //Cargar las fuentes de letra del reporte
            segoe = PdfFontFactory.createFont("src/fonts/segoeui.ttf");
            segoeBlack = PdfFontFactory.createFont("src/fonts/segoeui_black.ttf");

            //Estilo por defecto para los títulos
            titleStyle = new Style()
                    .setFont(segoeBlack)
                    .setFontColor(azul)
                    .setFontSize(10);
            //Estilo por defecto para las letras
            plainStyle = new Style()
                    .setFont(segoe)
                    .setFontSize(10);

            //Construir el documento
            cabecera();
            informacion();
            cuerpo();

            //Cerrar el documento
            documento.close();
            pdfDoc.close();
            pdfWriter.close();

            //Abrir la carpeta contenedora
            abrir();

        } catch (IOException e) {
            Mensaje.msjError("No se pudo generar el reporte.\nError: " + e);
        }
    }

    /**
     * Función principal para crear un reporte PDF, sin filtro de fechas
     *
     * @param type Tipo de reporte generado (cliente o proveedor)
     * @param path Ruta donde se generará el reporte (sin el nombre)
     */
    public static void crear(int type, String path) {
        CrearReporte.type = type;
        CrearReporte.path = path;
        CrearReporte.fileName = getDefaultName();
        CrearReporte.absolutePath = CrearReporte.path + "\\" + CrearReporte.fileName;

        int result = getDatos();

        //Validar que exista, al menos, un registro (sin contar el header)
        if (result > 1) {
            crearPDF();
        } else if (result == 1) {
            //Si no se obtuvo ningún registro, mostrar un mensaje de advertencia
            String adv = "No se obtuvo ningún registro de la base de datos.\n"
                    + "¿Quiere crear el reporte de igual forma?";

            //Validar si se creará el reporte o no
            if (Mensaje.msjYesNo(adv)) {
                crearPDF();
            }

            //Si el resultado fue menor de 1, implica un error de conexión con la BDD
        } else {
            Mensaje.msjError("No se pudo extraer los registros de la base de "
                    + "datos\npara la generación del reporte.\n"
                    + "Por favor, verifique su conexión con la base de datos.");
        }

        vaciarDatos();
    }

    /**
     * Función principal para crear un reporte PDF, filtrado con una fecha
     * inicial o una fecha final.
     *
     * @param type Tipo de reporte que será generado (venta, compra, trasvase,
     * recarga o deuda)
     * @param path Ruta en que será generado el PDF (sin el nombre del archivo)
     * @param initialDate Fecha desde donde será filtrado los registros
     * @param finalDate Fecha final hasta donde se mostrarán los registros
     * @param sucursal
     */
    public static void crear(int type, String path, String initialDate, String finalDate, int sucursal) {

        //Almacenar los datos enviados a los atributos de la clase
        CrearReporte.type = type;
        CrearReporte.path = path;
        CrearReporte.fileName = getDefaultName();
        CrearReporte.absolutePath = CrearReporte.path + "\\" + CrearReporte.fileName;
        CrearReporte.initialDate = initialDate;
        CrearReporte.finalDate = finalDate;
        CrearReporte.sucursal = sucursal;

        //Buscar los datos y obtener el número de datos obtenidos
        int result = getDatos();

        //Validar que exista, al menos, un registro (sin contar el header)
        if (result > 1) {
            crearPDF();
        } else if (result == 1) {
            //Si no se obtuvo ningún registro, mostrar un mensaje de advertencia
            String adv = "No se obtuvo ningún registro de la base de datos.\n"
                    + "¿Quiere crear el reporte de igual forma?";

            //Validar si se creará el reporte o no
            if (Mensaje.msjYesNo(adv)) {
                crearPDF();
            }

            //Si el resultado fue menor de 1, implica un error de conexión con la BDD
        } else {
            Mensaje.msjError("No se pudo extraer los registros de la base de "
                    + "datos\npara la generación del reporte.\n"
                    + "Por favor, verifique su conexión con la base de datos.");
        }

        vaciarDatos();
    }

    /**
     * Función para vacíar los datos importantes luego de crear, o no, los
     * documentos PDF
     */
    private static void vaciarDatos() {
        type = 0;
        absolutePath = null;
        initialDate = null;
        finalDate = null;
        datos = null;
        sucursal = -1;
    }

    /**
     * Función para mostrar un mensaje de éxito y preguntar si se abrirá la
     * carpeta contenedora del reporte generado.
     */
    private static void abrir() {
        String msj
                = "<html>"
                + "<p><b>¡Se ha creado el reporte con éxito!</b></p>"
                + "<p>Nombre del archivo: " + fileName + "</p><br>"
                + "<p><b>¿Desea abrir la carpeta donde se generó?</b></p>"
                + "</html>";

        String[] botones = {"Abrir carpeta", "Finalizar"};

        int opcion = javax.swing.JOptionPane.showOptionDialog(
                null,
                msj,
                "Reporte PDF generado",
                0,
                javax.swing.JOptionPane.INFORMATION_MESSAGE,
                null,
                botones,
                botones[1]
        );

        //Validar si se va abrir o no la carpeta
        if (opcion == 0) {

            //Archivo que contiene la ruta del documento creado
            File folder = new File(path);

            //Comprobar si el dispositivo donde se ejecuta el programa, es 
            //compatible con la clase "Desktop" para poder abrir una carpeta
            if (Desktop.isDesktopSupported()) {
                try {
                    //Intentar abrir la carpeta
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(folder);

                } catch (IOException e) {
                    Mensaje.msjError("No se pudo abrir la ruta:\n"
                            + folder.getAbsolutePath() + "\nPor favor, verifique"
                            + "la existencia de la carpeta y el respaldo."
                    );
                }
            } else {
                Mensaje.msjError("La clase 'Desktop' no es compatible con la "
                        + "plataforma actual.\nNo se podrá abrir la carpeta"
                        + "contenedora del respaldo");
            }
        }
    }

    //ATRIBUTOS FRONTEND
    private static int type;
    private static String path;
    private static String fileName;
    private static String absolutePath;
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
