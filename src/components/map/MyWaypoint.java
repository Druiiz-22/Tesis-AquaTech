package components.map;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjAdvertencia;
import static properties.Mensaje.msjInformativo;
import static properties.Mensaje.msjError;
import tabs.ventas.Pedidos;

public class MyWaypoint extends DefaultWaypoint {

    /**
     * Función para obtener el nombre del punto creado
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Función para asignar el nombre al punto creado
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Función para obtener el objeto JButton del punto creado
     *
     * @return JButton
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Función para asignar el objeto JButton al punto creado
     *
     * @param button
     */
    public void setButton(JButton button) {
        this.button = button;
    }

    /**
     * Constructor del punto en el mapa
     *
     * @param name Nombre del punto de referencia
     * @param event Evento que realizará cuando se presione
     * @param coord GeoPosition del punto de referencia
     */
    public MyWaypoint(String name, EventWaypoint event, GeoPosition coord) {
        super(coord);
        this.name = name;

        initButton(event);
        initPopUpMenu(coord);
        listeners();
    }

    /**
     * Función para iniciar el botón del punto
     *
     * @param event
     */
    private void initButton(EventWaypoint event) {
        button = new ButtonWaypoint();
        button.addActionListener((ActionEvent ae) -> {
            event.selected(MyWaypoint.this);
        });
        button.setToolTipText("<html>"
                + "<p><b>Cédula del pedido:</b> " + getName() + "</p>"
                + "<p>Presiona para ver más información.</p>"
                + "</html>");
    }

    /**
     * Función para iniciar el menú despegable para cada botón
     *
     * @param coord
     */
    private void initPopUpMenu(GeoPosition coord) {
        //Guardar las coordendas
        latitud = coord.getLatitude();
        longitud = coord.getLongitude();

        //Asignar las coordenadas al item de la posición
        String lat = String.valueOf(Math.round(latitud * 100000) / 100000.0);
        String lon = String.valueOf(Math.round(longitud * 100000) / 100000.0);
        posicion.setText(lat + ", " + lon);

        //Asignar las fuentes de letra
        posicion.setFont(segoe(13, properties.Constantes.PLANO));
        posicion.setForeground(properties.Colores.NEGRO);

        informacion.setFont(segoe(13, properties.Constantes.PLANO));
        informacion.setForeground(properties.Colores.NEGRO);

        pedido.setFont(segoe(13, properties.Constantes.PLANO));
        pedido.setForeground(properties.Colores.NEGRO);

        google.setFont(segoe(13, properties.Constantes.PLANO));
        google.setForeground(properties.Colores.NEGRO);
        
        pagar.setFont(segoe(13, properties.Constantes.PLANO));
        pagar.setForeground(properties.Colores.NEGRO);
        
        enfocar.setFont(segoe(13, properties.Constantes.PLANO));
        enfocar.setForeground(properties.Colores.NEGRO);
        
        try {
            ImageIcon img = new ImageIcon(getClass().getResource("/icons/popup/copiar.png"));
            posicion.setIcon(new ImageIcon(img.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));

            img = new ImageIcon(getClass().getResource("/icons/popup/informacion.png"));
            informacion.setIcon(new ImageIcon(img.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));

            img = new ImageIcon(getClass().getResource("/icons/popup/factura.png"));
            pedido.setIcon(new ImageIcon(img.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));

            img = new ImageIcon(getClass().getResource("/icons/popup/web.png"));
            google.setIcon(new ImageIcon(img.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
            
            img = new ImageIcon(getClass().getResource("/icons/popup/vender.png"));
            pagar.setIcon(new ImageIcon(img.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
            
            img = new ImageIcon(getClass().getResource("/icons/popup/ubicacion.png"));
            enfocar.setIcon(new ImageIcon(img.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos del menú desplegable del mapa.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {
            //Agregar los items al menú y agregar el menú al botón
            menu.add(posicion);
            menu.add(enfocar);
            menu.addSeparator();
            menu.add(pagar);
            menu.add(informacion);
            menu.add(pedido);
            menu.addSeparator();
            menu.add(google);
        }
    }

    private void listeners() {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Obtener la posición del mouse
                int x = e.getPoint().x;
                int y = e.getPoint().y;

                //Obtener los límites en los que puede estar el mouse para
                //ser considerado "dentro" del botón
                int x_max = button.getWidth();
                int y_max = button.getHeight();

                //Mostrar el menú cuando el mouse se suelte y esté dentro 
                //del botón presionado
                if ((x > 0 && x < x_max) && (y > 0 && y < y_max)) {
                    menu.show(button, x, y);
                }
            }
        });

        posicion.addActionListener((e) -> {
            String p = latitud + ", " + longitud;
            StringSelection selection = new StringSelection(p);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);

            msjInformativo("Se copió la dirección al portapapeles.");
        });

        informacion.addActionListener((e) -> {
            Pedidos.infoPedido(this.getName());
        });
        
        pagar.addActionListener((e) -> {
            Pedidos.pagarPedido(this.getName());
        });
        
        enfocar.addActionListener((e) -> {
            PanelMap.enfocarPunto(latitud, longitud);
        });

        pedido.addActionListener((e) -> {
            Pedidos.enfocarPedido(this.getName());
        });

        google.addActionListener((e) -> {
            abrirGoogleMaps();
        });
    }

    /**
     * Función para abrir un punto marcado en Google Maps, según sus coordenadas
     */
    private void abrirGoogleMaps() {
        //Validar que la plataforma sea capaz de usar la clase Desktop
        if (Desktop.isDesktopSupported()) {
            Desktop escritorio = Desktop.getDesktop();

            //Validar que la plataforma sea capaz de usar un navegador
            if (escritorio.isSupported(Desktop.Action.BROWSE)) {
                try {
                    //URL de google maps, abriendo un punto en el mapa, según 
                    //la latitud y longitud del mismo
                    URI uri = new URI("https://www.google.es/maps?q=" + latitud + "," + longitud);

                    //Intentar abrir el navegador y buscar la URL
                    escritorio.browse(uri);

                } catch (IOException | URISyntaxException e) {
                    msjError("No se pudo abrir el navegador.\nError: " + e.getMessage());
                }
            } else {
                msjError("La plataforma actual no es compatible para "
                        + "abrir un navegador.\nDeberá copiar las coordenadas del"
                        + "punto y buscarlo manualmente en Google Maps.");
            }
        } else {
            msjError("La plataforma actual no es compatible con la clase"
                    + "\"Desktop\", impidiendo\n la posibilidad de abrir ficheros y "
                    + "sitios web.");
        }
    }

    private String name;
    private JButton button;
    private double latitud;
    private double longitud;
    private final JPopupMenu menu = new JPopupMenu();
    private final JMenuItem posicion = new JMenuItem();
    private final JMenuItem informacion = new JMenuItem("Ver la informacion");
    private final JMenuItem pagar = new JMenuItem("Pagar deuda");
    private final JMenuItem enfocar = new JMenuItem("Enfocar el punto");
    private final JMenuItem pedido = new JMenuItem("Seleccionar el pedido");
    private final JMenuItem google = new JMenuItem("Abrir en Google Maps");
}
