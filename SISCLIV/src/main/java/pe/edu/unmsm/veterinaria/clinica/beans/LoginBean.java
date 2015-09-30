/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

import pe.edu.unmsm.veterinaria.clinica.dao.UsuarioDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Usuario;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author Cristian1312
 * Probando un comentario sin malograr
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	
	Session session;
    Transaction transaction;
	
    private Usuario usuario;
    
    public LoginBean() {
    	this.usuario = new Usuario();
    }

	public void login() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage facesMessage = null;
        String view = "";
        
        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            UsuarioDao usuarioDao = new UsuarioDao();
            this.transaction = this.session.beginTransaction();
            this.usuario = usuarioDao.verificarUsuario(this.session, this.usuario);
            if (this.usuario != null) {
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido",
                        this.usuario.getUsuario());
                view = "tareasRecepcionista.xhtml";
            } else {
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error",
                        "Usuario y/o contraseña incorrecto");
                view = "index.xhtml";
            }
            this.transaction.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            context.addCallbackParam("view", view);
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
