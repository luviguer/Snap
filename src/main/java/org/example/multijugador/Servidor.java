package org.example.multijugador;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Servidor {


    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(55555)) {

            System.out.println("Servidor iniciado");

            ExecutorService executorService = Executors.newFixedThreadPool(2);

            while (true) {
                Socket jugador1 = ss.accept();
                Socket jugador2 = ss.accept();

                System.out.println("Conexiones establecidas con clientes");

                JuegoHandler handler1 = new JuegoHandler(jugador1);
                JuegoHandler handler2 = new JuegoHandler(jugador2);

                // Ejecutar ambos handlers en hilos separados
                executorService.execute(handler1);
                executorService.execute(handler2);

                // Esperar a que ambos hilos terminen
                executorService.shutdown();
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

                //Calculamos el ganador
                if(handler1.getResultado()>handler2.getResultado()){
                    System.out.println("ganador 1");
                }
                if(handler1.getResultado()<handler2.getResultado()){
                    System.out.println("ganador 2");
                }
                if(handler1.getResultado()==handler2.getResultado()){
                    System.out.println("empate");
                }


            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




}
