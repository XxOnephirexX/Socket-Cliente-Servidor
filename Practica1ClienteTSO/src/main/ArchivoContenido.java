package main;

import java.io.Serializable;

/**
 * Clase que representa el contenido de un archivo.
 *
 * @author Irvin Carapia Hernández
 */
public class ArchivoContenido implements Serializable {

    /**
     * Contenido del archivo.
     */
    private byte[] contenido;

    /**
     * Constructor que inicializa el contenido del archivo.
     *
     * @param contenido Contenido del archivo.
     */
    public ArchivoContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene el contenido del archivo.
     *
     * @return Contenido del archivo.
     */
    public byte[] getContenido() {
        return contenido;
    }
}
