package com.graph;

import java.io.Serializable;

public class VisualisationEdge implements Serializable {

    private VisualisationNode from, to;
    private int weight;

    public VisualisationEdge(VisualisationNode from, VisualisationNode to) {
        this.from = from;
        this.to = to;
        weight = 1;
    }

    public void addWeight() {
        weight++;
    }

    public VisualisationNode getStart() {
        return from;
    }

    public VisualisationNode getEnd() {
        return to;
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}
