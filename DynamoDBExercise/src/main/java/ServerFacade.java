
import models.Follow;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    private List<Follow> follows;
    private List<User> userList;

    public ServerFacade(){
        initializeFollowees();
    }

    public List<Follow> makeFollowers() {
        return follows;
    }

    public List<User> makeUsers() {
        return userList;
    }

    /**
     * Generates the followee data.
     */
    private void initializeFollowees() {

        follows = getFollowGenerator().generateUsersAndFollows(30,
                1, 30, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);
        userList = new ArrayList<User>();
        for (Follow follow: follows) {
            if(!userList.contains(follow.getFollowee())){
                userList.add(follow.getFollowee());
            }
            if(!userList.contains(follow.getFollower())){
                userList.add(follow.getFollower());
            }
        }
    }

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }
}
