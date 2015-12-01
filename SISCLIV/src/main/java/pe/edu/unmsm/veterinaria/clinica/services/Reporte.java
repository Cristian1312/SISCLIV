/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.services;

import java.util.ArrayList;
import java.util.List;
import pe.edu.unmsm.veterinaria.clinica.entities.ItemReporte;
import pe.edu.unmsm.veterinaria.clinica.entities.Pagoservicio;
import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;

/**
 *
 * @author DENNYZ
 */
public class Reporte {

    public List<ItemReporte> crearReporteServicios(List<Servicio> servicios, List<Pagoservicio> pagosServicio) {
        List<ItemReporte> itemReportes = new ArrayList<>();
        int cantidad = 1;

        for (Servicio servicio : servicios) {
            ItemReporte item = null;
            for (Pagoservicio pago : pagosServicio) {
                if (servicio.getIdServicio().equals(pago.getServicio().getIdServicio())) {
                    item = armarItem(cantidad, servicio);
                    itemReportes.add(item);
                    cantidad++;
                }
            }
            cantidad = 1;
        }
        return itemReportes;
    }

    private static ItemReporte armarItem(int cantidad, Servicio servicio) {
        ItemReporte item = new ItemReporte();

        item.setCantidad(cantidad);
        item.setNombre(servicio.getNombre());
        item.setTarifa(servicio.getTarifa().doubleValue());
        item.setSubtotal(item.getCantidad() * item.getTarifa());
        return item;
    }
}
