package com.spikart.sweater.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "usr")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    private var username: String,
    private var password: String,
    var isActive: Boolean = true,
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role> = mutableSetOf(Role.USER)
) : UserDetails {

    var email: String = ""
    var activationCode: String = ""


    fun isAdmin() = roles.contains(Role.ADMIN)

    override fun getUsername(): String = username
    fun setUsername(name: String) {
        username = name
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
    }

    override fun getPassword(): String = password
    override fun isEnabled(): Boolean = isActive
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
}

enum class Role: GrantedAuthority {
    USER { override fun getAuthority() = name },
    ADMIN { override fun getAuthority() = name };
}