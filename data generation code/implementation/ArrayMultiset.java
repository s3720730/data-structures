package implementation;

import java.util.ArrayList;
import java.util.List;


/**
 * Array implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class ArrayMultiset extends RmitMultiset
{	
	private Data[] array;                                                                                                                                                                                                               
	private int capacity;
	private int size;
	private int addtionalCapacity;
	
	public ArrayMultiset(){
		capacity = 2;
		addtionalCapacity = 2;
		size = 0;
        array = new Data[capacity];
	}

	public int getSize(){
		return size;
	}

	public Data[] getArray(){
		return array;
	}

    @Override
	public void add(String elem) {
			
            boolean existingElem = false;

			//increase capacity of array has reached capacity
			if(size + 1 == capacity){
				this.increaseCapacity();
			}

			
			int i = 0;
			while(i < size && !existingElem){
		
				if(elem.equals(array[i].id)){
			
					array[i].instances++;
					existingElem = true;
				}
				
				i++;
			}

			//if element is not already instance in array add to list
			if(!existingElem){

				array[size] = new Data(elem);
				size++;
			}
            
			

        
		
        
    } // end of add()

	private void increaseCapacity(){

		Data[] newArray = new Data[capacity+addtionalCapacity];
		for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
        }

        // update array reference
        array = newArray;

		//updates capacity
		capacity = capacity+addtionalCapacity;
			
	}


    @Override
	public int search(String elem) {
		
		boolean found = false;
		int instanceCount = searchFailed;

		int i = 0;
		while(i < size && !found){
		
			if(elem.equals(array[i].id)){
				found = true;
				instanceCount = (array[i].instances);
			}

			i++;
		}
		

        return instanceCount;
    } // end of search()


    @Override
    public List<String> searchByInstance(int instanceCount) {

		List<String> elements = new ArrayList<String>();
		
		int i = 0;

		while(i < size){
		
			if(instanceCount == array[i].instances)		
				elements.add(array[i].id);

			i++;
		}

        return elements;
    } // end of searchByInstance


    @Override
	public boolean contains(String elem) {

        boolean found = false;

		int i = 0;
		while(i < size && !found){
		
			if(elem.equals(array[i].id)){
				found = true;
			}

			i++;
		}
        return found;
    } // end of contains()


    @Override
	public void removeOne(String elem) {

        boolean found = false;

		int i = 0;
		while(i < size && !found){
		
			if(elem.equals(array[i].id)){
				found = true;

				if(array[i].instances > 1)
					array[i].instances--;
				else{
					int j = i;
					while(array[j+1] != null){
						array[j]=array[j+1];
						j++;
					}
					array[j] = null;
					size--;
				}


			}

			i++;
		}
   
    } // end of removeOne()


    @Override
	public String print() {
		
		Data[] newArray = array;

		sortArray(newArray);

		int i = 0;
		String string = "";
		String info;
		while(i < size){
			info = array[i].id + ":" + Integer.toString(array[i].instances) + "\n";
			string = string.concat(info);
			i++;
		}
        return string;
    } // end of OrderedPrint

	private void sortArray(Data[] array) {

		int max;
        for (int i = 0; i < size -1; i++) {
			max = i;
			for (int j = i + 1; j < size; j++) {
				if(array[j].instances > array[max].instances)
					max = j;
			
			}
			Data tmp = array[i];
			array[i] = array[max];
			array[max] = tmp;
		}
    } // end of sort()


    @Override
	public String printRange(String lower, String upper) {
		int i = 0;
		String string = "";
		String info;
		while(i < size){
	
			if( array[i].id.compareTo(upper)<=0 && array[i].id.compareTo(lower)>=0){
				info = array[i].id + ":" + Integer.toString(array[i].instances) + "\n";
				string = string.concat(info);
			}
			i++;
		}
        return string;
   
    } // end of printRange()


	  @Override
	public RmitMultiset union(RmitMultiset other) {
	
		ArrayMultiset newMultiset = new ArrayMultiset();

		//copying array
		for(int i = 0; i < size; i++){
			for(int j = 0; j < array[i].instances; j++)
			newMultiset.add(array[i].id);
		}
		
		//adding other multiset
		ArrayMultiset otherArrayMultiset = (ArrayMultiset)other;
		Data[] otherArray = otherArrayMultiset.getArray();
		
		for(int i = 0; i < otherArrayMultiset.getSize(); i++){

			for(int j = 0; j < otherArray[i].instances; j++){
				newMultiset.add((otherArray[i].id));
			}
		}
   
        return newMultiset;
		
    } // end of union()

    @Override
	public RmitMultiset intersect(RmitMultiset other) {

		ArrayMultiset newMultiset = new ArrayMultiset();
		ArrayMultiset otherArrayMultiset = (ArrayMultiset)other;
		Data[] otherArray = otherArrayMultiset.getArray();
		
		for(int i = 0; i < otherArrayMultiset.getSize(); i++){

			for(int j = 0; j < size; j++){

				if(otherArray[i].id.equals(array[j].id)){
					
					for(int k = 0; k < Math.min(array[j].instances,otherArray[i].instances); k++){
						newMultiset.add(array[j].id);
					
					}

				}
			}
		
		}
        return newMultiset;
    } // end of intersect()
	
	@Override
	public RmitMultiset difference(RmitMultiset other) {

		ArrayMultiset newMultiset = new ArrayMultiset();
		ArrayMultiset otherArrayMultiset = (ArrayMultiset)other;
		boolean match = false; 
		Data[] otherArray = otherArrayMultiset.getArray();
		
		for(int i = 0; i < size; i++){
			
			match = false;
			int j = 0;

			while(j<otherArrayMultiset.getSize() && !match){
				
				if(otherArray[j].id.equals(array[i].id)){
			
					match = true;
				}
				else{
					j++;
				}
			
			}
	
			
			if(!match){
				for(int z = 0; z < array[i].instances; z++)
					newMultiset.add(array[i].id);
			
			}
			else if(array[i].instances>otherArray[j].instances){


				for(int z = 0; z < array[i].instances-otherArray[j].instances; z++)
						newMultiset.add(array[i].id);
				
			}
	
		}
   
        return newMultiset;
    } // end of difference()
	
	private class Data{
		private String id;
		private int instances;
		public Data(String id){
			this.id = id;
			this.instances = 1;
		}
		
	}
	
	
		
	
} // end of class ArrayMultiset
