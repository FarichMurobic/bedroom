package com.bedroom.infrastructure.persistence.mapper;

import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;
import com.bedroom.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class UserMapper {

    public UserEntity toEntity(User user) {

        if (user == null) {
            throw new IllegalArgumentException(
                    "User cannot be null"
            );
        }

        return new UserEntity(
                user.id().value(),
                user.username().value(),
                user.status(),
                user.roles(),
                user.createdAt(),
                user.updatedAt()
        );
    }

    public User toDomain(UserEntity entity) {

        if (entity == null) {
            throw new IllegalArgumentException(
                    "User entity cannot be null"
            );
        }

        return User.reconstitute(
                UserId.of(entity.getId()),
                new Username(entity.getUsername()),
                entity.getStatus(),
                entity.getRoles(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}