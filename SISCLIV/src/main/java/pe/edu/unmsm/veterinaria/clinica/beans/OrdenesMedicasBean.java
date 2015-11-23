package pe.edu.unmsm.veterinaria.clinica.beans;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.edu.unmsm.veterinaria.clinica.dao.AnalisisMedicoDao;
import pe.edu.unmsm.veterinaria.clinica.dao.PacienteDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Analisismedico;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IAnalisisMedicoDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPacienteDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
*
* @author Cristian1312
*/
@ManagedBean
@SessionScoped
public class OrdenesMedicasBean {
	Session session;
    Transaction transaction;
    
	private List<Analisismedico> ordenes;
	
	private List<Paciente> getPacienteParaOrdenMedica(int idPaciente) {
        this.session = null;
        this.transaction = null;
        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IPacienteDao pacienteDao = new PacienteDao();
            this.transaction = this.session.beginTransaction();
            List<Paciente> pacienteParaCarnet = pacienteDao.getPacienteById(session, idPaciente);
            this.transaction.commit();
            return pacienteParaCarnet;
        } catch (Exception ex) {
            if (this.transaction != null) {
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

	public void imprimirOrdenMedica(ActionEvent actionEvent, Analisismedico am) throws JRException, IOException {
    	Map<String, Object> parametros = new HashMap<String, Object>();
        String sexo = "";
        String fechaNac = "";
        String serviciosElegidos = "";
        int idPaciente = am.getAtencionmedica().getHistorial().getPaciente().getIdPaciente();
        parametros.put("txtNombreFacultad", "Facultad de Medicina Veterinaria");
        parametros.put("txtUniversidad", "UNMSM");
        parametros.put("txtNombreClinica", "Clinica de Animales Menores");
        
        Paciente paciente = this.getPacienteParaOrdenMedica(idPaciente).get(0);
        if (paciente.getSexo().equals(1)) sexo = "Macho";
        else sexo = "Hembra";
        
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		fechaNac = fecha.format(paciente.getFechaNac());
		serviciosElegidos = am.getTipoanalisis().getNombre();
        
        parametros.put("txtServicios", serviciosElegidos);
        parametros.put("txtSexo", sexo);
        parametros.put("txtFechaNac", fechaNac);
        
    	File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ordenMedicaPaciente.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getPacienteParaOrdenMedica(idPaciente)));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=ordenMedicaPaciente" + paciente.getNombre() + ".pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
	
	public List<Analisismedico> getOrdenes() {
		this.session = null;
        this.transaction = null;

        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IAnalisisMedicoDao analisisMedicoDao = new AnalisisMedicoDao();
            this.transaction = this.session.beginTransaction();
            ordenes = analisisMedicoDao.getAllPendientes(session);
            this.transaction.commit();
            
            return ordenes;
        } catch (Exception ex) {
            if (this.transaction != null) {
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

	public void setOrdenes(List<Analisismedico> ordenes) {
		this.ordenes = ordenes;
	}
}
