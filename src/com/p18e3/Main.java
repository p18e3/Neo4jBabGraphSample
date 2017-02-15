package com.p18e3;

import com.p18e3.dataAccess.DatabaseConstants;
import com.p18e3.dataAccess.EdgeRepository;
import com.p18e3.dataAccess.NodeRepository;
import com.p18e3.dataAccess.XmlImporter;
import com.p18e3.models.Graph;
import com.p18e3.models.Node;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Path;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException {

        // Read XML data.
        Graph graph = XmlImporter.ImportFromFile("C:\\Users\\p.eberle\\Desktop\\graph_bab.xml");

        // Persist the data into the Neo4j database.
        //persistDataIntoNeo4j(graph);

        // Calculate shortest path.
        Node startNode = getNodeByName(graph, "STUTTGART");
        Node endNode = getNodeByName(graph, "HAMBURG");

        System.out.printf("StartNode is %s\n", startNode.getId());
        System.out.printf("EndNode is %s\n", endNode.getId());
        System.out.printf("\n\n");
        calculateShortestPath(startNode, endNode);
    }


    private static void persistDataIntoNeo4j(Graph graph) {
        NodeRepository nodeRepository = new NodeRepository();
        EdgeRepository edgeRepository = new EdgeRepository();

        graph.getNodes().forEach(node -> {
            System.out.printf("Persisting node %s in graph database ...\n", node.getId());
            nodeRepository.save(node);
        });

        graph.getEdges().forEach(edge -> {
            System.out.printf("Persisting edge %s --> %s in graph database ...\n", edge.getSource(), edge.getTarget());
            edgeRepository.save(edge);
        });
    }

    private static void calculateShortestPath(Node startNode, Node endNode) {
        String query = "MATCH (p1:Node {id: '%s'}), (p2:Node {id: '%s'})," +
                "path = shortestpath((p1)-[:Route*]-(p2))" +
                "RETURN path";
        String enrichedQuery = String.format(query, startNode.getId(), endNode.getId());

        Driver driver = GraphDatabase.driver(DatabaseConstants.DatabaseUrl, AuthTokens.basic(DatabaseConstants.User, DatabaseConstants.Password));
        Session session = driver.session();
        StatementResult result = session.run(enrichedQuery);

        System.out.println("Route:");

        while (result.hasNext()) {
            Record record = result.next();
            Path path = record.get("path").asPath();
            path.nodes().forEach(node -> {
                System.out.println(node.get("id").asString());
            });
        }

        session.close();
        driver.close();
    }

    private static Node getNodeByName(Graph graph, String name) {
        Optional<Node> node = graph.getNodes()
                .stream()
                .filter(n -> n.getId().contains(name)).findFirst();

        return node.get();
    }
}