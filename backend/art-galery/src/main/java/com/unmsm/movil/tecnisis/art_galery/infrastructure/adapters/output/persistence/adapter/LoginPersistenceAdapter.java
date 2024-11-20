package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.LoginPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginPersistenceAdapter implements LoginPersistencePort {

    private final UserRepository userRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userPersistenceMapper::toUser);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userPersistenceMapper.toUserEntity(user);
        return userPersistenceMapper.toUser(userRepository.save(userEntity));
    }
}
