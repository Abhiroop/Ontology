
public class pro {

	public static String arr2[],arr1[],m,z;
	
	public static int st=2, i;

public static void main(String[] args)
{
String strLine="Sakshi";
arr2= strLine.split("/");

System.out.println(arr2.length);
arr1=new String[20];


 if(arr2.length<st)
 {
 	
  for(i=0;i<arr2.length;i++)
      	 {
 	
 	 arr1[i]=arr2[i];
 	System.out.println(arr1[i]);
      	 }
  for(i=arr2.length;i<10;i++)
	 {
	  	arr1[i]=" ";
		System.out.println(arr1[i]);
	 }
 		
      
      	 }
  arr2=arr1;
 }
 

}
