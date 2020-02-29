import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MongoTest
 * @Description TODO
 * @Date 2020/2/17 11:01
 * @Created by 74541
 */
public class MongoTest {
    public static void main(String[] args) {
        //mongodb测试
        // 连接mongo 客户端
        MongoClient mongoClient = new MongoClient("192.168.163.128");
        // 连接哪个数据库
        MongoDatabase spitdb = mongoClient.getDatabase("spitdb");
        // 连接某个集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        /**
         * 查询数据
         */
        // 封装查询条件
        //BasicDBObject bson = new BasicDBObject("version",20);
        BasicDBObject bson = new BasicDBObject("version",new BasicDBObject("$gt",10)); // >10 条件

        // 进行查找操作
        FindIterable<Document> documents = spit.find(bson);
        for (Document document : documents) {
            System.out.println("内容："+document.getInteger("version"));
        }
        /**
         * 添加数据
         */
        Map<String, Object> map = new HashMap<>();
        map.put("_id","10");
        map.put("version",40);
        map.put("content","因为一只蝙蝠");
        Document document = new Document(map);
        spit.insertOne(document);
        // 关闭当前连接客户端
        mongoClient.close();
    }
}
