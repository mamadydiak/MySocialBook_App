import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

// Here abstract class is used to provide common method the class post will implements postInterface
public abstract class Post implements PostInterface{

    protected UUID postID;
    protected String text;
    protected Date originationDate;
    protected ArrayList<String> taggedUsers;
    protected Location location;

    public abstract void setPostID(UUID postID);

    public abstract void setOriginationDate(Date originationDate);

    public abstract ArrayList<String> getTaggedUsers() ;

    public abstract void setTaggedUsers(ArrayList<String> taggedUsers) ;

    public abstract Location getLocation() ;

    public abstract void setLocation(Location location);
}