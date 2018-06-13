package com.devepsbuddy.backend.service;

import ch.qos.logback.core.pattern.color.BoldCyanCompositeConverter;
import com.devepsbuddy.backend.persistence.domain.backend.Plan;
import com.devepsbuddy.backend.persistence.domain.backend.User;
import com.devepsbuddy.backend.persistence.domain.backend.UserRole;
import com.devepsbuddy.backend.persistence.repositories.PlanRepository;
import com.devepsbuddy.backend.persistence.repositories.RoleRepository;
import com.devepsbuddy.backend.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public User createUser(User user, com.devopsbuddy.enums.PlansEnum plansEnum, Set<UserRole> userRoles){

        String encryptedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        Plan plan=new Plan(plansEnum);

        if (!planRepository.existsById(plansEnum.getId())){
            plan=planRepository.save(plan);
        }

        user.setPlan(plan);

        for (UserRole ur: userRoles){
            roleRepository.save(ur.getRole());
        }

        user.getUserRoles().addAll(userRoles);

        user=userRepository.save(user);

        return user;
    }

}
