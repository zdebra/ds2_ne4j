package cvut.fel.ds2.brabezd1;


import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;

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
            lorry[1].createRelationshipTo(delivery[2], MyType.DELIVERS);
            lorry[3].createRelationshipTo(delivery[3], MyType.DELIVERS);

            // retrieve to which addresses lorry delivering
            TraversalDescription deliveryAdressesOfLorry = db.traversalDescription()
                    .breadthFirst()
                    .relationships(MyType.DELIVERS, Direction.BOTH)
                    .evaluator(Evaluators.excludeStartPosition())
                    .uniqueness(Uniqueness.NODE_GLOBAL);
            Traverser t = deliveryAdressesOfLorry.traverse(lorry[1]);
            for (Path p : t) {
                System.out.println(p.endNode().getProperty("address"));
            }

            // retrieve what delivery contains
            TraversalDescription foodsInPackage = db.traversalDescription()
                    .breadthFirst()
                    .relationships(MyType.CONTAINS, Direction.BOTH)
                    .evaluator(Evaluators.excludeStartPosition())
                    .uniqueness(Uniqueness.NODE_GLOBAL);
            Traverser t2 = foodsInPackage.traverse(delivery[3]);
            for (Path p : t2) {
                System.out.println(p.endNode().getProperty("name") + ", " + p.endNode().getProperty("count"));
            }

            // finds all vegetables with count > 100
            Result r1 = db.execute("MATCH (f:food) WHERE f.count > 100 RETURN f");
            while (r1.hasNext()) {
                Map<String, Object> row = r1.next();
                Node f = (Node) row.get("f");
                System.out.println("name: "+f.getProperty("name") + ", count: " + f.getProperty("count"));
            }


            // finds all food lorry is delivering
            Result r2 = db.execute("MATCH (l:lorry {driver:'vytick'})-->(d:delivery)-->(f:food) RETURN l.driver AS driver, f.name AS name");
            while (r2.hasNext()) {
                Map<String, Object> row = r2.next();
                System.out.println("driver: "+row.get("driver") + ", food: " + row.get("name"));
            }

            tx.success();
        } catch (Exception e) {
            tx.failure();
            System.err.println(e.getMessage());
        } finally {
            tx.close();
        }





    }


}
