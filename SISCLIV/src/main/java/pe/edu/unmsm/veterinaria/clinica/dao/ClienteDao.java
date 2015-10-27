package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.entities.Usuario;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IClienteDao;

public class ClienteDao implements IClienteDao {

	@Override
	public List<Cliente> getAll(Session session) throws Exception {
		List<Cliente> clientes = session.createCriteria(Cliente.class).list();
        for (Cliente cli : clientes) {
            Hibernate.initialize(cli.getIdCliente());
            Hibernate.initialize(cli.getNombres());
            Hibernate.initialize(cli.getApellidos());
            Hibernate.initialize(cli.getDireccion());
            Hibernate.initialize(cli.getDistrito());
            Hibernate.initialize(cli.getTelefono());
            Hibernate.initialize(cli.getEmail());
            Hibernate.initialize(cli.getDni());
        }

        return session.createCriteria(Cliente.class).list();
	}

	@Override
	public void insertarCliente(Session session, Cliente cliente) throws Exception {
		session.save(cliente);		
	}

	@Override
	public void modificarCliente(Session session, Cliente cliente) throws Exception {
		session.update(cliente);		
	}
}
