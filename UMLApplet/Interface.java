import java.io.*;
import java.awt.*;
import java.util.*;

public class Interface extends Type
                       implements Serializable
{
   public Interface( UMLCanvas theCanvas, int xInput, int yInput )
   {
      super( theCanvas, xInput, yInput );
      propsWindow = new InterfacePropsWindow( this );
   }
   
   public void setBoxAngle()
   {
      boxAngle = 4 * Math.PI / 9;
   }
   
   public void setDefaultText()
   {
      Vector dummyName = new Vector();
      dummyName.addElement( new String( "<<interface>>" ) );
      dummyName.addElement( new String( "MyInterface" ) );
      
      setName( dummyName );
      
      Vector dummyFieldVector = new Vector();
      
      setFields( dummyFieldVector );

      Vector dummyMethodVector = new Vector();
      
      setMethods( dummyMethodVector );
   }
   
   public void createPropsWindow()
   {
      propsWindow.showFrame();
   }

   public void updateFromProps()
   {
      Vector name = ( ( InterfacePropsWindow )propsWindow ).getVectorName();
      Vector fields = ( ( InterfacePropsWindow )propsWindow ).getFields();
      Vector methods = ( ( InterfacePropsWindow )propsWindow ).getMethods();
      
      setName( name );

      setFields( fields );

      setMethods( methods );
      
      box = new Box( name, fields, methods, 
                     new Point( getX(), getY() ),
                     boxAngle );
      
      repaintCanvas();
   }   
}