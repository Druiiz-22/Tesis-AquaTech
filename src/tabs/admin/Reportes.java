package tabs.admin;

import com.toedter.calendar.JCalendar;
import components.Boton;
import components.CampoTexto;
import components.Label;
import components.PanelInfo;
import database.ReadDB;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import properties.Fuentes;
import javax.swing.JFileChooser;
import static javax.swing.BorderFactory.createLineBorder;
import main.Frame;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;
import static properties.ValidarTexto.formatoFecha;
import static properties.ValidarTexto.rangoFecha;
import static properties.ValidarTexto.cronologia;

/**
 * Clase para la creación del panel de generación de reportes, en el
 * administrador
 */
public class Reportes extends JPanel implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    /**
     *
     */
    private void generarReporte() {

        int index = boxTipoReporte.getSelectedIndex();

        if (validarCampos()) {

            //Validar de fechas en caso de que el tipo de reporte seleccionado
            //sea DISTINTO al de clientes, proveedores o deudas
            if ((index == REP_CLIENTES || index == REP_PROVEEDORES
                    || index == REP_DEUDAS || index == REP_EMPLEADOS) ? true : validarFechas()) {

                if (msjYesNo("¿Está seguro de realizar el reporte?")) {
                    new Thread() {
                        @Override
                        public void run() {
                            Frame.openGlass(0);

                            int type = boxTipoReporte.getSelectedIndex();
                            String path = txtUbicacion.getText();

                            //Validar si la ubicación es predeterminada o personalizada
                            path = (path.toUpperCase().equals("PREDETERMINADO")) ? getDefaultFolder() : path;

                            //Switch para crear el reporte, según su tipo
                            switch (index) {
                                case REP_CLIENTES:
                                case REP_PROVEEDORES:
                                case REP_DEUDAS:
                                    //Crear reporte sin filtro por fechas y sucursal
                                    CrearReporte.crear(type, path);
                                    break;

                                case REP_EMPLEADOS:
                                    //Crear reporte sin filtro por fechas, pero
                                    //filtrado por sucursal
                                    CrearReporte.crear(type, path, id_sucursal);
                                    break;

                                default:
                                    //Crear el reporte con filtro por fecha y
                                    //por sucursal
                                    String initialDate = fechaInicio.getSelectedDate();
                                    String finalDate = fechaFin.getSelectedDate();
                                    CrearReporte.crear(type, path, initialDate, finalDate, id_sucursal);
                                    break;
                            }

                            //Reposicionar el combobox
                            boxTipoReporte.setSelectedIndex(0);
                            boxSucursales.setSelectedIndex(0);
                            //Cerrar el GlassPane
                            Frame.closeGlass();
                        }
                    }.start();
                }
            }
        }
    }

    /**
     * Función para seleccionar la carpeta donde se generará el reporte
     */
    private void buscarCarpeta() {
        //Crear el componentes FileChooser
        JFileChooser chooser = new JFileChooser();
        //Asignar el título
        chooser.setDialogTitle("Selecciona el directorio");

        //Asignar que sea de SOLO se seleccionen carpetas
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //Abrir el FileChooser y obtener la opción seleccionadda
        int opcion = chooser.showOpenDialog(this);

        //Validar que se seleccionó una carpeta
        if (opcion == JFileChooser.APPROVE_OPTION) {

            //Obtener la ruta de la carpeta seleccionada
            String path = chooser.getSelectedFile().getAbsolutePath();

            //Validar que la ruta NO esté vacía
            if (!path.trim().isEmpty()) {

                //Validar la existencia de la ruta
                if (validateFile(path)) {
                    txtUbicacion.setText(path);

                } else {
                    //Si la ruta no es válida, asignar la ruta por defecto
                    txtUbicacion.setText("Predeterminado");
                }

            } else {
                //Si la ruta está vacía, asignar la ruta predeterminada
                txtUbicacion.setText("Predeterminado");
            }
        } else {
            //Si no se seleccionó una carpeta, asignar la ruta predeterminada
            txtUbicacion.setText("Predeterminado");
        }
    }

    /**
     * Función para validar que un archivo o ruta sea válido
     *
     * @param path Ruta personalizada
     * @return
     */
    private boolean validateFile(String path) {
        //Crear un archivo con la ruta seleccionada
        File filePath = new File(path);

        //Validar que la ruta sea un archivo o carpeta existente        
        return filePath.exists();
    }

    /**
     * Función para validar que los campos NO estén vacíos
     *
     * @return TRUE en caso de que ningún campo esté vacío
     */
    private boolean validarCampos() {
        //Validar que haya seleccionado algún tipo de reporte
        if (boxTipoReporte.getSelectedIndex() > 0) {

            //Comprobar que las fechas NO estén vacías
            if (!fechaInicio.getSelectedDate().trim().isEmpty()) {
                if (!fechaFin.getSelectedDate().trim().isEmpty()) {

                    //Obtener el index del tipo de reporte seleccionado
                    int tipo = boxTipoReporte.getSelectedIndex();
                    //Comrobar si el reporte NO llevará filtro por sucursales
                    if (tipo == REP_CLIENTES || tipo == REP_PROVEEDORES || tipo == REP_DEUDAS) {
                        return true;
                    }

                    //Obtener el index de la sucursal seleccionada para filtrarlo
                    int sucursal_i = boxSucursales.getSelectedIndex();

                    //Validar que la sucursal sea mayor que 0 o que el tipo de 
                    //reporte seleccionado permita el uso de sucursal
                    if (sucursal_i > 0) {

                        //Comprobar si se seleccionaron TODAS las sucursales
                        if (sucursal_i == 1) {
                            id_sucursal = TODAS_SUCURSALES;
                            return true;

                        } else {
                            try {
                                //Intentar obtener el ID de la sucursal seleccionada
                                Object sucursal = boxSucursales.getSelectedItem();
                                String id = ((ComboItem) sucursal).getValue();

                                //Validar que el id obtenido NO sea nulo
                                if (!id.isEmpty()) {
                                    //Convertir de String a Entero y validar su valor 
                                    id_sucursal = Integer.valueOf(id);
                                    if (id_sucursal > 0) {

                                        return true;

                                    } else {
                                        throw new NumberFormatException();
                                    }
                                } else {
                                    msjError("No se pudo obtener el ID de la sucursal "
                                            + "seleccionada.");
                                }
                            } catch (NumberFormatException e) {
                                msjError("El ID de la sucursal seleccionada es "
                                        + "inválida.\nPor favor, actualice el"
                                        + "programa y valide la existencia de"
                                        + "la sucursal.");
                            }
                        }
                    } else {
                        msjError("Debe seleccionar una sucursal válida.");
                    }
                } else {
                    msjError("La fecha final final no puede estar vacía.");
                }
            } else {
                msjError("La fecha inicial no puede estar vacía.");
            }
        } else {
            msjError("Debe seleccionar el tipo de reporte.");
        }

        return false;
    }

    /**
     * Función para validar que las fechas sean correctas
     *
     * @return TRUE si las fechas son correctas
     */
    private boolean validarFechas() {

        //Obtener las fechas seleccionadas
        String fin = fechaFin.getSelectedDate();
        String inicio = fechaInicio.getSelectedDate();

        //Validar que las fechas ingresadas cumplan con los formatos de una
        //fecha correcta: dd-MM-yyyy
        if (formatoFecha(inicio)) {
            if (formatoFecha(fin)) {
                //Validar que las reglas estén dentro del rango correcto
                if (rangoFecha(inicio)) {
                    if (rangoFecha(fin)) {
                        //Validar que la fecha de fin NO sea menor a la del final
                        if (cronologia(inicio, fin)) {

                            return true;

                        } else {
                            msjError("La fecha final no puede ser inferior a la fecha "
                                    + "inicial.\nPor favor, vuelva a ingresar las fechas.");
                        }
                    } else {
                        msjError("La fecha final no está dentro del rango correcto"
                                + " de las fechas.\nRango: mayor que 1/1/1900, menor"
                                + " que 1/1/2100.\n\nPor favor, vuelva a ingresar "
                                + "la fecha inicial..");
                    }
                } else {
                    msjError("La fecha inicial no está dentro del rango correcto"
                            + " de las fechas.\nRango: mayor que 1/1/1900, menor"
                            + " que 1/1/2100.\n\nPor favor, vuelva a ingresar "
                            + "la fecha inicial..");
                }
            } else {
                msjError("La fecha final ingresada es inválida.\nPor favor, "
                        + "vuelva a ingresar la fecha final.");
            }
        } else {
            msjError("La fecha inicial ingresada es inválida.\nPor favor, "
                    + "vuelva a ingresar la fecha de inicio.");
        }

        return false;
    }

    /**
     * Función para obtener la ruta de la carpeta predeterminada para los
     * reportes generados
     *
     * @return ruta de la carpeta por defecto, según el tipo de reporte
     */
    private String getDefaultFolder() {

        //Ubicar la carpeta principal "Documents"
        String documentsPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();

        //Nombre de la carpeta "Aquatech" donde se almacenan los archivos 
        //del programa
        String mainPath = "\\AquaTech";

        //Crear un archivo con la ruta de Documentos + la carpeta principal
        File mainFolder = new File(documentsPath + mainPath);

        //Validar que NO exista la carpeta principal AquaTech
        if (!mainFolder.exists()) {
            //Si no existe, crea la carpeta en "/Documents"
            mainFolder.mkdir();
        }

        //Nombre de la carpeta principal donde se almacenan los reportes
        mainPath += "\\Reportes generados";

        //Crear un archivo en la ruta de Documentos + La carpeta de respaldos
        File backupFolder = new File(documentsPath + mainPath);

        //Validar que NO exista la carpeta principal de respaldos
        if (!backupFolder.exists()) {
            //Si no existe, crea la carpeta en "/Documents/AquaTech"
            backupFolder.mkdir();
        }

        //Validar el tipo de reporte
        switch (boxTipoReporte.getSelectedIndex()) {
            case REP_TRASVASOS:
                mainPath += "\\Trasvasos";
                break;
            case REP_DEUDAS:
                mainPath += "\\Deudas";
                break;
            case REP_RECARGAS:
                mainPath += "\\Recargas";
                break;
            case REP_COMPRAS:
                mainPath += "\\Compras";
                break;
            case REP_VENTAS:
                mainPath += "\\Ventas";
                break;
            case REP_CLIENTES:
                mainPath += "\\Clientes";
                break;
            case REP_PROVEEDORES:
                mainPath += "\\Proveedores";
                break;
            case REP_EMPLEADOS:
                mainPath += "\\Empleados";
                break;
        }

        //Crear un archivo con la ruta de la carpeta donde se almacenarán los
        //reportes, según el tipo de reporte seleccionado
        File reportFolder = new File(documentsPath + mainPath);

        //Validar que NO exista la carpeta para los reportes
        if (!reportFolder.exists()) {
            //Si no existe, se crea la carpeta del respectivo reporte
            reportFolder.mkdir();
        }

        //Retornar la ruta predeterminada
        return reportFolder.getAbsolutePath();
    }

    //CONSTANTES BACKEND
    private int id_sucursal;
    private static final int REP_TRASVASOS = 1;
    private static final int REP_DEUDAS = 2;
    private static final int REP_RECARGAS = 3;
    private static final int REP_COMPRAS = 4;
    private static final int REP_VENTAS = 5;
    private static final int REP_CLIENTES = 6;
    private static final int REP_PROVEEDORES = 7;
    private static final int REP_EMPLEADOS = 8;

    // ========== FRONTEND ==========
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

        //Asignar la ruta predeterminada y hacer ineditable el campo
        txtUbicacion.setText("Predeterminado");
        txtUbicacion.setEditable(false);

        //Asignar la fuente al comboBox
        boxTipoReporte.setFont(Fuentes.segoe(16, PLANO));
        boxSucursales.setFont(Fuentes.segoe(16, PLANO));

        //Asignar los tooltip texts
        lblTipoReporte.setToolTipText("Determinar el tipo de información e "
                + "historial que mostrará el reporte generado.");
        lblUbicacion.setToolTipText(
                "<html>"
                + "<p>"
                + "Determinar la ubicación del archivo del reporte (PDF)."
                + "</p>"
                + "<p>"
                + "En caso de dejar el <b>campo vacío,</b> se creará en la <b>ruta por defecto.</b>"
                + "</p>"
                + "</html>"
        );
        lblSucursales.setToolTipText("Filtrar los reportes por sucursal.");

        //Asignar las propiedades al panel de reportes
        panelReportes.setBackground(BLANCO);
        panelReportes.setBorder(createLineBorder(GRIS));

        //Asignar los componentes al panel de reportes
        panelReportes.add(lblTitulo);
        panelReportes.add(lblTipoReporte);
        panelReportes.add(boxTipoReporte);
        panelReportes.add(lblUbicacion);
        panelReportes.add(txtUbicacion);
        panelReportes.add(btnUbicacion);
        panelReportes.add(lblSucursales);
        panelReportes.add(boxSucursales);
        panelReportes.add(btnCancelar);
        panelReportes.add(btnAceptar);

        this.add(informacion);
        this.add(panelReportes);
        this.add(fechaInicio);
        this.add(fechaFin);
    }

    /**
     * Función para aplicar los listener a los componentes
     */
    private void listeners() {
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                vaciarCampos();
            }
        });

        btnAceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                generarReporte();
            }
        });

        btnUbicacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                buscarCarpeta();
            }
        });

        boxTipoReporte.addItemListener((ItemEvent e) -> {
            //Obtener el index del item seleccionado
            int index = boxTipoReporte.getSelectedIndex();

            //Validar si se selecionó clientes o proveedor para deshabilitar
            //las fechas o activarlas en caso contrario
            switch (index) {
                case REP_DEUDAS:
                case REP_CLIENTES:
                case REP_PROVEEDORES:
                    boxSucursales.setEnabled(false);
                    fechaInicio.habilitar(false);
                    fechaFin.habilitar(false);
                    break;
                default:
                    boxSucursales.setEnabled(true);
                    fechaInicio.habilitar(true);
                    fechaFin.habilitar(true);
            }
        });
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     *
     * @param width Ancho del parent contenedor
     * @param height Alto del parent contenedor
     */
    protected void relocateComponents(int width, int height) {
        //Variables recurrentes
        Reportes.width = width;
        Reportes.panelHeight = height - padding * 2;

        //Posicionar la información al comienzo
        informacion.setLocation(padding, padding);

        //Validar el tamaño del ancho del contenedor
        if (Reportes.width < 600) {
            panelPequenio();

        } else if (Reportes.width < 900) {
            panelMediano();

        } else if (Reportes.width >= 900) {
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
    private void panelPequenio() {
        //Ancho de los paneles
        int w = Reportes.width - padding * 2;

        int infoH;
        if (w < 410) {
            infoH = 260;

        } else if (w < 548) {
            infoH = 230;

        } else {
            infoH = 200;
        }

        informacion.setSize(w, infoH);

        //Posición del panel de reportes
        int y = informacion.getHeight() + padding * 2;
        panelReportes.setBounds(padding, y, w, panelHeight);

        //Altura de las fechas
        int h = 320;
        //Posición de la primera fecha
        y += panelHeight + padding;
        fechaInicio.setBounds(padding, y, w, h);

        //Posición de la segunda fecha
        y += h + padding;
        fechaFin.setBounds(padding, y, w, h);

        //Tamaño del panel
        int absoluteHeight = padding * 5 + panelHeight + informacion.getHeight() + h * 2;
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 600px, pero menor a
     * 900 px
     */
    private void panelMediano() {
        //Obtener el tercio del panel contenedor
        panelHeight += 15;
        informacion.setSize(infoMaxWidth, panelHeight);

        //Posicionar el panel de reportes al lado de la información
        int x = padding * 2 + infoMaxWidth;
        //Asignar el ancho como el resto del espacio
        int w = width - x - padding;
        panelReportes.setBounds(x, padding, w, panelHeight);

        //Altura de las fechas
        int h = 320;
        //Posición vertical de las fechas
        int y = padding * 2 + panelHeight;
        //Ancho de las fechas
        w = width / 2 - padding * 3 / 2;
        fechaInicio.setBounds(padding, y, w, h);

        //Posición de la segunda fecha, al lado de la primera fecha
        x = padding * 2 + w;
        fechaFin.setBounds(x, y, w, h);

        int absoluteHeight = padding * 3 + h + panelHeight;
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 900 px
     */
    private void panelGrande() {
        //Aumentar el ancho y el alto en 15 px
        Reportes.width += 15;
        Reportes.panelHeight += 15;

        //Obtener el tercio del panel contenedor
        int thirdWidth = width / 3 - padding * 2;
        //Obtener el ancho del panel del info SIN superar el límite máximo
        int infoWidth = (thirdWidth < infoMaxWidth) ? thirdWidth : infoMaxWidth;
        //Asignar el tamaño
        informacion.setSize(infoWidth, panelHeight);

        //Altura de las fechas
        int h = panelHeight / 2 - padding / 2;
        //Posición en X de las fechas
        int x = width - infoWidth - padding;
        //Asignar la posición y tamaño de la primera fecha
        fechaInicio.setBounds(x, padding, infoWidth, h);

        //La altura será un padding debajo de la mitad
        int y = h + padding * 2 + 1;
        //Asignar la posición y tamaño de la segunda fecha
        fechaFin.setBounds(x, y, infoWidth, h);

        //La posición en x será al lado del panel del info
        x = padding * 2 + infoWidth;
        //Ancho del reporte, siendo este lo restante entre 
        //el panel de info y el ancho de las fechas
        int w = width - padding * 4 - infoWidth * 2;
        panelReportes.setBounds(x, padding, w, panelHeight);
    }

    /**
     * Función para reposicionar los componentes dentro del panel de reportes
     */
    private void relocateReporte() {
        boolean alturaBaja = panelHeight < 410;
        int txtHeight = (alturaBaja) ? 30 : 40;
        int gapV = 5;
        int repW = panelReportes.getWidth();
        int txtWidth = repW - padding * 2;

        //Asignar el tamaño a los componentes que requieran
        //declarar sus tamaños
        boxTipoReporte.setSize(txtWidth, txtHeight);
        txtUbicacion.setSize(txtWidth - txtHeight - gapV * 2, txtHeight);
        btnUbicacion.setSize(txtHeight, txtHeight);
        boxSucursales.setSize(txtWidth, txtHeight);

        //Posición del título del panel para los datos
        lblTitulo.setLocation(padding, padding);

        //Para posicionar los campos en el centro vertical del panel
        //primero se obtiene le punto medio del panel
        int middleY = panelReportes.getHeight() / 2;
        //Luego la suma de la altura de TODOS los componentes y sus labels
        int allHeights = txtHeight * 3 + lblTipoReporte.getHeight() * 3;
        //Finalmente calcular el punto medio, sumando, además, los padding utilizados 
        int positionY = middleY - (allHeights + ((alturaBaja) ? padding / 2 : padding) * 4) / 2;
        lblTipoReporte.setLocation(padding, positionY);

        //Posición vertical para el primer campo de texto
        positionY += lblTipoReporte.getHeight() + ((alturaBaja) ? gapV / 2 : gapV);
        boxTipoReporte.setLocation(padding, positionY);

        //Posición vertical del segundo label para su campo de texto
        positionY += txtHeight + ((alturaBaja) ? padding / 2 : padding);
        lblSucursales.setLocation(padding, positionY);

        //Posición vertical para el segundo campo de texto y su botón
        positionY += lblSucursales.getHeight() + ((alturaBaja) ? gapV / 2 : gapV);
        boxSucursales.setLocation(padding, positionY);

        //Posición vertical del tercer label para su campo de texto
        positionY += txtHeight + ((alturaBaja) ? padding / 2 : padding);
        lblUbicacion.setLocation(padding, positionY);

        //Posición vertical para el tercer campo de texto y su botón
        positionY = positionY + lblUbicacion.getHeight() + ((alturaBaja) ? gapV / 2 : gapV);
        int positionX = repW - padding - txtHeight;
        btnUbicacion.setLocation(positionX, positionY);
        txtUbicacion.setLocation(padding, positionY);

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
     * Función para vaciar todos los campos
     */
    protected static void vaciarCampos() {
        fechaInicio.vaciarCampos();
        fechaFin.vaciarCampos();
        boxTipoReporte.setSelectedIndex(0);
        txtUbicacion.setText("Predeterminado");
    }

    protected boolean actualizarDatos() {
        //Cargar los sucursales
        sucursales = ReadDB.getSucursales();
        //Retornar falso si las sucursales están vacías
        if (sucursales == null) {
            return false;
        }

        //Vaciar el comboBox de las sucursales
        boxSucursales.removeAllItems();

        //Agregar el primer index
        boxSucursales.addItem("Seleccionar");
        boxSucursales.addItem("Todos");

        //Guardar todas las sucursales
        for (Object[] sucursal : sucursales) {
            //Capitalizar las sucursales
            String capitalize = sucursal[1].toString();
            capitalize = capitalize.substring(0, 1).toUpperCase() + capitalize.substring(1).toLowerCase();
            //Agregar las sucursales
            boxSucursales.addItem(
                    new ComboItem(
                            capitalize,
                            sucursal[0].toString()
                    )
            );
        }
        //Retornar true al final
        return true;
    }

    protected void habilitarComponents(boolean estado) {
        boxTipoReporte.setEnabled(estado);
        boxSucursales.setEnabled(estado);
        txtUbicacion.setEnabled(estado);

        int box = boxTipoReporte.getSelectedIndex();
        if (box != REP_CLIENTES && box != REP_PROVEEDORES) {
            fechaInicio.habilitar(estado);
            fechaFin.habilitar(estado);
        } else {
            fechaInicio.habilitar(false);
            fechaFin.habilitar(false);
        }
    }

    //ATRIBUTOS
    private static int width, panelHeight;
    private static final int padding = 20;
    private static final int infoMaxWidth = 300;

    //COMPONENTES
    private static final PanelInfo informacion = new PanelInfo(ADMIN_REPORTES);
    private static final JPanel panelReportes = new JPanel(null);
    private static final PanelFecha fechaInicio = new PanelFecha("Fecha Inicio");
    private static final PanelFecha fechaFin = new PanelFecha("Fecha Fin");

    private static final Label lblTitulo = new Label("Generar Reportes", TITULO, 24);

    private static final Label lblTipoReporte = new Label("Tipo de reporte", PLANO, 18, true);
    private static final String[] opciones = {"Seleccionar", "Trasvasos", "Deudas",
        "Recargas", "Compras", "Ventas", "Clientes", "Proveedores", "Empleados"};
    private static final JComboBox boxTipoReporte = new JComboBox(opciones);

    private static Object[][] sucursales;
    private static final Label lblSucursales = new Label("Tipo de reporte", PLANO, 18, true);
    private static final JComboBox boxSucursales = new JComboBox();

    private static final Label lblUbicacion = new Label("Ubicación del reporte", PLANO, 18, true);
    private static final CampoTexto txtUbicacion = new CampoTexto("Ubicación del reporte", CUALQUIER);

    private static final Boton btnAceptar = new Boton("Guardar", CELESTE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);

    private static final BotonDirectory btnUbicacion = new BotonDirectory();

    //CLASES PRIVADAS
    /**
     * Clase para los paneles con las fechas
     */
    private static class PanelFecha extends JPanel {

        /**
         * Constructor de los paneles de fechas
         *
         * @param tipoFecha Titulo del panel
         */
        public PanelFecha(String tipoFecha) {
            this.setLayout(null);
            this.setBackground(BLANCO);
            this.setBorder(createLineBorder(GRIS));

            initComponets(tipoFecha);

            listener();
        }

        /**
         * Función para iniciar los componentes
         *
         * @param tipoFecha Título del panel
         */
        private void initComponets(String tipoFecha) {
            //Propiedades del título
            lblTitulo.setText(tipoFecha);
            lblTitulo.setSize(lblTitulo.getPreferredSize());

            //Asignar bordes grises
            dateChooser.setBorder(createLineBorder(GRIS));

            //Obtener la fecha del día, con su formato, y colocarlo
            //en el campo de texto
            txtFecha.setText(dateFormat.format(dateChooser.getCalendar().getTime()));

            this.add(lblTitulo);
            this.add(txtFecha);
            this.add(dateChooser);
        }

        /**
         * Función para asignar los listener a los componentes
         */
        private void listener() {
            dateChooser.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                //Validar que el dato NO sea nulo
                if (evt.getOldValue() != null) {
                    //Obtener la fecha seleccionada, con el formato, y asignarlo
                    //al campo de texto
                    txtFecha.setText(
                            dateFormat.format(dateChooser.getCalendar().getTime())
                    );
                }
            });
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
            int h = 30;
            int y = padding * 2 + lblTitulo.getHeight();
            txtFecha.setBounds(padding * 2, y, w, h);

            y += h + padding;
            w = this.getWidth() - padding * 4;
            h = this.getHeight() - y - padding * 2;
            dateChooser.setBounds(padding * 2, y, w, h);
        }

        /**
         * Función para vaciar los campos
         */
        protected void vaciarCampos() {
            txtFecha.setText("");
        }

        /**
         * Función para obtener la fecha seleccionada en el formato dd-MM-YYYY
         *
         * @return String de la fecha
         */
        protected String getSelectedDate() {
            return txtFecha.getText();
        }

        /**
         * Función para habilitar o deshabilitar los componentes del panel
         *
         * @param estado Booleano para el estado de los componentes
         */
        protected void habilitar(boolean estado) {
            this.txtFecha.setEnabled(estado);
        }

        /**
         * Función para reiniciar el date chooser y asignar la fecha actual
         */
        protected void resetDate() {
            dateChooser.setDate(new Date());
            txtFecha.setText(dateFormat.format(dateChooser.getCalendar().getTime()));
        }

        //ATRIBUTOS
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //COMPONENTES
        private final Label lblTitulo = new Label("", TITULO, 22);
        private final CampoTexto txtFecha = new CampoTexto("99-99-9999", FECHA);
        private final JCalendar dateChooser = new JCalendar();
    }

    private class ComboItem {

        private String key;
        private String value;

        public ComboItem(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
