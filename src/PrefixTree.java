import java.util.ArrayList;

public class PrefixTree {

    private Node root;

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return this.root;
    }

    public boolean contains(String word){
        Node previousNode = root;

        for (int i = 0; i < word.length(); i++) {
            boolean hasEdge = false;
            ArrayList<Edge> rootEdges = root.getEdges();
            if(i ==0) {
                for (Edge rootEdge : rootEdges) {
                    if (rootEdge.getReferences().getValue() == word.charAt(i)) {
                        previousNode = rootEdge.getReferences();
                        hasEdge = true;
                        break;
                    }
                }
            }
            else{
                ArrayList<Edge> nodeEdges = previousNode.getEdges();
                for (Edge nodeEdge : nodeEdges) {
                    if(nodeEdge.getReferences().getValue() == word.charAt(i)){
                        previousNode = nodeEdge.getReferences();
                        hasEdge = true;
                        break;
                    }
                }
            }
//            if(){
            if(!hasEdge){
                return false;
            }
            if(i == word.length() - 1 && !previousNode.isFinal()){
                return false;
            }

//        }
        }
        return true;
    }
}
