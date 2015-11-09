package pe.edu.unmsm.veterinaria.clinica.interfaces;

import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Atencionmedica;

public interface IAtencionMedicaDao {
	public void insertarAtencionMedica(Session session, Atencionmedica atencionmedica) throws Exception;
}
