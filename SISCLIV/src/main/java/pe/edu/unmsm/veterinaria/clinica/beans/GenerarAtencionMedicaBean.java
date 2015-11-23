package pe.edu.unmsm.veterinaria.clinica.beans;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.edu.unmsm.veterinaria.clinica.dao.AnalisisMedicoDao;
import pe.edu.unmsm.veterinaria.clinica.dao.AtencionMedicaDao;
import pe.edu.unmsm.veterinaria.clinica.dao.ClienteDao;
import pe.edu.unmsm.veterinaria.clinica.dao.PacienteDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Analisismedico;
import pe.edu.unmsm.veterinaria.clinica.entities.Atencionmedica;
import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.entities.Clinicoveterinario;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.entities.Tipoanalisis;
import pe.edu.unmsm.veterinaria.clinica.entities.Usuario;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IAnalisisMedicoDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IAtencionMedicaDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPacienteDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author DENNYZ
 */
@ManagedBean
@SessionScoped
public class GenerarAtencionMedicaBean {

    /**
     * Creates a new instance of GenerarAtencionMedicaBean
     */
    List<Atencionmedica> atenciones;

    private Atencionmedica atencion;
    private Paciente p;
    private List<Paciente> pacientes;
    private String[] selectedServices;
    private List<String> servicios;
    private boolean disableButtonImprimir;

    Session session;
    Transaction transaction;

    @PostConstruct
    public void init() {
    	this.servicios = new ArrayList<String>();
    	servicios.add("Uroanalisis");
    	servicios.add("Analisis de sangre");
    	setDisableButtonImprimir(false);
    }
    
    public GenerarAtencionMedicaBean() {
    	setDisableButtonImprimir(false);
    }

    public String registrarAtencion() {
        System.out.println("SE INICIA registrarAtencion");
        this.session = null;
        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = clienteDao.getClientebyId(session, atencion.getHistorial().getPaciente().getCliente().getIdCliente());
        System.out.println("CLIENTE: " + cliente.getNombres());

        p = atencion.getHistorial().getPaciente();
        p.setCliente(cliente);

        return "registrarAtencionMedica.xhtml";
    }

    public void finalizarAtencion() {
        this.session = null;

        atencion.setEstadoAtencionMedica("Atendido");
        atencion.setProximaCita(atencion.getProximaCita().substring(0, 11));;
        AtencionMedicaDao atencionMedicaDao = new AtencionMedicaDao();
        atencionMedicaDao.modificarAtencionMedica(session, atencion);
        atenciones = null;
        atenciones = getAtenciones();
        generarOrdenMedicaPaciente(atencion);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Correcto", "Atención médica registrada correctamente"));
        RequestContext.getCurrentInstance().update("atencionMedicaForm:mensajeGeneral");
        //return "generarOrdenMedica.xhtml";
    }

    public List<Atencionmedica> getAtenciones() {
        System.out.println("INICIO DE getAtenciones");
        this.session = null;
        this.transaction = null;
        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IAtencionMedicaDao atencionmedicaDao = new AtencionMedicaDao();
            this.transaction = this.session.beginTransaction();
            atenciones = atencionmedicaDao.getPendientes(session);
            this.transaction.commit();
            System.out.println("cantidad de atenciones pendientes: " + atenciones.size());
            return atenciones;
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
    
    /******************Para generar Orden Medica********************/
    public List<Paciente> getPacienteParaOrdenMedica(int idPaciente) {
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
    
    private void addAnalisisMedico(Analisismedico analisismedico) {
		this.session = null;
        this.transaction = null;
        
        try {
        	this.session = NewHibernateUtil.getSessionFactory().openSession();
        	IAnalisisMedicoDao analisisMedicoDao = new AnalisisMedicoDao();
        	this.transaction = this.session.beginTransaction();
        	analisisMedicoDao.insertarAnalisisMedico(this.session, analisismedico);
        	this.transaction.commit();
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
	}
    
    public void generarOrdenMedicaPaciente(Atencionmedica atM) {
    	Analisismedico uroanalisis = null;
        Analisismedico sanguineo = null;
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Usuario user = (Usuario) context.getSessionMap().get("usuario");
        Clinicoveterinario cVet = new Clinicoveterinario(user.getNombrecompleto(), "", null);
        cVet.setIdClinicoVeterinario(user.getIdUsuario());
        
        Tipoanalisis uro = new Tipoanalisis();uro.setIdTipoAnalisis(1);
		uroanalisis = new Analisismedico(atM, uro, cVet, "Pendiente", null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		
		Tipoanalisis sangre = new Tipoanalisis();sangre.setIdTipoAnalisis(2);
		sanguineo = new Analisismedico(atM, sangre, cVet, "Pendiente", null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        
		if (this.selectedServices != null) {
			setDisableButtonImprimir(true);
	        if (this.selectedServices.length == 2) {
	        	this.addAnalisisMedico(uroanalisis);
	        	this.addAnalisisMedico(sanguineo);
			} else {
				if (this.selectedServices[0].equals("Uroanalisis")) {
					this.addAnalisisMedico(uroanalisis);
				} else {
					this.addAnalisisMedico(sanguineo);
				}
			}
		}
    }
    
    public void imprimirOrdenMedica(ActionEvent actionEvent) throws JRException, IOException {
    	Map<String, Object> parametros = new HashMap<String, Object>();
        String sexo = "";
        String fechaNac = "";
        String serviciosElegidos = "";
        parametros.put("txtNombreFacultad", "Facultad de Medicina Veterinaria");
        parametros.put("txtUniversidad", "UNMSM");
        parametros.put("txtNombreClinica", "Clinica de Animales Menores");
		parametros.put("txtSexo", sexo);
        parametros.put("txtFechaNac", fechaNac);
        
        Paciente paciente = this.getPacienteParaOrdenMedica(p.getIdPaciente()).get(0);
        if (paciente.getSexo().equals(1)) sexo = "Macho";
        else sexo = "Hembra";
        
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		fechaNac = fecha.format(paciente.getFechaNac());
        
        for (int i = 0;i < this.selectedServices.length;i++) {
        	serviciosElegidos = this.selectedServices[i] + ", " + serviciosElegidos;
        }
        
        parametros.put("txtServicios", serviciosElegidos);
        
    	File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ordenMedicaPaciente.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getPacienteParaOrdenMedica(p.getIdPaciente())));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=ordenMedicaPaciente" + paciente.getNombre() + ".pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void setAtenciones(List<Atencionmedica> atenciones) {
        this.atenciones = atenciones;
    }

    public Atencionmedica getAtencion() {
        return atencion;
    }

    public void setAtencion(Atencionmedica atencion) {
        this.atencion = atencion;
    }

	public Paciente getP() {
		return p;
	}

	public void setP(Paciente p) {
		this.p = p;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public String[] getSelectedServices() {
		return selectedServices;
	}

	public void setSelectedServices(String[] selectedServices) {
		this.selectedServices = selectedServices;
	}

	public List<String> getServicios() {
		return servicios;
	}

	public void setServicios(List<String> servicios) {
		this.servicios = servicios;
	}

	public boolean isDisableButtonImprimir() {
		return disableButtonImprimir;
	}

	public void setDisableButtonImprimir(boolean disableButtonImprimir) {
		this.disableButtonImprimir = disableButtonImprimir;
	}
}