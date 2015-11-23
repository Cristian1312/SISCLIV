package pe.edu.unmsm.veterinaria.clinica.interfaces;

import java.util.List;

import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Analisismedico;

public interface IAnalisisMedicoDao {
	public void insertarAnalisisMedico(Session session, Analisismedico analisismedico) throws Exception;
	public List<Analisismedico> getAllPendientes(Session session) throws Exception;
}
