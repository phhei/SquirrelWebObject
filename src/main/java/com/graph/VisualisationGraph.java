package com.graph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class VisualisationGraph implements Serializable {

    private VisualisationNode[] nodes = new VisualisationNode[8];
    private VisualisationEdge[] edges = new VisualisationEdge[8];

    /**
     * Adds a {@link VisualisationNode} to the graph
     * @param uri the uri-Label of the node
     * @return the added node. If the node (that URI) was already existing, the return is {@code null}
     */
    public VisualisationNode addNode(String uri) {
        return addNode(uri, "unknown");
    }

    /**
     * Adds a {@link VisualisationNode} to the graph
     * @param uri the uri-Label of the node
     * @param ip the IP, where the URI is hosted
     * @return the added node. If the node (that URI + IP) was already existing, the return is {@code null}
     */
    public VisualisationNode addNode(String uri, String ip) {
        if(Arrays.stream(nodes).noneMatch(n -> n != null && n.getUri().equals(uri) && n.getIp().equals(ip))) {
            VisualisationNode newNode = new VisualisationNode(uri, ip);
            nodes = extendArray(nodes, newNode);
            return newNode;
        }
        return null;
    }

    /**
     * Adds a {@link VisualisationEdge} to the graph (directed)
     * @param fromURI the uri of the starting node (initiating node)
     * @param toURI the uri of the ending node (destination node)
     * @return the added edge. It's possible to have multiple edges between 2 nodes
     */
    public VisualisationEdge addEdge(String fromURI, String toURI) {
        Optional<VisualisationNode> fromNode = Arrays.stream(nodes).filter(n -> n.getUri().equals(fromURI)).findFirst();
        Optional<VisualisationNode> toNode = Arrays.stream(nodes).filter(n -> n.getUri().equals(toURI)).findFirst();

        VisualisationNode finalFromNode = fromNode.orElseGet(() -> addNode(fromURI));
        VisualisationNode finalToNode = toNode.orElseGet(() -> addNode(toURI));

        VisualisationEdge newEdge = new VisualisationEdge(finalFromNode, finalToNode);
        edges = extendArray(edges, newEdge);
        return newEdge;
    }

    /**
     * adds the object to an array. If there is no place in the array left, it will be extended about 8 places.
     * @param array the target array
     * @param object the object, that should be added
     * @return the extended array
     */
    private <T> T[] extendArray(T[] array, T object) {
        T[] cloneArray = array.clone();
        for (int i=0; i<cloneArray.length; i++) {
            if (cloneArray[i] == null) {
                cloneArray[i] = object;
                return cloneArray;
            }
        }

        // extend Array
        T[] newArray = Arrays.copyOf(array, array.length+8);
        newArray[array.length] = object;
        return newArray;
    }

    /**
     * removes all {@code null} places in the both array fields ({@link VisualisationNode} + {@link VisualisationEdge})
     */
    protected void optimizeArrays() {
        nodes = optimizeArray(nodes);
        edges = optimizeArray(edges);
    }

    /**
     * removes all {@code null} places in the array
     * @param array the array, that should be optimized
     * @return the optimized array
     */
    private <T> T[] optimizeArray(T[] array) {
        int length = (int) Arrays.stream(array).filter(Objects::nonNull).count();

        T[] newArray = Arrays.copyOf(array, length);

        int currentIndex = 0;
        for (T element : array) {
            if (element != null) {
                newArray[currentIndex] = element;
                currentIndex++;
            }
        }

        return newArray;
    }

    /**
     * for the RABBIT
     * @return the byte stream of the graph
     */
    public byte[] convertToByteStream() {
        optimizeArrays();
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(this);
            }
            return b.toByteArray();
        } catch (IOException e) {
            System.out.println("ERROR during serializing: " + e.getMessage());
            return new byte[] {};
        }
    }

    /**
     * get all edges
     * @return all edges
     */
    public VisualisationEdge[] getEdges() {
        return edges;
    }

    /**
     * get all edges, that are connected with a certain node
     * @param node the anchor node (must be {@link VisualisationNode}, not a uri)
     * @return all specified edges
     */
    public VisualisationEdge[] getEdges(VisualisationNode node) {
        return Arrays.stream(edges).filter(e -> e != null && (e.getStart() == node|| e.getEnd() == node)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll).toArray(new VisualisationEdge[0]);
    }

    /**
     * get a node with the certain URI
     * @param uri the URI
     * @return the node or {@code null}, if the node is not exiting
     */
    public VisualisationNode getNode(String uri) {
        return Arrays.stream(nodes).filter(n -> n.getUri().equals(uri)).findFirst().get();
    }
}