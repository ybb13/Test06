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
    //����
    public static MongodbUtil  getInstance(String address,int port,String dataBase,String collection){  
        if (mongoUtil!=null) {  
            return mongoUtil;  
        }else {  
            return new MongodbUtil(address,port,dataBase,collection);  
        }  
  
    }   
    /** 
     * ���ݵ�ַ�����ݿ⼰�����������ӵ����ݿ� 
     * @param address ���ݿ����ӵ�ַ 
     * @param port  ���ݿ�˿� 
     * @param dataBase ʵ���� 
     * @param collection ������ 
     */  
    public MongodbUtil (String address,int port,String dataBase,String collection) {  
  
        this.address = address;  
        this.dataBase = dataBase;  
        this.collection = collection;  
        //ʵ����  
        getMongoCon();   
    }  
    /** 
     * ���ӵ����ݿ� 
     * @return  
     */  
    @Test  
    public boolean mongodbConTest() {   
          
        try{      
            // ���ӵ� mongodb ����  
            MongoClient mongoClient = new MongoClient( address , port );   
            // ���ӵ����ݿ�  
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
     * ��������  
     */  
    @Test  
    public void mongodbCreate() {   
            getMongoCon();   
            mongoDatabase.createCollection(collection);    
    }  
  
    /** 
     * ��mongodb������� 
     */  
    @Test  
    public void mongdbAddDoc(Document doc) {  
   
        getMongoCon();   
        mongoCol.insertOne(doc);   
    }  
    /** 
     * �ر�mongodb���� 
     */  
    @Test  
    public void mongoClose() {  
  
        getMongoCon();   
        mongoClient.close();  
    }  
  
    /** 
     * ���ݹ̶�������mongodb�������  
     */  
    @Test  
    public void mongdbAddDocByOptions(Document doc,InsertOneOptions options) {  
   
        getMongoCon();   
        mongoCol.insertOne(doc, options);   
          
  
    }  
    /** 
     * ��mongodb��Ӷ������� 
     */  
    @Test  
    public void mongdbAddDocs(List<Document> list) {  
   
        getMongoCon();   
        mongoCol.insertMany(list);    
          
  
    }  
    /** 
     * ��mongodbɾ��һ������ 
     * @param filter  
     * ��ѯ���� //ע��Bson�ļ���ʵ���࣬BasicDBObject, BsonDocument,  
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
     * ��mongodbɾ���������� 
     * @param filter  
     * ��ѯ���� //ע��Bson�ļ���ʵ���࣬BasicDBObject, BsonDocument,  
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
     * ��mongodb�޸ĵ������� 
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
     * ��mongodb�޸ĵ������� 
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
     * mongodb���ݹ̶�������ѯ���ݲ�ѯ���� 
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
     * mongodb����BasicDBObject�еĹ̶�������ѯ���ݲ�ѯ���� 
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
     * ����key��valueֵ��ѯ���� 
     * @param key ��ѯ�ֶ� 
     * @param value ��ѯ���� 
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
     * ��mongodb��ѯ�������� 
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
     * ͨ���û��������¼(������) 
     */  
    @Test  
    public void getConUsePass(){   
        try {    
            //���ӵ�MongoDB���� �����Զ�����ӿ����滻��localhost��Ϊ����������IP��ַ    
            //ServerAddress()���������ֱ�Ϊ ��������ַ �� �˿�    
            ServerAddress serverAddress = new ServerAddress(address,27017);    
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();    
            addrs.add(serverAddress);    
  
            //MongoCredential.createScramSha1Credential()���������ֱ�Ϊ �û��� ���ݿ����� ����    
            MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());    
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();    
            credentials.add(credential);    
  
            //ͨ��������֤��ȡMongoDB����    
            MongoClient mongoClient = new MongoClient(addrs,credentials);    
  
            //���ӵ����ݿ�    
            MongoDatabase mongoDatabase = mongoClient.getDatabase("databaseName");    
            System.out.println("Connect to database successfully");    
        } catch (Exception e) {    
            System.err.println(e.getClass().getName() + "->" + e.getMessage());    
        }     
    }   
  
}  