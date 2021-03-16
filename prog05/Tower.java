package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Tower {
  static UserInterface ui = new GUI("Towers of Hanoi");

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == -1)
      return;
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();

    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a' (pegs[0]).
    
   
   pegs[0] = new ArrayStack<Integer>(); 
   for(int i = nDisks; i>0 ; i--) { 
	  
	   pegs[0].push(i);
	   
   }
   pegs[1] = new ArrayStack<Integer>();
   pegs[2] = new ArrayStack<Integer>();
   
    
    
    
    
    

  }

  void play () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };

    while (pegs[0].empty()== false || pegs[1].empty() == false) {
      displayPegs();
      int imove = ui.getCommand(moves);
      if (imove == -1)
        return;
      String move = moves[imove];
      int from = move.charAt(0) - 'a';
      int to = move.charAt(1) - 'a';
      move(from, to);
    }

    ui.sendMessage("You win!");
  }

  String stackToString (StackInt<Integer> peg) {
    StackInt<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s = "";

    // EXERCISE:  append the items in peg to s from bottom to top.

    while( peg.empty() == false) { 
    	//b.push(a.pop()) push and a pop 
    	helper.push(peg.pop());
    }
    
    while(helper.empty() == false) { 
    	
    		s += helper.peek()+"";
    		
    	  //System.out.println(s);
    	  peg.push(helper.pop());	
    }
    



    return s;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  void move (int from, int to) {
    // EXERCISE:  move one disk from pegs[from] to pegs[to].
    // Don't allow illegal moves:  send a warning message instead.
    // For example "Cannot place disk 2 on top of disk 1."
    // Use ui.sendMessage() to send messages.
	  
	  
	  if(pegs[from].empty()==true) { 
		  ui.sendMessage("Cannot move disk from empty stack");
	  }
	  else if(pegs[to].empty() == false && pegs[from].peek() > pegs[to].peek()) { //cant put big on top of small 
		  ui.sendMessage("Cannot place disk " + pegs[from].peek() + " on top of " + pegs[to].peek());
	  }
	  
	  else{ 
		  pegs[to].push(pegs[from].pop());
	  }






  }

  // EXERCISE:  create Goal class.
  class Goal {
	
	  
    // Data.

	  int num; //number of disks you want to move 
	  int from; // 0 = a, 1 = b, 2 = c
	  int to; 


    // Constructor of goal stack 
	  Goal(int num, int from, int to){ 
		  this.num = num; 
		  this.from = from; 
		  this.to = to; 
	  }






    public String toString () {
      String[] pegNames = { "a", "b", "c" };
      String s = "";


      if(num == 1) { 
    	  s += "move " + num + " disk from " + pegNames[from] + " to " + pegNames[to] + "\n";

      }
      else if(num > 1){ 
    	  s += "move " + num + " disks from " + pegNames[from] + " to " + pegNames[to] + "\n";
    	 
      }







      return s;
    }
  }
  


  // EXERCISE:  display contents of a stack of goals
  void displayGoals(StackInt<Goal>goals) { 
	  //uses a helper stack 
	  StackInt<Goal> helper = new LinkedStack<Goal>();
	  String s = ""; 
	 while(goals.empty()==false) { 
		 s += helper.push(goals.pop());
	 }
	 while(helper.empty()== false) { 
		//s += helper.peek()+""; 
		goals.push(helper.pop());
	 }
	 
	

	   ui.sendMessage(s);
  }


  
  void solve () {
    // EXERCISE
	  StackInt<Goal>goals = new ArrayStack<Goal>();
	  Goal y = new Goal(nDisks, 0, 2); 
	  goals.push(y);
	  displayPegs();
	  
	  
	  while(goals.empty()== false) { 
		
		  Goal next = goals.pop();
		  
		  int num = next.num; 
		  int from = next.from;
		  int to = next.to; 
		  
		  int other = 3 - from - to; 
		  
		  if(num == 1) { 
			  move(from, to);
			  displayPegs();
			  
		  }
		  else { 
			  
			  goals.push(new Goal(num-1, other, to));
			  goals.push(new Goal (1, from, to));
			  goals.push(new Goal (num-1, from, other));
		  }
		  
		  displayGoals(goals);
		  
		  
	  }


  }        
}
