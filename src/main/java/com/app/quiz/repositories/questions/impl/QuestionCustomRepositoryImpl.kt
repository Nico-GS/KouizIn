package com.app.quiz.repositories.questions.impl

import com.app.quiz.entities.quiz.AnswersQuestion
import com.app.quiz.entities.quiz.Question
import com.app.quiz.entities.quiz.Quiz
import com.app.quiz.repositories.questions.QuestionCustomRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Root
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
open class QuestionCustomRepositoryImpl(
    @PersistenceContext private val entityManager: EntityManager
) : QuestionCustomRepository
{

    /**
     * Use for get list of [Question] from [Quiz.id]
     */
    override fun getQuestionByQuizzes(quiz: Quiz): List<Question>
    {
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Question::class.java)
        val root: Root<Question> = criteriaQuery.from(Question::class.java)

        // JOIN
        val quizJoin = root.join<Question, Quiz>("quizzes")

        // EQUAL
        val condition = criteriaBuilder.equal(quizJoin.get<Long>("id"), quiz.id)

        // WHERE
        criteriaQuery.where(condition)

        val query = entityManager.createQuery(criteriaQuery)
        return query.resultList
    }

    // TODO virer la criteria pour mieux apres
    override fun findAllWithAnswersByQuestionId(questionId: Long): List<Pair<Question, AnswersQuestion>>
    {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createTupleQuery()
        val root: Root<Question> = criteriaQuery.from(Question::class.java)
        val answersJoin = root.join<Question, AnswersQuestion>("answers", JoinType.LEFT)

        criteriaQuery.multiselect(root, answersJoin)

        criteriaQuery.where(criteriaBuilder.equal(root.get<Long>("id"), questionId))

        val query = entityManager.createQuery(criteriaQuery)
        val resultList = query.resultList

        return resultList.map { result ->
            val question = result.get(0, Question::class.java)
            val answers = result.get(1, AnswersQuestion::class.java)
            Pair(question, answers)
        }
    }
}