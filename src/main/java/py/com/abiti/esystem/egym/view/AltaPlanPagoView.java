package py.com.abiti.esystem.egym.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.jpa.JpaPlanPago;
import py.com.abiti.esystem.egym.model.PlanPago;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig (uri = "planpago", displayName = "planpago")

public class AltaPlanPagoView extends CustomComponent implements View {
	
	private  VerticalLayout formLayout;
	private VerticalLayout mainLayout;
	
	private Button btnGuardar;
	private Button btnSalir;
	
	private TextField  txtDescripcion; 
	private TextField txtCosto;
	
	private JpaPlanPago jpaPlanPago = new JpaPlanPago(JpaUtil.getEntityManagerFactory());

	public AltaPlanPagoView () {
		
		mainLayout = new VerticalLayout();
		
		
		setCompositionRoot(mainLayout);
		
         
         
         crearComponentes();	
         
         btnSalir.addClickListener(e -> salir());
         
         btnGuardar.addClickListener(e -> guardar());
         
     }

	private void guardar() {
		
		if (controlardatos() == false) {
			
			return;
			
		}
		
		PlanPago pl = new PlanPago();
		pl.setDescripcion(txtDescripcion.getValue().toUpperCase());
		pl.setCosto( Double.parseDouble(txtCosto.getValue() ));
		
		
		jpaPlanPago.create(pl);
		
		Notification.show("Se guardo correctamente");
		
	}

	private boolean controlardatos() {
		
		if (txtCosto.getValue().isEmpty()) {
			
			Notification.show("Se debe agregar costo", Notification.TYPE_WARNING_MESSAGE);
			txtCosto.focus();
			return false;
			
		}
			
			if (txtDescripcion.getValue().isEmpty()) {
				Notification.show("Se debe agregar descripcion",Notification.TYPE_WARNING_MESSAGE);
		       txtDescripcion.focus();
			
			
			}
			
			
		
		return false;
	}

	private void crearComponentes() {
	
		formLayout = new VerticalLayout();
		
		btnGuardar = new Button(); 
		btnGuardar.setCaption("Guardar");
		btnSalir = new Button();
		btnSalir.setCaption("Salir");
		txtDescripcion = new TextField();
		txtDescripcion.setCaption("Descripcion");
		txtCosto = new TextField();
		txtCosto.setCaption("Costo");
		
		
		
	}

	private void salir() {
		EgymUI.getCurrent().getCurrent().getNavigator().navigateTo("");		
		
	}


private void mostrarComponente (){
	
	mainLayout.addComponent(formLayout);
	
	formLayout.addComponent(txtDescripcion);
    formLayout.addComponent(txtCosto);	
    
    
    
    
    
    
    
    
	}


	
	




}

 

	
	

