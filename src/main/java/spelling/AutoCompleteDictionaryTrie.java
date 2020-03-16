package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author You
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompleteDictionaryTrie() {
        root = new TrieNode();
    }

    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should convert the
     * string to all lower case before you insert it.
     * <p>
     * This method adds a word by creating and linking the necessary trie nodes
     * into the trie, as described outlined in the videos for this week. It
     * should appropriately use existing nodes in the trie, only creating new
     * nodes when necessary. E.g. If the word "no" is already in the trie,
     * then adding the word "now" would add only one additional node
     * (for the 'w').
     *
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word) {
        return addWord(word.toLowerCase(), 0, root);
    }

    public boolean addWord(String word, int index, TrieNode node) {
        if (word.isEmpty() || (node.endsWord() && node.getText().equals(word))) {
            return false;
        } else if (node.getText().equals(word)) {
            node.setEndsWord(true);
            size++;
            return true;
        }
        node.insert(word.charAt(index));
        return addWord(word, index + 1, node.getChild(word.charAt(index)));

    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        return size;
    }

    /**
     * Returns whether the string is a word in the trie, using the algorithm
     * described in the videos for this week.
     */
    @Override
    public boolean isWord(String s) {
        String lowercase = s.toLowerCase();
        TrieNode node = findNode(lowercase, 0, root);
        return node != null && node.endsWord();
    }

    /**
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions
     * of the prefix string. All legal completions must be valid words in the
     * dictionary. If the prefix itself is a valid word, it is included
     * in the list of returned words.
     * <p>
     * The list of completions must contain
     * all of the shortest completions, but when there are ties, it may break
     * them in any order. For example, if there the prefix string is "ste" and
     * only the words "step", "stem", "stew", "steer" and "steep" are in the
     * dictionary, when the user asks for 4 completions, the list must include
     * "step", "stem" and "stew", but may include either the word
     * "steer" or "steep".
     * <p>
     * If this string prefix is not in the trie, it returns an empty list.
     *
     * @param prefix         The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {

        prefix = prefix.toLowerCase();

        List<String> completions = new LinkedList<>();

        TrieNode node = findNode(prefix, 0, root);

        if (node != null) {
            List<TrieNode> queue = new LinkedList<>();
            queue.add(node);
            while (numCompletions != 0 && !queue.isEmpty()) {

                node = queue.remove(0);

                if (node.endsWord()) {
                    completions.add(node.getText());
                    numCompletions--;
                }

                Set<Character> validNextChars = node.getValidNextCharacters();

                for (Character c : validNextChars) {
                    queue.add(node.getChild(c));
                }

            }

        }
        return completions;
    }

    private TrieNode findNode(String s, int index, TrieNode node) {

        if (node.getText().equals(s)) {
            return node;
        }
        if (index == s.length() || !node.getValidNextCharacters().contains(s.charAt(index))) {
            return null;
        }
        return findNode(s, index + 1, node.getChild(s.charAt(index)));
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next = null;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }
}