package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import pe.edu.unmsm.veterinaria.clinica.entities.Analisismedico;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IAnalisisMedicoDao;

public class AnalisisMedicoDao implements IAnalisisMedicoDao {

	@Override
	public void insertarAnalisisMedico(Session session, Analisismedico analisismedico) throws Exception {
		session.save(analisismedico);
	}

	@Override
	public List<Analisismedico> getAllPendientes(Session session) throws Exception {
		Criteria criteria = session.createCriteria(Analisismedico.class);
        criteria.add(Restrictions.eq("estadoAnalisisMedico", new String("Pendiente")));
        List<Analisismedico> analisisMedicos = criteria.list();
        for (Analisismedico am : analisisMedicos) {
        	Hibernate.initialize(am.getIdAnalisisMedico());
        	Hibernate.initialize(am.getAtencionmedica().getHistorial().getPaciente());
        	Hibernate.initialize(am.getClinicoveterinario());
        	Hibernate.initialize(am.getTipoanalisis());
        	Hibernate.initialize(am.getEstadoAnalisisMedico());
        }
        return analisisMedicos;
	}
}
