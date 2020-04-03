
import models.Follow;
import models.Status;
import models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MockDatabase {

    private static MockDatabase instance;
    private static Map<User, List<User>> userFollowing;
    private static Map<User, List<User>> userFollowers;

    private static List<Follow> follows;
    private static User tweeterBot = new User("Tweeter", "Bot", "@TweeterBot","https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    private static User testUser = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    private static Map<User, List<Status>> userStatuses;
    private static Map<User, List<Status>> userFeeds;
    private static List<User> allUsers;


    /*
        Constructors
     */
    public static MockDatabase getInstance() {
        if(instance == null) {
            instance = new MockDatabase();
        }

        return instance;
    }

    private MockDatabase(){
        intializeFollowData();
        initializeStatuses();
        initializeFeeds();
//        allUsers.add(new User("Test", "User",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
    }

       /*
                --------------------- Generates follow data

     */
    /**
     * Generates the followee data.
     */
    private void intializeFollowData() {

        userFollowing = new HashMap<User, List<User>>();
        allUsers = new ArrayList<User>();


        follows = getFollowGenerator().generateUsersAndFollows(100,
                2, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        follows.add(new Follow(new User("fname23", "lname23", "@tempAlias23", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png"),
                testUser));


        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = userFollowing.get(follow.getFollower());

            if(follow.getFollower().getAlias().equals("@tempAlias23")){
                System.out.println("found");
            }

            if(followees == null) {
                followees = new ArrayList<User>();
                userFollowing.put(follow.getFollower(), followees);
            }
            else {

            }

            if(!allUsers.contains(follow.getFollowee())){
                allUsers.add(follow.getFollowee());
            }

            followees.add(follow.getFollowee());
        }

        userFollowers = new HashMap<User, List<User>>();

        // Now do the same for a map of followers, keyed by followee to handle follower requests
        for(Follow follow : follows) {
            List<User> followers = userFollowers.get(follow.getFollowee());

            if(follow.getFollower().getAlias().equals("@tempAlias23")){
                System.out.println("found");
            }

            if(followers == null) {
                followers = new ArrayList<User>();
                userFollowers.put(follow.getFollowee(), followers);
            }

            if(!allUsers.contains(follow.getFollower())){
                allUsers.add(follow.getFollower());
            }



            followers.add(follow.getFollower());
        }



//        List<User> thing = new ArrayList<User>();
//        thing.add(testUser);
//
//        userFollowing.put(tweeterBot, thing);
//        userFollowers.put(tweeterBot, thing);
//        allUsers.add(tweeterBot);


        return;
    }

    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }


    /*
             --------------------- Initialize Statuses

  */
    private void initializeStatuses() {

        userStatuses = new HashMap<User, List<Status>>();

        for (User currentUser : allUsers) {
            if(currentUser.getAlias().equals("@tempAlias23")){
                System.out.println("found");
            }
            List<Status> statusList = new ArrayList<Status>();
            for(int i = 1; i < 5; i++){
                try {
                    statusList.add(new Status(currentUser, "Test status " + i));
                    Thread.sleep(1);
                }
                catch (Exception e){

                }
            }



            userStatuses.put(currentUser, statusList);
        }
    }

    /*
         --------------------- Initialize User Feeds

*/
    private void initializeFeeds(){

        userFeeds = new HashMap<User, List<Status>>();
//        System.out.println(userStatuses);

        for (Map.Entry<User, List<User>> entry : userFollowing.entrySet()) {
            User currentUser = entry.getKey();
            List<Status> statusList = new ArrayList<Status>();
            for (User following: entry.getValue()) {
                if(userStatuses.get(following) != null) {
                    for (int i = 0; i < userStatuses.get(following).size(); i++) {
                        statusList.add(userStatuses.get(following).get(i));

                    }
                }
            }
            userFeeds.put(currentUser, statusList);
        }
    }

    public Map<User, List<User>> getUserFollowing() {
        return userFollowing;
    }

    public Map<User, List<User>> getUserFollowers() {
        return userFollowers;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public User getTweeterBot() {
        return tweeterBot;
    }

    public User getTestUser() {
        return testUser;
    }

    public Map<User, List<Status>> getUserStatuses() {
        return userStatuses;
    }

    public Map<User, List<Status>> getUserFeeds() {
        return userFeeds;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }
}
