/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.beans;

import java.math.BigDecimal;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pe.edu.unmsm.veterinaria.clinica.dao.ServicioDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IServicioDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author DENNYZ
 */
@ManagedBean
@SessionScoped
public class ServicioBean {

    Session session;

    Servicio servicio;
    List<Servicio> servicios;
    BigDecimal tarifaNueva;

    /**
     * Creates a new instance of ServicioBean
     */
    public ServicioBean() {
        this.servicio = new Servicio();
        tarifaNueva = BigDecimal.valueOf(0.00);
    }

    public void modificarTarifaServicio() {
        IServicioDao conexionServicio = new ServicioDao();
        
        servicio.setTarifa(tarifaNueva);
        try {
            System.out.println("SE VA A DAO MODIFICAR SERVICIO" + servicio.getNombre());
            conexionServicio.modificarServicio(session, servicio);
            servicio = new Servicio();
            servicios = getServicios();
        } catch (Exception ex) {
            System.out.println("cach! " + ex);
        }
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio usuario) {
        this.servicio = usuario;
    }

    public List<Servicio> getServicios() {

        this.session = null;
        Transaction transaction = null;

        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IServicioDao servicioDao = new ServicioDao();
            transaction = this.session.beginTransaction();
            servicios = servicioDao.getAll(session);
            transaction.commit();

            return servicios;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
            return null;
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public BigDecimal getTarifaNueva() {
        return tarifaNueva;
    }

    public void setTarifaNueva(BigDecimal tarifaNueva) {
        this.tarifaNueva = tarifaNueva;
    }
    
}
