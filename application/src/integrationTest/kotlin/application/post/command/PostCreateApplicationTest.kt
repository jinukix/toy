package application.post.command

import application.IntegrationTest
import com.jinuk.toy.applicaiton.post.command.PostCreateApplication
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import com.jinuk.toy.domain.post.value.PostTitle
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class PostCreateApplicationTest(
    private val postCreateApplication: PostCreateApplication,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("post create") {
            context("post exists title") {
                val existsTitle = PostTitle("exists")
                val exits = PostFixture.create(title = existsTitle)
                postRepository.save(exits)

                it("create success") {
                    val title = PostTitle("title")
                    val command = PostCreateCommand(1, title, "content")

                    val post = postCreateApplication(command)
                    val postEntity = postRepository.findById(post.id!!)

                    post shouldBe postEntity
                    post.userId shouldBe 1
                    post.title shouldBe title
                    post.content shouldBe "content"
                }

                it("create fail - exists title") {
                    val command = PostCreateCommand(1, existsTitle, "content")
                    shouldThrow<IllegalArgumentException> {
                        postCreateApplication(command)
                    }
                }
            }
        }
    },
)
