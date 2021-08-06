package com.encore.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.encore.backend.dto.UserDto;
import com.encore.backend.repository.UserRepository;
import com.encore.backend.vo.UserVO;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserVO user = mapper.map(userDto, UserVO.class);
        user.setFollowers(new ArrayList<String>());
        user.setFollowings(new ArrayList<String>());
        user.setLikes(new ArrayList<String>());
        user.setScraps(new ArrayList<String>());
        user.setTags(new ArrayList<String>());

        userRepository.save(user);
        UserDto returnUserDto = mapper.map(user, UserDto.class);
        return returnUserDto;
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

    @Override
    public boolean updateUserByEmail(String email, UserVO user) {
        boolean result = userRepository.updateUserByEmail(email, user);
        return result;
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        boolean flag = userRepository.deleteUserByEmail(email);
        return flag;
    }

    @Override
    public List<String> getUserScraps(String email, int scrapPageNumber) {
        int start = (scrapPageNumber - 1) * 10;
        int end = scrapPageNumber * 10;
        List<String> returnScraps = userRepository.findScrapsByEmail(email, start, end).getScraps();
        return returnScraps;
    }

    @Override
    public boolean addUserScraps(String email, String boardId) {
        boolean result = userRepository.addUserScraps(email, boardId);
        return result;
    }

    @Override
    public boolean removeUserScraps(String email, String boardId) {
        boolean result = userRepository.removeUserScraps(email, boardId);
        return result;
    }

    @Override
    public List<String> getUserFollowers(String email) {
        List<String> followers = userRepository.findFollowersByEmail(email).getFollowers();
        return followers;
    }

    @Override
    public boolean addUserFollowers(String email, String followerEmail) {
        boolean result = userRepository.addUserFollowers(email, followerEmail);
        return result;
    }

    @Override
    public boolean removeUserFollowers(String email, String followerEmail) {
        boolean result = userRepository.removeUserFollowers(email, followerEmail);
        return result;
    }

    @Override
    public int getUserScrapsCount(String email) {
        int result = userRepository.findScrapsCountByEmail(email);
        return result;
    }

}
