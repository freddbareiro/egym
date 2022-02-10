package py.com.abiti.esystem.egym.view;

import org.jsoup.select.Evaluator.IsEmpty;

import com.vaadin.navigator.View;
import com.vaadin.sass.internal.util.StringUtil;
import com.vaadin.ui.AbstractLocalDateTimeField;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.jpa.JpaCliente;
import py.com.abiti.esystem.egym.jpa.JpaPersona;
import py.com.abiti.esystem.egym.model.Cliente;
import py.com.abiti.esystem.egym.model.Persona;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.StringUtils;
import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig(uri = "altaCliente", displayName = "cliente")

public class AltaClienteView  extends CustomComponent implements View {
	
	private com.vaadin.ui.VerticalLayout mainlayouLayout; 
	private VerticalLayout formLayout;
	private HorizontalLayout botonLayout;
	private HorizontalLayout checkLayout;
	
	private Button btnGuardar; 
	private Button btnSalir; 
	
	
	private DateField dtF_inicio; 
	private CheckBox chkMatricula;
	private CheckBox chkHabilitado;
	private CheckBox chkActivo;
	
	private  JpaCliente jpaCliente = new JpaCliente(JpaUtil.getEntityManagerFactory());
	
	
	public AltaClienteView () {
		
		mainlayouLayout = new VerticalLayout();
		setCompositionRoot(mainlayouLayout);
		checkLayout = new HorizontalLayout();
		
		
		
		crearComponentes(); 
		mostrarComponemtes();
		
		btnGuardar.addClickListener(e -> guardarCliente());
		btnSalir.addClickListener(e -> salir());
		
		cargarcombo();
	    
	    
	    
	    
	   
	    
	    
	    
		
		
		
		
	}


	private void mostrarComponemtes() {
		
		mainlayouLayout.addComponent(formLayout);
		
		
		
		formLayout.addComponent(checkLayout);
		
		
		checkLayout.addComponent(chkActivo);
		
		
		checkLayout.addComponent(chkHabilitado);
		checkLayout.addComponent(chkMatricula);
		
		formLayout.addComponent(botonLayout);
	}	
		
		 
			
			
		
		
		
	
		
	


	private void  salir() {
		
		EgymUI.getCurrent().getNavigator().navigateTo("");
		
	}
		
	private void cargarcombo() {
		
		
	    
	    
		
		
		
	
	
	
	
		
		
		
		
		
	}


	private void guardarCliente() {
		
		if (controlarDatos() == true ) {
			
			return;  
			 
			
			
		}
		
		Cliente cl = new Cliente();
		
		cl.setActivo(chkActivo.getValue());
		
		cl.setMatricula(chkMatricula.getValue());
		cl.setHabilitado(chkHabilitado.getValue());
		cl.setFInicio(StringUtils.convertirLocalDateToDate(dtF_inicio.getValue()));
		
		try {
		
			jpaCliente.create(cl);
			Notification.show("Agrego el cliente correctamente");
			cargarcombo();
			
			
		} catch (Exception e) {
			
			Notification.show("Error al guardar cliente " + e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			
			
		}
		
		limpiarDatos();
		
	}


	private void limpiarDatos() {
		
		
		dtF_inicio.clear();
		chkActivo.clear();
		chkHabilitado.clear();
		chkMatricula.clear();
		
	}


	private boolean controlarDatos() {
		
		
			
			
		if (dtF_inicio.isEmpty()) {
			Notification.show("No se puede quedar vacia ",Notification.TYPE_WARNING_MESSAGE);
			dtF_inicio.focus();
			return true;
		}
				
			
		
		return false;
		
	}


	
	
		
		
		
	private void crearComponentes() {
		
		
		
		dtF_inicio = new 	DateField ();
		dtF_inicio.setCaption("FECHA DE INICIO");
		
		chkMatricula = new CheckBox();
		chkMatricula.setCaption("MATRICULA");
		chkHabilitado = new CheckBox();
		chkHabilitado.setCaption("HABILITADO");
		chkActivo = new CheckBox();
		chkActivo.setCaption("ACTIVO");
		
		formLayout = new VerticalLayout();
		botonLayout = new HorizontalLayout();
		checkLayout = new HorizontalLayout();
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSalir = new Button();
		btnSalir.setCaption("Salir");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		
		botonLayout.addComponent(btnGuardar);
		botonLayout.addComponent(btnSalir);
		
		
		
		
		
		
		
		
		
		
	
		
		
		
		
	}




		
		
		
		
	}

