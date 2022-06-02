package com.codigonline.tema3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketThread extends Thread{
    private final DataInputStream recibirDatos;
    private final DataOutputStream enviarDatos;

    public SocketThread(Socket socket) throws IOException {
        this.recibirDatos = new DataInputStream(socket.getInputStream());
        this.enviarDatos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        writeUTF("¡Bienvenido a tu calculadora favorita!");
        writeUTF("Porfavor, selecciona una opción:");
        int opcion = -1;
        while (opcion != 0) {
            writeUTF("\n\t1.-SUMAR\n\t2.-RESTAR\n\t3.-MULTIPLICAR\n\t4.-DIVIDIR\n\t5.-MOD\n\t0.-SALIR");
            opcion = readInt();
            switch (opcion) {
                case 0:
                    writeUTF("Muchas gracias por utilizar la aplicación");
                    System.out.println("Fin del cliente");
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    operacion(opcion);
                    break;
                default:
                    writeUTF("Opción incorrecta");
            }
        }
    }

    private void readUTF() {
        try {
            System.out.println(Colors.ANSI_RED + "CLIENT: " + recibirDatos.readUTF() + Colors.ANSI_RESET);
        } catch (IOException ex) {
            System.err.println("Error al leer datos del servidor");
        }
    }

    private void writeUTF(String text) {
        try {
            enviarDatos.writeUTF(text);
        } catch (IOException ex) {
            System.err.println("Error al enviar datos al servidor");
        }
    }

    private int readInt() {
        int num = 0;
        try {
            num = recibirDatos.readInt();
            System.out.println(Colors.ANSI_RED + "CLIENT: " + num + Colors.ANSI_RESET);
        } catch (IOException ex) {
            System.err.println("Error al leer datos del servidor");
        }
        return num;
    }


    private void operacion(int opcion) {
        writeUTF("Por favor introduce un numero:");
        int num1 = readInt();
        writeUTF("Por favor introduce un segundo numero:");
        int num2 = readInt();
        switch (opcion) {
            case 1:
                writeUTF("El resultado de la suma es: " + (num1 + num2));
                break;
            case 2:
                writeUTF("El resultado de la resta es: " + (num1 - num2));
                break;
            case 3:
                writeUTF("El resultado de la multiplicacion es: " + (num1 * num2));
                break;
            case 4:
                writeUTF("El resultado de la division es: " + (num1 / num2));
                break;
            case 5:
                writeUTF("El resultado del mod es: " + (num1 % num2));
                break;
        }

    }
}
