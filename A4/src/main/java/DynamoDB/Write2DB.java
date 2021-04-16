package DynamoDB;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Write2DB {

  public static Boolean loadData(int storeID,int customerID, String orderDate,String purchase, Table table) {
//    int storeID = Integer.valueOf(args[0]);
//    int customerID = Integer.valueOf(args[1]);
//    String orderDate = args[2];
//    String purchase = args[3];
//
//
//    int storeID = 4;
//    int customerID = 5;
//    String orderDate = "20200103";
//    String purchase = "args[3]";

//    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
//        .build();

//    AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
//        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
//        .withExecutorFactory(() -> Executors.newFixedThreadPool(20))
//        .build();
//
//    DynamoDB dynamoDB = new DynamoDB(ddbAsync);
//    Table table = dynamoDB.getTable("A4_Supermarket");

    final Map<String, Object> infoMap = new HashMap<String, Object>();
//    infoMap.put("customerID", customerID);
    infoMap.put("orderDate", orderDate);
    infoMap.put("purchase", purchase);
    try {
      System.out.println("Adding a new item...");
      PutItemOutcome outcome = table
          .putItem(new Item().withPrimaryKey("storeID", storeID,"customerID",customerID).withMap("info", infoMap));

      System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
      return true;
    }catch (AmazonDynamoDBException e){
      System.err.println("Unable to add item: " + storeID);
      System.err.println(e.getMessage());
      return false;
    }
  }
}
