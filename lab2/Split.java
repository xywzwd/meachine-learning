import java.io.File;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;


public class Split {
	public static void main(String[] args) {
		
		 try {
			Instances Set = DataSource.read("E:/isuclasses/COMS573/lab2 materials/datasets-UCI/UCI/vote.arff");
			Set.randomize(new Random(0));  
			int setSize = (int) Math.round(Set.numInstances() * 0.2);
		     
		    Instances Set1 = new Instances(Set, 0, setSize); 
		    
		    Instances Set2 = new Instances(Set, setSize, setSize);
		    Instances Set3 = new Instances(Set, setSize*2, setSize);
		    Instances Set4 = new Instances(Set, setSize*3, setSize);
		    Instances Set5 = new Instances(Set, setSize*4, Math.round(Set.numInstances()) - setSize*4);
		    
		    ArffSaver saver = new ArffSaver();
		    saver.setInstances(Set1);
		    saver.setFile(new File("E:/isuclasses/COMS573/lab2/data/set1.arff"));
		    saver.writeBatch();
		    
		    saver.setInstances(Set2);
		    saver.setFile(new File("E:/isuclasses/COMS573/lab2/data/set2.arff"));
		    saver.writeBatch();
		    
		    saver.setInstances(Set3);
		    saver.setFile(new File("E:/isuclasses/COMS573/lab2/data/set3.arff"));
		    saver.writeBatch();
		    
		    saver.setInstances(Set4);
		    saver.setFile(new File("E:/isuclasses/COMS573/lab2/data/set4.arff"));
		    saver.writeBatch();
		    
		    saver.setInstances(Set5);
		    saver.setFile(new File("E:/isuclasses/COMS573/lab2/data/set5.arff"));
		    saver.writeBatch();
		    
		} catch (Exception e) {
			
			e.printStackTrace();
		}  
	}
}
