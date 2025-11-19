package org.lifehive

import org.hibernate.cfg.Configuration
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val cfg = Configuration()
    cfg.configure("hibernate.cfg.xml")
    cfg.addAnnotatedClass(Alien::class.java)
    cfg.buildSessionFactory().use { factory ->
        factory.openSession().use { session ->
            try {
                val aliens: List<Alien> = session.createQuery("from Alien", Alien::class.java).list()
                if (aliens.isEmpty()) {
                    println("No Alien records found.")
                    return
                }

                println("Existing Aliens:")
                for (a in aliens) {
                    println(a)
                }

                print("\nEnter the aid of the record you want to update: ")
                val idInput = scanner.nextLine().trim()
                val aid = try {
                    idInput.toInt()
                } catch (e: NumberFormatException) {
                    println("Invalid id: $idInput")
                    return
                }

                val target: Alien? = session.find(Alien::class.java, aid)
                if (target == null) {
                    println("No Alien found with aid=$aid")
                    return
                }

                println("Selected -> aid=$target")

                println("What would you like to update? (1) name  (2) tech")
                print("Enter 1 or 2: ")
                val choice = scanner.nextLine().trim().toInt()

                when (choice) {
                    1 -> {
                        print("Enter new name: ")
                        val newName = scanner.nextLine()
                        target.setAname(newName)
                    }
                    2 -> {
                        print("Enter new tech: ")
                        val newTech = scanner.nextLine()
                        target.setTech(newTech)
                    }
                    else -> {
                        println("Invalid choice.")
                        return
                    }
                }

                val tx = session.beginTransaction()
                val merged: Alien = session.merge(target)
                tx.commit()

                println("Update successful:")
                println("$merged")
            } catch (ex: Exception) {
                println("Error: ${ex.message}")
                ex.printStackTrace()
            }
        }
    }
}
