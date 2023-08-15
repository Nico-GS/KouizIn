package com.app.quiz.entities.user

import com.app.quiz.entities.social.Friendship
import com.app.quiz.enums.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING

@Entity
@Table(name = "users")
data class User(

        @Column(nullable = true)
        var lastName: String = "",

        @Column(nullable = true)
        var firstName: String = "",

        @Column(nullable = false, unique = true)
        var email: String = "",

        @Column(nullable = false)
        @JsonIgnore
        var password: String = "",

        @Enumerated(STRING)
        var role: Role = Role.fromString("DEFAULT"),

        @JsonIgnore
        @Column(nullable = true)
        var phoneNumber: Int = 0,

        @Column(nullable = true)
        var pathProfilePicture: String? = null,

        @Column(nullable = true)
        var resetToken: String? = null,

        @Column(nullable = true)
        var pseudo: String? = null,

        @Transient
        @JsonIgnore
        var friendships: MutableList<Friendship> = mutableListOf()
)
{

    // TODO UUID, ou pas ça bugait
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", allocationSize = 1)
    var id: Long = 0
}