package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.post.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostQueryService(
    private val postRepository: PostRepository,
) {
    fun getById(id: Long) = postRepository.findById(id) ?: throw NoSuchElementException("존재하지 않는 게시글입니다.")
}
