import java.util.ArrayList;

public class Node {

    private boolean isFinal;
    private final char value;
    private ArrayList<Edge> edges = new ArrayList<>();

    Node(char value) {
        this.value = value;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public char getValue() {
        return value;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public String toString() {
        return "Node - " +
                "(" +
                edges.size() + value + (isFinal ? "." : "") +
                ')';
    }
}