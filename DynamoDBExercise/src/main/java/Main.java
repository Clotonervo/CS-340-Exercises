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
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static String testUserAlias;
    public static String test2UserAlias;
    public static DynamoDB dynamoDB;
    public static AmazonDynamoDB client;
    public static MockDatabase server;
    public static Table table;
    public static Table storyTable;
    public static Table feedTable;
    public static Table userTable;
    public static Table authTable;
    public static int j = 0;
    public static User currentUserFor;

    public static void main(String[] args){

        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2)
                .build();

        dynamoDB = new DynamoDB(client);

        table = dynamoDB.getTable("follows");
        storyTable = dynamoDB.getTable("stories");
        feedTable = dynamoDB.getTable("feed");
        userTable = dynamoDB.getTable("user");
        server = MockDatabase.getInstance();
        List<User> userList = server.getAllUsers();
//        getUser();

//        System.out.println(queryUser());
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(new String("password").getBytes());
            System.out.println(new String(passwordHash));
            updateAnItem(new String(passwordHash));
//            System.out.println(imageURL);
        }
        catch (NoSuchAlgorithmException x){
            x.printStackTrace();
        }

//        //Put all users into database
//        for (User user: userList) {
//            putUser(user);
//        }
//
//        //Put all follows into database
//        List<Follow> followList = server.getFollows();
//
//        for (Follow follow: followList) {
//            putFollow(follow);
//        }
//
////        //Post all statuses to story and feed
////        Map<User, List<Status>> statuses = server.getUserStatuses();
////        try {
////            for (User currentUser: server.getAllUsers()) {
////                List<User> userList2 = server.getUserFollowing().get(currentUser);
////                currentUserFor = currentUser;
////                if(userList2 == null){
////                    continue;
////                }
////                for (User following : userList2) {
////                    for (int i = 0; i < statuses.get(following).size(); i++) {
////                        postStatustoStory(statuses.get(following).get(i));
////                        postStatusToFeed(currentUser.getAlias(), statuses.get(following).get(i));
////                    }
////                }
////
////            }
////        }
//        catch(Exception x){
//            System.out.println("Exception caught!");
//            System.out.println(x.getStackTrace());
//            System.out.println(server.getUserFollowing().get(currentUserFor));
//            System.out.println(server.getUserStatuses().get(currentUserFor));
//
//            currentUserFor = null;
//
//        }

    }

    static void putFollow(Follow follow){

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("follower_handle", follow.getFollower().getAlias())
                            .withString("followee_handle", follow.getFollowee().getAlias())
                            .withString("follower_fname", follow.getFollower().getFirstName())
                            .withString("followee_fname", follow.getFollowee().getFirstName())
                            .withString("follower_lname", follow.getFollower().getLastName())
                            .withString("followee_lname", follow.getFollowee().getLastName())
                            .withString("follower_url", follow.getFollower().getImageUrl()));
        } catch (Exception e) {
            System.err.println("Unable to add item");
            System.err.println(e.getMessage());
        }
    }

    static void getUser(){
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_alias", "@TestUser");

        Item result = null;

        try {
            System.out.println("Attempting to read the item...");
            result = table.getItem(spec);
        }
        catch (Exception e) {
            System.err.println("Unable to read item ");
            System.err.println(e.getMessage());
//            throw new IOException("Database error");
        }

        if (result == null){
//            return new UserAliasResponse();
        }
        else {
//            User userToReturn = new User(result.getString("user_fname"), result.getString("user_lname"), alias, result.getString("user_url"));
//            return new UserAliasResponse(userToReturn);
        }
    }

    static boolean queryUser(){
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":user_alias", "@TestUser320941324");

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("user_alias = :user_alias")
                .withValueMap(valueMap)
                .withScanIndexForward(false);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            items = userTable.query(querySpec);
            iterator = items.iterator();
            iterator.hasNext();

        } catch (Exception e) {
            System.err.println("Unable to query");
            System.err.println(e.getMessage());
//            throw new IOException("Database Error");
        }

        QueryOutcome outcome = items.getLastLowLevelResult();
        if (outcome.getItems().size() == 0){
            return false;
        }
        else{
            return true;
        }
    }


    static void postStatusToFeed(String ownerAlias, Status status){
        try {
                PutItemOutcome outcome = feedTable
                        .putItem(new Item().withPrimaryKey("feed_owner", ownerAlias, "time_stamp", status.getTimeStamp())
                                .withString("message", status.getMessage())
                        .withString("user_fname", status.getUser().getFirstName())
                        .withString("user_lname", status.getUser().getLastName())
                        .withString("user_alias", status.getUser().getAlias())
                        .withString("user_url", status.getUser().getImageUrl()));

            }
        catch (Exception e) {
                System.err.println("Unable to add item");
                System.err.println(e.getMessage());
        }
    }

    static void postStatustoStory(Status status){
        try {
            PutItemOutcome outcome = storyTable
                    .putItem(new Item().withPrimaryKey("story_owner", status.getUser().getAlias(), "time_stamp", status.getTimeStamp())
                            .withString("message", status.getMessage()));

        }
        catch (Exception e) {
            System.err.println("Unable to add item");
            System.err.println(e.getMessage());
        }
    }

    static void putUser(User cur){

        try {
            PutItemOutcome outcome = userTable
                    .putItem(new Item().withPrimaryKey("user_alias", cur.getAlias())
                            .withString("user_fname", cur.getFirstName())
                            .withString("user_lname", cur.getLastName())
                            .withString("user_url", cur.getImageUrl())
                            .withString("user_password", "password123" + j));
//            System.out.println("test");
        } catch (Exception e) {
            System.err.println("Unable to add item");
            System.err.println(e.getMessage());
        }
        j++;
    }


//    static FeedResponse getFeed(FeedRequest request){
//        HashMap<String, Object> valueMap = new HashMap<String, Object>();
//        valueMap.put(":feed_owner", request.getUser().getAlias());
//
//        QuerySpec querySpec = new QuerySpec()
//                .withValueMap(valueMap)
//                .withKeyConditionExpression("feed_owner = :feed_owner")
//                .withScanIndexForward(true)
//                .withMaxPageSize(request.limit);
//
//        if(request.getLastStatus() != null) {
//            querySpec.withExclusiveStartKey("feed_owner", request.getLastStatus().getUser().getAlias(),
//                    "time_stamp", request.getLastStatus().getTimeStamp());
//        }
//
//        ItemCollection<QueryOutcome> items = null;
//        Iterator<Item> iterator = null;
//
//        try {
//            items = table.query(querySpec);
//            iterator = items.iterator();
//            iterator.hasNext();
////            while (iterator.hasNext()) {
////                item = iterator.next();
////                System.out.println(item.getString("message"));
////            }
//            }
//        catch (Exception e) {
//            System.err.println(e.getMessage());
////            throw new IOException("Database Error");
//        }
//
//        boolean hasMorePages = false;
//        QueryOutcome outcome = items.getLastLowLevelResult();
//        ArrayList<Item> itemList = new ArrayList<Item>(outcome.getItems());
//        ArrayList<Status> statusList = new ArrayList<Status>();
//
//        if(outcome.getQueryResult().getLastEvaluatedKey() == null){
//            hasMorePages = false;
//        }
//        else {
//            hasMorePages = true;
//        }
//        for (Item testItem: itemList) {
//            User user = new User(testItem.getString("user_fname"), testItem.getString("user_lname"), testItem.getString("user_alias"), testItem.getString("user_url"));
//            Status status = new Status(user, testItem.getString("message"));
//            status.setTimeStamp(testItem.getLong("time_stamp"));
//            statusList.add(status);
//        }
//
//        return new FeedResponse(hasMorePages, statusList);
//    }





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
    static void updateAnItem(String password){
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("user_alias", "@TestUser")
                .withUpdateExpression("set user_password=:up")
                .withValueMap(new ValueMap().withString(":up", password))
                .withReturnValues(ReturnValue.UPDATED_NEW);

//        new Item().withPrimaryKey("user_alias", cur.getAlias())
//                .withString("user_fname", cur.getFirstName())
//                .withString("user_lname", cur.getLastName())
//                .withString("user_url", cur.getImageUrl())
//                .withString("user_password", "password123" + j));

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = userTable.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: ");
            System.err.println(e.getMessage());
        }
    }
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
