package com.app.quiz.repositories.questions

import com.app.quiz.entities.quiz.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long>, QuestionCustomRepository