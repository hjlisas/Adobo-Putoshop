import java.io.*;
import java.awt.*;
import java.util.*;

public abstract class Type extends UMLObject 
                           implements Serializable
{
   protected Vector name    = new Vector();
   protected Vector fields  = new Vector();
   protected Vector methods = new Vector();

   protected double boxAngle;
   
   protected Box box;
   
   public Type( UMLCanvas theCanvas )
   {
      super( theCanvas );
   }
   
   public Type( UMLCanvas theCanvas, int xInput, int yInput )
   {
      super( theCanvas );
      
      setXY( xInput, yInput );
      Point center = new Point( getX(), getY() );
            
      setDefaultText();
      setBoxAngle();
      setColor( Color.black );   
         
      box = new Box( name, fields, methods, 
                     center, boxAngle );
   }
   
   public void draw( Graphics g )
   {
      box.setColor( getColor() );
      box.draw( g );
   }
   
   public Point getAttachPoint( int x, int y )
   {
      if( insideMe( x, y ) )
      {
         return( new Point( getX(), getY() ) );
      }

      LineSegment l = new LineSegment( new Point( x, y ),
                                       new Point( getX(), getY() ) );
      Point ip;
      int i, j;

      //determine which edge corner intersects with 
      //the line ( XY - center ) and return the intersection point
      for( i = 0; i < 4; i++ )
      {
         j = ( i + 1 ) % 4;
         ip = l.getIntersectPoint( new LineSegment( box.getCorner( toCornerIndex( i ) ), 
                                                    box.getCorner( toCornerIndex( j ) ) ) );
         if( ip != null )
         {
            return( ip );
         }
      }

      //return the center point if no edge intersects with the line
      return( new Point( getX(), getY() ) ); 
   }
                
   
   // convenience methods
   public int toCornerIndex( int ordinalIndex )
   {
      int cornerIndex = 0;
      
      switch( ordinalIndex )
      {
         case 1 : cornerIndex = 1;
                  break;
         case 2 : cornerIndex = 4;
                  break;
         case 3 : cornerIndex = 5;
                  break;
      }
      
      return cornerIndex;
   }
   
   public Vector getName()
   {
      return name;  
   }
   
   public void setName( Vector inputName )
   {
      name = inputName;
   }
   
   public Vector getFields()
   {
      return fields;
   }

   public void setFields( Vector inputFields )
   {
      fields = inputFields;
   }

   public Vector getMethods()
   {
      return methods;
   }
   
   public void setMethods( Vector inputMethods )
   {
      methods = inputMethods;
   }
   
   public void update( Graphics g )
   {
      box.setColor( getColor() );
      box.setCenter( g, getX(), getY() );
   }
   
   public abstract void setDefaultText();
   public abstract void setBoxAngle();
   
   public boolean insideMe( int xInput, int yInput )
   {
      int UR = getBitPattern( xInput, yInput,
                         box.getCorner( Box.UPPER_RIGHT ) ),
          UL = getBitPattern( xInput, yInput,
                         box.getCorner( Box.UPPER_RIGHT ) ),
          LL = getBitPattern( xInput, yInput,
                         box.getCorner( Box.LOWER_LEFT ) ),
          LR = getBitPattern( xInput, yInput,
                         box.getCorner( Box.LOWER_RIGHT ) );
          
      if(   UR == 1 && ( UL == 3 || UL == 1 ) 
         && LL == 2 && ( LR == 0 || LR == 2 ) )
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   public int getBitPattern( int x, int y, Point corner )
   {
      int dxSign = sgn( x - corner.x );
      int dySign = sgn( y - corner.y );
      
      int bitPattern = ( dxSign * 2 ) + dySign;

      return bitPattern;
   } 
   
   public int sgn( double testValue )
   {
      int result = 1; 
      
      if( testValue >= 0 )
      {
         result = 1;
      }
      else if( testValue < 0 )
      {
         result = 0;
      }
      
      return result;
   }
}