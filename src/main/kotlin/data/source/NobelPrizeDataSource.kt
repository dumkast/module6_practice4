package org.example.data.source

import org.example.domain.model.Laureate
import org.example.domain.model.NobelPrize

object NobelPrizeDataSource {
    val prizes = listOf(
        NobelPrize(
            year = "1901",
            category = "Physics",
            prizeAmount = 150782,
            laureates = listOf(
                Laureate(
                    id = "1",
                    firstName = "Wilhelm Conrad",
                    surname = "Röntgen",
                    motivation = "in recognition of the extraordinary services he has rendered by the discovery of the remarkable rays subsequently named after him",
                    share = "1"
                )
            )
        ),
        NobelPrize(
            year = "1901",
            category = "Chemistry",
            prizeAmount = 150782,
            laureates = listOf(
                Laureate(
                    id = "160",
                    firstName = "Jacobus H.",
                    surname = "van 't Hoff",
                    motivation = "in recognition of the extraordinary services he has rendered by the discovery of the laws of chemical dynamics and osmotic pressure in solutions",
                    share = "1"
                )
            )
        ),
        NobelPrize(
            year = "1901",
            category = "Physiology or Medicine",
            prizeAmount = 150782,
            laureates = listOf(
                Laureate(
                    id = "293",
                    firstName = "Emil",
                    surname = "von Behring",
                    motivation = "for his work on serum therapy, especially its application against diphtheria",
                    share = "1"
                )
            )
        ),
        NobelPrize(
            year = "1901",
            category = "Literature",
            prizeAmount = 150782,
            laureates = listOf(
                Laureate(
                    id = "569",
                    firstName = "Sully",
                    surname = "Prudhomme",
                    motivation = "in special recognition of his poetic composition, which gives evidence of lofty idealism, artistic perfection",
                    share = "1"
                )
            )
        ),
        NobelPrize(
            year = "1901",
            category = "Peace",
            prizeAmount = 150782,
            laureates = listOf(
                Laureate(
                    id = "462",
                    firstName = "Henry",
                    surname = "Dunant",
                    motivation = "for his humanitarian efforts to help wounded soldiers and create international understanding",
                    share = "2"
                ),
                Laureate(
                    id = "463",
                    firstName = "Frédéric",
                    surname = "Passy",
                    motivation = "for his lifelong work for international peace conferences, diplomacy and arbitration",
                    share = "2"
                )
            )
        ),
        NobelPrize(
            year = "1902",
            category = "Physics",
            prizeAmount = 141847,
            laureates = listOf(
                Laureate(
                    id = "2",
                    firstName = "Hendrik A.",
                    surname = "Lorentz",
                    motivation = "in recognition of the extraordinary service they rendered by their researches into the influence of magnetism upon radiation phenomena",
                    share = "2"
                ),
                Laureate(
                    id = "3",
                    firstName = "Pieter",
                    surname = "Zeeman",
                    motivation = "in recognition of the extraordinary service they rendered by their researches into the influence of magnetism upon radiation phenomena",
                    share = "2"
                )
            )
        ),
        NobelPrize(
            year = "1902",
            category = "Chemistry",
            prizeAmount = 141847,
            laureates = listOf(
                Laureate(
                    id = "161",
                    firstName = "Emil",
                    surname = "Fischer",
                    motivation = "in recognition of the extraordinary services he has rendered by his work on sugar and purine syntheses",
                    share = "1"
                )
            )
        ),
        NobelPrize(
            year = "1902",
            category = "Physiology or Medicine",
            prizeAmount = 141847,
            laureates = listOf(
                Laureate(
                    id = "294",
                    firstName = "Ronald",
                    surname = "Ross",
                    motivation = "for his work on malaria, by which he has shown how it enters the organism",
                    share = "1"
                )
            )
        ),
        NobelPrize(
            year = "1902",
            category = "Literature",
            prizeAmount = 141847,
            laureates = listOf(
                Laureate(
                    id = "571",
                    firstName = "Theodor",
                    surname = "Mommsen",
                    motivation = "the greatest living master of the art of historical writing, with special reference to his monumental work, A History of Rome",
                    share = "1"
                )
            )
        ),
        NobelPrize(
            year = "1902",
            category = "Peace",
            prizeAmount = 141847,
            laureates = listOf(
                Laureate(
                    id = "464",
                    firstName = "Élie",
                    surname = "Ducommun",
                    motivation = "for his untiring and skilful directorship of the Bern Peace Bureau",
                    share = "2"
                ),
                Laureate(
                    id = "465",
                    firstName = "Albert",
                    surname = "Gobat",
                    motivation = "for his eminently practical administration of the Inter-Parliamentary Union",
                    share = "2"
                )
            )
        ),
        NobelPrize(
            year = "1975",
            category = "Physics",
            prizeAmount = 630000,
            laureates = listOf(
                Laureate(
                    id = "102",
                    firstName = "Aage N.",
                    surname = "Bohr",
                    motivation = "for the discovery of the connection between collective motion and particle motion in atomic nuclei and the development of the theory of the structure of the atomic nucleus",
                    share = "3"
                ),
                Laureate(
                    id = "103",
                    firstName = "Ben",
                    surname = "Mottelson",
                    motivation = "for the discovery of the connection between collective motion and particle motion in atomic nuclei and the development of the theory of the structure of the atomic nucleus",
                    share = "3"
                ),
                Laureate(
                    id = "104",
                    firstName = "James",
                    surname = "Rainwater",
                    motivation = "for the discovery of the connection between collective motion and particle motion in atomic nuclei and the development of the theory of the structure of the atomic nucleus",
                    share = "3"
                )
            )
        ),
        NobelPrize(
            year = "2001",
            category = "Economic Sciences",
            prizeAmount = 10000000,
            laureates = listOf(
                Laureate(
                    id = "745",
                    firstName = "A. Michael",
                    surname = "Spence",
                    motivation = "for their analyses of markets with asymmetric information",
                    share = "3"
                ),
                Laureate(
                    id = "746",
                    firstName = "George A.",
                    surname = "Akerlof",
                    motivation = "for their analyses of markets with asymmetric information",
                    share = "3"
                ),
                Laureate(
                    id = "747",
                    firstName = "Joseph E.",
                    surname = "Stiglitz",
                    motivation = "for their analyses of markets with asymmetric information",
                    share = "3"
                )
            )
        ),
        NobelPrize(
            year = "2004",
            category = "Chemistry",
            prizeAmount = 10000000,
            laureates = listOf(
                Laureate(
                    id = "779",
                    firstName = "Aaron",
                    surname = "Ciechanover",
                    motivation = "for the discovery of ubiquitin-mediated protein degradation",
                    share = "3"
                ),
                Laureate(
                    id = "780",
                    firstName = "Avram",
                    surname = "Hershko",
                    motivation = "for the discovery of ubiquitin-mediated protein degradation",
                    share = "3"
                ),
                Laureate(
                    id = "781",
                    firstName = "Irwin",
                    surname = "Rose",
                    motivation = "for the discovery of ubiquitin-mediated protein degradation",
                    share = "3"
                )
            )
        )
    )
}