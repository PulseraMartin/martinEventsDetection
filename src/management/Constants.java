package management;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Arrays;
import java.util.List;

//import weka.classifiers.bayes.NaiveBayes;

public class Constants {
	// Debugging
	public static long global_time;
	public static Calendar mydate = Calendar.getInstance();
	
//	public static NaiveBayes naiveBayesCaidas = new NaiveBayes();
	public static String first_record;
	public static String last_record;
	
	// Time management constants
	public static LocalDate current_time = LocalDate.now();
	public static int year 	= current_time.getYear();
	public static int month = current_time.getMonthValue();
	public static int day 	= current_time.getDayOfMonth();
	
	// Data scheme
	public static int READING_ID 		= 0;
	public static int SENSOR_ID			= 1;
	public static int MEASUREMENT_1 	= 2;
	public static int RAW_MEASUREMENT_1 = 3;
	public static int RAW_MEASUREMENT_2 = 4;
	public static int RAW_MEASUREMENT_3 = 5;
	public static int READING_TIME 		= 6;
	
	// Files management
	public static String root_folder	= "/Users/jorge/Documents/workspace/martinEventsDetection/";
	
	// Management auxiliary functions
	public void update_date(){
		current_time = LocalDate.now();
		year = current_time.getYear();
		month = current_time.getMonthValue();
		day = current_time.getDayOfMonth();
		}
	
	public static synchronized void getProcessTimeMeasurement(String donde, long time){
		long time_elapsed = time = getGlobalTime();
		setGlobalTime(time);
		String res = donde + time_elapsed;
		System.out.println(res);
		}
	
	public static synchronized void setGlobalTime(long time){
		Constants.global_time = System.currentTimeMillis();
		}
	
	public static synchronized long getGlobalTime(){
		return Constants.global_time;
		}
	
	public static synchronized void setLastRecord(String record){
		last_record = record;
		}
	
	public static synchronized String getLastRecord(){
		return last_record;
		}
	
	public static String[] parseRawData(String raw_data){ 
		return raw_data.split("\t"); 
		}
	
	public static String getDatePath(){
		return Constants.year + "/" + Constants.month + "/" + Constants.day + "/";
		}
	
	public static void setLatestDataFile(){
		File data_source_folder = new File(getDatePath());
		List<String> files_in_folder = listFilesForFolder(data_source_folder);
		last_record = java.util.Collections.max(files_in_folder);
		}
	
	public static List<String> listFilesForFolder(File folder){
		List<String> files = new ArrayList<String>();
		for(File fileEntry : folder.listFiles()){
			if(fileEntry.isDirectory()){
				listFilesForFolder(fileEntry);
				}
			else{ 
				files.add(fileEntry.getName()); 
				}
			}
		return files;
	}
	
	//calculo de la hora del dia
	public static int currentHourOfDay(){
		mydate.setTimeInMillis((long)System.currentTimeMillis());
	    int hour  = mydate.get(Calendar.HOUR_OF_DAY);
	    int min   = mydate.get(Calendar.MINUTE);
	    int sec   = mydate.get(Calendar.SECOND);
	    int milli = mydate.get(Calendar.MILLISECOND);
	    return ((hour*3600000) + (min*60000) + (sec*1000) + milli);
	}
	
}
