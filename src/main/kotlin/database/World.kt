package org.example.database

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Worlds : LongIdTable("worlds") {
    val name = text("name")
}

class World(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<World>(Worlds)

    var name by Worlds.name

    val members by User via WorldMemberships
    val memberships by WorldMembership.referrersOn(WorldMemberships.worldId)
}
