package com.cheeper.buildbulk.web;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/build-bulk")
public class BuilBulkController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CheepRepository cheepRepository;

    @GetMapping("/user")
    public String process() {

        List<User> users = new ArrayList<>();

        for (int i = 2 ; i <= 10000; i++) {
            User user = new User();
            user.setId(i);
            user.setPassword("123");
            user.setBio("Gerado pelo build bulk");
            user.setVerifiedEmail(true);

            Faker faker = new Faker();
            String fullName = faker.name().fullName();
            String profileName = faker.name().username();

            user.setName(fullName);
            user.setProfileName(profileName.replace(".", "_").concat(""+i));
            user.setEmail(profileName.replace(".", "").concat("@cheeper.com"));

            users.add(user);

            // de 30 em 30...
            if(i % 30 == 0) {
                userRepository.saveAll(users);
                users.clear();
            }
        }
        if(users.size() > 0) userRepository.saveAll(users);

        return "Usuários cadastrados";
    }

    @GetMapping("/cheeps")
    public String cheeps() {
        List<Cheep> cheeps = new ArrayList<>();

        AtomicLong cheepId = new AtomicLong(2);
        userRepository.findAll().stream().forEach(user -> {
            int numberOfCheepsPerUser = getRandomIntegerBetweenRange(2, 5);

            for (int i = 2 ; i <= numberOfCheepsPerUser; i++) {
                Cheep cheep = new Cheep();
                cheep.setId(cheepId.getAndIncrement());
                cheep.setMessage(new Faker().lorem().sentence(10));
                cheep.setProfile(user);

                cheeps.add(cheep);

            }
            if (cheepId.intValue() % 30 == 0) {
                cheepRepository.saveAll(cheeps);
                cheeps.clear();
            }
        });

        if(cheeps.size() > 0) cheepRepository.saveAll(cheeps);

        return "Cheeps cadastrados";
    }

    @GetMapping("/follow")
    public String follow() {

        userRepository.findAll().stream().forEach(user -> {
            int numberOfFollowers = getRandomIntegerBetweenRange(1, 5);

            for (int j = 1 ; j < numberOfFollowers; j++) {
                User idFollower = userRepository.getOne(getRandomIntegerBetweenRange(1, 999));

                if(user.getId() != idFollower.getId() && !user.getFollowing().contains(idFollower)) {
                    user.follow(idFollower);
                    userRepository.save(user);
                }
            }
        });

        return "Relacionados";
    }

    private static int getRandomIntegerBetweenRange(int min, int max){
        return (int)(Math.random()*((max-min)+1))+min;
    }
}
