package com.github.sdonkov;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrefixTree {

    private final static Logger LOGGER = LogManager.getLogger(PrefixTree.class);
    private final Node root;

    PrefixTree() {
        this.root = new Node('\u0000');
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

    public boolean contains(CharSequence word) {
        TraverseResult traverseResult = traverse(word);
        if (traverseResult.index == word.length() && traverseResult.node.isEndOfWord()) {
            LOGGER.trace("Tree contains this word - {}", word);
            return true;
        }
        LOGGER.trace("Tree doesn't contain this word - {}", word);
        return false;
    }

    public boolean add(CharSequence word) {
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
        return true;
    }

    private TraverseResult traverse(CharSequence word) {
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