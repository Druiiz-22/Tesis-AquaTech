package tabs.compras;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.PanelFactura;
import components.PanelInfo;
import database.RegistrarDB;
import java.awt.Cursor;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.BorderFactory.createLineBorder;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjYesNoWarning;
import static properties.ValidarTexto.teclaSuelta;
import static properties.ValidarTexto.teclaSueltaDoble;
import static main.MenuLateral.clickButton;

/**
 * Función para la creación del apartado de las compras, esta clase contiene el
 * menú de navegación superior y el panel contenedor
 */
public class Compras extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del apartado de compras
     */
    public Compras() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        mouseListeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        //Activar el botón
        btnRecargas.setForeground(AZUL_PRINCIPAL);

        //Asignar el cursor de mano
        btnRecargas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCompras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Agregar el tooltiptext
        btnRecargas.setToolTipText("Realizar y registrar una recarga de botellones con los proveedores");
        btnCompras.setToolTipText("Realizar y registrar una compra de botellones con los proveedores");

        //Propiedades del menú de navegación
        menu.setBackground(GRIS);
        menu.add(btnRecargas);
        menu.add(btnCompras);

        //Propiedades del panel contenedor
        //Asignarle el CardLayout para navegar
        //entre sus paneles
        contenedor.setLayout(card);
        contenedor.setOpaque(false);
        contenedor.add(panelRecargas, "1");
        contenedor.add(panelCompras, "2");
        card.show(contenedor, "1");

        //Scroll para el panel contenedor
        scroll.setViewportView(contenedor);
        scroll.setBorder(null);
        scroll.setOpaque(false);

        this.add(menu);
        this.add(scroll);
    }

    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void mouseListeners() {
        btnRecargas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(COMPRAS_RECARGA);
            }
        });
        btnCompras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(COMPRAS_BOTELLON);

            }
        });
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     *
     * @param size Tamaño del parent contenedor
     */
    public void relocateComponents(java.awt.Dimension size) {
        this.setSize(size);

        menu.setSize(size.width, menu.getPreferredSize().height);

        int contY = menu.getHeight();
        scroll.setLocation(0, contY);
        scroll.setSize(size.width, size.height - contY);

        //Reducir el tamaño del panel dentro del scroll
        //en 20px horizontal y vertical
        int scrollW = scroll.getWidth() - 20;
        int scrollY = scroll.getHeight() - 20;

        panelCompras.relocateComponents(scrollW, scrollY);
        panelRecargas.relocateComponents(scrollW, scrollY);

        //Determinar el preferred size
        if (btnCompras.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelCompras.getPreferredSize());

        } else if (btnRecargas.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelRecargas.getPreferredSize());

        }
    }

    /**
     * Función para reemplazar el panel contenedor
     *
     * @param type panel que será mostrado
     */
    public static void replacePanel(int type) {
        //Activar o desactivar el botón, según el tipo
        btnRecargas.setForeground((type == COMPRAS_RECARGA) ? AZUL_PRINCIPAL : NEGRO);
        btnCompras.setForeground((type == COMPRAS_BOTELLON) ? AZUL_PRINCIPAL : NEGRO);

        //Determinar qué panel será mostrado
        card.show(contenedor, (type == COMPRAS_RECARGA) ? "1" : "2");

        //Determinar el preferred size
        if (btnCompras.getForeground().equals(AZUL_PRINCIPAL)) {
            panelCompras.actualizarDatos();
            contenedor.setPreferredSize(panelCompras.getPreferredSize());

        } else if (btnRecargas.getForeground().equals(AZUL_PRINCIPAL)) {
            panelRecargas.actualizarDatos();
            contenedor.setPreferredSize(panelRecargas.getPreferredSize());
        }
    }

    /**
     * Función para vaciar todos los campos de todos los paneles
     */
    public void vaciarCampos() {
        panelCompras.vaciarCampos();
        panelRecargas.vaciarCampos();
    }

    public static void setProveedor(String rif, String nombre){
        panelCompras.setProveedor(rif, nombre);
    }
    
    //COMPONENTES
    private static final JScrollPane scroll = new JScrollPane();
    private static final JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 5));
    private static final JPanel contenedor = new JPanel();
    private static final CardLayout card = new CardLayout();
    private static final Label btnRecargas = new Label("Recargas", TITULO, 18);
    private static final Label btnCompras = new Label("Compras", TITULO, 18);
    private static final PanelCompras panelCompras = new PanelCompras();
    private static final Recargas panelRecargas = new Recargas();
}

/**
 * Clase para la creación del panel para registrar la compra de botellones
 */
class PanelCompras extends JPanel implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    private void registrar() {
        //Mensaje de confirmación
        if (msjYesNo("¿Está seguro de realizar el registro de la compra?")) {

            if (validarCampos()) {
                if (validarDatos()) {

                    //Validar que el registro NO sea de números más grandes de 100
                    if (cantidad > 100) {

                        //En caso de ser alguno mayor de 100, lanzar un mensaje de alerta
                        String msj = "Está apunto de realizar un registro con una alta\n"
                                + "cantidad de botellones, ¿Está seguro de realizar el registro?";

                        if (msjYesNoWarning(msj)) {
                            RegistrarDB.compra(cantidad, precio, provRIF);
                        }
                    } else {
                        RegistrarDB.compra(cantidad, precio, provRIF);
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
        if (!txtCantidad.getText().trim().isEmpty()) {
            if (!txtPrecio.getText().trim().isEmpty()) {
                if (!provRIF.isEmpty() && !provNombre.isEmpty()) {
                    return true;

                } else {
                    msjError("Debe seleccionar un proveedor");
                }
            } else {
                msjError("El precio de cada botellón no puede estár vacío.");
                txtPrecio.requestFocus();
            }
        } else {
            msjError("La cantidad de botellones a comprar no puede estár vacío.");
            txtCantidad.requestFocus();
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

        //Validar que la cantidad de botellones esté dentro del rango correcto
        if (cantidad > 0 && cantidad < 8388607) {

            //Validar que el precio del botellón esté dentro del rango correcto
            double limit = Double.valueOf("10000000000");
            if (precio > 0 && precio <= limit) {

                return true;

            } else {
                msjError("El precio por cada botellón es inválido."
                        + "\nPor favor, verifique el precio.");
                txtPrecio.requestFocus();
            }

        } else {
            msjError("La cantidad de botellones a comprar es inválido."
                    + "\nPor favor, verifique la cantidad.");
            txtCantidad.requestFocus();
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
        btnProv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(PROVEEDOR);
            }
        });

        //KEY LISTENERS
        txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                //Retornar el valor convertido en entero, en caso de 
                //que sea válido la conversion.
                cantidad = teclaSuelta(txtCantidad.getText());

                //Actualizar la cantidad en la factura
                factura.setBotellonesTotal(cantidad);
                
                //Actualizar el monto total en la factura
                factura.setMontoTotal(precio * cantidad);
            }
        });
        txtPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                //Retornar el valor convertido en doble, en caso de 
                //que sea válido la conversion.
                precio = teclaSueltaDoble(txtPrecio.getText());

                //Actualizar el precio en la factura
                factura.setPrecioCadaUno(precio);
                
                //Actualizar el monto total en la factura
                factura.setMontoTotal(precio * cantidad);
            }
        });
    }

    /**
     * Función para actualizar los datos del panel de información cada vez que
     * se visualice el panel de trasvasos
     */
    protected void actualizarDatos() {
        informacion.actualizarDatos();

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
     * Función para asignar el proveedor seleccionado en la clase de recargas
     *
     * @param rif RIF del proveedor
     * @param nombre Nombre del proveedor
     */
    protected void setProveedor(String rif, String nombre) {
        provRIF = rif;
        provNombre = nombre;

        //Actualizar la factura
        factura.setInformacion(provRIF, provNombre);
    }

    //ATRIBUTOS BACKEND
    private static int cantidad = 0;
    private static double precio = 0;
    private static String provRIF = "", provNombre = "";

    // ========== FRONTEND ==========
    /**
     * Constructor del panel de compras de botellones
     */
    public PanelCompras() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {

        lblCantidad.setToolTipText(
                "<html>"
                + "Cantidad de <b>botellones que se compraron.</b><br>"
                + "El límite es de 8.388.606."
                + "</html>"
        );
        lblPrecio.setToolTipText(
                "<html>"
                + "Precio de compra de cada uno de los botellones.<br>"
                + "El límite es de 10.000.000.000 Bs."
                + "</html>"
        );

        panelCompras.add(lblTitulo);
        panelCompras.add(lblCantidad);
        panelCompras.add(txtCantidad);
        panelCompras.add(lblPrecio);
        panelCompras.add(txtPrecio);
        panelCompras.add(btnCancelar);
        panelCompras.add(btnAceptar);

        panelCompras.setBorder(createLineBorder(GRIS));
        panelCompras.setBackground(BLANCO);

        this.add(btnProv);
        this.add(factura);
        this.add(panelCompras);
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
        compraHeight = height - padding * 2;

        //Posición del botón y factura
        btnProv.setLocation(padding, padding);
        factura.setLocation(padding, facY);

        //PANEL PEQUEÑO
        if (width < 600) {
            
            //Cambiar el tamaño de la factura
            facHeight = 280;
            //Cambiar el tamaño de la compra
            compraHeight = 400;
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
        factura.relocateComponents();
        relocateCompra();
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño menor a 600 px
     */
    private void panelPequenio() {

        //Tamaño del botón y factura
        int panelWidth = width - padding * 2;
        btnProv.setSize(panelWidth, btnHeight);
        factura.setSize(panelWidth, facHeight);

        //Altura de la factura, sumando la altura de la factura, su
        //alto y un padding
        int trasvY = facY + facHeight + padding;
        //Asignar la posición y tamaño
        panelCompras.setBounds(padding, trasvY, panelWidth, compraHeight);

        //Altura de la información, sumando la altura del panel de
        //trasvaso, su alto y un padding
        int infoY = trasvY + compraHeight + padding;
        int infoHeight = 250;
        //Asignar la posición y tamaño
        informacion.setBounds(padding, infoY, panelWidth, infoHeight);

        //Calcular la altura absoluta del panel contenedor, sumando cinco
        //padding, la altura del botón, la altura de la factura y dos 
        //veces la altura del panel de trasvaso
        int absoluteHeight = padding * 5 + btnHeight + facHeight + compraHeight + infoHeight;

        //Asignar el tamaño preferido al panel
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 600px, pero menor a
     * 900 px
     */
    private void panelMediano() {

        //Dividir el panel en dos 
        int halfWidth = width / 2 - padding * 2;

        //Comprobar que la mitad del panel NO supere el ancho
        //máximo posible del botón y factura
        int clienteWidth = (halfWidth > facMaxWidth) ? facMaxWidth : halfWidth;
        btnProv.setSize(clienteWidth, btnHeight);
        factura.setSize(clienteWidth, facHeight);

        //Posicionar el panel de trasvaso después de la factura
        int trasvX = padding * 2 + clienteWidth;
        //Obtener el ancho del panel, menos la posición en X del panel
        //de trasvaso y restarle un padding
        int trasvWidth = width - trasvX - padding;
        //Asignar la posición y el tamaño
        panelCompras.setBounds(trasvX, padding, trasvWidth, compraHeight);

        //Posicionar el panel info debajo del panel de trasvaso
        int infoY = padding * 2 + compraHeight;
        //Asignarle todo el ancho del panel
        int infoWidth = width - padding * 2;
        //Asignarle la altura estática
        int infoHeight = 200;
        //Asignar la posición y el tamaño
        informacion.setBounds(padding, infoY, infoWidth, infoHeight);

        //Calcular la altura absoluta del panel contenedor, sumando
        //tres padding, la altura del panel de trasvaso y la altura
        //del panel de info
        int absoluteHeight = padding * 3 + compraHeight + infoHeight;

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
        this.compraHeight += 15;

        //Dividir el panel en 3 partes iguales
        int thirdWidth = width / 3 - padding * 2;

        //Comprobar que el tercio del panel NO supere el 
        //ancho máximo posible del botón y factura
        int facWidth = (thirdWidth > facMaxWidth) ? facMaxWidth : thirdWidth;
        btnProv.setSize(facWidth, btnHeight);
        factura.setSize(facWidth, facHeight);

        //Calcular la posición del panel info a la derecha del panel,
        //obteniendo el ancho del panel, menos el ancho máximo para
        //el panel info y restarle un padding
        int infoX = width - facWidth - padding;
        //Asignar la posición y el tamaño
        informacion.setBounds(infoX, padding, facWidth, compraHeight);

        //Calcular la posición en X del panel trasvaso, sumando
        //el ancho de la factura y dos padding
        int trasvX = facWidth + padding * 2;
        //Calcular el ancho del panel trasvaso, obteniendo el ancho del
        //panel, menos el ancho de la factura y del panel info, y
        //restandole cuatro paddings
        int trasvWidth = (width - facWidth * 2) - padding * 4;
        panelCompras.setBounds(trasvX, padding, trasvWidth, compraHeight);
    }

    /**
     * Función para posicionar los componentes del panel de compra
     */
    private void relocateCompra() {
        int txtHeight = 40;
        int gapV = 5;
        int trasW = panelCompras.getWidth();
        int txtWidth = trasW - padding * 2;

        //Asignar el tamaño a los componentes que requieran
        //declarar sus tamaños
        txtCantidad.setSize(txtWidth, txtHeight);
        txtPrecio.setSize(txtWidth, txtHeight);

        //Posición del título del panel para los datos
        lblTitulo.setLocation(padding, padding);

        //Para posicionar los campos en el centro vertical del panel
        //primero se obtiene le punto medio del panel
        int middleY = panelCompras.getHeight() / 2;
        //Luego la suma de la altura de TODOS los componentes y sus labels
        int allHeights = txtHeight * 2 + lblCantidad.getHeight() * 2;
        //Finalmente calcular el punto medio, sumando, además, los padding utilizados 
        int positionY = middleY - (allHeights + padding * 3) / 2;
        lblCantidad.setLocation(padding, positionY);

        //Posición vertical para el primer campo de texto
        positionY = positionY + lblCantidad.getHeight() + gapV;
        txtCantidad.setLocation(padding, positionY);

        //Posición vertical del segundo label para su campo de texto
        positionY = positionY + txtHeight + padding;
        lblPrecio.setLocation(padding, positionY);

        //Posición vertical para el segundo campo de texto
        positionY = positionY + lblPrecio.getHeight() + gapV;
        txtPrecio.setLocation(padding, positionY);

        //Posición vertical de los dos botones inferiores
        positionY = panelCompras.getHeight() - txtHeight - padding;

        //Ancho del botón de cancelar
        int btnW = btnCancelar.getPreferredSize().width + 30;
        btnCancelar.setSize(btnW, txtHeight);
        btnCancelar.setLocation(padding, positionY);

        //Ancho del botón de aceptar
        int positionX = padding * 2 + btnW;
        btnW = trasW - btnCancelar.getWidth() - padding * 3;
        btnAceptar.setSize(btnW, txtHeight);
        btnAceptar.setLocation(positionX, positionY);
    }

    /**
     * Función para vaciar los campos del panel de compras
     */
    protected void vaciarCampos() {
        //Restablecer los campos
        txtCantidad.setText("");
        txtPrecio.setText("");

        //Restablecer la factura
        factura.setPrecioCadaUno(0);
        factura.setBotellonesTotal(0);
        factura.setMontoTotal(0.00);
        factura.setInformacion("", "");

        //Vaciar los atributos
        cantidad = 0;
        precio = 0;
        provRIF = "";
        provNombre = "";
    }

    //ATRIBUTOS FRONTEND
    private int width, facHeight, compraHeight, facY;
    private final int padding = 20;
    private final int btnHeight = 80;
    private final int facMaxWidth = 300;

    //COMPONENTES
    private static final Boton btnProv = new Boton(ASIGNAR_PROV);
    private static final PanelFactura factura = new PanelFactura(COMPRAS_BOTELLON);
    private static final JPanel panelCompras = new JPanel(null);
    private static final PanelInfo informacion = new PanelInfo(COMPRAS);

    private static final Label lblTitulo = new Label("Compra de botellones", TITULO, 24);

    private static final Label lblCantidad = new Label("Cantidad de botellones", PLANO, 18, true);
    private static final CampoTexto txtCantidad = new CampoTexto("Botellones comprados", NUMERO);

    private static final Label lblPrecio = new Label("Precio de cada botellón", PLANO, 18, true);
    private static final CampoTexto txtPrecio = new CampoTexto("Precio del botellón", DECIMAL);

    private static final Boton btnAceptar = new Boton("Registrar", VERDE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);
}
