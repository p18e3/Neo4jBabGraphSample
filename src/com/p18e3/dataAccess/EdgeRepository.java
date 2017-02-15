package com.p18e3.dataAccess;

import com.p18e3.models.Edge;
import org.neo4j.driver.v1.Session;

import java.util.List;
import java.util.Optional;

/**
 * Created by P.Eberle on 14.02.2017.
 */
public class EdgeRepository extends BaseRepository<Edge> {
    //private String createStatement = "MATCH(a:Node {id: '{sourceId}'}), (b:Node {id: '{destinationId}'}) CREATE (b)-[:Route { distance: {distance} } ]->(a) RETURN a, b";
    private String createStatement = "MATCH(a:Node {id: '%s'}), (b:Node {id: '%s'}) CREATE (b)-[:Route { distance: %s } ]->(a) RETURN a, b";

    @Override
    public boolean save(Edge edge) {
        Session session = getSession();
        /*StatementResult statementResult = session.run(createStatement,
                parameters(
                        "sourceId", edge.getSource(),
                        "destinationId", edge.getTarget(),
                        "distance", edge.getData().get(1).getValue())
        );*/
        String statement = String.format(createStatement, edge.getSource(), edge.getTarget(), edge.getData().get(1).getValue());
        session.run(statement);

        closeSession(session);
        return true;
    }

    @Override
    public Optional<Edge> get(int id) {
        return null;
    }

    @Override
    public List<Edge> getAll() {
        return null;
    }

    @Override
    public boolean delete(Edge edge) {
        return false;
    }
}