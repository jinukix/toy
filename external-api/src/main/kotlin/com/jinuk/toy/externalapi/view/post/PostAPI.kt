package com.jinuk.toy.externalapi.view.post

import com.jinuk.toy.applicaiton.post.PostCommandBus
import com.jinuk.toy.applicaiton.post.PostQueryBus
import com.jinuk.toy.applicaiton.post.command.DeletePostCommand
import com.jinuk.toy.applicaiton.post.query.GetPostDetailQuery
import com.jinuk.toy.externalapi.global.security.AuthRole
import com.jinuk.toy.externalapi.global.security.AuthUser
import com.jinuk.toy.externalapi.view.post.request.PostCreateRequest
import com.jinuk.toy.externalapi.view.post.request.PostUpdateRequest
import com.jinuk.toy.externalapi.view.post.request.toCommand
import com.jinuk.toy.externalapi.view.post.response.toResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "게시글")
@RequestMapping("/post")
@RestController
class PostAPI(
    private val postCommandBus: PostCommandBus,
    private val postQueryBus: PostQueryBus,
) {

    @Operation(
        summary = "게시글 등록",
        description = "게시글을 신규 등록합니다.",
    )
    @Secured(AuthRole.USER)
    @PostMapping
    fun create(
        @AuthenticationPrincipal user: AuthUser,
        @RequestBody request: PostCreateRequest
    ) = request.toCommand(user.id).let {
        postCommandBus.execute(it)
    }.toResponse()

    @Operation(
        summary = "게시글 상세 조회",
        description = "id로 게시글을 조회합니다.",
    )
    @GetMapping("/{postId}")
    fun getPostDetail(@PathVariable postId: Long) = GetPostDetailQuery(postId).let {
        postQueryBus.query(it)
    }.toResponse()

    @Operation(
        summary = "게시글 수정",
    )
    @Secured(AuthRole.USER)
    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable postId: Long,
        @RequestBody request: PostUpdateRequest
    ) = request.toCommand(user.id, postId).let {
        postCommandBus.execute(it)
    }

    @Operation(
        summary = "게시글 삭제",
        description = "id로 게시글을 삭제합니다.",
    )
    @Secured(AuthRole.USER)
    @DeleteMapping("/{postId}")
    fun delete(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable postId: Long
    ) = DeletePostCommand(user.id, postId).let {
        postCommandBus.execute(it)
    }
}
