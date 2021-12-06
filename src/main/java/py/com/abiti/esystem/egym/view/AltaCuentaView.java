package py.com.abiti.esystem.egym.view;

import java.sql.Date;

import org.eclipse.persistence.internal.helper.JPAClassLoaderHolder;

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
import py.com.abiti.esystem.egym.jpa.JpaCliente;
import py.com.abiti.esystem.egym.jpa.JpaCuenta;
import py.com.abiti.esystem.egym.jpa.JpaPlanPago;
<<<<<<< Updated upstream
import py.com.abiti.esystem.egym.jpa.JpaPersona;
=======
import py.com.abiti.esystem.egym.jpa.jpaPersona;
>>>>>>> Stashed changes
import py.com.abiti.esystem.egym.model.Cliente;
import py.com.abiti.esystem.egym.model.Cuenta;
import py.com.abiti.esystem.egym.model.PlanPago;
import py.com.abiti.esystem.egym.util.JpaUtil;
import py.com.abiti.esystem.egym.util.StringUtils;
import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig (uri = "altaCuenta" , displayName = "cuenta")
public class AltaCuentaView  extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	
	private HorizontalLayout botonLayout;
	
	private Button btnGuardar;
	private Button btnSalir;
	
	private ComboBox<Cliente> cbxCliente;
	private DateField dfVencimiento;
	private TextField txtMatricula;
	private ComboBox<PlanPago> cbxPlanPago;
	private DateField dfVtoAnterior;
	
	private JpaCuenta jpaCuenta = new JpaCuenta(JpaUtil.getEntityManagerFactory());
	private JpaPlanPago jpaPlanPago = new JpaPlanPago(JpaUtil.getEntityManagerFactory());
	private JpaCliente jpaCliente = new JpaCliente(JpaUtil.getEntityManagerFactory());
	
	
	public AltaCuentaView() {
		
		mainLayout = new VerticalLayout();
		botonLayout = new HorizontalLayout();
		
		setCompositionRoot(mainLayout);
		
		crearComponente();
		
		btnGuardar.addClickListener(e -> guardarCuenta() );
		
		mainLayout.addComponent(botonLayout);
		
		mostrarComponentes();
	}





	private void mostrarComponentes() {
		mainLayout.addComponent(cbxCliente);
		mainLayout.addComponent(dfVencimiento);
		mainLayout.addComponent(txtMatricula);
		mainLayout.addComponent(cbxPlanPago);
		mainLayout.addComponent(dfVtoAnterior);
		
		botonLayout.addComponents(btnGuardar, btnSalir);
		
		mainLayout.addComponent(botonLayout);
		
	}





	private void guardarCuenta() {
		if (controlardatos() == false) {
		   	
			return;
		}
		
		Cuenta addCuenta = new Cuenta();
		addCuenta.setCliente(cbxCliente.getValue());
		addCuenta.setVencimiento(StringUtils.convertirLocalDateToDate(dfVencimiento.getValue()));
		addCuenta.setMatricula(Double.parseDouble(txtMatricula.getValue() ));
		addCuenta.setPlanPago(cbxPlanPago.getValue());
		addCuenta.setVencAterior(StringUtils.convertirLocalDateToDate(dfVtoAnterior.getValue()));
		
	}





	private boolean controlardatos() {
		
		if (cbxCliente.isEmpty()) {
			Notification.show("La casilla no puede quedar vacia", Notification.TYPE_WARNING_MESSAGE);
			cbxCliente.focus();
			return false;
			
		}
		
		if (dfVencimiento.isEmpty()) {
			
			Notification.show("La casilla no puede quedar vacia", Notification.TYPE_WARNING_MESSAGE);
			dfVencimiento.focus();
			return false;
		}
		
		if (txtMatricula.getValue().isEmpty()) {
			
			Notification.show("La casilla no puede quedar vacia", Notification.TYPE_WARNING_MESSAGE);
			txtMatricula.focus();
			return false;
		}
		
		if (cbxPlanPago.isEmpty()) {
			Notification.show("La casilla no puede quedar vacia", Notification.TYPE_WARNING_MESSAGE);
			cbxPlanPago.focus();
			return false;
			
		}
		
		if (dfVtoAnterior.isEmpty()) {
			Notification.show("La casilla no piede quedar vacia", Notification.TYPE_WARNING_MESSAGE);
			dfVtoAnterior.focus();
			return false;
		}
		
	return true;
		
	}





	private void crearComponente() {
		
		cbxCliente = new ComboBox<Cliente>();
		cbxCliente.setCaption("CLIENTE");
		dfVencimiento = new DateField();
		dfVencimiento.setCaption("VENCIMIENTO");
		txtMatricula = new TextField();
		txtMatricula.setCaption("MATRICULA");
		cbxPlanPago = new ComboBox<PlanPago>();
		cbxPlanPago.setCaption("PLAN PAGO");
		dfVtoAnterior = new DateField();
		dfVtoAnterior.setCaption("VTO ANTERIOR");
		
		btnSalir = new Button();
		btnSalir.setCaption("SALIR");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		
		btnGuardar = new Button();
		btnGuardar.setCaption("GUARDAR");
		btnGuardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		btnSalir.addClickListener(e -> salir());
		
		
	}





	private void salir() {
		EgymUI.getCurrent().getNavigator().navigateTo("");
	}
	
	private void cargarCombo() {
		cbxCliente.setItems(jpaCliente.findClienteEntities());
		cbxCliente.setEmptySelectionAllowed(false);
		cbxCliente.setItemCaptionGenerator(gen -> gen.getCliente().toString()  );
	}

}
