import java.io.*;
import java.awt.*;
import java.util.*;

public class AbstractClass extends Type
                           implements Serializable
{
   public AbstractClass( UMLCanvas theCanvas, int xInput, int yInput )
   {
      super( theCanvas, xInput, yInput );
      propsWindow = new AbstractClassPropsWindow( this );
   }
   
   public void setBoxAngle()
   {
      boxAngle = Math.PI / 2;
   }
      
   public void setDefaultText()
   {
      Vector dummyName = new Vector();
      dummyName.addElement( new String( "<<abstract>>" ) );
      dummyName.addElement( new String( "MyAbstractClass" ) );
      
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
      Vector name = ( ( AbstractClassPropsWindow )propsWindow ).getVectorName();
      Vector fields = ( ( AbstractClassPropsWindow )propsWindow ).getFields();
      Vector methods = ( ( AbstractClassPropsWindow )propsWindow ).getMethods();
      
      setName( name );

      setFields( fields );

      setMethods( methods );
      
      box = new Box( name, fields, methods, 
                     new Point( getX(), getY() ),
                     boxAngle );
      
      repaintCanvas();
   } 
}