package com.devepsbuddy;

import com.devepsbuddy.backend.persistence.domain.backend.Role;
import com.devepsbuddy.backend.persistence.domain.backend.User;
import com.devepsbuddy.backend.persistence.domain.backend.UserRole;
import com.devepsbuddy.backend.service.UserService;
import com.devepsbuddy.utils.UserUtils;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DevopsbuddyApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DevopsbuddyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String username="proUser";
        String email="proUser@devopsbuddy.com";

        User user=UserUtils.createBasicUser(username, email);
        Set<UserRole> userRoles=new HashSet<>();
        userRoles.add(new UserRole(user, new Role(RolesEnum.BASIC)));

        userService.createUser(user, PlansEnum.PRO, userRoles);


    }
}
