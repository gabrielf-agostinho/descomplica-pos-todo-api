package com.descomplicapostodoapi.Services;

import com.descomplicapostodoapi.Adapters.UserAdapter;
import com.descomplicapostodoapi.Models.DTOs.User.UserGetDTO;
import com.descomplicapostodoapi.Models.DTOs.User.UserPostPutDTO;
import com.descomplicapostodoapi.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UserGetDTO> getAll() {
        return new UserAdapter().parse(usersRepository.findAll());
    }

    public UserGetDTO getById(long id) {
        return usersRepository.findById(id)
                .map(user -> new UserAdapter().parse(user))
                .orElse(null);
    }

    public void post(UserPostPutDTO userPostPutDTO) {
        usersRepository.save(new UserAdapter().parse(userPostPutDTO));
    }

    public void put(UserPostPutDTO userPostPutDTO) {
        usersRepository.findById(userPostPutDTO.getId())
                .ifPresent(user -> usersRepository.save(new UserAdapter().parse(userPostPutDTO, user)));
    }

    public void delete(long id) {
        usersRepository.findById(id)
                .ifPresent(usersRepository::delete);
    }
}
