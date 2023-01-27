package com.github.sdonkov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PrefixTree extends AbstractCollection<String> {

    private final static Logger LOGGER = LogManager.getLogger(PrefixTree.class);
    private final Node root;
    private int size;
    private final ArrayList<String> data;

    PrefixTree() {
        this.root = new Node('\u0000');
        data = new ArrayList<>();
    }

    private class CustomIterator implements Iterator<String> {

        private int currentIndex;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data.get(currentIndex++);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new CustomIterator();
    }

    @Override
    public int size() {
        return size;
    }

    private static class TraverseResult {
        private Node node;
        private int index;
    }

    private static class Node {

        private boolean endOfWord;
        private final char value;
        private final ArrayList<Node> edges = new ArrayList<>();

        Node(char value) {
            this.value = value;
        }

        public boolean isEndOfWord() {
            return endOfWord;
        }

        public void setEndOfWord() {
            this.endOfWord = true;
        }

        public char getValue() {
            return value;
        }

        public Optional<Node> findChild(char value) {
            return this.edges.stream()
                    .filter(edge -> edge.getValue() == value)
                    .findFirst();
        }

        //TODO future improvement
        public void addEdge(Node node) {
            edges.add(node);
        }

        @Override
        public String toString() {
            return "Node - " +
                    "(" +
                    edges.size() + value + (endOfWord ? "." : "") +
                    ')';
        }
    }

    public boolean contains(String word) {
        if (word == null) {
            return false;
        }
        TraverseResult traverseResult = traverse(word);
        if (traverseResult.index == word.length() && traverseResult.node.isEndOfWord()) {
            LOGGER.trace("Tree contains this word - {}", word);
            return true;
        }
        LOGGER.trace("Tree doesn't contain this word - {}", word);
        return false;
    }

    public boolean add(String word) {
        if (word == null) {
            return false;
        }
        TraverseResult traverseResult = traverse(word);
        Node currentNode = traverseResult.node;
        if (traverseResult.index == word.length() && traverseResult.node.isEndOfWord()) {
            LOGGER.trace("Word has been added already. {}", word);
            return false;
        }
        for (int i = traverseResult.index; i < word.length(); i++) {
            Node nextNode = new Node(word.charAt(i));
            currentNode.addEdge(nextNode);
            currentNode = nextNode;
        }
        currentNode.setEndOfWord();
        size++;
        data.add(word);
        return true;
    }

    private TraverseResult traverse(String word) {
        TraverseResult traverseResult = new TraverseResult();
        traverseResult.node = root;
        for (; traverseResult.index < word.length(); traverseResult.index++) {
            Optional<Node> edgeFound = traverseResult.node.findChild(word.charAt(traverseResult.index));
            if (!edgeFound.isPresent()) {
                return traverseResult;
            }
            traverseResult.node = edgeFound.get();
        }
        return traverseResult;
    }
}