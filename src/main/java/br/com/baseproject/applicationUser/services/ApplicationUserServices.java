package br.com.baseproject.applicationUser.services;

import br.com.baseproject.applicationUser.entity.ApplicationUser;
import br.com.baseproject.applicationUser.entity.dto.UserPostRequestBody;
import br.com.baseproject.applicationUser.entity.dto.UserReturnPostRequestBody;
import br.com.baseproject.applicationUser.repository.ApplicationUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationUserServices {

    private ApplicationUserRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    public ApplicationUserServices(ApplicationUserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public UserReturnPostRequestBody salvarUsuario(UserPostRequestBody user) {
        ApplicationUser applicationUser = modelMapper.map(user, ApplicationUser.class);
        encodePassword(applicationUser);
        ApplicationUser userSaved = repository.save(applicationUser);
        return modelMapper.map(userSaved, UserReturnPostRequestBody.class);
    }

    private void encodePassword(ApplicationUser user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEndocer = encoder.encode(user.getPassword());
        user.setPassword(passwordEndocer);
    }
}
