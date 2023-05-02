package tabs.ventas;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.PanelFactura;
import components.PanelInfo;
import database.CreateDB;
import database.ReadDB;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import static javax.swing.BorderFactory.createLineBorder;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNoWarning;
import static properties.ValidarTexto.teclaSuelta;
import static main.MenuLateral.clickButton;

/**
 * Clase para la creación del panel para el registro de trasvases de botellones
 * a los clientes
 */
public class Trasvasos extends JPanel implements properties.Colores, properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para validar los campos, sus datos y registrar los datos en la
     * base de datos
     */
    private void registrar() {

        if (validarCampos()) {
            if (validarDatos()) {

                if (msjYesNo("¿Está seguro de realizar el registro del trasvaso?")) {

                    //Validar que el registro NO sea de números más grandes de 100
                    if (entregados > 100 || pagados > 100) {

                        //En caso de ser alguno mayor de 100, lanzar un mensaje de alerta
                        String msj = "<html>Está apunto de realizar un registro con una <b>alta</b><br>"
                                + "<b>cantidad de botellones</b>, ¿Está seguro de realizar el registro?</html>";

                        if (msjYesNoWarning(msj)) {
                            CreateDB.createTravaso(entregados, pagados, tipoPago, checkDelivery.isSelected(), cedula);
                        }
                    } else {
                        CreateDB.createTravaso(entregados, pagados, tipoPago, checkDelivery.isSelected(), cedula);
                    }

                    vaciarCampos();
                }
            }
        }
    }

    /**
     * Función para comprobar que los campos NO estén vacíos y se haya
     * seleccionado un método de pago del cliente
     *
     * @return TRUE en caso de que todos los campos estén validos
     */
    private boolean validarCampos() {

        //Validar que los campos NO estén vacíos y que 
        //se haya seleccionado un tipo de pago
        if (!txtEntregados.getText().trim().isEmpty()) {
            if (!txtPagados.getText().trim().isEmpty()) {
                if (boxTipoPago.getSelectedIndex() > 0) {
                    if (!cedula.isEmpty() && !apellido.isEmpty()) {
                        return true;

                    } else {
                        msjError("Debe seleccionar un cliente");
                    }
                } else {
                    msjError("Debe seleccionar un tipo de pago");
                    boxTipoPago.requestFocus();
                }
            } else {
                msjError("La cantidad de botellones pagados no puede estár vacía.");
                txtPagados.requestFocus();
            }
        } else {
            msjError("La cantidad de botellones entregados no puede estár vacío.");
            txtEntregados.requestFocus();
        }

        //Retornar falso en caso de no retornar true anteriormente
        return false;
    }

    /**
     * Función para validar que los datos ingresados en los campos sean válidos
     *
     * @return TRUE en caso de que los datos sean válidos
     */
    private boolean validarDatos() {

        //Validar que alguno de los dos tenga un valor
        if (entregados > 0 || pagados > 0) {

            //Validar que los dos estén en un rango correcto
            if (entregados >= 0 && entregados < 8388607) {
                if (pagados >= 0 && pagados < 8388607) {

                    return true;

                } else {
                    msjError("La cantidad de botellones pagados es inválido."
                            + "\nPor favor, verifique la cantidad.");
                    txtPagados.requestFocus();
                }
            } else {
                msjError("La cantidad de botellones entregados es inválido."
                        + "\nPor favor, verifique la cantidad.");
                txtEntregados.requestFocus();
            }
        } else {
            msjError("Los dos campos de entregados y pagados no pueden estar vacíos."
                    + "\nPor favor, verifique la cantidad.");
            txtEntregados.requestFocus();
        }
        //Retornar falso en caso de no haber retornado true anteriormente
        return false;
    }

    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void listeners() {
        //MOUSE LISTENERS
        btnAceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                registrar();
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Mensaje de confirmación
                String msj = "¿Está seguro de cancelar el trasvaso?\nSe vaciarán los campos y los datos.";
                if (msjYesNo(msj)) {

                    vaciarCampos();
                }
            }
        });
        btnCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(CLIENTES);
            }
        });

        //KEY LISTENERS
        txtEntregados.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                //Retornar el valor convertido en entero, en caso de 
                //que sea válido la conversion.
                entregados = teclaSuelta(txtEntregados.getText());

                //Actualizar la cantidad en la factura
                factura.setBotellonesEntregados(entregados);
            }
        });
        txtPagados.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                //Retornar el valor convertido en entero, en caso de 
                //que sea válido la conversion.
                pagados = teclaSuelta(txtPagados.getText());

                //Actualizar la cantidad en la factura
                factura.setBotellonesPagados(pagados);
            }
        });

        //ACTION LISTENER
        checkDelivery.addActionListener((ActionEvent e) -> {
            //Enviar a factura el estado del check button cuando
            //este sea accionado.
            factura.setDelivery(checkDelivery.isSelected());
        });

        //ITEM LISTENER
        boxTipoPago.addItemListener((ItemEvent e) -> {
            //Obtener el index del item seleccionado
            int index = boxTipoPago.getSelectedIndex();

            //Validar el tipo de pago seleccionado
            switch (index) {
                case 1:
                    tipoPago = "EFECT";
                    factura.setTipoPago(tipoPago);
                    break;
                case 2:
                    tipoPago = "TRNSF";
                    factura.setTipoPago(tipoPago);
                    break;
                case 3:
                    tipoPago = "DOLAR";
                    factura.setTipoPago(tipoPago);
                    break;
                default:
                    tipoPago = "";
                    factura.setTipoPago(tipoPago);
                    break;
            }
        });
    }

    /**
     * Función para actualizar los datos del panel de información cada vez que
     * se visualice el panel de trasvasos
     */
    protected void actualizarDatos() {
        informacion.actualizarDatos();

        precio = ReadDB.getPrecioTrasvaso();

        //Reposicionar el panel de información, según el 
        //ancho del contenedor
        if (width < 600) {

            informacion.relocateComponents(PANEL_MEDIANO);

        } else if (width < 900) {

            informacion.relocateComponents(PANEL_GRANDE);

        } else if (width >= 900) {

            informacion.relocateComponents(PANEL_MEDIANO);
        }
    }

    /**
     * Función para asignar el cliente seleccionado en la factura de trasvasos
     *
     * @param ci Cédula del cliente seleccionado
     * @param apellido Apellido del cliente seleccionado
     */
    public static void setCliente(String ci, String apellido) {
        Trasvasos.cedula = ci;
        Trasvasos.apellido = apellido;

        //Actualizar la factura
        factura.setInformacion(cedula, apellido);
    }

    /**
     * Función para asignar los datos para pagar una deuda con un cliente
     *
     * @param cedula
     * @param apellido
     * @param pagar
     * @param entregar
     */
    public static void pagarDeuda(String cedula, String apellido, int pagar, int entregar) {
        Trasvasos.cedula = cedula;
        Trasvasos.apellido = apellido;
        Trasvasos.pagados = pagar;
        Trasvasos.entregados = entregar;

        //Poner el valor en los campos
        txtEntregados.setText(String.valueOf(Trasvasos.entregados));
        txtPagados.setText(String.valueOf(Trasvasos.pagados));

        //Actualizar la factura
        factura.setInformacion(cedula, apellido);
        factura.setBotellonesEntregados(Trasvasos.entregados);
        factura.setBotellonesPagados(Trasvasos.pagados);
    }

    //ATRIBUTOS BACKEND
    private static int entregados = 0, pagados = 0;
    private static double precio = 0;
    private static String tipoPago = "", cedula = "", apellido = "";

    // ========== FRONTEND ==========
    /**
     * Constructor del panel de trasvasos de botellones
     */
    public Trasvasos() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {

        //Fuente de letra para el combo box
        boxTipoPago.setFont(segoe(16, PLANO));

        //Check button para las entregas a domicilio
        checkDelivery.setOpaque(false);
        checkDelivery.setFont(segoe(18, PLANO));

        //Asignar los Tooltip text a los labels
        lblEntregados.setToolTipText(
                "<html>"
                + "Cantidad de botellones que se le <b>trasvasaron al cliente.</b><br>"
                + "El límite es de 8.388.606."
                + "</html>"
        );
        lblPagados.setToolTipText(
                "<html>"
                + "Cantidad de botellones que el cliente <b>confirmó el pago.</b><br>"
                + "El límite es de 8.388.606."
                + "</html>"
        );
        lblTipoPago.setToolTipText("Tipo de pago realizado por el cliente");
        checkDelivery.setToolTipText("Determinar si la entrega se hará en el local o entrega a domicilio");

        //Asignar las propiedades al panel de trasvaso
        panelTrasvaso.setBorder(createLineBorder(GRIS));
        panelTrasvaso.setBackground(BLANCO);

        //Agregar los componentes al panel de trasvasos
        panelTrasvaso.add(lblTitulo);
        panelTrasvaso.add(lblEntregados);
        panelTrasvaso.add(txtEntregados);
        panelTrasvaso.add(lblPagados);
        panelTrasvaso.add(txtPagados);
        panelTrasvaso.add(lblTipoPago);
        panelTrasvaso.add(boxTipoPago);
        panelTrasvaso.add(checkDelivery);
        panelTrasvaso.add(btnCancelar);
        panelTrasvaso.add(btnAceptar);

        //Actualizar la información
        informacion.actualizarDatos();

        this.add(btnCliente);
        this.add(factura);
        this.add(panelTrasvaso);
        this.add(informacion);
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     *
     * @param width Ancho del parent contenedor
     * @param height Altura del parent contenedor
     */
    public void relocateComponents(int width, int height) {

        this.width = width;

        //Altura de la factura (solo usado
        //en el panel pequeño)
        facY = padding * 2 + btnHeight;
        //Altura de la factura
        facHeight = height - btnHeight - padding * 3;
        //Altura del trasvaso y del panel
        //info (solo en el panel pequeño y grande)
        trasvHeight = height - padding * 2;

        //Posición del botón y factura
        btnCliente.setLocation(padding, padding);
        factura.setLocation(padding, facY);

        //PANEL PEQUEÑO
        if (width < 600) {

            //Cambiar el tamaño de la factura
            facHeight = 320;
            panelPequenio();
            informacion.relocateComponents(PANEL_MEDIANO);

            //PANEL MEDIANO
        } else if (width < 900) {

            panelMediano();
            informacion.relocateComponents(PANEL_GRANDE);

            //PANEL GRANDE
        } else if (width >= 900) {

            //Asignar el tamaño del parent contenedor como
            //el tamaño preferido del panel
            this.setPreferredSize(new Dimension(width, height));

            panelGrande();

            informacion.relocateComponents(PANEL_MEDIANO);
        }

        //Reposicionar los elementos de la factura
        factura.relocateComponents(facHeight < 281);
        relocateTrasvaso();
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño menor a 600 px
     */
    private void panelPequenio() {

        //Tamaño del botón y factura
        int panelWidth = width - padding * 2;
        btnCliente.setSize(panelWidth, btnHeight);

        factura.setSize(panelWidth, facHeight);

        //Altura de la factura, sumando la altura de la factura, su
        //alto y un padding
        int trasvY = facY + facHeight + padding;
        //Asignar la posición y tamaño
        panelTrasvaso.setBounds(padding, trasvY, panelWidth, trasvHeight);

        //Altura de la información, sumando la altura del panel de
        //trasvaso, su alto y un padding
        int infoY = trasvY + trasvHeight + padding;
        int infoHeight = 456;
        //Asignar la posición y tamaño
        informacion.setBounds(padding, infoY, panelWidth, infoHeight);

        //Calcular la altura absoluta del panel contenedor, sumando cinco
        //padding, la altura del botón, la altura de la factura y dos 
        //veces la altura del panel de trasvaso
        int absoluteHeight = padding * 5 + btnHeight + facHeight + trasvHeight + infoHeight;

        //Asignar el tamaño preferido al panel
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 600px, pero menor a
     * 900 px
     */
    private void panelMediano() {

        this.facHeight += 20;
        this.trasvHeight += 20;
        //Dividir el panel en dos 
        int halfWidth = width / 2 - padding * 2;

        //Comprobar que la mitad del panel NO supere el ancho
        //máximo posible del botón y factura
        int clienteWidth = (halfWidth > facMaxWidth) ? facMaxWidth : halfWidth;
        btnCliente.setSize(clienteWidth, btnHeight);
        factura.setSize(clienteWidth, facHeight);

        //Posicionar el panel de trasvaso después de la factura
        int trasvX = padding * 2 + clienteWidth;
        //Obtener el ancho del panel, menos la posición en X del panel
        //de trasvaso y restarle un padding
        int trasvWidth = width - trasvX - padding;
        //Asignar la posición y el tamaño
        panelTrasvaso.setBounds(trasvX, padding, trasvWidth, trasvHeight);

        //Posicionar el panel info debajo del panel de trasvaso
        int infoY = padding * 2 + trasvHeight;
        //Asignarle todo el ancho del panel
        int infoWidth = width - padding * 2;
        //Asignarle la altura estática
        int infoHeight = 280;
        //Asignar la posición y el tamaño
        informacion.setBounds(padding, infoY, infoWidth, infoHeight);

        //Calcular la altura absoluta del panel contenedor, sumando
        //tres padding, la altura del panel de trasvaso y la altura
        //del panel de info
        int absoluteHeight = padding * 3 + trasvHeight + infoHeight;

        //Asignar el tamaño preferido al panel
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 900 px
     */
    private void panelGrande() {

        this.width += 15;
        this.facHeight += 15;
        this.trasvHeight += 15;

        //Dividir el panel en 3 partes iguales
        int thirdWidth = width / 3 - padding * 2;

        //Comprobar que el tercio del panel NO supere el 
        //ancho máximo posible del botón y factura
        int facWidth = (thirdWidth > facMaxWidth) ? facMaxWidth : thirdWidth;
        btnCliente.setSize(facWidth, btnHeight);
        factura.setSize(facWidth, facHeight);

        //Calcular la posición del panel info a la derecha del panel,
        //obteniendo el ancho del panel, menos el ancho máximo para
        //el panel info y restarle un padding
        int infoX = width - facWidth - padding;
        //Asignar la posición y el tamaño
        informacion.setBounds(infoX, padding, facWidth, trasvHeight);

        //Calcular la posición en X del panel trasvaso, sumando
        //el ancho de la factura y dos padding
        int trasvX = facWidth + padding * 2;
        //Calcular el ancho del panel trasvaso, obteniendo el ancho del
        //panel, menos el ancho de la factura y del panel info, y
        //restandole cuatro paddings
        int trasvWidth = (width - facWidth * 2) - padding * 4;
        panelTrasvaso.setBounds(trasvX, padding, trasvWidth, trasvHeight);
    }

    /**
     * Función para posicionar los componentes del panel de trasvasos
     */
    private void relocateTrasvaso() {
        boolean alturaBaja = trasvHeight < 410;
        int txtHeight = (alturaBaja) ? 30 : 40;
        int gapV = 5;
        int trasW = panelTrasvaso.getWidth();
        int txtWidth = trasW - padding * 2;

        //Asignar el tamaño a los componentes que requieran
        //declarar sus tamaños
        txtEntregados.setSize(txtWidth, txtHeight);
        txtPagados.setSize(txtWidth, txtHeight);
        boxTipoPago.setSize(txtWidth, txtHeight);
        checkDelivery.setSize(checkDelivery.getPreferredSize());

        //Posición del título del panel para los datos
        lblTitulo.setLocation(padding, padding / 2);

        //Para posicionar los campos en el centro vertical del panel
        //primero se obtiene le punto medio del panel
        int middleY = panelTrasvaso.getHeight() / 2;
        //Luego la suma de la altura de TODOS los componentes y sus labels
        int allHeights = txtHeight * 3 + checkDelivery.getHeight() + lblEntregados.getHeight() * 3;
        //Finalmente calcular el punto medio, sumando, además, los padding utilizados 
        int positionY = middleY - (allHeights + ((alturaBaja)? padding/2 : padding) * 5) / 2;
        lblEntregados.setLocation(padding, positionY);

        //Posición vertical para el primer campo de texto
        positionY = positionY + lblEntregados.getHeight() + ((alturaBaja)? gapV/2 : gapV);
        txtEntregados.setLocation(padding, positionY);

        //Posición vertical del segundo label para su campo de texto
        positionY = positionY + txtHeight + ((alturaBaja)? padding/2 : padding);
        lblPagados.setLocation(padding, positionY);

        //Posición vertical para el segundo campo de texto
        positionY = positionY + lblPagados.getHeight() + ((alturaBaja)? gapV/2 : gapV);
        txtPagados.setLocation(padding, positionY);

        //Posición vertical del label para el comboBox
        positionY = positionY + txtHeight + ((alturaBaja)? padding/2 : padding);
        lblTipoPago.setLocation(padding, positionY);

        //Posición vertical para el comboBox
        positionY = positionY + lblTipoPago.getHeight() + ((alturaBaja)? gapV/2 : gapV);
        boxTipoPago.setLocation(padding, positionY);

        //Posición vertical y horizontal para el checkBox
        int positionX = trasW / 2 - checkDelivery.getWidth() / 2;
        positionY = positionY + txtHeight + ((alturaBaja)? padding/2 : padding);
        checkDelivery.setLocation(positionX, positionY);

        //Posición vertical de los dos botones inferiores
        txtHeight += 10;
        positionY = panelTrasvaso.getHeight() - txtHeight - padding;

        //Ancho del botón de cancelar
        int btnW = btnCancelar.getPreferredSize().width + 30;
        btnCancelar.setSize(btnW, txtHeight);
        btnCancelar.setLocation(padding, positionY);

        //Ancho del botón de aceptar
        positionX = padding * 2 + btnW;
        btnW = trasW - btnCancelar.getWidth() - padding * 3;
        btnAceptar.setSize(btnW, txtHeight);
        btnAceptar.setLocation(positionX, positionY);
    }

    /**
     * Función para vaciar los campos del panel de trasvasos
     */
    protected void vaciarCampos() {
        //Restablecer los campos
        txtEntregados.setText("");
        txtPagados.setText("");
        checkDelivery.setSelected(false);
        boxTipoPago.setSelectedIndex(0);

        //Restablecer la factura
        factura.setBotellonesEntregados(0);
        factura.setBotellonesPagados(0);
        factura.setDelivery(false);
        factura.setTipoPago("");
        factura.setMontoTotal(0.00);
        factura.setInformacion("", "");

        //Vaciar los atributos
        entregados = 0;
        pagados = 0;
        tipoPago = "";
        cedula = "";
        apellido = "";
    }

    //ATRIBUTOS FRONTEND
    private int width, facHeight, trasvHeight, facY;
    private final int padding = 20;
    private final int btnHeight = 80;
    private final int facMaxWidth = 300;
    private static final int labelFontSize = 18;

    //COMPONENTES
    private static final Boton btnCliente = new Boton(SELECT_CLIENTE);
    private static final PanelFactura factura = new PanelFactura(VENTAS_TRASVASO);
    private static final JPanel panelTrasvaso = new JPanel(null);
    private static final PanelInfo informacion = new PanelInfo(VENTAS_TRASVASO);

    private static final Label lblTitulo = new Label("Trasvaso de botellones", TITULO, 24);

    private static final Label lblEntregados = new Label("botellones entregados", PLANO, labelFontSize, true);
    private static final CampoTexto txtEntregados = new CampoTexto("Botellones entregados", NUMERO);

    private static final Label lblPagados = new Label("botellones pagados", PLANO, labelFontSize, true);
    private static final CampoTexto txtPagados = new CampoTexto("Botellones pagados", NUMERO);

    private static final Label lblTipoPago = new Label("Tipo de pago", PLANO, labelFontSize, true);
    private static final String opciones[] = {"Seleccionar", "Efectivo", "Transferencia", "Dolar en efectivo"};
    private static final JComboBox boxTipoPago = new JComboBox(opciones);

    private static final JCheckBox checkDelivery = new JCheckBox("Entrega domicilio");

    private static final Boton btnAceptar = new Boton("Registrar", VERDE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);
}
