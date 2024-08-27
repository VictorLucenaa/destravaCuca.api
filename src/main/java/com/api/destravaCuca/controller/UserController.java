package com.api.destravaCuca.controller;

import com.api.destravaCuca.Service.UserService;
import com.api.destravaCuca.model.user.UserModelDto;
import com.api.destravaCuca.model.user.UserModel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin( origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserModelDto userModelDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userModelDto));
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PatchMapping("disable-user/{id}")
    @Transactional
    public ResponseEntity<String> inactivateUser (@PathVariable UUID id){
        userService.inactivateUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário desativado com sucesso!");
    }

    @PatchMapping("enable-user/{id}")
    @Transactional
    public ResponseEntity<String> activateUser (@PathVariable UUID id){
        userService.activateUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário ativado com sucesso!");
    }

    @PutMapping("edit/{id}")
    @Transactional
    public ResponseEntity<UserModel> editUser(@RequestBody @Valid UserModelDto userModelDto, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.editUser(userModelDto, id));
    }
}
