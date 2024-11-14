package anotherCode

class aihelp {
    // Классы карт
    open class Card(val name: String, val description: String)

    class HealthCard(name: String, description: String, val healthChange: Int) : Card(name, description) {
        fun applyEffect(character: Character) {
            character.health += healthChange
            println("${character.name} получает эффект от карты '$name'. Здоровье: ${character.health}")
        }
    }

    class MoneyCard(name: String, description: String, val moneyChange: Int) : Card(name, description) {
        fun applyEffect(character: Character) {
            character.money += moneyChange
            println("${character.name} получает эффект от карты '$name'. Деньги: ${character.money}")
        }
    }

    class LightningCard(name: String, description: String, val lightningCount: Int) : Card(name, description) {
        fun applyEffect(enemy: Enemy) {
            enemy.lightning += lightningCount
            println("${enemy.name} получает $lightningCount молний. Молнии: ${enemy.lightning}")
        }
    }

    class DrawCard(name: String, description: String) : Card(name, description) {
        fun drawCard(player: Player) {
            if (player.deck.isNotEmpty()) {
                val newCard = player.deck.removeAt(0)
                player.hand.add(newCard)
                println("${player.name} берет карту '$newCard'.")
            }
        }
    }

    // Класс персонажа (игрока)
    open class Character(val name: String, var health: Int, var money: Int, var lightning: Int, val deck: MutableList<Card>) {
        val hand = mutableListOf<Card>()

        fun isAlive() = health > 0

        open fun takeDamage(damage: Int) {
            health -= damage
            println("$name получает $damage урона. Здоровье: $health")
        }

        fun attackLightning(enemy: Enemy) {
            if (lightning >= 5) {
                println("$name атакует молниями и наносит урон врагу!")
                enemy.takeDamage(10) // Пример урона, который наносится при полном накоплении молний
                lightning = 0
            }
        }
    }

    // Класс игрока (состояние карты, деньги и т.д.)
    class Player(name: String, health: Int, money: Int, lightning: Int, deck: MutableList<Card>) : Character(name, health, money, lightning, deck) {
        fun playTurn(game: Game) {
            println("${name} приступает к своему ходу.")

            // Применить карты
            for (card in hand) {
                when (card) {
                    is HealthCard -> card.applyEffect(this)
                    is MoneyCard -> card.applyEffect(this)
                    is LightningCard -> card.applyEffect(game.enemy)
                    else -> println("Неподдерживаемая карта.")
                }
            }

            // Попытаться атаковать молниями
            attackLightning(game.enemy)

            // Сбросить карты
            hand.clear()
            lightning = 0
            println("$name сбрасывает все карты и молнии.")

            // Пополнение карт (покупка, если есть деньги)
            if (money >= 5) {
                val newCard = DrawCard("Заклинание улучшения", "Покупка новой карты")
                deck.add(newCard)
                println("$name покупает новую карту: $newCard")
            }

            // Пополнить руку (взять 5 новых карт)
            repeat(5) {
                if (deck.isNotEmpty()) {
                    hand.add(deck.removeAt(0))
                }
            }
        }
    }

    // Класс врага (противник)
    open class Enemy(val name: String, var health: Int, var lightning: Int) {
        fun isAlive() = health > 0

        open fun takeDamage(damage: Int) {
            health -= damage
            println("$name получает $damage урона. Здоровье: $health")
        }

        fun applyDarkArtsEffect(game: Game) {
            println("Применяются Темные Искусства!")
            game.darkArtsLevel += 1
            println("Темные Искусства усиливаются: $game.darkArtsLevel.")
        }
    }

    // Класс игры
    class Game(val players: List<Player>, val enemy: Enemy) {
        var currentPlayerIndex = 0
        var darkArtsLevel = 0

        fun playRound() {
            val activePlayer = players[currentPlayerIndex]
            println("\n--- Новый раунд ---")
            println("Текущий уровень Темных Искусств: $darkArtsLevel")

            // Применяем темные искусства
            enemy.applyDarkArtsEffect(this)

            // Игрок разыгрывает свои карты
            activePlayer.playTurn(this)

            // Пополнение карт и сброс
            println("Раунд завершен, пополнение карт для игрока.")
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size
        }
    }

    // Пример игры
    fun main() {
        // Создание карт для игроков
        val harryDeck = mutableListOf<Card>(
            HealthCard("Зелье здоровья", "Восстанавливает 5 здоровья", 5),
            MoneyCard("Голди", "Дает 5 монет", 5),
            LightningCard("Молния", "Наносит 1 молнию врагу", 1),
            DrawCard("Заклинание", "Позволяет взять новую карту")
        )

        val ronDeck = mutableListOf<Card>(
            HealthCard("Зелье здоровья", "Восстанавливает 3 здоровья", 3),
            MoneyCard("Голди", "Дает 3 монеты", 3),
            LightningCard("Молния", "Наносит 2 молнии врагу", 2),
            DrawCard("Заклинание", "Позволяет взять новую карту")
        )

        // Создание игроков
        val harry = Player("Гарри Поттер", 20, 10, 0, harryDeck)
        val ron = Player("Рон Уизли", 18, 8, 0, ronDeck)

        // Создание врага
        val voldemort = Enemy(name = "Волдеморт", health = 50, lightning = 0)

        // Создание игры
        val game = Game(players = listOf(harry, ron), enemy = voldemort)

        // Игровой процесс
        while (voldemort.isAlive()) {
            game.playRound()
        }

        println("Игра завершена!")
    }
}

package com.example.harrypottergame

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "harry_potter_game.db"
        const val DATABASE_VERSION = 1
        const val TABLE_CARDS = "cards"
        const val TABLE_VILLAINS = "villains"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Создание таблицы карт
        val createCardsTable = """
            CREATE TABLE $TABLE_CARDS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT,
                type TEXT NOT NULL,
                health_change INTEGER,
                money_change INTEGER,
                lightning_count INTEGER
            );
        """.trimIndent()

        // Создание таблицы злодеев
        val createVillainsTable = """
            CREATE TABLE $TABLE_VILLAINS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT,
                health INTEGER NOT NULL,
                lightning_needed INTEGER NOT NULL
            );
        """.trimIndent()

        db?.execSQL(createCardsTable)
        db?.execSQL(createVillainsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Для простоты просто удаляем старую таблицу и создаем новую
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CARDS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_VILLAINS")
        onCreate(db)
    }

    // Вставка карты в таблицу карт
    fun insertCard(name: String, description: String, type: String, healthChange: Int, moneyChange: Int, lightningCount: Int) {
        val db = writableDatabase
        val query = """
            INSERT INTO $TABLE_CARDS (name, description, type, health_change, money_change, lightning_count)
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()
        val statement = db.compileStatement(query)
        statement.bindString(1, name)
        statement.bindString(2, description)
        statement.bindString(3, type)
        statement.bindLong(4, healthChange.toLong())
        statement.bindLong(5, moneyChange.toLong())
        statement.bindLong(6, lightningCount.toLong())
        statement.executeInsert()
    }

    // Вставка злодея в таблицу злодеев
    fun insertVillain(name: String, description: String, health: Int, lightningNeeded: Int) {
        val db = writableDatabase
        val query = """
            INSERT INTO $TABLE_VILLAINS (name, description, health, lightning_needed)
            VALUES (?, ?, ?, ?)
        """.trimIndent()
        val statement = db.compileStatement(query)
        statement.bindString(1, name)
        statement.bindString(2, description)
        statement.bindLong(3, health.toLong())
        statement.bindLong(4, lightningNeeded.toLong())
        statement.executeInsert()
    }

    // Получение всех карт из базы
    fun getCards(): List<Card> {
        val db = readableDatabase
        val cursor = db.query(TABLE_CARDS, null, null, null, null, null, null)
        val cards = mutableListOf<Card>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val type = cursor.getString(cursor.getColumnIndex("type"))
                val healthChange = cursor.getInt(cursor.getColumnIndex("health_change"))
                val moneyChange = cursor.getInt(cursor.getColumnIndex("money_change"))
                val lightningCount = cursor.getInt(cursor.getColumnIndex("lightning_count"))
                cards.add(Card(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cards
    }

    // Получение всех злодеев из базы
    fun getVillains(): List<Villain> {
        val db = readableDatabase
        val cursor = db.query(TABLE_VILLAINS, null, null, null, null, null, null)
        val villains = mutableListOf<Villain>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val health = cursor.getInt(cursor.getColumnIndex("health"))
                val lightningNeeded = cursor.getInt(cursor.getColumnIndex("lightning_needed"))
                villains.add(Villain(id, name, description, health, lightningNeeded))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return villains
    }
}

data class Card(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val healthChange: Int,
    val moneyChange: Int,
    val lightningCount: Int
)

data class Villain(
    val id: Int,
    val name: String,
    val description: String,
    val health: Int,
    val lightningNeeded: Int
)

package com.example.harrypottergame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var playerDeck: MutableList<Card>
    private lateinit var villainsList: List<Villain>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация базы данных
        dbHelper = DatabaseHelper(this)

        // Загрузка карт из базы
        playerDeck = dbHelper.getCards().toMutableList()

        // Загрузка злодеев из базы
        villainsList = dbHelper.getVillains()

        // Выводим карты на экран (пример)
        playerDeck.forEach { card ->
            println("Карта: ${card.name}, Тип: ${card.type}, Молнии: ${card.lightningCount}")
        }

        // Выводим злодеев
        villainsList.forEach { villain ->
            println("Злодей: ${villain.name}, Здоровье: ${villain.health}, Необходимые молнии: ${villain.lightningNeeded}")
        }
    }
}

dbHelper.insertCard("Экспеллиармус", "Отбрасывает врага", "заклинание", 0, 0, 5)
dbHelper.insertVillain("Волдеморт", "Темный Лорд", 50, 10)