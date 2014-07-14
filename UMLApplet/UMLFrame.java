import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
   
public class UMLFrame extends JPanel
{
   private String name;

   public UMLFrame( UMLCanvas theCanvas, int fileNumber )
   {
      name = "Untitled " + fileNumber;
      add( theCanvas );
      addMouseListener( theCanvas );
      addMouseMotionListener( theCanvas );      
   }
   
   public String getName()
   {
      return name;
   }
   
   public void setName( String newName )
   {
      name = newName;
   }
}   
