package com.jf.datadict.test;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

public class MongoDemoA {
    public static void main(String[] args) throws Exception {

        //设置要连接的数据库的主机名称与端口号
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // 显示所有数据库名字
        MongoIterable<String> databaseNames = mongoClient.listDatabaseNames();
        for (String databaseName : databaseNames) {
            System.out.println(databaseName);
        }

        System.out.println("-----------");

        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> doc = db.getCollection("t1_name");

        FindIterable<Document> iter = doc.find();
        iter.forEach(new Block<Document>() {
            public void apply(Document _doc) {
                System.out.println(_doc.toJson());
            }
        });

        mongoClient.close();
    }
}