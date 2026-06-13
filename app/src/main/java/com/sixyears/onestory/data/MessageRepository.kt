package com.sixyears.onestory.data

import com.sixyears.onestory.util.DateUtils

/**
 * Static message pools for notifications. All offline, no network needed.
 */
object MessageRepository {

    val goodMorningMessages: List<String> = listOf(
        "🌞 Good Morning Love ❤️\nAnother beautiful day closer to your special day.",
        "❤️ Good Morning Sunshine.\nHope today brings you happiness and smiles.",
        "🌹 Wake up beautiful.\nSomeone loves you more every day.",
        "☀️ Rise and shine, my love.\nThe world is brighter with you in it.",
        "💖 Good morning to the best part of my life.\nHave a wonderful day.",
        "🌸 Morning, my love.\nMay your day be as sweet as you are.",
        "🦋 Good morning beautiful soul.\nYou make every day feel special.",
        "✨ Wishing my favorite person a magical morning.\nI love you.",
        "🌻 Good morning sunshine.\nKeep shining, the world needs your light.",
        "💌 Just a little reminder before your day starts:\nyou are deeply loved.",
        "🌷 Good morning love.\nHope your coffee is strong and your day is gentle.",
        "🌅 Another sunrise, another chance to fall more in love with you.\nGood morning.",
        "💕 Good morning to my favorite person in the whole world.",
        "🐦 Good morning, sleepyhead.\nThe birds are singing your name, I'm sure of it.",
        "🌼 Rise and shine, my queen.\nToday is going to be a great day.",
        "💗 Good morning love.\nI hope today treats you as kindly as you treat others.",
        "🌈 Good morning beautiful.\nMay your day be filled with little joys.",
        "🍯 Sweet good morning to my sweetest person.\nHave a lovely day.",
        "🌞 Morning love.\nThinking of you already, as always.",
        "💞 Good morning. \nYour smile is the best part of my day, even from afar.",
        "🌺 Good morning my love.\nDon't forget how amazing you are.",
        "🥰 Good morning to the one who makes my heart smile.",
        "🍃 Wishing you a calm, happy morning, my love.",
        "💫 Good morning star.\nYou light up every room you walk into.",
        "🌙 Hope you slept well, love.\nGood morning, the day is yours.",
        "🧡 Good morning. \nJust wanted you to start your day knowing you're loved.",
        "🌟 Good morning to the most wonderful person I know.",
        "🍓 Good morning sweetheart.\nMay your day be as sweet as strawberries.",
        "🐝 Good morning busy bee.\nDon't work too hard, take care of yourself today.",
        "🌊 Good morning love.\nLet today's waves bring you peace and happiness.",
        "🎈 Good morning! \nGetting closer to your special day, I can feel the excitement.",
        "🍂 Good morning love.\nNo matter the season, my love for you stays the same.",
        "🌱 Good morning.\nLike this little seed, our love just keeps growing.",
        "🕊️ Good morning, peace and love to you today.",
        "🎀 Good morning gorgeous.\nHope your day is wrapped in good things.",
        "🍀 Good morning love.\nWishing you luck and joy today.",
        "🌤️ Good morning.\nWhatever the weather, you're my sunshine.",
        "💐 Good morning beautiful.\nSending you a virtual bouquet to start the day.",
        "🎶 Good morning love.\nMay your day have a happy soundtrack.",
        "🧁 Good morning sweet one.\nHope your day is as nice as a cupcake.",
        "🌍 Good morning love.\nYou make my world so much better.",
        "🦄 Good morning to someone truly magical.\nHave a great day.",
        "🍩 Good morning love.\nHope your day is full of little sweet moments.",
        "🌷 Good morning. \nI'm grateful for you every single day.",
        "💝 Good morning my love.\nYou are the best gift I never expected.",
        "🌻 Good morning. \nKeep that beautiful smile on today, it suits you.",
        "🌌 Good morning love.\nEven the stars don't compare to you.",
        "🍵 Good morning. \nHope your morning is calm and your day is kind.",
        "🎵 Good morning love.\nYou're the favorite song stuck in my head.",
        "🌞 Good morning. \nEvery day with you getting closer is a good day.",
        "💖 Good morning love.\nJust six more reasons to smile today: you exist."
    )

    /**
     * Returns one of the goodMorningMessages without immediate repetition,
     * using [lastIndex] (the previously-used index, or -1 if none) to avoid repeats.
     */
    fun pickGoodMorningMessage(lastIndex: Int): Pair<String, Int> {
        if (goodMorningMessages.isEmpty()) return "" to -1
        var index: Int
        do {
            index = goodMorningMessages.indices.random()
        } while (goodMorningMessages.size > 1 && index == lastIndex)
        return goodMorningMessages[index] to index
    }

    /**
     * Evening countdown message based on days remaining until the birthday.
     */
    fun countdownMessage(daysLeft: Long): String {
        return when {
            daysLeft <= 0 -> "🎂 Your birthday is almost here."
            daysLeft == 1L -> "❤️ Tomorrow is your special day."
            daysLeft <= 3 -> "❤️ Only $daysLeft days left."
            daysLeft <= 7 -> "🎂 Your birthday is almost here.\n❤️ $daysLeft days left until your special day."
            else -> "❤️ $daysLeft days left until your birthday."
        }
    }

    /** Birthday-day notification messages, keyed by time slot. */
    object BirthdayDay {
        const val MIDNIGHT = "🎂 Happy Birthday My Love ❤️"
        const val MORNING = "🌞 Good Morning Birthday Girl ❤️"
        const val NOON = "🎉 Today is all about you."
        const val EVENING = "❤️ Open the app.\nA surprise is waiting for you."
    }

    fun loveGrowingPercent(): Int = DateUtils.loveGrowingPercent()
}
