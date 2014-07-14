import java.io.*;
import java.awt.*;
import java.util.*;

public class Box implements Serializable
{
   public static final int UPPER_LEFT          = 0;
   public static final int UPPER_RIGHT         = 1;
   public static final int UPPER_DIVIDER_RIGHT = 2;
   public static final int LOWER_DIVIDER_RIGHT = 3;
   public static final int LOWER_RIGHT         = 4;
   public static final int LOWER_LEFT          = 5;
   public static final int LOWER_DIVIDER_LEFT  = 6;
   public static final int UPPER_DIVIDER_LEFT  = 7;
   public static final int CENTER              = 8;
   
   // set static fields for fonts
   public static final Font PLAIN   = new Font( "Arial", Font.PLAIN, 10 );
   public static final Font ITALICS = new Font( "Arial", Font.ITALIC, 10 );
   
   protected Point[] points = new Point[9];
   
   protected double boxAngle;
   protected int boxHeight;
   protected int boxWidth;
   protected int boxPadding = 3;
   
   protected int textHeight;
   protected int longestTextWidth;
   
   protected int nameLineCount, fieldLineCount, methodLineCount;
   
   protected Vector nameText, fieldText, methodText;
   
   protected Color color;
   
   public Box( Vector nameVector, Vector fieldVector, Vector methodVector,
               Point centerPoint, double angle )
   {
      // set text vectors
      nameText   = nameVector;
      fieldText  = fieldVector;
      methodText = methodVector;
      
      // set boxAngle
      boxAngle = angle;
      
      // set center
      points[CENTER] = centerPoint;
   }
   
   public void setCenter( Graphics g, int x, int y )
   {
      setCenter( g, new Point( x, y ) );
   }
   
   public void setCenter( Graphics g, Point center )
   {
      points[CENTER] = center;
      update( g );
   }
   
   public void update( Graphics g )
   {
      // count number of lines per text box
      nameLineCount   = nameText.size();
      fieldLineCount  = fieldText.size();
      methodLineCount = methodText.size();
      
      // get longest line
      g.setFont( PLAIN );
      textHeight = g.getFontMetrics().getHeight();
      longestTextWidth = getLongestTextWidth( g );

      // calculate box height dimensions
      boxWidth  = ( 2 * boxPadding ) + longestTextWidth;
      boxHeight = ( 6 * boxPadding ) 
                  + getPixelHeight( nameLineCount   )
                  + getPixelHeight( fieldLineCount  )
                  + getPixelHeight( methodLineCount );
                  
      // calculate corners
      points[UPPER_LEFT] = getRelativeXOffsetPoint( points[CENTER],
                           - boxHeight / 2,
                           ( Math.PI - boxAngle ) / 2 );
      points[UPPER_LEFT].x -= 2 * ( points[UPPER_LEFT].x - points[CENTER].x );
                                                    
      points[UPPER_DIVIDER_LEFT] = getRelativeXOffsetPoint( points[UPPER_LEFT],
                                   ( 2 * boxPadding ) + getPixelHeight( nameLineCount ),
                                   boxAngle );
      
      points[LOWER_DIVIDER_LEFT] = getRelativeXOffsetPoint( points[UPPER_DIVIDER_LEFT],
                                   ( 2 * boxPadding ) + getPixelHeight( fieldLineCount ),
                                   boxAngle );
                                                            
      points[LOWER_LEFT] = getRelativeXOffsetPoint( points[LOWER_DIVIDER_LEFT],
                           ( 2 * boxPadding ) + getPixelHeight( methodLineCount ),
                           boxAngle );  
      
      points[UPPER_RIGHT] = getRelativeHorizontalOffset( points[UPPER_LEFT], boxWidth );
      
      points[UPPER_DIVIDER_RIGHT] = getRelativeHorizontalOffset( points[UPPER_DIVIDER_LEFT], boxWidth );
      
      points[LOWER_DIVIDER_RIGHT]  = getRelativeHorizontalOffset( points[LOWER_DIVIDER_LEFT], boxWidth );
      
      points[LOWER_RIGHT] = getRelativeHorizontalOffset( points[LOWER_LEFT], boxWidth );
   }

   public void draw( Graphics g )
   {
      // set center point
      setCenter( g, points[CENTER] );
      setColor( Color.black );
      
      drawBox( g );
      drawText( g );
   }

   public int getPixelHeight( int lineCount )
   {
      return lineCount * textHeight;
   }
   
   public Point getRelativeHorizontalOffset( Point pivot, int dx )
   {
      int x  = pivot.x + dx;
      
      return new Point( x, pivot.y );
   }
   
   public static Point getRelativeXOffsetPoint( Point pivot, int dy, double angle )
   {
      int y  = pivot.y + dy;

      int dx = ( int )Math.round( dy / Math.tan( angle ) );
      int x  = pivot.x - dx;
      
      return new Point( x, y );
   }
   
   public int getTextWidth( Graphics g, String text )
   {
      return g.getFontMetrics().stringWidth( text );
   }
   
   public int getLongestTextWidth( Graphics g, Iterator textIterator )
   {
      int longest = 0;

      while( textIterator.hasNext() )
      {
         String textString = ( String )textIterator.next();
                  
         if( (  textString.startsWith(  "<<" ) 
                && textString.endsWith( ">>" ) )
             || textString.startsWith( "#" ) )
         {
            g.setFont( ITALICS );
         }
         else
         {
            g.setFont( PLAIN );
         }
            
         longest = Math.max( longest, getTextWidth( g, textString ) );
      }  

      return longest;
   }
   
   public int getLongestTextWidth( Graphics g )
   {
      int longest = 0;
      
      longest = Math.max( getLongestTextWidth( g, nameText.iterator() ),
                          getLongestTextWidth( g, fieldText.iterator() ) );
                          
      longest = Math.max( longest, 
                          getLongestTextWidth( g, methodText.iterator() ) );
      
      return longest;
   }
   
   public void drawLine( Graphics g, Point p1, Point p2 )
   {
      g.setColor( getColor() );
      g.drawLine( p1.x, p1.y, p2.x, p2.y );
   }
   
   public void drawText( Graphics g,
                         Iterator textIterator, 
                         Point pivot, boolean center )
   {
      int counter = 0;
      Point stringPoint = new Point( pivot.x, pivot.y );
      
      stringPoint.x += boxPadding;
      stringPoint.y += boxPadding;

      while( textIterator.hasNext() )
      {
         String stringBuffer = ( String )textIterator.next();

         stringPoint = getRelativeXOffsetPoint( stringPoint, 
                                                textHeight,
                                                boxAngle );
         // stringPoint self-increments

         if( (  stringBuffer.startsWith( "<<" ) 
             && stringBuffer.endsWith(   ">>" ) )
             || stringBuffer.startsWith( "#"  ) )
         {
            g.setFont( ITALICS );
         }
         else
         {
            g.setFont( PLAIN );
         }
         
         // bypass this stringPoint.x value
         int tempX = stringPoint.x;

         if( center == true )
         {
            stringPoint.x += ( ( boxWidth - getTextWidth( g, stringBuffer ) ) / 2 )
                             - boxPadding;
         }
         
         g.drawString( stringBuffer, 
                       stringPoint.x,
                       stringPoint.y );
         
         // restore old stringPoint.x value              
         if( center == true )
         {
            stringPoint.x = tempX;
         }
      }
   }
   
   public void drawBox( Graphics g )
   {
      for( int i = 0; i <= 7; i++ )
      {
         int j = ( i + 1 ) % 8;
         
         drawLine( g, points[i], points[j] );
      }
      
      drawLine( g, points[UPPER_DIVIDER_LEFT], points[UPPER_DIVIDER_RIGHT] );
      drawLine( g, points[LOWER_DIVIDER_LEFT], points[LOWER_DIVIDER_RIGHT] );
   }   
   
   public void drawText( Graphics g )
   {
      drawText( g, nameText.iterator(), points[UPPER_LEFT], true );
      drawText( g, fieldText.iterator(), points[UPPER_DIVIDER_LEFT], false );
      drawText( g, methodText.iterator(), points[LOWER_DIVIDER_LEFT], false );
   }
   
   public Point getCorner( int index )
   {
      return points[index];
   }
   
   public int getBoxWidth()
   {
      return boxWidth;
   }
   
   public int getBoxHeight()
   {
      return boxHeight;
   }
   
   public void setColor( Color color )
   {
      this.color = color;
   }
   
   public Color getColor()
   {
      return color;
   }
}   
  