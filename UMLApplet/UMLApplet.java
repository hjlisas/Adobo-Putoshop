/*
 * @(#)UMLApplet.java 1.0 02/01/08
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_2\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class UMLApplet extends JApplet
{
   
   Vector        umlCanvasVector    = new Vector();
   
//   UMLCheckbox   umlCheckbox        = new UMLCheckbox( umlCanvasVector );
   
   JDesktopPane  desktop = new JDesktopPane();
   
   public void init() 
   {
      setLayout( new BorderLayout() );
//      cp.add( "North", umlCheckbox );
//      cp.add( "Center", umlCanvas );
   }
   
   public void paint( Graphics g )
   {
      super.paint( g );
   }
}


