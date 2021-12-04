package py.com.abiti.esystem.egym.view;
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
import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.jpa.JpaPersona;
import py.com.abiti.esystem.egym.model.Persona;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.StringUtils;
import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig(uri = "altaPersona" , displayName = "Alta de Persona")
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
	private ComboBox<String> cbxGenero;
	
	private JpaPersona jpaPersona = new JpaPersona(JpaUtil.getEntityManagerFactory());
	private List<String> genero = new ArrayList<String>();
	
	
	public AltaPersonaView() {
		
		mainLayout = new VerticalLayout();
		BotonLayout = new HorizontalLayout();
		
		setCompositionRoot(mainLayout);
		
		crearComponente();
		
		btnGuardar.addClickListener(e -> guardarPersona() );
		
		btnSalir.addClickListener(e -> salir());
		
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
		cbxGenero = new ComboBox<String>();
		cbxGenero.setCaption("GENERO");
		cargarCombo();
		
		
		btnSalir = new Button();
		btnSalir.setCaption("SALIR");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		
		btnGuardar = new Button();
		btnGuardar.setCaption("GUARDAR");
		btnGuardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		
		
	}


	private void salir() {
		EgymUI.getCurrent().getNavigator().navigateTo("");
	}


	private void cargarCombo() {
		genero.add("MASCULINO");
		genero.add("FEMENINO");
		cbxGenero.setItems(genero);
		cbxGenero.setEmptySelectionAllowed(false);
		//cbxGenero.setItemCaptionGenerator(gen -> gen.getDescripcion().toString());
		
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
		
		if (cbxGenero.getValue().toString() == ("MASCULINO") ) {
			
			addPersona.setGenero('M');
			
		}else {
			
			addPersona.setGenero('F');
			
		}
		
	
		
		if (!dfFecNac.isEmpty()) {
			
			addPersona.setFNac(StringUtils.convertirLocalDateToDate(dfFecNac.getValue()));
			
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
