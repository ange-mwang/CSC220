package prog02;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
      to talk to the user.
      @param pd The PhoneDirectory object to use
      to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
				break;
			//Add/Change Entry
			case 0:
				name = ui.getInfo("Enter a name: ");
					
				
				if(name == null || name.isEmpty()) {
					break;
				}
				number = ui.getInfo("Enter a number: ");
				if(number == null || name.isEmpty()) {
					break;
				}
			
				oldNumber = pd.addOrChangeEntry(name, number);
				//adding a new entry 
				if(oldNumber == null) {
					ui.sendMessage("A new entry "+ name + " has been added with "+number);
				}
				//changing an existing entry
				else {
					ui.sendMessage("An old entry "+ name + "has changed from " +oldNumber+ " to "+number);
				}
			
				break;
			//Look Up Entry 
			case 1:
				name = ui.getInfo("Enter name ");
				if( name == null || name.isEmpty()) {
					break;
				}
				number = pd.lookupEntry(name);
				if(number == null) {
					ui.sendMessage("unavailable");
					
				}
				else{
					ui.sendMessage(name + " has " + number);
				}
				break;
			//Remove Entry
			case 2:
				name = ui.getInfo("Enter name ");
				number = pd.removeEntry(name);
				if(name == null || name.isEmpty()) {
					break;
				}
				if(number == null) {
					ui.sendMessage(name + " is not listed in the directory");
				}
				else { 
					ui.sendMessage("removed " + name + " with number "+ number);
				}
				break;
			//Save Directory 
			case 3:
				pd.save();
				break;
			//Exit  
			case 4:
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI("Phone Directory");
		//UserInterface ui = new TestUI();
		processCommands(fn, ui, pd);
	}
}
