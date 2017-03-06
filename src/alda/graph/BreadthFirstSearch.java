package alda.graph;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;

import java.util.*;

/**
 * A class that implements a method for a breadth first search on a graph
 * @author Ludvig Lundberg
 */

public class BreadthFirstSearch{
    private Graph graph;
    private IntHashSet marked;
    private IntIntHashMap edgeTo;
    private Queue<Integer> queue;
    private

    BreadthFirstSearch(Graph graph){
        this.graph = graph;
        marked = new IntHashSet();
        edgeTo = new IntIntHashMap();
        queue = new LinkedList<>();
    }

    /**
     * A method that searches the graph, breadth first, until all nodes have been searched or the goal is found. If the goal is found, the path to the goal and the number of edges searched is printed out.
     *
     * The method works by adding the start index to a queue and marking the index as checked, and thereafter starting a search loop
     * In each loop, the first element on the queue is pulled. If the pulled element is not the goal, the edges of the pulled indexed are iterated through.
     * Each node that is not already marked is then marked and added to the queue while saving the parent-node from which the pulled-node came. The loop then continuous.
     * If the pulled loop is the goal, the path to the node is saved to a list by iterating back the path from each node until start is reached, and this list is then then printed out.
     *
     * @param start index of the start element of the search
     * @param goal index of the goal of the search
     * @return number of nodes between start and goal / 2, which in the Kevin Bacon game is the Bacon Number of the goal. -1 is returned if no connection between the nodes is found.
     */

    public int breadthFirstSearch(int start, int goal){
        marked.add(start);
        queue.add(start);

        long numberofedgesSearched = 0;

        while(!queue.isEmpty()){
            int current = queue.poll();

            if(current == goal){
                ArrayList<String> pathTo = new ArrayList<>();

                for(int node = current ; node != start ; node = edgeTo.get(node) ){
                    pathTo.add(graph.getNodeByIndex(node));
                }

                pathTo.add(graph.getNodeByIndex(start));
                System.out.println(pathTo.toString());
                System.out.println("Number of nodes searched: " + numberofedgesSearched);
                return (pathTo.size()/2);
            }

            IntArrayList currentList = graph.getEdgesByIndex(current);
            for(int i = 0; i < currentList.size() ; i++){
                int index = currentList.get(i);
                if(!marked.contains(index)){
                    edgeTo.put(index, current);
                    marked.add(index);
                    queue.add(index);
                }
            }
            numberofedgesSearched++;
        }
        return -1;
    }

}
