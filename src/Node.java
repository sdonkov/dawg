import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Node {

    private boolean endOfWord;
    private final char value;
    private ArrayList<Node> edges = new ArrayList<>();

    Node(char value) {
        this.value = value;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public char getValue() {
        return value;
    }

    public Collection<Node> getEdges() {
        return Collections.unmodifiableCollection(edges);
    }

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
