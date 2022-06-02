package com.codigonline.ftp_protocol;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

class Client {

    private String server;
    private int port;
    private String user;
    private String password;
    private FTPClient ftp;

    public Client(String user, String password) {
        this.user = user;
        this.password = password;
        server = "10.211.55.4";
        port = 21;
    }

    // constructor

    boolean connect() throws IOException {
        ftp = new FTPClient();
        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("No se ha podido conectar al servidor");
        }
        System.out.println("Conectado correctamente");

        return ftp.login(user, password);
    }

    void close() throws IOException {
        ftp.disconnect();
    }

    public void getListFiles() throws IOException {
        FTPFile[] files = ftp.listDirectories();
        for (FTPFile ftpFile : files) {
            System.out.println(ftpFile.getName());
        }
        files = ftp.listFiles();
        for (FTPFile file : files) {
            System.out.println(file.getName());
        }
    }

    public void cambiarDirectorio(String directorio) throws IOException {
        ftp.changeWorkingDirectory(directorio);
        getListFiles();
    }

    public void obtenerArchivo(String fichero) throws IOException {
        File ficheroLocal = new File("files/" + fichero);
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(ficheroLocal));
        try {
            boolean downloaded = ftp.retrieveFile(fichero, outputStream);
            outputStream.flush();
            outputStream.close();
            if (downloaded) {
                System.out.println("Fichero descargado correctamnete");
            }
        } catch (IOException ex) {
            System.err.println("Error al descargar el fichero");
        }
    }
    public void subirArchivo(Path fichero) throws IOException{
        ftp.changeWorkingDirectory("~/Escritorio/m09");
        Files.readAllLines(fichero).forEach(System.out::println);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fichero.toFile()));
        boolean upload = ftp.storeFile("subir.txt", bufferedInputStream);
        if(upload)
            System.out.println("Fichero subido");
        else{
            System.out.println(ftp.getReplyString());
        }
        bufferedInputStream.close();
    }
}
