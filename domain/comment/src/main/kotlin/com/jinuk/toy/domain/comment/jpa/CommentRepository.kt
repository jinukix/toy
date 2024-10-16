package com.jinuk.toy.domain.comment.jpa

import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.toEntity
import com.jinuk.toy.domain.comment.toModel
import com.jinuk.toy.infra.rdb.comment.jpa.CommentEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommentRepository(
    private val commentEntityRepository: CommentEntityRepository,
) {
    fun save(comment: Comment) = commentEntityRepository.save(comment.toEntity()).toModel()

    fun delete(comment: Comment) = commentEntityRepository.delete(comment.toEntity())

    fun findById(id: Long) = commentEntityRepository.findByIdOrNull(id)?.toModel()

    fun findByIdIn(ids: List<Long>): List<Comment> = commentEntityRepository.findAllById(ids).map { it.toModel() }
}

