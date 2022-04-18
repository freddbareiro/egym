package py.com.abiti.esystem.egym.view;

import org.jsoup.select.Evaluator.IsEmpty;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig(uri = "altaFuncionario", displayName = "cargo")

public class AltaFuncionarioView extends CustomComponent implements View{

	private com.vaadin.ui.VerticalLayout mainLayout;
	private VerticalLayout formLayout;
	private HorizontalLayout botonLayout;
	
	private Button btnGuardar;
	private Button btnSalir;
	
	private	AltaPersonaView personaView;
	
	
}
