import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public abstract class PropsWindow extends JDialog implements 
                                                  ActionListener,
                                                  WindowListener,
                                                  Serializable
{
   public static final int LABEL       = 0;
   public static final int TEXT_FIELD  = 1;
   public static final int TABBED_PANE = 2;
   public static final int BUTTONS     = 3;
   
   public static final int CANCEL      = 0;
   public static final int OK          = 1;
   
   protected UMLObject obj;
   protected JComponent[]  component = new JComponent[4];
   protected JPanel    panel = new JPanel();
   protected Container cp;
   protected int buttonFlag = CANCEL;

   public PropsWindow( UMLObject u )
   {
      super();
      
      cp = getContentPane();
      setResizable( false );

      obj = u;

      loadData();
      createComponents();
      createLayout();
      
      cp.add( panel );
   }
   
   public void createComponents()
   {
      component[LABEL] = new JLabel();
      component[TEXT_FIELD] = new JTextField( 10 );
      component[TABBED_PANE] = new JTabbedPane();
      component[BUTTONS] = new JPanel();
      
      createLabel();
      createTextField();
      createTabbedPane();
      createButtonPanel();
   }
   
   public abstract void createLabel();
   
   public abstract void createTextField();
   
   public abstract void createTabbedPane();
   
   public abstract void loadData();
   
   public JTable createTablePanel()
   {
      JTable tb = new JTable();
      
      return tb;
   }
   
   public void createButtonPanel()
   {
      JButton okButton = new JButton( "OK" );
      JButton cancelButton = new JButton( "Cancel" );
      
      component[BUTTONS].setLayout( new FlowLayout( FlowLayout.CENTER ) );
      component[BUTTONS].add( okButton );
      component[BUTTONS].add( cancelButton );
      
      okButton.addActionListener( this );
      cancelButton.addActionListener( this );
   }
   
   public void createLayout()
   {
      JPanel textFieldSubPanel = new JPanel();
      
      textFieldSubPanel.setLayout( new BoxLayout( textFieldSubPanel,
                                                  BoxLayout.Y_AXIS ) );
      textFieldSubPanel.add( ( JLabel )component[LABEL] );
      textFieldSubPanel.add( ( JTextField )component[TEXT_FIELD] );
      
      panel.setLayout( new BorderLayout() );
      panel.add( "North", textFieldSubPanel );
      panel.add( "Center", ( JComponent )component[TABBED_PANE] );
      panel.add( "South", ( JPanel )component[BUTTONS] );
   }
   
   public void showFrame()
   {
      update();
      setVisible( true );
   }
   
   public void update()
   {
   }
   
   public void hideFrame()
   {
      dispose();
   }
   
   // implementation for WindowListener interface methods
   
   public void windowActivated( WindowEvent we )
   {
   }

   public void windowClosed( WindowEvent we )
   {
   }

   public void windowOpened( WindowEvent we )
   {
   }

   public void windowDeiconified( WindowEvent we )
   {
   }

   public void windowDeactivated( WindowEvent we )
   { 
   }

   public void windowIconified( WindowEvent we )
   {
   }
   
   public void windowClosing( WindowEvent we )
   {
      dispose();
   }

   public void actionPerformed( ActionEvent ae )
   {
      String command = ae.getActionCommand();
      
      if( command.equals( "OK" ) )
      {
         transmitData();
      }
      
      dispose();
   }
   
   public void transmitData()
   {
      obj.updateFromProps();
   }   
}
