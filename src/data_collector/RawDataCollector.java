package data_collector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

import management.Constants;
import management.MartinExecute;

public class RawDataCollector implements Callable<Object>{
	// ESTE PUERTO DEBERIA SER MANEJADO POR OTRA CLASE
	// ESTA CLASE SOLO RECIBE DATA EN FORMATO Y LA ESCRIBE
	static ServerSocket socket;
	protected final static int port = 19999;
	static Socket connection;
	static StringBuffer process;
	static String TimeStamp;
	
	@Override
	public Object call() throws Exception {
		rawDataCollector();
		return null;
	}
	
	public void rawDataCollector(){
		try{
//			socket = new ServerSocket(port);
//			System.out.println("Socket Server initialized...");
//			connection = socket.accept();
//			System.out.println("Socket connection stablished...");
			
			//** File structure initialization
			Constants.first_record = "00.init_"+Constants.current_time+".txt";
			String output_string 	= createMicroCollectorFile(Constants.first_record);
			File outputFile 		= new File(output_string);
			BufferedWriter bwr		= new BufferedWriter(new FileWriter(outputFile, true));
			PrintWriter out 		= new PrintWriter(bwr);
//			BufferedInputStream is 	= new BufferedInputStream(connection.getInputStream());
//			BufferedReader br 		= new BufferedReader(new InputStreamReader(is));
			
//			while(socket.isBound()){
				String line;
				process = new StringBuffer();
//				int current_mill = 999;
//				while ((line = br.readLine()) != null) {
//					int reception = Constants.currentHourOfDay();
//					int latest_sec = new Integer(parseRawData(line)[Constants.READING_TIME]);
//					int latest_mill = latest_sec%250; // (seg, millis): (1, 1000); (0.75, 750); (0.5, 500); (0.25, 250)
//					if(current_mill > latest_mill){
						bwr.close();
						out.close();
						MartinExecute.runProcessing(output_string);
						String latest_record = latest_sec+".txt";
						Constants.setLastRecord(latest_record);
						output_string = createMicroCollectorFile(latest_record);
						outputFile	  = new File(Constants.getDatePath() + output_string);
						bwr 		  = new BufferedWriter(new FileWriter(outputFile, true));
						out		 	  = new PrintWriter(bwr);
//						}
//					current_mill = latest_mill;
//					out.println(line + "\t" + reception + "\t" + Constants.currentHourOfDay());
//					}
//				}
			out.close();
			
		}catch(Exception e){}
//		try {
//			connection.close();
//			System.out.println("Socket connection closed...");
//		} catch (Exception e){}
	}
	
	public static String[] parseRawData(String raw_data){ 
		return raw_data.split("\t"); 
		}
	
	public static String createMicroCollectorFile(String file_name){
		String path = Constants.getDatePath();
		String file = path + file_name;
		try{
			new File(path).mkdirs();	
			File outputFile = new File(file);
			if(!outputFile.exists() && !outputFile.isDirectory()){
				outputFile.createNewFile();
				}
			}
		catch (Exception e){ file = "failed"+file_name; }
		return file_name;
		}
}
