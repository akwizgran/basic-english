import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class BasicEnglish
{
	int bits;
	ArrayList<String> n, vi, vt, adj, adv, p, art;
	
	public BasicEnglish(int b) {
		bits = b;
		try {
			n = readFile("word-lists/basic-nouns");
			vi = readFile("word-lists/basic-verbs-i");
			vt = readFile("word-lists/basic-verbs-t");
			adj = readFile("word-lists/basic-adjectives");
			adv = readFile("word-lists/basic-adverbs");
			p = readFile("word-lists/basic-prepositions");
			art = readFile("word-lists/basic-articles");
		} catch(Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
	
	ArrayList<String> readFile(String name) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(name));
		ArrayList<String> words = new ArrayList<String>();
		String word = in.readLine();
		while(word != null) {
			words.add(word);
			word = in.readLine();
		}
		in.close();
		return words;
	}
	
	int rand(int range) {
		bits -= (int) (Math.log(range) / Math.log(2));
		return (int) (Math.random() *(range));
	}

	boolean branch() {
		if(bits > 0) return rand(2) == 0;
		return false;
	}
	
	void printWord(ArrayList<String> array) {
		System.out.print(array.get(rand(array.size())) + " ");
	}
	
	void printNounPhrase() {
		printWord(art);
		if(branch()) printWord(adj);
		printWord(n);
	}
	
	void printSentence() {
		printNounPhrase(); // Subject
		if(branch()) {
			printWord(vt); // Transitive verb
			printNounPhrase(); // Object of transitive verb
		} else {
			printWord(vi); // Intransitive verb
		}
		if(branch()) printWord(adv); // Adverb
		if(branch()) {
			printWord(p); // Preposition
			printNounPhrase(); // Object of preposition
		}
		System.out.println();
	}
	
	void printPoem() {
		while(bits > 0) printSentence();
	}
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: BasicEnglish <num_bits>");
			return;
		}
		int b = Integer.parseInt(args[0]);
		new BasicEnglish(b).printPoem();
	}
}
