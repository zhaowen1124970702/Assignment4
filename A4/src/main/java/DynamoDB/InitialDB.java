package DynamoDB;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class InitialDB {
  public static String TABLENAME = "A4_Supermarket";
  public static Table createDynamoDBTable() {

    AmazonDynamoDBAsync ddbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
        .withExecutorFactory(() -> Executors.newFixedThreadPool(20))
        .build();

    DynamoDB dynamoDB = new DynamoDB(ddbAsync);
    Table table = null;
    try{
      System.out.println("Attempting to create table; please wait...");
      table = dynamoDB.createTable(TABLENAME,
          Arrays.asList(new KeySchemaElement("storeID", KeyType.HASH),
              new KeySchemaElement("customerID", KeyType.RANGE)), // Partition
          // key
              Arrays.asList(new AttributeDefinition("storeID", ScalarAttributeType.N),
                  new AttributeDefinition("customerID", ScalarAttributeType.N)),
          new ProvisionedThroughput(10L, 10L));
      table.waitForActive();
      System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
      return table;

    }catch (Exception e){
      System.err.println("Unable to create table: ");
      System.err.println(e.getMessage());
      return table;
    }


  }

}
