package modelos_predictivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// ventana de analisis -1 seg, +1 seg

import management.Constants;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class Zamarreos {
	private static int train_set_attributes;
	public Instances train_instance;
	public static NaiveBayes naiveBayesZamarreos = new NaiveBayes();
	private static String train_set			 	 = Constants.root_folder + "datos/models/zamarreos_training.txt";
	public String zamarreoEvents			  	 = Constants.root_folder + Constants.getDatePath() + "zamarreos/falls.txt";

	public Zamarreos() throws Exception{
		naiveBayesZamarreos = trainModel(train_set);
	}

	public NaiveBayes trainModel(String train) throws Exception{
		BufferedReader breader = new BufferedReader(new FileReader(train));
		train_instance = new Instances(breader);
		train_set_attributes = train_instance.numAttributes();
		train_instance.setClassIndex(train_set_attributes-1);
		naiveBayesZamarreos.buildClassifier(train_instance);
		return naiveBayesZamarreos;
	}

	public String classifyWithModel(String data_set) throws Exception{
		BufferedReader breader = new BufferedReader(new FileReader(data_set));
		Instances test = new Instances(breader);
		
		test.setClassIndex(train_set_attributes-1);
		Instances labeled = new Instances(test);
		for (int i=0; i<test.numInstances(); i++){
			double clsLabel = naiveBayesZamarreos.classifyInstance(test.instance(i));
			labeled.instance(i).setClassValue(clsLabel);
		}
		String outcome = (data_set.split(".txt")[0]).split("/")[4] + "\t" +labeled.instance(0).toString().split(",")[1];
		writeToZamarreos(outcome);
		return outcome;
	}

	public boolean writeToZamarreos(String outcome){
		boolean stored 		= false;
		File outputFile		= new File(zamarreoEvents);
		BufferedWriter bwr;
		try {
			bwr = new BufferedWriter(new FileWriter(outputFile, true));
			bwr.write(outcome + "\n");
			bwr.close();
			stored = true;
		} 
		catch (IOException e) {
			stored = false;
			e.printStackTrace();
		}
		return stored;
	}
}

