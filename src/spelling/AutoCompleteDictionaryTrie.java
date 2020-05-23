package spelling;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import sun.security.util.Length;

//import com.sun.corba.se.impl.orbutil.graph.Node;
//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
//import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part
	 * 2), you should convert the string to all lower case before you insert it.
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes
	 * into the trie, as described outlined in the videos for this week. It
	 * should appropriately use existing nodes in the trie, only creating new
	 * nodes when necessary. E.g. If the word "no" is already in the trie, then
	 * adding the word "now" would add only one additional node (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already
	 *         exists in the dictionary.
	 */
	public boolean addWord(String word) {

		// TODO: Implement this method.
		TrieNode node = root;
		char[] chars = word.toLowerCase().toCharArray();
		boolean ins = false;
		for (char ch : chars) {
			TrieNode nodechild = node.getChild(ch);
			if (nodechild == null) {
				node = node.insert(ch);
				ins = true;
			} else if (nodechild.getText().equals(word) && !nodechild.endsWord()) {
				node = nodechild;
				ins = true;
				break;
			} else {
				node = nodechild;
			}
		}

		if (ins) {
			size++;
			node.setEndsWord(true);
		}
		return ins;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		// TODO: Implement this method
		return size;
	}

	/**
	 * Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week.
	 */
	@Override
	public boolean isWord(String s) {
		if (s.length() == 0) {
			return false;
		}
		TrieNode node = root;
		char[] chars = s.toLowerCase().toCharArray();
		for (char ch : chars) {

			TrieNode childnode = node.getChild(ch);
			if (childnode == null) {
				return false;
			}
			node = childnode;
		}
		return node.endsWord();
		// TODO: Implement this method
	}

	/**
	 * Return a list, in order of increasing (non-decreasing) word length,
	 * containing the numCompletions shortest legal completions of the prefix
	 * string. All legal completions must be valid words in the dictionary. If
	 * the prefix itself is a valid word, it is included in the list of returned
	 * words.
	 * 
	 * The list of completions must contain all of the shortest completions, but
	 * when there are ties, it may break them in any order. For example, if
	 * there the prefix string is "ste" and only the words "step", "stem",
	 * "stew", "steer" and "steep" are in the dictionary, when the user asks for
	 * 4 completions, the list must include "step", "stem" and "stew", but may
	 * include either the word "steer" or "steep".
	 * 
	 * If this string prefix is not in the trie, it returns an empty list.
	 * 
	 * @param prefix
	 *            The text to use at the word stem
	 * @param numCompletions
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to numCompletions best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		TrieNode node = root;

		List<String> queue = new LinkedList<>();
		/*
		 * if(prefix == ""){ Set<Character> validChars = node.getValidNext
		 * Characters(); prefix = validChars.iterator().next().toString(); }
		 */
		char[] chars = prefix.toLowerCase().toCharArray();
		// for alle chars i stem
		for (char ch : chars) {
			TrieNode nodechild = node.getChild(ch);
			if (nodechild == null) {
				// stem ikke findes return tom liste
				return (List<String>) new LinkedList<String>();
			} else {
				node = nodechild;
			}
		}
		// recursiv func
		if (node.endsWord() && numCompletions > 0) {
			queue.add(node.getText());
			// return getChildWords(node, queue, numCompletions);
		}
		queue = getChildWords(node, queue, numCompletions);
		queue.sort(Comparator.comparingInt(String::length));
		return queue;
	}

	private List<String> getChildWords(TrieNode node, List<String> queue, int completions) {
		// tjek children treeset for level order (breadth search)
		TreeSet<Character> validChars = new TreeSet<>(node.getValidNextCharacters());
		// ArrayList<Character> arrayList = new ArrayList<>(validChars);
		// Collection.sort(arrayList);
		for (char ch1 : validChars) {
			if (node.getChild(ch1).endsWord()) {

				if (completions == queue.size()) {
					return (List<String>) queue;
				}
				queue.add(node.getChild(ch1).getText());
			}
		}
		for (char ch1 : validChars) {
			queue = (getChildWords(node.getChild(ch1), queue, completions));
		}
		return (List<String>) queue;
	}
	// TODO: Implement this method
	// This method should implement the following algorithm:
	// 1. Find the stem in the trie. If the stem does not appear in the trie,
	// return an
	// empty list
	// 2. Once the stem is found, perform a breadth first search to generate
	// completions
	// using the following algorithm:
	// Create a queue (LinkedList) and add the node that completes the stem to
	// the back
	// of the list.
	// Create a list of completions to return (initially empty)
	// While the queue is not empty and you don't have enough completions:
	// remove the first Node from the queue
	// If it is a word, add it to the completions list
	// Add all of its child nodes to the back of the queue
	// Return the list of completions

	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText() + curr.endsWord());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}

}