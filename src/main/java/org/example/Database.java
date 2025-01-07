package org.example;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class Database {
    private static final Session session;
    private static final ServiceRegistry sr;

    static {
        System.out.println("Processing...");
        Configuration config = new Configuration().configure("hibernate.cfg.xml");

        sr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory sessionFactory = config.buildSessionFactory(sr);
        session = sessionFactory.openSession();
    }

    public static void save(Task task) {
        session.beginTransaction();
        session.persist(task);
        session.getTransaction().commit();
        System.out.println("saved");
    }

    /*public static ArrayList<Task> get() {
        ArrayList<Task> tasks = new ArrayList<>();

        Task task = session.get(Task.class, 1);

        for (int i = 2; task != null; i++) {
            tasks.add(task);
            task = session.get(Task.class, i);
        }

        return tasks;
    }*/

    public static Task get(int id) {
        return session.get(Task.class, id);
    }

    public static List<Task> get() {
        final String QUERY = "FROM Task";
        TypedQuery<Task> q = session.createQuery(QUERY, Task.class);
        return q.getResultList();
    }

    public static void update(Task task) {
        session.beginTransaction();
        session.merge(task);
        session.getTransaction().commit();
        System.out.println(Messages.GREEN + "Updated" + Messages.RESET);
    }

    public static void delete(Task task) {
        session.beginTransaction();
        session.remove(task);
        session.getTransaction().commit();
    }

    public static void close() {
        session.close();
        sr.close();
        System.out.println("App Closed...");
    }
}
