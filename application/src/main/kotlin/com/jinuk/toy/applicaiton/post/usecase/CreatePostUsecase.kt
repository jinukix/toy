package com.jinuk.toy.applicaiton.post.usecase

import com.jinuk.toy.applicaiton.post.command.CreatePostCommand
import com.jinuk.toy.applicaiton.post.command.toPost
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostPersistenceService
import com.jinuk.toy.domain.post.service.PostQueryService
import org.springframework.stereotype.Service

@Service
class CreatePostUsecase(
    private val postQueryService: PostQueryService,
    private val postPersistenceService: PostPersistenceService,
) {

    operator fun invoke(command: CreatePostCommand): Post {
        require(!postQueryService.existsByTitle(command.title.value)) { "이미 존재하는 게시글 제목입니다." }
        return postPersistenceService.persist(command.toPost())
    }
}
