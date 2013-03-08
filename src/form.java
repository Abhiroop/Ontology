import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
 
class form extends JFrame implements ActionListener
{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JButton SUBMIT;
 JPanel panel;
 JLabel label1;
 final JTextField  text1;
  form()
  {
  label1 = new JLabel();
  label1.setText("ENTER WORD / WORDS");
  text1 = new JTextField(100);

 

 
  SUBMIT=new JButton("SUBMIT");
  
  panel=new JPanel(new GridLayout(3,1));
  panel.add(label1);
  panel.add(text1);
 
  panel.add(SUBMIT);
  add(panel,BorderLayout.CENTER);
  SUBMIT.addActionListener(this);
  setTitle("Enter your search");
  }
 public void actionPerformed(ActionEvent ae)
  {
  String value1=text1.getText();

  
  formdisplay hello = new formdisplay(value1);

      JFrame frame = new JFrame(value1);
      frame.getContentPane().add(hello);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocation(10,10);
      frame.setSize(1200,800);
      frame.setVisible(true);


  }
  
 


 
  public static void main(String arg[])
  {
  try
  {
form frame=new form();
  frame.setSize(300,100);
  frame.setVisible(true);
  
  

    
  }
  catch(Exception e)
  {JOptionPane.showMessageDialog(null, e.getMessage());}
  }
}