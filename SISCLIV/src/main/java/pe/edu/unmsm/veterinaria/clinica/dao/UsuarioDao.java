/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pe.edu.unmsm.veterinaria.clinica.entities.Perfil;

import pe.edu.unmsm.veterinaria.clinica.entities.Usuario;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPerfilDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IUsuarioDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author Cristian1312
 */
public class UsuarioDao implements IUsuarioDao {

    @Override
    public Usuario verificarUsuario(Session session, Usuario usuario) throws Exception {
        Usuario user = null;
        String hql = "from Usuario where usuario = '" + usuario.getUsuario()
                + "' and password = '" + usuario.getPassword() + "'";
        Query query = session.createQuery(hql);
        if (!query.list().isEmpty()) {
            user = (Usuario) query.uniqueResult();
        }

        return user;
    }

    @Override
    public Usuario verificarCorreo(Session session, Usuario usuario) throws Exception {
        Usuario operario = null;
        String hql = "from Usuario where email = '" + usuario.getUsuario() + "'";
        Query querys = session.createQuery(hql);
        if (!querys.list().isEmpty()) {
            operario = (Usuario) querys.uniqueResult();

        }
        return operario;
    }

    @Override
    public void modificarUsuario(Session session, Usuario usuario) throws Exception {
        Transaction transaction = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            System.out.println("SE HARA LA ACTUALIZACION");
            session.update(usuario);
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

    @Override
    public void instertarUsuario(Session session, Usuario usuario) throws Exception {
        Usuario usu = null;
        Transaction transaction = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(usuario);
            transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Usuario> getAll(Session session) throws Exception {
        List<Usuario> usuarios = null;
        Perfil per = null;
        IPerfilDao enlaceperfil = new PerfilDao();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuario");
            usuarios = (List<Usuario>) query.list();

            for (Usuario u : usuarios) {
                per = (Perfil) enlaceperfil.buscarPerfil(session, u.getPerfil().getIdPerfil());
                u.setPerfil(per);
            }

            return usuarios;
        } catch (Exception ex) {
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
