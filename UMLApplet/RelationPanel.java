import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class RelationPanel extends JPanel implements ActionListener,
                                                     Serializable
{
   private JButton    editButton   = new JButton( "Edit" );
   private JPanel     textPanel = new JPanel();
   private JPanel     buttonPanel  = new JPanel();
   
   private JLabel     methodLabel  = new JLabel( "Method" );
   private JLabel     cardinalityLabel = new JLabel( "Cardinality" );
   private JTextField keyTF            = new JTextField( 5 );
   private JTextField cardinalityInput = new JTextField( 5 );
   private JList      list             = new JList();
   private Vector     vectorList          = new Vector();
   private Hashtable  input;
   private UniRelationPropsWindow propsWindow;
   private AdapterMouseListener ma = new AdapterMouseListener();
                                          
   public RelationPanel( Hashtable theList )
   {
      input = theList;
      
      loadData( theList );
      setLayout( new BorderLayout() );
   
      keyTF.setEditable( false );
      
      textPanel.setLayout( new GridLayout( 2, 2 ) );
      textPanel.add( methodLabel );
      textPanel.add( keyTF );
      textPanel.add( cardinalityLabel );
      textPanel.add( cardinalityInput );
      
      editButton.setEnabled( true );
      editButton.addActionListener( this );

      buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS ) );
      buttonPanel.add( editButton );

      add( "North", textPanel );
      add( "Center", list );
      add( "East", buttonPanel );
      
      list.addMouseListener( ma );
   }
   
   public RelationPanel( Hashtable theList, UniRelationPropsWindow reference )
   {
      this( theList );
      propsWindow = reference;
   }

   private void edit()
   {
      String newCardinality = cardinalityInput.getText();
      String theKey         = keyTF.getText();
      
      input.put( theKey, newCardinality );
      loadData( input );
   }
      
   public void loadData( Hashtable inputList )
   {
      Hashtable subjectList = inputList;
      
      vectorList = new Vector();

      Enumeration enumKeys = subjectList.keys();
      
      while( enumKeys.hasMoreElements() )
      {
         vectorList.addElement( ( String )enumKeys.nextElement() );
      }
      
      list.setListData( vectorList );
   }

   public Hashtable getHashTable()
   {
      return input;      
   }
      
   public void actionPerformed( ActionEvent ae )
   {
      edit();
   }
   
   public void update()
   {
      propsWindow.update();
   }

   class AdapterMouseListener extends MouseAdapter implements Serializable
   {
      public void mouseClicked( MouseEvent me )
      {
         String selected = ( String )list.getSelectedValue();
         String cardinal = ( String )input.get( selected );
      
         if( selected != null )
         {
            keyTF.setText( selected );
            cardinalityInput.setText( cardinal );
         }         
      }
   }   
}
