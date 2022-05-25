package com.spikart.sweater.domain

import javax.persistence.*

@Entity
data class Message(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    val text: String = "",
    val tag: String = "",

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val author: User? = null
) {
    fun getAuthorName() = author?.username ?: "<none>"
}