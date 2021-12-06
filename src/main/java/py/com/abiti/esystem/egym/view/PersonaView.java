package py.com.abiti.esystem.egym.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.jpa.JpaUsuario;
<<<<<<< Updated upstream
import py.com.abiti.esystem.egym.jpa.JpaPersona;
=======
import py.com.abiti.esystem.egym.jpa.jpaPersona;
>>>>>>> Stashed changes
import py.com.abiti.esystem.egym.model.Persona;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig(uri = "PersonaView" , displayName = "persona")
public class PersonaView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout datoLayout;
	private HorizontalLayout botonLayout;
	
	
	private Grid<Persona> gridPersona;
	
	private Button btnSalir;
	
<<<<<<< Updated upstream
	private JpaPersona jpaPersona = new JpaPersona(JpaUtil.getEntityManagerFactory());
=======
	private jpaPersona jpaPersona = new jpaPersona(JpaUtil.getEntityManagerFactory());
>>>>>>> Stashed changes
	private JpaUsuario jpausuario = new JpaUsuario(JpaUtil.getEntityManagerFactory());
	
	private Window ventana;
	private VerticalLayout datosLayout;
	
	
	public PersonaView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		cargarGrilla();
		btnSalir.addClickListener(e ->volver());
	}

	private void volver() {
		EgymUI.getCurrent().getNavigator().navigateTo("");
	}

	private void cargarGrilla() {
		
		gridPersona.setItems(jpaPersona.findPersonaEntities());
		gridPersona.addColumn(usu -> usu.getPersona().toString()).setId("ID").setCaption("ID");
		gridPersona.addColumn(usu -> usu.getPersona()).setId("NOMBRE").setCaption("NOMBRE");
		
	}

	private void buildMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("70%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		setWidth("100%");
		setHeight("-1px");
		
		formLayout = buildFormLayout();
		mainLayout.addComponent(formLayout);
		
	}

	private VerticalLayout buildFormLayout() {
		formLayout = new VerticalLayout();
		formLayout.setHeight("-1px");
		formLayout.setWidth("100%");
		formLayout.setMargin(false);
		
		datosLayout = builDatosLayout();
		formLayout.addComponent(datosLayout);
		
		botonLayout = buildBotonLayout(); 
		//formLayout.addComponent(botonLayout);
		formLayout.addComponent(botonLayout);
		
		
		return formLayout;
	}

	private HorizontalLayout buildBotonLayout() {
		botonLayout = new HorizontalLayout();
		
		btnSalir = new Button();
		btnSalir.setCaption("SALIR");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		
		botonLayout.addComponent(btnSalir);
		
		return botonLayout;
	
	}

	private VerticalLayout builDatosLayout() {
		datosLayout = new VerticalLayout();
		datoLayout.setWidth("100%");
		datoLayout.setSpacing(false);
		datosLayout.setMargin(false);
		
		gridPersona = new Grid<Persona>();
		gridPersona.setWidth("100%");
		
		datosLayout.addComponent(gridPersona);
		return datosLayout;
		
	}

	

	
	}
