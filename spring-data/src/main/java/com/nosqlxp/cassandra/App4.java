package com.nosqlxp.cassandra;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.Set;

/**
 * Hello world!
 */
public class App4 {


    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class)) {

            CassandraTemplate template = ctx.getBean(CassandraTemplate.class);

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

            Category category = template.selectOneById("Java", Category.class);
            System.out.println(category);
            template.deleteById("Java", Category.class);

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
