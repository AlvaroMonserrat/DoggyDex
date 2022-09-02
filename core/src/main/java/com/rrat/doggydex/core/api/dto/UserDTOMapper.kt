package com.rrat.doggydex.core.api.dto

import com.rrat.doggydex.core.model.User

class UserDTOMapper {

    fun fromUserDTOToUserDomain(userDTO: UserDTO): User {
        return User(
            userDTO.id,
            userDTO.email,
            userDTO.authenticationToken
        )
    }
}