package data_collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import management.Constants;
import modelos_predictivos.*;

// This class will pack and analyze events per time window. Later this processing will be separated into modules
public class RawDataProcessor implements Callable<Object>{
	public ArrayList<String> existing_data	= new ArrayList<String>();
	public String data_path					= "/Users/jorge/Documents/workspace/martinEventsDetection/datos";
	public String file_to_analize;
	
	public RawDataProcessor(String file){
		this.file_to_analize = file;
	}
	
	@Override
	public Object call() throws Exception {
		rawDataProcessor();
		return null;
	}
	
	public void rawDataProcessor() throws Exception{
		
//		Zamarreos zamarreo = new Zamarreos();
		
		Caidas caida  = new Caidas();
		String path = Constants.getDatePath() + this.file_to_analize;
		FileReader inputFile = new FileReader(path);
		BufferedReader br = new BufferedReader(inputFile);
		ArrayList<Double> analysis_data = new ArrayList<Double>();
		String line;
		String[] sensor;
		while((line = br.readLine()) != null ){
			sensor = Constants.parseRawData(line);
			if (sensor[Constants.SENSOR_ID].equals("D")){
				Double transformation = simpleEuclidianDistanceFromScheme(line);
				analysis_data.add(transformation);
				}
			}
		br.close();
		Double average = average(analysis_data);
		String output = createMicroCollectorFile(this.file_to_analize);
		File outputFile		= new File(Constants.getDatePath() + "results/" + output);
		BufferedWriter bwr	= new BufferedWriter(new FileWriter(outputFile));
		bwr.write(fileHeader(average));
		bwr.close();
		Thread.sleep(100);
		caida.classifyWithModel(Constants.getDatePath() + "results/" + this.file_to_analize);
	}
	
	////////////////////////////////////
	/////// Manejo especifico de eventos
	////////////////////////////////////

	// Manejo de Caidas
	public static String fileHeader(Double inputData){
		return "@relation falls\n@attribute magnitude numeric\n@attribute label {0, 1}\n@data\n"+inputData+",?";
	}

	public static Double average(ArrayList<Double> data){
		double average = 0;
		for (double register : data) average += register;
		return average/data.size();
		}
	
	public static double simpleEuclidianDistanceFromScheme(String line){
		String[] data = Constants.parseRawData(line);
		Double a_d = new Double(data[Constants.RAW_MEASUREMENT_1]);
		Double b_d = new Double(data[Constants.RAW_MEASUREMENT_2]);
		Double c_d = new Double(data[Constants.RAW_MEASUREMENT_3]);
		return Math.sqrt(Math.pow(a_d,2) + Math.pow(b_d,2) + Math.pow(c_d,2));
		}
	
	public static String createMicroCollectorFile(String file_name){
		String path = Constants.getDatePath() + "results/";
		String file = path + file_name;
		try{
			new File(path).mkdirs();	
			File outputFile = new File(file);
			if(!outputFile.exists() && !outputFile.isDirectory()){
				outputFile.createNewFile();
				}
			}
		catch (Exception e){ 
			file = "failed"+file_name; 
			}
		return file_name;
		}

	// Manejo de Zamarreos
	public static String fileHeaderZamarreo(Double inputData){
		return "@relation falls\n@attribute magnitude numeric\n@attribute label {0, 1}\n@data\n"+inputData+",?";
	}
}
