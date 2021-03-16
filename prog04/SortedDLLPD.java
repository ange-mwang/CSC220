package prog04;

public class SortedDLLPD extends DLLBasedPD {
	/** Add a new entry at a location.
    @param location The location to add the new entry, null to add
    it at the end of the list
    @param name The name in the new entry
    @param number The number in the new entry
    @return the new entry
	 */
	protected DLLEntry add (DLLEntry location, String name, String number) {
		DLLEntry entry = new DLLEntry(name, number);


		// CHECK CASE : if location is passed in empty
		if( location == null){
			// if location is empty, just add entry to the linked list. As entry will be the only element, it is both the head AND the tail
			if(head == null){
				head=entry;
				tail=entry;
			}
			// if location is null, but the list is NOT empty, add entry to the end of the list. Entry becomes the new tail.
			else{
				tail.setNext(entry);
				entry.setNext(null);	
				entry.setPrevious(tail);	
				tail = entry;
			}
		}
		// CHECK CASE: if location is the first element in the linked list
		else if ( location.getPrevious() == null){
			location.setPrevious(entry);
			entry.setNext(location);
			head = entry;
		}
		else{
			DLLEntry prev =  location.getPrevious();
			prev.setNext(entry);
			entry.setNext(location);
			entry.setPrevious(prev);
			location.setPrevious(entry);
		}
		return entry; 
	}


	/** Find an entry in the directory.
    @param name The name to be found

    Add entry to head 
    entry goes through linked list until it finds the name 

    @return The entry with the same name or null if it is not there.


	 */
	protected DLLEntry find (String name) {
		// EXERCISE
		// For each entry in the directory.
		// What is the first?  What is the next?  How do you know you got them all?
		for(DLLEntry entry = head; entry!=null; entry = entry.getNext()) { 
			// If this is the entry you want

			if(entry.getName().equals(name)) { 
				// return it.
				
				return entry;

			}
			else if(entry.getName().compareTo(name)!=0){ 
				return entry.getNext();
			}
		}



		return null; // Name not found.
	}

	/** Check if a name is found at a location.
    @param location The location to check
    @param name The name to look for at that location
    @return false, if location is null or it does not have that
    name; true, otherwise.
	 */
	protected boolean found (DLLEntry location, String name) {
		if (location == null)
			return false;
		if (location.getName()!=name) { 
			return false; 

		}
		else if(!location.getName().equals(name)){ 
			return false;

		}

		return true;
	}

}
