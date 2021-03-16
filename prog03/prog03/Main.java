package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, int ncalls) {
    // Get the current time in nanoseconds.	  
    long start = System.nanoTime();

    // Call fib(n) ncalls times (needs a loop!).
    for(int i = 0; i< ncalls; i++) { 
      fibn = fib.fib(n); //calculates nth fib number
    } 
    // Get the current time in nanoseconds.
    long end = System.nanoTime();

    // Return the average time converted to microseconds averaged over ncalls.
    return (end - start) / 1000.0 / ncalls;
  }

  /** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time in microseconds using the time method above.
    double t = averageTime(fib, n, 1);
    
    // If the time is (equivalent to) more than a second, return it.
    if(t > Math.exp(6)) { 
    	return t; 
    	
    }


    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
    int numcalls = (int)(1000000/t);


    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }

  private static UserInterface ui = new GUI("Fibonacci experiments");

  
  
  
  
  
  
  
  
  public static void doExperiments (Fib fib) {
    System.out.println("doExperiments " + fib);
    // EXERCISES 8 and 9
    
   
  
 
    
    
  double constant = 0; 
  while (true) {
		
			 String prompt = ui.getInfo("Give an integer: ");
			    int n = Integer.parseInt(prompt);
			    fibn = fib.fib(n);
			    double time = accurateTime(fib, n);
			    
			    constant = time/ fib.O(n);
				
			
			if(constant!=0) {
				ui.sendMessage("Current running time: " + time + "\nEstimate:  " + fibn);
				// |constant * fib.O(n) - time| / time * 100  
				ui.sendMessage("n: " +n+ "\nfib(n): " +fibn+ "\nRun time: " +time+ "\nConstant: " +constant);
				
			}
			
			else{
				ui.sendMessage("n: " +n+ "\nfib(n): " +fibn+ "\nRun time: " +time);
				ui.sendMessage("Estimate and Actual time Percentage error: " + Math.abs(constant*fib.O(n)-time)/time*100 );
			}
		
	
		//Exit  
	
		}
	}

    
  

  public static void doExperiments () {
	  
	  String[] commands = {
				"ExponentialFib",
				"LinearFib",
				"LogFib",
				"ConstantFib",
				"MysteryFib",
		"Exit"};
	  int c = ui.getCommand(commands);
	  switch (c) {
		case -1:
			ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
			break;
		case 0: 
			doExperiments(new ExponentialFib());
			break; 
		case 1: 
			doExperiments(new LinearFib());
			break;
		case 2: 
			doExperiments(new LogFib());
			break;
			
		case 3: 
			doExperiments(new ConstantFib());
			break;
		case 4: 
			doExperiments(new MysteryFib());
			break;
		case 5: 
			break;
	
	
		
	}
	  
	  
    // EXERCISE 10
	 

  }

  
  
  
  
  
  
  
  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new ExponentialFib();
	//Fib efib = new LogFib();
	  
    //Fib efib = new LinearFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = averageTime(efib, n1, 1000);
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    //insert code
    // calculate ncalls =  ?
    int ncalls = (int)(1000000/time1); 
    
    // call averageTime() with ncalls 
    time1 = averageTime(efib, n1, ncalls);
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    // print results
    // call accurateTime() 
    time1 = accurateTime(efib, n1);
    // print the results 
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.O(n1);
    System.out.println("c " + c);
    
    
    
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.O(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    
    
    
    // Calculate actual running time for n2=30.
    double time2 = averageTime(efib, n2, 100);
    System.out.println("n2 " + n2 + " actual time " + time2);
  //insert code
    // calculate ncalls = ? 
    ncalls = (int)(1000000/time2);
    // call averageTime() with ncalls 
    time2 = averageTime(efib, n2, ncalls);
    // print results
    System.out.println("n1 " + n1 + " time1 " + time2);
    // call accurateTime() 
    // print the results 
    
    
    int n3 = 100;
    double time3est = c * efib.O(n3);
    System.out.println("n3 " + n3 + " estimated time " + time3est);
    // 
  }


  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    //labExperiments();
    //doExperiments(new ExponentialFib());
    

    doExperiments();
  }
}



//use constant from previous loop 
// constnat * fib.O(n)
//calculate time and fib 
//give estimate, actual, percent error 


//if the n is this 
