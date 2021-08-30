package py.com.abiti.esystem.egym.view;

import org.eclipse.persistence.jpa.JpaEntityManagerFactory;

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
import py.com.abiti.esystem.egym.jpa.jpaPersona;
import py.com.abiti.esystem.egym.model.Usuario;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.ViewConfig;


@ViewConfig(uri = "usuarioView" , displayName = "usuario")
public class Usuarioview extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private VerticalLayout datosLayout;
	private HorizontalLayout botonLayout;
	
	private Grid<Usuario> gridUsuario;
	
	private Button btnSalir;
	
	private JpaUsuario jpaUsuario = new JpaUsuario (JpaUtil.getEntityManagerFactory());
	private jpaPersona jpaPersona = new jpaPersona(JpaUtil.getEntityManagerFactory());
	
	private Window ventana;
	
	public Usuarioview() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		cargarGrilla();
		btnSalir.addClickListener(e -> volver());
		}

	private void volver() {
		EgymUI.getCurrent().getNavigator().navigateTo("");
	}

	private void cargarGrilla() {
		gridUsuario.setItems(jpaUsuario.findUsuarioEntities());
		gridUsuario.addColumn(usu -> usu.getUsuario().toString()).setId("ID").setCaption("ID");
		gridUsuario.addColumn(usu -> usu.getAlias()).setId("ALIAS").setCaption("ALIAS");
		
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
		
		datosLayout = buidDatosLayout();
		formLayout.addComponent(datosLayout);
		
		botonLayout = buildBotonLayout();
		formLayout.addComponent(botonLayout);
		
		return formLayout;
	}

	private HorizontalLayout buildBotonLayout() {
		botonLayout = new HorizontalLayout();
		
		btnSalir = new Button();
		btnSalir.setCaption("Salir");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		botonLayout.addComponent(btnSalir);
		
		return botonLayout;
		
		
	}

	private VerticalLayout buidDatosLayout() {
		datosLayout = new VerticalLayout();
		datosLayout.setWidth("100%");
		datosLayout.setSpacing(false);
		datosLayout.setMargin(false);
		
		gridUsuario = new Grid<Usuario>();
		gridUsuario.setWidth("100%");
		
		datosLayout.addComponent(gridUsuario);
		return datosLayout;
	}
	}
 