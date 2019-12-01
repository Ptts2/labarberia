package labarberia;
import org.apache.commons.math3.distribution.ExponentialDistribution;

class Barbero extends Thread{
	
	private final String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//Atributos del Barbero
	static Barberia barberia;
	static ExponentialDistribution distribucionExponencial;
	private char nBarbero;
	private volatile boolean running;
	
	public Barbero(int nBarbero) {
		
		running = true;
		this.nBarbero = abecedario.charAt(nBarbero-1);
		System.out.println("El barbero "+this.nBarbero+" se ha creado.");
	}
	
	public final void interrupt() {
		barberia.cerrar();
		this.running=!this.running;
		System.out.println("El barbero "+this.nBarbero+" ha sido destruido.");
	}
	
	public void run() {
		while(running) {
			barberia.cortarPelo(this.nBarbero, distribucionExponencial.sample());
		}
	}
}
