import java.io.*;  
import java.net.*;  
public class MyClient {  
public static void main(String[] args) {  
try{      
	// -- Socket Creation -- //
	Socket s=new Socket("localhost",50000);  
	BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
	DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
	
	// -- Variables -- //

	String[] storeTemp;
	int jobID = 0; // Tells LRR which job to schedule
	int core = 0;
	int memory = 0;
	int disk = 0;
	int serverID=0;


	// -- Handshake -- //
	dout.write(("HELO\n").getBytes());
	dout.flush(); 
	 
	String str=(String)dis.readLine();
	System.out.println(str);
	  
	dout.write(("AUTH ryan\n").getBytes());
	dout.flush();
	
	str=dis.readLine(); 
	System.out.println(str);
	
	
	// -- Job Scheduler While Loop -- //
	while(!str.contains("NONE")){  // WHILE LOOP START
	
		String jobSpecs[] = new String[10];
	
		dout.write(("REDY\n").getBytes());
		dout.flush();
		
		str=dis.readLine(); 
		System.out.println("Post REDY = " + str);
		jobSpecs=str.split(" ", 7);
		
		core = Integer.parseInt(String.valueOf(jobSpecs[4]));
		memory = Integer.parseInt(String.valueOf(jobSpecs[5]));
		disk = Integer.parseInt(String.valueOf(jobSpecs[6]));
		
		dout.write(("OK\n").getBytes());
		dout.flush();
		str=dis.readLine(); 
		System.out.println("Post OK = " + str);
	
		dout.write(("GETS Capable" + " " + jobSpecs[4] + " " + jobSpecs[5] + " " + jobSpecs[6] + "\n").getBytes()); // Get server data
		dout.flush();
		
		str=dis.readLine(); 
		System.out.println("Post GETS " + str);
	
		dout.write(("OK\n").getBytes());
		dout.flush();
		
		str=dis.readLine(); 
		System.out.println("Post OK 2 " + str);
		

		
		str=dis.readLine(); 
		System.out.println("str print 1 = " + str);
		
		String store[] = new String[10];
		
		store=str.split(" ", 9);
		
		//dout.write(("OK\n").getBytes());
		//dout.flush();
		
		//str=dis.readLine(); 
		//System.out.println("str print 2 = " + str);

		
		dout.write(("OK\n").getBytes());
		dout.flush();
		
		 if(jobSpecs[0].contains("JOBN") && !jobSpecs[0].contains("JCPL")){ //Schedule a job if server sends one (Don't schedule one if server sends a
		
			System.out.println("Schedule Job " + jobID + " to server " + store[0] + " " + serverID );
			dout.write(("SCHD " + jobID + " " + store[0] + " " + serverID + "\n").getBytes());
			dout.flush();
	
			while(!str.contains("OK")) str=dis.readLine(); // Wait until server acknowledges schedule
			
			jobID++; // Increment to next job
		} //END IF
		
		
		
 	} // END WHILE
	
		// -- QUIT -- //
	dout.write(("QUIT\n").getBytes());
	dout.flush();

	dout.close();  
	s.close();  
}catch(Exception e){System.out.println(e);}  
}  
} 
