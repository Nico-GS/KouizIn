package com.app.quiz.exceptions.category_question

class CategoryQuestionAlreadyExistException(message: String) : RuntimeException(message)

class CategoryNotFoundException(message: String): RuntimeException(message)