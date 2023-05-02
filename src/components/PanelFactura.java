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
            this.lblDatos[0][1].setText("" + cantidad);
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
        lblPersona[1][0].setText(id);
        lblPersona[1][1].setText(name);
        this.lblPersona[1][0].setSize(this.lblPersona[1][0].getPreferredSize());
        this.lblPersona[1][1].setSize(this.lblPersona[1][1].getPreferredSize());
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
        this.lblTotal[0] = new Label("Total", GRUESA, fontSize, true);
        this.lblTotal[0].setToolTipText("Ganancias totales (Bs) de la cuenta");
        this.lblTotal[1] = new Label("0,00 Bs", GRUESA, fontSize - 2);

        //SEPARADORES
        this.separadores = new JSeparator[3];

        //PROPIEDADES DE LOS SEPARADORES
        for (int i = 0; i < 3; i++) {
            separadores[i] = new JSeparator(HORIZONTAL);
            separadores[i].setForeground(NEGRO);
        }

        //PROPIEDADES DEL DESTINATARIO
        this.lblPersona = new Label[2][2];
        this.lblPersona[0][0] = new Label((this.type == COMPRAS) ? "RIF" : "Cédula", GRUESA, fontSize);
        this.lblPersona[0][1] = new Label((this.type == COMPRAS) ? "Nombre" : "Apellido", GRUESA, fontSize);
        this.lblPersona[1][0] = new Label("", PLANO, fontSize);
        this.lblPersona[1][1] = new Label("", PLANO, fontSize);

        //AGREGAR LOS COMPONENTES
        this.add(this.lblTitulo);
        this.add(this.lblTotal[0]);
        this.add(this.lblTotal[1]);
        this.add(this.separadores[0]);
        this.add(this.separadores[1]);
        this.add(this.separadores[2]);
        for (Label[] row : lblPersona) {
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
        this.lblDatos[0][0] = new Label("Cantidad", PLANO, fontSize, true);
        this.lblDatos[0][1] = new Label("0", GRUESA, fontSize);
        this.lblDatos[1][0] = new Label("Precio c/u", PLANO, fontSize, true);
        this.lblDatos[1][1] = new Label("0,00 Bs", GRUESA, fontSize - 2);

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
        this.lblDatos[0][0] = new Label("Botellones en total", PLANO, fontSize, true);
        this.lblDatos[0][1] = new Label("0", GRUESA, fontSize);
        this.lblDatos[1][0] = new Label("Entrega a domicilio", PLANO, fontSize, true);
        this.lblDatos[1][1] = new Label("NO", GRUESA, fontSize);
        this.lblDatos[2][0] = new Label("Tipo de pago", PLANO, fontSize, true);
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
        this.lblDatos[0][0] = new Label("Bot. Entregados", PLANO, fontSize, true);
        this.lblDatos[0][1] = new Label("0", GRUESA, fontSize);
        this.lblDatos[1][0] = new Label("Bot. Pagados", PLANO, fontSize, true);
        this.lblDatos[1][1] = new Label("0", GRUESA, fontSize);
        this.lblDatos[2][0] = new Label("Entrega a domicilio", PLANO, fontSize, true);
        this.lblDatos[2][1] = new Label("NO", GRUESA, fontSize);
        this.lblDatos[3][0] = new Label("Tipo de pago", PLANO, fontSize, true);
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
     *
     * @param alturaBaja TRUE si la altura del panel es muy baja, requiriendo
     * bajar el tamaño de la fuente de letra. FALSE si la fuente de letra se
     * puede mantener de tamaño normal
     */
    public void relocateComponents(boolean alturaBaja) {
        //Ancho y alto de los separadores
        int spWidth = this.getWidth() - padding * 2;
        int spHeight = separadores[0].getPreferredSize().height;

        //El título de la factura siempre estará en la misma posición
        this.lblTitulo.setLocation(padding, padding/2);

        //PRIMERA FILA CABECERA
        //Altura de las primeras celdas
        int cellY = lblTitulo.getHeight() + ((alturaBaja) ? padding * 3 / 2 : padding * 2);
        //Posición de la segunda celda
        int cellX = padding + 80;
        lblPersona[0][0].setLocation(padding, cellY);
        lblPersona[0][1].setLocation(cellX, cellY);
        //Reajustar el tamaño de fuente de letra
        lblPersona[0][0].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        lblPersona[0][1].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        //Reajustar el tamaño del label
        lblPersona[0][0].setSize(lblPersona[0][0].getPreferredSize());
        lblPersona[0][1].setSize(lblPersona[0][1].getPreferredSize());

        //alto de las celdas, según el tamaño del label anterior
        int cellHeight = lblPersona[0][0].getHeight();

        //SEPARADOR
        //Posicion y altura de la celda + el gap vertical
        int spY = cellY + cellHeight + gapV;
        separadores[0].setBounds(padding, spY, spWidth, spHeight);

        //SEGUNDA FILA CABECERA
        //Posición y altura del separador + el gap vertical
        cellY = spY + spHeight + ((alturaBaja) ? gapV / 2 : gapV);
        lblPersona[1][0].setLocation(padding, cellY);
        lblPersona[1][1].setLocation(cellX, cellY);
        //Reajustar el tamaño de fuente de letra
        lblPersona[1][0].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        lblPersona[1][1].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        //Reajustar el tamaño del label
        lblPersona[1][0].setSize(lblPersona[1][0].getPreferredSize());
        lblPersona[1][1].setSize(lblPersona[1][1].getPreferredSize());

        //SEPARADOR
        //Posición y altura de la última celda + un padding
        spY = cellY + cellHeight + ((alturaBaja) ? padding / 2 : padding);
        separadores[1].setBounds(padding, spY, spWidth, spHeight);

        //PRIMERA FILA DE LOS DATOS
        //Reajustar el tamaño de fuente de letra
        lblDatos[0][0].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        lblDatos[0][1].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        //Reajustar el tamaño del label
        lblDatos[0][0].setSize(lblDatos[0][0].getPreferredSize().width + 5, lblDatos[0][0].getPreferredSize().height);
        lblDatos[0][1].setSize(lblDatos[0][1].getPreferredSize());
        //Posición y altura del último separador + gap vertical
        cellY = spY + spHeight + gapV;
        //Posición de la segunda celda a la derecha de la factura
        cellX = this.getWidth() - padding - lblDatos[0][1].getWidth();
        lblDatos[0][0].setLocation(padding, cellY);
        lblDatos[0][1].setLocation(cellX, cellY);

        //SEGUNDA FILA DE LOS DATOS
        //Reajustar el tamaño de fuente de letra
        lblDatos[1][0].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        //Si el panel es de tipo compra o recarga, este dato es más pequeño de
        //lo normal, ya que representa un precio en Bs
        if (type == COMPRAS_RECARGA || type == COMPRAS_BOTELLON) {
            lblDatos[1][1].setFontSize((alturaBaja) ? smallFontSize - 2 : fontSize - 2);
        } else {
            lblDatos[1][1].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        }
        //Reajustar el tamaño del label
        lblDatos[1][0].setSize(lblDatos[1][0].getPreferredSize().width + 5, lblDatos[1][0].getPreferredSize().height);
        lblDatos[1][1].setSize(lblDatos[1][1].getPreferredSize());
        //Posición y altura de la última celda + gap vertical
        cellY = cellY + cellHeight + gapV;
        //Posición de la segunda celda a la derecha de la factura
        cellX = this.getWidth() - padding - lblDatos[1][1].getWidth();
        lblDatos[1][0].setLocation(padding, cellY);
        lblDatos[1][1].setLocation(cellX, cellY);

        //Validar si existe una tercera fila en los datos
        if (datosRows > 2) {
            //TERCERA FILA DE LOS DATOS
            //Reajustar el tamaño de fuente de letra
            lblDatos[2][0].setFontSize((alturaBaja) ? smallFontSize : fontSize);
            lblDatos[2][1].setFontSize((alturaBaja) ? smallFontSize : fontSize);
            //Reajustar el tamaño del label
            lblDatos[2][0].setSize(lblDatos[2][0].getPreferredSize().width + 5, lblDatos[2][0].getPreferredSize().height);
            lblDatos[2][1].setSize(lblDatos[2][1].getPreferredSize());
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
            //Reajustar el tamaño de fuente de letra
            lblDatos[3][0].setFontSize((alturaBaja) ? smallFontSize : fontSize);
            lblDatos[3][1].setFontSize((alturaBaja) ? smallFontSize : fontSize);
            //Reajustar el tamaño del label
            lblDatos[3][0].setSize(lblDatos[3][0].getPreferredSize().width + 5, lblDatos[3][0].getPreferredSize().height);
            lblDatos[3][1].setSize(lblDatos[3][1].getPreferredSize());
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
        //Reajustar el tamaño de fuente de letra
        lblTotal[0].setFontSize((alturaBaja) ? smallFontSize : fontSize);
        lblTotal[1].setFontSize((alturaBaja) ? smallFontSize - 2 : fontSize - 2);
        //Reajustar el tamaño del label
        lblTotal[0].setSize(lblTotal[0].getPreferredSize().width + 5, lblTotal[0].getPreferredSize().height);
        lblTotal[1].setSize(lblTotal[1].getPreferredSize());
        //Posición y altura del último separador + gap vertical
        cellY = spY + spHeight + gapV;
        //Posición de la segunda celda a la derecha de la factura
        cellX = this.getWidth() - padding - lblTotal[1].getWidth();
        lblTotal[0].setLocation(padding, cellY);
        lblTotal[1].setLocation(cellX, cellY);
    }

    //ATRIBUTOS
    private int datosRows;
    private int fontSize = 16;
    private int smallFontSize = fontSize - 2;
    private final int type;
    private static final int padding = 20;
    private static final int gapV = 4;

    //COMPONENTES
    private JSeparator[] separadores;
    private Label[][] lblPersona;
    private Label[][] lblDatos;
    private Label[] lblTotal;
    private final Label lblTitulo = new Label("Cuenta", TITULO, fontSize + 10);

}
