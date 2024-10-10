package com.jinuk.toy.externalapi.view.post

import com.jinuk.toy.applicaiton.post.command.adaptor.PostCommandBus
import com.jinuk.toy.externalapi.global.exception.ErrorResponse
import com.jinuk.toy.externalapi.view.post.request.PostCreateRequest
import com.jinuk.toy.externalapi.view.post.request.toCommand
import com.jinuk.toy.externalapi.view.post.response.PostCreateResponse
import com.jinuk.toy.externalapi.view.post.response.toResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "게시글")
@RequestMapping("/post")
@RestController
class ExternalPostAPI(
    private val postCommandBus: PostCommandBus
) {

    @Operation(
        summary = "게시글 등록",
        description = "제목(title)으로 게시글을 신규 등록합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "게시글 등록 성공",
                content = [
                    Content(schema = Schema(implementation = PostCreateResponse::class))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "게시글 등록 실패",
                content = [
                    Content(schema = Schema(implementation = ErrorResponse::class))
                ]
            )
        ]
    )
    @PostMapping
    fun create(
        @RequestBody request: PostCreateRequest
    ) = postCommandBus.execute(request.toCommand()).toResponse()
}
