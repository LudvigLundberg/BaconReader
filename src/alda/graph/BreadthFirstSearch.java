package alda.graph;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;

import java.util.*;

public class BreadthFirstSearch{
    Graph graph;
    IntHashSet marked;
    IntIntHashMap edgeTo;
    Queue<Integer> queue;
    ArrayList<String> pathTo;

    BreadthFirstSearch(Graph graph){
        this.graph = graph;
        marked = new IntHashSet();
        edgeTo = new IntIntHashMap();
        pathTo = new ArrayList<>();

        queue = new LinkedList<>();
    }

    public int breadthFirstSearch(int start, int goal){
        marked.add(start);
        queue.add(start);

        long numberofedgesSearched = 0;
        while(!queue.isEmpty()){
            int current = queue.poll();

            if(current == goal){
                for(int node = current ; node != start ; node = edgeTo.get(node) ){
                    pathTo.add(graph.getNodeByIndex(node));
                }
                pathTo.add(graph.getNodeByIndex(start));
                System.out.println(pathTo.toString());
                System.out.println("Number of nodes searched: " + numberofedgesSearched++);
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
