import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;



public class UsersPerform {

    public SimpleDateFormat dateFormat;

    {
        new SimpleDateFormat("MM/dd/yyyy");
    }

    // All users.txt in the system is in the ArrayList<User>
    private ArrayList<User> users;

     //These variables are for signIn, signOut and the variances of these operations

    private boolean isLoggedIn;
    private String loggedUserUsername;
    private String loggedUserPassword;
    private int loggedUserIndex;


    public UsersPerform() {
        super();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        users = new ArrayList<User>();
        isLoggedIn = false;
        loggedUserPassword = null;
        loggedUserPassword = null;
        loggedUserIndex = -1;
    }

    public UsersPerform(String usersFile) {
        super();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        users = new ArrayList<User>();
        isLoggedIn = false;
        loggedUserPassword = null;
        loggedUserPassword = null;
        loggedUserIndex = -1;
        initializeUsers(usersFile);
    }

     //reads "users.txt" file and initializes all users.txt within the "users.txt.txt" file!
    // now initialize Users
    public void initializeUsers(String usersFile){
        FileReader fReader = null;
        BufferedReader bufReader = null;

        try{
            fReader = new FileReader(usersFile);
            bufReader = new BufferedReader(fReader);

            String line;

            while((line = bufReader.readLine()) != null){
                String[] tokens = line.split("\t");

                // First, going to create an date object for birthDay
                DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                Date dateOfBirth = format.parse(tokens[3]);

                // Now, going to create users.txt and add them to releated lists !
                int userID =  -1;
                if(this.users.size()!=0){
                    userID = this.users.get(this.users.size()-1).getUserID()+1;
                }else{
                    userID = 1;
                }

                User newUser = new User(userID, tokens[0], tokens[1], tokens[2], dateOfBirth, tokens[4]);
                this.users.add(newUser);
            }

            fReader.close();
            bufReader.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
// add User
    public void addUser(String name, String userName, String password, Date dateOfBirth, String schoolGraduated){
        System.out.println("-----------------------------");
        System.out.println("Command: ADDUSER\t" +name+"\t"+userName+"\t"+password+"\t"+dateFormat.format(dateOfBirth)+"\t"+schoolGraduated);

        int userID =  -1;
        if(this.users.size()!=0){ // if user size is different to 0
            userID = this.users.get(this.users.size()-1).getUserID()+1; // this will be executed
        }else{
            userID = 1; // else user ID will be egale to 1
        }

        User newUser = new User(userID, name, userName, password, dateOfBirth, schoolGraduated);

        this.users.add(newUser);
        System.out.println(name+" has been successfully added.");
    }
    // this will remove User
    public void removeUser(int userID){
        System.out.println("------------------------------");
        System.out.println("Command: REMOVEUSER "+userID);

        // users.txt are in a ArrayList,
        // so, i need the index of the user instance of with userID
        int index = getUserIndex(userID);

        if(index==-1){// if index equals (-1), means there's no such user
            System.out.println("No such user!");
        }else{
            // removing the user!
            this.users.remove(index);
            System.out.println("User has been successfully removed.");
        }
    }

            // here user will signe In With Name and password
    public void signIn(String userName, String password){
        System.out.println("---------------------------");
        System.out.println("Command: SIGNIN     "+userName+"\t"+password);


        int index = getUserIndex(userName);

        if(index == -1){// if Wrong userName
        }else if( !this.users.get(index).getPassword().equals(password)){ // if  Wrong password the following will be printed
            System.out.println("Invalid username or password! Please try again.");
        }else{// userName and password is valid !
            User loggedInUser = this.users.get(index);
             // if loggeIn is true this part will be executed
            this.isLoggedIn = true;
            this.loggedUserUsername = loggedInUser.getUsername();
            this.loggedUserPassword = loggedInUser.getPassword();
            this.loggedUserIndex = index;
            System.out.println("You have successfully signed in.");
        }
    }
      // User list
    public void listUsers(){
        if(this.isLoggedIn){
            System.out.println("-----------------------------");
            System.out.println("Command: LISTUSERS");

            for(int i=0;i<this.users.size();i++){
                listUser(i);
            }
        }else{                // not loggedIn
            System.out.println("Error: Please sign in and try again.");
        }
    }

       // for update Profile
    public void updateProfile(String name, Date dateOfBirth, String schoolGraduated){
        System.out.println("-------------------------------");
        System.out.println("Command: UPDATEPROFILE\t"+ name + "\t"+ dateFormat.format(dateOfBirth) + "\t" + schoolGraduated);

        if(this.isLoggedIn && this.loggedUserUsername!=null){
            int index = getUserIndex(this.loggedUserUsername);
            User temp = this.users.get(index);
            temp.setName(name);
            temp.setDateOfBirth(dateOfBirth);
            temp.setSchoolInformation(schoolGraduated);
            this.users.set(index, temp);
        }else{
            System.out.println("Error: Please sign in and try again.");
        }
    }
    // change Password
    public void changePassword(String oldPassword, String newPassword){
        System.out.println("------------------------------");
        System.out.println("Command: CHPASS\t" + oldPassword + "\t" + newPassword);

        if(this.isLoggedIn){
            if(this.loggedUserPassword.equals(oldPassword)){
                int index = getUserIndex(this.loggedUserUsername);
                User temp = this.users.get(index);
                temp.setPassword(newPassword);
                this.users.set(index, temp);
            }else{
                System.out.println("Password mismatch!");
            }
        }else{
            System.out.println("Error: Please sign in and try again.");
        }
    }

     // part of adding Friend
    public void addFriend(String userName){
        System.out.println("----------------------------------");
        System.out.println("Command: ADDFRIEND\t"+ userName);
        if(this.isLoggedIn){//  LoggedIn ?
            int index = getUserIndex(userName);
            if(index == -1){// is there such user with "userName"
                System.out.println("No such user!");
            }else if(isFriend(userName)){ // is user with "userName" already a friend ?
                System.out.println("This user is already in your friend list!");
            }else{
                // adding  as friend
                User loggedInUser = getLoggedInUser();
                ArrayList<String> friends = loggedInUser.getFriends();
                friends.add(userName);
                System.out.println(userName+" has been successfully added to your friend list.");
                loggedInUser.setFriends(friends);
                this.users.set(loggedUserIndex, loggedInUser);
            }
        }else{
            System.out.println("Error: Please sign in and try again.");
        }
    }

     // removes a friend from friendslist of current loggedIn user!
    public void removeFriend(String userName){
        System.out.println("--------------------------------");
        System.out.println("Command: REMOVEFRIEND\t"+ userName);

        if(this.isLoggedIn){// loggedIn?
            if(isFriend(userName)){// is friend ?

                // remove that friend !
                User loggedInUser = getLoggedInUser();
                ArrayList<String> friends = loggedInUser.getFriends();
                for(int i=0;i<friends.size();i++){
                    User tempFriend = getUser(friends.get(i));
                    if(tempFriend.getUsername().equals(userName)){
                        friends.remove(i);
                    }
                }
                loggedInUser.setFriends(friends);
                System.out.println(userName+" has been successfully removed from your friend list.");
            }else{
                System.out.println("No such friend!");
            }

        }else{
            System.out.println("Error: Please sign in and try again"); // Not logged in
        }
    }

    //list the friends of current LoggedIn user!

    public void listFriends(){
        System.out.println("--------------------------------");
        System.out.println("Command: LISTFRIENDS\t");

        if(this.isLoggedIn){ // is logged In ?
            User loggedInUser = getLoggedInUser();
            ArrayList<String> friendIDs = loggedInUser.getFriends();
            if(friendIDs.size()!=0){
                for(String friID:friendIDs){
                    listUser(getUser(friID));
                }
            }else{
                System.out.println("You haven�t added any friends yet!");
            }
        }else{
            System.out.println("Error: Please sign in and try again");
        }

    }
    public void addTextPost(String textContext, Double longitude, Double latitude, String taggedFriendUserNames){
        System.out.println("-----------------------------");
        System.out.println("Command: ADDPOST-TEXT\t" + textContext + "\t" + longitude + "\t" + latitude + "\t" + taggedFriendUserNames);

        // getting TaggedFriendList
        String[] taggedFriendsList = taggedFriendUserNames.split(":");
        if(this.isLoggedIn){
            // Logged In

            // Creating newTextPost with given parameters
            TextPost newTextPost = new TextPost();
            newTextPost.setPostID(new UUID(10000, 99999));
            newTextPost.setText(textContext);
            newTextPost.setLocation(new Location(latitude, longitude));

            // Getting localDate and initializing the Date field  of the post
            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
            Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            // Setting the date
            newTextPost.setOriginationDate(out);

            // Creating a  taggedFriendList
            ArrayList<String> taggedFriendArrayList = new ArrayList<String>();

            // Let's check the friend list !
            for(int i=0; i< taggedFriendsList.length;i++){
                if(isFriend(taggedFriendsList[i])){// if user with userName friend
                    // a friend, then add !
                    taggedFriendArrayList.add(taggedFriendsList[i]) ;
                }else{
                    // Not a friend !
                    System.out.println( taggedFriendsList[i]+ " is not your friend, and will not be tagged!");
                }
            }

            // Now we have to set taggedFriendArrayList in the newTextPost
            newTextPost.setTaggedUsers(taggedFriendArrayList);

            // Getting the LoggedInUser instance and setting them !
            User loggedUser = getLoggedInUser();
            ArrayList<Post> tempPostList =  loggedUser.getPosts();
            tempPostList.add(newTextPost);
            loggedUser.setPosts(tempPostList);

            this.users.set(loggedUserIndex, loggedUser);

            System.out.println("The post has been successfully added.");
        }else{
            System.out.println("Error: Please sign in and try again.");
        }
    }


    public void addImagePost(String textContext, Double longitude, Double latitude, String taggedFriendUserNames, String filePath, int imageWidth, int imageHeight){
        String[] taggedFriendsList = taggedFriendUserNames.split(":");
        System.out.println("-------------------------------");
        System.out.println("Command: ADDPOST-IMAGE\t" + textContext + "\t" + longitude + "\t" + latitude + "\t" + taggedFriendUserNames + "\t" + filePath + "\t" + imageWidth+"x"+imageHeight);

        if(this.isLoggedIn){
            ImagePost newImagePost = new ImagePost();
            newImagePost.setPostID(new UUID(10000, 99999));
            newImagePost.setText(textContext);
            newImagePost.setLocation(new Location(latitude, longitude));
            newImagePost.setImageFileName(filePath);
            newImagePost.setImageWidth(imageWidth);
            newImagePost.setImageHeight(imageHeight);

            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
            Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

            newImagePost.setOriginationDate(out);

            ArrayList<String> taggedFriendArrayList = new ArrayList<String>();

            // Let's check the friend list !
            for(int i=0; i< taggedFriendsList.length;i++){
                if(isFriend(taggedFriendsList[i])){
                    //taggedFriendsList.
                    taggedFriendArrayList.add(taggedFriendsList[i]) ;
                }else{
                    System.out.println("Username " + taggedFriendsList[i]+ " is not your friend, and will not be tagged!");
                }
            }

            // Now we have to taggedFriendArrayList
            newImagePost.setTaggedUsers(taggedFriendArrayList);

            User loggedUser = getLoggedInUser();
            ArrayList<Post> tempPostList =  loggedUser.getPosts();
            tempPostList.add(newImagePost);
            loggedUser.setPosts(tempPostList);

            this.users.set(getUserIndex(loggedUser.getUserID()), loggedUser);

            System.out.println("The post has been successfully added.");
        }else{
            System.out.println("Error: Please sign in and try again.");
        }
    }

    public void addVideoPost(String textContext, Double longitude, Double latitude, String taggedFriendUserNames, String filePath, int videoDuration){
        String[] taggedFriendsList = taggedFriendUserNames.split(":");
        System.out.println("-------------------------------");
        System.out.println("Command: ADDPOST-VIDEO\t" + textContext + "\t" + longitude + "\t" + latitude + "\t" + taggedFriendUserNames + "\t" + filePath + "\t" + videoDuration);

        if(this.isLoggedIn){
            VideoPost newVideoPost = new VideoPost();
            newVideoPost.setPostID(new UUID(10000, 99999));
            newVideoPost.setText(textContext);
            newVideoPost.setLocation(new Location(latitude, longitude));
            newVideoPost.setVideoFilename(filePath);
            newVideoPost.setMaximumVideoLength(videoDuration);

            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
            Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

            newVideoPost.setOriginationDate(out);

            ArrayList<String> taggedFriendArrayList = new ArrayList<String>();

            // Let's check the friend list !
            for(int i=0; i< taggedFriendsList.length;i++){
                if(isFriend(taggedFriendsList[i])){
                    //taggedFriendsList.
                    taggedFriendArrayList.add(taggedFriendsList[i]) ;
                }else{
                    System.out.println("Username " + taggedFriendsList[i]+ " is not your friend, and will not be tagged!");
                }
            }

            // Now we have to taggedFriendArrayList
            newVideoPost.setTaggedUsers(taggedFriendArrayList);

            User loggedUser = getLoggedInUser();
            ArrayList<Post> tempPostList =  loggedUser.getPosts();
            tempPostList.add(newVideoPost);
            loggedUser.setPosts(tempPostList);

            this.users.set(getUserIndex(loggedUser.getUserID()), loggedUser);

            System.out.println("The post has been successfully added.");
        }else{
            System.out.println("Error: Please sign in and try again.");
        }
    }

     //removes the last Post of the loggedInUser instance

    public void removeLastPost(){
        System.out.println("------------------------------");
        System.out.println("Command: REMOVELASTPOST" );

        //Checking if user loggedIn
        if(this.isLoggedIn){
            // LoggedIn - Checking if user has any posts
            User loggedInUser= getLoggedInUser();
            if(loggedInUser.getTotalPostNumber() > 0){
                // Removing last post
                ArrayList<Post> tempPostList = loggedInUser.getPosts();
                tempPostList.remove(tempPostList.size()-1);
                loggedInUser.setPosts(tempPostList);
                System.out.println("Your last post has been successfully removed.");
                // Set loggedUser
                this.users.set(this.loggedUserIndex, loggedInUser);
            }else{
                // loggedIn user has zero posts
                System.out.println("Error: You don't have any posts");
            }
        }else{
            // Not loggedIN
            System.out.println("Error: Please sign in and try again.");
        }
    }

    public void showPosts(String userName){
        System.out.println("--------------------------------");
        System.out.println("Command: SHOWPOSTS\t"+ userName );

        // No need to be loggedIn

        // Check if such user exits with username "userName"
        if(isUser(userName)){
            System.out.println("**************\n"+userName+"'s Posts\n**************");
            User tempUser = getUser(userName);
            ArrayList<Post> tempPosts = tempUser.getPosts();
            for(Post pst:tempPosts){
                String temp = pst.toString();

                int taggedUsersSize = pst.getTaggedUsers().size();
                if(taggedUsersSize>0){
                    temp  = temp + "\nFriends tagged in this post: ";
                }

                for(int i= 0; i < taggedUsersSize;i++){
                    temp = temp + getUser(pst.getTaggedUsers().get(i)).getName();
                    if(i < taggedUsersSize-1){
                        temp = temp+ ", ";
                    }
                }

                temp = temp +"\n-----------------------------";
                System.out.println(temp);




            }
        }else{
            System.out.println("No such user!");
        }
    }

    /**
     * blocks the user with given param. userName
     * @param userName
     */
    public void block(String userName){
        System.out.println("-------------------------------");
        System.out.println("Command: BLOCK\t"+ userName );

        if(isLoggedIn){
            // LoggedIn
            // Let's check if userName is valid ?
            if(isUser(userName)){
                // a user
                User loggedInUser = getLoggedInUser();
                ArrayList<String> blockedUser = loggedInUser.getBlockedUsers();
                blockedUser.add(userName);
                loggedInUser.setBlockedUsers(blockedUser);
                this.users.set(this.loggedUserIndex, loggedInUser);

                System.out.println(userName+" has been successfully blocked.");
            }else{
                // not a user
                System.out.println("No such user!");
            }
        }else{
            // Not logged In
            System.out.println("Error: Please sign in and try again.");
        }
    }
    public void showBlockedUsers(){
        System.out.println("-----------------------------");
        System.out.println("Command: SHOWBLOCKEDUSERS");


        if(isLoggedIn){
            // LoggedIn
            User loggedInUser = getLoggedInUser();
            ArrayList<String> blockedUsers = loggedInUser.getBlockedUsers();

            // Checking if there's any blockedUsers
            if(blockedUsers.size() == 0){
                System.out.println("You haven�t blocked any users.txt yet!");
            }else{
                for(String blockedUserName:blockedUsers){

                    User tempUser = getUser(blockedUserName);

                    if(tempUser != null){
                        System.out.println(tempUser.toString());
                        System.out.println("------------------------------");
                    }
                }
            }
        }else{
            // Not loggedIn
            System.out.println("Error: Please sign in and try again.");
        }
    }
    public void showBlockedFriends(){
        System.out.println("-----------------------------");
        System.out.println("Command: SHOWBLOCKEDFRIENDS");


        if(isLoggedIn){
            // LoggedIn
            User loggedInUser = getLoggedInUser();
            ArrayList<String> blockedUsers = loggedInUser.getBlockedUsers();

            // Checking if there's any blockedUsers
            if(blockedUsers.size() == 0){
                System.out.println("You haven�t blocked any users.txt yet!");
            }else{
                for(String blockedUserName:blockedUsers){

                    User tempUser = getUser(blockedUserName);

                    if(tempUser != null){
                        if(isFriend(tempUser.getUsername())){
                            System.out.println(tempUser.toString());
                            System.out.println("-------------------------------");
                        }
                    }
                }
            }
        }else{
            // Not loggedIn
            System.out.println("Error: Please sign in and try again.");
        }
    }

    public void unblock(String userName){
        System.out.println("-------------------------------");
        System.out.println("Command: UNBLOCK \t" + userName);

        if(isLoggedIn){
            // LoggedIn
            User loggedInUser = getLoggedInUser();
            ArrayList<String> blockedUsers  = loggedInUser.getBlockedUsers();

            // Checking if user with "userName" is blocked or not !
            boolean isBlocked = false;
            for(String usrName:blockedUsers){
                if(usrName.equals(userName)){
                    // user with "userName" is blocked
                    // Let's unblock
                    blockedUsers.remove(usrName);
                    isBlocked = true;
                    break;
                }
            }

            if(isBlocked){
                System.out.println( userName + " has been successfully unblocked.");
            }else{
                System.out.println("No such user in your blocked-users list!");
            }
        }else{
            // Not LoggedIn
            System.out.println("Error: Please sign in and try again.");
        }
    }

    public void signOut(){
        System.out.println("------------------------------");
        System.out.println("Command: SIGNOUT"  );

        isLoggedIn = false;
        loggedUserPassword = null;
        loggedUserIndex = -1;
        System.out.println("You have successfully signed out.");
    }


    public int  getUserIndex(int userId){
        int index = -1;

        for(int i=0;i<this.users.size();i++){
            if(this.users.get(i).getUserID() == userId){
                index = i;
                break;
            }
        }
        return index;
    }


    public int getUserIndex(String userName){
        int index = -1;

        for(int i=0;i<this.users.size();i++){
            if(this.users.get(i).getUsername().equals(userName)){
                index = i;
                break;
            }
        }
        return index;
    }

    public boolean isFriend(String userName){
        ArrayList<String> friends = getLoggedInUser().getFriends();
        for(String friendUserName:friends){
            User temp = getUser(friendUserName);
            if(temp.getUsername().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public User getLoggedInUser(){
        return this.users.get(this.loggedUserIndex);
    }


    public User getUser(int userID){
        int index = getUserIndex(userID);
        if(index != -1){
            return this.users.get(index);
        }
        return null;
    }


    public User getUser(String userName){
        int index = getUserIndex(userName);
        if(index != -1){
            return this.users.get(index);
        }
        return null;

    }

    public void listUser(int index){
        listUser(this.users.get(index));
    }


    public void listUser(User usr){
        System.out.println(usr.toString());
        System.out.println("-------------------------------");
    }


    public boolean isUser(String userName){
        int index = getUserIndex(userName);

        if(index < 0){
            return false;
        }

        return true;
    }


    public boolean isUser(int userId){
        int index = getUserIndex(userId);

        if(index < 0){
            return false;
        }

        return true;
    }

}
