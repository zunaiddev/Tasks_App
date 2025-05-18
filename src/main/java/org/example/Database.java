package org.example;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class Database {
    private static final SessionFactory factory;

    static {
        System.out.println("Processing...");
        Configuration config = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Task.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        factory = config.buildSessionFactory(registry);
    }

    public static void save(Task task) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.persist(task);
        session.getTransaction().commit();
        System.out.println("saved");
    }

    public static Task get(int id) {
        Session session = factory.openSession();
        return session.get(Task.class, id);
    }

    public static List<Task> get() {
        Session session = factory.openSession();
        TypedQuery<Task> q = session.createQuery("FROM Task", Task.class);
        return q.getResultList();
    }

    public static void update(Task task) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.merge(task);
        session.getTransaction().commit();
        System.out.println(Messages.GREEN + "Updated" + Messages.RESET);
    }

    public static void delete(Task task) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.remove(task);
        session.getTransaction().commit();
    }

    public static void close() {
        factory.close();
        System.out.println("App Closed...");
    }
}
