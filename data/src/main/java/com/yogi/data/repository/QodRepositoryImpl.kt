package com.yogi.data.repository

import javax.inject.Inject

class QodRepositoryImpl @Inject constructor(): QODRepository {
    private val quoteMap = mapOf<Int, String>(
        1 to "When you have a dream, you've got to grab it and never let go. — Carol Burnett",
        2 to "Nothing is impossible. The word itself says 'I'm possible!' — Audrey Hepburn",
        3 to "Life has got all those twists and turns. You've got to hold on tight and off you go. — Nicole Kidman",
        4 to "Be courageous. Challenge orthodoxy. Stand up for what you believe in. When you are in your rocking chair talking to your grandchildren many years from now, " +
                "be sure you have a good story to tell. — Amal Clooney",
        5 to "You don't always need a plan. Sometimes you just need to breathe, trust, let go and see what happens. — Mandy Hale",
        6 to "I'm not going to continue knocking that old door that doesn't open for me. I'm going to create my own door and walk through that. — Ava DuVernay",
        7 to "The soul is neither born, nor does it ever die; nor is it that having come to exist, It will ever cease to be. The soul is birth less, eternal, immortal and ageless; It is not destroyed when the body is destroyed. BG: 2-20",
        8 to "You have a right to perform your prescribed duties, but you are not entitled to the fruits of your actions. Never consider yourself to be the cause of the results of your activities, nor be attached to inaction. BG: 2-47",
        9 to " Just as the ocean remains undisturbed by the incessant flow of waters from rivers merging into it, likewise the sage who is unmoved despite the flow of desirable objects all around him attains peace, and not the person who strives to satisfy desires. BG:2-70 ",
        10 to "The senses are superior to the gross body, and superior to the senses is the mind. Beyond the mind is the intellect, and even beyond the intellect is the soul. BG: 3-42"
    )

    override fun getQOD(): String? = quoteMap[(1 until quoteMap.size).random()]


}