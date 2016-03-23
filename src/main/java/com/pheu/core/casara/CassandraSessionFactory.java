package com.pheu.core.casara;

import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.collect.Iterables;

public class CassandraSessionFactory {

	protected List<String> nodes;
	protected String keyspace;

	protected Cluster cluster;
	protected Session session;

	public CassandraSessionFactory() {
		
	}

	public Session connect() {
		if (session == null) {
			if (cluster == null) {
				cluster = Cluster.builder().addContactPoints(Iterables.toArray(nodes, String.class)).build();
			}
			session = cluster.connect();
		}
		session.execute("CREATE KEYSPACE IF NOT EXISTS " + getKeyspace()
				+ " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 }");
		return session;
	}

	public void close() {
		if (session != null) {
			session.close();
		}
		if (cluster != null) {
			cluster.close();
		}
	}

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	public Cluster getCluster() {
		return cluster;
	}
	
}
