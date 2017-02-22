package alda.graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
    Map<String, LinkedList<GraphEdge>> nodesAndEdges;
    int actorCount = 0;
    int titleCount = 0;
    int edges = 0;

    public Graph(String[] inputFiles){
        nodesAndEdges = new HashMap<>();

        for (String filename: inputFiles) {
            loadGraph(filename);
        }
    }

    public void addTitle(String title, String currentActor, LinkedList<GraphEdge> currentActorList){
        if(nodesAndEdges.containsKey(title)){
            LinkedList<GraphEdge> titleList = nodesAndEdges.get(title);
            currentActorList.add(new GraphEdge(currentActor, title));
            titleList.add(new GraphEdge(title, currentActor));
            edges++;
        }
        else{
            LinkedList<GraphEdge> titleList = new LinkedList<>();
            nodesAndEdges.put(title, titleList);
            currentActorList.add(new GraphEdge(currentActor, title));
            titleList.add(new GraphEdge(title, currentActor));
            titleCount++;
            edges++;
        }
    }


    public void loadGraph(String fileName){
        System.out.println("Loading input");
        long currentTime = System.currentTimeMillis();

        try {
            BaconReader reader = new BaconReader(fileName);
            BaconReader.Part part;
            StringBuilder builder = new StringBuilder();

            String currentActor = null;
            LinkedList<GraphEdge> currentActorList = null;

            while ((part = reader.getNextPart()) != null){
                if(part.type == BaconReader.PartType.NAME && currentActor != null){
                    String title = builder.toString();
                    addTitle(title, currentActor, currentActorList);
                    builder = new StringBuilder();
                }

                switch (part.type){
                    case NAME: {
                        currentActor = part.text;
                        currentActorList = new LinkedList<>();
                        nodesAndEdges.put(currentActor, currentActorList);
                        actorCount++;
                    }
                    case TITLE:{
                        String title = builder.toString();
                        addTitle(title, currentActor, currentActorList);
                        builder = new StringBuilder();
                        builder.append(part.text);
                    }
                    case YEAR:{
                        builder.append(part.text);
                    }
                    case ID:{
                        builder.append(part.text);
                    }

                    case INFO:
                }
            }
            reader.close();
            System.out.println((System.currentTimeMillis()-currentTime)/1000 + " s to load input");
            System.out.println("Number of Nodes: " + nodesAndEdges.size() + "\nEdges: " + edges);
            System.out.println("Actors: " + actorCount + "\nTitles: " + titleCount);
        } catch (FileNotFoundException e){
            System.err.println("File not found! " + fileName);
        } catch (IOException e){
            System.err.println("IOException when reading file " + fileName);
        }


    }
}
