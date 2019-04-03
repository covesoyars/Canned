package edu.vcu.cmsc355.starter;
/**
 * Application class used to save information of who's logged in across all activities. contains
 * methods to mutate User object (from Edit_profile and Forgot_info) and retrieve the user object for
 * any activity that needs to access the information of who's logged int
 */

import android.app.Application;

public class UserLoggedIn extends Application {

    private User loggedIn;

    public void setLoggedIn(User aUser){
        loggedIn = aUser;
    }

    public User getLoggedIn(){
        return loggedIn;
    }

    public void logOut(){
        loggedIn = null;
    }

    /**
     * used to verifiy if user is a manager (will be important for launching correct hub page)
     * @return true if user is manager
     */

    public boolean isManager(){
        if(loggedIn instanceof Manager){
            return true;
        }
        else{
            return false;
        }
    }
}