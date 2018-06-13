package com.devepsbuddy.test.integration;


import com.devepsbuddy.DevopsbuddyApplication;
import com.devepsbuddy.backend.persistence.domain.backend.Plan;
import com.devepsbuddy.backend.persistence.domain.backend.Role;
import com.devepsbuddy.backend.persistence.domain.backend.User;
import com.devepsbuddy.backend.persistence.domain.backend.UserRole;
import com.devepsbuddy.backend.persistence.repositories.PlanRepository;
import com.devepsbuddy.backend.persistence.repositories.RoleRepository;
import com.devepsbuddy.backend.persistence.repositories.UserRepository;
import com.devepsbuddy.utils.UserUtils;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Rule public TestName testName=new TestName();


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

        String username=testName.getMethodName();
        String email=testName.getMethodName()+"@devopsbuddy.com";

        User basicUser= createUser(username, email);

            basicUser=userRepository.save(basicUser);

            Optional<User> newlyCreatedUser=userRepository.findById(basicUser.getId());

            Assert.assertNotNull(newlyCreatedUser);
            Assert.assertTrue(newlyCreatedUser.get().getId() != 0);
            Assert.assertNotNull(newlyCreatedUser.get().getPlan());
            Assert.assertNotNull(newlyCreatedUser.get().getPlan().getId());
            Set<UserRole> newlyCreatedUserRoles=newlyCreatedUser.get().getUserRoles();
            for (UserRole ur: newlyCreatedUserRoles){
                Assert.assertNotNull(ur.getRole());
                Assert.assertNotNull(ur.getRole().getId());
            }
        }

        @Test
    public void testDeleteUser() throws Exception{

        String username=testName.getMethodName();
        String email=testName.getMethodName()+"@devopsbuddy.com";

        User basicUser= createUser(username, email);
        userRepository.delete(basicUser);
    }


//        Private methods

    private User createUser(String username, String email) {

        Plan plan= createBasicPlan(PlansEnum.BASIC);
        planRepository.save(plan);


        User basicUser=UserUtils.createBasicUser(username, email);
        basicUser.setPlan(plan);

        Role basicRole=createBasicRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Set<UserRole> userRoles=new HashSet<>();
        UserRole userRole=new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);
        basicUser=userRepository.save(basicUser);

        return basicUser;





    }


    private Role createBasicRole(com.devopsbuddy.enums.RolesEnum rolesEnum) {

        return new Role(rolesEnum);
    }

    private Plan createBasicPlan(com.devopsbuddy.enums.PlansEnum plansEnum) {

        return new Plan(plansEnum);
    }


}
