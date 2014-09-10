package se.callista.oauth.socialmedia.demo.model;

/**
 * Created by magnus on 18/08/14.
 */
public class UserProfile {

    private final String userId;

    private String name;

    private final String firstName;

    private final String lastName;

    private final String email;

    private final String username;

    public UserProfile(String userId, String name, String firstName, String lastName, String email, String username) {
        this.userId = userId;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;

        fixName();
    }

    private void fixName() {
        // Is the name null?
        if (name == null) {

            // Ok, lets try with first and last name...
            name = firstName;

            if (lastName != null) {
                if (name == null) {
                    name = lastName;
                } else {
                    name += " " + lastName;
                }
            }

            // Try with email if still null
            if (name == null) {
                name = email;
            }

            // Try with username if still null
            if (name == null) {
                name = username;
            }

            // If still null set name to UNKNOWN
            if (name == null) {
                name = "UNKNOWN";
            }
        }
    }

    public UserProfile(String userId, org.springframework.social.connect.UserProfile up) {
        this.userId = userId;
        this.name = up.getName();
        this.firstName = up.getFirstName();
        this.lastName = up.getLastName();
        this.email = up.getEmail();
        this.username = up.getUsername();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String toString() {
        return
            "name = " + name +
            ", firstName = " + firstName +
            ", lastName = " + lastName +
            ", email = " + email +
            ", username = " + username;
    }
}