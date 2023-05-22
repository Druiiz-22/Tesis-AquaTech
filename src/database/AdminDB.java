package database;

import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import main.Frame;
import main.Run;
import properties.Encript;
import static properties.Mensaje.msjError;

public class AdminDB implements properties.Constantes {

    public static boolean exportDB(String fileName, String filePath, boolean copiaSeguridad) {

        return true;
    }

    public static boolean importDB(String filePath) {

        return true;
    }

    /**
     * Función para pedir la contraseña del usuario administrador iniciado
     * sesión
     *
     * @return
     */
    public static boolean validateAdminUser() {

        //Obtener el rol del usuario de la sesión iniciada
        int rol = Frame.getUserRol();

        //Validar que el rol del usuario SEA administrador
        if (rol == ADMINISTRADOR || rol == ENCARGADO) {

            //Panel de ingreso de contraseña para el JOptionPane
            JPanel panel = new JPanel();
            JLabel titulo = new JLabel("Ingrese su contraseña");
            JPasswordField clave = new JPasswordField(18);
            JCheckBox mostrar = new JCheckBox("Mostrar contraseña");
            String[] opciones = {"Aceptar", "Cancelar"};

            //Asignar un padding inferior al título
            titulo.setBorder(createEmptyBorder(0, 0, 2, 0));
            //Asignar un padding vertical al checkbox
            mostrar.setBorder(createEmptyBorder(4, 0, 4, 0));

            //Propiedades del campo de clave
            clave.setEchoChar('•');
            //Asignar el layout al panel
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            //Añadir los componentes
            panel.add(titulo);
            panel.add(clave);
            panel.add(mostrar);

            //Listener para cuando se presione el checkbox
            mostrar.addActionListener((e -> {
                if (mostrar.isSelected()) {
                    //Mostrar la clave
                    clave.setEchoChar((char) 0);
                } else {
                    //Ocultar los carácteres por asteriscos
                    clave.setEchoChar('•');
                }
            }));

            //Mostrar el mensaje para el ingreso de contraseña
            int opcion = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Ingrese su contraseña",
                    JOptionPane.NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            //Validar que se presionó ACEPTAR
            if (opcion == 0) {

                //Encriptar la clave obtenida
                String password = Encript.encriptar(clave.getPassword());

                //Validar que coincida la clave ingresada con el usuario que 
                //tiene la sesión iniciada
                Object[] cuenta = ReadDB.getUser(Frame.getUserIdentified(), password);

                //Validar que se haya obtenido respuesta
                if (cuenta != null) {
                    
                    //Guardar el rol obtenido de la base de datos
                    int rol_2 = (int) (cuenta[0]);
                    
                    //Validar que el usuario obtenido REALMENTE sea administrador
                    if (rol_2 == ADMINISTRADOR || rol_2 == EMPLEADO) {
                        intentos = 0;
                        return true;

                    } else {
                        msjError("Su usuario no cuenta con los permisos para "
                                + "realizar esta acción.\nPor seguridad, el "
                                + "programa se cerrará.");
                        //Cerrar el programa
                        Run.cerrarPrograma();

                        //Terminar de ejecutar el programa
                        System.exit(0);
                    }
                } else {
                    //Sumar un intento fallido 
                    intentos++;

                    if (intentos >= 3) {
                        //Mostrar un mensaje de error
                        msjError(
                                "Ha superado el límite de intentos de verificación.\n"
                                + "El programa se cerrará."
                        );

                        //Cerrar el programa
                        Run.cerrarPrograma();

                        //Terminar de ejecutar el programa
                        System.exit(0);
                    }
                }
            }
        } else {
            msjError("Su usuario no cuenta con los permisos para realizar esta "
                    + "acción.\nPor seguridad, el programa se cerrará.");
            
            //Cerrar el programa
            Run.cerrarPrograma();

            //Terminar de ejecutar el programa
            System.exit(0);
        }
        return false;
    }

    private static int intentos = 0;
}
