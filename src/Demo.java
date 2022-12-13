public class Demo {

    public static void main(String[] args) {

        String word1 = "table";
        String word2 = "tab";
        String word3 = "abe";

        for (int i = 0; i < word1.length(); i++) {
            Node node = new Node();
            char character = word1.charAt(i);
            node.setValue(character);
            // check if last char
            if(i == word1.length()-1){
                node.setFinal(true);
                break;
            }
            // set reference
            Edge edge = new Edge();
            Node nextNode = new Node();
            nextNode.setValue(word1.charAt(i + 1));
            edge.setReference(nextNode);
            node.addEdge(edge);
        }
        //xm
    }
}
