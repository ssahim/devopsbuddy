package com.devepsbuddy.test.integration;


import com.devepsbuddy.DevopsbuddyApplication;
import com.devepsbuddy.backend.persistence.domain.backend.Plan;
import com.devepsbuddy.backend.persistence.domain.backend.Role;
import com.devepsbuddy.backend.persistence.domain.backend.User;
import com.devepsbuddy.backend.persistence.domain.backend.UserRole;
import com.devepsbuddy.backend.persistence.repositories.PlanRepository;
import com.devepsbuddy.backend.persistence.repositories.RoleRepository;
import com.devepsbuddy.backend.persistence.repositories.UserRepository;
import com.devepsbuddy.utils.UsersUtils;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
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



    @Before
    public void init(){
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(roleRepository);
    }

    @Test
    public void testCreateNewPlan(){
        Plan basicPlan=createBasicPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        Optional<Plan> retrievedPlan=planRepository.findById(PlansEnum.BASIC.getId());

        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void createNewRole(){
       Role basicRole=createBasicRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Optional<Role> retrievedRole=roleRepository.findById(RolesEnum.BASIC.getId());

        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void createNewUser() throws Exception {

        Plan basicPlan = createBasicPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UsersUtils.createBasicUser();
        basicUser.setPlan(basicPlan);

        Role basicRole=createBasicRole(RolesEnum.BASIC);
        Set<UserRole> userRoles=new HashSet<>();
        UserRole userRole=new UserRole(basicUser,basicRole);

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


    private Role createBasicRole(com.devopsbuddy.enums.RolesEnum rolesEnum) {

        return new Role(rolesEnum);
    }

    private Plan createBasicPlan(com.devopsbuddy.enums.PlansEnum plansEnum) {

        return new Plan(plansEnum);
    }


}
