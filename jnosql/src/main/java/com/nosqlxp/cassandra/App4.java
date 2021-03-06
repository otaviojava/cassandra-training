package com.nosqlxp.cassandra;

import com.google.common.collect.Sets;
import org.jnosql.artemis.cassandra.column.CassandraTemplate;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Optional;
import java.util.Set;

/**
 * Hello world!
 */
public class App4 {


    public static void main(String[] args) {
        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            CassandraTemplate template =  container.select(CassandraTemplate.class).get();

            BookType cleanCode = getBook(1L, "Clean Code", "Robert Cecil Martin", Sets.newHashSet("Java", "OO"));
            BookType cleanArchitecture = getBook(2L, "Clean Architecture", "Robert Cecil Martin", Sets.newHashSet("Good practice"));
            BookType effectiveJava = getBook(3L, "Effective Java", "Joshua Bloch", Sets.newHashSet("Java", "Good practice"));
            BookType nosqlDistilled = getBook(4L, "Nosql Distilled", "Martin Fowler", Sets.newHashSet("NoSQL", "Good practice"));


            Category java = getCategory("Java", Sets.newHashSet(cleanCode, effectiveJava));
            Category oo = getCategory("OO", Sets.newHashSet(cleanCode, effectiveJava, cleanArchitecture));
            Category goodPractice = getCategory("Good practice", Sets.newHashSet(cleanCode, effectiveJava, cleanArchitecture, nosqlDistilled));
            Category nosql = getCategory("NoSQL", Sets.newHashSet(nosqlDistilled));

            template.insert(java);
            template.insert(oo);
            template.insert(goodPractice);
            template.insert(nosql);

            Optional<Category> category = template.find(Category.class, "Java");
            System.out.println(category);
            template.delete(Category.class, "Java");

            org.jnosql.artemis.PreparedStatement prepare = template.prepare("select * from Category where name = @name");
            prepare.bind("name","NoSQL");
            Optional<Book> result = prepare.getSingleResult();
            System.out.println("prepare: " + result);
        }

    }

    private static Category getCategory(String name, Set<BookType> books) {
        Category category = new Category();
        category.setName(name);
        category.setBooks(books);
        return category;
    }

    private static BookType getBook(long isbn, String name, String author, Set<String> categories) {
        BookType book = new BookType();
        book.setIsbn(isbn);
        book.setName(name);
        book.setAuthor(author);
        book.setCategories(categories);
        return book;
    }

}
