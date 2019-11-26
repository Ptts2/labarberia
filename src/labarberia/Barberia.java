package labarberia;

import java.util.ArrayList;

public class Barberia{
	
	//Atributos de la barberia
	
	private int sillas;
	private int sillasLibres;
	private static Barberia barberia; //La barberia ha de ser unica por el patron singleton
	private Barbero[] barberos;
	private ArrayList<Cliente> clientes;
	/**
	 * Constructor privado que no permite que se cree un constructor por defecto
	 */
	private Barberia() {
		this.sillas = 0;
		this.sillasLibres = 0;
		this.clientes = new ArrayList<Cliente>();
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
	

	public int getSillasLibres() {
		return this.sillasLibres;
	}
	public void setNumeroSillas(int nSillas) {
		this.sillas = nSillas;
		this.sillasLibres = nSillas; 
	}
	
	/*
	 * Simula que un cliente se sienta
	 */
	public synchronized int clienteSeSienta(){
		
		if(sillasLibres > 0) {
			this.sillasLibres--;
			return 0;
		}else {
			return -1; //No hay sillas libres
		}
	}
	
	public void cortarPelo(char nBarbero, double tiempo) {
		Cliente cliente;
		
		synchronized(clientes) {
			
			while(clientes.size()==0) {
				System.out.println("El barbero "+nBarbero+" se pone a dormir.");
				
				try {
					clientes.wait();
				}catch(InterruptedException e) {};
			}
		}
		
		cliente =  clientes.get(0);
		clientes.remove(0);
		System.out.println("El barbero "+nBarbero+" atiende al cliente "+cliente.getNCliente()+".");
		try {
			Thread.sleep((long) tiempo);
		}catch(InterruptedException e) {};
		System.out.println("El barbero "+nBarbero+" ha cortado el pelo al cliente "+cliente.getNCliente()+".");
	}
	
	public void entrar(Cliente cliente) {
		System.out.println("El cliente "+cliente.getNCliente()+" llega a la barbería.");
		
		synchronized(clientes) {
			
			if(clientes.size() == this.sillas) {
				System.out.println("El cliente "+cliente.getNCliente()+" se marcha sin ser atendido.");
				return;
			}
			
			clientes.add(cliente);
			System.out.println("El cliente "+cliente.getNCliente()+" se sienta en una silla de espera.");
			if(clientes.size()==1) {
				clientes.notify();
			}
		}
	}
	/*
	 * Simula que un cliente se levanta
	 */
	public synchronized void clienteSeLevanta() {
		this.sillasLibres++;
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
