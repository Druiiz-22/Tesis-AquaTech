package components;

import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JPanel;
import static database.ReadDB.*;

/**
 * Clase para la creación de los paneles informativos dentro del programa
 * principal
 */
public class PanelInfo extends JPanel implements properties.Colores, properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para actualizar los datos según su tipo
     */
    public void actualizarDatos() {
        switch (type) {
            case COMPRAS:
                datosCompras();
                break;
            case VENTAS_BOTELLON:
                datosVentas();
                break;
            case VENTAS_TRASVASO:
                datosTrasvases();
                break;
        }
    }

    /**
     * Función para asignar los datos en las compras y recargas
     */
    private void datosCompras() {
        //Precio del travaso
        lblDatos[0][1].setText(getPrecioTrasvaso() + " Bs");
        //Precio de venta del botellón
        lblDatos[1][1].setText(getPrecioVenta() + " Bs");
        //Botellones en el almacen
        lblDatos[2][1].setText(String.valueOf(getBotellonesAlmacen()));
    }

    /**
     * Función para asignar los datos en las ventas
     */
    private void datosVentas() {
        //Precio del botellón
        lblDatos[0][1].setText(getPrecioVenta() + " Bs");
        //Botellones en el almacen
        lblDatos[1][1].setText(String.valueOf(getBotellonesAlmacen()));
        //Clientes que deben pagar
        lblDatos[2][1].setText(String.valueOf(getClientesPagar()));
        //Clientes que debemos entregar
        lblDatos[3][1].setText(String.valueOf(getClientesEntregar()));
    }

    /**
     * Función para asignar los datos en los trasvases
     */
    private void datosTrasvases() {
        //Precio del trasvaso
        lblDatos[0][1].setText(getPrecioTrasvaso() + " Bs");
        //Cantidad de botellones llenos
        lblDatos[1][1].setText(String.valueOf(getBotellonesLlenos()));
        //Cantidad de botellones vacíos
        lblDatos[2][1].setText(String.valueOf(getBotellonesVacios()));
        //Botellones en el almacén
        lblDatos[3][1].setText(String.valueOf(getBotellonesAlmacen()));
        //Clientes que deben pagar
        lblDatos[4][1].setText(String.valueOf(getClientesPagar()));
        //Clientes que debemos entregar
        lblDatos[5][1].setText(String.valueOf(getClientesEntregar()));

    }

    //ATRIBUTOS BACKEND
    private int type;

    // ========== FRONTEND ==========
    /**
     * Constructor del panel informativo
     *
     * @param type Tipo de información que mostrará
     */
    public PanelInfo(int type) {
        this.type = type;

        this.setLayout(null);
        this.setBackground(BLANCO);
        this.setBorder(createLineBorder(GRIS));

        this.add(lblTitulo);

        //Determinar la cantidad de datos
        //que tendrá el panel, según el tipo
        switch (type) {
            case VENTAS_TRASVASO:
                initTrasvaso();
                break;
            case VENTAS_BOTELLON:
                initVenta();
                break;
            case COMPRAS:
                initCompra();
                break;

            default:
                initAdmin();
                break;
        }
    }

    /**
     * Función para mostrar la información en la administración
     */
    private void initAdmin() {
        //Cambiar el texto del título
        lblTitulo.setText("Información");
        lblTitulo.setSize(lblTitulo.getPreferredSize());

        //Obtener la información que será mostrada
        String info = "";
        switch (type) {
            case ADMIN_AJUSTES:
                info = "<html>"
                        + "<p>"
                        + "El apartado de ajustes permite configurar datos "
                        + "del programa manualmente:"
                        + "</p>"
                        + "<ul style='list-style-type:none;margin-left:5px'>"
                        + "<li>Precio de trasvase y venta</li>"
                        + "<li>Sucursales y dispositivos registrados</li>"
                        + "</ul>"
                        + "<p>"
                        + "<b>Solo los administradores tienen permitido "
                        + "realizar los cambios.</b>"
                        + "</p><br>"
                        + "<p>"
                        + "<font color='#C00000'><b>Advertencia:</b></font> "
                        + "Los cambios aplicados, <b>se verán reflejados en "
                        + "todos los dispositivos</b> conectados a la base de "
                        + "datos local y la nube."
                        + "</p>"
                        + "</html>";
                break;

            case ADMIN_USUARIOS:
                info = "<html>"
                        + "<p>"
                        + "El apartado de usuarios permite acceder a la "
                        + "información básica de los usuarios registrados en "
                        + "el sistema, así como eliminarlos, ascender o "
                        + "revocarles sus roles."
                        + "</p><br>"
                        + "<p>"
                        + "<b>Solo los administradores tienen permitido "
                        + "realizar cambios en los usuarios</b>"
                        + "</p>"
                        + "</html>";
                break;

            case ADMIN_REPORTES:
                info = "<html>"
                        + "<p>"
                        + "El apartado de reportes permite la generación de "
                        + "un documento PDF que visualiza todos los movimientos "
                        + "realizados desde una fecha determinada."
                        + "</p><br>"
                        + "<p>"
                        + "<b>Solo los administradores tienen permitido realizar "
                        + "un reporte.</b>"
                        + "</p>"
                        + "</html>";
                break;

            case ADMIN_RESPALDO:
                info = "<html>"
                        + "<p>"
                        + "El apartado de respaldo permite realizar una "
                        + "copia de la base de datos e importar un "
                        + "respaldo almacenado."
                        + "</p><br>"
                        + "<p>"
                        + "<b>Solo los administradores tienen permitido realizar "
                        + "cambios en los usuarios.</b>"
                        + "</p><br>"
                        + "<p>"
                        + "<font color='#C00000'><b>Advertencia:</b></font> "
                        + "Importar una base de datos <b>eliminará todos los "
                        + "datos</b> en ella para importa el respaldo."
                        + "</p>"
                        + "</html>";
                break;

        }

        //Instanciar la información
        this.lblDatos = new Label[1][1];
        this.lblDatos[0][0] = new Label(info, PLANO, FONT_SIZE);
        this.lblDatos[0][0].setVerticalAlignment(javax.swing.JLabel.TOP);

        //Asignar que solo hay una celda con información
        this.infoAdmin = true;

        //Agregar el título
        this.add(this.lblTitulo);

        //Agregar el dato
        this.add(this.lblDatos[0][0]);
    }

    /**
     * Función para mostrar la información en los trasvasos
     */
    private void initTrasvaso() {
        //Declarar los componentes
        this.lblDatos = new Label[6][2];

        //Instanciar los componentes
        this.lblDatos[0][0] = new Label("Precio del trasvaso:", TITULO, FONT_SIZE);
        this.lblDatos[0][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[1][0] = new Label("Botellones llenos:", TITULO, FONT_SIZE);
        this.lblDatos[1][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[2][0] = new Label("Botellones vacíos:", TITULO, FONT_SIZE);
        this.lblDatos[2][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[3][0] = new Label("Botellones en el almacén:", TITULO, FONT_SIZE);
        this.lblDatos[3][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[4][0] = new Label("<html><center>Clientes con pagos<br>pendientes:</center></html>", TITULO, FONT_SIZE);
        this.lblDatos[4][0].setSize(this.lblDatos[4][0].getPreferredSize());
        this.lblDatos[4][0].setHorizontalTextPosition(javax.swing.JLabel.CENTER);
        this.lblDatos[4][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[5][0] = new Label("<html><center>Clientes con entregas<br>pendientes:</center></html>", TITULO, FONT_SIZE);
        this.lblDatos[5][0].setSize(this.lblDatos[5][0].getPreferredSize());
        this.lblDatos[5][0].setHorizontalTextPosition(javax.swing.JLabel.CENTER);
        this.lblDatos[5][1] = new Label("", PLANO, FONT_SIZE);

        //Asignar los tooltip text
        this.lblDatos[0][0].setToolTipText("Precio del trasvaso de botellón a los clientes");
        this.lblDatos[1][0].setToolTipText("Cantidad de botellones vacíos actualmente");
        this.lblDatos[2][0].setToolTipText("Cantidad de botellones llenos, listos para trasvases");
        this.lblDatos[3][0].setToolTipText("Cantidad de botellones que dispone el local");
        this.lblDatos[4][0].setToolTipText("<html>Cantidad de clientes que <b>NO han pagado</b> por los trasvasos de botellones</html>");
        this.lblDatos[5][0].setToolTipText("<html>Cantidad de clientes que <b>la empresa les debe</b> recargar sus botellones</html>");

        //Obtener la cantidad de filas
        this.datosRow = this.lblDatos.length;

        //Agregar los datos
        for (Label[] row : lblDatos) {
            for (Label dato : row) {
                this.add(dato);
            }
        }
    }

    /**
     * Función para mostrar la información en las ventas
     */
    private void initVenta() {
        //Declarar los componentes
        this.lblDatos = new Label[4][2];

        //Instanciar los componentes
        this.lblDatos[0][0] = new Label("Precio del botellón:", TITULO, FONT_SIZE);
        this.lblDatos[0][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[1][0] = new Label("Botellones en el almacén:", TITULO, FONT_SIZE);
        this.lblDatos[1][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[2][0] = new Label("<html><center>Clientes con pagos<br>pendientes:</center></html>", TITULO, FONT_SIZE);
        this.lblDatos[2][0].setSize(this.lblDatos[2][0].getPreferredSize());
        this.lblDatos[2][0].setHorizontalTextPosition(javax.swing.JLabel.CENTER);
        this.lblDatos[2][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[3][0] = new Label("<html><center>Clientes con entregas<br>pendientes:</center></html>", TITULO, FONT_SIZE);
        this.lblDatos[3][0].setSize(this.lblDatos[3][0].getPreferredSize());
        this.lblDatos[3][0].setHorizontalTextPosition(javax.swing.JLabel.CENTER);
        this.lblDatos[3][1] = new Label("", PLANO, FONT_SIZE);

        //Asignar los tooltip text
        this.lblDatos[0][0].setToolTipText("Precio de la venta de un botellón nuevo a los clientes");
        this.lblDatos[1][0].setToolTipText("Cantidad de botellones que dispone el local");
        this.lblDatos[2][0].setToolTipText("<html>Cantidad de clientes que <b>NO han pagado</b> por los trasvasos de botellones</html>");
        this.lblDatos[3][0].setToolTipText("<html>Cantidad de clientes que <b>la empresa les debe</b> recargar sus botellones</html>");

        //Obtener la cantidad de filas
        this.datosRow = this.lblDatos.length;

        //Agregar los datos
        for (Label[] row : lblDatos) {
            for (Label dato : row) {
                this.add(dato);
            }
        }
    }

    /**
     * Función para mostrar la información en las compras y recargas
     */
    private void initCompra() {
        //Declarar los componentes
        this.lblDatos = new Label[3][2];

        //Instanciar los componentes
        this.lblDatos[0][0] = new Label("Precio del trasvaso:", TITULO, FONT_SIZE);
        this.lblDatos[0][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[1][0] = new Label("Precio del botellón:", TITULO, FONT_SIZE);
        this.lblDatos[1][1] = new Label("", PLANO, FONT_SIZE);
        this.lblDatos[2][0] = new Label("Botellón en el almacén:", TITULO, FONT_SIZE);
        this.lblDatos[2][1] = new Label("", PLANO, FONT_SIZE);

        //Asignar los tooltip text
        this.lblDatos[0][0].setToolTipText("Precio del trasvaso de botellón a los clientes");
        this.lblDatos[1][0].setToolTipText("Precio de la venta de un botellón nuevo a los clientes");
        this.lblDatos[2][0].setToolTipText("Cantidad de botellones que dispone el local");

        //Obtener la cantidad de filas
        this.datosRow = this.lblDatos.length;

        //Agregar los datos
        for (Label[] row : lblDatos) {
            for (Label dato : row) {
                this.add(dato);
            }
        }
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     *
     * @param ancho PANEL_MEDIANO si el ancho del panel de información equivale
     * a un panel. PANEL_GRANDE si el ancho del panel de informacion equivale a
     * dos paneles.
     */
    public void relocateComponents(int ancho) {

        int width = this.getWidth();

        //Posicionar el título
        int tituloX = width / 2 - this.lblTitulo.getWidth() / 2;
        this.lblTitulo.setLocation(tituloX, PADDING * 2);

        //Validar que SOLO exista una celda
        if (infoAdmin) {
            //Posicionar la información
            int datoY = PADDING * 4 + lblTitulo.getHeight();
            int datoWidth = width - PADDING * 4;
            int datoHeight = this.getHeight() - datoY - PADDING * 2;
            this.lblDatos[0][0].setBounds(PADDING * 2, datoY, datoWidth, datoHeight);

        } else {
            //PANEL PEQUEÑO Y MEDIANO
            if (ancho == PANEL_MEDIANO) {
                panelPequeño();

            } else if (ancho == PANEL_GRANDE) {
                panelGrande();
            }
        }

    }

    /**
     * Función para posicionar los elementos cuando el panel sea de tamaño
     * pequeño, es decir, cuando tenga un ancho de 300px o un ancho equivalente
     * a otro panel.
     */
    private void panelPequeño() {
        //Posicionar todos los datos uno debajo del
        //otro y centrados horizontalmente

        //PRIMER TÍTULO DEL DATO
        int middleX = this.getWidth() / 2;
        int datoY = PADDING * 4 + lblTitulo.getHeight();
        int datoX = middleX - lblDatos[0][0].getWidth() / 2;
        lblDatos[0][0].setLocation(datoX, datoY);
        //PRIMER DATO
        lblDatos[0][1].setSize(lblDatos[0][1].getPreferredSize());
        int datoHeight = lblDatos[0][0].getHeight();
        datoY = datoY + datoHeight;
        datoX = middleX - lblDatos[0][1].getWidth() / 2;
        lblDatos[0][1].setLocation(datoX, datoY);

        //SEGUNDO TÍTULO DEL DATO
        datoHeight = lblDatos[0][0].getHeight();
        datoY = datoY + datoHeight + PADDING;
        datoX = middleX - lblDatos[1][0].getWidth() / 2;
        lblDatos[1][0].setLocation(datoX, datoY);
        //SEGUNDO DATO
        lblDatos[1][1].setSize(lblDatos[1][1].getPreferredSize());
        datoHeight = lblDatos[1][0].getHeight();
        datoY = datoY + datoHeight;
        datoX = middleX - lblDatos[1][1].getWidth() / 2;
        lblDatos[1][1].setLocation(datoX, datoY);

        //TERCER TÍTULO DEL DATO
        datoHeight = lblDatos[1][1].getHeight();
        datoY = datoY + datoHeight + PADDING;
        datoX = middleX - lblDatos[2][0].getWidth() / 2;
        lblDatos[2][0].setLocation(datoX, datoY);
        //TERCER DATO
        lblDatos[2][1].setSize(lblDatos[2][1].getPreferredSize());
        datoHeight = lblDatos[2][0].getHeight();
        datoY = datoY + datoHeight;
        datoX = middleX - lblDatos[2][1].getWidth() / 2;
        lblDatos[2][1].setLocation(datoX, datoY);

        //Validar que hayan más de 3 filas
        if (datosRow > 3) {
            //CUARTO TÍTULO DEL DATO
            datoHeight = lblDatos[2][1].getHeight();
            datoY = datoY + datoHeight + PADDING;
            datoX = middleX - lblDatos[3][0].getWidth() / 2;
            lblDatos[3][0].setLocation(datoX, datoY);
            //CUARTO DATO
            lblDatos[3][1].setSize(lblDatos[3][1].getPreferredSize());
            datoHeight = lblDatos[3][0].getHeight();
            datoY = datoY + datoHeight;
            datoX = middleX - lblDatos[3][1].getWidth() / 2;
            lblDatos[3][1].setLocation(datoX, datoY);
        }

        //Validar que hayan más de 4 filas
        if (datosRow > 4) {
            //QUINTO TÍTULO DEL DATO
            datoHeight = lblDatos[3][1].getHeight();
            datoY = datoY + datoHeight + PADDING;
            datoX = middleX - lblDatos[4][0].getWidth() / 2;
            lblDatos[4][0].setLocation(datoX, datoY);
            //QUINTO DATO
            lblDatos[4][1].setSize(lblDatos[4][1].getPreferredSize());
            datoHeight = lblDatos[4][0].getHeight();
            datoY = datoY + datoHeight;
            datoX = middleX - lblDatos[4][1].getWidth() / 2;
            lblDatos[4][1].setLocation(datoX, datoY);

            //SEXTO TÍTULO DEL DATO
            datoHeight = lblDatos[4][1].getHeight();
            datoY = datoY + datoHeight + PADDING;
            datoX = middleX - lblDatos[5][0].getWidth() / 2;
            lblDatos[5][0].setLocation(datoX, datoY);
            //SEXTO DATO
            lblDatos[5][1].setSize(lblDatos[5][1].getPreferredSize());
            datoHeight = lblDatos[5][0].getHeight();
            datoY = datoY + datoHeight;
            datoX = middleX - lblDatos[5][1].getWidth() / 2;
            lblDatos[5][1].setLocation(datoX, datoY);
        }
    }

    /**
     * Función para posicionar los elementos cuando el panel sea de tamaño
     * grande, es decir, cuando tenga un ancho equivalente a dos paneles
     */
    private void panelGrande() {

        int tituloH = lblTitulo.getHeight();

        int firstColumn = this.getWidth() * 1 / 3;
        int secondColumn = this.getWidth() * 2 / 3;

        int firstRow = tituloH + PADDING * 4;

        int datoY;
        int datoX;

        //Contar la cantidad de filas que existan
        switch (datosRow) {

            //Validar que hayan solo 3 filas
            case 3:

                //PRIMER TÍTULO DEL DATO
                datoX = firstColumn - lblDatos[0][0].getWidth() / 2;
                lblDatos[0][0].setLocation(datoX, firstRow);
                //PRIMER DATO
                lblDatos[0][1].setSize(lblDatos[0][1].getPreferredSize());
                datoX = firstColumn - lblDatos[0][1].getWidth() / 2;
                datoY = firstRow + lblDatos[0][0].getHeight();
                lblDatos[0][1].setLocation(datoX, datoY);

                //SEGUNDO TÍTULO DEL DATO
                datoX = secondColumn - lblDatos[1][0].getWidth() / 2;
                lblDatos[1][0].setLocation(datoX, firstRow);
                //SEGUNDO DATO
                lblDatos[1][1].setSize(lblDatos[1][1].getPreferredSize());
                datoX = secondColumn - lblDatos[1][1].getWidth() / 2;
                datoY = firstRow + lblDatos[1][0].getHeight();
                lblDatos[1][1].setLocation(datoX, datoY);

                //TERCER TÍTULO DEL DATO
                int middleX = this.getWidth() / 2;
                datoX = middleX - lblDatos[2][0].getWidth() / 2;
                datoY = datoY + lblDatos[1][1].getHeight() + PADDING;
                lblDatos[2][0].setLocation(datoX, datoY);
                //TERCER DATO
                lblDatos[2][1].setSize(lblDatos[2][1].getPreferredSize());
                datoX = middleX - lblDatos[2][1].getWidth() / 2;
                datoY = datoY + lblDatos[2][0].getHeight();
                lblDatos[2][1].setLocation(datoX, datoY);
                break;

            //Validar que hayan solo 4 filas
            case 4:
                //PRIMER TÍTULO DEL DATO
                datoX = firstColumn - lblDatos[0][0].getWidth() / 2 - PADDING;
                lblDatos[0][0].setLocation(datoX, firstRow);
                //PRIMER DATO
                lblDatos[0][1].setSize(lblDatos[0][1].getPreferredSize());
                datoX = firstColumn - lblDatos[0][1].getWidth() / 2 - PADDING;
                datoY = firstRow + lblDatos[0][0].getHeight();
                lblDatos[0][1].setLocation(datoX, datoY);

                //SEGUNDO TÍTULO DEL DATO
                datoX = secondColumn - lblDatos[1][0].getWidth() / 2 + PADDING;
                lblDatos[1][0].setLocation(datoX, firstRow);
                //SEGUNDO DATO
                lblDatos[1][1].setSize(lblDatos[1][1].getPreferredSize());
                datoX = secondColumn - lblDatos[1][1].getWidth() / 2 + PADDING;
                datoY = firstRow + lblDatos[1][0].getHeight();
                lblDatos[1][1].setLocation(datoX, datoY);

                //TERCER TÍTULO DEL DATO
                datoX = firstColumn - lblDatos[2][0].getWidth() / 2 - PADDING;
                datoY = lblDatos[0][1].getY() + lblDatos[0][1].getHeight() + PADDING;
                lblDatos[2][0].setLocation(datoX, datoY);
                //TERCER DATO
                lblDatos[2][1].setSize(lblDatos[2][1].getPreferredSize());
                datoX = firstColumn - lblDatos[2][1].getWidth() / 2 - PADDING;
                datoY = datoY + lblDatos[2][0].getHeight();
                lblDatos[2][1].setLocation(datoX, datoY);

                //CUARTO TÍTULO DEL DATO
                datoX = secondColumn - lblDatos[3][0].getWidth() / 2 + PADDING;
                datoY = lblDatos[1][1].getY() + lblDatos[1][1].getHeight() + PADDING;
                lblDatos[3][0].setLocation(datoX, datoY);
                //CUARTO DATO
                lblDatos[3][1].setSize(lblDatos[3][1].getPreferredSize());
                datoX = secondColumn - lblDatos[3][1].getWidth() / 2 + PADDING;
                datoY = datoY + lblDatos[3][0].getHeight();
                lblDatos[3][1].setLocation(datoX, datoY);
                break;

            //Validar que hayan solo 6 filas
            case 6:

                //PRIMER TÍTULO DEL DATO
                datoX = firstColumn - lblDatos[0][0].getWidth() / 2 - PADDING;
                lblDatos[0][0].setLocation(datoX, firstRow);
                //PRIMER DATO
                lblDatos[0][1].setSize(lblDatos[0][1].getPreferredSize());
                datoX = firstColumn - lblDatos[0][1].getWidth() / 2 - PADDING;
                datoY = firstRow + lblDatos[0][0].getHeight();
                lblDatos[0][1].setLocation(datoX, datoY);

                //SEGUNDO TÍTULO DEL DATO
                datoX = secondColumn - lblDatos[1][0].getWidth() / 2 + PADDING;
                lblDatos[1][0].setLocation(datoX, firstRow);
                //SEGUNDO DATO
                lblDatos[1][1].setSize(lblDatos[1][1].getPreferredSize());
                datoX = secondColumn - lblDatos[1][1].getWidth() / 2 + PADDING;
                datoY = firstRow + lblDatos[1][0].getHeight();
                lblDatos[1][1].setLocation(datoX, datoY);

                //TERCER TÍTULO DEL DATO
                datoX = firstColumn - lblDatos[2][0].getWidth() / 2 - PADDING;
                datoY = lblDatos[0][1].getY() + lblDatos[0][1].getHeight() + PADDING;
                lblDatos[2][0].setLocation(datoX, datoY);
                //TERCER DATO
                lblDatos[2][1].setSize(lblDatos[2][1].getPreferredSize());
                datoX = firstColumn - lblDatos[2][1].getWidth() / 2 - PADDING;
                datoY = datoY + lblDatos[2][0].getHeight();
                lblDatos[2][1].setLocation(datoX, datoY);

                //CUARTO TÍTULO DEL DATO
                datoX = secondColumn - lblDatos[3][0].getWidth() / 2 + PADDING;
                datoY = lblDatos[1][1].getY() + lblDatos[1][1].getHeight() + PADDING;
                lblDatos[3][0].setLocation(datoX, datoY);
                //CUARTO DATO
                lblDatos[3][1].setSize(lblDatos[3][1].getPreferredSize());
                datoX = secondColumn - lblDatos[3][1].getWidth() / 2 + PADDING;
                datoY = datoY + lblDatos[3][0].getHeight();
                lblDatos[3][1].setLocation(datoX, datoY);

                //QUINTO TÍTULO DEL DATO
                datoX = firstColumn - lblDatos[4][0].getWidth() / 2 - PADDING;
                datoY = lblDatos[2][1].getY() + lblDatos[2][1].getHeight() + PADDING;
                lblDatos[4][0].setLocation(datoX, datoY);
                //QUINTO DATO
                lblDatos[4][1].setSize(lblDatos[4][1].getPreferredSize());
                datoX = firstColumn - lblDatos[4][1].getWidth() / 2 - PADDING;
                datoY = datoY + lblDatos[4][0].getHeight();
                lblDatos[4][1].setLocation(datoX, datoY);

                //SEXTO TÍTULO DEL DATO
                datoX = secondColumn - lblDatos[5][0].getWidth() / 2 + PADDING;
                datoY = lblDatos[3][1].getY() + lblDatos[3][1].getHeight() + PADDING;
                lblDatos[5][0].setLocation(datoX, datoY);
                //SEXTO DATO
                lblDatos[5][1].setSize(lblDatos[5][1].getPreferredSize());
                datoX = secondColumn - lblDatos[5][1].getWidth() / 2 + PADDING;
                datoY = datoY + lblDatos[5][0].getHeight();
                lblDatos[5][1].setLocation(datoX, datoY);

                break;
        }
    }

    //ATRIBUTOS FRONTEND
    private static final int FONT_SIZE = 16;
    private static final int PADDING = 10;

    //COMPONENTES
    private Label lblTitulo = new Label("Información General", TITULO, 24);
    private Label lblDatos[][];
    private int datosRow;
    private boolean infoAdmin = false;
}
