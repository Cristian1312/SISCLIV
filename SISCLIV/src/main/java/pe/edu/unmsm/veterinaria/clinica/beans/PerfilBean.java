/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import pe.edu.unmsm.veterinaria.clinica.dao.PagoServicioDao;
import pe.edu.unmsm.veterinaria.clinica.dao.PerfilDao;
import pe.edu.unmsm.veterinaria.clinica.dao.ServicioDao;
import pe.edu.unmsm.veterinaria.clinica.entities.ItemReporte;
import pe.edu.unmsm.veterinaria.clinica.entities.Pagoservicio;
import pe.edu.unmsm.veterinaria.clinica.entities.Perfil;
import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPerfilDao;
import pe.edu.unmsm.veterinaria.clinica.services.Reporte;

/**
 *
 * @author DENNYZ
 */
@ManagedBean
@SessionScoped
public class PerfilBean {

    Session session;

    Perfil perfil;
    List<Perfil> perfiles;

    List<ItemReporte> itemsReporte;
    double total;

    /**
     * Creates a new instance of UsuarioBean
     */
    public PerfilBean() {
        this.perfil = new Perfil();
        total = 0.0;
    }

    public void registrarPerfil() {
        IPerfilDao conexionPerfil = new PerfilDao();
        try {
            conexionPerfil.insertarPerfil(session, perfil);
        } catch (Exception ex) {
            System.out.println(ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }

    public void modificarPerfil() {
        IPerfilDao conexionPerfil = new PerfilDao();
        try {
            conexionPerfil.modificarPerfil(session, perfil);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }

    public List<Perfil> getPerfiles() {
        try {
            System.out.println("getPerfiles");
            perfiles = new PerfilDao().getAll(session);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
        return perfiles;
    }

    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<ItemReporte> getItemsReporte() {
        Reporte reporte = new Reporte();
        List<Servicio> servicios = null;
        List<Pagoservicio> pagosServicio = null;
        servicios = new ServicioDao().listarServicios(session);
        pagosServicio = new PagoServicioDao().listarPagosServicio(session);
        itemsReporte = reporte.crearReporteServicios(servicios, pagosServicio);

        return itemsReporte;
    }

    public void setItemsReporte(List<ItemReporte> itemsReporte) {
        this.itemsReporte = itemsReporte;
    }

    public double getTotal() {
        total = 0.0;
        for (ItemReporte i : itemsReporte) {
            total += i.getSubtotal();
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
