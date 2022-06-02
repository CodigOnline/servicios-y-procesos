package com.codigonline.tema2;

import java.util.ArrayList;

public class Carrera {

    private final ArrayList<Coche> participantes;
    private final ArrayList<Coche> finalizacion;
    private Coche ganador;

    public Carrera() {
        this.participantes = new ArrayList<>();
        this.finalizacion = new ArrayList<>();
    }

    public void addParticpante(String nombre) {
        participantes.add(new Coche(nombre, this));
    }

    public void iniciarCarrera() {
        for (Coche coche : participantes) {
            coche.start();
        }
    }

    public synchronized void setGanador(Coche coche) {
        if (this.ganador == null) {
            this.ganador = coche;
        }
        finalizacion.add(coche);
    }

    public Coche getGanador() {
        for (Coche coche : participantes) {
            try {
                if (coche.isAlive())
                    coche.join();
            }catch (InterruptedException ex){
                System.err.println("Error al esperar la finalización del hilo");
            }
        }
        return ganador;
    }

    public ArrayList<Coche> getFinalizacion() {
        for (Coche coche : participantes) {
            try {
                if (coche.isAlive())
                    coche.join();
            }catch (InterruptedException ex){
                System.err.println("Error al esperar la finalización del hilo");
            }
        }
        return finalizacion;
    }
}
