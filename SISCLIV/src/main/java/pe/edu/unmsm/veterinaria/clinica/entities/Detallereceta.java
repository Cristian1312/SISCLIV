package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 25/10/2015 10:09:39 PM by Hibernate Tools 3.4.0.CR1

/**
 * Detallereceta generated by hbm2java
 */
public class Detallereceta implements java.io.Serializable {

	private Integer iddetallereceta;
	private String nombre;
	private String indicaciones;

	public Detallereceta() {
	}

	public Detallereceta(String nombre, String indicaciones) {
		this.nombre = nombre;
		this.indicaciones = indicaciones;
	}

	public Integer getIddetallereceta() {
		return this.iddetallereceta;
	}

	public void setIddetallereceta(Integer iddetallereceta) {
		this.iddetallereceta = iddetallereceta;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIndicaciones() {
		return this.indicaciones;
	}

	public void setIndicaciones(String indicaciones) {
		this.indicaciones = indicaciones;
	}

}