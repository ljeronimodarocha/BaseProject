package br.com.baseproject.applicationUser.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPostRequestBody {

    @NotEmpty(message = "The field username cannot be empty")
    private String username;

    @NotEmpty(message = "The field password cannot be empty")
    private String password;
}
