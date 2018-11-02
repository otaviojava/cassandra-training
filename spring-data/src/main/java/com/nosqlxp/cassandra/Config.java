package com.nosqlxp.cassandra;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@ComponentScan("com.nosqlxp.cassandra")
@EnableCassandraRepositories("com.nosqlxp.cassandra")
public class Config {
}
