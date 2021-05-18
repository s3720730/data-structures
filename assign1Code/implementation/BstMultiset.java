package implementation;

import java.util.List;
import java.util.ArrayList;

/**
 * BST implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class BstMultiset extends RmitMultiset
{	
	private Node root;

	public BstMultiset(){
		root = new Node();
	}

    @Override
	public void add(String item) {
		boolean found = false;

		//check is root is empty
        if(root.data == null)
			root.data = new Data(item);
		else{  
			Node currNode = root;
			
			while(!found){
				//less than
				if(currNode.data.id.compareTo(item)>0){

					if(currNode.left==null){
						found = true;
						currNode.left = new Node(item);
					}
					else{
						currNode = currNode.left;
					}

				}
				//greater than
				else if(currNode.data.id.compareTo(item)<0){
					if(currNode.right==null){
						found = true;
						currNode.right = new Node(item);
					}
					else{
						currNode = currNode.right;
					}
				}
				//equal to
				else if(currNode.data.id.compareTo(item)==0){
					//if already exists, adds another instance
					currNode.data.instances++;
					found = true;
				}
			}
			
		}
    } // end of add()


    @Override
	public int search(String item) {

		Node node = searchNode(root, item);
		
        if(node==null){
			return -1;
		}
		else{
			return node.data.instances;
		}
    } // end of search()

	private Node searchNode(Node node, String id){
		
		//less than
		if(node.data.id.compareTo(id)>0){
			node=node.left;
			if(node != null){
				
				node = searchNode(node, id);
			}
			else{
				node = null;
			}
		}
		else if(node.data.id.compareTo(id)<0){
			node=node.right;
			if(node != null){
				
				node = searchNode(node, id);				
			}
			else{
				node = null;
			}
		}
		else if(node.data.id.compareTo(id)==0){
			return node;
		}

		return node;
	}


    @Override
	public List<String> searchByInstance(int instanceCount) {

        List<String> list = new ArrayList<String>();

		searchNodeByInstance(root, instanceCount, list);

        return list;
    } // end of searchByInstance    

	private void searchNodeByInstance(Node node, int instanceCount, List<String> list){
		
		if(node.data.instances == instanceCount)
			list.add(node.data.id);
		
		if(node.left!=null)
			searchNodeByInstance(node.left, instanceCount, list);

		if(node.right!=null)
			searchNodeByInstance(node.right, instanceCount, list);

	}


    @Override
	public boolean contains(String item) {
		
		boolean found = false;
        Node node = searchNode(root, item);
		
        if(node!=null)
			found = true;
		
	
        return found;

    } // end of contains()


    @Override
	public void removeOne(String item) {
		
	
        if(root!=null)
			removeNode(root, item);
		
    } // end of removeOne()

	private Node removeNode(Node node, String id){
		
		if(node.data.id.compareTo(id)>0){
			
			if(node.left != null)
				node.left = removeNode(node.left, id);
			
		}
		else if(node.data.id.compareTo(id)<0){
		
			if(node.right != null)
				node.right = removeNode(node.right, id);				
		}
		else if(node.data.id.compareTo(id)==0){
			
			if(node.data.instances>1){
				node.data.instances--;
			}
			//if leaf node
			else if(node.left == null && node.right == null){
				node = null;
			}
			//if has right node
			else if(node.left == null){
				node = node.right;
			}
			//if has left node
			else if(node.right == null){
				node = node.left;
			}
			//has two children
			else{
				//finds smallest node from right subtree
				Data min = minNode(node.right);
				//sets node to new value
                node.data = min;
				//deletes the original node
                node.right = removeNode(node.right, min.id);
       
			}
		}
		
		return node;
	}

	private Data minNode(Node node) {

		//finds the smallest (leftmost) node in subtree
        if(node.left != null) {
            return minNode(node.left);
        }
		
        return node.data;
    }

	@Override
	public String print() {

		int size = getSize();
        Node[] nodes = new Node[size];
		toArray(root, nodes);
		//put into linear format
		sortArray(nodes, size);
		String string = "";

		for(int i = 0; i < size; i++){
			String info = nodes[i].data.id + ":" + Integer.toString(nodes[i].data.instances) + "\n";
			string = string.concat(info);
		}

        return string;

    } // end of OrderedPrint

	private void sortArray(Node[] array, int size) {

		int max;
        for (int i = 0; i < size -1; i++) {
			max = i;
			for (int j = i + 1; j < size; j++) {
				if(array[j].data.instances > array[max].data.instances)
					max = j;
			
			}
			Node tmp = array[i];
			array[i] = array[max];
			array[max] = tmp;
		}
    } // end of sort()
	

	private void toArray(Node node, Node[] nodeArray){
		
		addToArray(node, nodeArray);
	
		if(node.right!=null)
			toArray(node.right, nodeArray);
		if(node.left!=null)
			toArray(node.left, nodeArray);

	}

	private void addToArray(Node node, Node[] nodeArray){
		int i = 0;
		while(nodeArray[i]!=null){
			i++;
		}
		nodeArray[i] = node;
		
	}

	private int getSize(){

		int size = 0;

		if(root!=null)
			size = getSizeSearch(root);
	
		return size;
	}

	private int getSizeSearch(Node root){

		int size;

		if(root==null)
			size =  0;
		else{
			size = 1+ getSizeSearch(root.right) + getSizeSearch(root.left);
		}

		return size;

	}

    @Override
	public String printRange(String lower, String upper) {

        String string = "";

		if(root!=null)
			string = string.concat(printRangeSearch(root, lower, upper));

        return string;

    } // end of printRange()

	private String printRangeSearch(Node node, String lower, String upper){
		
		
		String info = node.data.id + ":" + Integer.toString(node.data.instances) + "\n";
		
		if(node.left!=null){
			if(node.data.id.compareTo(lower)>=0)
				info = info.concat(printRangeSearch(node.left, lower, upper));
			else{
				info = "";
			}
		
		}
		if(node.right!=null){
			if(node.data.id.compareTo(upper)<=0)
				info = info.concat(printRangeSearch(node.right, lower, upper));
			else{
				info = "";
			}
		}

		return info;

	}

	public Node getRoot(){
		return root;
	}


    @Override
	public RmitMultiset union(RmitMultiset other) {

        BstMultiset multiset = new BstMultiset();
		BstMultiset otherMultiset = (BstMultiset)other;
		
		if(root!=null)
			addNode(root, multiset);

		if(otherMultiset.getRoot() != null)
			addNode(otherMultiset.getRoot(), multiset);

        return multiset;

    } // end of union()

	private void addNode(Node node, BstMultiset multiset){
		
		for(int i = 0; i < node.data.instances; i++)
			multiset.add(node.data.id);

		if(node.left!=null)
			addNode(node.left, multiset);

		if(node.right!=null)
			addNode(node.right, multiset);

	}

    @Override
	public RmitMultiset intersect(RmitMultiset other) {
		
		BstMultiset multiset = new BstMultiset();
		BstMultiset otherMultiset = (BstMultiset)other;
		
		if(root!=null && otherMultiset.getRoot() != null)
			addNodeIntersect(otherMultiset.getRoot(), multiset);

        return multiset;
        
    } // end of intersect()

	private void addNodeIntersect(Node nodeToSearch, BstMultiset newMultiset){

		Node node = searchNode(root,nodeToSearch.data.id);
		if(node!=null){
		
				for(int i = 0; i < Math.min(node.data.instances,nodeToSearch.data.instances); i++)
					newMultiset.add(node.data.id);
				
		}

		if(nodeToSearch.left!=null)
			addNodeIntersect(nodeToSearch.left, newMultiset);

		if(nodeToSearch.right!=null)
			addNodeIntersect(nodeToSearch.right, newMultiset);

	}


    @Override
	public RmitMultiset difference(RmitMultiset other) {

        BstMultiset multiset = new BstMultiset();
		BstMultiset otherMultiset = (BstMultiset)other;
		
		if(root!=null && otherMultiset.getRoot() != null)
			addNodeDifference(root, otherMultiset.getRoot(), multiset);
		
        return multiset;

    } // end of difference()

	private void addNodeDifference(Node nodeToSearch, Node otherRoot, BstMultiset newMultiset){

		Node node = searchNode(otherRoot,nodeToSearch.data.id);
		if(node!=null){
		
			if(nodeToSearch.data.instances-node.data.instances>0)
				for(int i = 0; i < nodeToSearch.data.instances-node.data.instances; i++){
					newMultiset.add(nodeToSearch.data.id);
				}
				
		}
		else{

			for(int i = 0; i < nodeToSearch.data.instances; i++){
					newMultiset.add(nodeToSearch.data.id);
				}
		
		}

		if(nodeToSearch.left!=null)
			addNodeDifference(nodeToSearch.left, otherRoot, newMultiset);

		if(nodeToSearch.right!=null)
			addNodeDifference(nodeToSearch.right, otherRoot, newMultiset);

	}


	private class Data{
		private String id;
		private int instances;
		public Data(String id){
			this.id = id;
			this.instances = 1;
		}
		
	}

	private class Node{

		private Data data;
		private Node left, right;

		public Node(){
			this.left = null;
			this.right = null;
			this.data = null;
		}
		@SuppressWarnings("unused")
		public Node(Data data){
			this.left = null;
			this.right = null;
			this.data = data;
		}
		public Node(String id){
			this.left = null;
			this.right = null;
			this.data = new Data(id);
		}
	}

} // end of class BstMultiset
