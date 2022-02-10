package py.com.abiti.esystem.egym.view; 

import org.jsoup.select.Evaluator.IsEmpty;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.esystem.egym.util.ViewConfig;

 @ViewConfig(uri = "AltaClienteCompleto",  displayName = "cliente") 
public  class AltaCliente extends CustomComponent implements View  {


 

 private  VerticalLayout mainLayout;
 private  HorizontalLayout formLayout;
 private  HorizontalLayout botonLayout;
 
 private Button btnGuardar;
 private Button btnSalir;
 
 private AltaPersonaView altaPersonaView;
 private AltaClienteView altaClienteView;
 
 public AltaCliente() {
	 
	 mainLayout = new VerticalLayout();
	 setCompositionRoot(mainLayout);
	 
	 crearComponente();
	 mostrarComponente();
	 
	 
	 
 }

private void mostrarComponente() {
	mainLayout.addComponent(formLayout);
	formLayout.addComponent(altaPersonaView);
	formLayout.addComponent(altaClienteView);
	
}

private void crearComponente() {
	
	formLayout  = new HorizontalLayout();
	botonLayout = new HorizontalLayout();
	
	 altaPersonaView = new AltaPersonaView();
	 altaClienteView = new AltaClienteView();
	
	
	
	
}

 
 }
 
 
 
