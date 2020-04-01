package models;


public class FollowerRequest {

    public String followee;
    public int limit;
    public String lastFollower;
    public String authToken;


    public FollowerRequest(String followee, int limit, String lastFollower) {
        this.followee = followee;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    public FollowerRequest(){}

    public String getFollower() {
        return followee;
    }

    public int getLimit() {
        return limit;
    }

    public String getLastFollowee() {
        return lastFollower;
    }

    public String getAuthToken() {
        return authToken;
    }
}
