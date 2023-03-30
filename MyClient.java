import java.io.*;  
import java.net.*;  
public class MyClient {  
public static void main(String[] args) {  
try{      
	Socket s=new Socket("localhost",50000);  
	BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
	DataOutputStream dout=new DataOutputStream(s.getOutputStream());  

	dout.write(("HELO\n").getBytes());
	dout.flush(); 
	 
	String str=(String)dis.readLine(); 
	System.out.println(str);
	  
	dout.write(("AUTH ryan\n").getBytes());
	dout.flush();  
	
	while(str!="NONE"){
	str=dis.readLine(); 
	System.out.println(str);
	dout.write(("REDY\n").getBytes());
	
	dout.flush();
	str=dis.readLine(); 
	System.out.println(str);
	System.out.println("Start While");
	
	String[] jobs = str.split(" ", 10);
	
	dout.write(("GETS All\n").getBytes());
	dout.flush();
	
	str=dis.readLine(); 
	System.out.println(str);
	
	int nRecs = Integer.parseInt(String.valueOf(str.charAt(5)));
	int topCores = 0;
	String store[] = new String[10];
	
	dout.write(("OK\n").getBytes());
	dout.flush();

	for (int i=0; i<nRecs; i++){
		str = dis.readLine();
		System.out.println(str);
		
        	String[] arr = str.split(" ", 9);
		int coreNum = Integer.parseInt(String.valueOf(arr[4]));
    	
    		if(topCores < coreNum){
    			topCores=coreNum;
    			store = arr;
		}
	}
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	
	
	dout.write(("SCHD " + jobs[2] + " " + store[0] + " " + store[1] + "\n").getBytes());
	dout.flush();
	
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	
}
	
	dout.write(("QUIT\n").getBytes());
	dout.flush();
	dout.close();  
	s.close();  
}catch(Exception e){System.out.println(e);}  
}  
} 
