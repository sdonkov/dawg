import java.util.ArrayList;
import java.util.HashSet;

public class Edge {

    private Node reference;

    public Node getReferences() {
        return reference;
    }

    public void setReference(Node reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "reference=" + reference +
                '}';
    }
}
