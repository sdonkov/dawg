import java.util.ArrayList;

public class PrefixTree {

    private Node root;

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return this.root;
    }

    private static class Helper{
        private Node node;
        private int index;

    }

    public boolean contains(CharSequence word){
        Helper helper = traverse(word);
        if(helper.index == -1){
            System.out.println("Tree contains this word - " + word);
            return true;
        }else{
            System.out.println("Tree doesn't contain this word - " + word);
            return false;
        }
    }

    public boolean add(CharSequence word){
        Helper helper = traverse(word);
        Node previousNode = helper.node;
        if(helper.index == -1){
            System.out.println("Word has been added already. " + word);
            return false;
        }
        else{
            for (int i = helper.index; i < word.length(); i++) {
                if(word.length() == 1){
                    break;
                }
                Edge edge = new Edge();
                Node node = new Node();
                node.setValue(word.charAt(i));
                edge.setReference(node);
                previousNode.addEdge(edge);
                previousNode = node;
            }
            previousNode.setFinal(true);
            return true;
        }
    }

    private Helper traverse(CharSequence word){
        Helper helper = new Helper();
        Node previousNode = root;
        for (int i = 0; i < word.length(); i++) {
            boolean hasEdge = false;
            ArrayList<Edge> nodeEdges = previousNode.getEdges();
            for (Edge nodeEdge : nodeEdges) {
                if(nodeEdge.getReferences().getValue() == word.charAt(i)){
                    previousNode = nodeEdge.getReferences();
                    hasEdge = true;
                    break;
                }
            }
            if(!hasEdge){
                helper.index = i;
                helper.node = previousNode;
                return helper;
            }
        }
        if(!previousNode.isFinal()){
            helper.index = word.length() - 1;
            helper.node = previousNode;
            return helper;
        }
        helper.index = -1;
        helper.node = previousNode;
        return helper;
    }
}
