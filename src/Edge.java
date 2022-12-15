public class Edge {

    private Node reference;

    Edge(Node reference){
        this.reference = reference;
    }

    public Node getNode() {
        return reference;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "reference=" + reference +
                '}';
    }
}
