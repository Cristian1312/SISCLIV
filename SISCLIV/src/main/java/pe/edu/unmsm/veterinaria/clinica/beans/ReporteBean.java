/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import pe.edu.unmsm.veterinaria.clinica.dao.PagoServicioDao;
import pe.edu.unmsm.veterinaria.clinica.dao.ServicioDao;
import pe.edu.unmsm.veterinaria.clinica.entities.ItemReporte;
import pe.edu.unmsm.veterinaria.clinica.entities.Pagoservicio;
import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;
import pe.edu.unmsm.veterinaria.clinica.services.Reporte;

/**
 *
 * @author DENNYZ
 */
@ManagedBean
@SessionScoped
public class ReporteBean {

    Session session;

    List<ItemReporte> itemsReporte;

    /**
     * Creates a new instance of UsuarioBean
     */
    public ReporteBean(List<ItemReporte> itemsReporte) {
        this.itemsReporte = itemsReporte;
    }

    public List<ItemReporte> getItemsReporte() {
        Reporte reporte = new Reporte();
        List<Servicio> servicios = new ArrayList<>();
        List<Pagoservicio> pagosServicio = new ArrayList<>();

        servicios = new ServicioDao().listarServicios(session);
        pagosServicio = new PagoServicioDao().listarPagosServicio(session);

        itemsReporte = reporte.crearReporteServicios(servicios, pagosServicio);

        return itemsReporte;
    }

    public void setItemsReporte(List<ItemReporte> itemsReporte) {
        this.itemsReporte = itemsReporte;
    }

}
