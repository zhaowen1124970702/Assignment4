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

public class UseDynamoDBMapper {

  public static void main(String[] args) {
//    int storeID = Integer.valueOf(args[0]);
//    int customerID = Integer.valueOf(args[1]);
//    String orderDate = args[2];
//    String purchase = args[3];

    int storeID = 2;
    int customerID = 3;
    String orderDate = "20200102";
    String purchase = "args[3]";

//    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
//        .build();

    AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
        .withExecutorFactory(() -> Executors.newFixedThreadPool(10))
        .build();

    DynamoDB dynamoDB = new DynamoDB(ddbAsync);
    Table table = dynamoDB.getTable("A4_Supermarket");
    final Map<String, Object> infoMap = new HashMap<String, Object>();
//    infoMap.put("customerID", customerID);
    infoMap.put("orderDate", orderDate);
    infoMap.put("purchase", purchase);
    try {
      System.out.println("Adding a new item...");
      PutItemOutcome outcome = table
          .putItem(new Item().withPrimaryKey("storeID", storeID,"customerID",customerID).withMap("info", infoMap));

      System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
    }catch (Exception e){
      System.err.println("Unable to add item: " + storeID);
      System.err.println(e.getMessage());
    }





//    PurchaseRecord purchaseRecord = new PurchaseRecord();
//    try{
//      // add new item to the A4_Supermarket table
//      purchaseRecord.setStoreID(storeID);
//      purchaseRecord.setCustomerID(customerID);
//      purchaseRecord.setOrderDate(orderDate);
//      purchaseRecord.setPurchase(purchase);
//
//      // save the item
//      DynamoDBMapper mapper = new DynamoDBMapper(client);
//      mapper.save(purchaseRecord);
//      System.out.println("Item updated:");
//      System.out.println(purchaseRecord);
//      System.out.print("Done");
//    } catch (AmazonDynamoDBException e){
//      e.getStackTrace();
//    }

  }
  @DynamoDBTable(tableName="A4_Supermarket")
  public static class PurchaseRecord{
    private int storeID;
    private int customerID;
    private String orderDate;
    private String purchase;

    @DynamoDBHashKey(attributeName="storeID")
    public int getStoreID() {
      return storeID;
    }

    @DynamoDBAttribute(attributeName="customerID")
    public int getcustomerID() {
      return customerID;
    }

    @DynamoDBAttribute(attributeName="orderDate")
    public String getOrderDate() {
      return orderDate;
    }

    @DynamoDBAttribute(attributeName="purchase")
    public String getPurchase() {
      return purchase;
    }

    public void setStoreID(int storeID) {
      this.storeID = storeID;
    }

    public void setCustomerID(int cusID) {
      this.customerID = customerID;
    }

    public void setOrderDate(String orderDate) {
      this.orderDate = orderDate;
    }

    public void setPurchase(String purchase) {
      this.purchase = purchase;
    }
  }

}
