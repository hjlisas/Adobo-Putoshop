import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class UMLCheckbox extends JToolBar implements ActionListener
{
   public static final int MANIPULATE      = 0;
   public static final int CONCRETE_CLASS  = 1;
   public static final int ABSTRACT_CLASS  = 2;
   public static final int INTERFACE       = 3;
   public static final int INHERITANCE     = 4;
   public static final int ASSOCIATION     = 5;
   public static final int DEPENDENCY      = 6;
   public static final int COMPOSITION     = 7;
   public static final int AGGREGATION     = 8;
   
   private String[] nameArray = 
      { "Manipulate", "Concrete Class", "Abstract Class",
        "Interface",  "Inheritance",    "Association",
        "Dependency", "Composition",    "Aggregation" };
        
   private String[] iconFiles =
      { "move.gif",       "concrete.gif",    "abstract.gif",
        "interface.gif",  "inheritance.gif", "association.gif",
        "dependency.gif", "composition.gif", "aggregation.gif" };
        
   
   private ButtonGroup toggleGroup = new ButtonGroup();
   
   private Vector myCanvases;
   
   int itemSelectedIndex = 0;
   
   public UMLCheckbox( Vector theOthers ) 
   {
      super();
      
      myCanvases = theOthers;
      
      createPanel();
   }   
   
   public void createPanel()
   {
      for( int i = 0; i < nameArray.length; i++ )
      {
         createImageIconButton( iconFiles[i], nameArray[i] );
      }
   }
   
   public void createImageIconButton( String fileName, String tip )
   {
      ImageIcon icon = new ImageIcon( fileName, tip );
      JToggleButton toggleButton = new JToggleButton( icon );
      toggleButton.addActionListener( this );
      toggleButton.setName( tip );
      toggleButton.setToolTipText( tip );
      
      toggleGroup.add( toggleButton );
      add( toggleButton );
   }

   public void actionPerformed( ActionEvent ae )
   {
      String name = ( ( JToggleButton )ae.getSource() ).getName();
         
      for( int i = 0; i < nameArray.length; i++ )
      {
         if( name == nameArray[i]  )
         {
            tellCanvas( i );
         }
      }
   }
   
   public void tellCanvas( int selected )
   {
      Iterator canvasIterator = myCanvases.iterator();
      
      while( canvasIterator.hasNext() )
      {
         UMLCanvas myCanvas = ( UMLCanvas )canvasIterator.next();
         myCanvas.setOptionSelected( selected );
      }
   }
}
