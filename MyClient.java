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
	String jobs[] = new String[10];
	String[] storeTemp;
	int numServers = 0;
	int coreNum = 0;
	int jobID = 0;
	int serverID=0;
	String[] strcheck;

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
	
	
	
	str=dis.readLine(); 
	System.out.println(str);
	
	
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
		
        	storeTemp = str.split(" ", 9);
		coreNum = Integer.parseInt(String.valueOf(storeTemp[4]));
    	
    		if(topCores < coreNum){
    			topCores=coreNum;
    			store = storeTemp;
    			numServers = Integer.parseInt(String.valueOf(store[1]));
		}
	}
	
	
	}
	System.out.println("No. Servers = " + numServers);
	getsComplete=true;
	
//	dout.write(("OK\n").getBytes());
//	dout.flush();
	
	
//	dout.write(("SCHD " + jobID + " " + store[0] + " " + serverID + "\n").getBytes());
//	dout.flush();
	
	
//	dout.write(("OK\n").getBytes());
//	dout.flush();
	
//	str=dis.readLine(); 
//	System.out.println(str);
	int i=0;
	//while(str!="NONE"){
	boolean ready=true;
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	String check = "Hello";
	boolean none = false;
	boolean scheduled=false;
	//while(none==false){
	while(i<10){
		if(serverID>numServers) serverID=0;
		
//		System.out.println("str =" + str);
//		strcheck = str.split(" ", 10);
//		check = strcheck[0];
//		System.out.println(ready);

		if(str.contains("NONE")) none =true;
		
		str=dis.readLine(); 
		System.out.println("str print = " + str);
		//jobs = str.split(" ",9);
		//if(jobs[1]==jobID) scheduled = true;
		//System.out.println("jobs[1] = " + jobs[1]);
		
		dout.write(("OK\n").getBytes());
		dout.flush();
		
		dout.write(("REDY\n").getBytes());
		dout.flush();
		
		String a = String.valueOf(jobID);
		
		//if(str.contains("JOBN") && str.contains(a)){
		if(str.contains("JOBN")){
		System.out.println("SCHD" + jobID + store[0] + store[1] );
		dout.write(("SCHD " + jobID + " " + store[0] + " " + serverID + "\n").getBytes());
		dout.flush();
	
		scheduled=true;
		jobID++;
		serverID++;
		i++;
		}
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
