package prog12;


import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;


public class Binge2 implements SearchEngine {

	HardDisk<PageFile> PageDisk = new HardDisk<PageFile>(); //harddisk maps an INDEX TO PAGEFILE
	PageTrie URLtoIndex = new PageTrie();    				//trie maps a URL TO INDEX

	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>(); //maps a LONG WORD INDEX TO ITS WORDFILE
	WordTable wordToIndex = new WordTable();  //WordTable (hash)maps a WORD TO ITS INDEX 



	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub

		
		URLtoIndex.read(PageDisk); 
		wordToIndex.read(wordDisk);

	}
	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub

		System.out.println("pageDisk\n" + PageDisk);
		System.out.println("urlToIndex\n" + URLtoIndex);
		System.out.println("wordDisk\n" + wordDisk);
		System.out.println("wordToIndex\n" + wordToIndex);

		
		Iterator<Long>[] wordFileIterators = (Iterator<Long>[]) new Iterator[keyWords.size()]; //has each words page of indices 
		long[] currentPageIndices = new long[keyWords.size()] ; //stores the current index



		//initialize wordFileIterators

		int i = 0;
		for( String word : keyWords) { 
			if(!wordToIndex.containsKey(word)) { 

				return new String[0]; 

			}
			else { //the word is in the trie, get the index 

				long index = wordToIndex.get(word); 
				wordFileIterators[i] = wordDisk.get(index).iterator(); 
				i++;

			}
		}

		PriorityQueue<Long> bestPageIndices = new PriorityQueue<Long>(numResults, new PageComparator());  

		while(getNextPageIndices(currentPageIndices , wordFileIterators) == true) {

			if(allEqual(currentPageIndices) == true) {  //found a match 

				//long peek = bestPageIndices.peek();
				//long putIn = currentPageIndices[0]; //is 0 becuase they are all matching anyways they're all the same shit 

				//if(bestPageIndices.size() < numResults || PageDisk.get(putIn).getRefCount() > PageDisk.get(peek).getRefCount()) { 
				if(bestPageIndices.size() < numResults || PageDisk.get(currentPageIndices[0]).getRefCount() > PageDisk.get(bestPageIndices.peek()).getRefCount()){	

					if(bestPageIndices.size() == numResults) { //the queue is full

						bestPageIndices.poll(); 

					}
					//bestPageIndices.offer(putIn);
					bestPageIndices.offer(currentPageIndices[0]);
				}
			}
		}


		String [] outputArray = new String[bestPageIndices.size()]; 
		
		for(int k = outputArray.length-1 ; k >= 0 ; k--) { //print BACKWARDS 

			outputArray[k] = PageDisk.get(bestPageIndices.poll()).url; 
				System.out.println(outputArray[k]);

		}
		//System.out.println(outputArray);
		
		//for(int k = 0; k < outputArray.length ; k++) { 
			
			//outputArray[k] = PageDisk.get(bestPageIndices.poll()).url;
			//System.out.println(outputArray[k]);
		//}
		
		
		return outputArray; 



	}
	public class PageComparator implements Comparator<Long>{ 

		public int compare (Long page1, Long page2) { 

			return PageDisk.get(page1).getRefCount() - PageDisk.get(page2).getRefCount(); 

		}
	}
	/** If all the currentPageIndices are the same (because are just
    starting or just found a match), get the next page index for
    each word: call next() for each word file iterator and put the
    result into current page indices.

    If they are not all the same, only get the next index if the
    current index is smaller than the largest.

    Return false if hasNext() is false for any iterator.

    @param currentPageIndices array of current page indices
    @param wordFileIterators array of iterators with next page indices
    @return true if all minimum page indices updates, false otherwise
	 */

	private boolean getNextPageIndices(long[] currentPageIndices, Iterator<Long>[] wordFileIterators) {


		long largest = currentPageIndices[0]; 

		if(allEqual(currentPageIndices)==true) { //if everything is equal (0,0,0) then we get next

			for(int i = 0; i < wordFileIterators.length; i++) { 

				if(wordFileIterators[i].hasNext() == false) { 

					return false; 
				}
				else {
					currentPageIndices[i] = wordFileIterators[i].next();
				}	
			}	
		}




		else { 

			for( long i : currentPageIndices ) { //finding the largest index 
				if( i > largest) { 
					largest = i; 
				}
			}
			for(int i = 0; i < currentPageIndices.length; i++) { 


				if(currentPageIndices[i] < largest) { 

					if(wordFileIterators[i].hasNext() == false) {
						return false; 
					}
					else{
						currentPageIndices[i] = wordFileIterators[i].next(); 
					}
				}


			}

		}

		return true; 
	}

	/** Check if all elements in an array are equal.
    @param array an array of numbers
    @return true if all are equal, false otherwise
	 */
	public boolean allEqual(long[] array) { 



		for(int i = 0; i < array.length; i++) { 

			if(array[0] != array[i]) { 

				return false; 

			}
		}

		return true;  
	}

}
