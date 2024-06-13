package com.makersacademy.acebook;

import com.makersacademy.acebook.model.Friend;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRepository;
import com.makersacademy.acebook.repository.UserRepository;

import java.util.Optional;

public class Utils {

    public static String GetFriendStatus(String one, String two, UserRepository userRepository, FriendRepository friendRepository){
        User sender = userRepository.findByUsername(one);
        User recipient = userRepository.findByUsername(two);

        if (sender == null || recipient == null || sender.getId() == recipient.getId()) return "N/A";

        Optional<Friend> existingConnection = friendRepository.findBySenderAndRecipient(sender, recipient);
        if (existingConnection.isPresent()){
            if (existingConnection.get().isAccepted()) return "Friend";
            return "Sent";
        }
        existingConnection = friendRepository.findBySenderAndRecipient(recipient, sender);
        if (existingConnection.isPresent()){
            if (existingConnection.get().isAccepted()) return "Friend";
            return "Received";
        }
        return "None";
    }

    public static String GetFriendStatus(User one, User two, UserRepository userRepository, FriendRepository friendRepository){
        User sender = userRepository.findByUsername(one.getUsername());
        User recipient = userRepository.findByUsername(two.getUsername());

        if (sender == null || recipient == null || sender.getId() == recipient.getId()) return "N/A";

        Optional<Friend> existingConnection = friendRepository.findBySenderAndRecipient(sender, recipient);
        if (existingConnection.isPresent()){
            if (existingConnection.get().isAccepted()) return "Friend";
            return "Sent";
        }
        existingConnection = friendRepository.findBySenderAndRecipient(recipient, sender);
        if (existingConnection.isPresent()){
            if (existingConnection.get().isAccepted()) return "Friend";
            return "Received";
        }
        return "None";
    }
}