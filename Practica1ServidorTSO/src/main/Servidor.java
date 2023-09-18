package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Irvin Carapia Hernández
 */
public class Servidor {

    public static final int PUERTO = 8000;
    public static final String RUTA_SERVIDOR = "archivos_servidor/";

    // Método para obtener la lista de nombres de archivos en la ruta del servidor
    public static List<String> obtenerNombresArchivosEnRuta() {
        List<String> archivosEnRuta = new ArrayList<>();
        File[] archivos = new File(RUTA_SERVIDOR).listFiles();
        for (File archivo : archivos) {
            if (archivo.isFile()) {
                archivosEnRuta.add(archivo.getName());
            }
        }
        return archivosEnRuta;
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor levantado, escuchando en el puerto: " + PUERTO);

            // Verificar si la carpeta "archivos_servidor" existe, si no, créala
            File carpetaServidor = new File(RUTA_SERVIDOR);
            if (!carpetaServidor.exists()) {
                carpetaServidor.mkdir();
                System.out.println("Carpeta 'archivos_servidor' creada.");
            }

            while (true) {
                try (Socket socket = serverSocket.accept(); ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                    String operacion = (String) in.readObject();

                    String respuesta = "";

                    switch (operacion) {
                        case "LECTURA" -> {
                            String nombreArchivo = (String) in.readObject();
                            File file = new File(RUTA_SERVIDOR + nombreArchivo);
                            if (file.exists() && !file.isDirectory()) {
                                byte[] fileBytes = new byte[(int) file.length()];
                                try (InputStream fileIn = new FileInputStream(file)) {
                                    fileIn.read(fileBytes);
                                }
                                ArchivoContenido archivoContenido = new ArchivoContenido(fileBytes);
                                out.writeObject(archivoContenido); // Envía el objeto serializado
                            } else {
                                respuesta = "Error: el archivo no existe.";
                            }
                        }

                        case "ESCRITURA" -> {
                            String nombreArchivoEscritura = (String) in.readObject();
                            // Leer el contenido editado del documento desde el cliente
                            ArchivoContenido archivoContenido = (ArchivoContenido) in.readObject();

                            File destino = new File(RUTA_SERVIDOR + nombreArchivoEscritura);
                            try (OutputStream fileOut = new FileOutputStream(destino)) {
                                // Escribir el contenido editado en el archivo
                                fileOut.write(archivoContenido.getContenido());
                                respuesta = "Se ha realizado el cambio en el documento.";
                            } catch (IOException e) {
                                respuesta = "Error al escribir el archivo.";
                            }
                        }

                        case "COPIAR" -> {
                            String nombreArchivoCopia = (String) in.readObject();
                            File origen = new File(RUTA_SERVIDOR + nombreArchivoCopia);
                            if (origen.exists() && !origen.isDirectory()) {
                                try (InputStream fileIn = new FileInputStream(origen)) {
                                    byte[] contenidoArchivo = fileIn.readAllBytes();

                                    // Envía el contenido del archivo como objeto al cliente
                                    ArchivoContenido archivoContenido = new ArchivoContenido(contenidoArchivo);
                                    out.writeObject(archivoContenido);
                                    out.flush();

                                    respuesta = "Archivo copiado exitosamente.";
                                } catch (IOException e) {
                                    respuesta = "Error al leer el archivo fuente: " + e.getMessage();
                                }
                            } else {
                                respuesta = "Error: el archivo fuente no existe.";
                            }
                        }

                        case "ELIMINAR" -> {
                            String nombreArchivoEliminar = (String) in.readObject();
                            File archivoEliminar = new File(RUTA_SERVIDOR + nombreArchivoEliminar);
                            if (archivoEliminar.exists() && !archivoEliminar.isDirectory()) {
                                if (archivoEliminar.delete()) {
                                    respuesta = "Archivo eliminado exitosamente.";
                                    
                                } else {
                                    respuesta = "Error al eliminar el archivo.";
                                }
                            } else {
                                respuesta = "Error: el archivo no existe.";
                            }
                        }

                        case "SOLICITAR_NOMBRES_ARCHIVOS" -> {
                            // Enviar lista de nombres de archivos en la ruta del servidor
                            List<String> nombresArchivos = obtenerNombresArchivosEnRuta();
                            out.writeObject(nombresArchivos);
                        }

                        default ->
                            respuesta = "Operación no reconocida.";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
