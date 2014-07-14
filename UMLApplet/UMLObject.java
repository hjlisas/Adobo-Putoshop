import java.io.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;

public abstract class UMLObject extends Object
                                           implements MouseListener,
                                           MouseMotionListener,
                                           Serializable
{
   protected Color       color;
   protected transient   UMLCanvas   myCanvas;
   
   protected boolean     selected = false;
   protected transient   PropsWindow propsWindow;
   protected int         mouseDx, mouseDy;
   
   protected int x, y;         // coordinates for the object's center
   
   public static final int NULL_VALUE = -1;
   
   public UMLObject( UMLCanvas theCanvas )
   {
      setCanvas( theCanvas );
   }
   
   public void setCanvas( UMLCanvas theCanvas )
   {
      myCanvas = theCanvas;
   }
   
   public int getX()
   {
      return x;
   }
   
   public int getY()
   {
      return y;
   }
   
   public void setXY( int xInput, int yInput )
   {
      x = xInput;
      y = yInput;
   }
      
   public boolean isSelected()
   {
      return selected;
   }
   
   public void select()
   {
      selected = true;
      setColor( Color.red );
   }
   
   public void deselect()
   {
      selected = false;
      setColor( Color.black );
   }
   
   public abstract void draw( Graphics g );
      
   public abstract boolean insideMe( int xInput, int yInput );
   
   public abstract void createPropsWindow(); // should call PropsWindow
   
   public void showPropsWindow()
   {
      propsWindow.setVisible( true );
   }
   
   public void hidePropsWindow()
   {
      propsWindow.setVisible( false );
   }
   
   public abstract void updateFromProps();
   
   public void repaintCanvas()
   {
      myCanvas.repaint();
   }
   
   // MouseListener event handlers
   
   public void mouseClicked( MouseEvent me )
   {
      if( me.getClickCount() == 2
          && insideMe( me.getX(), me.getY() )
          && myCanvas.getOption() == UMLCheckbox.MANIPULATE )
      {
         createPropsWindow();
      }
   }
   
   public void mousePressed( MouseEvent me )
   {
      mouseDx = me.getX() - x;
      mouseDy = me.getY() - y;
      if ( insideMe( me.getX(), me.getY() ) )
      {
         select();
         repaintCanvas();
      }
   }
   
   public void mouseReleased( MouseEvent me )
   {
      deselect();
      repaintCanvas();
   }
   
   public void mouseEntered( MouseEvent me )
   { 

   }
   
   public void mouseExited( MouseEvent me )
   { 
   }
   
   // MouseMotionListener event handlers
   
   public void mouseDragged( MouseEvent me )
   { 
      if ( isSelected() && myCanvas.getOption() == 0 )
      {
         setXY( me.getX() - mouseDx, me.getY() - mouseDy );
         update( myCanvas.getGraphics() );
         repaintCanvas();
      }
   }
   
   public void mouseMoved( MouseEvent me )
   {
   }
   
   public abstract void update( Graphics g );
   
   public void setColor( Color hue )
   {
      color = hue;
   }
   
   public Color getColor()
   {
      return color;
   }
}   
    
