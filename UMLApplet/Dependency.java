import java.io.*;
import java.awt.*;

public class Dependency extends Relationship
                        implements Serializable
{
   public Dependency( UMLCanvas theCanvas, 
                      Type source, 
                      Type destination )
   {
      super( theCanvas, source, destination );
      propsWindow = new UniRelationPropsWindow( this );
   }
   
   public Dependency( UMLCanvas theCanvas )
   {
      super( theCanvas );
      propsWindow = new UniRelationPropsWindow( this );
   }   
   
   public void setLineSegment( LineSegment ls )
   {
      super.setLineSegment( ls ); 
      
      Point[] head = new Point[3];
      head[0] = new Point( -10, 10 );
      head[1] = new Point( 0, 0 );
      head[2] = new Point( 10, 10 );
      
      ls.headShape = head;
      ls.headState = LineSegment.OPEN;
   }
}