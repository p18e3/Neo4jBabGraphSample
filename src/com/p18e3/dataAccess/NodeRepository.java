package com.p18e3.dataAccess;

import com.p18e3.models.Node;
import org.neo4j.driver.v1.Session;

import java.util.List;
import java.util.Optional;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by P.Eberle on 14.02.2017.
 */
public class NodeRepository extends BaseRepository<Node> {

    private String createStatement = "CREATE (a:Node { id: {id}, lat: {lat}, lon: {lon} });";

    @Override
    public boolean save(Node node) {
        Session session = getSession();
        session.run(createStatement,
                parameters(
                        "id", node.getId(),
                        "lat", Double.parseDouble(node.getData().get(0).getValue()),
                        "lon", Double.parseDouble(node.getData().get(1).getValue())));

        closeSession(session);
        return false;
    }

    @Override
    public Optional<Node> get(int id) {
        return null;
    }

    @Override
    public List<Node> getAll() {
        return null;
    }

    @Override
    public boolean delete(Node node) {
        return false;
    }
}