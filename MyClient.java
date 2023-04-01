import java.io.*;  
import java.net.*;  
public class MyClient {  
public static void main(String[] args) {  
try{      
	Socket s=new Socket("localhost",50000);  
	BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
	DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
	
	boolean getsComplete = false; 
	int topCores = 0;
	String store[] = new String[10];
	String[] arr;
	int coreNum = 0;
	int jobID = 0;

	dout.write(("HELO\n").getBytes());
	dout.flush(); 
	 
	String str=(String)dis.readLine();
	System.out.println(str);
	  
	dout.write(("AUTH ryan\n").getBytes());
	dout.flush();
	
	
	str=dis.readLine(); 
	System.out.println(str);
	
	dout.write(("REDY\n").getBytes());
	dout.flush();
	
	
	System.out.println("loop start");
	
	
	str=dis.readLine(); 
	System.out.println(str);
	System.out.println("Start While");
	
	
	dout.write(("GETS All\n").getBytes());
	dout.flush();
	
	
	str=dis.readLine(); 
	System.out.println(str);
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	
	if (getsComplete==false){
	int nRecs = Integer.parseInt(String.valueOf(str.charAt(5)));



	for (int i=0; i<nRecs; i++){
		str = dis.readLine();
		System.out.println(str);
		
        	arr = str.split(" ", 9);
		coreNum = Integer.parseInt(String.valueOf(arr[4]));
    	
    		if(topCores < coreNum){
    			topCores=coreNum;
    			store = arr;
		}
	}
	
	}
	
		getsComplete=true;
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	
	
	dout.write(("SCHD " + jobID + " " + store[0] + " " + store[1] + "\n").getBytes());
	dout.flush();
	
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	
	str=dis.readLine(); 
	System.out.println(str);
	
	//while(str!="NONE"){
		
		jobID++;
	
		dout.write(("REDY\n").getBytes());
		dout.flush();
	
		dout.write(("OK\n").getBytes());
		dout.flush();
	
		dout.write(("SCHD " + jobID + " " + store[0] + " " + store[1] + "\n").getBytes());
		dout.flush();
	
	
		dout.write(("OK\n").getBytes());
		dout.flush();
	
		str=dis.readLine(); 
		System.out.println(str);
	
 	//}
	
	dout.write(("QUIT\n").getBytes());
	dout.flush();

	dout.close();  
	s.close();  
}catch(Exception e){System.out.println(e);}  
}  
} 
