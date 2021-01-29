import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * Defines what words appear in the dictionary and the words adjacent to the chosen word.
 *
 * @author ketchaam.
 *         Created Mar 20, 2019.
 */
public class Links implements LinksInterface {
	
	private HashMap<String, Set<String>> data;

	public Links(String textFile) throws FileNotFoundException {
		this.data = new HashMap<String, Set<String>>();
		BufferedReader reader = new BufferedReader(new FileReader(textFile));
		try {
			for(String line; (line = reader.readLine()) != null;) {
				HashSet<String> lineValue = new HashSet<String>();
				this.data.put(line, lineValue);
				for(String key : this.data.keySet()) {
					if(this.isOneLeterAway(line, key)) {
						this.data.get(line).add(key);
						this.data.get(key).add(line);
					}
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public Set<String> getCandidates(String word) {
		if(!this.exists(word)) {
			return null;
		}
		if(this.data.get(word).isEmpty()) {
			return null;
		}
		return this.data.get(word);
	}

	@Override
	public boolean exists(String word) {
		if(this.data.containsKey(word)) {
			return true;
		}
		return false;
	}
	
	private boolean isOneLeterAway(String word, String otherWord) {
		int count = 0;
		if(otherWord.length() != word.length()) {
			return false;
		}
		for(int i = 0; i < word.length(); i++) {
			if(word.charAt(i) != otherWord.charAt(i)) {
				count++;
			}
		}
		if(count != 1) {
			return false;
		}
		return true;
	}
	

}
