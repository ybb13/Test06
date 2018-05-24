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
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("oadata");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("User");
			System.out.println("集合 test 选择成功");

			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3. 通过游标遍历检索出的文档集合
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

		// 连接到 mongodb 服务
		MongoClient mongoClient = new MongoClient("localhost", 27017);

		// 连接到数据库
		MongoDatabase mongoDatabase = mongoClient.getDatabase("oadata");
		System.out.println("Connect to database successfully");

		MongoCollection<Document> User = mongoDatabase.getCollection(tablename);
		System.out.println("集合 " + tablename + " 选择成功");
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