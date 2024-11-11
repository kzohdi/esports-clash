package com.spoofy.esportsclash.auth.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SQLUserMapper extends SQLBaseEntityMapper<User, SQLUser> {
}
