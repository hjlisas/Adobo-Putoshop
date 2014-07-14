import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class BiRelationPropsWindow extends UniRelationPropsWindow
{
   protected Vector destinationName, sourceName;
   protected Hashtable destinationCardinality, sourceCardinality;
   
   protected RelationPanel destinationCardinalityPanel, 
                           sourceCardinalityPanel;

   protected JLabel destinationNameLabel, sourceNameLabel;
   protected JTextField destinationNameTF, sourceNameTF;
   
   public BiRelationPropsWindow( UMLObject umlObject )
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
      destinationCardinalityPanel.loadData( destinationCardinality );
      sourceCardinalityPanel.loadData( destinationCardinality );
   }
   
   public void createLabel()
   {
   }
   
   public void createTextField()
   {
   }      
   
   public void createTabbedPane()
   {
      component[TABBED_PANE] = new JTabbedPane();
      
      destinationCardinalityPanel = new RelationPanel( destinationCardinality, this );
      sourceCardinalityPanel = new RelationPanel( sourceCardinality, this );
      
      destinationNameLabel = new JLabel( "Destination" );
      destinationNameTF    = new JTextField( 10 );
      
      sourceNameLabel = new JLabel( "Source" );
      sourceNameTF    = new JTextField( 10 );
      
      String nameText = ( String )destinationName.elementAt( 0 );
      destinationNameTF.setText( nameText );      
      destinationNameTF.setEditable( false );
      
      nameText = ( String )sourceName.elementAt( 0 );
      sourceNameTF.setText( nameText );      
      sourceNameTF.setEditable( false );
      
      destinationCardinalityPanel.add( destinationNameLabel );
      destinationCardinalityPanel.add( destinationNameTF );
      
      sourceCardinalityPanel.add( sourceNameLabel );
      sourceCardinalityPanel.add( sourceNameTF );

      JTabbedPane thePane = ( JTabbedPane )component[TABBED_PANE];
      
      thePane.addTab( "Destination", destinationCardinalityPanel );
      thePane.addTab( "Source", sourceCardinalityPanel );
   }
   
   public Hashtable getDestinationCardinality()
   {
      return destinationCardinality;
   }
   
   public Hashtable getSourceCardinality()
   {
      return sourceCardinality;
   }
   
   public void loadData()
   {
      destinationName = ( ( Relationship )obj ).getDestinationName();
      destinationCardinality = ( ( Relationship )obj ).getDestinationCardinality();
      sourceName = ( ( Relationship )obj ).getSourceName();
      sourceCardinality = ( ( Relationship )obj ).getSourceCardinality();
   }
   
   public void windowClosing( WindowEvent we )
   { 
      hideFrame();
      obj.updateFromProps();
   }
}