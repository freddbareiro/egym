package py.com.abiti.esystem.egym.view;
import org.w3c.dom.Text;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.jpa.JpaProducto;
import py.com.abiti.esystem.egym.jpa.JpaUsuario;
import py.com.abiti.esystem.egym.model.Producto;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig(uri = "altaProducto" , displayName = "producto")
public class AltaProductoView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	
	private HorizontalLayout botonLayout;
	
	private Button btnGuardar;
	private Button btnSalir;
	
	private TextField txtDescripcion;
	private TextField txtCosto;
	
	private JpaProducto jpaProducto = new JpaProducto(JpaUtil.getEntityManagerFactory());
	
	
	public AltaProductoView() {
		
		mainLayout = new VerticalLayout();
		botonLayout = new HorizontalLayout();
		
		setCompositionRoot(mainLayout);
		
		crearComponente();
		
		btnGuardar.addClickListener(e -> guardarProducto());
		
		mostrarComponente();
		
	
	}


	private void mostrarComponente() {
		mainLayout.addComponent(txtDescripcion);
		mainLayout.addComponent(txtCosto);
		
		botonLayout.addComponents(btnGuardar, btnSalir);
		
		mainLayout.addComponent(botonLayout);
		
	}


	private void guardarProducto() {
		if (controlardatos() == false) {
			
			return;
		}
		
		Producto addProducto = new Producto();
		addProducto.setDescripcion(txtDescripcion.getValue().toUpperCase());
		addProducto.setCosto(Double.parseDouble(txtCosto.getValue()));
		jpaProducto.create(addProducto);
		Notification.show("Guardado correctamente.");
		txtCosto.clear();
		txtDescripcion.clear();
		
		
		
		
	}


	private boolean controlardatos() {
		
		if (txtDescripcion.getValue().isEmpty()) {
			Notification.show("La descripcion no puede quedar vacia", Notification.TYPE_WARNING_MESSAGE);
			txtDescripcion.focus();
			return false;
		}
		
		 if (txtCosto.getValue().isEmpty()) {
			 
			 Notification.show("El costo no puede quedar vacio", Notification.TYPE_WARNING_MESSAGE);
			 txtCosto.focus();
			 return false;
		 }
		 
		return true;
	}


	private void crearComponente() {
		
		txtDescripcion = new TextField();
		txtDescripcion.setCaption("DESCRIPCION");
		txtCosto = new TextField();
		txtCosto.setCaption("COSTO");
		
		btnSalir = new Button();
		btnSalir.setCaption("Salir");
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
	
	
		

	
	
