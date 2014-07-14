import javax.swing.*;
import java.util.*;

public class AbstractClassPropsWindow extends ClassPropsWindow
{
   
   public AbstractClassPropsWindow( UMLObject umlObject )
   {
      super( umlObject );
   }
   
   public void createTabbedPane()
   {
      JTabbedPane thePane = ( JTabbedPane )component[TABBED_PANE];
      
      fieldsPanel = new TabPanel( fields );
      methodsPanel = new TabPanel( methods, true );
      
      thePane.addTab( "Fields", fieldsPanel );
      thePane.addTab( "Methods", methodsPanel );
   }
   
   public Vector getVectorName()
   {
      JTextField ref = ( JTextField )component[TEXT_FIELD];
      Vector dummyName = new Vector();
      String newName = ref.getText();

      dummyName.addElement( new String( "<<abstract>>" ) );
      dummyName.addElement( new String( newName ) );
      
      return dummyName;
   }   

   public void createLabel()
   {
      ( ( JLabel )component[LABEL] ).setText( "Abstract Class Name" );
   }

   public void createTextField()
   {
      ( ( JTextField )component[TEXT_FIELD] ).setText( ( String )name.elementAt( 1 ) );      
   }
   
}