import java.io.BufferedReader;
import java.io.FileReader;

import prefuse.data.Node;


public class parsing {
	
	
	parsing(String n)
	{
	 try{
	     // Open the file that is the first
	     // command line parameter



		 String splitArr[];
		 String urls[][];
		 int i=0,flag=1;
		 String strLine;
		  n="Top/"+n;
	      FileReader fr = new FileReader("G:/sakshi/sakshi/downloads/content.rdf.u8");
	      BufferedReader br = new BufferedReader(fr);
	     while ((strLine = br.readLine()) != null)
	     {
	    	 
 			
	    	 if(strLine.contains("\""+n+"\""))
	    	 {
	    		
	    		 flag=0;	    		 
	    	 }
	    	 
	    	 
	    	 if(strLine.contains("<catid>") && flag==0)
	    	 {
	    		
	    		 flag=2;
	    		 continue;
	    	 }
	    	
	    	 if(strLine.contains("ExternalPage about") && flag==2)
    		 {
	    		 
    			 splitArr=strLine.split("=");
    			 splitArr[1]=splitArr[1].substring(1, splitArr[1].length()-2);
    			 System.out.println(splitArr[1]);
    			
    			 
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
	 	
	}

public static void main(String arg[])
{
	
	parsing frame=new parsing("Arts/Animation");
	
}

}