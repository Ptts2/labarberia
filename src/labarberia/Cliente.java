package labarberia;
import org.apache.commons.math3.distribution.NormalDistribution;

public class Cliente extends Thread{
	
	//Atributos del cliente
	static Barberia barberia;
	static NormalDistribution distribucionNormal;
	private int nCliente;
	
	
	public Cliente(int nCliente) {
		this.nCliente = nCliente;
		System.out.println("El cliente "+this.nCliente+" se ha creado.");
	}
	
	@Override
	public final void interrupt() {
		System.out.println("El cliente "+this.nCliente+" ha sido destruido.");
		Thread.currentThread().interrupt();
	}
	
	public int getNCliente() {
		return this.nCliente;
	}
	
	public void sleep(double tiempo) {
		try {
		Thread.sleep((long) tiempo);
		}catch(InterruptedException e) {}
	}

	public void run() {
		
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep((long)Math.abs(distribucionNormal.sample()));
			}catch(InterruptedException e) {}; 
			entrarALaBarberia();
		}
		
	}
	
	private synchronized void entrarALaBarberia() {
		barberia.entrar(this);
	}
}
