package com.epicjii.Database

import com.epicjii.serializable.PasswordEntry
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.div

val workingPath: Path = Paths.get("").toAbsolutePath()
val path = Files.createDirectories(workingPath.div("data")).toString()
val userName = " "
val password = " "
val database = Database.connect("jdbc:sqlite:$path/passwords.db", "org.sqlite.JDBC", userName, password)

fun createDatabase() {
    transaction(database) {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(PasswordDatabase)
    }
}

object PasswordDatabase : Table() {
    val domain = text("domain")
    val key = text("key")
    val userName = text("user_name")
    val password = text("password")
    override val primaryKey = PrimaryKey(domain, key)
}

fun createEntry(newEntry: PasswordEntry): Boolean {
    return transaction(database) {
        PasswordDatabase.insert {
            it[domain] = newEntry.domain
            it[key] = newEntry.key
            it[userName] = newEntry.userName
            it[password] = newEntry.password
        }
    }.insertedCount == 1
}

fun getUserNamePassword(domain: String): List<PasswordEntry> {
    return transaction(database) {
        PasswordDatabase.select {
            PasswordDatabase.domain eq domain
        }.map {
            PasswordEntry(
                domain = domain,
                key = it[PasswordDatabase.key],
                userName = it[PasswordDatabase.userName],
                password = it[PasswordDatabase.password]
            )
        }
    }
}

fun updateEntry(entry: PasswordEntry): Boolean {
    return transaction {
        PasswordDatabase.update({
            PasswordDatabase.domain eq entry.domain
            PasswordDatabase.key eq entry.key
        }) {
            it[userName] = entry.userName
            it[password] = entry.password
        }
    } == 1
}

fun deleteEntry(domain: String, key: String, userName: String, password: String) {
    transaction {
        PasswordDatabase.deleteWhere {
            PasswordDatabase.domain eq domain
            PasswordDatabase.key eq key
            PasswordDatabase.userName eq userName
            PasswordDatabase.password eq password
        }
    }
}

