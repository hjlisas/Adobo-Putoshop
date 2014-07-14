import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class ClassPropsWindow extends PropsWindow
{
   protected Vector name, fields, methods;
 
   protected TabPanel fieldsPanel;
   protected TabPanel methodsPanel;
   
   public ClassPropsWindow( UMLObject umlObject )
   {
      super( umlObject );
      setSize( 300, 300 );
   }
   
   public void createTextField()
   {
      ( ( JTextField )component[TEXT_FIELD] ).setText( ( String )name.elementAt( 0 ) );      
   }
   
   public void createTabbedPane()
   {
      JTabbedPane thePane = ( JTabbedPane )component[TABBED_PANE];
      
      fieldsPanel = new TabPanel( fields );
      methodsPanel = new TabPanel( methods );
      
      thePane.addTab( "Fields", fieldsPanel );
      thePane.addTab( "Methods", methodsPanel );
   }
   
   public Vector getVectorName()
   {
      JTextField ref = ( JTextField )component[TEXT_FIELD];
      Vector dummyName = new Vector();
      String newName = ref.getText();

      dummyName.addElement( new String( newName ) );
      
      return dummyName;
   }
   
   public Vector getFields()
   {
      Vector dummyFieldVector = fieldsPanel.getVector();
      
      return dummyFieldVector;
   }
   
   public Vector getMethods()
   {
      Vector dummyMethodVector = methodsPanel.getVector();
      
      return dummyMethodVector;
   }
   
   public void loadData()
   {
      name = ( ( Type )obj ).getName();
      fields = ( ( Type )obj ).getFields();
      methods = ( ( Type )obj ).getMethods();
   }

   public void createLabel()
   {
      ( ( JLabel )component[LABEL] ).setText( "Class Name" );
   }
   
   public void windowClosing( WindowEvent we )
   { 
      hideFrame();
      obj.updateFromProps();
   }
     
}