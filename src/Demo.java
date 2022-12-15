import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) {


        Node root = new Node('\u0000');
        PrefixTree prefixTree = new PrefixTree(root);
        prefixTree.add("axa");
        prefixTree.add("abe");
        prefixTree.add("tab");
        prefixTree.add("table");
        prefixTree.add("a");
        prefixTree.add("ayasx");
        prefixTree.add("ayasx");

        System.out.println(root.getEdges().size());
        System.out.println(root.getEdges().get(0).getNode());
        System.out.println(root.getEdges().get(0).getNode().getEdges().get(0)
                .getNode().getEdges().get(0).getNode());
        ArrayList<Edge> edges = root.getEdges();
        System.out.println(edges.get(0).getNode().getEdges().size());
        System.out.println(prefixTree.contains("tabl")); // false
        System.out.println(prefixTree.contains("table")); // true
        System.out.println(prefixTree.contains("a")); // true
        System.out.println(prefixTree.contains("ab")); // false
        System.out.println(prefixTree.contains("abe")); // true
        System.out.println(prefixTree.contains("tab")); //false
        System.out.println(prefixTree.contains("axa")); // true
        System.out.println(prefixTree.contains("ayasx"));//true
    }
}
