package prog03;

public class LinearFib implements Fib {

	@Override
	public double fib(int n) {
		// TODO Auto-generated method stub
		int a = 0;
		int b = 1;
		int x = 0;
		
		for(int i= 0; i<n; i++) { 

			x = a+b;
			b = a; 
			a = x; 
		
		}
		
		return x;
	}

	@Override
	public double O(int n) {
		// TODO Auto-generated method stub
		return n;
	}
	
	


	

	
	
	
	
	
}
