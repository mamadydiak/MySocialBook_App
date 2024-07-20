import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class User {

    private int userID;
    private String name;
    private String username;
    private String password;
    private Date dateOfBirth;
    private String schoolInformation;
    private Date lastLoginDate;
    private ArrayList<String> friends;
    private ArrayList<String> blockedUsers;
    private ArrayList<Post> posts;
    // the constructor for user
    public User(int userID, String name, String username, String password, Date dateOfBirth, String schoolInformation) {
        super();
        this.userID = userID;
        this.name = name;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.schoolInformation = schoolInformation;
        this.lastLoginDate = new Date();
        this.friends = new ArrayList<String>();
        this.blockedUsers = new ArrayList<String>();
        this.posts = new ArrayList<Post>();
    }

    public User() {

        super();
    }

// this part is for get and set all information about User
    public int getUserID() {

        return userID;
    }

    public void setUserID(int userID) {

        this.userID = userID;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Date getDateOfBirth() {

        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {

        this.dateOfBirth = dateOfBirth;
    }

    public String getSchoolInformation() {

        return schoolInformation;
    }

    public void setSchoolInformation(String schoolInformation) {

        this.schoolInformation = schoolInformation;
    }

    public Date getLastLoginDate() {

        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {

        this.lastLoginDate = lastLoginDate;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {

        this.friends = friends;
    }

    public ArrayList<String> getBlockedUsers() {

        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<String> blockedUsers) {

        this.blockedUsers = blockedUsers;
    }

    public ArrayList<Post> getPosts() {

        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {

        this.posts = posts;
    }

    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String instanceString = "Name: ";
        instanceString = instanceString + this.name+"\n";
        instanceString = instanceString + "Username: " + this.username + "\n";
        instanceString = instanceString + "Date of Birth: " + dateFormat.format(this.dateOfBirth) + "\n";
        instanceString = instanceString + "School: " + this.schoolInformation ;
        return instanceString;
    }

    public int getTotalPostNumber(){

        return posts.size();
    }
}
