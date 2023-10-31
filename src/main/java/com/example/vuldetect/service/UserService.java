package com.example.vuldetect.service;

import com.example.vuldetect.domain.User;
import com.example.vuldetect.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @PostConstruct
    public void initUser() {
        User userA = new User("A", "1234");
        User userB = new User("B", "1234");
        join(userA);
        join(userB);
    }

    public Long join(User user) {
        validateDuplicateMember(user); // 중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateMember(User user) {
        User findMember = userRepository.findByName(user.getUsername());
        if (findMember != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public int login(String username, String password) {
        User findUser = userRepository.findByName(username);
        if(findUser.getUsername().equals(username) && findUser.getPassword().equals(password)) {
            return 1;
        }
        return 0;
    }

    //회원 전체 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    public User findOne(String userName) { return userRepository.findByName(userName); }
}
