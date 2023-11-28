package org.example.multijugador;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServidorM {


    public static void main(String[] args) throws EOFException {
        try (ServerSocket ss = new ServerSocket(5555)) {

            System.out.println("Servidor iniciado");

            ExecutorService executorService = Executors.newFixedThreadPool(2);

            while (true) {
                Socket jugador1 = ss.accept();
                Socket jugador2 = ss.accept();

                System.out.println("Conexiones establecidas con clientes");

                Exchanger<Integer> exchanger = new Exchanger<>();

                JuegoHandler handler1 = new JuegoHandler(jugador1,exchanger);
                JuegoHandler handler2 = new JuegoHandler(jugador2,exchanger);

                // Ejecutar ambos handlers en hilos separados
                executorService.execute(handler1);
                executorService.execute(handler2);

                // Esperar a que ambos hilos terminen
                executorService.shutdown();
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);






            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }





}