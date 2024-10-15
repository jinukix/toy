package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GetPostDetailUsecaseTest(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 단일 조회 유스케이스") {
            context("exists 이름을 가진 게시글 존재") {
                val exist = postRepository.save(PostFixture.create())

                it("조회 성공") {
                    val post = getPostDetailUsecase(GetPostDetailQuery(exist.id))
                    post shouldBe exist
                }

                it("조회 실패 - 다른 id로 조회") {
                    shouldThrow<NoSuchElementException> {
                        getPostDetailUsecase(GetPostDetailQuery(exist.id + 1))
                    }
                }
            }
        }
    },
)

