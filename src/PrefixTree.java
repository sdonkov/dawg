import java.util.ArrayList;
import java.util.Optional;

public class PrefixTree {

    private final Node root;

    PrefixTree() {
        this.root = new Node('\u0000');
    }

    private static class TraverseResult {
        private Node node;
        private int index;

    }

    public boolean contains(CharSequence word) {
        TraverseResult traverseResult = traverse(word);
        if (traverseResult.index == word.length() && traverseResult.node.isFinal()) {
            System.out.println("Tree contains this word - " + word);
            return true;
        }
        System.out.println("Tree doesn't contain this word - " + word);
        return false;
    }

    public boolean add(CharSequence word) {
        TraverseResult traverseResult = traverse(word);
        Node currentNode = traverseResult.node;
        if (traverseResult.index == word.length() && traverseResult.node.isFinal()) {
            System.out.println("Word has been added already. " + word);
            return false;
        }
        for (int i = traverseResult.index; i < word.length(); i++) {
            Node nextNode = new Node(word.charAt(i));
            Edge edge = new Edge(nextNode);
            currentNode.addEdge(edge);
            currentNode = nextNode;
        }
        currentNode.setFinal(true);
        return true;
    }

    private TraverseResult traverse(CharSequence word) {
        TraverseResult traverseResult = new TraverseResult();
        traverseResult.node = root;
        for (; traverseResult.index < word.length(); traverseResult.index++) {
            ArrayList<Edge> nodeEdges = traverseResult.node.getEdges();

            Optional<Edge> edgeFound = nodeEdges.stream()
                    .filter(edge -> edge.getNode().getValue() == word.charAt(traverseResult.index))
                    .findFirst();

            if (edgeFound.isEmpty()) {
                return traverseResult;
            }
            traverseResult.node = edgeFound.get().getNode();
        }
        return traverseResult;
    }
}