package com.api.destravaCuca.Service;

import com.api.destravaCuca.infra.UserAlreadyExistsException;
import com.api.destravaCuca.infra.UserNotFoundException;
import com.api.destravaCuca.model.user.UserModelDto;
import com.api.destravaCuca.model.user.UserModel;
import com.api.destravaCuca.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public UserModel createUser(UserModelDto userModelDto){


            var userExists = userRepository.findByEmail(userModelDto.getEmail());
           if(userExists.isPresent()){
               throw new UserAlreadyExistsException("Usuário já cadastrado!");
           }
           UserModel user = new UserModel();

            BeanUtils.copyProperties(userModelDto, user);
            user.setActive(true);
            userRepository.save(user);
            return user;


    }

    public List<UserModel> getAllUsers(){
        return this.userRepository.findAll();
    }

    public void inactivateUser(UUID id) {

            UserModel user = userRepository.findById(id)
                    .orElseThrow(() ->
                            new UserNotFoundException("Usuário não encontrado!"));
            user.setActive(false);
            userRepository.save(user);
    }

    public void activateUser(UUID id) {

        UserModel user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Usuário não encontrado!"));
        user.setActive(true);
        userRepository.save(user);
    }

    public UserModel editUser(UserModelDto userModelDto, UUID id){

        var userExists = userRepository.findById(id);

        if(userExists.isEmpty()) throw new UserNotFoundException("Usuário não encontrado");

        if(userRepository.findByEmail(userModelDto.getEmail()).isPresent()) throw new UserAlreadyExistsException("Usuário com este e-mail já existe!");

        UserModel user = new UserModel();

        BeanUtils.copyProperties(userModelDto, user);
        user.setId(id);
        userRepository.save(user);
        return user;
    }

}
