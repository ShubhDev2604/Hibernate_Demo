package org.lifehive;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add name");
        String name = scanner.nextLine();
        System.out.println("Add Tech");
        String tech = scanner.nextLine();
        Alien a1 = new Alien();
        a1.setAname(name);
        a1.setTech(tech);

        Configuration config = new Configuration();
        config.addAnnotatedClass(org.lifehive.Alien.class);
        config.configure("hibernate.cfg.xml");

        Session session;
        try (SessionFactory factory = config.buildSessionFactory()) {
            session = factory.openSession();
            Transaction trans = session.beginTransaction();
            session.persist(a1);
            //After transaction, commiting is imp
            trans.commit();
            //relation = table
            session.close();
        }



    }
}
