package cvut.fel.ds2.brabezd1;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabasePopulator {

    private GraphDatabaseService db;

    public DatabasePopulator(GraphDatabaseService db) {
        this.db = db;
    }

    public Node addLorry(String driver, String spz) {

        Node node = db.createNode();
        node.setProperty("driver", driver);
        node.setProperty("spz", spz);
        node.addLabel(Label.label("lorry"));

        return node;

    }

    public Node addFood(String name, int count) {

        Node node = db.createNode();
        node.setProperty("name", name);
        node.setProperty("count", count);
        node.addLabel(Label.label("food"));

        return node;

    }

    public Node addDelivery(long deliverUntil, String address) {

        Node node = db.createNode();
        SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
        node.setProperty("createdAt", sdf.format(new Date()));
        node.setProperty("deliverUntil", deliverUntil);
        node.setProperty("address", address);
        node.addLabel(Label.label("delivery"));

        return node;
    }

}
