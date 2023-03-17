package components;

import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import static javax.swing.SwingConstants.HORIZONTAL;

/**
 * Clase para la creación del panel de la cuenta generada en los trasvases,
 * recargas, ventas y compras.
 */
public class PanelFactura extends JPanel implements properties.Colores, properties.Constantes {

    // ========== BACKEND ==========
    
    //SETTERS
    /**
     * Función para asignar el monto total en las facturas de venta y trasvasos
     *
     * @param total Precio total de la venta o trasvaso
     */
    public void setMontoTotal(double total) {
        
        this.lblTotal[1].setText(String.format("%.2f", total) + " Bs");
        this.lblTotal[1].setSize(this.lblTotal[1].getPreferredSize());

        //Reposicionar
        int y = lblTotal[0].getY();
        int x = this.getWidth() - padding - lblTotal[1].getWidth();
        this.lblTotal[1].setLocation(x, y);
        
    }
    
    /**
     * Función para asignar la cantidad de botellones entregados en la factura
     * de los trasvasos
     *
     * @param cantidad Cantidad de botellones
     */
    public void setBotellonesEntregados(int cantidad) {
        //Validar que el tipo de factura sea de trasvasos
        if (type == VENTAS_TRASVASO) {

            //Asignar la cantidad de botellones entregados
            this.lblDatos[0][1].setText(String.valueOf(cantidad));
            this.lblDatos[0][1].setSize(this.lblDatos[0][1].getPreferredSize());

            //Reposicionar
            int y = lblDatos[0][0].getY();
            int x = this.getWidth() - padding - lblDatos[0][1].getWidth();
            this.lblDatos[0][1].setLocation(x, y);
        }
    }

    /**
     * Función para asignar la cantidad de botellones pagados en la factura de
     * los trasvasos
     *
     * @param cantidad Cantidad de botellones
     */
    public void setBotellonesPagados(int cantidad) {
        //Validar que el tipo de factura sea de trasvasos
        if (type == VENTAS_TRASVASO) {

            //Asignar la cantidad de botellones entregados
            this.lblDatos[1][1].setText(String.valueOf(cantidad));
            this.lblDatos[1][1].setSize(this.lblDatos[1][1].getPreferredSize());

            //Reposicionar
            int y = lblDatos[1][0].getY();
            int x = this.getWidth() - padding - lblDatos[1][1].getWidth();
            this.lblDatos[1][1].setLocation(x, y);
        }
    }

    /**
     * Función para asignar la cantidad de botellones en total en las facturas
     * de compras y ventas de botellones
     *
     * @param cantidad Cantidad de botellones
     */
    public void setBotellonesTotal(int cantidad) {
        //Validar que el tipo de factura sea de trasvasos
        if (type == VENTAS_BOTELLON || type == COMPRAS_RECARGA || type == COMPRAS_BOTELLON) {

            //Asignar la cantidad de botellones entregados
            this.lblDatos[0][1].setText(""+cantidad);
            this.lblDatos[0][1].setSize(this.lblDatos[0][1].getPreferredSize());

            //Reposicionar
            int y = lblDatos[0][0].getY();
            int x = this.getWidth() - padding - lblDatos[0][1].getWidth();
            this.lblDatos[0][1].setLocation(x, y);
        }
    }

    /**
     * Función para asignar que se hará la venta o trasvaso a domicilio
     *
     * @param delivery True o false.
     */
    public void setDelivery(boolean delivery) {
        //Validar que la factura sea de tipo de trasvaso
        if (type == VENTAS_TRASVASO) {
            this.lblDatos[2][1].setText((delivery) ? "SÍ" : "NO");
            this.lblDatos[2][1].setSize(this.lblDatos[2][1].getPreferredSize());

            //Reposicionar
            int y = lblDatos[2][0].getY();
            int x = this.getWidth() - padding - lblDatos[2][1].getWidth();
            this.lblDatos[2][1].setLocation(x, y);

            //Validar que la factura sea de tipo de ventas
        } else if (type == VENTAS_BOTELLON) {
            this.lblDatos[1][1].setText((delivery) ? "SÍ" : "NO");
            this.lblDatos[1][1].setSize(this.lblDatos[1][1].getPreferredSize());

            //Reposicionar
            int y = lblDatos[1][0].getY();
            int x = this.getWidth() - padding - lblDatos[1][1].getWidth();
            this.lblDatos[1][1].setLocation(x, y);
        }
    }

    /**
     * Función para asignar el tipo de pago que se realizará en el trasvaso o
     * venta de botellón
     *
     * @param tipo Tipo de pago
     */
    public void setTipoPago(String tipo) {
        //Validar que la factura sea de tipo de trasvaso
        if (type == VENTAS_TRASVASO) {
            this.lblDatos[3][1].setText(tipo);
            this.lblDatos[3][1].setSize(this.lblDatos[3][1].getPreferredSize());

            //Reposicionar
            int y = lblDatos[3][0].getY();
            int x = this.getWidth() - padding - lblDatos[3][1].getWidth();
            this.lblDatos[3][1].setLocation(x, y);

            //Validar que la factura sea de tipo de ventas
        } else if (type == VENTAS_BOTELLON) {
            this.lblDatos[2][1].setText(tipo);
            this.lblDatos[2][1].setSize(this.lblDatos[2][1].getPreferredSize());

            //Reposicionar
            int y = lblDatos[2][0].getY();
            int x = this.getWidth() - padding - lblDatos[2][1].getWidth();
            this.lblDatos[2][1].setLocation(x, y);
        }
    }

    /**
     * Función para asignar el precio c/u de los botellones en las compras
     *
     * @param precio Precio de cada botellón
     */
    public void setPrecioCadaUno(double precio) {
        //Validar que la factura sea de tipo de compras
        
        if (type == COMPRAS_RECARGA || type == COMPRAS_BOTELLON) {
            
            this.lblDatos[1][1].setText(String.format("%.2f", precio) + " Bs");
            this.lblDatos[1][1].setSize(this.lblDatos[1][1].getPreferredSize());

            //Reposicionar
            int y = lblDatos[1][0].getY();
            int x = this.getWidth() - padding - lblDatos[1][1].getWidth();
            this.lblDatos[1][1].setLocation(x, y);
        }
    }

    /**
     * Función para asignar la información de la persona o proveedor en las
     * facturas
     *
     * @param id Identificación de la empresa o persona
     * @param name Nombre de la empresa o persona
     */
    public void setInformacion(String id, String name) {
        lblHeader[1][0].setText(id);
        lblHeader[1][1].setText(name);
        this.lblHeader[1][0].setSize(this.lblHeader[1][0].getPreferredSize());
        this.lblHeader[1][1].setSize(this.lblHeader[1][1].getPreferredSize());
    }

    // ========== FRONTEND ==========
    /**
     * Constructor del panel para la cuenta
     *
     * @param type Tipo de cuenta (Trasvaso, venta o compra)
     */
    public PanelFactura(int type) {
        this.type = type;

        this.setLayout(null);
        this.setBackground(BLANCO);
        this.setBorder(createLineBorder(GRIS));

        initComponents();

        //Determinar el tipo de factura
        switch (this.type) {
            case COMPRAS_RECARGA:
            case COMPRAS_BOTELLON:
                datosCompra();
                break;
            case VENTAS_BOTELLON:
                datosVenta();
                break;
            case VENTAS_TRASVASO:
                datosTrasvaso();
                break;
            default:
                break;
        }
    }

    /**
     * Función para iniciar los componentes básicos
     *
     * @param type
     */
    private void initComponents() {
        //MONTO TOTAL
        this.lblTotal = new Label[2];
        this.lblTotal[0] = new Label("Total", GRUESA, fontSize);
        this.lblTotal[0].setToolTipText("Ganancias totales (Bs) de la cuenta");
        this.lblTotal[1] = new Label("0,00 Bs", GRUESA, 14);

        //SEPARADORES
        this.separadores = new JSeparator[3];

        //PROPIEDADES DE LOS SEPARADORES
        for (int i = 0; i < 3; i++) {
            separadores[i] = new JSeparator(HORIZONTAL);
            separadores[i].setForeground(NEGRO);
        }

        //PROPIEDADES DEL HEADER
        this.lblHeader = new Label[2][2];
        this.lblHeader[0][0] = new Label((this.type == COMPRAS) ? "RIF" : "Cédula", GRUESA, fontSize);
        this.lblHeader[0][1] = new Label((this.type == COMPRAS) ? "Nombre" : "Apellido", GRUESA, fontSize);
        this.lblHeader[1][0] = new Label("", NORMAL, fontSize);
        this.lblHeader[1][1] = new Label("", NORMAL, fontSize);

        //AGREGAR LOS COMPONENTES
        this.add(this.lblTitulo);
        this.add(this.lblTotal[0]);
        this.add(this.lblTotal[1]);
        this.add(this.separadores[0]);
        this.add(this.separadores[1]);
        this.add(this.separadores[2]);
        for (Label[] row : lblHeader) {
            for (Label dato : row) {
                this.add(dato);
            }
        }
    }

    /**
     * Función para agregar los datos para las facturas de compras
     */
    private void datosCompra() {
        //Instanciar los componentes
        this.lblDatos = new Label[2][2];
        this.lblDatos[0][0] = new Label("Cantidad", NORMAL, fontSize, true);
        this.lblDatos[0][1] = new Label("0", GRUESA, fontSize);
        this.lblDatos[1][0] = new Label("Precio c/u", NORMAL, fontSize, true);
        this.lblDatos[1][1] = new Label("0,00 Bs", GRUESA, 14);

        //Asignar el tooltip según el tipo
        String tooltip = (this.type == COMPRAS_BOTELLON)
                ? "Cantidad de botellones que se van a comprar"
                : "Cantidad de botellones que se van a recargar";
        this.lblDatos[0][0].setToolTipText(tooltip);

        tooltip = (this.type == COMPRAS_BOTELLON)
                ? "Precio de cada botellón que se va a comprar"
                : "Precio de cada botellón que se va a recargar";
        this.lblDatos[1][0].setToolTipText(tooltip);

        //Obtener la cantidad de filas de los datos
        this.datosRows = this.lblDatos.length;

        //Agregar los componentes
        for (Label[] row : lblDatos) {
            for (Label dato : row) {
                this.add(dato);
            }
        }
    }

    /**
     * Función para agregar los datos para las facturas de ventas
     */
    private void datosVenta() {
        //Instanciar los componentes
        this.lblDatos = new Label[3][2];
        this.lblDatos[0][0] = new Label("Botellones en total", NORMAL, fontSize, true);
        this.lblDatos[0][1] = new Label("0", GRUESA, fontSize);
        this.lblDatos[1][0] = new Label("Entrega a domicilio", NORMAL, fontSize, true);
        this.lblDatos[1][1] = new Label("NO", GRUESA, fontSize);
        this.lblDatos[2][0] = new Label("Tipo de pago", NORMAL, fontSize, true);
        this.lblDatos[2][1] = new Label("", GRUESA, fontSize);

        //Asignar los tooltip text
        this.lblDatos[0][0].setToolTipText("Cantidad de botellones que se van a vender al cliente");
        this.lblDatos[1][0].setToolTipText("Determinar si se le hará entregara a domicilio al cliente");
        this.lblDatos[2][0].setToolTipText("Tipo de pago que hará el cliente para realizar la venta");
        this.lblDatos[2][1].setToolTipText(
                "<html>"
                + "EFECT = <b>Efectivo</b><br>"
                + "TRNSF = <b>Transferencia</b><br>"
                + "DOLAR = <b>Dolar en efectivo</b>"
                + "</html>"
        );

        //Obtener el número de filas
        this.datosRows = this.lblDatos.length;

        //Agregar los componentes
        for (Label[] row : lblDatos) {
            for (Label dato : row) {
                this.add(dato);
            }
        }
    }

    /**
     * Función para agregar los datos para las facturas de trasvasos
     */
    private void datosTrasvaso() {
        //Instanciar los componentes
        this.lblDatos = new Label[4][2];
        this.lblDatos[0][0] = new Label("Bot. Entregados", NORMAL, fontSize, true);
        this.lblDatos[0][1] = new Label("0", GRUESA, fontSize);
        this.lblDatos[1][0] = new Label("Bot. Pagados", NORMAL, fontSize, true);
        this.lblDatos[1][1] = new Label("0", GRUESA, fontSize);
        this.lblDatos[2][0] = new Label("Entrega a domicilio", NORMAL, fontSize, true);
        this.lblDatos[2][1] = new Label("NO", GRUESA, fontSize);
        this.lblDatos[3][0] = new Label("Tipo de pago", NORMAL, fontSize, true);
        this.lblDatos[3][1] = new Label("", GRUESA, fontSize);

        //Asignar los tooltip text
        this.lblDatos[0][0].setToolTipText("<html>Cantidad de botellones <b>que se le trasvasaron al cliente</b></html>");
        this.lblDatos[1][0].setToolTipText("<html>Cantidad de botellones <b>que el cliente pagó</b></html>");
        this.lblDatos[2][0].setToolTipText("Determinar si se le hará entregara a domicilio al cliente");
        this.lblDatos[3][0].setToolTipText("Tipo de pago que hará el cliente para realizar el trasvaso");
        this.lblDatos[3][1].setToolTipText(
                "<html>"
                + "EFECT = <b>Efectivo</b><br>"
                + "TRNSF = <b>Transferencia</b><br>"
                + "DOLAR = <b>Dolar en efectivo</b>"
                + "</html>"
        );

        //Obtener la cantidad de filas
        this.datosRows = this.lblDatos.length;

        //Agregar los componentes
        for (Label[] row : lblDatos) {
            for (Label dato : row) {
                this.add(dato);
            }
        }
    }

    /**
     * Función para reposicionar los componentes en tiempo real
     */
    public void relocateComponents() {
        //Ancho y alto de los separadores
        int spWidth = this.getWidth() - padding * 2;
        int spHeight = separadores[0].getPreferredSize().height;
        //alto de las celdas
        int cellHeight = lblHeader[0][0].getHeight();

        //El título de la factura siempre estará en la misma posición
        this.lblTitulo.setLocation(padding, padding);

        //PRIMERA FILA CABECERA
        //Altura de las primeras celdas
        int cellY = padding * 2 + lblTitulo.getHeight();
        //Posición de la segunda celda
        int cellX = padding + 80;
        lblHeader[0][0].setLocation(padding, cellY);
        lblHeader[0][1].setLocation(cellX, cellY);

        //SEPARADOR
        //Posicion y altura de la celda + el gap vertical
        int spY = cellY + cellHeight + gapV;
        separadores[0].setBounds(padding, spY, spWidth, spHeight);

        //SEGUNDA FILA CABECERA
        //Posición y altura del separador + el gap vertical
        cellY = spY + spHeight + gapV;
        lblHeader[1][0].setLocation(padding, cellY);
        lblHeader[1][1].setLocation(cellX, cellY);

        //SEPARADOR
        //Posición y altura de la última celda + un padding
        spY = cellY + cellHeight + padding;
        separadores[1].setBounds(padding, spY, spWidth, spHeight);

        //PRIMERA FILA DE LOS DATOS
        //Posición y altura de la última celda + gap vertical
        cellY = spY + spHeight + gapV;
        //Posición de la segunda celda a la derecha de la factura
        cellX = this.getWidth() - padding - lblDatos[0][1].getWidth();
        lblDatos[0][0].setLocation(padding, cellY);
        lblDatos[0][1].setLocation(cellX, cellY);

        //SEGUNDA FILA DE LOS DATOS
        //Posición y altura de la última celda + gap vertical
        cellY = cellY + cellHeight + gapV;
        //Posición de la segunda celda a la derecha de la factura
        cellX = this.getWidth() - padding - lblDatos[1][1].getWidth();
        lblDatos[1][0].setLocation(padding, cellY);
        lblDatos[1][1].setLocation(cellX, cellY);

        //Validar si existe una tercera fila en los datos
        if (datosRows > 2) {
            //TERCERA FILA DE LOS DATOS
            //Posición y altura de la última celda + gap vertical
            cellY = cellY + cellHeight + gapV;
            //Posición de la segunda celda a la derecha de la factura
            cellX = this.getWidth() - padding - lblDatos[2][1].getWidth();
            lblDatos[2][0].setLocation(padding, cellY);
            lblDatos[2][1].setLocation(cellX, cellY);
        }

        //Validar si existe una cuarta fila en los datos
        if (datosRows > 3) {
            //CUARTA FILA DE LOS DATOS
            //Posición y altura de la última celda + gap vertical
            cellY = cellY + cellHeight + gapV;
            //Posición de la segunda celda a la derecha de la factura
            cellX = this.getWidth() - padding - lblDatos[3][1].getWidth();
            lblDatos[3][0].setLocation(padding, cellY);
            lblDatos[3][1].setLocation(cellX, cellY);
        }

        //SEPARADOR
        //Posición y altura de la última celda + gap vertical
        spY = cellY + cellHeight + gapV;
        separadores[2].setBounds(padding, spY, spWidth, spHeight);

        //MONTO TOTAL
        //Posición y altura del último separador + gap vertical
        cellY = spY + spHeight + gapV;
        //Posición de la segunda celda a la derecha de la factura
        cellX = this.getWidth() - padding - lblTotal[1].getWidth();
        lblTotal[0].setLocation(padding, cellY);
        lblTotal[1].setLocation(cellX, cellY);
    }

    //ATRIBUTOS
    private final int type;
    private static final int padding = 20;
    private static final int gapV = 4;

    //COMPONENTES
    private final Label lblTitulo = new Label("Cuenta", TITULO, 26);
    private Label[] lblTotal;
    private JSeparator[] separadores;
    private Label[][] lblHeader;
    private Label[][] lblDatos;
    private int datosRows;
    private int fontSize = 16;

}
