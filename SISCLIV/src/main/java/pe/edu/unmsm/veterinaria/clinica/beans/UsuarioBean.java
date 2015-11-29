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
import pe.edu.unmsm.veterinaria.clinica.dao.UsuarioDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Perfil;
import pe.edu.unmsm.veterinaria.clinica.entities.Usuario;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPerfilDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IUsuarioDao;

/**
 *
 * @author DENNYZ
 */
@ManagedBean
@SessionScoped
public class UsuarioBean {

    Session session;

    Usuario usuario;
    List<Usuario> usuarios;
    String rePassword;
    String perfil;

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
        this.usuario = new Usuario();
    }

    public void registrarUsuario() {
        IPerfilDao conexionPerfil = new PerfilDao();
        IUsuarioDao conexionUsuario = new UsuarioDao();
        boolean registrado = false;
        try {
            for (Usuario u : usuarios) {
                if (usuario.getUsuario().equals(u.getUsuario())) {
                    registrado = true;
                }
            }
            if (!registrado) {
                Perfil p;
                p = conexionPerfil.buscarPerfil(session, Integer.parseInt(perfil));
                usuario.setPerfil(p);
                if (rePassword.equals(usuario.getPassword())) {
                    conexionUsuario.instertarUsuario(session, usuario);
                    usuario = new Usuario();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, "Error", "Contraseñas no coinciden"));
                }
            } else {
            }
            usuarios = new UsuarioDao().getAll(session);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }

    public void modificarUsuario() {
        IPerfilDao conexionPerfil = new PerfilDao();
        IUsuarioDao conexionUsuario = new UsuarioDao();
        try {
            Perfil p;
            p = conexionPerfil.buscarPerfil(session, Integer.parseInt(perfil));
            usuario.setPerfil(p);
            if (rePassword.equals(usuario.getPassword())) {
                System.out.println("SE VA A DAO MODIFICAR USUARIO"+usuario.getNombrecompleto());
                conexionUsuario.modificarUsuario(session, usuario);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Error", "Contraseñas no coinciden"));
            }
            usuario = new Usuario();
            usuarios = new UsuarioDao().getAll(session);
        } catch (Exception ex) {
            System.out.println("cach! "+ex);
        }
    }
    
    public void imprimirRelacionUsuarios(){
        
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        try {
            usuarios = new UsuarioDao().getAll(session);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

}
