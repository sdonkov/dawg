import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) {

        String word1 = "abe";
        String word2 = "tab";
        String word3 = "table";
        String word4 = "a";
        ArrayList<String> words = new ArrayList<>();
        words.add(word1);
        words.add(word2);
        words.add(word3);
        words.add(word4);

        PrefixTree prefixTree = new PrefixTree();
        Node root = new Node();
        prefixTree.setRoot(root);
        boolean hasEdge = false;
        Node previousNode = new Node();

        for (String word : words) {
            for (int j = 0; j < word.length(); j++) {
                boolean hasEdgeToNextNode = false;
                char character = word.charAt(j);
                //check if root has edge with first char
                if (j == 0) {
                    ArrayList<Edge> rootEdges = prefixTree.getRoot().getEdges();
                    for (Edge rootEdge : rootEdges) {
                        if (rootEdge.getReferences().getValue() == character) {
                            System.out.println("Root already has an edge to this node");
                            hasEdge = true;
                            previousNode = rootEdge.getReferences();
                            if(word.length() == 1){
                                previousNode.setFinal(true);
                            }
                            break;
                        }
                    }
                    if(hasEdge){
                        continue;
                    }
                    Edge edge = new Edge();
                    // create new node with value the first character of the word
                    Node node = new Node();
                    node.setValue(character);
                    edge.setReference(node);
                    // add edge from root to next node
                    prefixTree.getRoot().addEdge(edge);
                    previousNode = node;
                    completeTheWord(word, previousNode, 1);
                    break;
                } else {
                    // check if node has edge to next node
                    ArrayList<Edge> edges = previousNode.getEdges();
                    for (Edge edge : edges) {
                        if(edge.getReferences().getValue() == character){
                            System.out.println("Node has an edge to the next node");
                            previousNode = edge.getReferences();
                            hasEdgeToNextNode = true;
                            break;
                        }
                    }
                    if(!hasEdgeToNextNode) {
                        completeTheWord(word, previousNode, j);
                        break;
//                        }
                    }
                }
            }
        }
        System.out.println(root.getEdges().size());
        ArrayList<Edge> edges = root.getEdges();
        System.out.println(edges.get(0).getReferences().getEdges().size());
        System.out.println(prefixTree.contains("tabl")); // false
        System.out.println(prefixTree.contains("a")); // true
        System.out.println(prefixTree.contains("ab")); // false
        System.out.println(prefixTree.contains("abe")); // true
        System.out.println(prefixTree.contains("tabe")); //false
    }

    private static void completeTheWord(String currentWord, Node previousNode, int index) {
        for (int i = index; i < currentWord.length(); i++) {
            char character = currentWord.charAt(i);
            Edge edge = new Edge();
            Node node = new Node();
            node.setValue(character);
            edge.setReference(node);
            previousNode.addEdge(edge);
            previousNode = node;
            if(currentWord.length() - 1 ==i){
                node.setFinal(true);
                break;
            }
        }
        if(currentWord.length() == 1){
            previousNode.setFinal(true);
        }
    }
}
