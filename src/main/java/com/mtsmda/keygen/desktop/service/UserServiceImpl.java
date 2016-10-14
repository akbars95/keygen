package com.mtsmda.keygen.desktop.service;

import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dminzat on 9/21/2016.
 */
@Service("userServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(KeygenServiceImpl.class);

    @Autowired
    @Qualifier("userRepositoryImpl")
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public CommonResponse<List<User>> getAllUsers() {
        return userRepository.getAllUsers();
    }
}