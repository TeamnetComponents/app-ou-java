package ro.teamnet.ou.plugin;

import ro.teamnet.neo.plugin.Neo4JType;
import ro.teamnet.neo.plugin.Neo4jResourcePropertiesPathPlugin;

public class OuNeo4jResourcePropertiesPathPlugin   implements Neo4jResourcePropertiesPathPlugin {
    @Override
    public String neo4jResourcePropertiesPath() {
        return "config/neo/ou-neo4j";
    }

    @Override
    public boolean supports(Neo4JType delimiter) {
        return delimiter==Neo4JType.RESOURCE_PROPERTIES_PATH;
    }
}