package com.rrat.doggydex.api.dto

import com.rrat.doggydex.model.User

class UserDTOMapper {

    fun fromUserDTOToUserDomain(userDTO: UserDTO): User {
        return User(
            userDTO.id,
            userDTO.email,
            userDTO.authenticationToken
        )
    }
}