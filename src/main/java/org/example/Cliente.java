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
        String pista="";



        try (Socket s = new Socket("localhost", 55555);
             DataInputStream in = new DataInputStream(s.getInputStream());
             DataOutputStream out = new DataOutputStream(s.getOutputStream());) {

            System.out.println("Jugador conectado");

            //leemos la descripcion y la mostramos
            while(sino.equals("si")) {
                descripcion = in.readUTF();//recibe la definicion y la guarda en description

                System.out.println(descripcion); //Muestra por pantalla la definicion
                Scanner sc = new Scanner(System.in);

                respuesta = sc.nextLine();//Escribe la respuesta
                out.writeUTF(respuesta);//Manda la respuesta al servidor
                resultado = in.readUTF();// Recibe falso en caso de que la palabra sea incorrecta y verdadero en el caso contrario
                while (resultado.equals("falso")) {

                    System.out.println("Has fallado introduce otra respuesta o pide pista(introcude pista)");

                    respuesta = sc.nextLine();

                    if(!respuesta.equals("pista")){

                        System.out.println(respuesta);
                        out.writeUTF(respuesta);
                        resultado=in.readUTF();//AQUI NO LLEGA EL VERDADERO
                        System.out.println("ha llegado el verdadero");
                        System.out.println(resultado);


                    }else{

                        System.out.println(respuesta);
                        out.writeUTF(respuesta);
                        pista=in.readUTF();
                        System.out.println(pista);

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














