package org.example.unjugador;

import org.example.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteU {
    public static void main(String[] args) {

        String descripcion="";
        String respuesta="";
        String resultado="";
        String sino="si";
        String pista="";
        int priimeraPista=0;
        int numAciertos=0;



        try (Socket s = new Socket("localhost", 55555);
             DataInputStream in = new DataInputStream(s.getInputStream());
             DataOutputStream out = new DataOutputStream(s.getOutputStream());) {

            System.out.println("Jugador conectado");
            System.out.println("VAMOS A COMENZAR EL JUEGO");


            System.out.println("Si quieres pasar introduce paso");
            System.out.println("Si quieres abandonar introduce exit");
            System.out.println("Si quieres conseguir una pista introduce pista");

            while(true) {


                priimeraPista=0;
                descripcion = in.readUTF();//recibe la definicion y la guarda en description

                System.out.println(descripcion); //Muestra por pantalla la definicion
                Scanner sc = new Scanner(System.in);

                respuesta = sc.nextLine();//Escribe la respuesta

                //No se puede pedir la primera palabra pista
                if(respuesta.equals("pista")&& priimeraPista==0){
                    System.out.println("No puedes pedir pista tan pronto, intentalo una vez");
                    respuesta=sc.nextLine();
                    priimeraPista=1;
                }

               if(respuesta.equals("exit")){
                   System.out.println("ouu te has rendido ");
                   System.out.println("Has conseguido "+numAciertos+" aciertos");
                   break;
               }


               out.writeUTF(respuesta);//Manda la respuesta al servidor
                resultado = in.readUTF();// Recibe falso en caso de que la palabra sea incorrecta y verdadero en el caso contrario



                while (resultado.equals("falso")) {



                    if(!respuesta.equals("pista")){
                        System.out.println("Has fallado introduce otra respuesta o pide pista(introcude pista)");

                    }else{

                        System.out.println("intentalo de nuevo");
                    }

                    respuesta = sc.nextLine();


                    if(!respuesta.equals("pista") && !respuesta.equals("exit")){


                        out.writeUTF(respuesta);
                        resultado=in.readUTF();



                    }if(respuesta.equals("pista")) {


                        out.writeUTF(respuesta);
                        pista=in.readUTF();
                        System.out.println(pista);

                    }
                    if(respuesta.equals("exit")) {

                        System.out.println("ouu te has rendido");
                        resultado="verdadero";
                        break;





                    }


                }


                if(!respuesta.equals("paso")){

                    if(!respuesta.equals("exit")){

                        System.out.println("RESPUESTA CORRECTA");
                        numAciertos++;



                    }else{
                        System.out.println("Has conseguido "+numAciertos+" aciertos");
                        break;
                    }


                }



            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }





}














