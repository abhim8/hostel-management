package com.project.hostelmanagement.service;

import com.project.hostelmanagement.exception.BadRequestException;
import com.project.hostelmanagement.model.Credentials;
import com.project.hostelmanagement.repository.CredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public boolean authenticate(String mail, String password) {
        if(StringUtils.isNotEmpty(mail) && StringUtils.isEmpty(password)){
            log.info("validating email");
            Optional<Credentials> credentials = credentialsRepository.findByMail(mail);
            return credentials.isPresent() && credentials.get().getMail().equals(mail);
        }
        else if(StringUtils.isNotEmpty(mail) && StringUtils.isNotEmpty(password)){
            log.info("validating password");
            Optional<Credentials> credentials = credentialsRepository.findByMail(mail);
            return credentials.isPresent() && credentials.get().getMail().equals(mail) && credentials.get().getPassword().equals(password);
        }
        return false;
    }

    @Override
    public boolean saveCredentials(String mail, String password) {
        if(authenticate(mail, password)){
            log.info("email is already used!");
            throw new BadRequestException("email already used!, Please use another email");
        }
        credentialsRepository.save(Credentials.builder().mail(mail).password(password).build());
        return true;
    }
}
