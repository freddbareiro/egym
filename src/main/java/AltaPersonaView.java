import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import Genero.Genero;
import JpaGenero.JpaGenero;
import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.jpa.jpaPersona;
import py.com.abiti.esystem.egym.model.Persona;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.StringUtils;
import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig(uri = "altaPersona" , displayName = "persona")
public class AltaPersonaView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	
	private HorizontalLayout BotonLayout;
	
	private Button btnGuardar;
	private Button btnSalir;
	
	private TextField txtNombre;
	private TextField txtApellido;
	private TextField Txtdireccion;
	private TextField txtNroDoc;
	private TextField txtRuc;
	private TextField txtTelefono;
	private DateField dfFecNac;
	private ComboBox<Genero> cbxGenero;
	
	private jpaPersona jpaPersona = new jpaPersona(JpaUtil.getEntityManagerFactory());
	private List<String> genero = new ArrayList<String>();
	
	
	public AltaPersonaView() {
		
		mainLayout = new VerticalLayout();
		BotonLayout = new HorizontalLayout();
		
		setCompositionRoot(mainLayout);
		
		crearComponente();
		
		btnGuardar.addClickListener(e -> guardarPersona() );
		
		mostrarComponentes();
	}


	private void crearComponente() {
		txtNombre = new TextField();
		txtNombre.setCaption("NOMBRE");
		txtApellido = new TextField();
		txtApellido.setCaption("APELLIDO");
		Txtdireccion = new TextField();
		Txtdireccion.setCaption("DIRECCION");
		txtNroDoc = new TextField();
		txtNroDoc.setCaption("NUMERO DOCUMENTO");
		txtRuc = new TextField();
		txtRuc.setCaption("RUC");
		txtTelefono = new TextField();
		txtTelefono.setCaption("TELEFONO");
		dfFecNac = new DateField();
		dfFecNac.setCaption("FECHA NACIMIENTO");
		cbxGenero = new ComboBox<Genero>();
		cbxGenero.setCaption("GENERO");
		cargarCombo();
		
		
		btnSalir = new Button();
		btnSalir.setCaption("SALIR");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		
		btnGuardar = new Button();
		btnGuardar.setCaption("GUARDAR");
		btnGuardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		
		btnSalir.addAttachListener(e -> salir());
		
		
		
	}


	private void salir() {
		EgymUI.getCurrent().getNavigator().navigateTo("");
	}


	private void cargarCombo() {
		cbxGenero.setItems(JpaGenero.findGeneroEntities());
		cbxGenero.setEmptySelectionAllowed(false);
		cbxGenero.setItemCaptionGenerator(gen -> gen.getDescripcion().toString());
		
	}


	private void mostrarComponentes() {
		mainLayout.addComponent(txtNombre);
		mainLayout.addComponent(txtApellido);
		mainLayout.addComponent(Txtdireccion);
		mainLayout.addComponent(txtNroDoc);
		mainLayout.addComponent(txtRuc);
		mainLayout.addComponent(dfFecNac);
		mainLayout.addComponent(cbxGenero);
		
		BotonLayout.addComponents(btnGuardar, btnSalir);
		
		mainLayout.addComponent(BotonLayout);
		
	}


	private void guardarPersona() {
		if (controlardatos() == false) {
			
			return;
			
		}
		
		Persona addPersona = new Persona();
		addPersona.setNombre(txtNombre.getValue().toUpperCase());
		addPersona.setApellido(txtApellido.getValue().toUpperCase());
		addPersona.setDireccion(Txtdireccion.getValue().toUpperCase());
		addPersona.setDocumento(txtNroDoc.getValue());
		addPersona.setRuc(txtRuc.getValue());
		addPersona.setGenero(cbxGenero.getValue());
		
		if (!dfFecNac.isEmpty()) {
			
			addPersona.setFechaNacimiento(StringUtils.convertirLocalDateToDate(dfFecNac.getValue()));
		}
		
		try {
			
			jpaPersona.create(addPersona);
			
			Notification.show("persona agregada correctamente");
			
			limpiarcampos();
			
		} catch (Exception e) {
			
			Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
		
		
		
	}


	private void limpiarcampos() {
		txtNombre.clear();
		txtApellido.clear();
		Txtdireccion.clear();
		txtNroDoc.clear();
		txtRuc.clear();
		cbxGenero.clear();
		dfFecNac.clear();
		
	}


	private boolean controlardatos() {
		if (txtNombre.getValue().isEmpty()) {
			
			Notification.show("La casilla no puede quedar vacia", Notification.TYPE_ERROR_MESSAGE);
			txtNombre.focus();
			return false;
		}
		
		if (txtApellido.getValue().isEmpty()) {
			
			Notification.show("La casilla no puede quedar vacia", Notification.TYPE_ERROR_MESSAGE);
			txtApellido.focus();
			return false;
		}
		
		if (Txtdireccion.getValue().isEmpty()) {
			
			Notification.show("La casilla no puede quedar vacia", Notification.TYPE_ERROR_MESSAGE);
			Txtdireccion.focus();
			return false;
		}
		if (txtNroDoc.getValue().isEmpty()) {
			
			Notification.show("La casilla no puede quedar vacia", Notification.TYPE_ERROR_MESSAGE);
			txtNroDoc.focus();
			return false;
			
		}
		
		return true; 
	}
	
	

}
