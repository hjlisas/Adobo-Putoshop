import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class TabPanel extends JPanel implements ActionListener,
                                                Serializable
{
   private JButton    addButton    = new JButton( "Add" );
   private JButton    editButton   = new JButton( "Edit" );
   private JButton    deleteButton = new JButton( "Delete" );
   private JPanel     buttonPanel  = new JPanel();
   private JTextField input        = new JTextField( 8 );
   private JList      list         = new JList();
   private boolean   allowAbstract = false;
   private Vector     theList;
   private AdapterMouseListener ma = new AdapterMouseListener();
                                          
   public TabPanel( Vector theList )
   {
      this.theList = theList;
      
      loadData();
      setLayout( new BorderLayout() );
   
      addButton.setEnabled( true );
      addButton.addActionListener( this );
      
      editButton.setEnabled( true );
      editButton.addActionListener( this );
      
      deleteButton.setEnabled( true );
      deleteButton.addActionListener( this );

      buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS ) );
      buttonPanel.add( addButton );
      buttonPanel.add( editButton );
      buttonPanel.add( deleteButton );

      add( "North", input );
      add( "Center", list );
      add( "East", buttonPanel );
      
      list.addMouseListener( ma );
   }
   
   public TabPanel( Vector theList, boolean abstractAllow )
   {
      this( theList );
      allowAbstract = abstractAllow;
   }

   private void add()
   {
      String inputText = input.getText();
      
      if( !allowAbstract )
      {
         if( inputText.startsWith( "#" ) )
         {
            theList.add( inputText.substring( 1 ) );
         }
         else
         {
            theList.add( inputText );
         }
      }
      else
      {
         theList.add( inputText );
      }
      
      loadData();
   }
      
   private void edit()
   {
      try
      {
         theList.set( list.getSelectedIndex(), input.getText() );
      }
      catch( Exception e )
      {
         e.printStackTrace();
         // if out of bounds do nothing
      }
      loadData();
   }
      
   private void delete()
   {
      int startIndex = list.getSelectedIndex();
      int endIndex = theList.size() - 1;
      
      for( int i = startIndex; i < endIndex; i++ )
      {
         Object temporaryObject = theList.elementAt( i + 1 );
         
         theList.setElementAt( temporaryObject, i );
      }
      
      theList.removeElementAt( endIndex );
      loadData();
   }
   
   public void loadData()
   {
      list.setListData( theList );
   }

   public Vector getVector()
   {
      return theList;      
   }
      
   public void actionPerformed( ActionEvent ae )
   {
      String command = ae.getActionCommand();
      
      if( command.equals( "Add" ) )
      {
         add();
      }
      else if( command.equals( "Edit" ) )
      {
         edit();
      }
      else if( command.equals( "Delete" ) )
      {
         delete();
      }
   }
   
   class AdapterMouseListener extends MouseAdapter implements Serializable
   {
      public void mouseClicked( MouseEvent me )
      {
         input.setText( ( String )list.getSelectedValue() );
      }
   }
}
