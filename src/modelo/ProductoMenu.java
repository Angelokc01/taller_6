package modelo;


public class ProductoMenu implements Producto{
	
	private String nombre;
	private int precioBase;
	
	public ProductoMenu(String nombre, int precioBase) {
		this.nombre = nombre;
		this.precioBase = precioBase;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPrecio() {
		return precioBase;
	}
	
	public String generarTextoFactura() {
		double precioBaseDouble = this.precioBase;		
		double valorIva = precioBaseDouble*0.19;
		double valorNeto = precioBaseDouble-valorIva;
		
		return  "(Nombre producto: " + this.getNombre() +  ", Precio total: " + precioBase + ", Precio neto: " + valorNeto + ", Precio Iva: " + valorIva + ")";
	}
	
}
