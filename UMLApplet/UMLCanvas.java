import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


public class UMLCanvas extends Canvas implements MouseListener,
                                                 MouseMotionListener,
                                                 Serializable
{
   private Vector objectVector;
   private int optionSelected;
   private int lastX, lastY;
   private String name;
   
   public UMLCanvas()
   {
   }
   
   public UMLCanvas( int fileNumber )
   {
      name = "Untitled " + fileNumber;
      setBackground( Color.white );
      objectVector = new Vector();
      this.addMouseListener( this );
      this.addMouseMotionListener( this );
   }
   
   public UMLCanvas( String fileName )
   {
      name = fileName;
      setBackground( Color.white );
      objectVector = new Vector();
      this.addMouseListener( this );
      this.addMouseMotionListener( this );
   }
   
   public String getName()
   {
      return name;
   }
   
   public String getFileName()
   {
      return getName() + ".uml";
   }
   
   public void setFileNumber( int fileNumber )
   {
      name = "Untitled " + fileNumber;
      setBackground( Color.white );
      objectVector = new Vector();
      this.addMouseListener( this );
      this.addMouseMotionListener( this );
   }
   
   public void setName( String newName )
   {
      name = newName;
   }   
   
   public void paint( Graphics g )
   {
      Iterator umlIterator = objectVector.iterator();

      while( umlIterator.hasNext() )
      {
         UMLObject s = ( UMLObject )umlIterator.next();
         s.draw( g );
      }
   }
   
   public void addUMLObject( UMLObject object )
   {
      objectVector.addElement( object );
      addMouseListener( object );
      addMouseMotionListener( object );
   }
   
   public void save( String fileName, String newName )
   {
      try
      {
         setName( newName );
         
         ObjectOutputStream oos = new ObjectOutputStream(
                                     new FileOutputStream( 
                                       fileName ) );

         oos.writeObject( objectVector );
         oos.close();
      }
      catch( Exception e )
      {
         e.printStackTrace();
      }
   }
   
   public void open( String filename )
   {
      try
      {
         ObjectInputStream ois = new ObjectInputStream(
                                    new FileInputStream( 
                                       filename ) );

         Vector v = ( Vector )ois.readObject();
         Iterator vIterator = v.iterator();
         
         while( vIterator.hasNext() )
         {
            UMLObject temp = ( UMLObject )vIterator.next();
            temp.setCanvas( this );
            addUMLObject( temp );
         }
         ois.close();
      }
      catch( Exception e )
      {
         e.printStackTrace();
      }
   }
      
   
   public void mouseClicked( MouseEvent me )
   {
      if( me.getClickCount() == 1 )
      {
         /* if single click then check Checkbox option and draw the
            desired object as specified; if option chosen was to draw
            a relation then check click coordinates if it is within
            instances of a Type (do not include relation).  If so,
            then set it as a source and animate a line being drawn
            from that point to the destination point (where the user
            releases the button).  If at release point there is another
            Type instance, then create relation as specified in the
            option. Else, do not draw */
         
         UMLObject newObject;
         
         switch( getOption() )
         {
            case UMLCheckbox.INTERFACE :  
               newObject = new Interface( this, me.getX(), me.getY() );
               processObject( newObject );
               break;
               
            case UMLCheckbox.ABSTRACT_CLASS :
               newObject = new AbstractClass( this, me.getX(), me.getY() );
               processObject( newObject );
               break;
               
            case UMLCheckbox.CONCRETE_CLASS :
               newObject = new ConcreteClass( this, me.getX(), me.getY() );
               processObject( newObject );
               break;
         }
         
         repaint();            
      }
   }
   
   public void processObject( UMLObject thisObject )
   {
      addUMLObject( ( UMLObject )thisObject );
      addMouseListener( thisObject );
      addMouseMotionListener( thisObject );
   }
   
   public void setOptionSelected( int selected )
   {
      optionSelected = selected;
   }
   
   public int getOption()
   {
      return optionSelected;
   }
      
   public void mousePressed( MouseEvent me )
   {
      if(    getOption() == UMLCheckbox.INHERITANCE
          || getOption() == UMLCheckbox.ASSOCIATION
          || getOption() == UMLCheckbox.DEPENDENCY
          || getOption() == UMLCheckbox.COMPOSITION
          || getOption() == UMLCheckbox.AGGREGATION )
      {
         lastX = me.getX();
         lastY = me.getY();
      }
      
      repaint();
   }
   
   public UMLObject getObjectAt( int x, int y )
   {
      boolean notFound = true;
      UMLObject found = null;
      
      Iterator umlObjectIterator = objectVector.iterator();
      while( notFound && umlObjectIterator.hasNext() )
      {
         UMLObject testItem = ( UMLObject )umlObjectIterator.next();
         
         if( testItem.insideMe( x, y ) )
         {
            notFound = false;
            found = testItem;
         }
      }
      
      return found;
   }
   
   public void mouseReleased( MouseEvent me )
   {
      UMLObject newObject;
      UMLObject source      = getObjectAt( lastX, lastY );
      UMLObject destination = getObjectAt( me.getX(), me.getY() );
      if(    source != null && destination != null 
          && source instanceof Type && destination instanceof Type 
          && source != destination )
      {
         switch( getOption() )
         {
            case UMLCheckbox.INHERITANCE :
               newObject = new Inheritance( this, 
                                            ( Type )source,
                                            ( Type )destination );
               processObject( newObject );
               break;
            
            case UMLCheckbox.ASSOCIATION :
               newObject = new Association( this, 
                                            ( Type )source,
                                            ( Type )destination );
               processObject( newObject );
               break;
         
            case UMLCheckbox.DEPENDENCY :
               newObject = new Dependency( this, 
                                           ( Type )source,
                                           ( Type )destination );
               processObject( newObject );
               break;
               
            case UMLCheckbox.COMPOSITION :
               newObject = new Composition( this,
                                            ( Type )source,
                                            ( Type )destination );
               processObject( newObject );
               break;
               
            case UMLCheckbox.AGGREGATION :
               newObject = new Aggregation( this,
                                            ( Type )source,
                                            ( Type )destination );
               processObject( newObject );
               break;
            
         }
      }
   }
   
   public void mouseEntered( MouseEvent me )
   { 
   }
   
   public void mouseExited( MouseEvent me )
   { 
   }
   
   // MouseMotionListener event handlers
   
   public void mouseDragged( MouseEvent me )
   {
      if(    getOption() == UMLCheckbox.INHERITANCE
          || getOption() == UMLCheckbox.ASSOCIATION
          || getOption() == UMLCheckbox.DEPENDENCY
          || getOption() == UMLCheckbox.COMPOSITION
          || getOption() == UMLCheckbox.AGGREGATION )
      {
         // draw temporary line
      }
   }
   
   public void mouseMoved( MouseEvent me )
   {
   }
}