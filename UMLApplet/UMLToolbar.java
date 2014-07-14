import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class UMLToolbar extends JToolBar implements ActionListener
{
   public static final int NEW      = 0;
   public static final int OPEN     = 1;
   public static final int SAVE     = 2;
   
   private String[] nameArray = { "New", "Open", "Save" };
   private String[] iconFiles = { "new.gif", "open.gif", "save.gif" };
   
   private Adobo myPanel;
   
   int itemSelectedIndex = 0;
   
   public UMLToolbar( Adobo jPanel ) 
   {
      myPanel = jPanel;
      
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
      JButton button = new JButton( icon );
      button.addActionListener( this );
      button.setName( tip );
      button.setToolTipText( tip );
      
      add( button );
   }
   
   public int getOption()
   {
      return itemSelectedIndex;
   }

   public void actionPerformed( ActionEvent ae ) 
   {
      String name = ( ( JButton )ae.getSource() ).getName();
      
      for( int i = 0; i < nameArray.length; i++ )
      {
         if( name.equals( nameArray[i] ) )
         {
            tellPanel( i );
         }
      }
   }
   
   public void tellPanel( int selected ) 
   {
      myPanel.toolbarChanged( selected );
   }
}
