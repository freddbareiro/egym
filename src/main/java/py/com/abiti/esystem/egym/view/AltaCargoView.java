package py.com.abiti.esystem.egym.view;

import org.jsoup.select.Evaluator.IsEmpty;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.jpa.JpaCargo;
import py.com.abiti.esystem.egym.model.Cargo;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig(uri = "altaCargo", displayName = "cargo")

public class AltaCargoView extends CustomComponent implements View{
	
	private com.vaadin.ui.VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout botonLayout;
	
	private Button btnGuardar;
	private Button btnSalir;
	
	private TextField txtCargo;
	private TextField txtDescripcion;
	
	private JpaCargo jpaCargo = new JpaCargo(JpaUtil.getEntityManagerFactory());
	
	public AltaCargoView() {
		
		mainLayout = new VerticalLayout();
		botonLayout = new HorizontalLayout();
		
		setCompositionRoot(mainLayout);
		
		crearComponente();
		
		btnGuardar.addClickListener(e -> guardarCargo());
		 
		mostrarComponente();
	}

	private void mostrarComponente() {
		mainLayout.addComponent(txtCargo);
		mainLayout.addComponent(txtDescripcion);
		
		botonLayout.addComponents(btnGuardar, btnSalir);
		
		mainLayout.addComponent(botonLayout);
	}

	private void guardarCargo() {
		if (controlardatos() == false) {
			
			return;
			
		}
		
		Cargo addCargo = new Cargo();
		addCargo.setDescripcion(txtDescripcion.getValue().toUpperCase());
		jpaCargo.create(addCargo);
		Notification.show("Guardado Correctamente");
		txtCargo.clear();
		txtDescripcion.clear();
	}

	private boolean controlardatos() {
		
		if (txtDescripcion.getValue().isEmpty()) {
			Notification.show("La Descripcion no puede quedar vacia", Notification.TYPE_WARNING_MESSAGE);
			txtDescripcion.focus();
			return false;
		}
		
		
		return true;
	}

	private void crearComponente() {
		txtDescripcion = new TextField();
		txtDescripcion.setCaption("DESCRIPCION");
		txtCargo = new TextField();
		txtCargo.setCaption("CARGO");
		
		btnSalir = new Button();
		btnSalir.setCaption("salir");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		btnSalir.addClickListener(e ->salir());
		
	}

	private void salir() {
	    EgymUI.getCurrent().getNavigator().navigateTo("");
	}
	

}
