package modelo;


import java.util.ArrayList;

public class Combo implements Producto{

	private ArrayList<ProductoMenu> itemsCombo;
	private double descuento;
	private String nombreCombo;
	
	
	public Combo(String nombre, double descuento) {
		this.nombreCombo = nombre;
		this.descuento = descuento;
		this.itemsCombo = new ArrayList<ProductoMenu>();
	}
	
	public void agregarItemACombo(ProductoMenu itemCombo) {
		this.itemsCombo.add(itemCombo);
		
	}
	
	@Override
	public int getPrecio() {
		int precioTotal = 0;
		for(ProductoMenu producto: this.itemsCombo) {
			precioTotal = precioTotal + producto.getPrecio();
		}
		double precioTotalDouble = precioTotal;
		double descuento = precioTotalDouble*this.descuento;
		double precioConDescuento = precioTotalDouble-descuento;
		
		return (int) precioConDescuento;
	}
	@Override
	public String getNombre() {

		return nombreCombo;
	}
	@Override
	public String generarTextoFactura() {
		String factura = "(Combo: " + this.nombreCombo + "\n";
		int precioSinDescuento = 0;
		for(ProductoMenu producto: this.itemsCombo) {
			factura = factura + producto.getNombre() + ": " + producto.getPrecio() + "\n";
			precioSinDescuento = precioSinDescuento + producto.getPrecio();
		}	
		int precioTotalDouble = this.getPrecio();
		int precioIva = (int) (precioTotalDouble*0.19);
		int precioNeto = (precioTotalDouble - precioIva);
		
		factura = factura + "Precio total 'sin descuento': " + precioSinDescuento + ", Precio total 'con descuento': " +
		                     precioTotalDouble + ", Precio neto: " + precioNeto + ", PrecioIva: " + precioIva + ")";  
		
		
		return factura;
	}

}
