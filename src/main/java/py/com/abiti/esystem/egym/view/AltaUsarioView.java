package py.com.abiti.esystem.egym.view;

import com.vaadin.navigator.View;


import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.abiti.esystem.egym.jpa.JpaUsuario;
import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.jpa.JpaPersona;
import py.com.abiti.esystem.egym.model.Persona;
import py.com.abiti.esystem.egym.model.Usuario;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.ViewConfig;



@ViewConfig(uri = "altaUsuario", displayName = "usuario")
public class AltaUsarioView extends CustomComponent implements View {

  private com.vaadin.ui.VerticalLayout mainlayout;
  
  private VerticalLayout formLayout;
  
  private HorizontalLayout botonLayout;
  
  private Button btnGuardar; 
  private Button btnSalir; 
  
  private  TextField txtAlias; 
  private   PasswordField txtClave;
  private   PasswordField txtRepetir;
  
  private ComboBox<Persona> cbxPersona;
  
  private JpaPersona jpaPersona = new JpaPersona(JpaUtil.getEntityManagerFactory());
   
  
  
  private JpaUsuario jpaUsuario = new JpaUsuario(JpaUtil.getEntityManagerFactory());
  
  
  
  public AltaUsarioView() {
  
	  
	  mainlayout = new VerticalLayout();
	  botonLayout = new HorizontalLayout(); 
	  
	  setCompositionRoot(botonLayout);
	  
	  crearComponentes(); 
	  
	  btnGuardar.addClickListener(e -> guardarUsuario());
	  btnSalir.addClickListener(e -> salir());
	 
	  mostrarComponentes();
	  
	  
  } 
	  
	  
	  
	private void mostrarComponentes() {
	
	
	
mainlayout.addComponent(formLayout);

formLayout.addComponent(txtAlias);
 formLayout.addComponent(txtRepetir);
 formLayout.addComponent(txtClave);
 formLayout.addComponent(botonLayout);
botonLayout.addComponent(btnGuardar);
botonLayout.addComponent(btnSalir);



	}
	private void salir() {
		
		EgymUI.getCurrent().getNavigator().navigateTo( "");
	// TODO Auto-generated method stub
	
	}

	private void cargarcombo() {
		
		cbxPersona.setItems(jpaPersona.findPersonaEntities());
		cbxPersona.setEmptySelectionAllowed(false);
		cbxPersona.setItemCaptionGenerator(gen -> gen.getNombre() + " "
			+	gen.getApellido());
		
		
		
	}
	





	private void crearComponentes() {
		
		formLayout = new VerticalLayout();
	
		txtAlias = new TextField();
		txtAlias.setCaption("ALIAS");
		txtRepetir = new PasswordField();
		txtRepetir.setCaption("REPETIR CLAVE");
		
		txtClave = new PasswordField();
		txtClave.setCaption("CLAVE");
		btnGuardar = new Button();
		
		btnSalir = new Button();
		btnSalir.setCaption("Salir");
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		 
		
		
		 
	
	}
	
	
	private void guardarUsuario() {
		  
	  if (controlarDatos()== false )   {
		  
		  return; 
		  
		  
		  
		 
	  }
	  
	  
	  
	  if (! txtRepetir.getValue().isEmpty() ) {
		  
		  return; 
		  
		  
	  } 
	  
	  
	  Usuario usu = new Usuario();
	  usu.setPersona(cbxPersona.getValue());
	  usu.setAlias(txtAlias.getValue());
	   
	  usu.setClave(txtClave.getValue());
	  
	  jpaUsuario.create(usu);
	  Notification.show("Usuario creada correctamente");
	
	  
	  
   }
	
	
	
	
	private boolean controlarDatos() {
		
		 if (txtAlias.getValue().isEmpty()) {
			 Notification.show("No puede quedar vacio", Notification.TYPE_WARNING_MESSAGE);
            txtAlias.focus(); 
            return false;
            
  
  	 
		 } 
		 if (txtClave.getValue().isEmpty()) {
			 Notification.show("El espacio no puede quedar vacio",Notification.TYPE_WARNING_MESSAGE);
			 txtClave.focus();
			 return false; 
		
	     }
		
	
	 
	 if (cbxPersona.getValue() == null) {
		 
		 Notification.show("No puede quedar vacio", Notification.TYPE_WARNING_MESSAGE);
		 cbxPersona.focus();
		 return false;  
		 
		 
	 }
	 if (txtRepetir.getValue().isEmpty()) {
		 Notification.show("Las contraseñas no coinciden" , Notification.TYPE_WARNING_MESSAGE);
		 txtRepetir.focus();
		 return false;
		 
	 }
      
	    return true; 
	    
	}
	
	
	
	 
// TODO Auto-generated method stub

  
}
 
 
  
  






