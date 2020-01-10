package org.algos;

import java.util.PriorityQueue;
import org.jheaps.tree.FibonacciHeap;

/*
 * Start with source node r.
 * Invariant: always only one tree!
 * Manage the unvisited neighbours from current tree, sorted ascending by edge weight.
 * DS: Priority Queue
 *
 * Pseudo-code:
 *  for each u element of G.V (G's vertex set)
 *      u.key = infinity
 *      u.pi = nil
 *  r.key = 0
 *  Q = G.V
 *  while Q not empty
 *      u = extract-min(Q)
 *      for each v element of G.Adj[u]
 *          if v element of Q ^ w(u,v) < v.key
 *              v.pi = u
 *              v.key = w(u,v)
 *
 * Run-time: Depends on implementation of priority queue.
 *           O(E * log V)
 *              - |E| times adjust priority: E log V
 *              - |V| times extract-min: V log V
 *
 *  O(E+VlogV) by using Fibonacci heaps, which imporves over binary-heap implementation
 *  if |V| is much smaller than |E|.
 *
 * More on Heaps: http://www.jheaps.org/heaps/
 * Fibonacci:
 * java.util API does not contain Fibonacci heap implementation, could be because
 * they are asymptotically greater in an amortized sense (average running time for a lot of operations),
 * e.g. consider self balancing tree: 3 different costs for insert operation -> if we aggregate all costs we get the avg.
 * running time of an algorithm.
 *
 * Binomial Heap: Collections of binomial trees that are linked together where each tree is an ordered heap.
 * There are either one or zero binomial trees of order k, where k helps describe the nr. of elements a given tree can have: 2^k.
 * Similar to binary heaps but have a more specific structure and allow for efficient merging of heaps.
 *
 * Binary Heap: Heap, i.e. a tree which obeys the property that the root of any tree is greater than or equal to all its children (heap property).
 * Primary use of heaps is to implement a priority queue.
 *
 *
 *
 *
 */


public class Prim {

}
