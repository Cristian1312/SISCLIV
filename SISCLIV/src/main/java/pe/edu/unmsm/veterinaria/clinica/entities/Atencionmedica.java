package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 25/10/2015 10:09:39 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Atencionmedica generated by hbm2java
 */
public class Atencionmedica implements java.io.Serializable {

	private Integer idAtencionMedica;
	private Estadoatencionmedica estadoatencionmedica;
	private Historial historial;
	private Medicoveterinario medicoveterinario;
	private Date fechaHoraRegistro;
	private Integer orden;
	private Set recetas = new HashSet(0);
	private Set analisismedicos = new HashSet(0);

	public Atencionmedica() {
	}

	public Atencionmedica(Estadoatencionmedica estadoatencionmedica, Historial historial,
			Medicoveterinario medicoveterinario, Date fechaHoraRegistro, Integer orden, Set recetas,
			Set analisismedicos) {
		this.estadoatencionmedica = estadoatencionmedica;
		this.historial = historial;
		this.medicoveterinario = medicoveterinario;
		this.fechaHoraRegistro = fechaHoraRegistro;
		this.orden = orden;
		this.recetas = recetas;
		this.analisismedicos = analisismedicos;
	}

	public Integer getIdAtencionMedica() {
		return this.idAtencionMedica;
	}

	public void setIdAtencionMedica(Integer idAtencionMedica) {
		this.idAtencionMedica = idAtencionMedica;
	}

	public Estadoatencionmedica getEstadoatencionmedica() {
		return this.estadoatencionmedica;
	}

	public void setEstadoatencionmedica(Estadoatencionmedica estadoatencionmedica) {
		this.estadoatencionmedica = estadoatencionmedica;
	}

	public Historial getHistorial() {
		return this.historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public Medicoveterinario getMedicoveterinario() {
		return this.medicoveterinario;
	}

	public void setMedicoveterinario(Medicoveterinario medicoveterinario) {
		this.medicoveterinario = medicoveterinario;
	}

	public Date getFechaHoraRegistro() {
		return this.fechaHoraRegistro;
	}

	public void setFechaHoraRegistro(Date fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Set getRecetas() {
		return this.recetas;
	}

	public void setRecetas(Set recetas) {
		this.recetas = recetas;
	}

	public Set getAnalisismedicos() {
		return this.analisismedicos;
	}

	public void setAnalisismedicos(Set analisismedicos) {
		this.analisismedicos = analisismedicos;
	}

}
