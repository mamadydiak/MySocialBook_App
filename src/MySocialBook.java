import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySocialBook {

    public static void main(String[] args){
        //Here we are Assigning args to variables ( args[0] = users.txt and args[1] = commands.txt)
        String users = args[0];
        String commands = args[1];

        // Creating userCheck and initializing all default users.txt into the system
        UsersPerform userCheck = new UsersPerform();
        userCheck.initializeUsers(users);


        // Here it will start to read from "commands.txt" file and execution of commands

        FileReader fReader;
        BufferedReader bufReader;

        try{
            fReader = new FileReader(commands);
            bufReader = new BufferedReader(fReader);

            String line;

            while((line = bufReader.readLine()) != null){
                String[] tokens = line.split("\t");
                try{
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date dateOfBirth = new Date();
                    dateFormat.clone();
                    // Here Each value is called a case , and the variable being switched on is checked for each case
                    switch(tokens[0]){
                        case "ADDUSER":
                            dateOfBirth = dateFormat.parse(tokens[4]);
                            userCheck.addUser(tokens[1], tokens[2], tokens[3], dateOfBirth, tokens[5]);
                            break;
                        case "REMOVEUSER":
                            userCheck.removeUser(Integer.parseInt(tokens[1]));
                            break;

                        case "SIGNIN":
                            userCheck.signIn(tokens[1], tokens[2]);
                            break;
                        case "LISTUSERS":
                            userCheck.listUsers();
                            break;
                        case "UPDATEPROFILE":
                            dateOfBirth = dateFormat.parse(tokens[2]);
                            userCheck.updateProfile(tokens[1], dateOfBirth, tokens[3]);
                            break;
                        case "CHPASS":
                            userCheck.changePassword(tokens[1], tokens[2]);
                            break;
                        case "ADDFRIEND":
                            userCheck.addFriend(tokens[1]);
                            break;
                        case "REMOVEFRIEND":
                            userCheck.removeFriend(tokens[1]);
                            break;
                        case "LISTFRIENDS":
                            userCheck.listFriends();
                            break;
                        case "ADDPOST-TEXT":
                            userCheck.addTextPost(tokens[1], Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), tokens[4]);
                            break;
                        case "ADDPOST-IMAGE":
                            String[] resolution = tokens[6].split("x");
                            int imageWidth = Integer.parseInt(resolution[0]);
                            int imageHeight = Integer.parseInt(resolution[1]);
                            userCheck.addImagePost(tokens[1], Double.parseDouble(tokens[2]),
                                    Double.parseDouble(tokens[3]), tokens[4],
                                    tokens[5], imageWidth, imageHeight);
                            break;
                        case "ADDPOST-VIDEO":
                            userCheck.addVideoPost(tokens[1], Double.parseDouble(tokens[2])
                                    , Double.parseDouble(tokens[3]), tokens[4]
                                    , tokens[5], Integer.parseInt(tokens[6]));
                            break;
                        case "REMOVELASTPOST":
                            userCheck.removeLastPost();
                            break;
                        case "SHOWPOSTS":
                            userCheck.showPosts(tokens[1]);
                            break;
                        case "BLOCK":
                            userCheck.block(tokens[1]);
                            break;
                        case "SHOWBLOCKEDFRIENDS":
                            userCheck.showBlockedFriends();
                            break;
                        case "SHOWBLOCKEDUSERS":
                            userCheck.showBlockedUsers();
                            break;
                        case "UNBLOCK":
                            userCheck.unblock(tokens[1]);
                            break;
                        case "SIGNOUT":
                            userCheck.signOut();
                            break;
                        default:
                            throw new IOException("There's no command type such as :" + tokens[0]);
                    }
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }

            fReader.close();
            bufReader.close();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}