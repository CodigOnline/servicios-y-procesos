package com.codigonline.tema3;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {

        Server server = null;
        try{
            server = new Server();
        }catch (IOException ex){
            System.err.println("No se ha podido inicializar el servidor");
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
        try {
            server.waitConnections();
        }catch (IOException ex){
            System.err.println("No se ha podido iniciar la comunicación con el cliente");
            System.err.println(ex.getMessage());
            System.exit(-2);
        }

    }
}
