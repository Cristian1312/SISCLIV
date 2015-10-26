package pe.edu.unmsm.veterinaria.clinica.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pe.edu.unmsm.veterinaria.clinica.beans.PacienteBean;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;

@FacesConverter("pacienteConverter")
public class PacienteConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Paciente p=null;
		if(value != null && value.trim().length() > 0 && value.length()> 0) {
            PacienteBean service = (PacienteBean) fc.getExternalContext().getApplicationMap().get("pacienteBean");
            List<Paciente> pacientes= service.getPacientes();
            for(int i=0; i<pacientes.size();i++){
            	if(pacientes.get(i).getIdPaciente().equals(value)){
            		p=pacientes.get(i);
            	}
            }
                
        }
        else {
        	p=new Paciente();
        }
		return p;
	}

	@Override
	 public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            //return String.valueOf(((Paciente) object).getIdPaciente());
        	System.out.println("Paciente object: " + object);
            Paciente paciente = (Paciente) object;
            System.out.println(paciente.getIdPaciente());
            return String.valueOf(paciente.getIdPaciente());
        }
        else {
            return null;
        }
    } 

}
