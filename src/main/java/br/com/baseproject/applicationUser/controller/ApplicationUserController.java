package br.com.baseproject.applicationUser.controller;


import br.com.baseproject.applicationUser.entity.dto.UserPostRequestBody;
import br.com.baseproject.applicationUser.entity.dto.UserReturnPostRequestBody;
import br.com.baseproject.applicationUser.services.ApplicationUserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class ApplicationUserController {

    private ApplicationUserServices service;

    @Autowired
    public ApplicationUserController(ApplicationUserServices service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a user")
    @ApiResponse(responseCode = "201", description = "When the user is created")
    public ResponseEntity<?> criar(@Valid @RequestBody UserPostRequestBody applicationUser) {
        UserReturnPostRequestBody userSave = service.salvarUsuario(applicationUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }
}
