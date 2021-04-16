package DynamoDB;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
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

public class InitialDB {
  public static String TABLENAME = "A4_Supermarket";
  public static void main(String[] args) {


    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
        .build();
    DynamoDB dynamoDB = new DynamoDB(client);
    try{
      System.out.println("Attempting to create table; please wait...");
      Table table = dynamoDB.createTable(TABLENAME,
          Arrays.asList(new KeySchemaElement("storeID", KeyType.HASH),
              new KeySchemaElement("customerID", KeyType.RANGE)), // Partition
          // key
              Arrays.asList(new AttributeDefinition("storeID", ScalarAttributeType.N),
                  new AttributeDefinition("customerID", ScalarAttributeType.N)),
          new ProvisionedThroughput(10L, 10L));
      table.waitForActive();
      System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

    }catch (Exception e){
      System.err.println("Unable to create table: ");
      System.err.println(e.getMessage());
    }


  }

}
