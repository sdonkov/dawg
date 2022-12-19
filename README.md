Try to implement DAWG (Directed Acyclic Word Graph) also known as DAFSA is a library for computing Deterministic Acyclic Finite State Automata.
DAFSA are data structures derived from tries that allow to represent a set of sequences that takes the form of a directed acyclic graph with a single source vertex
(a vertex with no incoming edges), in which each edge of the graph is labeled by a letter or symbol, and in which each vertex has at most one outgoing edge for each possible letter or symbol.
The strings represented by the DAFSA are formed by the symbols on paths in the graph from the source vertex to any sink vertex (a vertex with no outgoing edges).
In fact, a deterministic finite state automaton is acyclic if and only if it recognizes a finite set of strings.