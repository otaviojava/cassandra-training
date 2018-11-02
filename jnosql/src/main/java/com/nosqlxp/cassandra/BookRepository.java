package com.nosqlxp.cassandra;

import org.jnosql.artemis.Query;
import org.jnosql.artemis.cassandra.column.CQL;
import org.jnosql.artemis.cassandra.column.CassandraRepository;

import java.util.stream.Stream;

public interface BookRepository extends CassandraRepository<Book, Long> {

    Stream<Book> findAll();

    @CQL("select * from book")
    Stream<Book> findAll1();

    @Query("select * from Book")
    Stream<Book> findAll2();
}
