package org.example

import org.example.database.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect("jdbc:sqlite:file:test?mode=memory&cache=shared", "org.sqlite.JDBC")

    transaction {
        addLogger(StdOutSqlLogger)

        // Initialize database schema
        SchemaUtils.create(
            Users,
            Worlds,
            WorldMemberships,
        )

        // Add sample data
        val user = User.new {
            this.name = "My User"
        }

        val newWorld = World.new {
            this.name = "My World"
        }

        WorldMembership.new {
            this.user = user
            this.world = newWorld
            this.points = 100
        }

        // Crash reproduction
        val world = World.findById(1) ?: throw RuntimeException("World not found")

        world.members.forEach {
            println("${it.name} is a member of this world")
        }

        world.memberships.forEach {
            println("${it.user.name} has ${it.points} points in this world")
        }

        // Let's try to get the members again and... Boom!
        world.members.forEach {
            println("${it.name} is a member of this world")
        }
    }
}
