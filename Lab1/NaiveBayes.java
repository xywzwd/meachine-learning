package yuwei.classification;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NaiveBayes {

	public static void main(String[] args) {
		
		ArrayList arr = new ArrayList();
		arr = train_data();
		double[] prior;
		prior = (double[]) arr.get(0);
		Group[] group;
		group = (Group[]) arr.get(1);
		
		//test
		
		int[] docID = new int[10000000];
		int[] wordID = new int[10000000];
		int[] count = new int[10000000];
		int[] label = new int[10000000];
		int num = 0;
		
		
	
		//read test_data
		try {
			BufferedReader reader = new BufferedReader(new FileReader("test_data.csv"));
			String line = null;
			
			while((line=reader.readLine()) != null) {
				String item[] = line.split(",");
				docID[num] = Integer.parseInt(item[0]);
				wordID[num] = Integer.parseInt(item[1]);
				count[num] = Integer.parseInt(item[2]);
	
				num++;
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	
		
		int num_ID = 0;
		try {
			BufferedReader reader2 = new BufferedReader(new FileReader("test_label.csv"));
			String line = null;
			
			while((line = reader2.readLine()) != null) {
				label[num_ID] = Integer.parseInt(line);
				num_ID++;
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		//class to document
        
        Document doc[] = new Document[num_ID+1];
        for(int i = 1; i <= num_ID; i++) {
        	doc[i] = new Document(i, label[i-1]);
        }
        
        for(int i = 0; i < num; i++) {
        	doc[docID[i]].word_id[wordID[i]] = count[i];
        	
        }
   
        double doc_value_mle = 0; 
        double max_mle = Integer.MIN_VALUE;
        int[] group_est_mle = new int[num_ID+1];
        int correct_mle = 0;
        double mle_correct[] = new double[20];
        int[][] confusion_mle = new int[20][20];
        
        double doc_value_be = 0; 
        double max_be = Integer.MIN_VALUE;
        int[] group_est_be = new int[num_ID+1];
        int correct_be = 0;
        double be_correct[] = new double[20];
        int[][] confusion_be = new int[20][20];
        
		for (int i = 1; i <= num_ID; i++) {
			for (int j = 0; j < 20; j++) {
				for (int h = 1; h < 53976; h++) {
					if (doc[i].word_id[h] != 0) {
						
						doc_value_mle += Math.log(group[j].priority_mle[h] + 0.00000000000001);
						doc_value_be += Math.log(group[j].priority_be[h]);
					}
				}
				doc_value_mle += Math.log(prior[j+1]);
				doc_value_be += Math.log(prior[j+1]);
				
				
				if(max_mle < doc_value_mle) {
					max_mle = doc_value_mle;
					group_est_mle[i] = j+1;
					
				}
				doc_value_mle = 0;
				
				if(max_be < doc_value_be) {
					max_be = doc_value_be;
					group_est_be[i] = j+1;
					
				}
				doc_value_be = 0;
				
				
			}
			if(group_est_mle[i] == doc[i].group_id) {
				correct_mle++;
			}
			
			confusion_mle[group_est_mle[i]-1][doc[i].group_id-1]++;
			max_mle = Integer.MIN_VALUE;
			
			if(group_est_be[i] == doc[i].group_id) {
				correct_be++;
			}
			
			confusion_be[group_est_be[i]-1][doc[i].group_id-1]++;
			max_be = Integer.MIN_VALUE;
		}
		
		System.out.println("mle for test data " + (double)correct_mle/num_ID);
		
		for(int j = 0; j < 20; j++) {
			for(int i = 0; i < 20; i++) {
				mle_correct[j] += confusion_mle[i][j];
			}
			mle_correct[j] = confusion_mle[j][j]/mle_correct[j];
			System.out.println("class " + (j+1) + " accuracy: " + mle_correct[j]);
		}
		
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				System.out.print(confusion_mle[i][j]+" ");
			}
			System.out.println();
		}
		
        System.out.println("be for test data " + (double)correct_be/num_ID);
		
		for(int j = 0; j < 20; j++) {
			for(int i = 0; i < 20; i++) {
				be_correct[j] += confusion_be[i][j];
			}
			be_correct[j] = confusion_be[j][j]/be_correct[j];
			System.out.println("class " + (j+1) + " accuracy: " + be_correct[j]);
		}
		
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				System.out.print(confusion_be[i][j]+" ");
			}
			System.out.println();
		}
  
	
	}
	
	public static ArrayList train_data() {
		
		int[] docID = new int[10000000];
		int[] wordID = new int[10000000];
		int[] count = new int[10000000];
		int[] label = new int[10000000];
		int num = 0;
		
		ArrayList arr = new ArrayList();
		
		//read train_data
		try {
			BufferedReader reader = new BufferedReader(new FileReader("train_data.csv"));
			String line = null;
			
			while((line=reader.readLine()) != null) {
				String item[] = line.split(",");
				docID[num] = Integer.parseInt(item[0]);
				wordID[num] = Integer.parseInt(item[1]);
				count[num] = Integer.parseInt(item[2]);
	
				num++;
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	
		
		//read train label
		int num_ID = 0;
		try {
			BufferedReader reader2 = new BufferedReader(new FileReader("train_label.csv"));
			String line = null;
			
			while((line = reader2.readLine()) != null) {
				label[num_ID] = Integer.parseInt(line);
				num_ID++;
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		System.out.println("the number is:" + num_ID);
		for(int i = 0; i < 20; i++) {
			System.out.println(label[i]);
		}
		*/
		
		//calculate prior and total words
		int[] group_num = new int[21];
		double[] prior = new double[21];
		int[] total_words = new int[21];
		
		for(int i = 0; i < num_ID; i++) {
			group_num[label[i]]++;
			
		}
		
		for(int i = 0; i< num; i++) {
			total_words[label[docID[i]-1]]+=count[i];
		}
	
		for(int i = 1; i < 21; i++) {
			prior[i] = (double)group_num[i]/(double)num_ID;
			System.out.println("prior " + i + ": " + prior[i]);
		}
		
		//calculate estimator
		Group[] group = new Group[20];
		for(int i = 1; i < 21; i++) {
			group[i-1] = new Group(i);
		}
		
		for(int i = 0; i < num; i++) {
			group[label[docID[i]-1]-1].word_number[wordID[i]] += count[i];
		}
		
        for(int i = 0; i < 20; i++) {
        	for(int j = 1; j < 53976; j++) {
        		group[i].priority_mle[j] = (double)group[i].word_number[j]/(double)total_words[i+1];
        		group[i].priority_be[j] = ((double)group[i].word_number[j]+1)/((double)total_words[i+1]+53976);
        	}
        }
        
        
        //class to document
        
        Document doc[] = new Document[num_ID+1];
        for(int i = 1; i <= num_ID; i++) {
        	doc[i] = new Document(i, label[i-1]);
        }
        
        for(int i = 0; i < num; i++) {
        	doc[docID[i]].word_id[wordID[i]] = count[i];
        	
        }
       
        
        
        
        //test results
        double doc_value_mle = 0; 
        double max_mle = Integer.MIN_VALUE;
        int[] group_est_mle = new int[num_ID+1];
        int correct_mle = 0;
        double mle_correct[] = new double[20];
        int[][] confusion_mle = new int[20][20];
        
        double doc_value_be = 0; 
        double max_be = Integer.MIN_VALUE;
        int[] group_est_be = new int[num_ID+1];
        int correct_be = 0;
        double be_correct[] = new double[20];
        int[][] confusion_be = new int[20][20];
        
		for (int i = 1; i <= num_ID; i++) {
			for (int j = 0; j < 20; j++) {
				for (int h = 1; h < 53976; h++) {
					if (doc[i].word_id[h] != 0) {
						
						doc_value_mle += Math.log(group[j].priority_mle[h]);
						doc_value_be += Math.log(group[j].priority_be[h]);
					}
				}
				doc_value_mle += Math.log(prior[j+1]);
				doc_value_be += Math.log(prior[j+1]);
				
				
				if(max_mle < doc_value_mle) {
					max_mle = doc_value_mle;
					group_est_mle[i] = j+1;
					
				}
				doc_value_mle = 0;
				
				if(max_be < doc_value_be) {
					max_be = doc_value_be;
					group_est_be[i] = j+1;
					
				}
				doc_value_be = 0;
				
				
			}
			if(group_est_mle[i] == doc[i].group_id) {
				correct_mle++;
			}
			
			confusion_mle[group_est_mle[i]-1][doc[i].group_id-1]++;
			max_mle = Integer.MIN_VALUE;
			
			if(group_est_be[i] == doc[i].group_id) {
				correct_be++;
			}
			
			confusion_be[group_est_be[i]-1][doc[i].group_id-1]++;
			max_be = Integer.MIN_VALUE;
		}
		
		System.out.println("mle for train data " + (double)correct_mle/num_ID);
		
		for(int j = 0; j < 20; j++) {
			for(int i = 0; i < 20; i++) {
				mle_correct[j] += confusion_mle[i][j];
			}
			mle_correct[j] = confusion_mle[j][j]/mle_correct[j];
			System.out.println("class " + (j+1) + " accuracy: " + mle_correct[j]);
		}
		
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				System.out.print(confusion_mle[i][j]+" ");
			}
			System.out.println();
		}
		
        System.out.println("be for train data " + (double)correct_be/num_ID);
		
		for(int j = 0; j < 20; j++) {
			for(int i = 0; i < 20; i++) {
				be_correct[j] += confusion_be[i][j];
			}
			be_correct[j] = confusion_be[j][j]/be_correct[j];
			System.out.println("class " + (j+1) + " accuracy: " + be_correct[j]);
		}
		
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				System.out.print(confusion_be[i][j]+" ");
			}
			System.out.println();
		}
		
		arr.add(prior);
		arr.add(group);
		
		return arr;
	}
	

}

class Document{
	public int doc_id;
	public int[] word_id = new int[62000];
	public int group_id;
	
	public Document(int doc_id, int group_id) {
		super();
		this.doc_id = doc_id;
		this.group_id = group_id;
	}
	
	
}


class Group{
	public int group_id;
	public int[] word_number = new int[62000];
	public double[] priority_mle = new double[62000];
	public double[] priority_be = new double[62000];
	
	public Group(int group_id) {
		super();
		this.group_id = group_id;
	}
	
	
	
}
