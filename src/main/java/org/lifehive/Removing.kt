package org.lifehive

import org.hibernate.cfg.Configuration
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val cfg = Configuration()
    cfg.configure()
    cfg.addAnnotatedClass(Alien::class.java)
    cfg.buildSessionFactory().use { factory ->
        factory.openSession().use { session ->
            try {
                val aliens: List<Alien> = session.createQuery("from Alien", Alien::class.java).list()
                if(aliens.isEmpty()) {
                    println("No Alien records found.")
                    return
                }

                println("Existing Aliens:")
                for(a in aliens) println(a)

                print("\nEnter the aid of the record you want to delete: ")
                val idInput = scanner.nextInt()

                val target: Alien? = session.find(Alien::class.java, idInput)
                if(target == null) {
                    println("Record not found!")
                    return
                }

                println("Selected->$target")
                val tx = session.beginTransaction()
                session.remove(target)
                tx.commit()
                println("Deleted Successfully!...")
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

}