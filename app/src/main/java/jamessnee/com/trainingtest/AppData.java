package jamessnee.com.trainingtest;

/**
 * Created by jamessnee on 9/09/15.
 * Singleton class to store all the AppData
 */
public class AppData {

    private static AppData instance;

    private AppData() {
        this.instance = instance;
    }

    public static AppData getInstance() {

        if (instance == null){

            instance = new AppData();

        }

        return instance;
    }

    public void authenticate(String emailAddress) {



    }
}
