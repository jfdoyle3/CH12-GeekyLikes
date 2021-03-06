package com.careerdevs.geekylikes.payloads.response;


import com.careerdevs.geekylikes.entities.avatar.Avatar;
import com.careerdevs.geekylikes.entities.developer.Developer;
import com.careerdevs.geekylikes.entities.language.Language;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import java.util.Set;


public class FriendDeveloper extends PublicDeveloper{
    @JsonIncludeProperties({"id", "name"})
    private Set<Developer> friends;

    public FriendDeveloper(Long id, String name, Integer cohort, Set<Language> languages, Avatar avatar, Set<Developer> friends) {
        super(id, name, cohort, languages, avatar);
        this.friends = friends;
    }

    static public FriendDeveloper build(Developer developer) {
        Set<Developer> friends = developer.getRelationships();
        friends.addAll(developer.getInverseRelationships());
        return new FriendDeveloper(
                developer.getId(),
                developer.getName(),
                developer.getCohort(),
                developer.getLanguages(),
                developer.getAvatar(),
                friends
        );
    }

    public Set<Developer> getFriends() {
        return friends;
    }

    public void setFriends(Set<Developer> friends) {
        this.friends = friends;
    }
}
