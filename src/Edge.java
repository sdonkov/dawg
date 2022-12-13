import java.util.ArrayList;
import java.util.HashSet;

public class Edge {

    private ArrayList<Node> references = new ArrayList<>();

    public ArrayList<Node> getReferences() {
        return references;
    }

    public void addReference(Node node){
        references.add(node);
    }
}
