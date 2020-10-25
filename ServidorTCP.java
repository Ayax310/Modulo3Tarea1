package Modulo3Tarea1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ServidorTCP {
	public static void main(String[] args) throws IOException {
		Scanner lee = new Scanner(System.in);
		String n = "";
		int c = 0;
		String f = "";
		Persona x = new Persona(c, n, f);
		//instanciamos el serversocket para la conexion
		ServerSocket socketservidor = null;
		//un socket cliente para aceptar la conexion posteriormente
		Socket socketcliente = null;
		//instanciamos el buffer para la entrada de datos
		BufferedReader entrada = null;
		//instanciamos un printwriter para el flujo de salida de datos
		PrintWriter salida = null;

		System.out.println("SERVIDOR INICIADO");
		try {
			//instanciamos el socket del servidor en el puerto 8888
			socketservidor = new ServerSocket(8888);
		}catch (Exception e) {
		}

		try {

			while(true) {
				//instanciamos la conexion entre el socket del cliente y del servidor
				socketcliente = socketservidor.accept();
				//instanciamos los flujos de entrada de datos
				entrada = new BufferedReader(new InputStreamReader(socketcliente.getInputStream()));
				//instanciamos los flujos de salida de datos
				salida = new PrintWriter( new BufferedWriter(new OutputStreamWriter(socketcliente.getOutputStream())), true);
				String respuesta;
				//creamos un while para que se acepten varias peticiones.
				while(true)
				{
					//recibimos la peticion del cliente
					String opcion = entrada.readLine();
					switch(opcion) {
					case "1":
						try{
							System.out.println("GUARDAR");
							System.out.println("Ingrese su nombre y su C.I.:");
							n = lee.nextLine();
							c = lee.nextInt();
							f = obtenerFechaYHoraActual();
							x = new Persona(c, n, f);
							ObjectOutputStream creandoper = new ObjectOutputStream(new FileOutputStream("D:/Ayax/Documentos/Informática/LAB-273 - Laboratorio de INF-273/Auxiliatura/persona.dat"));
							creandoper.writeObject(x);
							creandoper.close();
							respuesta = "Se guardo con éxito :)";
							salida.println(respuesta);
						} catch (Exception e){
						}
						break;
					case "2":
						try{
							System.out.println("VERIFICAR");
							System.out.println("Ingrese su C.I.:");
							c = lee.nextInt();
							ObjectInputStream recu = new ObjectInputStream(new FileInputStream("D:/Ayax/Documentos/Informática/LAB-273 - Laboratorio de INF-273/Auxiliatura/persona.dat"));
							Persona y = (Persona) recu.readObject();
							while (y!=null){
								if (y instanceof Persona){
									if(c == y.getCi()){
										String res = ("Nombre: "+y.getNombre()+" C.I.: "+y.getCi()+" Fecha y hora Registro: "+y.getFechaReg()+" Puerto: "+socketservidor.getLocalPort());
										respuesta = res;
										salida.println(respuesta);
									} else{
										respuesta = "Usted no se encuentra en la BBDD.";
										salida.println(respuesta);
									}
								}
								y = (Persona) recu.readObject();
							}
							recu.close();
						} catch (Exception e){
						}
						break;
					case "3":
						break;
					}
				}		
			}
		}catch (Exception e) {

		}
		//liberamos los recursos de cada flujo
		salida.close();
		entrada.close();
		//liberamos los recursos de cada socket
		socketservidor.close();
		socketcliente.close();
	}
	
	public static String obtenerFechaYHoraActual() {
		String formato = "yyyy-MM-dd HH:mm:ss";
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
		LocalDateTime ahora = LocalDateTime.now();
		return formateador.format(ahora);
	}
}
class Persona implements Serializable{
	public int ci;
	public String nombre;
	public String fechareg;
	public Persona(int c, String n, String f){
		ci = c;
		nombre = n;
		fechareg = f;
	}
	public int getCi(){
		return ci;
	}
	public String getNombre(){
		return nombre;
	}
	public String getFechaReg(){
		return fechareg;
	}
	public void setCi(int c){
		ci = c;
	}
	public void setNombre(String n){
		nombre = n;
	}
	public void setFechaReg(String f){
		fechareg = f;
	}
}
