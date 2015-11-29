/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.beans;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import pe.edu.unmsm.veterinaria.clinica.dao.PerfilDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Perfil;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPerfilDao;

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

    /**
     * Creates a new instance of UsuarioBean
     */
    public PerfilBean() {
        this.perfil = new Perfil();
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

}
