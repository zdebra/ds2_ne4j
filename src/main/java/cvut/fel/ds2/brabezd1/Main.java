package cvut.fel.ds2.brabezd1;


import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;

import java.io.File;
import java.text.SimpleDateFormat;

public class Main {


    public static void main(String[] args) {

        GraphDatabaseService db = new GraphDatabaseFactory()
                .newEmbeddedDatabase(new File("MyNeo4jDB"));

        DatabasePopulator databasePopulator = new DatabasePopulator(db);
        Node lorry[] = new Node[4];
        Node food[] = new Node[11];
        Node delivery[] = new Node[4];

        Transaction tx = db.beginTx();

        try {
            lorry[0] = databasePopulator.addLorry("vytick", "A3B12345");
            lorry[1] = databasePopulator.addLorry("matej", "3U112345");
            lorry[2] = databasePopulator.addLorry("ryan", "T2X12345");
            lorry[3] = databasePopulator.addLorry("vilik", "ABC123356");

            food[0] = databasePopulator.addFood("onion", 10);
            food[1] = databasePopulator.addFood("mango", 3);
            food[2] = databasePopulator.addFood("strawberry", 400);
            food[3] = databasePopulator.addFood("apple", 40);
            food[4] = databasePopulator.addFood("peach", 1);
            food[5] = databasePopulator.addFood("apricot", 3);
            food[6] = databasePopulator.addFood("cherry", 30000);
            food[7] = databasePopulator.addFood("avocado", 8);
            food[8] = databasePopulator.addFood("banana", 89);
            food[9] = databasePopulator.addFood("bean", 398);
            food[10] = databasePopulator.addFood("broccoli", 42);

            delivery[0] = databasePopulator.addDelivery("1.5.2017", "Some 123, Praha 6, 16000");
            delivery[1] = databasePopulator.addDelivery("4.1.2017", "Think 1223, Praha 6, 16000");
            delivery[2] = databasePopulator.addDelivery("10.3.2017", "Else 123, Praha 6, 16000");
            delivery[3] = databasePopulator.addDelivery("12.3.2017", "Different 123, Praha 6, 16000");

            delivery[0].createRelationshipTo(food[0], MyType.CONTAINS);
            delivery[0].createRelationshipTo(food[1], MyType.CONTAINS);
            delivery[1].createRelationshipTo(food[2], MyType.CONTAINS);
            delivery[1].createRelationshipTo(food[3], MyType.CONTAINS);
            delivery[1].createRelationshipTo(food[4], MyType.CONTAINS);
            delivery[2].createRelationshipTo(food[5], MyType.CONTAINS);
            delivery[3].createRelationshipTo(food[6], MyType.CONTAINS);
            delivery[3].createRelationshipTo(food[7], MyType.CONTAINS);
            delivery[3].createRelationshipTo(food[8], MyType.CONTAINS);
            delivery[3].createRelationshipTo(food[9], MyType.CONTAINS);
            delivery[3].createRelationshipTo(food[10], MyType.CONTAINS);

            lorry[0].createRelationshipTo(delivery[0], MyType.DELIVERS);
            lorry[1].createRelationshipTo(delivery[1], MyType.DELIVERS);
            lorry[2].createRelationshipTo(delivery[2], MyType.DELIVERS);
            lorry[3].createRelationshipTo(delivery[3], MyType.DELIVERS);

            TraversalDescription td = db.traversalDescription()
                    .breadthFirst()
                    .relationships(MyType.DELIVERS, Direction.BOTH)
                    .evaluator(Evaluators.excludeStartPosition())
                    .uniqueness(Uniqueness.NODE_GLOBAL);
            Traverser t = td.traverse(lorry[0]);
            for (Path p : t) {
                System.out.println(p.endNode().getProperty("address"));
            }

            tx.success();
        } catch (Exception e) {
            tx.failure();
            System.err.println(e.getStackTrace());
        } finally {
            tx.close();
        }





    }


}
