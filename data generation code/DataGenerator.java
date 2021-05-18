
import implementation.*;


public class DataGenerator{

	public String[] sampleData;
	
	private int numOfTrials;

	private int stringSize;

	private int sampleSize;


	public DataGenerator(){

		numOfTrials = 100;

		stringSize = 2;

	}

	public static void main(String[] args) {

		DataGenerator generator = new DataGenerator();
		generator.run();

	}

	public void run(){

		//creating multisets
		RmitMultiset array = new ArrayMultiset();
		RmitMultiset bst = new BstMultiset();
		RmitMultiset linkedList = new OrderedLinkedListMultiset();
		RmitMultiset dualLinkedList = new DualLinkedListMultiset();

		//testing with sample size of 100
		setSampleSize(100);
		System.out.println("ARRAY | "+sampleSize+": ");
		testMultiset(array);
		System.out.println("BST | "+sampleSize+": ");
		testMultiset(bst);
	

		//testing with sample size of 500
		setSampleSize(500);
		System.out.println("ARRAY | "+sampleSize+": ");
		testMultiset(array);
		System.out.println("BST | "+sampleSize+": ");
		testMultiset(array);

		//testing with sample size of 750
		setSampleSize(750);
		System.out.println("ARRAY | "+sampleSize+": ");
		testMultiset(array);
		System.out.println("BST | "+sampleSize+": ");
		testMultiset(bst);


		//testing with sample size of 1000
		setSampleSize(1000);
		System.out.println("ARRAY | "+sampleSize+": ");
		testMultiset(array);
		System.out.println("BST | "+sampleSize+": ");
		testMultiset(bst);

		//testing with sample size of 2500
		setSampleSize(2500);
		System.out.println("ARRAY | "+sampleSize+": ");
		testMultiset(array);
		System.out.println("BST | "+sampleSize+": ");
		testMultiset(bst);
	
	}

	private void testMultiset(RmitMultiset multiset){

		long netGrowing = 0;
		long netPrinting = 0;
		long netIntersect = 0;
		long netShrinking = 0;

		//running multiple trials
		for(int i = 0; i < numOfTrials; i++){
			fill();
			netGrowing += growingMultiset(multiset);
			netPrinting += printMultiset(multiset);
			netIntersect += intersectMultiset(multiset);
			netShrinking += shrinkingMultiset(multiset);

		}

		//printing out averages
		System.out.println("Growing~ " + netGrowing/numOfTrials +"ns");
		System.out.println("Shrinkning~ " + netShrinking/numOfTrials +"ns");
		System.out.println("Intersect~ " + netIntersect/numOfTrials +"ns");
		System.out.println("Printing~ " + netPrinting/numOfTrials +"ns");

	}

	private long growingMultiset(RmitMultiset multiset){

		long netTime = 0;
		long startTime;
		long endTime;

		//testing growing
		for(String s : sampleData){
			startTime = System.nanoTime(); 
			multiset.add(s);
			endTime = System.nanoTime();
			netTime = netTime + (endTime-startTime);
			}

		return netTime;
	
	}

	private long shrinkingMultiset(RmitMultiset multiset){

		long netTime = 0;
		long startTime;
		long endTime;

		//testing shinking
		for(String s : sampleData){
			startTime = System.nanoTime(); 
			multiset.removeOne(s);
			endTime = System.nanoTime();
			netTime = netTime + (endTime-startTime);
			}
		
		return netTime;
	
	}

	private long printMultiset(RmitMultiset multiset){

		long netTime = 0;
		long startTime;
		long endTime;
		String s;

		//testing print
		startTime = System.nanoTime(); 
		s = multiset.print();
		endTime = System.nanoTime();
		netTime = endTime-startTime;
		
		return netTime;
	
	}

	private long intersectMultiset(RmitMultiset multiset){

		long netTime = 0;
		long startTime;
		long endTime;
		RmitMultiset otherMultiset;

		//creating new multiset to compare to testing multiset
		if(multiset.getClass().getSimpleName().equals("ArrayMultiset")){
			otherMultiset = new ArrayMultiset();
		}
		else if(multiset.getClass().getSimpleName().equals("BstMultiset")){
			otherMultiset = new BstMultiset();
		}
		else if(multiset.getClass().getSimpleName().equals("OrderedLinkedListMultiset")){
			otherMultiset = new OrderedLinkedListMultiset();
		}
		else{
			otherMultiset = new DualLinkedListMultiset();
		}

		//filling new multiset
		String[] otherSampleData = new String[sampleSize];
		for(int i = 0; i < sampleSize; i++){
			otherSampleData[i]=getRandomId();
			otherMultiset.add(otherSampleData[i]);
		}

		//testing intersect
		startTime = System.nanoTime(); 
		RmitMultiset newMultiset = multiset.intersect(otherMultiset);
		endTime = System.nanoTime();
		netTime = endTime-startTime;
	
		return netTime;
	
	}

	private void setSampleSize(int sampleSize){
		this.sampleSize = sampleSize;
	}


	private void fill(){

		sampleData = new String[sampleSize];

		for(int i = 0; i < sampleSize; i++){
			sampleData[i]=getRandomId();
		} 
	
	}


	private String getRandomId(){ 
  
        // chose a Character random from this String 
        String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; 
  
        StringBuilder sb = new StringBuilder(stringSize); 
  
        for (int i = 0; i < stringSize; i++) { 

            int index = (int)(alphabetString.length() * Math.random()); 
  
            sb.append(alphabetString.charAt(index)); 
        } 
  
        return sb.toString(); 
    } 

} 
