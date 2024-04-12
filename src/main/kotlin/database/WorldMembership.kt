package org.example.database

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object WorldMemberships : LongIdTable("world_memberships") {
    val worldId = reference("world_id", Worlds, ReferenceOption.CASCADE)
    val userId = reference("user_id", Users, ReferenceOption.CASCADE)

    val points = integer("points")

    init {
        uniqueIndex(worldId, userId)
    }
}

class WorldMembership(id: EntityID<Long>) : LongEntity(id) {
    var points by WorldMemberships.points

    var world by World referencedOn WorldMemberships.worldId
    var user by User referencedOn WorldMemberships.userId

    companion object : LongEntityClass<WorldMembership>(WorldMemberships)
}
