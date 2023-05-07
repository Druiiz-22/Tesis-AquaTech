package components.map;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import static properties.Mensaje.msjInformativo;
import static properties.Mensaje.msjError;

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
        button.setToolTipText("Presiona para ver su información.");
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
        posicion.setText(String.format("%.3f", latitud) + ", " + String.format("%.3f", longitud));
        posicion.setIcon(null);
        informacion.setIcon(null);
        pedido.setIcon(null);
        google.setIcon(null);

        //Agregar los items al menú y agregar el menú al botón
        menu.add(posicion);
        menu.add(informacion);
        menu.add(pedido);
        menu.add(google);
        button.setComponentPopupMenu(menu);
    }

    private void listeners() {
        posicion.addActionListener((e) -> {
            String p = latitud + ", " + longitud;
            StringSelection selection = new StringSelection(p);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            
            msjInformativo("Se copió la dirección al portapapeles.");
        });

        informacion.addActionListener((e) -> {

        });

        pedido.addActionListener((e) -> {

        });

        google.addActionListener((e) -> {
            abrirGoogleMaps();
        });
    }

    /**
     * Función para abrir un punto marcado en Google Maps, según sus coordenadas
     */
    private void abrirGoogleMaps() {
        if (Desktop.isDesktopSupported()) {
            Desktop escritorio = Desktop.getDesktop();

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
    private final JMenuItem informacion = new JMenuItem("Informacion");
    private final JMenuItem pedido = new JMenuItem("Ver el pedido");
    private final JMenuItem google = new JMenuItem("Abrir en Google Maps");
}
