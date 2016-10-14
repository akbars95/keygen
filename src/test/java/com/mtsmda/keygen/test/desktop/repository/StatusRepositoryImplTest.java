package com.mtsmda.keygen.test.desktop.repository;

import com.mtsmda.keygen.desktop.repository.StatusRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by MTSMDA on 03.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
@ActiveProfiles("testing")
public class StatusRepositoryImplTest {

    @Autowired
    @Qualifier("statusRepositoryImpl")
    private StatusRepository statusRepository;

    private EmbeddedDatabase embeddedDatabase;

    @Before
    public void setUP() {
        assertNull(embeddedDatabase);
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL).
                        addScripts("db/hsql/sql/create-db.sql", "db/hsql/sql/insert-data.sql").build();
        assertNotNull(embeddedDatabase);
    }

    @Test
    public void init1000CheckStatusRepositoryTest() {
        assertNotNull(statusRepository);
    }

}