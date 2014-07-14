import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;

public class Adobo extends JPanel
{
   Vector         umlCanvasVector = new Vector();
   JTabbedPane    tabbedPane      = new JTabbedPane( JTabbedPane.BOTTOM );
   UMLCheckbox    umlCheckbox     = new UMLCheckbox( umlCanvasVector );
   UMLToolbar     umlToolbar      = new UMLToolbar( this );
   
   public static void main( String[] args )
   {
      JFrame frame = new JFrame( "Adobo Putoshop" );
      Adobo panel = new Adobo();
      
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      frame.getContentPane().add( panel );
      frame.setSize( 800, 550 );
      frame.setVisible( true );
   }
   
   public Adobo() 
   {
      setLayout( new BorderLayout() );
      
      add( "North", umlToolbar );
      add( "Center", tabbedPane );
      add( "South", umlCheckbox );
   }
   
   public JFileChooser createFileChooser( int type, String fileName )
   {
      JFileChooser chooser = new JFileChooser();
      CustomFileFilter filter = new CustomFileFilter();

      filter.addExtension("uml");
      filter.setDescription("Adobo PutoShop Files");
      chooser.setFileFilter(filter);

      File defaultFile = new File( fileName + ".uml" );
      if( defaultFile.exists() ) 
      {
         chooser.setCurrentDirectory( defaultFile );
         chooser.setSelectedFile( defaultFile );
         chooser.setDialogType( type );
      }
   
      return chooser;
   }
   
   public void toolbarChanged( int selected )
   {
      if( selected == UMLToolbar.NEW )
      {
         UMLCanvas umlCanvas = new UMLCanvas( umlCanvasVector.size() );
         umlCanvasVector.addElement( umlCanvas );
         tabbedPane.addTab( umlCanvas.getName(), umlCanvas );
      }
      else if( selected == UMLToolbar.OPEN )
      {
         JFileChooser fc = createFileChooser( JFileChooser.OPEN_DIALOG, "*.uml" );
         String fileName;
         String tabName;
         
         if( fc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
         {
            fileName = fc.getSelectedFile().getPath();
            tabName = fc.getSelectedFile().getName();
            
            int dotIndex = tabName.indexOf( "." );
            if( dotIndex != -1 )
            {
               tabName = tabName.substring( 0, dotIndex );
            }
            
            UMLCanvas umlCanvas = new UMLCanvas( tabName );
            umlCanvas.open( fileName );
            umlCanvasVector.addElement( umlCanvas );
            tabbedPane.addTab( tabName, umlCanvas );
         }
      }
      else if( selected == UMLToolbar.SAVE )
      {
         UMLCanvas component = ( UMLCanvas )tabbedPane.getSelectedComponent();
         int componentIndex = tabbedPane.getSelectedIndex();
         String fileName = component.getFileName();
         String tabName;
         JFileChooser fc = createFileChooser( JFileChooser.SAVE_DIALOG, fileName );;
 
         if( fc.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION )
         {
            fileName = fc.getSelectedFile().getPath();
            tabName = fc.getSelectedFile().getName();
            
            int dotIndex = tabName.indexOf( "." );
            if( dotIndex == -1 )
            {
               fileName += ".uml";
            }
            else
            {
               tabName = tabName.substring( 0, dotIndex );
            }
            
            tabbedPane.setTitleAt( componentIndex, tabName );
            component.save( fileName, tabName );
         }
      }
   }
   
   class CustomFileFilter extends javax.swing.filechooser.FileFilter 
   {
      private Hashtable filters = null;
      private String description = null;
      private String fullDescription = null;
      private boolean useExtensionsInDescription = true;

      public CustomFileFilter() 
      {
         this.filters = new Hashtable();
      }

      public boolean accept( File file ) 
      {
         if( file != null ) 
         {
            if( file.isDirectory() ) 
            {
               return true;
            }
            
            String extension = getExtension( file );
            if(    extension != null 
                && filters.get( getExtension( file ) ) != null ) 
            {
               return true;
            }
         }  
         return false;
      }  

      public String getExtension( File file ) 
      {
         if( file != null ) 
         {
            String filename = file.getName();
            int i = filename.lastIndexOf( '.' );
            if( i > 0 && i < filename.length() - 1 ) 
            {
               return filename.substring( i + 1 ).toLowerCase();
            }
         }
         return null;
      }  

      public void addExtension( String extension ) 
      {
         if(filters == null) 
         {
            filters = new Hashtable(5);
         }
         filters.put( extension.toLowerCase(), this );
         fullDescription = null;
      }

      public String getDescription() 
      {
         if( fullDescription == null ) 
         {
            if( description == null ) 
            {
               fullDescription = description == null ? "(" 
                                                     : description 
                                                       + " (";
               Enumeration extensions = filters.keys();
               if( extensions != null ) 
               {
                  fullDescription += "." 
                                     + ( String )extensions.nextElement();
                  while( extensions.hasMoreElements() ) 
                  {
                     fullDescription += ", " 
                                        + (String) extensions.nextElement();
                  }
               }
      
               fullDescription += ")";
            } 
            else 
            {
               fullDescription = description;
            }
         }
         return fullDescription;
      }

      public void setDescription( String description ) 
      {
         this.description = description;
         fullDescription = null;
      }
   }
}


