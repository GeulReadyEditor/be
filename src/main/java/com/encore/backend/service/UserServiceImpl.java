package com.encore.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.encore.backend.dto.UserDto;
import com.encore.backend.repository.user.UserRepository;
import com.encore.backend.s3.S3Uploader;
import com.encore.backend.vo.UserVO;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, S3Uploader s3Uploader) {
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws IOException {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserVO user = mapper.map(userDto, UserVO.class);
        user.setIntro("");
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
    public boolean updateUserByEmail(String email, UserDto userDto, MultipartFile profileImage) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserVO userVO = mapper.map(userDto, UserVO.class);
        boolean result = true;
        try {
            if (profileImage != null)
                userVO.setProfileImage(s3Uploader.upload(profileImage, "profileImages", email));
            result = userRepository.updateUserByEmail(email, userVO);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        boolean result = true;
        try {
            s3Uploader.remove(email);
            result = userRepository.deleteUserByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
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
    public int getUserScrapsCount(String email) {
        int result = userRepository.findScrapsCountByEmail(email);
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
    public List<String> getUserFollowings(String email) {
        List<String> following = userRepository.findFollowingsByEmail(email).getFollowings();
        return following;
    }

    @Override
    public boolean addUserFollowings(String email, String followEmail) {
        boolean result = userRepository.addUserFollowings(email, followEmail);
        return result;
    }

    @Override
    public boolean removeUserFollowings(String email, String followEmail) {
        boolean result = userRepository.removeUserFollowings(email, followEmail);
        return result;
    }

}
