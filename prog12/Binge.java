package prog12;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Binge implements SearchEngine{


	HardDisk<PageFile> PageDisk = new HardDisk<PageFile>(); //harddisk maps an INDEX TO PAGEFILE
	PageTrie URLtoIndex = new PageTrie();    				//trie maps a URL TO INDEX

	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>(); //maps a LONG WORD INDEX TO ITS WORDFILE
	WordTable wordToIndex = new WordTable();  //WordTable (hash)maps a WORD TO ITS INDEX 



	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub



		System.out.println("gather " + startingURLs);
		Queue<Long> q = new ArrayDeque<Long>(); //queue of indices
		
		
		for( String URL : startingURLs) { 


			if( !URLtoIndex.containsKey(URL)) { //if not indexed already 


				Long index = indexPage(URL); //indexes it
				q.offer(index);


			}


		}
		while(q.isEmpty() == false) { 

			System.out.println("queue " + q);
			long pageIndex = q.poll();
			PageFile pagefile = PageDisk.get(pageIndex);
			long index; 
			
			
			System.out.println("dequeued " + pagefile);
			
			if( browser.loadPage(pagefile.url) == true) {  //can we load the url from pagefile to browser?  


				List<String> URLs = browser.getURLs();
				Set<Long> UrlSet = new HashSet<Long>(); //the set 
				
				System.out.println("urls " + URLs);

				for( String url: URLs ) {    

					if( !URLtoIndex.containsKey(url)) {   

						index = indexPage(url); 
						q.offer(index); 
						
					}
					else { 
						index = URLtoIndex.get(url);
					}

					UrlSet.add(index); 


				}

				for( Long i : UrlSet) { 


					PageDisk.get(i).incRefCount(); //pagefile2 is the pages in the set
					System.out.println("inc ref " + PageDisk.get(i));

				}
				
				
				
				List<String> words = browser.getWords();
				Set<Long> WordSet = new HashSet<Long>();  
				
				System.out.println("words " + words);
					
				
				for( String word : words ) { 
					
					if( !wordToIndex.containsKey(word)) { 
						index = indexWord(word); 
						//q.offer(index);
						
					}
					else { 
						index = wordToIndex.get(word);
					}
					
					if(!WordSet.contains(index)) { 
						
						WordSet.add(index);
						wordDisk.get(index).add(pageIndex);  	
						System.out.println("add page " + index + "(" + word + ")" + wordDisk.get(index));
						
					}
				}



			}
		}

		System.out.println("pageDisk\n" + PageDisk);
		System.out.println("urlToIndex\n" + URLtoIndex);
		System.out.println("wordDisk\n" + wordDisk);
		System.out.println("wordToIndex\n" + wordToIndex);

		URLtoIndex.write(PageDisk);
		wordToIndex.write(wordDisk); 

	}
	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		return new String[0]; 

	} 

	Long indexPage (String URL) { //stores page

		long index = PageDisk.newFile(); //index of newly created page file 
		PageFile pagefile = new PageFile(index, URL); 
		PageDisk.put(index, pagefile); //puts page in the disk
		URLtoIndex.put(URL, index); //puts url into trie 

		//System.out.println("gather" + pagefile.url);
		System.out.println("indexing page " + pagefile);


		return index; 


	}

	Long indexWord( String word ) { 
		//wordDisk wordToIndex
		//So for each word, we will have a list of the indices of web pages which contain it.

		long index = wordDisk.newFile();
		ArrayList<Long> wordList = new ArrayList<Long>(); 
		
		wordDisk.put(index, wordList);
		wordToIndex.put(word, index);
		
		System.out.println("indexing word " + index + "(" + word + ")[]");
		return index; 
		
		
		//wordToIndex.put(containsKey, value)
		//PageFile pagefile = new PageFile(containsWord, )

	}




}
