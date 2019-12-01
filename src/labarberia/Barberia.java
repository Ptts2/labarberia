package labarberia;

import java.util.LinkedList;

public class Barberia{
	
	//Atributos de la barberia
	
	private int sillas;
	private static Barberia barberia; //La barberia ha de ser unica por el patron singleton
	private Barbero[] barberos;
	private volatile int barberosOcupados;
	private LinkedList<Cliente> clientes;
	private volatile boolean cerrado;
	/**
	 * Constructor privado que no permite que se cree un constructor por defecto
	 */
	private Barberia() {
		this.cerrado = false;
		this.sillas = 0;
		this.clientes = new LinkedList<Cliente>();
		this.barberosOcupados = 0;
	}

	/**
	 * Metodo para crear y devolver una única barbería
	 * @return la barbería
	 */
	public static Barberia getBarberia() {
		
		if(barberia == null) {
			barberia = new Barberia();
		}else {
			System.out.println("Ya hay una barbería creada");
		}
		return barberia;
	}
	
	/**
	 * Metodo que inicializa los barberos
	 * @param barberos los barberos de la barberia
	 */
	public void setBarberos(Barbero[] barberos) {
		this.barberos = barberos;
	}
	
	public void setNumeroSillas(int nSillas) {
		this.sillas = nSillas;
	}
	
	public void cortarPelo(char nBarbero, double tiempo) {
		
		if(this.cerrado == true) {
			return;
		}else {
		
		Cliente cliente;
		
		synchronized(barberos) {
			
			while(clientes.size()==0) {
				System.out.println("El barbero "+nBarbero+" se pone a dormir.");
				try {
					barberos.wait();
				}catch(InterruptedException e) {};
			}
		}
		
		cliente = clientes.poll();
		
		System.out.println("El barbero "+nBarbero+" atiende al cliente "+cliente.getNCliente()+".");
		synchronized(barberos) {
			this.barberosOcupados++;
		}
		
		try {
			cliente.sleep(tiempo);
			Thread.sleep((long) tiempo);
		}catch(InterruptedException e) {};
		System.out.println("El barbero "+nBarbero+" ha cortado el pelo al cliente "+cliente.getNCliente()+".");
		
		synchronized(barberos) {
			this.barberosOcupados--;
		}
		synchronized(clientes){
			clientes.notify();
		}
		}
	}
	
	public void entrar(Cliente cliente) {
		
		if(this.cerrado == true) {
			System.out.println("El cliente "+cliente.getNCliente()+" ha sido destruido.");
			Thread.currentThread().interrupt();
		}else {
		
		
			System.out.println("El cliente "+cliente.getNCliente()+" llega a la barbería.");
		
			if(clientes.size() == this.sillas) {
				System.out.println("El cliente "+cliente.getNCliente()+" se marcha sin ser atendido.");
				return;
			}
			clientes.offer(cliente);
		
			synchronized(clientes) {
				while(barberosOcupados==this.barberos.length) {
					System.out.println("El cliente "+cliente.getNCliente()+" se sienta en una silla de espera.");
					try{
						clientes.wait();
					}catch(InterruptedException e) {}
				}		
			}
			synchronized(barberos){
				barberos.notify();
			}
		}
	}
	
	public void cerrar() {
		this.cerrado = true;
		synchronized(clientes) {
			clientes.notifyAll();
		}
		synchronized(barberos) {
			barberos.notifyAll();
		}
	}

	/**
	 * Para no permitir la clonación
	 */
	@Override
	public Barberia clone() {
		System.out.println("No se puede clonar una Barberia");
		return null;
	}
	
}
