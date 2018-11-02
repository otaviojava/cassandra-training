package com.nosqlxp.cassandra;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Hello world!
 */
public class App2 {


    public static void main(String[] args) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("hibernate");
        EntityManager manager = managerFactory.createEntityManager();
        manager.getTransaction().begin();
        String name = "Clean Code";
        Book cleanCode = getBook(1L, name, "Robert Cecil Martin");
        manager.merge(cleanCode);
        manager.getTransaction().commit();

        Query query = manager.createQuery("select b from book b where name = :name");
        query.setParameter("name", name);
        List<Book> books = query.getResultList();
        System.out.println("books: " + books);
    }


    private static Book getBook(long isbn, String name, String author) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setName(name);
        book.setAuthor(author);
        return book;
    }

}
