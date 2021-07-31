package com.l.vit.domain

import com.l.vit.annotations.NoArg
import org.hibernate.annotations.GenericGenerator
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
import javax.persistence.Table

@Entity
@NoArg
@Table(name = "scopes")
data class Scope(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private val id: UUID = UUID(0L, 0L),

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private val name: String,

    @Column(name = "type", nullable = false, unique = true, length = 50)
    private val type: String,

    @JoinTable(
        name = "roles_scopes",
        joinColumns = [JoinColumn(name = "scopes_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val roles: Set<Role> = mutableSetOf(),

    @Column(nullable = false)
    private val enabled: Boolean = true
) : Serializable {

    override fun toString() = "{ id: $id, name: $name, type: $type, isEnabled: $enabled }"

    override fun hashCode() = id.hashCode()
        .times(31).plus(name.hashCode())
        .times(31).plus(type.hashCode())
        .times(31).plus(enabled.hashCode())

    override fun equals(other: Any?) = this === other || (
        other is Scope &&
            other.id == id &&
            other.name == name &&
            other.type == type &&
            other.enabled == enabled
        )
}
