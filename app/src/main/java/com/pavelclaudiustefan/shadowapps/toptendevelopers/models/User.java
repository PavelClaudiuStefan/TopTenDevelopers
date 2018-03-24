package com.pavelclaudiustefan.shadowapps.toptendevelopers.models;

/**
 * Created by Claudiu on 23-Mar-18.
 */

public class User {

    private String name;
    private String profileImageUrl;
    private String location;
    private int goldBadgesCount;
    private int silverBadgesCount;
    private int bronzeBadgesCount;

    public User(String name, String profileImageUrl, String location) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.location = location;
    }

    public void setBadgesCount(int goldBadgesCount, int silverBadgesCount, int bronzeBadgesCount) {
        this.goldBadgesCount = goldBadgesCount;
        this.silverBadgesCount = silverBadgesCount;
        this.bronzeBadgesCount = bronzeBadgesCount;
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
}
