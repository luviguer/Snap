package org.example.multijugador;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServidorM {


    public static void main(String[] args) throws EOFException {
        try (ServerSocket ss = new ServerSocket(5555)) {

            System.out.println("Servidor iniciado");

            ExecutorService executorService = Executors.newFixedThreadPool(2);

            Map<String, String> paises = new HashMap<>();



            while (true) {
                Socket jugador1 = ss.accept();
                Socket jugador2 = ss.accept();

                System.out.println("Conexiones establecidas con clientes");

                Exchanger<Integer> exchanger = new Exchanger<>();

                paises = cargarDefinicionesDesdeArchivo("paises.txt");
                List<String> definicionesAleatorias = obtenerDefinicionesAleatorias(paises,10);


                JuegoHandler handler1 = new JuegoHandler(jugador1,exchanger,definicionesAleatorias);
                JuegoHandler handler2 = new JuegoHandler(jugador2,exchanger,definicionesAleatorias);

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



    private static List<String> obtenerDefinicionesAleatorias(Map<String, String> paises, int limite) {
        List<String> definiciones = new ArrayList<>(paises.values());
        Collections.shuffle(definiciones);


        if (definiciones.size() > limite) {
            definiciones = definiciones.subList(0, limite);
        }

        return definiciones;
    }

    private static Map<String, String> cargarDefinicionesDesdeArchivo(String nombreArchivo) {
        List<String> lineasMezcladas = obtenerLineasMezcladas(nombreArchivo);
        Map<String, String> paises = new HashMap<>();

        for (String linea : lineasMezcladas) {
            String[] partes = linea.split(",");
            if (partes.length == 2) {
                String definicion = partes[0].trim();
                String nombrePais = partes[1].trim();
                paises.put(nombrePais, definicion);
            }
        }

        return paises;
    }


    private static List<String> obtenerLineasMezcladas(String nombreArchivo) {
        List<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mezclar aleatoriamente las líneas solo si no están mezcladas ya
        if (!lineas.get(0).equals(lineas.get(1))) {
            Collections.shuffle(lineas);
        }

        return lineas;
    }



}