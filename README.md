# Socket-Cliente-Servidor
Proyecto de un socket cliente/servidor para la manipulación de archivos de texto con interfaz gráfica del lado del usuario

## Requisitos

* Java 17
* Abrir o habilitar el puerto 8000

## Instalación

1. Descargar el proyecto de GitHub.
2. Importar el proyecto en tu IDE favorito.
3. Compilar los programas cliente y servidor.

## Ejecución

1. Ejecutar el programa servidor (Se puede editar el puerto y la ruta de la carpeta para los archivos de acuerdo a las necesidades en la clase "Servidor.java").
2. Ejecutar el programa cliente (Se puede editar la dirección IP del servidor de acuerdo a las necesidades en la clase "ClienteGUI.java).
3. Seleccionar en el menú del cliente alguna de las operaciónes que se quieren realizar.

## Operaciones

El programa cliente permite realizar las siguientes operaciones:

* **Leer archivo:** Permite leer un archivo de texto listado dentro de la carpeta del servidor.
* **Editar archivo:** Permite editar una archivo de texto listando dentro de la carpeta del servidor (se debe salir al menu y elegir una opción para ver los cambios).
* **Copiar archivo:** Permite copiar un archivo de texto listado dentro de la carpeta del servidor a cualquier ruta que se quiera del sistema operativo.
* **Eliminar archivo:** Permite eliminar un archivo de texto listado dentro de la carpeta del servidor.

## Notas
1. Se tiene validaciónes en las operaciónes, en caso de no tener levantado el servidor antes de querer realizar una operación, el programa no dejará realizar ninguna de ellas.
2. Debe haber al menos 1 archivo en la carpeta del servidor para poder realizar algúna operación.
3. El programa del servidor no cuenta con interfáz grafica, todos los cambios son visto en la línea de comando
