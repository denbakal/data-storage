package ua.challenge;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class Starter {
    public static void main(String... args) throws Exception {
        final Ignite ignite = Ignition.start("apacheignite-cassandra.xml");

        ignite.cache("CatalogCategory").loadCache(null);
        ignite.cache("CatalogGood").loadCache(null);
    }
}
