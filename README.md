![💻DAWG](https://user-images.githubusercontent.com/102171106/214369779-21e03682-825a-441a-96ea-a7a4af98b61b.png)

![GitHub last commit](https://img.shields.io/github/last-commit/sdonkov/dawg) ![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/sdonkov/dawg/test.yaml) ![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/sdonkov/dawg) ![GitHub closed issues](https://img.shields.io/github/issues-closed/sdonkov/dawg)

# DAWG

A Directed Acyclic Word Graph (DAWG) is a data structure that is used to store a set of words in a compact and
efficient manner. It is similar to a Trie, which is a tree-like data structure that is used to store a collection of
words. However, a DAWG is a more efficient version of a Trie, because it reduces the amount of memory used by
eliminating duplicate words and sharing common suffixes among words. The DAWG is based on the idea of a directed acyclic
graph, which is a graph that does not have any cycles and has a direction for each edge, each edge is labeled with a
letter
or symbol, and each vertex has at most one outgoing edge for each possible letter or symbol. This structure is
particularly
useful for tasks such as spell-checking, autocompletion, and searching large sets of words.

## GOAL
I'm researching data structures to improve my programming skills

## Installation

- Install Maven
  - [Install Maven on Windows](https://www.baeldung.com/install-maven-on-windows-linux-mac#installing-maven-on-windows)
  - [Install Maven on Linux](https://www.baeldung.com/install-maven-on-windows-linux-mac#installing-maven-on-linux)
  - [Install Maven on Mac OSX](https://www.baeldung.com/install-maven-on-windows-linux-mac#installing-maven-on-mac-os-x)
- Run unit tests 
```
mvn test
```
- Run unit tests and performance tests
```
mvn verify
```

