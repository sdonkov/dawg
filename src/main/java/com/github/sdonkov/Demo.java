package com.github.sdonkov;

public class Demo {

    public static void main(String[] args) {
    //TODO replace with a proper test
        PrefixTree prefixTree = new PrefixTree();
        prefixTree.add("axa");
        prefixTree.add("abe");
        prefixTree.add("tab");
        prefixTree.add("table");
        prefixTree.add("a");
        prefixTree.add("ayasx");
        prefixTree.add("ayasx");

        System.out.println(prefixTree.contains("tabl")); // false
        System.out.println(prefixTree.contains("table")); // true
        System.out.println(prefixTree.contains("a")); // true
        System.out.println(prefixTree.contains("ab")); // false
        System.out.println(prefixTree.contains("abe")); // true
        System.out.println(prefixTree.contains("tab")); //true
        System.out.println(prefixTree.contains("axa")); // true
        System.out.println(prefixTree.contains("ayasx"));//true
    }
}
