package alda.graph;

/**
 * Collections from Eclipse, to avoid unnecessary space usage from Integer and a leaner version of a HashMap.
 */

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Graph {
    private ArrayList<IntArrayList> edgesByIndex;
    private Map<String, Integer> nodeAndIndex;
    private BreadthFirstSearch bfs;
    private Map<Integer, String> indexAndNode;

    private int actorCount = 0;
    private int titleCount = 0;
    private int edges = 0;

    public Graph(String[] inputFiles){
        edgesByIndex = new ArrayList<>();
        nodeAndIndex = new UnifiedMap<>();
        indexAndNode = new UnifiedMap<>();

        for (String filename: inputFiles) {
            loadGraph(filename);
        }
    }

    public int getDegreeOfSeparation(String nameToSearchFor){
        if(!nodeAndIndex.containsKey("Bacon, Kevin (I)")){
            System.out.println("NO BACON!");
            return -1;
        }
        if(!nodeAndIndex.containsKey(nameToSearchFor)){
            System.out.println("Actor " + nameToSearchFor + " does not exist");
            return -1;
        }
        else{
            bfs = new BreadthFirstSearch(this);
            return bfs.breadthFirstSearch(nodeAndIndex.get("Bacon, Kevin (I)"), nodeAndIndex.get(nameToSearchFor));
        }
    }

    protected IntArrayList getEdgesByIndex(int index){
        return edgesByIndex.get(index);
    }
    protected String getNodeByIndex(int index){
        return indexAndNode.get(index);
    }

    private void addTitle(String title, IntArrayList currentActorList, int actorIndex){
        if(nodeAndIndex.containsKey(title)){
            int titleIndex = nodeAndIndex.get(title);
            IntArrayList titleList = edgesByIndex.get(titleIndex);
            currentActorList.add(titleIndex);
            titleList.add(actorIndex);
            edges++;
        }
        else{
            IntArrayList titleList = new IntArrayList();
            int titleIndex = actorCount + titleCount++;
            edgesByIndex.add(titleIndex, titleList);
            nodeAndIndex.put(title, titleIndex);
            indexAndNode.put(titleIndex, title);
            currentActorList.add(titleIndex);
            titleList.add(actorIndex);
            edges++;
        }
    }


    private void loadGraph(String fileName){
        System.out.println("Loading input");
        long currentTime = System.currentTimeMillis();

        try {
            BaconReader reader = new BaconReader(fileName);
            BaconReader.Part part;
            StringBuilder builder = new StringBuilder();

            //String currentActor = null;
            int currentActorIndex = -1;
            IntArrayList currentActorList = null;

            while ((part = reader.getNextPart()) != null){

                switch (part.type){
                    case NAME: {
                        if(currentActorIndex != -1 && !builder.toString().isEmpty()){
                            String title = builder.toString();
                            addTitle(title, currentActorList,currentActorIndex);
                            builder = new StringBuilder();
                        }

                        String currentActor = part.text;
                        currentActorIndex = (actorCount++) + titleCount;
                        currentActorList = new IntArrayList();
                        edgesByIndex.add(currentActorIndex, currentActorList);
                        nodeAndIndex.put(currentActor, currentActorIndex);
                        indexAndNode.put(currentActorIndex, currentActor);
                        break;
                    }
                    case TITLE:{
                        if(!builder.toString().isEmpty()){
                            String title = builder.toString();
                            addTitle(title, currentActorList,currentActorIndex);
                            builder = new StringBuilder(part.text);
                        }
                        else{
                            builder.append(part.text);
                        }

                        break;
                    }
                    case YEAR:{
                        builder.append(" ");
                        builder.append(part.text);
                        break;
                    }
                    case ID:{
                        builder.append(" ");
                        builder.append(part.text);
                        break;
                    }

                    case INFO:
                        break;
                }
            }
            reader.close();
            System.out.println((System.currentTimeMillis()-currentTime)/1000 + " s to load input");
            System.out.println("Number of Nodes: " +  nodeAndIndex.size() + "\nEdges: " + edges);
            System.out.println("Actors: " + actorCount + "\nTitles: " + titleCount);
        } catch (FileNotFoundException e){
            System.err.println("File not found! " + fileName);
        } catch (IOException e){
            System.err.println("IOException when reading file " + fileName);
        }


    }
}
