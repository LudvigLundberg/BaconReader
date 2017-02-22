package alda.graph;

/**
 * @Author Ludvig Lundberg
 */

public class GraphEdge {
    String from;
    String to;

    public GraphEdge(String from, String to){
        this.from = from; this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}
