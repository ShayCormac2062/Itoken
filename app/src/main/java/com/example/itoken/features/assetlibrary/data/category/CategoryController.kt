package com.example.itoken.features.assetlibrary.data.category

object CategoryController {

    private val art = arrayListOf(
        "mir",
        "Son",
        "Guil",
        "Sheep",
        "Line",
        "Bed",
        "MAN",
        "Self",
        "#266",
        "WIP",
        "Wei",
        "cry",
        "art",
        "Money",
        "FPH",
        "#1500",
        "pix",
        "Angel",
        "Snail",
    )

    private val collectibles = arrayListOf(
        "Bears",
        "tao",
        "Ape",
        "Meet",
        "Fox",
        "Furri",
        "Dour",
        "Zeb",
        "Drug",
    )

    private val domainNames = arrayListOf(
        "Big",
        "Calla",
        "363",
        "Uk",
        "Lot",
        "put",
        "Kid",
        "video",
        "Su D",
        "clt",
        "Weird",
    )

    private val music = arrayListOf(
        "Meta",
        "Punks",
        "Cup",
        "skull",
        "0034",
        "gta",
        "Rock",
        "Pick",
        "Meka",
        "Pass",
        "Mand",
    )

    private val sports = arrayListOf(
        "SU",
        "Detroit",
        "Meme",
        "Bik",
        "Pet",
    )

    private val tradingCards = arrayListOf(
        "DAO",
        "Pass",
        "Kokeshi",
        "#1092",
        "SPACE",
        "Bart",
        "Eve",
        "pik",
        "pix",
    )

    private val utility = arrayListOf(
        "Gen",
        "Eye",
        "Vault",
        "Alien",
        "Paper",
        "Sons",
        "SNA",
        "gm",
    )

    private val virtualWorlds = arrayListOf(
        "53",
        "Asta",
        "Dog",
        "cre",
        "THE",
        "Conta",
        "Hiki",
        "God",
        "Hell",
        "SEE",
    )

    fun getList(category: String): List<String> =
        when (category) {
            "Art" -> art
            "Music" -> music
            "Domain names" -> domainNames
            "Utility" -> utility
            "Virtual Worlds" -> virtualWorlds
            "Trading Cards" -> tradingCards
            "Collectibles" -> collectibles
            "Sports" -> sports
            else -> arrayListOf()
        }
}