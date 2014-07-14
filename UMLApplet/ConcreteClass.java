import java.io.*;
import java.awt.*;
import java.util.*;

public class ConcreteClass extends Type
                           implements Serializable
{
   public ConcreteClass( UMLCanvas theCanvas, int xInput, int yInput )
   {
      super( theCanvas, xInput, yInput );
      propsWindow = new ClassPropsWindow( this );
   }
   
   public void setDefaultText()
   {
      Vector dummyName = new Vector();
      dummyName.addElement( new String( "MyClass" ) );
      
      setName( dummyName );
      
      Vector dummyFieldVector = new Vector();
      
      setFields( dummyFieldVector );

      Vector dummyMethodVector = new Vector();

      setMethods( dummyMethodVector );
   }

   public void setBoxAngle()
   {
      boxAngle = Math.PI / 2;
   }
   
   public void createPropsWindow()
   {
      propsWindow.showFrame();
   }
   
   public void updateFromProps()
   {
      Vector name = ( ( ClassPropsWindow )propsWindow ).getVectorName();
      Vector fields = ( ( ClassPropsWindow )propsWindow ).getFields();
      Vector methods = ( ( ClassPropsWindow )propsWindow ).getMethods();
      
      setName( name );

      setFields( fields );

      setMethods( methods );
      
      box = new Box( name, fields, methods, 
                     new Point( getX(), getY() ),
                     boxAngle );
      
      repaintCanvas();
   } 
}
