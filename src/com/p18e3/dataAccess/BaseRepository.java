package com.p18e3.dataAccess;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import java.util.List;
import java.util.Optional;

/**
 * Created by P.Eberle on 14.02.2017.
 */
public abstract class BaseRepository<TEntity> {

    private Driver driver = GraphDatabase.driver(DatabaseConstants.DatabaseUrl, AuthTokens.basic(DatabaseConstants.User, DatabaseConstants.Password));

    protected Session getSession(){
        return driver.session();
    }

    protected void closeSession(Session session){
        session.close();
        driver.close();
    }

    public abstract boolean save(TEntity entity);

    public abstract Optional<TEntity> get(int id);

    public abstract List<TEntity> getAll();

    public abstract boolean delete(TEntity entity);
}