package prog07;

import prog02.UserInterface;
import prog05.ListStack;
import prog05.StackInt;
import prog06.LinkedQueue;

import java.io.*;
import java.util.*;
import prog02.GUI;

public class WordStep {
	static UserInterface ui = new GUI("WordStep");
	static ArrayList<String> list = new ArrayList<String>();

	static public void main(String[] args) {
		WordStep game = new WordStep();
		String filename = ui.getInfo("name of word file");

		// problem

		game.loadWords(filename);

		String start = ui.getInfo("Start word: ");
		if (find(list, start) == -1) {
			ui.sendMessage(start + " is not a word");
			return;
		}

		String target = ui.getInfo("End word: ");
		if (find(list, target) == -1) {

			ui.sendMessage(target + " is not a word");
			return;
		}

		System.out.println(start);
		System.out.println(target);
		System.out.println(numDifferent(start, target));

		String[] commands = { "Human plays.", "Computer plays." };
		int c = ui.getCommand(commands);
		if (c == -1) {
			return;
		}
		if (c == 0) { // human plays
			game.play(start, target, list);
		} else if (c == 1) { // computer plays
			game.solve(start, target, list);
		}

	}

	// human
	void play(String start, String target, ArrayList<String> list) {

		while (true) { // until the return occurs

			ui.sendMessage("current word is: " + start + "\n the target word is: " + target);
			String input = ui.getInfo("the next word is: ");
			if (input == null) {
				break;
			}

			if (offBy1(start, input) == false) { // check off by 1
				ui.sendMessage("The word must differentiate by one letter. ");

			} else if (find(list, input) == -1) {
				ui.sendMessage("This is not a real word");

			} else {
				start = input;
			}

			if (start.equals(target)) {
				ui.sendMessage("You win!");
				return;
			}

		}

	}

	// computer
	void solve(String start, String target, ArrayList<String> list) {

		// String start, String target
		int parents[] = new int[list.size()];

		// initialize all entries in parents to -1
		for (int i = 0; i < parents.length; i++) {
			parents[i] = -1;
		}

		// create queue of integer
		

		IndexComparator compare = new IndexComparator(parents, target);

		Queue<Integer> q = new PriorityQueue<Integer>(compare);
		//Queue<Integer> q = new Heap<Integer>(compare);
		//Queue<Integer> q = new LinkedQueue<Integer>();
		//Queue<Integer> q = new LinkedList<Integer>();

		// q.offer(find(list, start));
		int startIndex = list.indexOf(start);
		q.add(startIndex);

		StringBuilder sb = new StringBuilder();
		StackInt<String> stack = new ListStack<String>();

		int times = 0;

		// breadth-first search
		while (!q.isEmpty()) {

			System.out.println("Hi you got here");
			
			int currentIndex = q.poll();
			String current = list.get(currentIndex); // updates current with head of queue
			times++;
			System.out.println(current);
			
			
			for (int i = 0; i < list.size(); i++) { // checks the word you are trying to put in

				// parents[i]==-1 checks if it is in the same ring or earlier ones
				// it is important that it doesn't have a parent because you want to find the
				// shortest path
				//System.out.println("here");

				if (offBy1(current, list.get(i)) == true && !list.get(i).equals(start) && (parents[i] == -1 ||
						numSteps(parents,currentIndex)+1 < numSteps(parents,i))) {

					System.out.println("off by 1 works");
					
					if(parents[i] != -1) { 
						q.remove(i);
								
					}
					parents[i] = find(list, current); // updates parents with index of current
					q.offer(i); // adds the neighbors to queue
					
					

					if (list.get(i).equals(target)) {
						System.out.println("if list equals target");

						while (i != startIndex) {
							System.out.println("while i does not equal start");
							
							stack.push(list.get(i));
							// sb.append(list.get(i) + "\n");
							i = parents[i];
						}

						System.out.println("boutta push");
						stack.push(list.get(i));

						while (!stack.empty()) {
							System.out.println("while stack is not empty");
							sb.append(stack.pop() + "\n");

						}
						

						System.out.println("send message");

						ui.sendMessage(sb.toString());
						ui.sendMessage("you win");
						ui.sendMessage("Gone through poll " + times + " times.");
						return;
					}
				}
			}

		}

	}

	// returns true if the Strings are same length, and differ by one character
	static boolean offBy1(String start, String input) {
		// System.out.println(start.compareTo(input));

		if (start.length() != input.length()) {

			return false;
		} else {
			int differ = 0;
			for (int i = 0; i < start.length(); i++) {
				if (start.charAt(i) != input.charAt(i)) {
					differ++;
				}

			}
			if (differ == 1) {
				return true;

			} else {
				return false;
			}

		}
	}

	void loadWords(String filename) {
		try {
			Scanner s = new Scanner(new File(filename));
			while (s.hasNextLine()) {
				list.add(s.nextLine());

			}
			s.close();
		} catch (FileNotFoundException e) {
			ui.sendMessage("File not found");
		}

	}

	// words is the file name
	// find word in the list of words
	static int find(ArrayList<String> list, String word) {

		for (int i = 0; i < list.size(); i++) {
			if (word.equals(list.get(i))) {
				return i; // where the word is in the list
			}
		}
		return -1; // if the word is not in the list
	}

	// number of steps to go back to the start word index (-1)
	static int numSteps(int[] parents, int i) {
		int count = 0;

		while (parents[i] != -1) {
			i = parents[i];
			count++;
		}
		return count;
	}

	// tells you the number of letters different between two words
	static int numDifferent(String word1, String word2) {
		int differ = 0;
		for (int i = 0; i < word1.length(); i++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				differ++;
			}

		}
		return differ;
	}

	class IndexComparator implements Comparator<Integer> {

		// returns the number of steps the index to the start index +
		// the # of differences between the word at that index to the target word

		int parents[];
		String target;

		public IndexComparator(int[] parents, String target) {
			this.parents = parents;
			this.target = target;
		}

		int sumNums(int i) {
			return numSteps(parents, i) + numDifferent(list.get(i), target);
		}

		public int compare(Integer indexA, Integer indexB) {

			// return sumNums(indexA) - sumNums(indexB);

			if (sumNums(indexA) < sumNums(indexB)) {
				return -1;
			}
			if (sumNums(indexA) > sumNums(indexB)) {
				return 1;
			} else {
				return 0;
			}

		}

	}
}
