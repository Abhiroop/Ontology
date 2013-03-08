import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.*;

public class urllink extends JPanel {
 

   public urllink(final int st,final String n,final String pindex, final int indSpec) {
	   String splitArr[];
		 final String[] url = new String[1000];
		 String[] des = new String[1000];
		 String[] detail = new String[1000];

		 int i=0,m,start,end=0,flag=1;
		 String strLine;
		  String pind="Top/"+pindex;
	 
		 try{
		     // Open the file that is the first
		     // command line parameter



			
		      FileReader fr = new FileReader("D:/DMOZ/content.rdf.u8");
		      BufferedReader br = new BufferedReader(fr);
		     while ((strLine = br.readLine()) != null)
		     {
		    	 
	 			
		    	 if(strLine.contains("\""+pind+"\""))
		    	 {
		    		System.out.println("contains n");
		    		 flag=0;	
		    		 continue;
		    	 }
		    	 
		    	 
		    	 if(strLine.contains("<catid>") && flag==0)
		    	 {
		    		 System.out.println("comes here");
		    		 flag=2;
		    		 continue;
		    	 }
		    	
		    	 if(strLine.contains("ExternalPage about") && flag==2)
	    		 {
		    		 
	    			 splitArr=strLine.split("=");
	    			 splitArr[1]=splitArr[1].substring(1, splitArr[1].length()-2);
	    			
	    			url[i]=splitArr[1];
	    			
	    			
	    			 System.out.println(i+url[i]);
	    			 i++;
	    			continue;
	    			 
	    		 }
		    	 if(strLine.contains("d:Title>") && flag==2)
	    		 {	start=strLine.length();
		    		 for(m=0;m<strLine.length();m++)
		    			{if(strLine.charAt(m)=='>')
		    				start=m;
		    			else if(strLine.charAt(m)=='<' && m>start)
		    		 	{end=m;
		    			break;}
		    			}
	    			 des[i-1]=strLine.substring(start, end);
	    			 
	    			 continue;
	    			
	    			 
	    		 }
		    	 if(strLine.contains("d:Description>") && flag==2)
	    		 {	start=strLine.length();
		    		 for(m=0;m<strLine.length();m++)
		    			{if(strLine.charAt(m)=='>')
		    				start=m;
		    			else if(strLine.charAt(m)=='<' && m>start)
		    		 	{end=m;
		    			break;}
		    			}
	    			 detail[i-1]=strLine.substring(start+1, end);
	    			 
	    			 continue;
	    			
	    			 
	    		 }
		    	if(strLine.contains("<catid>") && flag==2)
		    	 { 
		    		 
		    		 break;
		    		 
		    	 }
		    		
		     
		    }
		     //Close the input stream
		     fr.close();
		       }catch (Exception e)

		       {//Catch exception if any
		     System.err.println("Error: " + e.getMessage());
		     }
		 	
		
	   
	   
      ActionListener listener = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 int q,q2;
        	
        	 q=e.getActionCommand().charAt(0);
        	 q=q-48;
        	 if (e.getActionCommand().charAt(1)>=48 && e.getActionCommand().charAt(1)<=58)
        	 {
        		 
        		 q2=e.getActionCommand().charAt(1)-48;
        		 q=q+q2;
        	 }
        	 System.out.println(q);
        	urls hello = new urls(url[q]);
        	 System.out.println(url[q]);
            System.out.println("Button selected: " + e.getActionCommand());
            }
         };
      
      
      ActionListener listener2 = new ActionListener() {
          public void actionPerformed(ActionEvent e) {
         	expand val = new expand(st, n,pindex,indSpec);
         	JFrame frame = new JFrame(n);
            frame.getContentPane().add(val);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocation(10,10);
            frame.setSize(1200,800);
            frame.setVisible(true);
             System.out.println("Button selected: " + e.getActionCommand());
          }
       };

      setLayout(new GridLayout(i+1,1));
      setSize(400,900);
      ButtonGroup btnGroup = new ButtonGroup();
   //System.out.println(i+urls[i]);
      JToggleButton btn2 = new JToggleButton("Expand the tree");
      btn2.addActionListener(listener2);
      btn2.setForeground(Color.RED);
      btnGroup.add(btn2);
      add(btn2);
      JLabel lab1=new JLabel (" ");
      add(lab1); 
      
      i--;
      while(i!=-1) {
         
         
         JToggleButton btn = new JToggleButton(i+des[i]);
         btn.addActionListener(listener);
         btnGroup.add(btn);
         add(btn);
         JLabel lab2=new JLabel (detail[i]);
        
         
         add(lab2);
         i--;
      }
      
   }

  

  
	  
   
}