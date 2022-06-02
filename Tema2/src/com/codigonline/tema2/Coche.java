package com.codigonline.tema2;

import java.util.Random;

public class Coche extends Thread {

    private Random random = new Random();
    private final Carrera carrera;
    private String name;

    public Coche(String name, Carrera carrera) {
        this.name = name;
        this.carrera = carrera;
    }

    @Override
    public void run() {
        for (int i = 0; i < 9; i++) {
            try {
                sleep(random.nextInt(1) * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        carrera.setGanador(this);
    }

    public String getCoche() {
        return name;
    }

    @Override
    public String toString() {
        return "Coche name = " + name;
    }
}
