
package py.com.abiti.esystem.egym.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.esystem.egym.util.ViewConfig;

@ViewConfig(uri = "AltaHorariosView ", displayName =  "horarios")
public class AltaHorariosView extends CustomComponent {

	
			
  private com.vaadin.ui.VerticalLayout mainlaLayout;
  private VerticalLayout formLayout; 
  private HorizontalLayout botonLayout;
  
  private Button btnGuardar;
  private Button btnSalir; 
  
  private DateField dfFecha;
  private ComboBox<String> cbxfuncionario; 
  
  public AltaHorariosView() {
	
	  mainlaLayout = new VerticalLayout();
	
	    
	  
	  
  }
  
	  
	 
	  
}  
	  
  
  
  
  
  
   
  
  
   

  
  
  

   
  
  


	