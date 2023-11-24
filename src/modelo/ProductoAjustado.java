package modelo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {

	private ArrayList<Ingrediente> agregados;
	private ArrayList<Ingrediente> eliminados;
	private ProductoMenu base;

	public ProductoAjustado(ProductoMenu base) {
		this.agregados = new ArrayList<Ingrediente>();
		this.eliminados = new ArrayList<Ingrediente>();
		this.base = base;
	}

	@Override
	public int getPrecio() {
		int precioTotal = this.base.getPrecio();
		for(Ingrediente ingrediente: this.agregados) {
			precioTotal = precioTotal + ingrediente.getCostoAdicional();
		}
		return precioTotal;
	}

	@Override
	public String getNombre() {
		
		return this.base.getNombre();
	}

	@Override
	public String generarTextoFactura() { 
		String factura = "(";
		for(Ingrediente ingrediente: this.agregados) {
			factura = factura + "Ingrediente: " + ingrediente.getNombre() + ", costo adicional: " + ingrediente.getCostoAdicional() + "\n";
		}
		double precioBaseDouble = this.getPrecio();		
		double valorIva = precioBaseDouble*0.19;
		double valorNeto = precioBaseDouble-valorIva;
		
		factura = factura + "Nombre producto ajustado: " + this.getNombre() +   ", precio total: " + precioBaseDouble + ", Precio neto: " + valorNeto + ", Precio Iva: " + valorIva + ")";
		
		
		return factura;

	}
	
	public void agregarIngrediente(Ingrediente nuevoIngrediente) {
		this.agregados.add(nuevoIngrediente);
	}
	
	public void eliminarIngrediente(Ingrediente ingredienteEliminado) {
		this.eliminados.add(ingredienteEliminado);
	}

}
