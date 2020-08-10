package com.cheeper.buildbulk;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

        for (int i = 2 ; i <= 1000; i++) {
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

            // Se sobrou alguém depois do ultimo bach...
            if(users.size() > 0) {
                userRepository.saveAll(users);
                users.clear();
            }
        }

        return "Usuários cadastrados";
    }

    @GetMapping("/cheeps")
    public String cheeps() {
        userRepository.findAll().stream().forEach(user -> {
            int numberOfCheepsPerUser = getRandomIntegerBetweenRange(1, 10);

            for (int i = 1 ; i < numberOfCheepsPerUser; i++) {
                Cheep cheep = new Cheep();
                cheep.setMessage(new Faker().lorem().sentence(10));
                cheep.setProfile(user);
                this.cheepRepository.save(cheep);
            }
        });

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
