package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    public User getUser(long id) {
        return userRepository.findById(id).get();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
