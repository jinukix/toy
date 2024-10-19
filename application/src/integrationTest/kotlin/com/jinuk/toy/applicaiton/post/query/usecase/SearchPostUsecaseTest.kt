package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.comment.CommentFixture
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.UserFixture
import com.jinuk.toy.domain.post.value.PostTitle
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SearchPostUsecaseTest(
    private val searchPostUsecase: SearchPostUsecase,
    private val postFixture: PostFixture,
    private val commentFixture: CommentFixture,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
        {
            describe("게시글 검색 유스케이스") {
                it("조회 성공") {
                    val user = userFixture.persist()
                    val posts =
                        (1..20).map {
                            postFixture.persist(title = PostTitle("title$it"), userId = user.id)
                        }
                    val postsSize = posts.size

                    val commentCount = 5
                    repeat(commentCount) {
                        commentFixture.persist(postId = posts[postsSize - 3].id)
                    }

                    val query = SearchPostQuery(keyword = "title", page = 1, size = 3)
                    val result = searchPostUsecase(query)

                    result.totalElements shouldBe postsSize

                    val content = result.content
                    content.size shouldBe 3

                    content[0].id shouldBe posts[postsSize - 1].id
                    content[1].id shouldBe posts[postsSize - 2].id
                    content[2].id shouldBe posts[postsSize - 3].id
                    content[2].userName shouldBe user.username
                    content[2].commentCount shouldBe commentCount
                }
            }
        },
    )