package org.example.unjugador;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServidorU {

    public static void main(String[] args) {
        Map<String, String> paises = new HashMap<>();
        boolean acertado = false;
        String respuesta = "";
        int numPistas = 1;


        paises = cargarDefinicionesDesdeArchivo("paises.txt");

        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            while (true) {

                try (Socket sc = serverSocket.accept();
                     DataInputStream in = new DataInputStream(sc.getInputStream());
                     DataOutputStream out = new DataOutputStream(sc.getOutputStream())) {


                    // Obtener solo las definiciones y mezclarlas aleatoriamente
                    List<String> definicionesAleatorias = obtenerDefinicionesAleatorias(paises);

                    for (String definicion : definicionesAleatorias) {
                        acertado = false;
                        numPistas = 1;

                        out.writeUTF(definicion); //Manda la definicion al cliente

                        while (!acertado) {

                            respuesta = in.readUTF(); //recibe la respuesta
                            System.out.println(respuesta);

                            if(respuesta.equals("paso")){
                                out.writeUTF("verdadero");
                                break;
                            }




                            if (respuesta.toLowerCase().equals(obtenerPaisPorDefinicion(paises, definicion))) {
                                acertado = true;
                                out.writeUTF("verdadero");
                                out.flush();




                            }


                            if (respuesta.equals("pista")) {

                                String pais = obtenerPaisPorDefinicion(paises, definicion);

                                String pista=generaPista(pais,numPistas);
                                if(numPistas==1 | numPistas==2){
                                    numPistas++;
                                }else{
                                    if(numPistas==3){
                                        numPistas=0;


                                    }
                                }

                                out.writeUTF(pista);



                            }


                            if (!respuesta.equals("si") && !respuesta.equals("no") && !respuesta.equals("pista") && !respuesta.toLowerCase().equals(obtenerPaisPorDefinicion(paises, definicion)) && !respuesta.equals("exit") ) {
                                out.writeUTF("falso");
                            }
                        }

                    }
                }
            }










        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//FUNCIONES NECESARIAS

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

    private static List<String> obtenerDefinicionesAleatorias(Map<String, String> paises) {
        List<String> definiciones = new ArrayList<>(paises.values());
        Collections.shuffle(definiciones);
        return definiciones;
    }

    private static String obtenerPaisPorDefinicion(Map<String, String> paises, String definicion) {
        for (Map.Entry<String, String> entry : paises.entrySet()) {
            if (entry.getValue().equals(definicion)) {
                return entry.getKey();
            }
        }
        return null;
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

