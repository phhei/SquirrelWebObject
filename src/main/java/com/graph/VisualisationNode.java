package com.graph;

import java.awt.Color;
import java.io.Serializable;

public class VisualisationNode implements Serializable {

    private String uri;
    private String ip;
    private Color color;

    public VisualisationNode(String uri) {
        this.uri = uri;
        ip = "unknown";
        color = Color.LIGHT_GRAY;
    }

    public VisualisationNode(String uri, String ip) {
        this.uri = uri;
        this.ip = ip;
        color = Color.GREEN;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getUri() {
        return uri;
    }

    public String getIp() {
        return ip;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return uri + ((ip.equals("unknown")) ? "" : " (" + ip + ")");
    }

    public int toInt() {
        return uri.hashCode();
    }
}
