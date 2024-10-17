package com.jinuk.toy.externalapi.view.post

import com.jinuk.toy.applicaiton.comment.command.CommentCommandBus
import com.jinuk.toy.externalapi.global.ExternalAPIController
import com.jinuk.toy.externalapi.global.security.AuthRole
import com.jinuk.toy.externalapi.global.security.AuthUser
import com.jinuk.toy.externalapi.view.post.request.CommentCreateRequest
import com.jinuk.toy.externalapi.view.post.request.toCommand
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "게시글 댓글")
@ExternalAPIController
class PostCommentAPI(
    private val commentCommandBus: CommentCommandBus,
) {

    @Operation(
        summary = "게시글 댓글 작성",
        description = "게시글에 댓글을 작성합니다.",
    )
    @Secured(AuthRole.USER)
    @PostMapping("/v1/post/{postId}/comment")
    fun createComment(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable postId: String,
        @RequestBody request: CommentCreateRequest,
    ) = request.toCommand(user.id).let {
        commentCommandBus.execute(it)
    }
}