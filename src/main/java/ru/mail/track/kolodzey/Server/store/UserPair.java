package ru.mail.track.kolodzey.Server.store;

import java.util.Set;

/**
 * Created by DKolodzey on 24.01.16.
 */
public class UserPair {
    public static class InvalidNumberOfUsers extends Exception {
        InvalidNumberOfUsers(String message) {
            super(message);
        }
    }
    public Integer userId1;
    public Integer userId2;
    public int hashCode() {
        return userId1 + userId2;
    }
    public UserPair(Set<Integer> participants) throws InvalidNumberOfUsers {
        if (participants.size() > 2) {
            throw new InvalidNumberOfUsers(participants.size() + " is too many to create pair");
        }
        if (participants.size() < 1) {
            throw new InvalidNumberOfUsers(participants.size() + " is too few to create pair");
        }
        Integer[] participantArray = new Integer[2];
        participants.toArray(participantArray);
        this.userId1 = participantArray[0];
        this.userId2 = participantArray[1] != null ? participantArray[1] : participantArray[0];
        normalizeOrder();
    }

    private void normalizeOrder() {
        if (this.userId1 > this.userId2) {
            Integer tmp = this.userId1;
            this.userId1 = this.userId2;
            this.userId2 = tmp;
        }
    }

    public UserPair(Integer userId1, Integer userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        normalizeOrder();
    }

    public boolean equals(UserPair other) {
        return (userId1 == other.userId1) && (userId2 == other.userId2);
    }
}
