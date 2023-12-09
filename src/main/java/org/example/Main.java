package org.example;

import org.example.multijugador.ClienteM;
import org.example.multijugador.ServidorM;
import org.example.unjugador.ClienteU;
import org.example.unjugador.ServidorU;

import java.io.EOFException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Juego");
        System.out.println("1. Juego de un solo jugador");
        System.out.println("2. Juego multijugador");
        System.out.print("Elige una opción (1 o 2): ");

        int opcion = scanner.nextInt();

        if (opcion == 1) {
            // Juego de un solo jugador
            iniciarJuegoUnJugador();
        } else if (opcion == 2) {
            // Juego multijugador
            iniciarJuegoMultijugador();
        } else {
            System.out.println("Opción no válida. Por favor, elige 1 o 2.");
        }

        scanner.close();
    }

    private static void iniciarJuegoUnJugador() {
        System.out.println("Iniciando juego de un solo jugador...");

        // Inicia el servidor en un hilo separado
        new Thread(() -> ServidorU.main(null)).start();

        // Espera un momento para asegurar que el servidor esté funcionando antes de iniciar el cliente
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Inicia el cliente
        ClienteU.main(null);
    }

    private static void iniciarJuegoMultijugador() {
        // Código para iniciar el juego multijugador
        System.out.println("Iniciando juego multijugador...");
        // Puedes llamar a la clase Cliente correspondiente para el juego multijugador
        new Thread(() -> {
            try {
                ServidorM.main(null);
            } catch (EOFException e) {
                throw new RuntimeException(e);
            }
        }).start();
        // Espera un momento para asegurar que el servidor esté funcionando antes de iniciar el cliente
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Inicia el cliente
        ClienteM.main(null);
    }
    }
