package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.PanelInfo;
import database.AdminDB;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.Frame;
import main.Run;
import static javax.swing.BorderFactory.createLineBorder;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjInformativo;

/**
 * Clase para la creación del panel de respaldo de bases de datos, en el
 * administrador
 */
public class Respaldo extends JPanel implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    /**
     * Función para buscar el archivo .sql para importarlo en la base de datos
     */
    private void buscarRespaldo() {
        //Crear el componentes FileChooser
        JFileChooser chooser = new JFileChooser();
        //Asignar el título
        chooser.setDialogTitle("Selecciona la base de datos");

        //Asignar que sea de SOLO se seleccionen archivos
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //Filtro de archivos
        chooser.setFileFilter(new FileNameExtensionFilter("Base de datos MySQL (*.sql)", "sql"));

        //Abrir el FileChooser y obtener la opción seleccionadda
        int opcion = chooser.showOpenDialog(this);

        //Validar que se seleccionó un archivo
        if (opcion == JFileChooser.APPROVE_OPTION) {

            //Obtener el nombre completo del archivo
            String fileName = chooser.getSelectedFile().getName();

            //Validar que la extensión, del archivo seleccionado, sea sql,
            //comprobando que éste termine en "*.sql"
            if (fileName.endsWith("sql")) {

                //Obtener la ruta completa del archivo seleccionado
                respaldoSQL = new File(chooser.getSelectedFile().getAbsolutePath());

                //Mostrar el archivo seleccioando en el campo de texto
                txtArchivo.setText(this.respaldoSQL.getName());

            } else {
                txtArchivo.setText("");
                msjError("El archivo seleccionado debe ser una base de datos (*.sql).");
            }
        }
    }

    /**
     * Función para buscar la carpeta donde se alojará el respaldo de la base de
     * datos
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
     * Función para exportar la base de datos de la nube, por respaldo o por
     * copia de seguridad antes de importar alguna base de datos
     *
     * @param copiaSeguridad Booleano para comprobar de que se hará una copia de
     * seguridad (TRUE) o un simple respaldo (FALSE).
     *
     * @return TRUE si se logró realizar la exportación, FALSE en caso
     * contrario.
     */
    private boolean exportar(Boolean copiaSeguridad) {
        //Obtener los valores de la ruta y el nombre
        String fileName = txtNombre.getText().trim();
        String filePath = txtUbicacion.getText();

        //Obtener la ruta del archivo según si es predeterminada, copia de 
        //seguridad o personalizada
        filePath = (filePath.toUpperCase().equals("PREDETERMINADO") || copiaSeguridad)
                ? getDefaultFolder(copiaSeguridad)
                : filePath;

        //Validar si el nombre está vacío o es una copia de seguridad
        if (fileName.isEmpty() || copiaSeguridad) {

            //Asignar el nombre por defecto al respaldo
            fileName = getDefaultName();

        } else {
            //Validar si el nombre ya se le ingresó la extensión ".sql", sino,
            //se le coloca al final del nombre ingresado
            fileName = (fileName.endsWith(".sql")) ? fileName : fileName + ".sql";
        }

        //Mostrar mensaje de confirmación de la exportación o importación
        boolean realizar;

        if (copiaSeguridad) {

            //Mensaje de advertencia
            String msj
                    = "<html><div style='font-size:13pt;font-weight:400;'>"
                    + "<p style='margin-bottom:5px'><b>¿Está seguro de realizar la exportación?</b></p>"
                    + "<p>Se hará una copia de seguridad antes de la importación</p>"
                    + "</p>y <b style='color:red'>se borrará la base de datos</b>"
                    + " actual, para importar el</p>"
                    + "<p>respaldo seleccionado.</p>"
                    + "</div></html>";

            int opcion = showConfirmDialog(
                    null,
                    msj,
                    "Advertencia",
                    YES_NO_OPTION,
                    WARNING_MESSAGE
            );

            realizar = opcion == YES_OPTION;

        } else {
            realizar = msjYesNo("¿Está seguro de realizar la exportación?");
        }

        //Validar si se realizará o no la acción
        if (realizar) {
            //Validar la contraseña del usuario y si éste es admin
            if (AdminDB.validateAdminUser()) {

                //Crear un archivo con la ruta de la carpeta para validar
                //nuevamente de que exista ANTES de realizar la exportación
                File folderTest = new File(filePath);

                //Comprobar la existencia de la ruta
                if (folderTest.exists()) {

                    //Intentar la exportación de la base de datos
                    if (AdminDB.exportDB(fileName, filePath)) {

                        //Mostrar el mensaje de éxito cuando NO sea un respaldo
                        //por copia de seguridad
                        if (!copiaSeguridad) {
                            abrirCarpeta(fileName, filePath);
                        }
                        return true;
                    }

                } else {
                    msjError("No se encontró la carpeta seleccionada.\nPor favor"
                            + "vuelva a seleccionar la carpeta para el respaldo.");
                }
            }
        }

        return false;
    }

    /**
     * Función para importar un respaldo de una base de datos a la base de datos
     * en la nube.
     */
    private void importar() {
        String path = txtArchivo.getText().trim();

        //Validar que el campo de la ruta NO esté vacío
        if (!path.isEmpty()) {
            //Validar que coincidan el campo de la ruta y la ruta almacenada
            if (path.equals(respaldoSQL.getName())) {

                //Validar nuevamente que exista el archivo en la ruta especificada
                if (respaldoSQL.exists()) {

                    //Intentar la exportación de seguridad
                    if (exportar(true)) {

                        //Intentar la importación de la base de datos
                        if (AdminDB.importDB(respaldoSQL.getAbsolutePath())) {

                            String msj = "<html>"
                                    + "<p>La base de datos se ha importado con éxito.</p>"
                                    + "<p>Por seguridad, el programa se cerrará.</p>"
                                    + "<p style='margin-top:8px'>"
                                    + "Asegurece que <b>TODOS los dispositivos</b> "
                                    + "conectados<br>al programa, se reinicien y "
                                    + "actualicen."
                                    + "</p>"
                                    + "</html>";

                            //Mensaje de éxito
                            msjInformativo(msj);

                            //Cerrar el programa e ir al login
                            Run.cerrarPrograma();

                            //Terminar de ejecutar el programa
                            System.exit(0);

                        }

                    }
                } else {
                    msjError("No se encontró el archivo seleccionado en"
                            + "la ruta especificada.\nPor favor, vuelva a "
                            + "seleccionar la base de datos (*.sql).");
                }
            } else {
                msjError("El nombre del archivo NO coincide con la ruta "
                        + "seleccionada.\nPor favor, vuelva a seleccionar la"
                        + "base de datos (*.sql).");
            }
        } else {
            msjError("No ha seleccionado ninguna base de datos para "
                    + "importar.\nPor favor, seleccione una base de datos (*.sql).");
        }
    }

    /**
     * Función para obtener la ruta de la carpeta predeterminada para los
     * respaldos de bases de datos y de la carpeta de copia de seguridad de base
     * de datos, antes de importar una base de datos.
     *
     * @param copiaSeguridad Booleano para comprobar de que se hará una copia de
     * seguridad (TRUE) o un simple respaldo (FALSE)
     *
     * @return ruta de la carpeta
     */
    private String getDefaultFolder(boolean copiaSeguridad) {

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

        //Nombre de la carpeta principal donde se almacenan los respaldos
        mainPath += "\\Respaldos";

        //Crear un archivo en la ruta de Documentos + La carpeta de respaldos
        File backupFolder = new File(documentsPath + mainPath);

        //Validar que NO exista la carpeta principal de respaldos
        if (!backupFolder.exists()) {
            //Si no existe, crea la carpeta en "/Documents/AquaTech"
            backupFolder.mkdir();
        }

        //Validar que la función sea para una copia de seguridad antes de 
        //importar una base de datos o para un respaldo de la base de datos
        if (copiaSeguridad) {

            //Crear un archivo con la ruta de la carpeta de copias de seguridad
            //antes de realizar una importación
            File securityFolder = new File(documentsPath + mainPath + "\\Respaldo de Seguridad");

            //Validar que NO exista la carpeta para las copias de seguridad
            if (!securityFolder.exists()) {
                //Si no existe, se crea la carpeta para las copias de seguridad
                securityFolder.mkdir();
            }

            //Retornar la ruta de la carpeta de las copias de seguridad
            return securityFolder.getAbsolutePath();

        } else {
            //Retornar la ruta de la carpeta de respaldos
            return backupFolder.getAbsolutePath();
        }
    }

    /**
     * Obtener el nombre por defecto de los reportes a exportar
     *
     * @return Formato: AQUATECH_DB_año_mes_dia_hora_minuto_segundo.sql
     */
    private String getDefaultName() {
        //Objeto tipo date para obtener la fecha actual
        Date date = new java.util.Date();
        //Objeto de tipo calendario para obtener los datos de la fehca
        Calendar calendario = new java.util.GregorianCalendar();
        //Asignar la fecha actual
        calendario.setTime(date);

        //Dividir la información de la fecha
        int year = calendario.get(java.util.Calendar.YEAR);
        int month = calendario.get(java.util.Calendar.MONTH + 1);
        int day = calendario.get(java.util.Calendar.DAY_OF_MONTH);
        int hour = calendario.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendario.get(java.util.Calendar.MINUTE);
        int second = calendario.get(java.util.Calendar.SECOND);

        //Validar que los datos sean de uno o dos dígitos.
        //En caso de ser de un dígito, se le coloca un cero antes del 
        //valor. Ex: 9 -> "09"
        String mes = (month < 10) ? ("0" + month) : String.valueOf(month);
        String dia = (day < 10) ? ("0" + day) : String.valueOf(day);
        String hora = (hour < 10) ? ("0" + hour) : String.valueOf(hour);
        String minuto = (minute < 10) ? ("0" + minute) : String.valueOf(minute);
        String segundo = (second < 10) ? ("0" + second) : String.valueOf(second);

        //Nombre predeterminado de las base de datos
        String name = "AQUATECH_DB_";
        //Fecha cuando se realizó el respaldo
        String fecha = year + "_" + mes + "_" + dia + "_" + hora + "_" + minuto + "_" + segundo + ".sql";

        //Retornar el nombre con la fecha
        return name + fecha;
    }

    /**
     * Función para mostrar un mensaje de éxito del respaldo y dar la opción de
     * abrir la carpeta donde se aloja el respaldo.
     *
     * @param fileName Nombre del respaldo generado
     * @param filePath Ruta del respaldo generado
     */
    private void abrirCarpeta(String fileName, String filePath) {

        String msj
                = "<html>"
                + "<p><b>¡La base de datos se ha exportado con éxito!</b></p>"
                + "<p>Nombre: " + fileName + "</p>"
                + "<p>Ruta: " + filePath + "</p><br>"
                + "<p><b>¿Desea abrir su ubicación?</b></p>"
                + "</html>";

        String[] botones = {"Abrir ubicación", "Finalizar"};

        int opcion = javax.swing.JOptionPane.showOptionDialog(
                null,
                msj,
                "Reporte PDF generado",
                0,
                javax.swing.JOptionPane.INFORMATION_MESSAGE,
                null,
                botones,
                botones[1]
        );

        //Validar si se va abrir o no la carpeta
        if (opcion == 0) {

            //Archivo que contiene la ruta del documento creado
            File folder = new File(filePath);

            //Comprobar si el dispositivo donde se ejecuta el programa, es 
            //compatible con la clase "Desktop" para poder abrir una carpeta
            if (Desktop.isDesktopSupported()) {
                try {
                    //Intentar abrir la carpeta
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(folder);

                } catch (IOException e) {
                    msjError("No se pudo abrir la ruta:\n"
                            + folder.getAbsolutePath() + "\nPor favor, verifique"
                            + "la existencia de la carpeta y el respaldo."
                    );
                }
            } else {
                msjError("La clase 'Desktop' no es compatible con la "
                        + "plataforma actual.\nNo se podrá abrir la carpeta"
                        + "contenedora del respaldo");
            }
        }
    }

    //ATRIBUTOS BACKEND
    private static File respaldoSQL;

    // ========== FRONTEND ==========
    /**
     * Constructor del panel de los ajustes
     */
    public Respaldo() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {

        //Asignar el nombre y ubicación predeterminado
        this.txtUbicacion.setText("Predeterminado");

        //Hacer ineditable las rutas de la carpeta y del archivo
        this.txtUbicacion.setEditable(false);
        this.txtArchivo.setEditable(false);

        //Propiedades de los paneles
        panelExporte.setBackground(BLANCO);
        panelExporte.setBorder(createLineBorder(GRIS));

        panelImporte.setBackground(BLANCO);
        panelImporte.setBorder(createLineBorder(GRIS));

        //Asignar los tooltips
        lblUbicacion.setToolTipText(
                "<html>"
                + "<p>"
                + "Determinar la ubicación donde se creará el respaldo de la base de datos."
                + "</p>"
                + "<p>"
                + "En caso de dejar el <b>campo vacío,</b> se creará en la <b>ruta por defecto.</b>"
                + "</p>"
                + "</html>"
        );
        lblNombre.setToolTipText(
                "<html>"
                + "<p>"
                + "Determinar el nombre que tendrá el archivo del respaldo de la base de datos."
                + "</p>"
                + "<p>"
                + "En caso de dejar el <b>campo vacío,</b> se asginará el <b>nombre por defecto.</b>"
                + "</p>"
                + "</html>"
        );
        lblArchivo.setToolTipText("Determinar el archivo seleccionado que será importado a la base de datos.");

        //Añadir los componentes
        panelExporte.add(lblTituloExp);
        panelExporte.add(lblUbicacion);
        panelExporte.add(txtUbicacion);
        panelExporte.add(lblNombre);
        panelExporte.add(txtNombre);
        panelExporte.add(btnUbicacionExp);
        panelExporte.add(btnExportar);

        panelImporte.add(lblTituloImp);
        panelImporte.add(lblArchivo);
        panelImporte.add(txtArchivo);
        panelImporte.add(btnUbicacionImp);
        panelImporte.add(btnImportar);

        this.add(informacion);
        this.add(panelExporte);
        this.add(panelImporte);

    }

    /**
     * Función para aplicar los listener a los componentes
     */
    private void listeners() {
        btnUbicacionExp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                buscarCarpeta();
            }
        });
        btnUbicacionImp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                buscarRespaldo();
            }
        });

        btnExportar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        main.Frame.openGlass(0);
                        exportar(false);
                        main.Frame.closeGlass();
                    }
                }.start();
            }
        });

        btnImportar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        main.Frame.openGlass(0);
                        importar();
                        main.Frame.closeGlass();
                    }
                }.start();
            }
        });
    }

    /**
     * Función para reposicionar los componentes
     *
     * @param width Ancho del parent content
     * @param height Alto del parent content
     */
    protected void relocateComponents(int width, int height) {
        Respaldo.width = width;
        infoHeight = height - padding * 2;

        //Tamaño para los paneles
        expH = (int) (height * 0.55 - padding * 3 / 2);
        impH = (int) (height * 0.45 - padding * 3 / 2);

        informacion.setLocation(padding, padding);

        if (width < 700) {
            panelPequenio();

        } else if (width >= 700) {
            this.setPreferredSize(new Dimension(width, height));
            panelGrande();
        }

        informacion.relocateComponents(CUALQUIER);
        relocateExporte();
        relocateImporte();
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño menor a 600 px
     */
    private void panelPequenio() {
        int panelW = width - padding * 2;

        //Tamaño de la información
        int infoH;
        if (width < 452) {
            infoH = 340;

        } else if (width <= 650) {
            infoH = 280;

        } else {
            infoH = 260;
        }

        informacion.setSize(panelW, infoH);

        //Tamaño del panel de exportación
        expH = 270;
        int y = padding * 2 + informacion.getHeight();
        panelExporte.setBounds(padding, y, panelW, expH);

        impH = 210;
        y = y + expH + padding;
        panelImporte.setBounds(padding, y, panelW, impH);

        int absoluteHeight = padding * 4 + informacion.getHeight() + expH + impH;
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 600px
     */
    private void panelGrande() {
        width += 15;
        impH += 8;
        expH += 7;
        infoHeight += 15;

        informacion.setSize(infoMaxWidth, infoHeight);

        int x = infoMaxWidth + padding * 2;
        int w = width - x - padding;
        panelExporte.setBounds(x, padding, w, expH);

        int y = padding * 2 + expH;
        panelImporte.setBounds(x, y, w, impH);
    }

    /**
     * Función para reposicionar los componentes dentro del panel de exportación
     */
    private void relocateExporte() {
        lblTituloExp.setLocation(padding, padding);

        int expW = panelExporte.getWidth();
        int panelH = panelExporte.getHeight();
        int gap = 5;
        int h = (panelH < 210) ? 28 : 35;

        int w = expW - padding * 2 - h - gap;
        btnUbicacionExp.setSize(h, h);
        txtUbicacion.setSize(w, h);

        int y = padding * 3 / 2 + lblTituloExp.getHeight();
        lblUbicacion.setLocation(padding, y);

        y += lblUbicacion.getHeight() + gap;
        txtUbicacion.setLocation(padding, y);

        int x = expW - padding - h;
        btnUbicacionExp.setLocation(x, y);

        y += gap + h;
        lblNombre.setLocation(padding, y);

        //Validar si la altura del panel permite agregar el botón debajo del
        //campo del nombre, o si debe colocarlse a su derecha
        if (panelH < 260) {
            if (expW < 450) {
                btnExportar.setText("Guardar");
            } else {
                btnImportar.setText("Guardar Respaldo");
            }

            w = btnExportar.getPreferredSize().width + 20;
            x = expW - w - padding;
            y += lblNombre.getHeight() + gap;
            btnExportar.setBounds(x, y, w, h);

            w = x - padding - gap;
            txtNombre.setBounds(padding, y, w, h);

        } else {
            y += lblNombre.getHeight() + gap;
            w = expW - padding * 2;
            txtNombre.setBounds(padding, y, w, h);

            btnExportar.setText("Guardar Respaldo");
            w = btnExportar.getPreferredSize().width + 30;
            x = expW - w - padding;
            y = panelH - padding - h;
            btnExportar.setBounds(x, y, w, h);
        }
    }

    /**
     * Función para reposicionar los componentes dentro del panel de importación
     */
    private void relocateImporte() {
        lblTituloImp.setLocation(padding, padding);

        int impW = panelImporte.getWidth();
        int panelH = panelImporte.getHeight();
        int gap = 5;
        int h = 35;

        int y = padding * 2 + lblTituloImp.getHeight();
        lblArchivo.setLocation(padding, y);

        y += lblArchivo.getHeight() + gap;
        int w, x;
        if (panelH < 200) {
            if (impW < 450) {
                btnImportar.setText("Importar");
            } else {
                btnImportar.setText("Importar respaldo");
            }
            w = btnImportar.getPreferredSize().width + 20;
            x = impW - w - padding;
            btnImportar.setBounds(x, y, w, h);

            x = x - gap - h;
            btnUbicacionImp.setBounds(x, y, h, h);

            w = x - padding - gap;
            txtArchivo.setBounds(padding, y, w, h);

        } else {
            w = impW - padding * 2 - h - gap;
            txtArchivo.setSize(w, h);
            txtArchivo.setLocation(padding, y);

            x = impW - padding - h;
            btnUbicacionImp.setSize(h, h);
            btnUbicacionImp.setLocation(x, y);

            btnImportar.setText("Importar respaldo");
            w = btnImportar.getPreferredSize().width + 30;
            x = impW - w - padding;
            y = panelH - padding - h;
            btnImportar.setBounds(x, y, w, h);
        }
    }

    protected static void vaciarCampos() {
        txtUbicacion.setText("");
        txtNombre.setText("");
        txtArchivo.setText("");
    }

    protected void habilitarComponents(boolean estado) {
        txtUbicacion.setEnabled(estado);
        txtNombre.setEnabled(estado);
        txtArchivo.setEnabled(estado);
    }

    //ATRIBUTOS
    private static int width, infoHeight, expH, impH;
    private static final int padding = 20;
    private static final int infoMaxWidth = 300;

    //COMPONENTES
    private static final PanelInfo informacion = new PanelInfo(ADMIN_RESPALDO);

    private static final JPanel panelExporte = new JPanel(null);
    private static final Label lblTituloExp = new Label("Exportar BDD", TITULO, 22);
    private static final Label lblUbicacion = new Label("Ubicación", PLANO, 16, true);
    private static final CampoTexto txtUbicacion = new CampoTexto("Seleccionar la ubicación", CUALQUIER);
    private static final Label lblNombre = new Label("Nombre", PLANO, 16, true);
    private static final CampoTexto txtNombre = new CampoTexto("Nombre del archivo", CUALQUIER);
    private static final BotonDirectory btnUbicacionExp = new BotonDirectory();
    private static final Boton btnExportar = new Boton("Guardar respaldo", VERDE);

    private static final JPanel panelImporte = new JPanel(null);
    private static final Label lblTituloImp = new Label("Importar BDD", TITULO, 22);
    private static final Label lblArchivo = new Label("Archivo Seleccionado", PLANO, 16, true);
    private static final CampoTexto txtArchivo = new CampoTexto("Nombre del archivo", CLAVE);
    private static final BotonDirectory btnUbicacionImp = new BotonDirectory();
    private static final Boton btnImportar = new Boton("Importar respaldo", CELESTE);
}

/**
 * Clase para los botones de selección de archivo o ruta
 */
class BotonDirectory extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor de los botones de selección de archivo o ruta
     */
    public BotonDirectory() {
        this.setLayout(new BorderLayout());
        this.setBackground(BLANCO);
        this.setBorder(createLineBorder(GRIS_OSCURO));

        lblPuntos.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        lblPuntos.setVerticalAlignment(javax.swing.JLabel.CENTER);
        lblPuntos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(lblPuntos, BorderLayout.CENTER);

        //Mouse Listener para el color del panel cuando se presione y 
        //se suelte el mouse
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(BLANCO);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(GRIS);
            }
        });
    }

    //COMPONENTES
    private final Label lblPuntos = new Label(". . .", PLANO, 14);
}
