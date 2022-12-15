import java.util.ArrayList;

public class PrefixTree {

    private final Node root;

    PrefixTree(Node root){
        this.root = root;
    }

    private static class TraverseResult {
        private Node node;
        private int index;

    }

    public boolean contains(CharSequence word){
        TraverseResult helper = traverse(word);
        if(helper.index == -1){
            System.out.println("Tree contains this word - " + word);
            return true;
        }else{
            System.out.println("Tree doesn't contain this word - " + word);
            return false;
        }
    }

    public boolean add(CharSequence word){
        TraverseResult helper = traverse(word);
        Node currentNode = helper.node;
        if(helper.index == -1){
            System.out.println("Word has been added already. " + word);
            return false;
        }
        if(word.length() == 1){
            currentNode.setFinal(true);
            return true;
        }
        for (int i = helper.index; i < word.length(); i++) {
            Node nextNode = new Node(word.charAt(i));
            Edge edge = new Edge(nextNode);
            currentNode.addEdge(edge);
            currentNode = nextNode;
            }
            currentNode.setFinal(true);
            return true;
    }

    private TraverseResult traverse(CharSequence word){
        TraverseResult traverseResult = new TraverseResult();
        traverseResult.node = root;
        for (traverseResult.index = 0; traverseResult.index < word.length(); traverseResult.index++) {
            boolean hasEdge = false;
            ArrayList<Edge> nodeEdges = traverseResult.node.getEdges();
            for (Edge nodeEdge : nodeEdges) {
                if(nodeEdge.getNode().getValue() == word.charAt(traverseResult.index)){
                    traverseResult.node = nodeEdge.getNode();
                    hasEdge = true;
                    break;
                }
            }
            if(!hasEdge){
                return traverseResult;
            }
        }
        if(!traverseResult.node.isFinal()){
            traverseResult.index = word.length() - 1;
            return traverseResult;
        }
        traverseResult.index = -1;
        return traverseResult;
    }
}
