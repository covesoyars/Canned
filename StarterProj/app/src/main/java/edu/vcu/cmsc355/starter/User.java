package edu.vcu.cmsc355.starter;

/**
 * @Version: 0.1 of User class (parent of Manager and Volunteer)
 * has getters and setters for all instance variables
 * Needs:
 *  implementation of forgot password framework
 *
 *
 *
 * @contributors: Cove Soyars,
 */
import java.io.File;
import org.apache.commons.lang3.RandomStringUtils;



public class User{

    private String password;
    private String userName;
    private String firstName;
    private String lastName;
    private int dob;
    private String emailAddress;
    private File profilePicture;


    public User() {
        setDob(12071941);
        setFirstName("Jimbo");
        setLastName("David");
        setUserName("jimbo_dave");
        setEmailAddress("soyarsc@vcu.edu"); // my email for testing
        setPassword("password1");
        setProfilePicture(null);
    }
    /*
    Constructor to be called by sign up activity if the profile picture field in this activity is
    blank, the default profile picture will be passed to the constructor. If the username entered
    in the username field is already taken, or if any information is missing, the activity will
    not call the constructor and prompt for corrected input.
     */
    public User(String aPassword, String aUserName, String aFirstName, String aLastName, int aDob,
                String anEmail, File aPicture){

        setDob(aDob);
        setEmailAddress(anEmail);
        setPassword(aPassword);
        setUserName(aUserName);
        setFirstName(aFirstName);
        setLastName(aLastName);
        setProfilePicture(aPicture);

    }

    /**
     * Generate a random password of length 10
     * @return a random string with both letters and numbers
     */
    private String generatePassword(){
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return null;
    }

    public void forgotPassword(){
        String newPassword = generatePassword();
        setPassword(newPassword);
        /**
         * TO-DO: SEND NEW PASSWORD TO USER EMAIL
         */
    }

    // getters and setters
    public void setPassword(String aPassword){
        password = aPassword;
    }
    public String getPassword(){
        return password;
    }
    public void setLastName(String aName){
        lastName = aName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setUserName(String aUserName){
        userName = aUserName;
    }
    public String getUserName(){
        return userName;
    }
    public void setFirstName(String aName){
        firstName = aName;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setDob(int aDob){
        dob = aDob;
    }
    public int getDob(){
        return dob;
    }
    public void setEmailAddress(String anEmail){
        emailAddress = anEmail;
    }
    public String getEmailAddress(){
        return emailAddress;
    }
    public void setProfilePicture(File aPicture){
        profilePicture = aPicture;
    }
    public File getProfilePicture(){
        return profilePicture;
    }

}