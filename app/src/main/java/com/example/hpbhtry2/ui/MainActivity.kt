package com.example.hpbhtry2.ui

import Enemy
import HogwartsCard
import Player
import Table
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import attack
import buyCard
import draw
import useCard


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Table.deckDarkArts.shuffle()
                Table.deckEnemy.shuffle()
                Table.hogwartsDeck.shuffle()
                while (Table.magazine.size < 6){
                    draw(Table.hogwartsDeck, Table.magazine)
                }
                while(Table.activeEnemy.size < Table.numberOfActiveEnemy){
                    draw(Table.deckEnemy, Table.activeEnemy)
                }
                for(player in Table.players){
                    player.playerDeck.shuffle()
                    repeat(5){draw(player.playerDeck, player.playerHand)}
                }
                while (true) {
                    Table.round()
                }
                GameScreen()
            }
        }
    }
    @Composable
    fun GameScreen() {
        val players = remember { Table.players }

        val activeEnemy = remember { Table.activeEnemy }

        val magazine = remember { Table.magazine }

        var blackMarks by remember { mutableStateOf(Table.locations[0].currentBlackMark) }
        //var activeShopCards by remember { mutableStateOf(shopCards) }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Счетчик меток
            Text("Черные метки: ${blackMarks}", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Панели игроков в два ряда по 2 игрока
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    players.take(2).forEachIndexed { index, player ->
                        PlayerPanel(player)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    players.drop(2).forEachIndexed { index, player ->
                        PlayerPanel(player)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Активные злодеи в один ряд с выравниванием по середине
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                activeEnemy.forEach { villain ->
                    VillainPanel(villain)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Карты магазина в два ряда по 3 карты
            Text("Карты магазина:")
            Column {
                for (i in magazine.indices step 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        magazine.slice(i until minOf(i + 3, magazine.size)).forEach { card ->
                            ShopCard(card)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Text("Карты ${Table.activePlayer.name}:")
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Table.activePlayer.playerHand.forEach { card ->
                        HandCard(card)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            // Кнопка для смены активного игрока
            Button(onClick = {
                Table.endRound = true
            }, enabled = !Table.endRound) {
                Text("Завершить ход")
            }
        }
    }

    @Composable
    fun PlayerPanel(player: Player) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicText(text = player.name)
            //Image(painter = painterResource(id = R.drawable.heart), contentDescription = "Сердце")
            BasicText(text = player.hp.toString())
            //Image(painter = painterResource(id = R.drawable.lightning), contentDescription = "Молния")
            BasicText(text = player.lithning.toString())
            //Image(painter = painterResource(id = R.drawable.coin), contentDescription = "Монета")
            BasicText(text = player.money.toString())

            // Отображение количества карт в руках и в колоде
            BasicText(text = "Карт в руке: ${player.playerHand.size}")
            BasicText(text = "Карт в колоде: ${player.playerDeck.size}")
        }
    }

    @Composable
    fun VillainPanel(villain: Enemy) {
        Column(modifier = Modifier.padding(8.dp)) {
            BasicText(text = "Злодей HP: ${villain.hpToDie}")
            BasicText(text = "Молнии: ${villain.currentLithning}")
            BasicText(text = "Описание: ${villain.description}")
        }
        Button(onClick = { attack(villain) }, enabled = !Table.endRound) {
            Text("Атаковать злодея")
        }
    }

    @Composable
    fun HandCard(card: HogwartsCard) {
        var isExpanded by remember { mutableStateOf(false) }
        var showDialog by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .width(80.dp)
                .clickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(text = card.name)
            if (isExpanded) {
                Button(onClick = {
                    if (card.choised) {
                        showDialog = true
                    } else useCard(card)
                }, enabled = !Table.endRound) {
                    Text("Сыграть карту")
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Выбор") },
                text = { Text("Выберите одно из действий.") },
                confirmButton = {
                    Button(onClick = {
                        val effects = mutableListOf(card.effects[0])
                        card.effects.clear()
                        card.effects.addAll(effects)
                        useCard(card)
                        showDialog = false
                    }) {
                        Text("Эффект1")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        val effects = mutableListOf(card.effects[1])
                        card.effects.clear()
                        card.effects.addAll(effects)
                        useCard(card)
                        showDialog = false
                    }) {
                        Text("Эффект2")
                    }
                }
            )
        }
    }

    @Composable
    fun ShopCard(card: HogwartsCard) {
        var isExpanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .width(80.dp)
                .clickable { isExpanded = !isExpanded },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicText(text = card.name)
            if (isExpanded) {
                Button(onClick = { buyCard(card) }, enabled = !Table.endRound) {
                    Text("Купить карту")
                }
            }
        }
    }

    @Preview
    @Composable
    fun SimpleComposablePreview() {
        GameScreen()
    }
}