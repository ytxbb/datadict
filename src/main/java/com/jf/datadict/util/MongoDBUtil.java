package com.jf.datadict.util;

import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MongoDBVO;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.http.HttpSession;

@PropertySource("classpath:application-dev.properties")
public class MongoDBUtil {

    @Value("${mongodb.maxConnect}")
    private static int maxConnect;

    @Value("${mongodb.maxWaitThread}")
    private static int maxWaitThread;

    @Value("${mongodb.maxTimeOut}")
    private static int maxTimeOut;

    @Value("${mongodb.maxWaitTime}")
    private static int maxWaitTime;

    @Value("${mongodb.serverSelectionTimeout}")
    private static int serverSelectionTimeout;

    private static MongoClientOptions options;
    private static void init() {
        // 参数依次是：链接池数量 最大等待时间 scoket超时时间 设置连接池最长生命时间 连接超时时间
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectionsPerHost(maxConnect);
        build.threadsAllowedToBlockForConnectionMultiplier(maxWaitThread);
        build.connectTimeout(maxTimeOut);// 每次请求耗时超时
        build.maxWaitTime(maxWaitTime);
        build.serverSelectionTimeout(serverSelectionTimeout);// 客户端访问服务端时的连接超时设置时间
        options = build.build();
    }

    /**
     * 初始化连接池，设置参数。
     */
    private static MongoClient init(HttpSession session) {
        String mongoIp = (String)session.getAttribute("mongoIp");
        Integer mongoPort = (Integer)session.getAttribute("mongoPort");
        String mongoUserName = (String)session.getAttribute("mongoUserName");
        String mongoPassword = (String)session.getAttribute("mongoPassword");

        MongoClient client;
        // 验证用户名和密码是否都非空
        if (!MyStringUtil.isAllEmpty(mongoUserName, mongoPassword)) {
            // ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress(mongoIp, mongoPort);
            // 三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential(
                    mongoUserName, "admin", mongoPassword.toCharArray());
            // 通过连接认证获取MongoDB连接
            client = new MongoClient(serverAddress, credential, options);
        } else {
            client = new MongoClient(mongoIp, mongoPort);
        }
        return client;
    }

    public static void listCollection(MongoClient mongoClient, String dataBaseName, String collectName) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBaseName);
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectName);
        // 列出集合中的所有文档
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    /**
     * 校验mongodb是否能连接上
     * @param dbvo
     * @return
     */
    public static Boolean validateMongoDBConnect(MongoDBVO dbvo) {
        init();
        MongoClient client;
        // ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(dbvo.getMongodbIp(), dbvo.getMongodbPort());

        try {
            // 验证用户名和密码是否都非空
            if (!MyStringUtil.isHasEmpty(dbvo.getMongodbuserName(), dbvo.getMongodbPassword())) {
                // 三个参数分别为 用户名 数据库名称 密码
                MongoCredential credential = MongoCredential.createScramSha1Credential(
                        dbvo.getMongodbuserName(), "admin", dbvo.getMongodbPassword().toCharArray());
                // 通过连接认证获取MongoDB连接
                client = new MongoClient(serverAddress, credential, options);
            } else {
                client = new MongoClient(serverAddress, options);
            }

            client.getDatabase("admin");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // 参数依次是：链接池数量 最大等待时间 scoket超时时间 设置连接池最长生命时间 连接超时时间
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectionsPerHost(5);
        build.threadsAllowedToBlockForConnectionMultiplier(50);
        build.connectTimeout(30000);// 每次请求耗时超时
        build.maxWaitTime(30000);
        build.serverSelectionTimeout(3000);// 客户端访问服务端时的连接超时设置时间
        MongoClientOptions options = build.build();

        ServerAddress serverAddress = new ServerAddress("localhost", 27017);
        MongoClient mongoClient = new MongoClient(serverAddress, options);
        try {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("jf_exedc_ods_exchange");

            System.out.println("列出所有库名");
            MongoIterable<String> listDBNames = mongoClient.listDatabaseNames();
            for (String listDBName : listDBNames) {
                System.out.println(listDBName);
            }
            System.out.println("-------------------------------");

            System.out.println("列出所有集合名");
            MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
            for (String collectionName : collectionNames) {
                System.out.println(collectionName);
            }
            System.out.println("-------------------------------");

            //获取集合
            MongoCollection<Document> collection = mongoDatabase.getCollection("Cheat_Deviceinfo");
            // 所有索引
            ListIndexesIterable<Document> documents = collection.listIndexes();
            for (Document document : documents) {
                System.out.println(document.toString());
            }
            // 列出集合中的所有文档
            FindIterable findIterable = collection.find();
            //取出查询到的第一个文档
            Document document = (Document) findIterable.first();
            //打印输出
            System.out.println("取出查询到的第一个文档");
            System.out.println(document);

            System.out.println("取出第一个的keys");
            System.out.println(document.keySet().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("！！MongoDB数据库连接异常：" + e.getMessage() );
        }
    }
}
