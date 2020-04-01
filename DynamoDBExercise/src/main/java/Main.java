import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import models.*;
import org.w3c.dom.Attr;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static String testUserAlias;
    public static String test2UserAlias;
    public static DynamoDB dynamoDB;
    public static AmazonDynamoDB client;
    public static ServerFacade server;
    public static Table table;
    public static Table userTable;
    public static Table authTable;

    public static void main(String[] args){

        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2)
                .build();

        dynamoDB = new DynamoDB(client);

//        table = dynamoDB.getTable("follows");
        table = dynamoDB.getTable("stories");

        userTable = dynamoDB.getTable("user");
        authTable = dynamoDB.getTable("authentication");
        server = new ServerFacade();
//        put100Follows();
        testUserAlias = "@BartonLemus";
        test2UserAlias = "@HaleyMcpheeters";
        FollowerRequest request = new FollowerRequest("@DonaZieman", 2, null);
        User user = new User("test", "test", "@TestUser", "@TestUser");
        Status status = new Status(user, "This is a message");
        Status status1 = new Status(user, "This is a message1");
        Status status2 = new Status(user, "This is a message2");
        Status status3 = new Status(user, "This is a message3");


//        getAnItem();
//        updateAnItem();
//        queryFollows();
//        queryFollowsIndex();
//        query10Follows();
//        queryTrueInfo(request);
//        put10Users();
//        getUser(user);
//        authTableCheck("@testUser3", "token010");
//        boolean test = authTableValidate("token010");
//        System.out.println(test);

        try {
//            postStatusToStory(status);
//            TimeUnit.SECONDS.sleep(1);
//            postStatusToStory(status1);
//            TimeUnit.SECONDS.sleep(1);
//            postStatusToStory(status2);
//            TimeUnit.SECONDS.sleep(1);
//            postStatusToStory(status3);
            getStory(new StoryRequest(user, 3, null));
        }
        catch(Exception x){
        }

    }


    static void postStatusToStory(Status status){
        try {
                PutItemOutcome outcome = table
                        .putItem(new Item().withPrimaryKey("story_owner", status.getUser().getAlias())
                                .withLong("time_stamp", status.getTimeStamp())
                                .withString("message", status.getMessage()));

            }
        catch (Exception e) {
                System.err.println("Unable to add item");
                System.err.println(e.getMessage());
        }
    }

    static void getStory(StoryRequest request){
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":story_owner", request.getUser().getAlias());

        QuerySpec querySpec = new QuerySpec()
                .withValueMap(valueMap)
                .withKeyConditionExpression("story_owner = :story_owner")
                .withScanIndexForward(true)
                .withMaxPageSize(request.limit);

        if(request.getLastStatus() != null) {
            querySpec.withExclusiveStartKey("story_owner", request.getLastStatus().getUser().getAlias(),
                    "time_stamp", request.getLastStatus().getTimeStamp());              //TODO: make sure timestamp remains the same throughout all the code
        }

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            items = table.query(querySpec);
                        iterator = items.iterator();

            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("message"));
            }
            }
        catch (Exception e) {
            System.err.println(e.getMessage());
//            throw new IOException("Database Error");
        }

        boolean hasMorePages = false;
        QueryOutcome outcome = items.getLastLowLevelResult();
//        ArrayList<Item> itemList = new ArrayList<Item>(outcome.getItems());
//        ArrayList<Status> statusList = new ArrayList<Status>();

//        if(outcome.getQueryResult().getLastEvaluatedKey() == null){
//            hasMorePages = false;
//        }
//        else {
//            hasMorePages = true;
//        }
//        for (Item testItem: itemList) {
//            Status status = new Status(request.getUser(), testItem.getString("message"));
//            status.setTimeStamp(testItem.getLong("time_stamp"));
//            statusList.add(status);
//        }

    }




//    static void authTableCheck(String alias, String token){
//        try {
//            PutItemOutcome outcome = authTable
//                    .putItem(new Item().withPrimaryKey("user_alias", alias)
//                            .withString("auth_token", token)
//                            .withLong("expiration", System.currentTimeMillis()+10*60*1000));
//
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
////            throw new IOException("Database Error");
//        }
//    }
//
//    static boolean authTableValidate(String token){
//        HashMap<String, Object> valueMap = new HashMap<String, Object>();
//        valueMap.put(":auth_token", token);
//
//        QuerySpec querySpec = new QuerySpec()
//                .withKeyConditionExpression("auth_token = :auth_token")
//                .withValueMap(valueMap)
//                .withScanIndexForward(false);
//
//        ItemCollection<QueryOutcome> items = null;
//        Iterator<Item> iterator = null;
//        Item item = null;
//
//        try {
//            items = authTable.getIndex("auth_token-index").query(querySpec);
//            iterator = items.iterator();
//            while (iterator.hasNext()) {
//                item = iterator.next();
//            }
//        } catch (Exception e) {
//            System.err.println("Unable to query");
//            System.err.println(e.getMessage());
////            throw new IOException("Database Error");
//        }
//
//        QueryOutcome outcome = items.getLastLowLevelResult();
//        if (outcome.getItems().size() == 0){
//            return false;
//        }
//        else{ //if (outcome.getItems().get(0).getLong("expiration") - System.currentTimeMillis() > 20){
//            System.out.println(outcome.getItems().get(0).getLong("expiration") - System.currentTimeMillis() < 0); // true if expired
//            System.out.println(outcome.getItems().get(0).getLong("expiration") - System.currentTimeMillis());
//            return true;
//        }
//    }
//
//
//
//    static void put100Follows(){
//        List<Follow> followList = server.makeFollowers();
//
//        testUserAlias = followList.get(0).getFollower().getAlias();
//        test2UserAlias = followList.get(0).getFollowee().getAlias();
//
//
//        for (int i = 0; i < 100; i++) {
//            Follow follow = followList.get(i);
//
//            try {
//                PutItemOutcome outcome = table
//                        .putItem(new Item().withPrimaryKey("follower_handle", follow.getFollower().getAlias())
//                                .withString("followee_handle", follow.getFollowee().getAlias())
//                                .withString("follower_fname", follow.getFollower().getFirstName())
//                                .withString("followee_fname", follow.getFollowee().getFirstName())
//                                .withString("follower_lname", follow.getFollower().getLastName())
//                                .withString("followee_lname", follow.getFollowee().getLastName())
//                                .withString("follower_url", follow.getFollower().getImageUrl())
//                                .withString("followee_url", follow.getFollowee().getImageUrl()));
//
//            } catch (Exception e) {
//                System.err.println("Unable to add item");
//                System.err.println(e.getMessage());
//            }
//        }
//    }
//
//    static void put10Users(){
//        List<User> userList = server.makeUsers();
//        User cur = userList.get(0);
//
////        for (int i = 0; i < 10; i++) {
//
//            try {
//                PutItemOutcome outcome = userTable
//                        .putItem(new Item().withPrimaryKey("user_alias", cur.getAlias())
//                                .withString("user_fname", cur.getFirstName())
//                                .withString("user_lname", cur.getLastName())
//                                .withString("user_url", cur.getImageUrl())
//                                .withString("user_password", "password123x"));
//                System.out.println("test");
//            } catch (Exception e) {
//                System.err.println("Unable to add item");
//                System.err.println(e.getMessage());
//            }
////        }
//    }
//
//    static void getUser(User user){
//        GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_alias", user.getAlias());
//
//        Item result = null;
//
//        try {
//            System.out.println("Attempting to read the item...");
//            result = userTable.getItem(spec);
////            System.out.println("GetItem succeeded: " + outcome);
//
//        }
//        catch (Exception e) {
//            System.err.println("Unable to read item ");
//            System.err.println(e.getMessage());
//        }
//
//
//    }
//
//
//    static void deleteAnItem(){
//        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
//                .withPrimaryKey("follower_handle", testUserAlias, "followee_handle", test2UserAlias);
//
//        // Conditional delete (we expect this to fail)
//
//        try {
//            System.out.println("Attempting a conditional delete...");
//            table.deleteItem(deleteItemSpec);
//            System.out.println("DeleteItem succeeded");
//        }
//        catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//    }
//
//    static void getAnItem(){
//
//        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", testUserAlias, "followee_handle", test2UserAlias);
//
//        try {
//            System.out.println("Attempting to read the item...");
//            Item outcome = table.getItem(spec);
//            System.out.println("GetItem succeeded: " + outcome);
//
//        }
//        catch (Exception e) {
//            System.err.println("Unable to read item ");
//            System.err.println(e.getMessage());
//        }
//    }
//
//
//    static void updateAnItem(){
//        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("follower_handle", testUserAlias, "followee_handle", test2UserAlias)
//                .withUpdateExpression("set follower_name = :fhn, followee_name=:fehn")
//                .withValueMap(new ValueMap().withString(":fhn", "Yourmom").withString(":fehn", "Yessir"))
//                .withReturnValues(ReturnValue.UPDATED_NEW);
//
//        try {
//            System.out.println("Updating the item...");
//            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
//            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
//
//        }
//        catch (Exception e) {
//            System.err.println("Unable to update item: ");
//            System.err.println(e.getMessage());
//        }
//    }
//
//
//
//
//    //------ query thing
//    static void queryFollows(){
//        HashMap<String, Object> valueMap = new HashMap<String, Object>();
//        valueMap.put(":follower_handle", "@CierraRedmond");
//
//        QuerySpec querySpec = new QuerySpec()
//                .withKeyConditionExpression("follower_handle = :follower_handle")
//                .withValueMap(valueMap)
//                .withScanIndexForward(true);
//
//        ItemCollection<QueryOutcome> items = null;
//        Iterator<Item> iterator = null;
//        Item item = null;
//
//        try {
//            items = table.query(querySpec);
//
//            iterator = items.iterator();
//            while (iterator.hasNext()) {
//                item = iterator.next();
//                System.out.println(item.getString("followee_handle"));
//            }
//
//        }
//        catch (Exception e) {
//            System.err.println("Unable to query");
//            System.err.println(e.getMessage());
//        }
//
//        QueryOutcome outcome = items.getLastLowLevelResult();
////        System.out.println(outcome.getItems().size());
//    }
//
//
//
//
//    //------ follows index
//    static void queryFollowsIndex(){
//        HashMap<String, Object> valueMap = new HashMap<String, Object>();
//        valueMap.put(":followee_handle", "@ArthurDurrell");
////        valueMap.put(":followee_handle", "@JeraldLightfoot");
//
//        QuerySpec querySpec = new QuerySpec()
//                .withKeyConditionExpression("followee_handle = :followee_handle")
//                .withValueMap(valueMap)
//                .withScanIndexForward(false);
//
//        ItemCollection<QueryOutcome> items = null;
//        Iterator<Item> iterator = null;
//        Item item = null;
//
//        try {
//            items = table.getIndex("followee_handle-follower_handle-index").query(querySpec);
//
//            iterator = items.iterator();
//            while (iterator.hasNext()) {
//                item = iterator.next();
//                System.out.println(item.getString("followee_handle"));
//            }
//
//        }
//        catch (Exception e) {
//            System.err.println("Unable to query");
//            System.err.println(e.getMessage());
//        }
//
//        QueryOutcome outcome = items.getLastLowLevelResult();
//    }
//
//
//
//
//    //---------- query 10 follows
//    static void query10Follows(){
//        HashMap<String, Object> valueMap = new HashMap<String, Object>();
//        valueMap.put(":follower_handle", "@ArthurDurrell");
//
//        QuerySpec querySpec = new QuerySpec()
//                .withKeyConditionExpression("follower_handle = :follower_handle")
//                .withValueMap(valueMap)
//                .withScanIndexForward(true)
//                .withMaxPageSize(3)
//                .withExclusiveStartKey("follower_handle", "@ArthurDurrell","followee_handle", "@GaylordRule");
//
//        ItemCollection<QueryOutcome> items = null;
//        Iterator<Item> iterator = null;
//        Item item = null;
//
//        try {
//            items = table.query(querySpec);
//            System.out.println(items.pages());
//            iterator = items.iterator();
//            int i = 0;
//            while (iterator.hasNext() && i < 3) {
//                item = iterator.next();
//                System.out.println(item.getString("followee_handle"));
//                i++;
//            }
//
//        }
//        catch (Exception e) {
//            System.err.println("Unable to query");
//            System.err.println(e.getMessage());
//        }
//
//        QueryOutcome outcome = items.getLastLowLevelResult();
//        outcome.getQueryResult().getLastEvaluatedKey();
//
//    }
//
//
//    static void queryTrueInfo(FollowerRequest request){
//        HashMap<String, Object> valueMap = new HashMap<String, Object>();
//        valueMap.put(":followee_handle", request.getFollower());
//
//        QuerySpec querySpec = new QuerySpec()
//                .withKeyConditionExpression("followee_handle = :followee_handle")
//                .withValueMap(valueMap)
//                .withScanIndexForward(true)
//                .withMaxPageSize(request.limit);
//
//        if(request.lastFollower != null) {
//            querySpec.withExclusiveStartKey("follower_handle", request.getFollower(),
//                    "followee_handle", request.getLastFollowee());
//        }
//
//        ItemCollection<QueryOutcome> items = null;
//        Iterator<Item> iterator = null;
//        Item item = null;
//
//        try {
//            items = table.getIndex("followee_handle-follower_handle-index").query(querySpec);
//            System.out.println(items.pages());
//            iterator = items.iterator();
//            int i = 0;
//            while (iterator.hasNext() && i < 3) {
//                item = iterator.next();
//                System.out.println(item.getString("followee_handle"));
//                i++;
//            }
//        }
//        catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//
//        boolean hasMorePages = false;
//        QueryOutcome outcome = items.getLastLowLevelResult();
//        ArrayList<Item> itemList = new ArrayList<Item>(outcome.getItems());
//        ArrayList<User> followerList = new ArrayList<User>();
//
//        if(outcome.getQueryResult().getLastEvaluatedKey() == null){
//            hasMorePages = false;
//        }
//        else {
//            hasMorePages = true;
//        }
//
//        for (Item testItem: itemList) {
//           User user = new User(testItem.getString("follower_fname"), testItem.getString("follower_lname"), testItem.getString("follower_url"));
//           followerList.add(user);
//        }
//    }

}
