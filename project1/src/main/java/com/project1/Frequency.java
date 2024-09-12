package com.project1;
import java.util.Iterator;

public class Frequency<E extends Comparable> implements Iterable<E>{
    private Node first;	//starting node
    private Node parent;	//parent of currently processed node
    private int N;	//number of words
    
    /**
     * Linked List Node
     */
    private class Node implements Comparable<Node>{
    	private E key;
    	private int count;
        private Node next;
        /**
         * 	Constructor
         */
        Node(E e){
           key = e;
           count = 1;
           next = null;
        }
        /**
         * 	Constructor
         */
        Node(E e, Node r){
            key = e;
            count = 1;
            next = r;
         }
        
        @Override 
        public String toString(){
        	return "("+key +","+count+")";
        }
		@Override
		public int compareTo(Frequency<E>.Node o) {
			return 0;
		}
		
}

   /**
    * Inserts a word into linked list
    * @param key to be inserted 
    * @return true if the key is inserted successfully.
    */
    public boolean insert(E key){
		Node current = first;
        while(current != null){
            if(current.key.compareTo(key) == 0){
                current.count += 1;
                return true;
            }
            current = current.next;
        }
        insert(new Node(key));
    	return true;
	}
    
   /**
    * removes the nodes with given key
    * @param key: 
    * @return the deleted node
    */
    private Node remove(E key){
		Node current = first;
        Node prev = null;
        while(current != null){
            if (key.compareTo(current.key) == 0){
                if(prev == null) {
                    first = current.next;
                    return current;
                }
                prev.next = current.next;
                return current;
            }
            prev = current;
            current = current.next;
        }
    	return null;
	}

    /**
     * Inserts a node into correct location in the linked list
     * @param r is the node to be inserted
     * @return true if successful
     */
    private boolean insert(Node r){
        Node current = first;
        Node prev = null;
        while(current != null) {
            if (current.count < r.count) {
                return execInsert(prev, current, r);
            } else if (current.count == r.count) {
                if (r.key.compareTo(current.key) <= 0) {
                    return execInsert(prev, current, r);
                }
            }
            prev = current;
            current = current.next;
        }
    	return execInsert(prev, current, r);
	}

    private boolean execInsert(Node prev, Node current, Node r){
        if (prev == null){
            first = r;
            r.next = current;
            return true;
        }
        prev.next = r;
        r.next = current;
        return true;
    }
    
    
    /**
     * @param k is the key to be searched for
     * @return the node that has the word as its key
     */
    private Node find(E k){
        Node current = first;
        while(current != null){
            if(current.key.equals(k)){
                return current;
            }
            current = current.next;
        }
    	return null;
	}
    
    /**
     * @return true all the nodes are sorted, otherwise return false
     * 
     */
    public boolean isValid() {
        Node current = first;
        int lastInt = Integer.MAX_VALUE;
        E lastKey = null;
        while(current != null){
            if (current.count > lastInt){
                return false;
            }
            if (lastKey != null && lastInt == current.count && lastKey.compareTo(current.key) > 0){
                return false;
            }
            lastInt = current.count;
            lastKey = current.key;
            current = current.next;
        }
    	return true;
    }
    
    /**
     * 
     * @param key is the key to be searched for
     * @return frequency of the key. Returns -1 if key does not exist
     * 
     */
    public int getCount(E key){
        Node current = first;
        while(current != null){
            if (current.key.compareTo(key) == 0){
                return current.count;
            }
            current = current.next;
        }
        return -1;
	}
    /**
     * Returns the first n words and count
     * @param n number of words to be returned
     * @return first n words in the format [word1,freq1][word2,freq2]...[wordn,freqn] 
     * in the same order as the linked list
     */
    public String getWords(int n){
		Node current = first;
        StringBuilder sb = new StringBuilder();
        while(current != null){
            sb.append("[" + current.key + "," + current.count + "]");
            current = current.next;
        }
    	return sb.toString();
	}
    
    
    /**
     * Frequency List iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new FreqIterator();
    }
    
    /**
     * 
     * Frequency List iterator class
     *
     */
    private class FreqIterator implements Iterator<E>{
      @Override
      public boolean hasNext() {
		//TODO
    	  return true;
	}
      @Override
      public E next() {
		//TODO
    	  return null;
	}
    }
}
