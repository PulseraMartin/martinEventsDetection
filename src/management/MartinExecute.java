package management;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import data_collector.RawDataCollector;
import data_collector.RawDataProcessor;

public class MartinExecute {
	static ExecutorService service = Executors.newFixedThreadPool(3);
	
	public static void main(String[] args) throws InterruptedException{
		Constants.setLastRecord("inicio.txt");
//		Constants.globalSettings(); // (Deteccion y) seteo inicial de las variables para el procesamiento: CASOS 
		service.submit(new RawDataCollector()); // RawDataCollecto starts working
		}
	
	public static void runProcessing(String s){
		service.submit(new RawDataProcessor(s));
		}
}