package main;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Irvin Carapia Hernández
 */
public class ClienteSocket {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClienteSocket(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void enviarMensaje(String mensaje) throws IOException {
        out.writeObject(mensaje);
    }
    
    public void enviarObjeto(Serializable objeto) throws IOException {
        out.writeObject(objeto);
    }

    public Object recibirRespuesta() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    public ArrayList<String> obtenerNombresArchivosEnServidor() throws IOException, ClassNotFoundException {
        enviarMensaje("SOLICITAR_NOMBRES_ARCHIVOS");
        return (ArrayList<String>) in.readObject();
    }

    public void cerrarConexion() throws IOException {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
        if (socket != null) {
            socket.close();
        }
    }
}
