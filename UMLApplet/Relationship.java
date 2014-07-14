import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public abstract class Relationship extends UMLObject
                                   implements MouseMotionListener,
                                              Serializable
                                          
{
   protected Vector      destinationName        = new Vector();
   protected Hashtable   destinationCardinality = new Hashtable();
   
   protected Vector      sourceName             = new Vector();
   protected Hashtable   sourceCardinality      = new Hashtable();
   
   
   protected Type        sourceType;
   protected Type        destinationType;

   protected Point       sourceAttachPoint;
   protected Point       destinationAttachPoint;
   
   protected LineSegment lineSegment;
   
   protected Point[]     head;
   protected Point[]     tail;
   
   protected String      name;
   
   public Relationship( UMLCanvas theCanvas )
   {
      super( theCanvas );
      lineSegment = new LineSegment();
   }
   
   public Relationship( UMLCanvas theCanvas, 
                        Type source, 
                        Type destination )
   {
      super( theCanvas );
      sourceType = source;
      destinationType = destination;
      
      int x = ( int )Math.round( ( sourceType.getX() + destinationType.getX() ) / 2 );
      int y = ( int )Math.round( ( sourceType.getY() + destinationType.getY() ) / 2 );
      
      setXY( x, y );
      
      setDefaultData();
   }
   
   public void setLineSegment( LineSegment ls )
   {
      lineSegment = ls;
   }
   
   public String getName()
   {
      return name;
   }
   
   public void draw( Graphics g )
   {
      setLineSegment( new LineSegment( sourceType.x, sourceType.y,
                                       destinationType.x, destinationType.y ) );
      computeMyLineSegment();
      g.setColor( getColor() );
      lineSegment.draw( g );
   }
   
   public Point getSourceCenter()
   {
      return new Point( sourceType.x, sourceType.y );
   }
   
   public Point getDestinationCenter()
   {
      return new Point( destinationType.x, destinationType.y );
   }
   
   public void computeMyLineSegment()
   {
      Point sc = getSourceCenter();
      Point dc = getDestinationCenter();
      
      Point tailPoint = destinationType.getAttachPoint( sc.x, sc.y );
      Point headPoint = sourceType.getAttachPoint( dc.x, dc.y );
      
      lineSegment.setTailXY( tailPoint.x, tailPoint.y );
      lineSegment.setHeadXY( headPoint.x, headPoint.y );
   }
   
   public boolean insideMe( int xInput, int yInput )
   {
      return lineSegment.insideMe( xInput, yInput );
   }
   
   public void update( Graphics g )
   {
      computeMyLineSegment();
   }
   
   public void select()
   {
      super.select();
      sourceType.select();
      destinationType.select();
   }
   
   public void mouseDragged( MouseEvent me )
   { 
      super.mouseDragged( me );
      
      if ( myCanvas.getOption() == 0
           && ( sourceType.isSelected() 
                || destinationType.isSelected() ) )
      {
         update( myCanvas.getGraphics() );
         repaintCanvas();
      }
   }
   
   public Vector getDestinationName()
   {
      return destinationName;
   }
   
   public Vector getSourceName()
   {
      return sourceName;
   }
   
   public Hashtable getDestinationCardinality()
   {
      return destinationCardinality;
   }
   
   public Hashtable getSourceCardinality()
   {
      return sourceCardinality;
   }
      
   public void createPropsWindow()
   {
      setDefaultData();
      propsWindow = new UniRelationPropsWindow( this );
      propsWindow.showFrame();
   }

   public void showPropsWindow()
   {
      updateData();
      propsWindow.setVisible( true );
   }
   
   public void setCardinality( Hashtable dC )
   {
      destinationCardinality = dC;
   }
   
   public void updateFromProps()
   {
      setCardinality( ( ( UniRelationPropsWindow )propsWindow ).getDestinationCardinality() );
      update( myCanvas.getGraphics() );
   }
   
   public void setDefaultData()
   {
      Vector name    = destinationType.getName();
      
      destinationName = new Vector();
      destinationCardinality = new Hashtable();
      
      Iterator methodIterator = destinationType.getMethods().iterator();
      
      while( methodIterator.hasNext() )
      {
         destinationCardinality.put( methodIterator.next(),
                                     new String( "0" ) );
      }
      
      if( destinationType instanceof ConcreteClass )
      {
         destinationName.addElement( name.elementAt( 0 ) );
      }
      else
      {
         destinationName.addElement( name.elementAt( 1 ) );
      }
   }
   
   public void updateData()
   {
      Vector name    = destinationType.getName();
      
      destinationName = new Vector();

      Iterator methodIterator = destinationType.getMethods().iterator();

      while( methodIterator.hasNext() )
      {
         Object temp = methodIterator.next();
         
         if( destinationCardinality.get( temp ) == null )
         {
            destinationCardinality.put( temp, new String( "0" ) );
         }
      }
      
      if( destinationType instanceof ConcreteClass )
      {
         destinationName.addElement( name.elementAt( 0 ) );
      }
      else
      {
         destinationName.addElement( name.elementAt( 1 ) );
      }
   }
      
   
}
