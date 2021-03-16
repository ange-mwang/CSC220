package prog11;

import java.io.File;
import java.util.Scanner;

import prog10.ChainedHashTable;
import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Jumble {
  /**
   * Sort the letters in a word.
   * @param word Input word to be sorted, like "computer".
   * @return Sorted version of word, like "cemoptru".
   */
  public static String sort (String word) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    return new String(chars);
  }

  public static void main (String[] args) {
    UserInterface ui = new GUI("Jumble Solver");
    // EXERCISE:
    // Need to change this to allow multiple words with the same key.
    Map<String, List<String>> map = new ChainedHashTable<String, List<String>>();

    Scanner in = null;
    do {
      try {
        in = new Scanner(new File(ui.getInfo("Enter word file.")));
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again.");
      }
    } while (in == null);
	    
    int n = 0;
    while (in.hasNextLine()) {
    	
      String word = in.nextLine();
      if (n++ % 1000 == 0)
        System.out.println(word + " sorted is " + sort(word));
      
      String sorted = sort(word);

      // EXERCISE
      // Comment this out.
     // map.put(sorted, word);

      if (!map.containsKey(sorted)) {
        // EXERCISE 

        // Suppose word is "dare" (the first word with a,d,e, and r).
        // Create empty list.
    	  
    	  List<String> list = map.get(sorted);  //add a new entry 

    	  if(list == null) {    //if entry doesn't exist, then create a new arraylist
    		 
    		  list = new ArrayList<String>(); 
    	  }
    	// Add entry "dare" to it.
    	  //WHERE IS THE ADD FUNCTION WTF ??? 
    	  
    	  list.add(word); 
    	  
    	  
    	  
        // key is "ader" ; value is the list ["dare"]
        // Store that value for that key.

    	  map.put(sorted, list);
    	  
    	  
      }
      else {
        // EXERCISE
        // Suppose word is "read" (another word with a,d,e and r).
        // Key is "ader" and value is ["dare","dear"]
        // Get the value.
    	  
    	  List<String> value = map.get(sorted); 

        // Add "read" to the list.

    	  value.add(word); 
    	  
        // List is now ["dare","dear","read"]
        // Update the value for that key in the map
    	  
    	  map.put(sorted, value);

      }
    }

    String jumble = ui.getInfo("Enter jumble.");
    while (jumble != null) {
      List<String> word = map.get(sort(jumble));
      if (word == null) {
        ui.sendMessage("No match for " + jumble);
      }
      else {
    	  String unjumbled = jumble + " unjumbled is " + word.get(0);
    	  for (int i = 0; i < word.size(); i++) { 
    		  unjumbled +=  ", " + word.get(i);
    	  }
    	  
    	  
    	  ui.sendMessage(unjumbled);
        //ui.sendMessage(jumble + " unjumbled is " + word);
      }
      // EXERCISE:
      // Get the list of words for that jumble.
      

      // Send a message if there is no list:
      // "No match for " + jumble
      // Send a message if there is
      // jumble + " unjumbled is " + listOfWords

      jumble = ui.getInfo("Enter jumble.");
    }

    while (true) {
      String letters = ui.getInfo("Enter letters from clues in any order.");
      if (letters == null)
        return;
      String sortedLetters = sort(letters);

      int l = 0;
      do {
        String number = ui.getInfo("How many letters in the first word?");
        try {
          l = Integer.parseInt(number);
          if (l <= 0)
            ui.sendMessage(number + " is not positive");
        } catch (Exception e) {
          ui.sendMessage(number + " is not a number");
        }
      } while (l <= 0);

      for (String key1 : map.keySet()) {
        // EXERCISE:
        // Check if key1 has the right length.
    	  
    	 if(key1.length() == 1) { 
    		 String key2 = "";
    		int i = 0;  
    		
    		for( int k = 0; k < sortedLetters.length(); k++) { // Add the letters in sortedLetters that aren't in key1 to key2.
    			if(k >= key1.length()) { 
    				key2 = key2 + sortedLetters.charAt(k); 
    				
    			}
    			else if( sortedLetters.charAt(k) == key1.charAt(k)) { 
    				break; 
    			}
    			else { 
    				key2 = key2 + sortedLetters.charAt(k); 
    			}
    		}
    		
    		   // If sortedLetters had all the letters in key1 and
            // if key2 has a list of words,
            // show the lists for key1 and key2.
    		if( i == key1.length() && map.containsKey(key2)) { 
    			ui.sendMessage(map.get(key1) + " " + map.get(key2));  
    		}
    	 }

        
     

      }
    }
  }
}

        
    

