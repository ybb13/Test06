package tool;  
  
import java.util.ArrayList;   
import java.util.List;  
  
import org.bson.Document;  
import org.bson.conversions.Bson;   
import org.junit.Test;   
import com.mongodb.BasicDBObject;   
import com.mongodb.MongoClient;  
import com.mongodb.MongoCredential;  
import com.mongodb.ServerAddress;  
import com.mongodb.client.FindIterable;  
import com.mongodb.client.MongoCollection;  
import com.mongodb.client.MongoCursor;  
import com.mongodb.client.MongoDatabase;   
import com.mongodb.client.model.InsertOneOptions;  
import com.mongodb.client.result.DeleteResult;  
import com.mongodb.client.result.UpdateResult;   

public class MongodbUtil {   
      
    private static MongodbUtil  mongoUtil;  
  
    private String address = "localhost";  
    private int port = 27017;  
    private String dataBase = "oadata";  
    private String collection = "User";    
      
    MongoClient mongoClient ;              
    MongoDatabase mongoDatabase ;             
    MongoCollection<Document> mongoCol ;  
  
    private void getMongoCon() {  
          
        mongoClient = new MongoClient( address , port );               
        mongoDatabase = mongoClient.getDatabase(dataBase);                 
        mongoCol = mongoDatabase.getCollection(collection);  
    }  
    //单例
    public static MongodbUtil  getInstance(String address,int port,String dataBase,String collection){  
        if (mongoUtil!=null) {  
            return mongoUtil;  
        }else {  
            return new MongodbUtil(address,port,dataBase,collection);  
        }  
  
    }   
    /** 
     * 根据地址，数据库及连接名称连接到数据库 
     * @param address 数据库连接地址 
     * @param port  数据库端口 
     * @param dataBase 实例名 
     * @param collection 连接名 
     */  
    public MongodbUtil (String address,int port,String dataBase,String collection) {  
  
        this.address = address;  
        this.dataBase = dataBase;  
        this.collection = collection;  
        //实例化  
        getMongoCon();   
    }  
    /** 
     * 连接到数据库 
     * @return  
     */  
    @Test  
    public boolean mongodbConTest() {   
          
        try{      
            // 连接到 mongodb 服务  
            MongoClient mongoClient = new MongoClient( address , port );   
            // 连接到数据库  
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBase);    
            System.out.println("Connect to database successfully");  
            mongoDatabase.createCollection(collection);    
            return true;  
        }catch(Exception e){  
            System.err.println(e.getClass().getName() + "->" + e.getMessage());  
            return false;  
        }   
    }  
  
    /** 
     * 创建集合  
     */  
    @Test  
    public void mongodbCreate() {   
            getMongoCon();   
            mongoDatabase.createCollection(collection);    
    }  
  
    /** 
     * 向mongodb添加数据 
     */  
    @Test  
    public void mongdbAddDoc(Document doc) {  
   
        getMongoCon();   
        mongoCol.insertOne(doc);   
    }  
    /** 
     * 关闭mongodb连接 
     */  
    @Test  
    public void mongoClose() {  
  
        getMongoCon();   
        mongoClient.close();  
    }  
  
    /** 
     * 根据固定条件向mongodb添加数据  
     */  
    @Test  
    public void mongdbAddDocByOptions(Document doc,InsertOneOptions options) {  
   
        getMongoCon();   
        mongoCol.insertOne(doc, options);   
          
  
    }  
    /** 
     * 向mongodb添加多条数据 
     */  
    @Test  
    public void mongdbAddDocs(List<Document> list) {  
   
        getMongoCon();   
        mongoCol.insertMany(list);    
          
  
    }  
    /** 
     * 向mongodb删除一条数据 
     * @param filter  
     * 查询条件 //注意Bson的几个实现类，BasicDBObject, BsonDocument,  
     * BsonDocumentWrapper, CommandResult, Document, RawBsonDocument  
     */  
    @Test  
    public DeleteResult mougdbDel(BasicDBObject basic) {  
   
        getMongoCon();   
        Bson filter = basic;   
        DeleteResult dResult = mongoCol.deleteOne(filter);   
          
        return dResult;   
    }  
    /** 
     * 向mongodb删除多条数据 
     * @param filter  
     * 查询条件 //注意Bson的几个实现类，BasicDBObject, BsonDocument,  
     * BsonDocumentWrapper, CommandResult, Document, RawBsonDocument  
     */  
    @Test  
    public DeleteResult  mougdbDels(BasicDBObject basic) {  
   
        getMongoCon();   
        Bson filter = basic;   
        DeleteResult dResult = mongoCol.deleteMany(filter);   
          
        return dResult;   
    }  
    /** 
     * 向mongodb修改单条数据 
     * @param filter  
     * @param update 
     * @return 
     */  
    @Test  
    public UpdateResult mongdbUpdateOne(Bson filter, Bson update) {  
   
        getMongoCon();   
        UpdateResult result = mongoCol.updateOne(filter, update);  
        return result;   
    }   
    /** 
     * 向mongodb修改单条数据 
     * @param filter  
     * @param update 
     * @return 
     */  
    @Test  
    public UpdateResult mongdbUpdateMany(Bson filter, Bson update) {  
   
        getMongoCon();   
        UpdateResult result = mongoCol.updateMany(filter, update);  
        return result;   
    }    
    /** 
     * mongodb根据固定条件查询数据查询数据 
     */  
    @Test  
    public List<Document> mongdbQuery(Bson bson) {  
  
        getMongoCon();   
        List<Document> array = new ArrayList<>();   
        FindIterable<Document> findy = mongoCol.find(bson);  
        MongoCursor<Document> cursor = findy.iterator();  
        while (cursor.hasNext()) {  
  
            array.add(cursor.next());   
        }  
        return array;   
    }   
    /** 
     * mongodb根据BasicDBObject中的固定条件查询数据查询数据 
     */  
    @Test  
    public List<Document> mongdbQuery(BasicDBObject obj) {  
  
        getMongoCon();   
        List<Document> array = new ArrayList<>();  
        Bson bson = obj;  
        FindIterable<Document> findy = mongoCol.find(bson);  
        MongoCursor<Document> cursor = findy.iterator();  
        while (cursor.hasNext()) {  
  
            array.add(cursor.next());   
        }  
        return array;   
    }   
    /** 
     * 根据key和value值查询数据 
     * @param key 查询字段 
     * @param value 查询内容 
     * @return 
     */  
    @Test  
    public List<Document> mongdbQuery(String key,Object value) {  
  
        getMongoCon();   
        List<Document> array = new ArrayList<>();  
        Bson bson = new BasicDBObject().append(key, value);  
        FindIterable<Document> findy = mongoCol.find(bson);  
        MongoCursor<Document> cursor = findy.iterator();  
        while (cursor.hasNext()) {  
  
            array.add(cursor.next());   
        }  
        return array;   
    }   
    /** 
     * 向mongodb查询所有数据 
     */  
    @Test  
    public List<Document> mongdbQuerys() {  
        getMongoCon();   
        List<Document> array = new ArrayList<>();  
        FindIterable<Document> findAll = mongoCol.find();  
        MongoCursor<Document> corsor = findAll.iterator();  
        while (corsor.hasNext()) {  
               
            array.add(corsor.next());   
        }  
        return array;  
  
    }  
    /** 
     * 通过用户名密码登录(待测试) 
     */  
    @Test  
    public void getConUsePass(){   
        try {    
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址    
            //ServerAddress()两个参数分别为 服务器地址 和 端口    
            ServerAddress serverAddress = new ServerAddress(address,27017);    
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();    
            addrs.add(serverAddress);    
  
            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码    
            MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());    
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();    
            credentials.add(credential);    
  
            //通过连接认证获取MongoDB连接    
            MongoClient mongoClient = new MongoClient(addrs,credentials);    
  
            //连接到数据库    
            MongoDatabase mongoDatabase = mongoClient.getDatabase("databaseName");    
            System.out.println("Connect to database successfully");    
        } catch (Exception e) {    
            System.err.println(e.getClass().getName() + "->" + e.getMessage());    
        }     
    }   
  
}  