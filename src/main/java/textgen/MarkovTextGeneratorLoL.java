package textgen;

import java.util.*;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<ListNode>();
        starter = "";
        rnGenerator = generator;
    }

    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior

        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
//        System.out.println(textString);
        gen.train(textString);
//        System.out.println(gen);
        System.out.println(gen.generateText(0));
//        String textString2 = "You say yes, I say no, " +
//                "You say stop, and I say go, go, go, " +
//                "Oh no. You say goodbye and I say hello, hello, hello, " +
//                "I don't know why you say goodbye, I say hello, hello, hello, " +
//                "I don't know why you say goodbye, I say hello. " +
//                "I say high, you say low, " +
//                "You say why, and I say I don't know. " +
//                "Oh no. " +
//                "You say goodbye and I say hello, hello, hello. " +
//                "I don't know why you say goodbye, I say hello, hello, hello, " +
//                "I don't know why you say goodbye, I say hello. " +
//                "Why, why, why, why, why, why, " +
//                "Do you say goodbye. " +
//                "Oh no. " +
//                "You say goodbye and I say hello, hello, hello. " +
//                "I don't know why you say goodbye, I say hello, hello, hello, " +
//                "I don't know why you say goodbye, I say hello. " +
//                "You say yes, I say no, " +
//                "You say stop and I say go, go, go. " +
//                "Oh, oh no. " +
//                "You say goodbye and I say hello, hello, hello. " +
//                "I don't know why you say goodbye, I say hello, hello, hello, " +
//                "I don't know why you say goodbye, I say hello, hello, hello, " +
//                "I don't know why you say goodbye, I say hello, hello, hello,";
//        System.out.println(textString2);
//        gen.retrain(textString2);
//        System.out.println(gen);
//        System.out.println(gen.generateText(20));
    }

    /**
     * Train the generator by adding the sourceText
     */
    @Override
    public void train(String sourceText) {
        if (sourceText.equals("")) {
            return;
        }
        String[] words = sourceText.split(" +");
        starter = words[0];
        String word = starter;
        for (int i = 1; i < words.length; i++) {
            String nextWord = words[i];
            processWord(word, nextWord);
            word = nextWord;
        }
        processWord(word, starter);
    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        if (numWords < 1) {
            return "";
        }
        String currentWord = starter;
        StringBuilder output = new StringBuilder();
        output.append(currentWord);
        while (--numWords > 0) {
        	ListNode node = findNode(currentWord);
        	if (node != null) {
        		currentWord = node.getRandomNextWord(rnGenerator);
        		output.append(" ").append(currentWord);
			}
		}
        return output.toString();
    }

    // Can be helpful for debugging
    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
    }

    /**
     * Search for existing node in wordList by word
     *
     * @param word - word field in ListNode to search
     * @return reference to node in wordList or null if not exist
     */
    private ListNode findNode(String word) {
        for (ListNode node : wordList) {
            if (node.getWord().equals(word)) {
                return node;
            }
        }
        return null;
    }

    private void addNode(String word, String nextWord) {
        ListNode node = new ListNode(word);
        node.addNextWord(nextWord);
        wordList.add(node);
    }

    private void processWord(String word, String nextWord) {
        ListNode node = findNode(word);
        if (node != null) {
            node.addNextWord(nextWord);
        } else {
            addNode(word, nextWord);
        }
    }
}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    public String getWord() {
        return word;
    }

    public List<String> getNextWords() {
        return nextWords;
    }

    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator) {
        return nextWords.get(generator.nextInt(nextWords.size()));
    }

    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


