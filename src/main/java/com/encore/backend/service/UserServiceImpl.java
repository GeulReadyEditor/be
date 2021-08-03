package com.encore.backend.service;

import java.util.ArrayList;
import java.util.UUID;

import com.encore.backend.dto.UserDto;
import com.encore.backend.repository.UserRepository;
import com.encore.backend.vo.UserVO;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserVO user = mapper.map(userDto, UserVO.class);
        user.setFollowers(new ArrayList<String>());
        user.setFollowings(new ArrayList<String>());
        user.setLikes(new ArrayList<String>());
        user.setScraps(new ArrayList<String>());
        user.setTags(new ArrayList<String>());
        user.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(user);
        UserDto returnUserDto = mapper.map(user, UserDto.class);
        return returnUserDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userEntity = userRepository.findByEmail(username);
        if (userEntity == null)
            throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserVO user = userRepository.findByUserId(userId);
        if (user == null)
            throw new UsernameNotFoundException("User id not found");
        UserDto userDto = new ModelMapper().map(user, UserDto.class);

        return userDto;
    }

    @Override
    public Iterable<UserVO> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserVO entity = userRepository.findByEmail(email);
        if (entity == null) {
            throw new UsernameNotFoundException("찾을 수 없는 사용자입니다.");
        }
        UserDto userDto = new ModelMapper().map(entity, UserDto.class);
        return userDto;
    }

}
