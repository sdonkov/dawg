import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) {

        String word1 = "table";
        String word2 = "tab";
        String word3 = "abe";
        ArrayList<String> words = new ArrayList<>();
        words.add(word1);
        words.add(word2);
        words.add(word3);

        Node firstNode = new Node();
        Edge firstEdge = new Edge();


        for (int i = 0; i < words.size(); i++) {
            String currentWord = words.get(i);
            for (int j = 0; j < currentWord.length(); j++) {
                Node node = new Node();
                char character = currentWord.charAt(j);
                node.setValue(character);
                //set first node reference to next one
                if (currentWord.startsWith(String.valueOf(character))) {
                    firstEdge.addReference(node);
                    firstNode.addEdge(firstEdge);
                }
                // check if last char
                if (j == currentWord.length() - 1) {
                    node.setFinal(true);
                    break;
                }
                // set reference
                Edge edge = new Edge();
                Node nextNode = new Node();
                nextNode.setValue(currentWord.charAt(j + 1));
                edge.addReference(node);
                node.addEdge(edge);
            }


        }
//        System.out.println(firstNode.getEdges().get(0).getReferences().get(0).getValue());
        firstNode.getEdges().get(0).getReferences().stream().forEach(node -> System.out.println(node.getValue()));
    }
}
