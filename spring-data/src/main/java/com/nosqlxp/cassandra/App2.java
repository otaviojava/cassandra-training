package com.nosqlxp.cassandra;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.Set;

/**
 * Hello world!
 */
public class App2 {

    private static final String CONFIG_PACKAGE = "com.nosqlxp.cassandra";

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {

            ctx.scan(CONFIG_PACKAGE);
            ctx.refresh();
            CassandraTemplate template = ctx.getBean(CassandraTemplate.class);

            Book cleanCode = getBook(1L, "Clean Code", "Robert Cecil Martin", Sets.newHashSet("Java", "OO"));
            Book cleanArchitecture = getBook(2L, "Clean Architecture", "Robert Cecil Martin", Sets.newHashSet("Good practice"));
            Book effectiveJava = getBook(3L, "Effective Java", "Joshua Bloch", Sets.newHashSet("Java", "Good practice"));
            Book nosql = getBook(4L, "Nosql Distilled", "Martin Fowler", Sets.newHashSet("NoSQL", "Good practice"));

            template.insert(cleanCode);
            template.insert(cleanArchitecture);
            template.insert(effectiveJava);
            template.insert(nosql);

            Book book = template.selectOneById(1L, Book.class);
            System.out.println(book);
            template.deleteById(1L, Book.class);

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
