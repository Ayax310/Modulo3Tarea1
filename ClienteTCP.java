package Modulo3Tarea1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCP {
	public static void main(String[] args) throws IOException {
		//socket para el cliente
		Socket socketcliente = null;
		//elementos necesarios para el flujo de entrada y salida
		BufferedReader entrada = null;
		PrintWriter salida = null;
		try {
			//instanciamos el socket con el respectivo inetaddress y el puerto.
			socketcliente = new Socket("localhost", 8888);
			//instanciamos los flujos de entrada y salida para el manejo de datos
			entrada = new BufferedReader(new InputStreamReader(socketcliente.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketcliente.getOutputStream())), true);
			
		}catch (Exception e) {

		}
		//instanciamos el buffer de entrada de datos
		BufferedReader lee = new BufferedReader(new InputStreamReader(System.in));
		String opcion;
		String respuesta;
		try {
			while(true) {
				System.out.println("MENU\n 1.Guardar \n 2.Verificar \n 3.Salir\n");
				System.out.print("Ingrese su opcion: ");
				opcion = lee.readLine();
				//enviamos la opcion elegida
				salida.println(opcion);
				//recibimos la respuesta
				respuesta = entrada.readLine();
				if(opcion.equals("3")) break;
				//imprimimos la respuesta del servidor
				System.out.print(respuesta+"\n");
			
			}
		}catch (Exception e) {
		}
		//liberamos recursos de los flujos de entrada y salida
		salida.close();
		entrada.close();
		lee.close();
		//liberamos al socket
		socketcliente.close();
	}
}
