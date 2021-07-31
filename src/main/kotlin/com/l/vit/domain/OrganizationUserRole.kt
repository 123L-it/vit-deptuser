package com.l.vit.domain

import com.l.vit.annotations.NoArg
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Table

@Entity
@NoArg
@Table(name = "organizations_users_roles")
data class OrganizationUserRole(
    @Id
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @MapsId("organization_id")
    @JoinColumn(name = "organization_id")
    private val organization: Organization,

    @Id
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private val user: User,

    @Id
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    private val role: Role
) : Serializable {

    override fun toString() = "{ organization: $organization, user: $user, role: $role }"

    override fun hashCode() = organization.hashCode()
        .times(31).plus(user.hashCode())
        .times(31).plus(role.hashCode())

    override fun equals(other: Any?) = this === other || (
        other is OrganizationUserRole &&
            other.organization == organization &&
            other.user == user &&
            other.role == role
        )
}
