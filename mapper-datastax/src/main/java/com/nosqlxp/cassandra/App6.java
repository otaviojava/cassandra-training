package com.nosqlxp.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

/**
 * Hello world!
 */
public class App6 {

    public static void main(String[] args) throws Exception {
        try (Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build()) {

            Session session = cluster.connect();
            MappingManager manager = new MappingManager(session);
            Mapper<Book> mapper = manager.mapper(Book.class);
            BookRepository bookRepository = manager.createAccessor(BookRepository.class);


            Book cleanCode = getBook(1L, "Clean Code", "Robert Cecil Martin", Sets.newHashSet("Java", "OO"));
            Book cleanArchitecture = getBook(2L, "Clean Architecture", "Robert Cecil Martin", Sets.newHashSet("Good practice"));
            Book effectiveJava = getBook(3L, "Effective Java", "Joshua Bloch", Sets.newHashSet("Java", "Good practice"));
            Book nosql = getBook(4L, "Nosql Distilled", "Joshua Bloch", Sets.newHashSet("NoSQL", "Good practice"));

            mapper.save(cleanCode);
            mapper.save(cleanArchitecture);
            mapper.save(effectiveJava);
            mapper.save(nosql);

            Result<Book> all = bookRepository.getAll();
            StreamSupport.stream(all.spliterator(), false).forEach(System.out::println);

            Book book = bookRepository.findById(1L);
            Book book2 = bookRepository.findById(2L);
            System.out.println(book);

            System.out.println(book2);


        }

    }


    private static Book getBook(long isbn, String name, String author, Set<String> categories) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setName(name);
        book.setAuthor(author);
        book.setCategories(categories);
        return book;
    }

}
