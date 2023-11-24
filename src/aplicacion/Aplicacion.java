package aplicacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoAjustado;
import modelo.ProductoMenu;
import modelo.Restaurante;

public class Aplicacion {
	
	private Restaurante restaurante;
	private File archivoIngredientes;
	private File archivoMenu;
	private File archivoCombos;
	
	public Aplicacion(String rutaIngredientes, String rutaMenu, String rutaCombos) {
		this.restaurante = new Restaurante();
		this.archivoIngredientes = new File(rutaIngredientes);
		this.archivoMenu = new File(rutaMenu);
		this.archivoCombos = new File(rutaCombos);
	}
	
	public static void main(String[] args) throws IOException {
		Aplicacion aplicacion = new Aplicacion("src/data/ingredientes.txt","src/data/menu.txt" ,"src/data/combos.txt");		
		aplicacion.getRestaurante().cargarInformacionRestaurante(aplicacion.getArchivoIngredientes(), aplicacion.getArchivoMenu(), aplicacion.getArchivoCombos());
		aplicacion.mostrarMenu();	
		aplicacion.ejecutarOpcionMenu();
	}
	
	public void mostrarMenu() {
		System.out.println("HAMBURGUESAS MENU: \n");
		System.out.println("---------------------------------------------------------------");
		System.out.println("PRODUCTOS: ");
	    System.out.println("0- corral;14000\r\n"
	    		+ "1- corral queso, precio: 16000\r\n"
	    		+ "2- corral pollo, precio: 15000\r\n"
	    		+ "3- corralita, precio: 13000\r\n"
	    		+ "4- todoterreno, precio: 25000\r\n"
	    		+ "5- 1/2 libra, precio: 25000\r\n"
	    		+ "6- especial, precio: 24000\r\n"
	    		+ "7- casera, precio: 23000\r\n"
	    		+ "8- mexicana, precio: 22000\r\n"
	    		+ "9- criolla, precio: 22000\r\n"
	    		+ "10- costenia, precio: 20000\r\n"
	    		+ "11- hawaiana, precio: 20000\r\n"
	    		+ "12- wrap de pollo, precio: 15000\r\n"
	    		+ "13- wrap de lomo, precio: 22000\r\n"
	    		+ "14- ensalada mexicana, precio: 20900\r\n"
	    		+ "15- papas medianas, precio: 5500\r\n"
	    		+ "16- papas grandes, precio: 6900\r\n"
	    		+ "17- papas en casco medianas, precio: 5500\r\n"
	    		+ "18- papas en casco grandes, precio: 6900\r\n"
	    		+ "19- agua cristal sin gas, precio: 5000\r\n"
	    		+ "20- agua cristal con gas, precio: 5000\r\n"
	    		+ "21- gaseosa, precio: 5000 \r\n");
		
	    System.out.println("COMBOS:");
	    System.out.println("22- combo corral, descuento: 10%, productos: corral,papas medianas,gaseosa\r\n"
	    		+ "23- combo corral queso, descuento: 10%, productos: corral queso, papas medianas,gaseosa\r\n"
	    		+ "24- combo todoterreno, descuento: 7%, productos: todoterreno, papas grandes, gaseosa\r\n"
	    		+ "25- combo especial, descuento: 7%, productos: especial, papas medianas, gaseosa \n");
	    	    
	    System.out.println("INGREDIENTES ADICIONALES: ");
	    System.out.println("lechuga;1000\r\n"
	    		+ "tomate, precio: 1000\r\n"
	    		+ "cebolla, precio: 1000\r\n"
	    		+ "queso mozzarella, precio: 2500\r\n"
	    		+ "huevo, precio: 2500\r\n"
	    		+ "queso americano, precio: 2500\r\n"
	    		+ "tocineta express, precio: 2500\r\n"
	    		+ "papa callejera, precio: 2000\r\n"
	    		+ "pepinillos, precio: 2500\r\n"
	    		+ "cebolla grille, precio: 2500\r\n"
	    		+ "suero costenio, precio: 3000\r\n"
	    		+ "frijol refrito, precio: 4500\r\n"
	    		+ "queso fundido, precio: 4500\r\n"
	    		+ "tocineta picada, precio: 6000\r\n"
	    		+ "pinia;, precio: 2500 \n");
	    System.out.println("----------------------------------------------------");
	}
	   	
	public void ejecutarOpcionMenu() throws IOException {	    
		boolean continuar = true;	
		while(continuar) {
			System.out.println("OPCIONES: \n");
		    System.out.println("1- Iniciar pedido");
		    System.out.println("2- Consultar pedido");
		    System.out.println("3- Salir");    
		    int opcionSeleccionada = Integer.parseInt(input("Seleccione una opcion "));	
			if(opcionSeleccionada==1) {
				iniciarPedido();
				continuar = false;
			}
			else if(opcionSeleccionada==2) {
				consultarPedido();
				continuar = false;
			}
			else if(opcionSeleccionada==3) {
				continuar = false;
			}			
		}			
	}	
	
	private void iniciarPedido() throws IOException {
		String nombreCliente = input("Ingrese su nombre ");
		String direccionCliente = input("Ingrese su direccion");
		this.restaurante.iniciarPedido(nombreCliente, direccionCliente);
		System.out.println("PEDIDO INICIADO!!");
	    boolean continuar = true;	
		while(continuar) {
			mostrarMenu();
			System.out.println("OPCIONES: \n");
		    System.out.println("0-24 Seleccionar producto");
		    System.out.println("30- Cerrar pedido");    
		    int opcionSeleccionada = Integer.parseInt(input("Seleccione una opcion "));
			if(opcionSeleccionada >= 0 && opcionSeleccionada < 25) {
				if(opcionSeleccionada < 22) {
					Producto productoFinal = agregarEliminarIngredientes(this.restaurante.getMenuBase().get(opcionSeleccionada));
					this.restaurante.getPedidoEnCurso().agregarProducto(productoFinal);
					System.out.println("Producto agregado");
				}		
						
				else if(opcionSeleccionada > 21 && opcionSeleccionada < 25){
					if(opcionSeleccionada == 22) {
						this.restaurante.getPedidoEnCurso().agregarProducto(this.restaurante.getCombos().get(0));
						System.out.println("Producto agregado");
					}
					else if(opcionSeleccionada == 23) {
						this.restaurante.getPedidoEnCurso().agregarProducto(this.restaurante.getCombos().get(1));
						System.out.println("Producto agregado");
					}
					else if(opcionSeleccionada == 24) {
						this.restaurante.getPedidoEnCurso().agregarProducto(this.restaurante.getCombos().get(2));
						System.out.println("Producto agregado");
					}
					else if(opcionSeleccionada == 25) {
						this.restaurante.getPedidoEnCurso().agregarProducto(this.restaurante.getCombos().get(3));
						System.out.println("Producto agregado");
					}
				}	
			}
			else if(opcionSeleccionada==30) {
				this.restaurante.cerrarYGuardarPedido();
				System.out.println("PEDIDO CERRADO!!");
				continuar = false;
			}		
		}
	}
	
	private Producto agregarEliminarIngredientes(ProductoMenu productoBase) throws IOException {
		System.out.println("INGREDIENTES PARA ANIADIR: ");
	    System.out.println("0- lechuga;1000\r\n"
	    		+ "1- tomate, precio: 1000\r\n"
	    		+ "2- cebolla, precio: 1000\r\n"
	    		+ "3- queso mozzarella, precio: 2500\r\n"
	    		+ "4- huevo, precio: 2500\r\n"
	    		+ "5- queso americano, precio: 2500\r\n"
	    		+ "6- tocineta express, precio: 2500\r\n"
	    		+ "7- papa callejera, precio: 2000\r\n"
	    		+ "8- pepinillos, precio: 2500\r\n"
	    		+ "9- cebolla grille, precio: 2500\r\n"
	    		+ "10- suero costenio, precio: 3000\r\n"
	    		+ "11- frijol refrito, precio: 4500\r\n"
	    		+ "12- queso fundido, precio: 4500\r\n"
	    		+ "13- tocineta picada, precio: 6000\r\n"
	    		+ "14- pinia;, precio: 2500 \n");
	    	   
	    ProductoAjustado productoFinal = null;	    
	    boolean continuar = true;	
		while(continuar) {
			System.out.println("OPCIONES: \n");
			System.out.println("0-14- Agregar ingrediente");
			System.out.println("20- No agregar ingrediente");
			int opcionSeleccionada = Integer.parseInt(input("Seleccione una opcion "));
			if(opcionSeleccionada >= 0 && opcionSeleccionada < 15) {
				if(productoFinal != null) {
					productoFinal.agregarIngrediente(this.restaurante.getIngredientes().get(opcionSeleccionada));
					System.out.println("Ingrediente agregado");
				}
				else {
					productoFinal = new ProductoAjustado(productoBase);
					productoFinal.agregarIngrediente(this.restaurante.getIngredientes().get(opcionSeleccionada));
					System.out.println("Ingrediente agregado");
				}				
			}
			else if(opcionSeleccionada==20) {
				continuar = false;
			}		
		}
		
		System.out.println("INGREDIENTES PARA ELIMINAR: ");
	    System.out.println("0- lechuga;1000\r\n"
	    		+ "1- tomate, precio: 1000\r\n"
	    		+ "2- cebolla, precio: 1000\r\n"
	    		+ "3- queso mozzarella, precio: 2500\r\n"
	    		+ "4- huevo, precio: 2500\r\n"
	    		+ "5- queso americano, precio: 2500\r\n"
	    		+ "6- tocineta express, precio: 2500\r\n"
	    		+ "7- papa callejera, precio: 2000\r\n"
	    		+ "8- pepinillos, precio: 2500\r\n"
	    		+ "9- cebolla grille, precio: 2500\r\n"
	    		+ "10- suero costenio, precio: 3000\r\n"
	    		+ "11- frijol refrito, precio: 4500\r\n"
	    		+ "12- queso fundido, precio: 4500\r\n"
	    		+ "13- tocineta picada, precio: 6000\r\n"
	    		+ "14- pinia;, precio: 2500 \n");
	    	    
	    boolean continuar2 = true;					
		while(continuar2) {
			System.out.println("OPCIONES: \n");
		    System.out.println("0-14- Eliminar ingrediente");
		    System.out.println("20- No eliminar ingrediente");	    
		    int opcionSeleccionada2 = Integer.parseInt(input("Seleccione una opcion "));
			if(opcionSeleccionada2 > 0 && opcionSeleccionada2 < 15) {
				if(productoFinal != null) {
					productoFinal.eliminarIngrediente(this.restaurante.getIngredientes().get(opcionSeleccionada2));
					System.out.println("Ingrediente eliminado");
				}
				else {
					productoFinal = new ProductoAjustado(productoBase);
					productoFinal.eliminarIngrediente(this.restaurante.getIngredientes().get(opcionSeleccionada2));
					System.out.println("Ingrediente eliminado");
				}
						   
			}
			else if(opcionSeleccionada2==20) {
				continuar2 = false;
			}
		}		
		
		if(productoFinal == null) {
			return productoBase;
		}
		else {
			return productoFinal;
		}
			
	}
		

	private void consultarPedido() throws IOException {		
		String id = input("Ingrese id de factura");
		FileReader fr = new FileReader(new File("src/data/facturas.txt"));
		BufferedReader br = new BufferedReader(fr);
		String s;
		int i = 0;
		int numeroId = Integer.parseInt(id);
		String factura = "";
		while((s = br.readLine())!=null){
			if(i == numeroId) {
				factura = factura + s + "\n";
			}
			if(s.contains(";")) {
				i++;
			}
			
		}
		br.close();	
		System.out.println(factura);
	}	

	public File getArchivoIngredientes() {
		return archivoIngredientes;
	}

	public File getArchivoMenu() {
		return archivoMenu;
	}

	public File getArchivoCombos() {
		return archivoCombos;
	}
	
	public Restaurante getRestaurante() {
		return restaurante;
	}

	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
}
