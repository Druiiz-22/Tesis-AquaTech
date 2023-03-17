package properties;

import java.awt.Font;
import static java.awt.Font.PLAIN;

/**
 * Clase para obtener la fuente, que se utilizará durante todo el software, con
 * menos parámetros.
 */
public class Fuentes {

    /**
     * Función para obtener la fuente SegoeUI con un tamaño y estilo
     * determinado.
     *
     * @param tamaño Tamaño del a fuente.
     * @param estilo Estilo que tendrá la fuente (Normal, negrita, curveada).
     * @return
     */
    public static Font segoe(int tamaño, int estilo) {
        return new Font("Segoe UI", estilo, tamaño);
    }

    /**
     * Función para obtener la fuente SegoeUI Black, siendo este más gruesa que
     * la fuente normal en negrita.
     *
     * @param tamaño Tamaño que tendrá la fuente.
     * @return
     */
    public static Font segoeBlack(int tamaño) {
        return new Font("Segoe UI Black", PLAIN, tamaño);
    }

    /**
     * Función para obtener la fuente SegoeUI Semibold, siendo este con un
     * grosor mayor que la fuente normal, pero menor que la fuente normal en
     * negrita.
     *
     * @param tamaño Tamaño que tendrá la fuente.
     * @return
     */
    public static Font segoeSemibold(int tamaño) {
        return new Font("Segoe UI Semibold", PLAIN, tamaño);
    }
}
