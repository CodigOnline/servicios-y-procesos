package com.codigonline.tema2;

public class Main {

    public static void main(String[] args) {

        Carrera carrera = new Carrera();
        for (int i = 0; i < 5; i++) {
            carrera.addParticpante(i + "");
        }

        carrera.iniciarCarrera();

        System.out.println(carrera.getFinalizacion());
        System.out.println(carrera.getGanador());
        System.out.println(carrera.getFinalizacion());
    }
}
