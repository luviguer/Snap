package org.example.multijugador;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteM {


    public static void main(String[] args) {

        String descripcion="";
        String respuesta="";
        String resultado="continuamos";
        String sino="si";




        try (Socket s = new Socket("localhost", 5555);
             DataInputStream in = new DataInputStream(s.getInputStream());
             DataOutputStream out = new DataOutputStream(s.getOutputStream());) {

            System.out.println("Jugador conectado");

            //leemos la descripcion y la mostramos


            while(resultado.equals("continuamos")) {



                descripcion = in.readUTF();//recibe la definicion y la guarda en description

                System.out.println(descripcion); //Muestra por pantalla la definicion
                Scanner sc = new Scanner(System.in);

                respuesta = sc.nextLine();//Escribe la respuesta
                out.writeUTF(respuesta);//Manda la respuesta al servidor
                resultado = in.readUTF();// Recibe continuamos si va a continuar con las definiciones


            }


            System.out.println(resultado);

            resultado=in.readUTF();

            System.out.println(resultado);


            String ganador=in.readUTF();

            System.out.println(ganador);





        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}