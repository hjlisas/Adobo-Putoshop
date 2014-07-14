import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class UniRelationPropsWindow extends PropsWindow
                                    implements Serializable
{
   protected Vector destinationName;
   protected Hashtable destinationCardinality;
   
   protected RelationPanel cardinalityPanel;

   protected JLabel nameLabel;
   protected JTextField nameTF;

   public UniRelationPropsWindow( UMLObject umlObject )
   {
      super( umlObject );
      setSize( 300, 300 );
   }
   
   public void update()
   {
      loadData();
      createTextField();
      createLabel();
      createTabbedPane();
      cardinalityPanel.loadData( destinationCardinality );
   }
   
   public void createLabel()
   {
      ( ( JLabel )component[LABEL] ).setText( "Destination" );
   }
   
   public void createTextField()
   {
      String nameText = ( String )destinationName.elementAt( 0 );
      ( ( JTextField )component[TEXT_FIELD] ).setText( nameText );      
      ( ( JTextField )component[TEXT_FIELD] ).setEditable( false );
   }      
   
   public void createTabbedPane()
   {
      cardinalityPanel = new RelationPanel( destinationCardinality, this );

      component[TABBED_PANE] = cardinalityPanel;
   }
   
   public Hashtable getDestinationCardinality()
   {
      return destinationCardinality;
   }
   
   public void loadData()
   {
      destinationName = ( ( Relationship )obj ).getDestinationName();
      destinationCardinality = ( ( Relationship )obj ).getDestinationCardinality();
   }
   
   public void windowClosing( WindowEvent we )
   { 
      hideFrame();
      obj.updateFromProps();
   }
}
   
   