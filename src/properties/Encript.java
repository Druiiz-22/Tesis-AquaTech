package properties;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class Encript {

    public static String encriptar(char[] password) {
        if (password.length > 0) {
            try {
                //Convertir la cadena de caracteres en String
                String pass = String.valueOf(password);

                //Instanciar el MessageDigest dentro del bloque Try-Catch
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                //Obtener el digest
                byte[] digest = md.digest(pass.getBytes(StandardCharsets.UTF_8));

                //Retornar la contraseña encriptada en sha256
                return DatatypeConverter.printHexBinary(digest).toLowerCase();

            } catch (NoSuchAlgorithmException ex) {
                Mensaje.msjError("No se pudo encriptar la clave.\nError: " + ex);
            }
        }
        return "";
    }

    public static String encriptar(String password) {
        if (!password.isEmpty()) {
            try {
                //Instanciar el MessageDigest dentro del bloque Try-Catch
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                //Obtener el digest
                byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));

                //Retornar la contraseña encriptada en sha256
                return DatatypeConverter.printHexBinary(digest).toLowerCase();

            } catch (NoSuchAlgorithmException ex) {
                Mensaje.msjError("No se pudo encriptar la clave.\nError: " + ex);
            }
        }
        return "";
    }
}
