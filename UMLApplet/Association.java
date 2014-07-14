import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class Association extends Relationship
                         implements Serializable
{
   public Association( UMLCanvas theCanvas, 
                       Type source, 
                       Type destination )
   {
      super( theCanvas, source, destination );
      propsWindow = new BiRelationPropsWindow( this );
   }
   
   public Association( UMLCanvas theCanvas )
   {
      super( theCanvas );
      propsWindow = new BiRelationPropsWindow( this );
   } 
   
   public void setDefaultData()
   {
      setDefaultDestinationData();
      setDefaultSourceData();
   }
   
   public void setDefaultDestinationData()
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
   
   public void setDefaultSourceData()
   {
      Vector name    = sourceType.getName();
      
      sourceName = new Vector();
      sourceCardinality = new Hashtable();
      
      Iterator methodIterator = sourceType.getMethods().iterator();
      
      while( methodIterator.hasNext() )
      {
         sourceCardinality.put( methodIterator.next(),
                                     new String( "0" ) );
      }
      
      if( sourceType instanceof ConcreteClass )
      {
         sourceName.addElement( name.elementAt( 0 ) );
      }
      else
      {
         sourceName.addElement( name.elementAt( 1 ) );
      }
   }

   
   public void updateData()
   {   
   } 
   
   public void createPropsWindow()
   {
      propsWindow = new BiRelationPropsWindow( this );
      propsWindow.showFrame();
   }
   
   public void setCardinality( Hashtable dC, Hashtable sC )
   {
      setCardinality( dC );
      sourceCardinality = sC;
   }
   
   public void updateFromProps()
   {
      setCardinality( ( ( BiRelationPropsWindow )propsWindow ).getDestinationCardinality(),
                      ( ( BiRelationPropsWindow )propsWindow ).getSourceCardinality() );
      update( myCanvas.getGraphics() );
   }
}