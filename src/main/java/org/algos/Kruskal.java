package org.algos;

import org.jgrapht.alg.util.UnionFind;
import java.util.LinkedHashSet;

/*
 * Example: Electronic circuit designs often need to make the pins of several components
 * electrically equivalent by wiring them together. To interconnect a set of n pins we can use
 * an arrangement of n-1 wires, each connecting two pins. Of such arrangements, the one that uses
 * the least amount of wire is usually the most desirable.
 * We can model such a problem with a connected, undirected graph G = (V,E), where V is set of pins,
 * E is the set of possible interconnections between pairs of pins, and for each edge (u,v) e E, we have a weight
 * w(u, v) specifying the cost (amount of wire needed) to connect u and v.
 * We then wish to make a acyclic subset T from E that connects all of the vertices with minimized total weight.
 * Since acyclic and connected -> must form a tree T that spans the graph G
 *
 * Kruskal & Prim are greedy algorithms, each step must make one of several possible choices.
 * Greedy strategy advocates making the choice that is the best at the moment.
 * But: does not generally guarantee that it will always find globally optimal solutions.
 *
 * Visit edges in ascending order of weights, add only if two disconnected
 * trees in Forest get connected.
 *
 * Set DS:
 * HashSet: stores in hash table, best-performing implementation, but no order of iteration
 * TreeSet: red-black tree, orders elements based on their values
 * LinkedHashSet: hash table with a linked list running through it, orders elements based on order
 *                in which they were inserted into the set (insertion-order), only slightly higher cost than HashSet.
 *
 * Priority Queue: objects processed based on priority.
 *  Queue still follows FIFO algorithm, but according to priority.
 *  Elements of Q are ordered according to the natural ordering, or by a comparator
 *  provided at queue construction time.
 *  - No NULL permitted
 *  - Can't create PriorityQueue of Objects that are non-comparable
 *
 * Pseudo code:
 *  A = empty set
 *  for each v element of G vertex set
 *      make-set(v)
 *  sort edges in growing order by their weights w
 *  for each (u,v) element of G edge set (chosen by growing weight)
 *      if find-set(u) = find-set(v)
 *          A = A U {(u,v)}
 *          union(u,v)
 *  return A
 *
 * Run-time: Depends on implementation of union find data structure
 *           O(E * log V)
 *              * |V| make-set operations
 *              * |E| find-set operations
 *              * |E| union operations
 * O(E*logV) using ordinary binary heaps.
 * */

public class Kruskal {

}

