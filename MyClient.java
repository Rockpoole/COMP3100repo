import java.io.*;  
import java.net.*;  
public class MyClient {  
public static void main(String[] args) {  
try{      
	// -- Socket Creation -- //
	Socket mySocket=new Socket("localhost",50000);  
	BufferedReader dis = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	DataOutputStream dout=new DataOutputStream(mySocket.getOutputStream()); 
	
	// -- Variables -- //
	String store[] = new String[10];
	String biggest[] = new String[10];
	String[] storeTemp;
	int core = 0;
	int memory = 0;
	int disk = 0;
	int jobID = 0; // Tells RR which job to schedule
	boolean unavailable = false;
	boolean first = true;


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
	while(!str.equals("NONE")){  // WHILE LOOP START
	
	String jobSpecs[] = new String[10];
	
	if(unavailable==true){
//		dout.write(("LSTQ GQ #\n").getBytes());
//		dout.flush();
//		dout.write(("DEQJ GQ 0\n").getBytes());
//		dout.flush();
		unavailable=false;
	}
	
	// -- Find Server Information -- //
	dout.write(("REDY\n").getBytes());
	dout.flush();
	
	str=dis.readLine(); 
	System.out.println("First Ready = " + str);
	if(str.equals("NONE")) break;
	
	if(str.split(" ")[0].equals("JCPL")){
	dout.write(("REDY\n").getBytes());
	dout.flush();
	str=dis.readLine(); 
	System.out.println("JCPL = " + str);
	}
	
	if(!str.split(" ")[0].equals("JCPL")){
	jobSpecs=str.split(" ", 7);
	core = Integer.parseInt(String.valueOf(jobSpecs[4]));
	memory = Integer.parseInt(String.valueOf(jobSpecs[5]));
	disk = Integer.parseInt(String.valueOf(jobSpecs[6]));
	

	
	dout.write(("GETS Avail " + core + " " + memory + " " + disk + "\n").getBytes()); // Get server data
	dout.flush();
	
			System.out.println("After GETS = " + str);
	
	str=dis.readLine(); 
	System.out.println("String split = " + str);

	dout.write(("OK\n").getBytes());
	dout.flush();
	
	
		if(str.split(" ")[1].equals("0")) {
		unavailable = true;
		}
		
	if(str.split(" ")[1].equals("10000")) {
		unavailable = true;
		dout.write(("ENQJ GQ\n").getBytes());
		dout.flush();
		dout.write(("DEQJ GQ 0\n").getBytes());
		dout.flush();
		dout.write(("REDY\n").getBytes());
		dout.flush();
		str = dis.readLine();
		dout.write(("GETS Capable " + core + " " + memory + " " + disk + "\n").getBytes()); // Get server data
		dout.flush();
		str=dis.readLine(); 
	System.out.println("String split capable = " + str);

	dout.write(("OK\n").getBytes());
	dout.flush();
		}
		
		

		
		int nRecs = Integer.parseInt(String.valueOf(str.charAt(5))); // Find Number of Records (Servers)
					System.out.println("nRecs = " + nRecs);
					if(nRecs>0){
		for (int i=0; i<nRecs; i++){
			str = dis.readLine();
			System.out.println(str);
			
			storeTemp = str.split(" ", 9);
	    	
	    		if(i==0){ // Save first available server
	    			store = storeTemp;
			}
			if(i==(nRecs-1) & first==true) biggest=storeTemp;
		}
	}
		if(unavailable==true){
		System.out.println("NA TRUE");
		 str=dis.readLine(); 
		System.out.println("na = " + str);
		dout.write(("OK\n").getBytes());
		dout.flush();
		dout.write(("REDY\n").getBytes());
		dout.flush();
		
		str=dis.readLine(); 
		System.out.println("str print 1 = " + str);
		

		}

		dout.write(("OK\n").getBytes());
		dout.flush();


		dout.write(("REDY\n").getBytes());
		dout.flush();
		
		str=dis.readLine(); 
		System.out.println("str print 1 = " + str);
		
		dout.write(("OK\n").getBytes());
		dout.flush();
		
		str=dis.readLine(); 
		System.out.println("str print 2 = " + str);


		if(str.split(" ")[0].equals("JOBN") && unavailable==false){ //Schedule a job if server sends one (Don't schedule one if server sends a JCPL)
			System.out.println("Schedule Job " + jobID + " to server " + store[0] + " " + store[1]);
			dout.write(("SCHD " + jobID + " " + store[0] + " " + store[1] + "\n").getBytes());
			dout.flush();
	
			while(!str.contains("OK")) str=dis.readLine(); // Wait until server acknowledges schedule
			
			jobID++; // Increment to next job
		} //END IF
		if(str.split(" ")[0].equals("JOBN") && unavailable==true){ //Schedule a job if server sends one (Don't schedule one if server sends a JCPL)
			System.out.println("Schedule Job " + jobID + " to server " + biggest[0] + " " + biggest[1]);
			dout.write(("SCHD " + jobID + " " + biggest[0] + " " + biggest[1] + "\n").getBytes());
			dout.flush();
	
			while(!str.contains("OK")) str=dis.readLine(); // Wait until server acknowledges schedule
			
			jobID++; // Increment to next job
		} //END IF
		
	if(unavailable == true){
		
//		str=dis.readLine(); 
//		System.out.println("post queue = " + str);
		
//		dout.write(("OK\n").getBytes());
//		dout.flush();
	}
	}
	first = false;
 	} // END WHILE
	
		// -- QUIT -- //
	dout.write(("QUIT\n").getBytes());
	dout.flush();

	dout.close();  
	mySocket.close();  
}catch(Exception e){System.out.println(e);}  
}  
}
