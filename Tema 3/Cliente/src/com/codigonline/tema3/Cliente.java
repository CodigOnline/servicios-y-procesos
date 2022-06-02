package com.codigonline.tema3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

    private final String HOST;
    private final int PORT;
    private final Socket socket;
    private DataInputStream recibirDatos = null;
    private DataOutputStream enviarDatos = null;

    public Cliente(String HOST, int PORT) throws IOException {
        this.HOST = HOST;
        this.PORT = PORT;
        System.out.println("Iniciando Socket contra el HOST:" + HOST + " y PORT:" + PORT);
        socket = new Socket(HOST, PORT);

    }

    public void connect() throws IOException {
        recibirDatos = new DataInputStream(socket.getInputStream());
        enviarDatos = new DataOutputStream(socket.getOutputStream());
        readUTF();
        readUTF();
        int opcion = -1;
        while (opcion != 0) {
            readUTF(); //<-- MENU
            opcion = writeInt(); //<--OPCION
            if (opcion != 0)
                if (opcion <= 5 && opcion >= 1) {
                    readUTF();//<--PRIMER NUM
                    writeInt();//ENVIO NUM
                    readUTF();//<--SEGUNDO NUMERO
                    writeInt();//ENVIO NU;
                    readUTF();//RESULTADO
                } else {
                    readUTF();
                }

        }
        System.out.println("Adios");
    }


    private void readUTF() {
        try {
            System.out.println(Colors.ANSI_GREEN + "SERVER: " + recibirDatos.readUTF() + Colors.ANSI_RESET);
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

    private int writeInt() {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        try {
            enviarDatos.writeInt(num);
        } catch (IOException ex) {
            System.err.println("Error al enviar datos al servidor");
        }
        return num;
    }
}
