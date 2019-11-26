package labarberia;
import org.apache.commons.math3.distribution.NormalDistribution;

public class Cliente implements Runnable{
	
	//Atributos del cliente
	static Barberia barberia;
	static NormalDistribution distribucionNormal;
	private int nCliente;
	
	public Cliente(int nCliente) {
		this.nCliente = nCliente;
		System.out.println("El cliente "+this.nCliente+" se ha creado.");
	}
	
	public void interrupt() {
		System.out.println("El cliente "+this.nCliente+" ha sido destruido.");
	}
	
	public int getNCliente() {
		return this.nCliente;
	}

	@Override
	public void run() {
		try {
			Thread.sleep((long) distribucionNormal.sample());
		}catch(InterruptedException e) {};
		entrarALaBarberia();
	}
	
	private synchronized void entrarALaBarberia() {
		barberia.entrar(this);
	}
}
