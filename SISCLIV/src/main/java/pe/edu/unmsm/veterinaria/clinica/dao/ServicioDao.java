package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IServicioDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

public class ServicioDao implements IServicioDao {

    @Override
    public List<Servicio> getAll(Session session) throws Exception {
        return session.createCriteria(Servicio.class).list();
    }

    @Override
    public void modificarServicio(Session session, Servicio servicio) throws Exception {
        Transaction transaction = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            System.out.println("SE HARA LA ACTUALIZACION");
            session.update(servicio);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                session.getTransaction().rollback();
                System.out.println("ALGO SALIO MAL");
                System.out.println(e.toString());
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
