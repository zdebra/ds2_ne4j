package cvut.fel.ds2.brabezd1;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Label;
import java.io.File;

public class Main {
    /*
    static Node lorry, food, delivery;

    private static void populateDatabse(GraphDatabaseService db) {

        lorry = db.createNode();
        lorry.setProperty("driver", "pepa");
        lorry.setProperty("spz", "3AU12345");
        lorry.addLabel(Label.label("lorry"));

        food = db.createNode();
        food.setProperty("name", "onion");
        food.setProperty("count", 5);
       // food.addLabel("");

        delivery = db.createNode();
       // delivery.

    }*/

    public static void main(String[] args) {

       /** GraphDatabaseService db = new GraphDatabaseFactory()
                .newEmbeddedDatabase(new File("MyNeo4jDB"));


        Node actor = db.createNode();
        actor.setProperty("id", "trojan");
        actor.setProperty("name", "Ivan Trojan");
        actor.setProperty("year", 1964);
        actor.addLabel(Label.label("actor"));

       // actor.createRelationshipTo(actor2, MyTypes.KNOWS);

        db.shutdown();
        **/

        System.out.println("hojda");
    }


}
