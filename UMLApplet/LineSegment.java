import java.io.*;
import java.awt.*;

/**
 * 
 */
public class LineSegment implements Serializable
{
   public static final int HOLLOW = 0;
   public static final int SOLID  = 1;
   public static final int OPEN   = 2;

   public static final int FUZZ_FACTOR = 10;
   
   public int x1, y1, x2, y2;
   public Point[] headShape, tailShape;
   public int[] headX, headY, tailX, tailY;
   public int headState, tailState;
   
   public LineSegment()
   {
      // temporary constructor
   }
   
   public LineSegment( int x1, int y1, int x2, int y2, 
                       Point[] head, int hs, 
                       Point[] tail, int ts )
   {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      
      if( head != null )
      {
         headShape = head;
         headX = new int[ headShape.length ];
         headY = new int[ headShape.length ];
         headState = hs;
      }
      
      if( tail != null )
      {
         tailShape = tail;
         tailX = new int[ tailShape.length ];
         tailY = new int[ tailShape.length ];
         tailState = ts;
      }
   }

   public LineSegment( Point p1, Point p2 , 
                       Point[] head, int hs, 
                       Point[] tail, int ts )
   {
      this( p1.x, p1.y, p2.x, p2.y, head, hs, tail, ts );
   }

   public LineSegment( Point p1, Point p2, Point[] head, int hs )
   {
      this( p1.x, p1.y, p2.x, p2.y, head, hs, null, 0 );
   }

   public LineSegment( int x1, int y1, int x2, int y2, Point[] head, int hs )
   {
      this( x1, y1, x2, y2, head, hs, null, 0 );
   }

   public LineSegment( int x1, int y1, int x2, int y2 )
   {
      this( x1, y1, x2, y2, null, 0, null, 0 );
   }
   
   public LineSegment( Point p1, Point p2 )
   {
      this( p1.x, p1.y, p2.x, p2.y, null, 0, null, 0 );
   }

   public void setHeadXY( int x, int y)
   {
      x1 = x;
      y1 = y;
   }

   public void setTailXY( int x, int y)
   {
      x2 = x;
      y2 = y;
   }

   public double computeAngle( int x1, int y1, int x2, int y2 )
   {
      /*
         angle = arctan( y / x )
      */
    
      double dx = x2 - x1;
      double dy = y2 - y1;
      
      // Math.atan2 is called atan2( y, x );
      //   but in this case, 0 degrees is 90 degrees
      //   so we switch y and x
      return Math.atan2( -dx, -dy );
   }
   
   public Point[] translateShape( Point[] source, int dx, int dy )
   {
      Point[] newShape = new Point[ source.length ];                            
      
      for( int i = 0; i < source.length; i++ )
      {
         newShape[i] = new Point( source[i].x + dx, source[i].y + dy );
      }
      
      return newShape;
   }
   
   public Point[] rotateShape( Point[] source, double radAngle )
   {
      Point[] newShape = new Point[ source.length ];
      
      for( int i = 0; i < source.length; i++ )
      {
         newShape[i] = rotate( source[i], radAngle );
      }
      
      return newShape;
   }

   //rotate the Point source in relation to (0,0)
   public Point rotate( Point p, double radAngle )
   {
      double cosA = Math.cos( radAngle );
      double sinA = Math.sin( radAngle );

      return new Point( (int)Math.round( p.x*cosA + p.y*sinA ),
                        (int)Math.round( p.y*cosA - p.x*sinA ) );
   }
   
   public double getSlope()
   {
/*      
      y1 = mx1 + b  -> b = y1 - mx1
      y2 = mx2 + b  -> b = y2 - mx2
      y1 - mx1 = y2 - mx2
      y1 - y2 = mx1 - mx2
*/
      int dx = x2 - x1;
      int dy = y2 - y1;
      
      if ( dx != 0 )
      {
         return (double)dy / (double)dx;
      }
      else
      {
         if ( dy > 0 )
         {
            return Double.POSITIVE_INFINITY;
         }
         else if ( dy < 0 )
         {
            return Double.NEGATIVE_INFINITY;
         }
         else // if ( dy == 0 )
         {
            return 0;
         }
      }
   }
   
   public double getYIntercept()
   {
      double m = this.getSlope();
      if ( !Double.isInfinite( m ) )
      {
         return y1 - m*x1;
      }
      else
      {
         // the y-intercept is infinite too
         return m;
      }
   }
   
   public Point getIntersectPoint( LineSegment other )
   {
      
      // get intersection of their infinite lines
      Point p = getLineIntersectPoint( other );
      
      if ( p != null )
      {
         boolean insideThis = insideMe( p.x, p.y );
      
         boolean insideOther = other.insideMe( p.x, p.y );

/*         System.out.println( "LineSegment.getLineIntersectPoint of " 
                             + this + " and " + other + " = " + p 
                             + ". insideThis = " + insideThis
                             + ". insideOther = " + insideOther );
*/
         // point must exist and must be inside both line segments
         if ( ( p != null ) 
              && insideThis
              && insideOther )
         {
/*            System.out.println( "LineSegment.getIntersectPoint of " 
                                + this + " and " + other + " = " + p );
*/            return p;
         }
         else
         {
/*            System.out.println( "LineSegment.getIntersectPoint of " 
                                + this + " and " + other + " not found " );
*/            return null;
         }
      }
      else
      {
         return null;
      }
   }
   
   /**
    * Returns the intersection point of the two line segments
    * if they were extended to infinity
    */
   public Point getLineIntersectPoint( LineSegment other )
   {
/*
      y = m1x + b1
      y = m2x + b2
         
      m2x + b2 = m1x + b1
      m2x - m1x = b1 - b2
      (m2 - m1)x = (b1 - b2)
*/
      Point result = null;
      
      double m1 = this.getSlope();
      double b1 = this.getYIntercept();
      double m2 = other.getSlope();
      double b2 = other.getYIntercept();
      
      if ( (m1 == m2) 
           || ( Double.isInfinite( m1 ) && Double.isInfinite( m2 ) ) )
      {
         return null;
      }
      else if ( Double.isInfinite( m1 ) )
      {
         double x = this.x1;
         double y = m2*x + b2;
         return new Point( (int)Math.round( x ), (int)Math.round( y ) );
      }
      else if ( Double.isInfinite( m2 ) )
      {
         double x = other.x1;
         double y = m1*x + b1;
         return new Point( (int)Math.round( x ), (int)Math.round( y ) );
      }
      else // if ( !Double.isInfinite( m1 ) && !Double.isInfinite( m2 ) )
      {
         double x = (b1 - b2) / (m2 - m1);
         double y = m1*x + b1;   
      
         return new Point( (int)Math.round( x ), (int)Math.round( y ) );
      }
   }
   
   public boolean insideMe( int x, int y )
   {
      if ( ( x >= Math.min( x1, x2 ) ) 
           && ( x <= Math.max( x1, x2 ) ) 
           && ( y >= Math.min( y1, y2 ) )
           && ( y <= Math.max( y1, y2 ) ) )
      {
         int dx = x - x1;
         int dy = y - y1;
         
         double slope = this.getSlope();
         
         if ( Double.isInfinite( slope ) 
              || ( ( dx == 0 ) && ( dy == 0 ) ) )
         {
            // if x or y is the same
            // then it must be a hit
            return true;
         }
         else if ( slope < 1.0 )
         {
            // compare y's (for low slopes)
            int predictedDY = (int)Math.round( dx * this.getSlope() );
            boolean inside = ( Math.abs( dy - predictedDY ) < FUZZ_FACTOR );
            if ( !inside )
            {
/*               System.out.println( "[" + x + "," + y + "] is outside " + this
                                   + " because predictedDY is wrong" );
*/            }
            return inside;
         }
         else
         {
            int predictedDX = (int)Math.round( dy / this.getSlope() );
            boolean inside = ( Math.abs( dx - predictedDX ) < FUZZ_FACTOR );
            if ( !inside )
            {
/*               System.out.println( "[" + x + "," + y + "] is outside " + this
                                   + " because predictedDX is wrong" );
*/            }
            return inside;   
         }
      }
      else
      {
/*         System.out.println( "[" + x + "," + y + "] is outside " + this
                            + " because it's out of range." );
*/         return false;
      }
   }
   
   public Point getMidPoint()
   {
      return new Point( (x1 + x2) / 2, (y1 + y2) / 2 );
   }
   
   public String toString()
   {
      return "(" + x1 + "," + y1 + ")-(" + x2 + "," + y2 + ")";
   }

   public void draw( Graphics g )
   {
      g.drawLine( x1, y1, x2, y2 );
      if( headShape != null )
      {
         drawArrow( g, x1, y1, x2, y2, headShape, headState );
      }

      if( tailShape != null )
      {
         drawArrow( g, x2, y2, x1, y1, tailShape, tailState );
      }
   }
   
   protected void drawArrow( Graphics g,
                             int x1, int y1, int x2, int y2,
                             Point[] arrowShape, int arrowState )
   {
         double angle = computeAngle( x1, y1, x2, y2 );
         Point[] rotatedShape = rotateShape( arrowShape, angle );
         Point[] newShape = translateShape( rotatedShape, x2, y2 );
         
         int[] headX = new int[ newShape.length ];
         int[] headY = new int[ newShape.length ];
         
         for ( int i = 0; i < newShape.length; i++ )
         {
            headX[i] = newShape[i].x;
            headY[i] = newShape[i].y;
         }
         
         switch( arrowState )
         {
            case LineSegment.HOLLOW:   Color fore = g.getColor();
                                       g.setColor( Color.white );
                                       g.fillPolygon( headX, headY, newShape.length );
                                       g.setColor( fore );
                                       g.drawPolygon( headX, headY, newShape.length );
                                       break;
                                       
            case LineSegment.SOLID:    g.setColor( Color.black );
                                       g.fillPolygon( headX, headY, newShape.length );
                                       break;
                                       
            case LineSegment.OPEN:     g.setColor( Color.black );
                                       for( int i = 0; i < newShape.length-1; i++ )
                                       {
                                          g.drawLine( headX[i], headY[i], headX[i+1], headY[i+1] );
                                       }
         }
   }
}
