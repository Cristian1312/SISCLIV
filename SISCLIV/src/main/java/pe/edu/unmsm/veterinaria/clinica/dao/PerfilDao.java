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

import pe.edu.unmsm.veterinaria.clinica.interfaces.IPerfilDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author Cristian1312
 */
public class PerfilDao implements IPerfilDao {

    @Override
    public Perfil buscarPerfil(Session session, int idPerfil) throws Exception {
        Perfil per = null;
        Transaction transaction = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Perfil as p where p.idPerfil=" + idPerfil);
            per = (Perfil) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return per;
    }

    @Override
    public List<Perfil> getAll(Session session) throws Exception {
        List<Perfil> perfiles = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Perfil");
            perfiles = (List<Perfil>) query.list();

            return perfiles;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void modificarPerfil(Session session, Perfil perfil) throws Exception {
        Transaction transaction = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            System.out.println("SE HARA LA ACTUALIZACION");
            session.update(perfil);
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
    public void insertarPerfil(Session session, Perfil perfil) throws Exception {
        Transaction transaction;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(perfil);
            transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
