package modelo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.IngredienteRepetidoException;
import exceptions.ProductoRepetidoException;

public class Restaurante {
	
	private ArrayList<Ingrediente> ingredientes;
	private ArrayList<ProductoMenu> menuBase;
	private ArrayList<Combo> combos;
	private Pedido pedidoEnCurso;
	private ArrayList<Pedido> pedidos;

	public Restaurante() {
		this.pedidoEnCurso = null;
		this.combos = new ArrayList<Combo>();
		this.ingredientes = new ArrayList<Ingrediente>();
		this.menuBase = new ArrayList<ProductoMenu>();
		this.pedidos = new ArrayList<Pedido>();		
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente  ) {
		this.pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);		
	}
	
	public void cerrarYGuardarPedido() throws IOException {
		this.pedidos.add(pedidoEnCurso);
		this.pedidoEnCurso.guardarFactura(new File("src/data/facturas.txt"));
		this.pedidoEnCurso = null;
	}
	
	public ArrayList<Combo> getCombos() {
		return combos;
	}

	public Pedido getPedidoEnCurso() {
		return this.pedidoEnCurso;
	}
	
	public ArrayList<ProductoMenu> getMenuBase(){
		return this.menuBase;
	}

	public ArrayList<Ingrediente> getIngredientes(){
		return this.ingredientes; 
	}
	
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) throws IOException, IngredienteRepetidoException, NumberFormatException, ProductoRepetidoException {
		
		try {
			cargarIngredientes(archivoIngredientes);
			cargarMenu(archivoMenu);
		}
		catch(IngredienteRepetidoException e) {
			System.err.println(e.getMessage());
		}
		catch(ProductoRepetidoException e2) {
			System.err.println(e2.getMessage());
		}
		
		cargarCombos(archivoCombos);	
	}
	
	private void cargarIngredientes(File archivoIngredientes) throws IOException, IngredienteRepetidoException {		
		FileReader fr = new FileReader(archivoIngredientes);
		BufferedReader br = new BufferedReader(fr);
		String linea;
		
		while((linea=br.readLine())!= null) {
			String[] stringIngrediente = linea.split(";",2);
			String nombreIngrediente = stringIngrediente[0];
			int costoAdicional = Integer.parseInt(stringIngrediente[1]);
			Ingrediente nuevoIngrediente = new Ingrediente(nombreIngrediente, costoAdicional);			
			this.ingredientes.add(nuevoIngrediente);		
		}
		br.close();		
	}
	
	private void cargarMenu(File archivoMenu) throws NumberFormatException, IOException, ProductoRepetidoException {
		FileReader fr = new FileReader(archivoMenu);
		BufferedReader br = new BufferedReader(fr);
		String linea;
		
		while((linea=br.readLine())!= null) {
			String[] stringProducto = linea.split(";",2);
			String nombreProducto = stringProducto[0];
			int precioBase = Integer.parseInt(stringProducto[1]);
			ProductoMenu producto = new ProductoMenu(nombreProducto, precioBase);			
			this.menuBase.add(producto);	
		}
		br.close();	
	}
	
	private void cargarCombos(File archivoCombos) throws NumberFormatException, IOException {
		FileReader fr = new FileReader(archivoCombos);
		BufferedReader br = new BufferedReader(fr);
		String linea;
		
		while((linea=br.readLine())!= null) {
			String[] stringCombo = linea.split(";",5);
			String nombreCombo = stringCombo[0];
			int descuento = Integer.parseInt(stringCombo[1].replaceAll("%", ""));
			double descuentoDouble = descuento;
			double descuentoDoubleFinal = descuentoDouble/100;
			Combo combo = new Combo(nombreCombo, descuentoDoubleFinal);			
			
			for(ProductoMenu productoBase : this.menuBase) {
				if(productoBase.getNombre().equals(stringCombo[2]) || productoBase.getNombre().equals(stringCombo[3]) || productoBase.getNombre().equals(stringCombo[4])) {
					combo.agregarItemACombo(productoBase);
				}
			}
			this.combos.add(combo);
		}
		br.close();
	}
}
