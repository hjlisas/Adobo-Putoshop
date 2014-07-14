import java.io.*;
import java.awt.*;

public class Inheritance extends Relationship
                         implements Serializable
{
   public Inheritance( UMLCanvas theCanvas, 
                       Type source, 
                       Type destination )
   {
      super( theCanvas, source, destination );
   }
   
   public Inheritance( UMLCanvas theCanvas )
   {
      super( theCanvas );
   }   
   
   public void setDefaultData()
   {
   }

   public void setLineSegment( LineSegment ls )
   {
      super.setLineSegment( ls );
      
      Point[] head = new Point[3];
      head[0] = new Point( -10, 10 );
      head[1] = new Point( 0, 0 );
      head[2] = new Point( 10, 10 );  
      
      ls.headShape = head;
      ls.headState = LineSegment.HOLLOW;
      

   }
   
   public void createPropsWindow()
   {
   }
   
   public void updateFromProps()
   {
//      setName( propsWindow.getName() );
//      setFields( propsWindow.getFields() );
//      setMethods( propsWindow.getMethods() );
//      update( myCanvas.getGraphics() );
   }
}