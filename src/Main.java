
import java.util.Scanner;
import java.util.logging.*;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;


/*
 Una barbería dispone de varios barberos y de varias sillas de espera para los clientes. Si no hay clientes los barberos se ponen a dormir. 
 Si llega un cliente y no hay nadie esperando despierta a un barbero y éste le corta el pelo. Si en cambio todos los barberos están ocupados el cliente 
 espera en una silla si hay alguna libre, sino se va. Los barberos a medida que terminan su trabajo sobre un cliente eligen al cliente que más tiempo 
 lleva esperando.

Se pide un programa en java que simule el funcionamiento de la barbería. Los barberos se identificarán por una única letra mayúscula, A, B, C, ... del 
alfabeto español siendo n el número de barberos. Los clientes se identificarán por números 1, 2, 3, ... siendo m el número de clientes. 
El programa se ejecutará durante t segundos cumpliendo que nunca un barbero puede estar atendiendo a más de un cliente y que la barbería tiene un número 
limitado de sillas de espera y cada una sólo puede usarse por un cliente. El alumno deberá implementar las clases Cliente, Barbero y Barberia, 
dejando la clase Main tal como está. Se podrán usar más clases si se considera necesario.

No se podrá usar el paquete java.util.concurrent ni ninguno de sus subpaquetes. Se anularán las prácticas que los usen.

El programa deberá lanzar los siguientes mensajes por la salida estándar, donde i representa el identificador de un barbero y j el identificador de un 
cliente:

El barbero i se ha creado.
El barbero i se pone a dormir.
El barbero i atiende al cliente j.
El barbero i ha cortado el pelo al cliente j.
El barbero i ha sido destruido.
El cliente j se ha creado.
El cliente j llega a la barbería.
El cliente j se marcha sin ser atendido.
El cliente j se sienta en una silla de espera.
El cliente j ha sido destruido.
Cada cliente va a la  barbería cada cierto tiempo y ese tiempo sigue una distribución normal de media μ y desviación típica σ. Cada barbero tarde en cortar el pelo a un cliente un tiempo que sigue una distribución exponencial de parámetro λ. Para calcular esos tiempos se generarán números que siguen esas distribuciones y para ello se usará el paquete org.apache.commons.math3.distribution incluido en la librería Apache Commons Math que puede descargarse de http://commons.apache.org/proper/commons-math/download_math.cgi.

Entrada

La entrada vendrá dada de la siguiente forma:

1ª línea: el número de barberos n y el número de clientes m.
2ª línea: el tiempo en segundos t de simulación.
3ª línea: los parámetros media μ y desviación típica σ  (en milisegundos) de la distribución normal que modeliza el tiempo de espera de los clientes para ir a la barbería.
4ª línea: el parámetro λ (en milisegundos) de la distribución exponencial que modeliza el tiempo que tarda un barbero en cortar el pelo.
5ª línea: el número de sillas disponibles en la barbería.
Salida

Los mensajes indicados más arriba, cada uno en una línea completa.

Ejemplo

Entrada

1 10

5

20 10

5

Salida


 */
public class Main {

	public static int tiempoSimulacion;
	
	public static Logger logger = Logger.getLogger("ccia.labarberia");
	static {
		logger.setLevel(Level.OFF)
		//logger.setLevel(Level.WARNING)
		;}
	
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		
		int nBarberos = sc.nextInt();
		int nClientes = sc.nextInt();
		tiempoSimulacion = sc.nextInt();
		
		Cliente.distribucionNormal = new NormalDistribution(sc.nextInt(),sc.nextInt());

		Barbero.distribucionExponencial = new ExponentialDistribution(sc.nextInt());
		
		Barberia b = Barberia.getBarberia(); // La Barberia sigue el patron Singleton
		
		Cliente.barberia = b;
		Barbero.barberia = b;
		b.setNumeroSillas(sc.nextInt());
		sc.close();
		
		Barbero[] barberos = new Barbero[nBarberos];
		for (int i=1; i<=nBarberos; i++){
			barberos[i-1] = new Barbero(i);
			barberos[i-1].start();
		}
		b.setBarberos(barberos);
		
		Thread[] clientes = new Thread[nClientes];
		for (int j=1; j<=nClientes; j++){
			clientes[j-1] = new Thread(new Cliente(j));
			clientes[j-1].start();
		}
		
		Thread.sleep(tiempoSimulacion*1000);
		
		for (int j=0; j<nClientes; j++){
			clientes[j].interrupt();
		}
	
		for (int i=0; i<nBarberos; i++){
			barberos[i].interrupt();
		}
		
		for (int j=0; j<nClientes; j++){
			clientes[j].join();
		}
	
		for (int i=0; i<nBarberos; i++){
			barberos[i].join();
		}
		
		
	}

}

class Barbero{
	
	
}

class Cliente{
	
}
