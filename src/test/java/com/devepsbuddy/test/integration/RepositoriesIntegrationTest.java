package com.devepsbuddy.test.integration;


import com.devepsbuddy.DevopsbuddyApplication;
import com.devepsbuddy.backend.persistence.domain.backend.Plan;
import com.devepsbuddy.backend.persistence.domain.backend.Role;
import com.devepsbuddy.backend.persistence.domain.backend.User;
import com.devepsbuddy.backend.persistence.domain.backend.UserRole;
import com.devepsbuddy.backend.persistence.repositories.PlanRepository;
import com.devepsbuddy.backend.persistence.repositories.RoleRepository;
import com.devepsbuddy.backend.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)
public class RepositoriesIntegrationTest {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    private static final int BASIC_PLAN_ID=1;
    private static final int BASIC_ROLE_ID=1;

    @Before
    public void init(){
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(roleRepository);
    }

    @Test
    public void testCreateNewPlan(){
        Plan basicPlan=createBasicPlan();
        planRepository.save(basicPlan);

        Optional<Plan> retrievedPlan=planRepository.findById(BASIC_PLAN_ID);

        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void createNewRole(){
       Role basicRole=createBasicRole();
        roleRepository.save(basicRole);

        Optional<Role> retrievedRole=roleRepository.findById(BASIC_ROLE_ID);

        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void createNewUser() throws Exception {

        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);

        User basicUser = createBasicUser();
        basicUser.setPlan(basicPlan);

        Role basicRole=createBasicRole();
        Set<UserRole> userRoles=new HashSet<>();
        UserRole userRole=new UserRole();
        userRole.setRole(basicRole);
        userRole.setUser(basicUser);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);

        for (UserRole ur: userRoles){
            roleRepository.save(ur.getRole());
        }

        basicUser=userRepository.save(basicUser);

        Optional<User> newlyCreatedUser=userRepository.findById(basicUser.getId());



        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.get().getId()!=0);
        Assert.assertNotNull(newlyCreatedUser.get().getPlan());
        Assert.assertNotNull(newlyCreatedUser.get().getPlan().getId());
        Set<UserRole> newlyCreatedUserUserRoles=newlyCreatedUser.get().getUserRoles();
        for (UserRole ur: newlyCreatedUserUserRoles){
            Assert.assertNotNull(ur.getRole());
            Assert.assertNotNull(ur.getRole().getId());
        }
    }


    private Role createBasicRole() {

        Role role=new Role();
        role.setId(BASIC_ROLE_ID);
        role.setName("BasicRole");
        return role;
    }

    private Plan createBasicPlan() {

        Plan plan=new Plan();

        plan.setId(BASIC_PLAN_ID);
        plan.setName("Basic");

        return plan;
    }

    public User createBasicUser(){
        User user=new User();
        user.setUsername("basicUser");
        user.setPassword("secret");
        user.setEmail("me@example.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNumber("123456");
        user.setCountry("GB");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blabla.images.com/basicuser");

        return user;
    }
}
