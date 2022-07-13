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
public class UserReturnPostRequestBody {


    private Long id;

    @NotEmpty(message = "The field username cannot be empty")
    private String username;
}
