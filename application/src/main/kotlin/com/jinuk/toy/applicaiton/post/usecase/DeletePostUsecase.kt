package com.jinuk.toy.applicaiton.post.usecase

import com.jinuk.toy.applicaiton.post.command.DeletePostCommand
import com.jinuk.toy.domain.post.service.PostDeleteService
import org.springframework.stereotype.Service

@Service
class DeletePostUsecase(
    private val postDeleteService: PostDeleteService
) {
    operator fun invoke(command: DeletePostCommand) = with(command) {
        postDeleteService.delete(id, userId)
    }
}
