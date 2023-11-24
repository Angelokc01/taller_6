package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Pedido {

	private int numeroPedidos;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<Producto> itemsPedido;
	
	
	public Pedido(String nombreCliente, String direccionCliente) {
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.itemsPedido = new ArrayList<Producto>();
		this.idPedido = 0;
	}
	
	public int getIdPedido(){		
		return this.idPedido;
	}
	
	public void agregarProducto(Producto nuevoItem) {
		this.itemsPedido.add(nuevoItem);
	}
	
	private int getPrecioNetoPedido() {		
		return this.getPrecioTotalPedido() - this.getPrecioIVAPedido();		
	}
	
	private int getPrecioTotalPedido() {
		int precioTotal = 0;
		for(Producto producto : itemsPedido) {
			precioTotal = precioTotal + producto.getPrecio();
		}		
		return precioTotal;	
	}

	private int getPrecioIVAPedido() {
		return (int) ((int) this.getPrecioTotalPedido()*0.19);	
	}

	private String generarTextoFactura() throws IOException {
		
		FileReader fr = new FileReader(new File("src/data/facturas.txt"));
		BufferedReader br = new BufferedReader(fr);
		String s;
		int i = 0;
		while((s = br.readLine())!=null){
			if(s.contains(";")) {
				i++;
			}
		}
		br.close();	
		this.idPedido = i;
		
		String factura = "[ID: " + this.idPedido + ", CLIENTE: " + this.nombreCliente + ", DIRECCION CLIENTE: " + this.direccionCliente + "\n";
		for(Producto producto : itemsPedido) {
			factura = factura + producto.generarTextoFactura() + "\n";
		}
		factura = factura + "PRECIO TOTAL: " + this.getPrecioTotalPedido() + ", PRECIO NETO " + this.getPrecioNetoPedido() + ", IVA: " + this.getPrecioIVAPedido() + "];\n"; 
		return factura;	
	}
	
	public void guardarFactura(File archivo) throws IOException {
		try {
			
			if(!archivo.exists()) {
				archivo.createNewFile();
			}
			FileWriter fw = new FileWriter(archivo, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter wr = new PrintWriter(bw);
			wr.append(this.generarTextoFactura());
			bw.close();
			wr.close();
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
