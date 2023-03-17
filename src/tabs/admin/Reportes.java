package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.PanelInfo;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import properties.Fuentes;
import static javax.swing.BorderFactory.createLineBorder;

/**
 * Clase para la creación del panel de generación de reportes, en el
 * administrador
 */
public class Reportes extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del panel de los ajustes
     */
    public Reportes() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        
        boxTipoReporte.setFont(Fuentes.segoe(16, NORMAL));

        //Asignar los tooltip texts
        lblTipoReporte.setToolTipText("Determinar el tipo de información e "
                + "historial que mostrará el reporte generado");
        lblUbicacion.setToolTipText("Determinar la ubicación del archivo del "
                + "reporte (PDF)\nEn caso de dejar el campo vacío, se creará "
                + "en la ruta por defecto");

        panelReportes.setBackground(BLANCO);
        panelReportes.setBorder(createLineBorder(GRIS));
        
        panelReportes.add(lblTitulo);
        panelReportes.add(lblTipoReporte);
        panelReportes.add(boxTipoReporte);
        panelReportes.add(lblUbicacion);
        panelReportes.add(txtUbicación);
        panelReportes.add(btnUbicacion);
        panelReportes.add(btnAceptar);
        panelReportes.add(btnCancelar);

        this.add(informacion);
        this.add(panelReportes);
        this.add(fechaInicio);
        this.add(fechaFin);
    }

    /**
     * Función para aplicar los listener a los componentes
     */
    private void listeners() {
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     * @param width Ancho del parent contenedor
     * @param height Alto del parent contenedor
     */
    protected void relocateComponents(int width, int height) {
        //Variables recurrentes
        this.width = width;
        this.panelHeight = height - padding*2;
        this.fechaHeight = height/2 - padding; 
        
        //Posicionar la información al comienzo
        informacion.setLocation(padding, padding);

        //Validar el tamaño del ancho del contenedor
        if (this.width < 600) {
            panelPequenio();
            
        } else if (this.width < 900) {
            panelMediano();
            
        } else if (this.width >= 900) {
            this.setPreferredSize(new Dimension(width, height));
            panelGrande();
        }
        
        //Reposicionar el reporte
        relocateReporte();
        //Reposicionar la información
        informacion.relocateComponents(CUALQUIER);
        //Reposicionar las fechas
        fechaInicio.relocateComponents();
        fechaFin.relocateComponents();
    }
    
    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño menor a 600 px
     */
    private void panelPequenio(){
        //Ancho de los paneles
        int w = this.width - padding*2;
        
        //Tamaño del panel de información
        informacion.setSize(w, 300);
        
        //Posición del panel de reportes
        int y = informacion.getHeight() + padding*2;
        panelReportes.setBounds(padding, y, w, panelHeight);
        
        //Posición de la primera fecha
        y += panelHeight + padding;
        fechaInicio.setBounds(padding, y, w, fechaHeight);
        
        //Posición de la segunda fecha
        y += fechaHeight + padding;
        fechaFin.setBounds(padding, y, w, fechaHeight);
        
        //Tamaño del panel
        int absoluteHeight = padding*5 + panelHeight + informacion.getHeight() + fechaHeight*2 ;
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }
    
    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 600px, pero menor a
     * 900 px
     */
    private void panelMediano(){
        //Obtener el tercio del panel contenedor
        int halfWidth = width/2 - padding *2;
        int infoWidth = (halfWidth < infoMaxWidth) ? halfWidth : infoMaxWidth; 
        informacion.setSize(infoWidth, panelHeight);
        
        //Posicionar el panel de reportes al lado de la información
        int x = padding*2 + infoWidth;
        //Asignar el ancho como el resto del espacio
        int w = width - x - padding;
        panelReportes.setBounds(x, padding, w, panelHeight);
        
        //Posición vertical de las fechas
        int y = padding*2 + panelHeight;
        //Ancho de las fechas
        w = width/2 - padding*3/2;
        fechaInicio.setBounds(padding, y, w, fechaHeight);
        
        //Posición de la segunda fecha, al lado de la primera fecha
        x = padding*2 + w;
        fechaFin.setBounds(x, y, w, fechaHeight);
        
        int absoluteHeight = padding*3 + fechaHeight + panelHeight;
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }
    
    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 900 px
     */
    private void panelGrande(){
        
        //Aumentar el ancho y el alto en 15 px
        this.width += 15;
        this.panelHeight += 15;
        this.fechaHeight = panelHeight/2 - padding/2;
        
        //Obtener el tercio del panel contenedor
        int thirdWidth = width/3 - padding *2;
        //Obtener el ancho del panel del info SIN superar el límite máximo
        int infoWidth = (thirdWidth < infoMaxWidth) ? thirdWidth : infoMaxWidth; 
        //Asignar el tamaño
        informacion.setSize(infoWidth, panelHeight);
        
        //Posición en X de las fechas
        int x = width - infoWidth - padding;
        //Asignar la posición y tamaño de la primera fecha
        fechaInicio.setBounds(x, padding, infoWidth, fechaHeight);
        
        //La altura será un padding debajo de la mitad
        int y = fechaHeight + padding*2;
        //Asignar la posición y tamaño de la segunda fecha
        fechaFin.setBounds(x, y, infoWidth, fechaHeight);
        
        //La posición en x será al lado del panel del info
        x = padding*2 + infoWidth;
        //Ancho del reporte, siendo este lo restante entre 
        //el panel de info y el ancho de las fechas
        int w = width - padding *4 - infoWidth*2;
        panelReportes.setBounds(x, padding, w, panelHeight);
    }

    /**
     * Función para reposicionar los componentes dentro del panel de reportes
     */
    private void relocateReporte(){
        int txtHeight = 40;
        int gapV = 5;
        int repW = panelReportes.getWidth();
        int txtWidth = repW - padding * 2;

        //Asignar el tamaño a los componentes que requieran
        //declarar sus tamaños
        boxTipoReporte.setSize(txtWidth, txtHeight);
        btnUbicacion.setSize(txtHeight, txtHeight);
        txtUbicación.setSize(txtWidth - txtHeight - gapV*2, txtHeight);

        //Posición del título del panel para los datos
        lblTitulo.setLocation(padding, padding);

        //Para posicionar los campos en el centro vertical del panel
        //primero se obtiene le punto medio del panel
        int middleY = panelReportes.getHeight() / 2;
        //Luego la suma de la altura de TODOS los componentes y sus labels
        int allHeights = txtHeight * 2 + lblTipoReporte.getHeight() * 2;
        //Finalmente calcular el punto medio, sumando, además, los padding utilizados 
        int positionY = middleY - (allHeights + padding * 3) / 2;
        lblTipoReporte.setLocation(padding, positionY);

        //Posición vertical para el primer campo de texto
        positionY = positionY + lblTipoReporte.getHeight() + gapV;
        boxTipoReporte.setLocation(padding, positionY);

        //Posición vertical del segundo label para su campo de texto
        positionY = positionY + txtHeight + padding;
        lblUbicacion.setLocation(padding, positionY);

        //Posición vertical para el segundo campo de texto y su botón
        positionY = positionY + lblUbicacion.getHeight() + gapV;
        int positionX = repW - padding - txtHeight;
        btnUbicacion.setLocation(positionX, positionY);
        txtUbicación.setLocation(padding, positionY);

        //Posición vertical de los dos botones inferiores
        positionY = panelReportes.getHeight() - txtHeight - padding;

        //Ancho del botón de cancelar
        int btnW = btnCancelar.getPreferredSize().width + 30;
        btnCancelar.setSize(btnW, txtHeight);
        btnCancelar.setLocation(padding, positionY);

        //Ancho del botón de aceptar
        positionX = padding * 2 + btnW;
        btnW = repW - btnCancelar.getWidth() - padding * 3;
        btnAceptar.setSize(btnW, txtHeight);
        btnAceptar.setLocation(positionX, positionY);
    }
    /**
     * Función para obtener la ubicación de generación del reporte
     */
    private void getDirectoryPath(){
        
    }
    
    /**
     * Función para vaciar todos los campos
     */ 
    protected void vaciarCampos(){
        fechaInicio.vaciarCampos();
        fechaFin.vaciarCampos();
        boxTipoReporte.setSelectedIndex(0);
        txtUbicación.setText("");
    }
    
    //ATRIBUTOS
    private static int width, panelHeight, fechaHeight;
    private static final int padding = 20;
    private static final int infoMaxWidth = 300;

    //COMPONENTES
    private static final PanelInfo informacion = new PanelInfo(ADMIN_REPORTES);
    private static final JPanel panelReportes = new JPanel(null);
    private static final PanelFecha fechaInicio = new PanelFecha("Fecha Inicio");
    private static final PanelFecha fechaFin = new PanelFecha("Fecha Fin");

    private static final Label lblTitulo = new Label("Generar Reportes", TITULO, 24);

    private static final Label lblTipoReporte = new Label("Tipo de reporte", NORMAL, 18, true);
    private static final String[] opciones = {"Seleccionar", "Trasvasos", "Recargas", "Compras", "Ventas"};
    private static final JComboBox boxTipoReporte = new JComboBox(opciones);

    private static final Label lblUbicacion = new Label("Ubicación del reporte", NORMAL, 18, true);
    private static final CampoTexto txtUbicación = new CampoTexto("Ubicación del reporte", CUALQUIER);

    private static final Boton btnAceptar = new Boton("Guardar", CELESTE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);
    
    private static final BotonDirectory btnUbicacion = new BotonDirectory();
}

/**
 * Clase para los paneles con las fechas
 */
class PanelFecha extends JPanel implements properties.Constantes {

    /**
     * Constructor de los paneles de fechas
     * @param tipoFecha Titulo del panel
     */
    public PanelFecha(String tipoFecha) {
        this.setLayout(null);
        this.setBackground(properties.Colores.BLANCO);
        this.setBorder(createLineBorder(properties.Colores.GRIS));

        initComponets(tipoFecha);
    }

    /**
     * Función para iniciar los componentes
     * @param tipoFecha Título del panel
     */
    private void initComponets(String tipoFecha) {
        lblTitulo.setText(tipoFecha);
        lblTitulo.setSize(lblTitulo.getPreferredSize());

        this.add(lblTitulo);
        this.add(txtFecha);
    }

    /**
     * Función para reposicionar y redimensionar los elementos de las fechas
     */
    protected void relocateComponents() {
        int middleX = this.getWidth() / 2;
        int padding = 10;

        int x = middleX - lblTitulo.getWidth() / 2;
        lblTitulo.setLocation(x, padding);

        int w = this.getWidth() - padding * 4;
        int h = 25;
        int y = padding*2 + lblTitulo.getHeight();
        txtFecha.setBounds(padding*2, y, w, h);
    }

    /**
     * Función para vaciar los campos
     */
    protected void vaciarCampos(){
        txtFecha.setText("");
    }
    
    //Componentes
    private final Label lblTitulo = new Label("", TITULO, 22);
    private final CampoTexto txtFecha = new CampoTexto("99-99-9999", FECHA);

}
