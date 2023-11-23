package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        String descripcion="";
        String respuesta="";
        String resultado="";
        String sino="si";



        try (Socket s = new Socket("localhost", 55555);
             DataInputStream in = new DataInputStream(s.getInputStream());
             DataOutputStream out = new DataOutputStream(s.getOutputStream());) {

            System.out.println("Jugador conectado");

            //leemos la descripcion y la mostramos
            while(sino.equals("si")) {
                descripcion = in.readUTF();
                System.out.println(descripcion);
                Scanner sc = new Scanner(System.in);

                respuesta = sc.nextLine();
                out.writeUTF(respuesta);
                resultado = in.readUTF();
                while (resultado.equals("falso")) {
                    System.out.println("Has fallado introduce otra respuesta o pide pista(introcude pista)");

                    respuesta = sc.nextLine();

                    if(!respuesta.equals("pista")){
                        System.out.println(respuesta);
                        out.writeUTF(respuesta);
                        resultado=in.readUTF();

                    }else{
                        System.out.println(respuesta);
                        out.writeUTF(respuesta);
                        respuesta=in.readUTF();
                        System.out.println(respuesta);


                    }


                }

                System.out.println("Felicidades respuesta correcta");
                System.out.println("¿Quieres continuar con el juego?, si/no");
                Scanner scc = new Scanner(System.in);
                sino = scc.nextLine();
                out.writeUTF(sino);
            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}














