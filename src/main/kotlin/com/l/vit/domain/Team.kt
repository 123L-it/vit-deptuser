package com.l.vit.domain

import com.l.vit.annotations.NoArg
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.Date
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
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Table

@Entity
@NoArg
@Table(name = "teams")
data class Team(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private val id: UUID = UUID(0L, 0L),

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private val name: String,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private val createdBy: User,

    @Column(name = "created_date")
    private val createdDate: Date,

    @Column(name = "updated_date")
    private val updatedDate: Date,

    @JoinTable(
        name = "organizations_teams",
        joinColumns = [JoinColumn(name = "teams_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "organization_id", referencedColumnName = "id")]
    )
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val organizations: Set<Organization> = mutableSetOf(),

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val users: Set<User> = mutableSetOf(),

    @Column(nullable = false)
    private val enabled: Boolean = true
) : Serializable {

    override fun toString() = "{" +
        "id: $id, name: $name, createdBy: $createdBy, createdDate: $createdDate," +
        "updatedDate: $updatedDate, isEnabled: $enabled }"

    override fun hashCode() = id.hashCode()
        .times(31).plus(name.hashCode())
        .times(31).plus(createdBy.hashCode())
        .times(31).plus(createdDate.hashCode())
        .times(31).plus(updatedDate.hashCode())
        .times(31).plus(enabled.hashCode())

    override fun equals(other: Any?) = this === other || (
        other is Team &&
            other.id == id &&
            other.name == name &&
            other.createdBy == createdBy &&
            other.createdDate == createdDate &&
            other.updatedDate == updatedDate &&
            other.enabled == enabled
        )
}
