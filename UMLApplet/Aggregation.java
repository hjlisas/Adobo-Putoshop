import java.io.*;
import java.awt.*;
import java.util.*;

public class Aggregation extends Relationship
                         implements Serializable
{
   public Aggregation( UMLCanvas  theCanvas, 
                       Type source, 
                       Type destination )
   {
      super( theCanvas, source, destination );
      propsWindow = new UniRelationPropsWindow( this );
   }

   public Aggregation( UMLCanvas theCanvas )
   {
      super( theCanvas );
      propsWindow = new UniRelationPropsWindow( this );
   }   

   public void setLineSegment( LineSegment ls )
   {
      super.setLineSegment( ls );  
      
      Point[] head = new Point[4];
      head[0] = new Point( -10, 10 );
      head[1] = new Point( 0, 0 );
      head[2] = new Point( 10, 10 );
      head[3] = new Point( 0, 20 );    
      
      ls.headShape = head;
      ls.headState = LineSegment.HOLLOW;
   }

}