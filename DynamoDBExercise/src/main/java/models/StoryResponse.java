package models;

import java.util.List;


public class StoryResponse extends PagedResponse {

    private List<Status> statusList;

    public StoryResponse(List<Status> statusList, boolean hasMorePages) {
        super(true, null, hasMorePages);
        this.statusList = statusList;
    }

    public StoryResponse(String message){
        super(false, message, true);
        this.statusList = null;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public String getMessage()
    {
        return super.getMessage();
    }
    public boolean isSuccess(){ return super.isSuccess(); }
}
