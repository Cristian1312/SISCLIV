/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.interfaces;

import java.util.List;
import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Perfil;
/**
 *
 * @author Cristian1312
 */
public interface IPerfilDao {
    public Perfil buscarPerfil(Session session, int idPerfil) throws Exception;
    public List<Perfil> getAll(Session session) throws Exception;
    public void modificarPerfil(Session session, Perfil perfil) throws Exception;
    public void insertarPerfil(Session session, Perfil perfil) throws Exception;
}
