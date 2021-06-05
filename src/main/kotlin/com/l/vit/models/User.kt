package com.l.vit.models

data class User(
    val id: String,
    val name: String,
    val active: Boolean
) {
    override fun toString() = "{ id: $id, name: $name, active: $active }"

    override fun hashCode() = id.hashCode()
        .times(31).plus(name.hashCode())
        .times(31).plus(active.hashCode())

    override fun equals(other: Any?) = this === other ||
        (other is User && other.id == id && other.name == name && other.active == active)
}
