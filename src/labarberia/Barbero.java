package labarberia;
import org.apache.commons.math3.distribution.ExponentialDistribution;

class Barbero extends Thread{
	
	private final String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//Atributos del Barbero
	static Barberia barberia;
	static ExponentialDistribution distribucionExponencial;
	private char nBarbero;
	
	public Barbero(int nBarbero) {
		this.nBarbero = abecedario.charAt(nBarbero-1);
		System.out.println("El barbero "+this.nBarbero+" se ha creado.");
	}
	
	@Override
	public final void interrupt() {
		System.out.println("El barbero "+this.nBarbero+" ha sido destruido.");
	}
	
	@Override
	public void run() {
		
	}
}
