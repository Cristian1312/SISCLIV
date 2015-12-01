package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Pagoservicio;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPagoServicioDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

public class PagoServicioDao implements IPagoServicioDao {

    @Override
    public void insertarPagoServicio(Session session, Pagoservicio pagoservicio) throws Exception {
        session.save(pagoservicio);
    }

    @Override
    public List<Pagoservicio> listarPagosServicio(Session session) {
        List<Pagoservicio> pagosServicio;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Pagoservicio");
            pagosServicio = (List<Pagoservicio>) query.list();
            System.out.println("listarPagosServicio--->retorna lista de pagos de servicios con " + pagosServicio.size() + "elementos");
            return pagosServicio;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
