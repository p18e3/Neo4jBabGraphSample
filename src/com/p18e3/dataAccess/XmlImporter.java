package com.p18e3.dataAccess;

import com.p18e3.models.Graph;

import javax.xml.bind.JAXB;

/**
 * Created by P.Eberle on 14.02.2017.
 */
public class XmlImporter<T> {

    public static Graph ImportFromFile(String filePath){
        System.out.println("Unmarshalling data ...");
        Graph graph = JAXB.unmarshal(filePath,  Graph.class);
        return graph;
    }
}