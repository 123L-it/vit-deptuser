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
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@NoArg
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private val id: UUID = UUID(0L, 0L),

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private val name: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val scopes: Set<Scope> = mutableSetOf(),

    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val organizationUserRole: Set<OrganizationUserRole> = mutableSetOf(),

    @Column(nullable = false)
    private val enabled: Boolean = true
) : Serializable {

    override fun toString() = "{ id: $id, name: $name, isEnabled: $enabled }"

    override fun hashCode() = id.hashCode()
        .times(31).plus(name.hashCode())
        .times(31).plus(enabled.hashCode())

    override fun equals(other: Any?) = this === other || (
        other is Role &&
            other.id == id &&
            other.name == name &&
            other.enabled == enabled
        )
}
