package database;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import properties.Mensaje;

public class EmailCode {

    private static String emailEmpresa = "aquatech.tesis@gmail.com";
    private static String claveEmpresa = "xabidfizmcwimsri";
    private static String destino, asunto, contenido;
    private static int codigo;

    private static Properties prop;
    private static Session sesion;
    private static MimeMessage mCorreo;

    /**
     * Función para preparare el correo que será enviado, asignando los
     * servidores y propiedades.
     */
    private static void crearCorreo() throws AddressException, MessagingException {
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.port", "587");
        prop.setProperty("mail.smtp.user", emailEmpresa);
        prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.setProperty("mail.smtp.auth", "true");

        sesion = Session.getDefaultInstance(prop);


            mCorreo = new MimeMessage(sesion);
            mCorreo.setFrom(new InternetAddress(emailEmpresa));

            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            mCorreo.setSubject(asunto, "UTF-8");
            mCorreo.setText(contenido, "UTF-8", "html");
            
    }

    /**
     * Función para enviar el correo electrónico, desde el correo de orígen
     * hacia el correo destinario.
     */
    private static void enviarCorreo() throws NoSuchProviderException, MessagingException {
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(emailEmpresa, claveEmpresa);
            transporte.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            transporte.close();
    }

    /**
     * Función para enviar un código de seguridad a un correo para validar que
     * este sea válido y poder ser registrado en una cuenta nueva o para
     * asignarlo como el nuevo correo de una cuenta existente.
     *
     * @param correo Correo destinario
     * @param codigo Código de seguridad
     * @return 
     */
    public static boolean comprobarCorreo(String correo, int codigo) {
        EmailCode.destino = correo;
        EmailCode.codigo = codigo;
        prop = new Properties();

        asunto = "Verificación de correo AquaTech";
        contenido = "<html>"
                + "<div style='margin:auto;width:70%;padding:10px;font-family:Arial,Helvetica,sans-serif;'>"
                + "<center>"
                + "<h2>código de verificación:</h2>"
                + "<h1>" + EmailCode.codigo + "</h1>"
                + "</center>"
                + "<p>"
                + "Bienvenido a AquaTech, se acaba de enviar un código de "
                + "seguridad y debe completar este paso para crear su cuenta. "
                + "Confirme que esta sea su dirección de correo electrónico "
                + "correcto para ser usada en su nueva cuenta."
                + "</p>"
                + "<p><i>"
                + "Los código de verificación <b>caducan después de 30 minutos.</b>"
                + "</i></p>"
                + "<p>Gracias,<br> AquaTech.</p>"
                + "</div>"
                + "</html>";
        
        //Intentar crear y enviar el correo
        try {
            crearCorreo();
            enviarCorreo();
            return true;
            
        } catch (MessagingException e) {
            Mensaje.msjError("Hubo un error al crear el correo.\nPor favor, verifique su conexión con internet y los datos ingresados.");
            return false;
        }
    }

    /**
     * Función para enviar un código de seguridad a un correo para restaurar la
     * contraseña de la cuenta
     *
     * @param correo Correo destinario
     * @param codigo Código de seguridad
     * @return 
     */
    public static boolean recuperarCuenta(String correo, int codigo) {
        EmailCode.destino = correo;
        EmailCode.codigo = codigo;
        prop = new Properties();

        asunto = "Recuperar cuenta AquaTech";
        contenido = "<html>"
                + "<div style='margin: auto; width: 70%; padding: 10px; font-family: Arial, Helvetica, sans-serif;'>"
                + "<center>"
                + "<h2>Codigo de verificación:</h2>"
                + "<h1>" + EmailCode.codigo + "</h1>"
                + "</center>"
                + "<p>"
                + "Has recibido este correo porque has solicitado restaurar la "
                + "contraseña de tu cuenta. Se envió un código de seguridad y "
                + "deberá completar el paso para recuperar el acceso a su cuenta."
                + "</p>"
                + "<p><i>"
                + "Los código de verificación caducan después de 30 minutos."
                + "</i></p>"
                + "<p>"
                + "En caso de que no haya solicitado este correo, ignorelo por completo."
                + "</p>"
                + "<p>"
                + "Gracias,<br>"
                + "AquaTech."
                + "</p>"
                + "</div>"
                + "</html>";
        
        try {
            crearCorreo();
            enviarCorreo();
            return true;
            
        } catch (Exception e) {
            Mensaje.msjError("Hubo un error al crear el correo.\nPor favor, verifique su conexión con internet y los datos ingresados.");
            return false;
        }
    }
}
