package com.codigonline.telnet_protocol;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static MyTelnet telnet;

    public static void main(String[] args) {
        System.out.println("Iniciando telnet");
        Scanner teclado = new Scanner(System.in);
        connect();
        int exit = 1;
        while (exit != 0) {
            try {
                String msg = teclado.nextLine();
                try {
                    int number = Integer.parseInt(msg);
                    exit = number;
                } catch (NumberFormatException ex) {
                    telnet.enviar(msg);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        telnet.close();


    }

    private static void connect() {
        telnet = new MyTelnet();
        try {
            telnet.connect();
        } catch (IOException ex) {
            System.err.println("Error al connectar mediante el protocolo TELNET");
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
    }


}
