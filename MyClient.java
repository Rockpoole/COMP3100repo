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
	String input = "";
	String store[] = new String[10];
	String[] storeTemp;
	int core = 0;
	int memory = 0;
	int disk = 0;
	int jobID = 0; // Tells RR which job to schedule


	// -- Handshake -- //
	dout.write(("HELO\n").getBytes());
	dout.flush();

	input=dis.readLine();

	dout.write(("AUTH ryan\n").getBytes());
	dout.flush();

	input=dis.readLine();

	String jobSpecs[];

	// -- Job Scheduler While Loop -- //
	while(true){  // WHILE LOOP START -- RUNS UNTIL BREAK IS IMPLEMENTED

		// -- Find Server Information -- //
		dout.write(("REDY\n").getBytes());
		dout.flush();

		input=dis.readLine();

		if(input.equals("NONE")) break;

		if(input.split(" ")[0].equals("JOBN")){ // Begin Job Scheduling

			jobSpecs=input.split(" ", 7);
			core = Integer.parseInt(String.valueOf(jobSpecs[4]));
			memory = Integer.parseInt(String.valueOf(jobSpecs[5]));
			disk = Integer.parseInt(String.valueOf(jobSpecs[6]));
			jobID = Integer.parseInt(String.valueOf(jobSpecs[2]));

			dout.write(("GETS Avail " + core + " " + memory + " " + disk + "\n").getBytes()); // Get available server data
			dout.flush();

			input=dis.readLine();

				if(input.split(" ")[1].equals("0")) { //If no available servers, get first capable server
					dout.write(("OK\n").getBytes());
					dout.flush();

					input = dis.readLine();

					dout.write(("GETS Capable " + core + " " + memory + " " + disk + "\n").getBytes());
					input = dis.readLine();
				}

				dout.write(("OK\n").getBytes());
				dout.flush();

				int nRecs = Integer.parseInt(String.valueOf(input.charAt(5))); // Find Number of Records (Servers)

				for (int i=0; i<nRecs; i++){
					input = dis.readLine();

					storeTemp = input.split(" ", 9);

			    		if(i==0){ // Save first available server
			    			store = storeTemp;
					}
				}

				dout.write(("OK\n").getBytes());
				dout.flush();

				input=dis.readLine();

					dout.write(("SCHD " + jobSpecs[2] + " " + store[0] + " " + store[1] + "\n").getBytes());
					dout.flush();
					while(!input.contains("OK")) input=dis.readLine(); // Wait until server acknowledges schedule
		}
 	} // END WHILE

		// -- QUIT -- //
	dout.write(("QUIT\n").getBytes());
	dout.close();
	mySocket.close();
}catch(Exception e){System.out.println(e);}
}
}
