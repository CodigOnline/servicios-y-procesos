package com.codigonline.ftp_protocol;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Client client = new Client("parallels", "ubuntu");
        try {
            if (client.connect()) {
                System.out.println("Logging correcto");
            } else {
                System.out.println("Error de autenticación");
                client.close();
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el cliente FTP");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        try {
            client.getListFiles();
        } catch (IOException e) {
            System.err.println("Error al leer el directorio");
            System.err.println(e.getMessage());
            System.exit(-2);
        }
        int opcion;
        do {
            System.out.println("¿Que deseas hacer?");
            System.out.println("1.- Mostrar archivos");
            System.out.println("2.- Cambiar directorio");
            System.out.println("3.- Obtener archivo");
            System.out.println("4.- Subir archivo");
            System.out.println("0.- Salir");
            opcion = teclado.nextInt();
            switch (opcion) {
                case 0:
                    System.out.println("Gracias por utilizar la aplicación");
                    break;
                case 1:
                    try {
                        client.getListFiles();
                    } catch (IOException e) {
                        System.err.println("Error al leer el directorio");
                        System.err.println(e.getMessage());
                        System.exit(-2);
                    }
                    break;
                case 2:
                    String directorio = teclado.next();
                    try {
                        client.cambiarDirectorio(directorio);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        System.exit(-3);
                    }
                    break;
                case 3:
                    System.out.println("Nombre del archivo a recuperar");
                    String file = teclado.next();
                    try {
                        client.obtenerArchivo(file);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        System.exit(-4);
                    }
                    break;
                case 4:
                    System.out.println("Selecciona el fichero deseado:");
                    String nombre = teclado.next();
                    Path path = Path.of(nombre);
                    System.out.println(path.toAbsolutePath());
                    try {
                        client.subirArchivo(path);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        System.exit(-5);
                    }
                    break;

            }
        } while (opcion != 0);
    }
}
