package data_collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import management.Constants;
import modelos_predictivos.Caidas;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.time.LocalDate;
//import java.util.ArrayList;
//
//import management.Constants;
//import modelos_predictivos.Caidas;

public class tester {
	
	public static void main (String args[]) throws Exception{
		String s = "36408005.txt";
		Caidas caida = new Caidas();
		String res = caida.classifyWithModel(Constants.getDatePath() + "results/" + s);

//		long init = System.currentTimeMillis();
//		new RawDataProcessor(s).call();
//		System.out.println("TIEMPO PARA RAWDATAPROECESSOR (millis): " + (System.currentTimeMillis() - init)); // 500 millis
////		long init = System.currentTimeMillis();
////		Caidas caida  = new Caidas();
////		String file_to_analize = "1689000.txt";
////		String path = Constants.getDatePath()+file_to_analize;
////		FileReader inputFile = new FileReader(path);
////		BufferedReader br = new BufferedReader(inputFile);
////		ArrayList<Double> analysis_data = new ArrayList<Double>();
////		String line;
////		String[] sensor;
////		while((line = br.readLine()) != null ){
////			sensor = Constants.parseRawData(line);
////			if (sensor[Constants.SENSOR_ID].equals("D")){
////				Double transformation = simpleEuclidianDistanceFromScheme(line);
////				analysis_data.add(transformation);
////				}
////			}
////		br.close();
////		Double average = average(analysis_data);
////		String output = createMicroCollectorFile(file_to_analize);
////		File outputFile		= new File(output);
////		BufferedWriter bwr	= new BufferedWriter(new FileWriter(outputFile));
////		bwr.write(fileHeader(average));
////		bwr.close(); // 250 milisegundos
////		System.out.println(average + "");
////		Thread.sleep(100);
////		String res = caida.classifyWithModel(file_to_analize);
////		System.out.println(System.currentTimeMillis() - init); // 300 millis
////		System.out.println(file_to_analize + "\t" + res);
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
			System.out.println(file);}
		return file;
		}
	
	public static String fileHeader(Double inputData){
		return "@relation falls\n@attribute magnitude numeric\n@attribute label {0, 1}\n@data\n"+inputData+",?";
		}
	
	public static Double average(ArrayList<Double> data){
		double average = 0;
		for (double register : data) average += register;
		return average/data.size();
		}

}
