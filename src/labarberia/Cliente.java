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

	@Override
	public void run() {
		System.out.println("El cliente "+this.nCliente+" llega a la barbería.");
	}
}
