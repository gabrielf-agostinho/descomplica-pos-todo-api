package com.descomplicapostodoapi.Services;

import com.descomplicapostodoapi.Adapters.UserAdapter;
import com.descomplicapostodoapi.Models.DTOs.User.UserGetDTO;
import com.descomplicapostodoapi.Models.DTOs.User.UserPostPutDTO;
import com.descomplicapostodoapi.Models.Entities.User;
import com.descomplicapostodoapi.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final UserAdapter userAdapter = new UserAdapter();

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UserGetDTO> getAll() {
        return userAdapter.parse(usersRepository.findAll());
    }

    public UserGetDTO getById(long id) {
        return usersRepository.findById(id)
                .map(userAdapter::parse)
                .orElse(null);
    }

    public void post(UserPostPutDTO userPostPutDTO) {
        usersRepository.save(userAdapter.parse(userPostPutDTO));
    }

    public void put(UserPostPutDTO userPostPutDTO) {
        usersRepository.findById(userPostPutDTO.getId())
                .ifPresent(user -> usersRepository.save(userAdapter.parse(userPostPutDTO, user)));
    }

    public void updateRefreshAndExpiration(User user, String refresh, Date expiration) {
        user.setRefresh(refresh);
        user.setExpiresAt(expiration);

        usersRepository.save(user);
    }

    public void delete(long id) {
        usersRepository.findById(id)
                .ifPresent(usersRepository::delete);
    }
}
