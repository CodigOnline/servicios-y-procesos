package com.codigonline.telnet_protocol;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.*;
import java.util.Map;

public class MyTelnet {
    public static final String PARAM_TELNET_SERVER = "10.211.55.4";
    public static final String PARAM_TELNET_USERNAME = "parallels";
    public static final String PARAM_TELNET_PASSWORD = "ubuntu";

    private static final String prompt = "parallels@ubuntu-linux-20-04-desktop:";

    private TelnetClient telnet = null;
    private InputStream in = null;
    private PrintStream out = null;

    /**
     * Connects to the CAI system.
     */
    public void connect() throws IOException {

        telnet = new TelnetClient();

        // connect
        System.out.println("Connecting to server: " + PARAM_TELNET_SERVER);
        telnet.connect(PARAM_TELNET_SERVER);

        // Get input and output stream references
        in = telnet.getInputStream();
        out = new PrintStream(telnet.getOutputStream());

        // Login the user
        System.out.println("Iniciando sesión");
        lectura("login: ");
        write(PARAM_TELNET_USERNAME);
        lectura("Password: ");
        write(PARAM_TELNET_PASSWORD);

        lectura(prompt);
        System.out.println("Prompt found. Ready.");
    }

    public String enviar(String command) throws IOException {
        write(command);
        String result = lectura(prompt);
        System.out.println(result);
        if (result.length() == 0) {
            return "";
        } else {
            return result.substring(0, result.length() - 1);
        }
    }

    public void close() {

        try {
            telnet.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lectura(String pattern) throws IOException {
        char ultimoCaracterPattern = pattern.charAt(pattern.length() - 1);
        StringBuilder sb = new StringBuilder();
        int caracter;
        while ((caracter = in.read()) != -1) {
            sb.append((char) caracter);
            if ((char) caracter == ultimoCaracterPattern) {
                String str = sb.toString();
                if (str.endsWith(pattern)) {
                    return str.substring(0, str.length() - pattern.length());
                }
            }
        }
        return "";
    }
    private void write(String value) {
        out.println(value);
        out.flush();
    }


}