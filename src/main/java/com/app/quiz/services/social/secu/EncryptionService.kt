package com.app.quiz.services.social.secu

import jakarta.annotation.PostConstruct
import org.jasypt.util.text.StrongTextEncryptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class EncryptionService
{

    @Value("\${encryption.secret-key}")
    private lateinit var secretKey: String

    private val encryptor = StrongTextEncryptor()

    /**
     * Need @PostConstruct here or
     * "lateinit property secretKey has not been initialized"
     */
    @PostConstruct
    fun init() {
        encryptor.setPassword(secretKey)
    }

    fun encrypt(message: String): String
    {
        return encryptor.encrypt(message)
    }

    fun decrypt(message: String): String
    {
        return encryptor.decrypt(message)
    }

}