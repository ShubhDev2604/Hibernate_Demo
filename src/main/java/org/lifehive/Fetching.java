package org.lifehive;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Fetching {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which id you want to find?");
        int id = scanner.nextInt();
        Configuration config = new Configuration();
        config.addAnnotatedClass(org.lifehive.Alien.class);
        config.configure("hibernate.cfg.xml");
        Session session;
        try (SessionFactory factory = config.buildSessionFactory()) {
            session = factory.openSession();
            Alien response = session.find(Alien.class, id);
            if(response == null) {
                System.out.println("Data not found");
            } else {
                System.out.println("Data found!\n" + response);
            }
            session.close();
        }



    }
}
