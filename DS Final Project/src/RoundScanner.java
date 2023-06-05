import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RoundScanner {
	File rounds;
	Scanner s;
	
	public RoundScanner(String textFileName) {
		try {
			rounds = new File(textFileName);
			s = new Scanner(rounds);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Bloon> getNextRoundBloonArray(){
		ArrayList<Bloon> res = new ArrayList<Bloon>();
		String line = s.nextLine();
		
		for(int i = 0; i < line.length(); i++) {
			int bloonLayer = Integer.parseInt(line.substring(i, i+1));
			res.add(new Bloon(bloonLayer));
		}
		
		return res;
	}
	
	public void resetGame() {
		try {
			s = new Scanner(rounds);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
