package components.map;

import database.ReadDB;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import properties.Fuentes;
import static properties.Constantes.PLANO;

/**
 * Clase para la creación de un panel contenedor de un mapa
 */
public class PanelMap extends JPanel implements properties.Colores {

    /**
     * Constructor de la clase para la creación del panel con el mapa
     */
    public PanelMap() {
        this.setLayout(new GridLayout());
        this.setBorder(createLineBorder(GRIS_OSCURO));

        initComponents();
        initMap();
        listener();
    }

    /**
     * Función para inicializar los componentes y agruparlos en paneles
     */
    private void initComponents() {
        //Asignar el border layout al mapa
        mapa.setLayout(new BorderLayout());

        //Panel para colocar los elementos en la parte de abajo
        JPanel sur = new JPanel(new BorderLayout());
        sur.setOpaque(false);

        //Paneles para colocar el comboBox al suroeste
        JPanel suroeste = new JPanel(new BorderLayout());
        JPanel suroeste_sur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        suroeste.setOpaque(false);
        suroeste_sur.setOpaque(false);

        //Panel para colocar los botones al sureste
        JPanel sureste = new JPanel();
        BoxLayout box = new BoxLayout(sureste, BoxLayout.Y_AXIS);
        sureste.setLayout(box);
        sureste.setOpaque(false);
        sureste.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));

        //Mover los botones a la derecha
        btnActualizar.setAlignmentX(JButton.RIGHT);
        btnCentrar.setAlignmentX(JButton.RIGHT);

        btnActualizar.setSize(btnActualizar.getPreferredSize().width + 20, 35);
        btnCentrar.setSize(btnCentrar.getPreferredSize().width + 20, 35);

        //Asignar la fuente de letra por defecto del software
        boxEstilos.setFont(Fuentes.segoe(16, PLANO));
        btnActualizar.setFont(Fuentes.segoe(16, PLANO));
        btnCentrar.setFont(Fuentes.segoe(16, PLANO));

        this.add(mapa);
        mapa.add(sur, BorderLayout.SOUTH);
        sur.add(suroeste, BorderLayout.WEST);
        sur.add(sureste, BorderLayout.EAST);
        suroeste.add(suroeste_sur, BorderLayout.SOUTH);
        suroeste_sur.add(boxEstilos);
        sureste.add(btnActualizar);
        sureste.add(Box.createRigidArea(new Dimension(0, 20)));
        sureste.add(btnCentrar);
    }

    /**
     * Función para iniciar las propiedades del mapa
     */
    private void initMap() {
        //Establecer la carga por defecto de los "chunks"
        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapa.setTileFactory(tileFactory);

        //Asignar la posición por defecto de Maracaibo
        mapa.setAddressLocation(POSICION_PRINCIPAL);
        mapa.setZoom(4);

        //Asignar el mouseEvent
        MouseInputListener mapMouse = new PanMouseInputListener(mapa);
        mapa.addMouseListener(mapMouse);
        mapa.addMouseMotionListener(mapMouse);
        mapa.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapa));

        event = getEvent();
    }

    /**
     * Función para
     */
    private static void initWaypoint() {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(puntos);

        mapa.setOverlayPainter(wp);
        for (MyWaypoint p : puntos) {
            mapa.add(p.getButton());
        }
    }

    /**
     * Función para agregar un punto nuevo en el mapa
     *
     * @param punto
     */
    private static void agregarPunto(MyWaypoint punto) {
        for (MyWaypoint p : puntos) {
            mapa.remove(p.getButton());
        }
        puntos.add(punto);
        initWaypoint();
    }

    /**
     * Función para eliminar todos los puntos actuales en el mapa
     */
    private static void borrarPuntos() {
        for (MyWaypoint d : puntos) {
            mapa.remove(d.getButton());
        }
        puntos.clear();
        initWaypoint();
    }

    /**
     * Función para asignar el evento que ejecutarán los puntos
     *
     * @return
     */
    private EventWaypoint getEvent() {
        return (MyWaypoint waypoint) -> {

        };
    }

    /**
     * Función para obtener los pedidos pendientes, seleccionar sus direcciones
     * y mostrar los puntos en el mapa
     *
     * @return
     */
    public void actualizarPuntos() {
        //Vaciar los puntos del mapa
        borrarPuntos();

        //Función para recorrer todos los datos
        for (Object[] pedido : direcciones) {
            //Obtener la latitud y longitud de los pedidos
            double lat = Double.parseDouble(pedido[1].toString());
            double lon = Double.parseDouble(pedido[2].toString());
            //Agregar los puntos con la cédula del pedido y coordenadas
            agregarPunto(new MyWaypoint(pedido[0].toString(), event, new GeoPosition(lat, lon)));
        }
    }

    public static boolean getDirecciones() {
        //Obtener la lista de los pedidos
        direcciones = ReadDB.getPedidosDirecciones();
        return direcciones != null;
    }

    /**
     * Función para enfocar un punto en el mapa, según la dirección
     *
     * @param latitud
     * @param longitud
     */
    public static void enfocarPunto(double latitud, double longitud) {
        mapa.setAddressLocation(new GeoPosition(latitud, longitud));
        mapa.setZoom(2);
    }

    /**
     * Función para asignar los listener a los componentes
     */
    private void listener() {
        btnActualizar.addActionListener((e) -> {
            new Thread(){
                @Override
                public void run() {
                    if (getDirecciones()) {
                        actualizarPuntos();
                    }
                }
            }.start();
        });

        btnCentrar.addActionListener((e) -> {
            mapa.setAddressLocation(POSICION_PRINCIPAL);
            mapa.setZoom(4);
        });

        boxEstilos.addActionListener((e) -> {
            TileFactoryInfo info;
            int index = boxEstilos.getSelectedIndex();
            int zoom = mapa.getZoom();
            switch (index) {
                case 0:
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                    break;
                default:
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                    break;
            }
            DefaultTileFactory tileFactory = new DefaultTileFactory(info);
            mapa.setTileFactory(tileFactory);
            mapa.setZoom(zoom);
        });
    }

    //COMPONENTES
    private static final JXMapViewer mapa = new JXMapViewer();
    private static final String[] capas = {"Estándar", "Satelital"};
    private static final JComboBox boxEstilos = new JComboBox(capas);
    private static final JButton btnActualizar = new JButton("Actualizar");
    private static final JButton btnCentrar = new JButton("Centrar mapa");

    //ATRIBUTOS
    private static final double LATITUD_PRINCIPAL = 10.58344935417965;
    private static final double LONGITUD_PRINCIPAL = -71.65015478472176;
    private static final GeoPosition POSICION_PRINCIPAL = new GeoPosition(LATITUD_PRINCIPAL, LONGITUD_PRINCIPAL);
    private static final Set<MyWaypoint> puntos = new HashSet<>();
    private static Object[][] direcciones;
    private static EventWaypoint event;
}
