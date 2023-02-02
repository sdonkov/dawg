package com.github.sdonkov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PrefixTree<T extends CharSequence> implements Collection<T> {

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

    public boolean contains(T word) {
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

    @Override
    public int size() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        int count = node.isEndOfWord() ? 1 : 0;
        for (Node child : node.edges) {
            count += countNodes(child);
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof CharSequence)) {
            return false;
        }
        CharSequence word = (CharSequence) o;
        TraverseResult traverseResult = traverse((T) word);
        if (traverseResult.index == word.length() && traverseResult.node.isEndOfWord()) {
            LOGGER.trace("Tree contains this word - {}", word);
            return true;
        }
        LOGGER.trace("Tree doesn't contain this word - {}", word);
        return false;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final ArrayList<T> words = new ArrayList<>();
            private int index = 0;

            {
                collectWords(root, new StringBuilder());
            }

            private void collectWords(Node node, StringBuilder word) {
                if (node.isEndOfWord()) {
                    words.add((T) word.toString());
                }
                for (Node edge : node.edges) {
                    word.append(edge.value);
                    collectWords(edge, word);
                    word.deleteCharAt(word.length() - 1);
                }
            }

            @Override
            public boolean hasNext() {
                return index < words.size();
            }

            @Override
            public T next() {
                if (index >= words.size()) {
                    throw new NoSuchElementException();
                }
                return words.get(index++);
            }

            @Override
            public void remove() {
                if (index <= 0) {
                    throw new IllegalStateException();
                }
                PrefixTree.this.remove(words.remove(--index));
            }
        };
    }

    @Override
    public Object[] toArray() {
        ArrayList<String> words = new ArrayList<>();
        collectWords(root, new StringBuilder(), words);
        return words.toArray();
    }

    private void collectWords(Node node, StringBuilder prefix, ArrayList<String> words) {
        if (node.isEndOfWord()) {
            words.add(prefix.toString());
        }
        for (Node edge : node.edges) {
            prefix.append(edge.getValue());
            collectWords(edge, prefix, words);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        ArrayList<String> words = new ArrayList<>();
        collectWords(root, new StringBuilder(), words);
        if (a.length < size()) {
            return (T1[]) Arrays.copyOf(words.toArray(), words.size(), a.getClass());
        }
        System.arraycopy(words.toArray(), 0, a, 0, size());
        if (a.length > size()) {
            a[size()] = null;
        }
        return a;
    }

    public boolean add(T word) {
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
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof CharSequence)) {
            return false;
        }
        CharSequence word = (CharSequence) o;
        TraverseResult traverseResult = traverse((T) word);
        if (traverseResult.index == word.length() && traverseResult.node.isEndOfWord()) {
            traverseResult.node.endOfWord = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Iterator<T> it = iterator();
        boolean changed = false;
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    private TraverseResult traverse(T word) {
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