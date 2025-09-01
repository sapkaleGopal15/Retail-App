package Inventory.Exception;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){

        System.out.println("Getting data from local database");
        return authentication.getName();
    }
}
