package com.bedroom.domain.identity.repository;

import com.bedroom.domain.identity.enums.AuthenticationProvider;
import com.bedroom.domain.identity.model.AuthenticationIdentity;
import com.bedroom.domain.identity.valueobject.AuthenticationIdentityId;
import com.bedroom.domain.identity.valueobject.Email;
import com.bedroom.domain.identity.valueobject.ExternalIdentifier;
import com.bedroom.domain.identity.valueobject.PhoneNumber;

import java.util.Optional;

public interface AuthenticationIdentityRepository {

    AuthenticationIdentity save(AuthenticationIdentity identity);

    Optional<AuthenticationIdentity> findById(AuthenticationIdentityId id);

    Optional<AuthenticationIdentity> findByEmail(Email email);

    Optional<AuthenticationIdentity> findByPhoneNumber(PhoneNumber phoneNumber);

    Optional<AuthenticationIdentity> findByProviderAndExternelIdentifier(
            AuthenticationProvider provider,
            ExternalIdentifier externalIdentifier
    );

    boolean existsByEmail(Email email);

    boolean existsByPhoneNumber(PhoneNumber phoneNumber);

    boolean existsByProviderAndExternalIdentifier(
            AuthenticationProvider provider,
            ExternalIdentifier externalIdentifier
    );

    void deleteById(AuthenticationIdentityId id);

}
