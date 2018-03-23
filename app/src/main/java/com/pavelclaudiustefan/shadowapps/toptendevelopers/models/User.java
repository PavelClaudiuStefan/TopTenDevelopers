package com.pavelclaudiustefan.shadowapps.toptendevelopers.models;

import java.util.ArrayList;

/**
 * Created by Claudiu on 23-Mar-18.
 */

public class User {

    private int accountId;
    private String name;
    private String profileImageUrl;
    private String location;
    private int goldBadgesCount;
    private int silverBadgesCount;
    private int bronzeBadgesCount;

    public User(int accountId, String name, String profileImageUrl, String location) {
        this.accountId = accountId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.location = location;
    }

    public void setBadgesCount(int goldBadgesCount, int silverBadgesCount, int bronzeBadgesCount) {
        this.goldBadgesCount = goldBadgesCount;
        this.silverBadgesCount = silverBadgesCount;
        this.bronzeBadgesCount = bronzeBadgesCount;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getLocation() {
        return location;
    }

    public int getGoldBadgesCount() {
        return goldBadgesCount;
    }

    public int getSilverBadgesCount() {
        return silverBadgesCount;
    }

    public int getBronzeBadgesCount() {
        return bronzeBadgesCount;
    }



    // TODO - remove testing code
    private static ArrayList<User> users = new ArrayList<>();

    static {
        User tempUser;

        tempUser = new User(1, "User 1", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(2, "User 2", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(3, "User 3", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(4, "User 4", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(5, "User 5", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(6, "User 6", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(7, "User 7", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(8, "User 8", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(9, "User 9", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);

        tempUser = new User(10, "User 10", "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG", "Bucharest, Romania");
        tempUser.setBadgesCount(1, 10, 100);
        users.add(tempUser);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
