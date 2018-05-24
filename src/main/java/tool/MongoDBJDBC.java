/**
 * 
 */
package tool;

import javax.management.Query;
import javax.print.Doc;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDBJDBC {

	private MongoClient mongoClient = null;
	private MongoDatabase mongoDatabase = null;

	// private static volatile MongoDBJDBC instance = null;

	// public static MongoDBJDBC getInstance() {
	// if (instance == null) {
	// synchronized (MongoDBJDBC.class) {
	// if (instance == null) {
	// instance = new MongoDBJDBC();
	// }
	// }
	// }
	// return instance;
	// }

	public void query(String name, String value) {
		try {
			// ���ӵ� mongodb ����
			MongoClient mongoClient = new MongoClient("localhost", 27017);

			// ���ӵ����ݿ�
			MongoDatabase mongoDatabase = mongoClient.getDatabase("oadata");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("User");
			System.out.println("���� test ѡ��ɹ�");

			// ���������ĵ�
			/**
			 * 1. ��ȡ������FindIterable<Document> 2. ��ȡ�α�MongoCursor<Document> 3. ͨ���α�������������ĵ�����
			 */
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public boolean queryhave(String tablename, String name, String value) {

		// ���ӵ� mongodb ����
		MongoClient mongoClient = new MongoClient("localhost", 27017);

		// ���ӵ����ݿ�
		MongoDatabase mongoDatabase = mongoClient.getDatabase("oadata");
		System.out.println("Connect to database successfully");

		MongoCollection<Document> User = mongoDatabase.getCollection(tablename);
		System.out.println("���� " + tablename + " ѡ��ɹ�");
		BasicDBObject queryObject = new BasicDBObject("name","123");

	
		FindIterable<Document> iter = User.find(queryObject);
		if(iter.first()!=null) {
			return true;
		}
		iter.forEach(new Block<Document>() {
			@Override
			public void apply(Document t) {
				System.out.println("---------->"+t.getString("name"));

			}
		});
		return false;
	}

}