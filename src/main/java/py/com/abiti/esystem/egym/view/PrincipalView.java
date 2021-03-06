package py.com.abiti.esystem.egym.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import py.com.abiti.esystem.egym.EgymUI;
import py.com.abiti.esystem.egym.util.ViewConfig;











@ViewConfig(uri = "", displayName = "Principal")
public class PrincipalView extends CustomComponent implements View {
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout datosLayout;
	@AutoGenerated
	private HorizontalLayout botonLayout;
	@AutoGenerated
	private Button btn3;
	@AutoGenerated
	private Button btn2;
	@AutoGenerated
	private Button btn1;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	private Button btn4 ;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btn10;
	private Button btn11;
	private Button btn12;
	private Button btn13;
	private Button btn14;
	private Button btn15;
	private Button btn16;
	
	private Window ventana;
	
	private List<Button> listBtn = new ArrayList<Button>();
	
	public PrincipalView() {
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		
		
		
		/*btn1.addClickListener(event -> MiesysUI.getCurrent().getNavigator().navigateTo("personaConsulta"));
		btn2.addClickListener(event -> MiesysUI.getCurrent().getNavigator().navigateTo("personaAlta"));
		btn3.addClickListener(event -> MiesysUI.getCurrent().getNavigator().navigateTo("ReunionAlta"));
		btn4.addClickListener(event -> MiesysUI.getCurrent().getNavigator().navigateTo("ReunionAbm"));
		btn5.addClickListener(event -> MiesysUI.getCurrent().getNavigator().navigateTo("hanConsulta"));
		btn6.addClickListener(Event -> MiesysUI.getCurrent().getNavigator().navigateTo("hanAlta"));
		btn7.addClickListener(event -> MiesysUI.getCurrent().getNavigator().navigateTo("hanPersona"));
		*/
		
		btn1.addClickListener(e-> EgymUI.getCurrent().getNavigator().navigateTo("altaProducto"));
		btn2.addClickListener(e -> EgymUI.getCurrent().getNavigator().navigateTo("consultaUsuario"));
		btn3.addClickListener(e -> EgymUI.getCurrent().getNavigator().navigateTo("altaCuenta"));
		btn4.addClickListener(e -> EgymUI.getCurrent().getNavigator().navigateTo("altaPersona"));

		btn5.addClickListener(e -> EgymUI.getCurrent().getNavigator().navigateTo("altaCliente"));
		btn6.addClickListener(e -> EgymUI.getCurrent().getNavigator().navigateTo("AltaClienteCompleto"));
		datosLayout.addComponent(botonLayout);
		datosLayout.setComponentAlignment(botonLayout, Alignment.MIDDLE_CENTER);
			
		
		
		
		
		
		
	
		
	}
/*	private void addUsuario() {
		
		AltaUsuarioView altaUsuario = new AltaUsuarioView();
		
		ventana = new Window("Alta Persona", altaUsuario);
		ventana.center();
		ventana.setSizeFull();
		UI.getCurrent().addWindow(ventana);
		
		
	}*/
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		//mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		
		// datosLayout
		datosLayout = buildDatosLayout();
		mainLayout.addComponent(datosLayout);
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildDatosLayout() {
		// common part: create layout
		datosLayout = new VerticalLayout();
		//datosLayout.setImmediate(false);
		datosLayout.setWidth("100.0%");
		datosLayout.setHeight("100.0%");
		datosLayout.setMargin(false);
		
		// botonLayout
		botonLayout = buildBotonLayout();
		datosLayout.addComponent(botonLayout);
		datosLayout.setComponentAlignment(botonLayout, new Alignment(48));
		
		return datosLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildBotonLayout() {
		// common part: create layout
		botonLayout = new HorizontalLayout();
		//botonLayout.setImmediate(false);
		botonLayout.setWidth("-1px");
		botonLayout.setHeight("-1px");
		botonLayout.setMargin(false);
		
		// btn1
		btn1 = new Button();
		btn1.setCaption("Alta de Producto");
		//btn1.setImmediate(true);
		btn1.setWidth("-1px");
		btn1.setHeight("-1px");
		botonLayout.addComponent(btn1);
		
		// btn2
		btn2 = new Button();
		btn2.setCaption("Mantenimiento Usuario");
		//btn2.setImmediate(true);
		btn2.setWidth("-1px");
		btn2.setHeight("-1px");
		botonLayout.addComponent(btn2);
		
		// btn3
		btn3 = new Button();
		btn3.setCaption("Alta Cuenta");
		//btn3.setImmediate(true);
		btn3.setWidth("-1px");
		btn3.setHeight("-1px");
		botonLayout.addComponent(btn3);
		
		btn4 = new Button();
		btn4.setCaption("Alta Persona");
		//btn3.setImmediate(true);
		btn4.setWidth("-1px");
		btn4.setHeight("-1px");
		botonLayout.addComponent(btn4);
		
		btn5 = new Button();
		btn5.setCaption("Alta Cliente");
		botonLayout.addComponent(btn5);
		
		btn6 = new Button();
		btn6.setCaption("Alta de Han");
		botonLayout.addComponent(btn6);
		
		btn7 = new Button();
		btn7.setCaption("Integrantes de Han");
		botonLayout.addComponent(btn7);
		
		return botonLayout;
	}
	
	
	
	
	
	
	
	

}
