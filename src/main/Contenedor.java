package main;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;
import tabs.Inicio;
import tabs.Proveedores;
import tabs.admin.Admin;
import tabs.clientes.Clientes;
import tabs.compras.Compras;
import tabs.historial.Historial;
import tabs.ventas.Ventas;

/**
 * Clase para el contenedor de las pestañas del programa principal
 */
public class Contenedor extends JPanel implements properties.Constantes {

    /**
     * Constructor para la creación del panel contenedor de la ventana principal
     */
    public Contenedor() {
        this.setLayout(new BorderLayout());
        this.setBackground(properties.Colores.GRIS_CLARO);

        //Iniciar los componentes
        initComponents();
    }

    /**
     * Función para iniciar los componentes del contenedor
     */
    private void initComponents() {
        //Agregar el panel del inicio
        this.add(panelInicio, BorderLayout.CENTER);
    }

    /**
     * Función para reposicionar el panel contenedor y el panel dentro de sí
     */
    protected void relocateComponents() {

        java.awt.Dimension size = this.getSize();

        //Obtener el panel actual
        Component actualComponent = this.getComponent(0);

        //Comprobar cuál es el panel actual para redimensionarlo
        if (actualComponent.equals(panelInicio)) {
            panelInicio.relocateComponents(size);
        }
        if (actualComponent.equals(panelClientes)) {
            panelClientes.relocateComponents(size);
        }
        if (actualComponent.equals(panelVentas)) {
            panelVentas.relocateComponents(size);
        }
        if (actualComponent.equals(panelCompras)) {
            panelCompras.relocateComponents(size);
        }
        if (actualComponent.equals(panelHistorial)) {
            panelHistorial.relocateComponents(size);
        }
        if (actualComponent.equals(panelProv)) {
            panelProv.relocateComponents(size);
        }
        if (actualComponent.equals(panelAdmin)) {
            panelAdmin.relocateComponents(size);
        }

        //Actualizar el contenedor
        this.revalidate();
        this.repaint();
    }

    /**
     * Reemplazar el panel contenedor
     *
     * @param type Panel que será mostrado
     */
    protected void replacePanel(int type) {
        this.removeAll();

        //Validar la ventana que será desplegada y redimensionar
        //su tamaño
        switch (type) {
            case INICIO:
                this.add(panelInicio, BorderLayout.CENTER);
                panelInicio.relocateComponents(this.getSize());
                panelInicio.setButtonsUnselected();
                
                break;
            case CLIENTES:
                this.add(panelClientes, BorderLayout.CENTER);
                panelClientes.relocateComponents(this.getSize());
                break;
            case VENTAS:
                this.add(panelVentas, BorderLayout.CENTER);
                panelVentas.relocateComponents(this.getSize());
                break;
            case COMPRAS:
                this.add(panelCompras, BorderLayout.CENTER);
                panelCompras.relocateComponents(this.getSize());
                break;
            case HISTORIAL:
                this.add(panelHistorial, BorderLayout.CENTER);
                panelHistorial.relocateComponents(this.getSize());
                panelHistorial.actualizarDatos();
                break;
            case PROVEEDOR:
                this.add(panelProv, BorderLayout.CENTER);
                panelProv.relocateComponents(this.getSize());
                panelProv.actualizarDatos();
                break;
            case ADMIN:
                this.add(panelAdmin, BorderLayout.CENTER);
                panelAdmin.relocateComponents(this.getSize());
                panelAdmin.actualizarDatos();
                break;

        }

        //Actualizar el panel contenedor
        this.revalidate();
        this.repaint();
    }

    /**
     * Función para vaciar todos los campos de todos los paneles
     */
    protected void vaciarCampos(){
        panelClientes.vaciarCampos();
        panelVentas.vaciarCampos();
        panelHistorial.vaciarCampos();
        panelCompras.vaciarCampos();
        panelProv.vaciarCampos();
        panelAdmin.vaciarCampos(); 
    }
    
    //COMPONENTES
    private static final Inicio panelInicio = new Inicio();
    private static final Clientes panelClientes = new Clientes();
    private static final Ventas panelVentas = new Ventas();
    private static final Historial panelHistorial = new Historial();
    private static final Compras panelCompras = new Compras();
    private static final Proveedores panelProv = new Proveedores();
    private static final Admin panelAdmin = new Admin();
}
