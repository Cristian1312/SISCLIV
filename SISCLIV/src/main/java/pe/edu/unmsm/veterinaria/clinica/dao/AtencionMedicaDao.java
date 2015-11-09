package pe.edu.unmsm.veterinaria.clinica.dao;

import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Atencionmedica;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IAtencionMedicaDao;

public class AtencionMedicaDao implements IAtencionMedicaDao {

	@Override
	public void insertarAtencionMedica(Session session, Atencionmedica atencionmedica) throws Exception {
		session.save(atencionmedica);
	}

}
