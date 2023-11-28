package org.example.multijugador;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Exchanger;

public class JuegoHandler implements Runnable {

    private Socket c;

    private Exchanger<Integer> exchanger;

    Map<String, String> paises = new HashMap<>();

    String respuesta = "";

    int numAciertos=0;
    int contador=0;
    int numAciertosMandados=0;


    public JuegoHandler(Socket c,Exchanger<Integer> exchanger) {

        this.c=c;
        this.exchanger=exchanger;


    }


    @Override
    public void run() {

        paises = cargarDefinicionesDesdeArchivo("paises.txt");

        try (
                DataInputStream in = new DataInputStream(c.getInputStream());
                DataOutputStream out = new DataOutputStream(c.getOutputStream());) {

            System.out.println("VAMOS A COMENZAR EL JUEGO");

            // Obtener solo las definiciones y mezclarlas aleatoriamente
            List<String> definicionesAleatorias = obtenerDefinicionesAleatorias(paises,10);


            boolean salir=false;

            for (String definicion : definicionesAleatorias) {

                contador++;

                out.writeUTF(definicion); //Manda la definicion al cliente


                System.out.println("ha entrado por segunda vez");
                respuesta = in.readUTF(); //recibe la respuesta
                System.out.println(respuesta);

                if (respuesta.equals(obtenerPaisPorDefinicion(paises, definicion))) {
                    System.out.println("ha entrado en la opcion verdadera");
                    numAciertos++;



                }



                if(contador==definicionesAleatorias.size()) {


                    out.writeUTF("La lista de definicione se ha terminado");


                    salir=true;




                }else {
                    out.writeUTF("continuamos");
                }


                if(salir) {
                    break;

                }

            }


            System.out.println("llega aqui");


            numAciertosMandados=numAciertos;

            numAciertos=exchanger.exchange(numAciertos);

            if(numAciertos>numAciertosMandados) {

                out.writeUTF("ganador");
            }

            if(numAciertos<numAciertosMandados) {

                out.writeUTF("perdedor");
            }

            if(numAciertos==numAciertosMandados) {
                System.out.println("empate");
                out.writeUTF("empate");
            }



            c.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }











    public double getnumAciertos(){
        return numAciertos;
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







    private static String generaPista(String p,int numPistas) {

        String pista="";

        if (numPistas <= 3 && numPistas>0) {

            pista = formatearPalabra(p, numPistas);
        }
        else {
            pista="NO QUEDAN PISTAS";
        }
        return pista;

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

    private static List<String> obtenerDefinicionesAleatorias(Map<String, String> paises,int limite) {
        List<String> definiciones = new ArrayList<>(paises.values());
        Collections.shuffle(definiciones);


        if (definiciones.size() > limite) {
            definiciones = definiciones.subList(0, limite);
        }

        return definiciones;
    }

    private static String obtenerPaisPorDefinicion(Map<String, String> paises, String definicion) {
        for (Map.Entry<String, String> entry : paises.entrySet()) {
            if (entry.getValue().equals(definicion)) {
                return entry.getKey();
            }
        }
        return null;  // Manejar el caso en el que no se encuentra el país
    }

    public static String formatearPalabra(String palabra, int numero) {
        if (numero < 1 || numero > 3) {
            throw new IllegalArgumentException("El número debe ser 1, 2 o 3");
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palabra.length(); i++) {
            if ((numero == 1 && i == 0) ||
                    (numero == 2 && (i == 0 || i == 2)) ||
                    (numero == 3 && (i == 0 || i == 2 || i == 4))) {
                resultado.append(palabra.charAt(i));
            } else {
                resultado.append("-");
            }
        }

        return resultado.toString();
    }


















}