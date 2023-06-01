package components;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import main.MenuLateral;
import static java.awt.Font.BOLD;
import static properties.Constantes.ESCALA_SUAVE;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjAdvertencia;
import tabs.clientes.Deudas;
import tabs.ventas.Pedidos;

/**
 * Clase para la creación del panel contenedor de las notificaciones
 */
public class PanelNotificaciones extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del panel de notificaciones
     */
    public PanelNotificaciones() {
        this.setLayout(null);
        this.setOpaque(false);
        this.setSize(300, 400);
        //this.setBorder(BorderFactory.createLineBorder(NEGRO));

        initComponents();
        relocateComponents();

        actualizar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Deudas.actualizarDatos();
                Pedidos.actualizarDatos();
            }
        });
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        actualizar.setFont(segoe(16, BOLD));
        actualizar.setSize(actualizar.getPreferredSize());

        //Instanciar los separadores
        separador[0] = new JSeparator();
        separador[1] = new JSeparator();

        this.add(lblTitulo);
        this.add(deudas);
        this.add(pedidos);
        this.add(actualizar);
        this.add(separador[0]);
        this.add(separador[1]);
    }

    /**
     * Función para reposicionar los componentes
     */
    private void relocateComponents() {
        int x = 10;
        int y = 30;
        //Posicionar el título
        lblTitulo.setLocation(x, y);

        //Posicionar el separador debajo del título
        y += lblTitulo.getHeight() + 5;
        separador[0].setBounds(10, y, this.getWidth() - 20, separador[0].getPreferredSize().height);

        //Posicionar las deudas justamente debajo del separador
        y += separador[0].getHeight() + 1;
        deudas.setLocation(1, y);
        //Posicionar los pedidos justamente debajo de las deudas
        y += deudas.getHeight() + 1;
        pedidos.setLocation(1, y);

        //Posicionar el botón de actualizar al final del panel
        x = getWidth() / 2 - actualizar.getWidth() / 2;
        y = getHeight() - actualizar.getHeight() - 10;
        actualizar.setLocation(x, y);

        //Posicionar el separador arriba del botón de actualizar
        y -= 5;
        separador[1].setBounds(10, y, this.getWidth() - 20, separador[1].getPreferredSize().height);
    }

    /**
     * Función para acceder a las funciones gráficas del panel
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        //Definir el objeto Graphics2D
        Graphics2D g2D = (Graphics2D) (g);
        //Suavizar el renderizado
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Objeto para contener propiedades Graphics2D APARTE de la función
        AffineTransform propiedades = new AffineTransform();

        //Validar si el panel está en su forma grande o pequeña
        int x = (panelGrande) ? this.getWidth() * 2 / 3 + 10 : this.getWidth() - 52;

        propiedades.translate(x, 0);
        propiedades.rotate(Math.toRadians(45));

        //Objeto para crear una figura con propiedades establecidas
        int size = 30;
        int arc = 15;
        Path2D triangulo = new Path2D.Double(new RoundRectangle2D.Double(0, 0, size, size, arc, arc), propiedades);

        //Area para contener el triángulo y el rectángulo
        Area area = new Area(triangulo);

        //Posición del rectangulo a mitad de la diagonal del cuadrado
        int y = (int) ((Math.sqrt((size * size) + (size * size)) / 2) - arc / 2);

        //Crear un rectángulo y añadirlo al objeto Area
        area.add(new Area(new RoundRectangle2D.Double(0, y, this.getWidth(), this.getHeight() - y - 1, arc, arc)));

        //Asignar el color blanco para el rectángulo
        g2D.setColor(BLANCO);
        g2D.fill(area);

        super.paintComponent(g);
    }

    /**
     * Función que determina si el panel contenedor es de tamaño grande o no,
     * para reposicionar la punta del panel de notificación
     *
     * @param panelGrande TRUE si el panel es grande (+700)
     */
    public void panelGrande(boolean panelGrande) {
        PanelNotificaciones.panelGrande = panelGrande;
    }

    /**
     * Función para notificar la cantidad de deudas pendientes
     *
     * @param count
     */
    public static void setDeudas(int count) {
        deudas.setNumero(count);
    }

    /**
     * Función para notificar la cantidad de pedidos activos
     *
     * @param count
     */
    public static void setPedidos(int count) {
        pedidos.setNumero(count);
    }

    //COMPONENTES
    private static final Label lblTitulo = new Label("Notificaciones", TITULO, 18);
    private static final Label actualizar = new Label("Actualizar", LINK, 18);
    private static final JSeparator separador[] = new JSeparator[2];
    private static final ItemNotificacion deudas = new ItemNotificacion(DEUDAS);
    private static final ItemNotificacion pedidos = new ItemNotificacion(VENTAS_PEDIDOS);
    private static boolean panelGrande = true;

    /**
     * Clase privada para la creación de las notificaciones
     */
    private static class ItemNotificacion extends JPanel {

        /**
         * Constructor de la notificación
         *
         * @param type Tipo de notificación (Deuda o pedido)
         */
        public ItemNotificacion(int type) {
            this.type = type;
            this.setLayout(null);
            this.setOpaque(true);
            this.setBackground(BLANCO);
            this.setSize(298, 80);
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));

            initComponents();
            relocateComponents();

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(pressColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //Validar que el botón NO esté siendo presionado
                    if (!getBackground().equals(pressColor)) {
                        setBackground(BLANCO);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //Validar que el botón NO esté siendo presionado
                    if (!getBackground().equals(pressColor)) {
                        setBackground(GRIS_CLARO);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //Obtener la posición del mouse
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    //Obtener el ancho y alto del panel
                    int w = getWidth();
                    int y = getHeight();

                    //Validar que el mouse se encuentre FUERA del panel
                    if ((mouseX < 0 || mouseX > w) || (mouseY < 0 || mouseY > y)) {
                        setBackground(BLANCO);
                    } else {
                        setBackground(GRIS_CLARO);
                    }

                    //Validar el tipo de notificacion
                    if (type == DEUDAS) {
                        MenuLateral.clickButton(DEUDAS);

                    } else if (type == VENTAS_PEDIDOS) {
                        MenuLateral.clickButton(VENTAS_PEDIDOS);
                    }

                }
            });
        }

        /**
         * Función para iniciar los componentes
         */
        private void initComponents() {

            //Determinar el tipo de item
            if (type == DEUDAS) {
                lblTitulo.setText("Deudas");
                lblDescripcion.setText("<html><p>No hay ninguna deuda actualmente</p></html>");

            } else if (type == VENTAS_PEDIDOS) {
                lblTitulo.setText("Entregas");
                lblDescripcion.setText("<html><p>No hay ningún cliente por entrega</p></html>");
            }

            //Asignar el tamaño ajustado al título
            lblTitulo.setSize(lblTitulo.getPreferredSize());
            lblDescripcion.setVerticalAlignment(JLabel.TOP);

            try {
                //Cargar la imagen
                img.setIcon(getImage());

            } catch (Exception e) {
                msjAdvertencia("No se pudo cargar algún ícono en las notificaciones.\n"
                        + "El software seguirá ejecutandose sin el ícono.");
            } finally {
                //Alineamiento centrado
                img.setHorizontalAlignment(JLabel.CENTER);
                img.setVerticalAlignment(JLabel.CENTER);
                //Asignar el tamaño
                img.setSize(this.getHeight(), this.getHeight());
            }

            //Añadir los elementos
            this.add(lblTitulo);
            this.add(lblDescripcion);
            this.add(img);
        }

        /**
         * Función para obtener la imagen de la notificación
         *
         * @return Imagen de la notificación
         */
        private ImageIcon getImage() {
            String ruta = "/icons/popup/";

            ruta = ruta + ((type == DEUDAS) ? "deudas.png" : "pedidos.png");

            //Obtener la imagen
            ImageIcon icon = new ImageIcon(getClass().getResource(ruta));

            //Escalar la imagen
            return new ImageIcon(icon.getImage().getScaledInstance(50, 50, ESCALA_SUAVE));
        }

        /**
         * Función para reposicionar los elementos
         */
        private void relocateComponents() {
            //Posicionar los labels después de la imagen
            int x = img.getWidth();

            //Posicionar el label 10 px en Y
            int y = 10;
            lblTitulo.setLocation(x, y);

            //Posicionar la descripción debajo del título
            y += lblTitulo.getHeight();
            //El ancho será del ancho del panel, menos su posición en x
            int w = this.getWidth() - x;
            //El altura será del alto del panel, menos su posición en y
            int h = this.getHeight() - y;
            lblDescripcion.setBounds(x, y, w, h);
        }

        /**
         * Función para asignar el número de deudas o entregas en la
         * notificación
         *
         * @param number Número de deudas o entregas
         */
        protected void setNumero(int number) {
            String info = "";

            //Validar el tipo
            if (type == DEUDAS) {

                if (number > 0) {
                    info = "<html><p>Hay " + number + " clientes con deudas pendientes</p></html>";
                } else {
                    info = "No hay ninguna deuda actualmente";
                }

            } else if (type == VENTAS_PEDIDOS) {

                if (number > 0) {
                    info = "<html><p>Hay " + number + " esperando por su entrega</p></html>";
                } else {
                    info = "No hay ningún cliente por entrega";
                }
            }

            lblDescripcion.setText(info);
        }

        //COMPONENTES DE LA NOTIFICACIÓN
        private final java.awt.Color pressColor = new java.awt.Color(235, 235, 235);
        private final int type;
        private final Label lblTitulo = new Label("", TITULO, 14);
        private final Label lblDescripcion = new Label("", PLANO, 12);
        private final JLabel img = new JLabel();
    }
}
