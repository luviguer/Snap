package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {


    public static void main(String[] args)  {


        Map<String, String> paises = new HashMap<>();
        boolean acertado=false;
        String respuesta;

        paises=cargarDefinicionesDesdeArchivo("paises.txt");

            try (ServerSocket serverSocket = new ServerSocket(55555)) {
                while(true) {

                    try(Socket sc = serverSocket.accept();
                        DataInputStream in = new DataInputStream(sc.getInputStream());
                        DataOutputStream out = new DataOutputStream(sc.getOutputStream())){

                            System.out.println("VAMOS A COMENZAR EL JUEGO");



                        for (Map.Entry<String, String> entry : paises.entrySet()) {
                            String pais = entry.getKey();
                            String definicion = entry.getValue();

                            out.writeUTF(definicion);
                            acertado=false;

                                while (acertado==false) {
                                    System.out.println("ha entrado por segunda vez");
                                    respuesta=in.readUTF();
                                    System.out.println(respuesta);
                                    if(respuesta.equals(pais)){

                                        acertado=true;
                                        out.writeUTF("verdadero");

                                        if(!in.readUTF().equals("si")){
                                            break;
                                        }



                                    }
                                    else{
                                        System.out.println("ha llegado al servidor");
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






    private static Map<String, String> cargarDefinicionesDesdeArchivo(String nombreArchivo) {
        Map<String, String> paises = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String definicion = partes[0].trim();
                    String nombrePais = partes[1].trim();
                    paises.put(nombrePais, definicion);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();



        }

        return paises;
    }


}
