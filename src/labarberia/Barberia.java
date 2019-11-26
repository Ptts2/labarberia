package labarberia;

public class Barberia{
	
	//Atributos de la barberia
	
	private int sillas;
	private int sillasLibres;
	private static Barberia barberia; //La barberia ha de ser unica por el patron singleton
	private Barbero[] barberos;
	/**
	 * Constructor privado que no permite que se cree un constructor por defecto
	 */
	private Barberia() {
		this.sillas = 0;
		this.sillasLibres = 0;
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
	
	/**
	 * Metodo para establecer el numero de sillas de la barberia
	 * @param nSillas numero de sillas
	 */
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
