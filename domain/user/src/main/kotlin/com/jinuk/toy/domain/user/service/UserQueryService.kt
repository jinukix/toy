package com.jinuk.toy.domain.user.service

import com.jinuk.toy.domain.user.jpa.UserRepository
import com.jinuk.toy.domain.user.value.Username
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userRepository: UserRepository,
) {
    fun findByIdIn(id: List<Long>) = userRepository.findByIdIn(id)

    fun existsById(id: Long) = userRepository.existsById(id)

    fun getById(id: Long) = userRepository.findById(id) ?: throw NoSuchElementException("존재하지 않는 유저입니다.")

    fun findByUsername(username: Username) = userRepository.findByUsername(username)
}
