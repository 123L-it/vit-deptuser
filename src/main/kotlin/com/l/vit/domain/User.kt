package com.l.vit.domain

import com.fasterxml.jackson.annotation.JsonFilter
import com.l.vit.annotations.NoArg
import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@NoArg
@Table(name = "users")
@JsonFilter("excludePassword")
data class User(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private val id: UUID = UUID(0L, 0L),

    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private val userName: String,

    @Column(nullable = false)
    private val password: String,

    @Column(name = "non_expired", nullable = false)
    private val nonExpired: Boolean = true,

    @Column(name = "non_locked", nullable = false)
    private val nonLocked: Boolean = true,

    @Column(name = "credentials_non_expired", nullable = false)
    private val credentialsNonExpired: Boolean = true,

    @JoinTable(
        name = "teams_users",
        joinColumns = [JoinColumn(name = "users_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "team_id", referencedColumnName = "id")]
    )
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val teams: Set<Team> = mutableSetOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val organizationUserRole: Set<OrganizationUserRole> = mutableSetOf(),

    @Column(nullable = false)
    private val enabled: Boolean = true
) : UserDetails, Serializable {

    override fun getUsername() = userName
    override fun getPassword() = password
    override fun isAccountNonExpired() = nonExpired
    override fun isAccountNonLocked() = nonLocked
    override fun isCredentialsNonExpired() = credentialsNonExpired
    override fun getAuthorities() = null
    override fun isEnabled() = enabled

    override fun toString() = "{ id: $id, userName: $userName, isEnabled: $enabled }"

    override fun hashCode() = id.hashCode()
        .times(31).plus(userName.hashCode())
        .times(31).plus(password.hashCode())
        .times(31).plus(nonExpired.hashCode())
        .times(31).plus(nonLocked.hashCode())
        .times(31).plus(credentialsNonExpired.hashCode())
        .times(31).plus(authorities.hashCode())
        .times(31).plus(enabled.hashCode())

    override fun equals(other: Any?) = this === other || (
        other is User &&
            other.id == id &&
            other.userName == userName &&
            other.password == password &&
            other.nonExpired == nonExpired &&
            other.nonLocked == nonLocked &&
            other.credentialsNonExpired == credentialsNonExpired &&
            other.authorities == authorities &&
            other.enabled == enabled
        )
}
