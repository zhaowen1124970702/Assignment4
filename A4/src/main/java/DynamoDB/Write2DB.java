package DynamoDB;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

public class Write2DB {

  public final static AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://dynamodb.us-east-1.amazonaws.com", "us-east-1"))
      .withExecutorFactory(() -> Executors.newFixedThreadPool(32))
      .build();

//  public final static AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
//      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://localhost:8000", "us-east-1"))
//      .withExecutorFactory(() -> Executors.newFixedThreadPool(32))
//      .build();
  public final static DynamoDB dynamoDB = new DynamoDB(ddbAsync);
  public static Table table = dynamoDB.getTable("A4_Market2");

  public static Boolean loadData(int storeID,int customerID, String orderDate,String purchase) {
    String purchaseID = UUID.randomUUID().toString().replace("-", "");
    final Map<String, Object> infoMap = new HashMap<String, Object>();
    infoMap.put("customerID", customerID);
    infoMap.put("orderDate", orderDate);
    infoMap.put("purchase", purchase);
    try {
      System.out.println("Adding a new item...");
      PutItemOutcome outcome = table
          .putItem(new Item().withPrimaryKey("purchaseID", purchaseID,"storeID",storeID).withMap("info", infoMap));

      System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
      return true;
    }catch (AmazonDynamoDBException e){
      System.err.println("Unable to add item: " + purchaseID);
      System.err.println(e.getMessage());
      return false;
    }
  }
}
