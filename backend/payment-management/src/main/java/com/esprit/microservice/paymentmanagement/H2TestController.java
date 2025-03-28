package com.esprit.microservice.paymentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class H2TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test-h2")
    public String testH2Connection() {
        try {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(255))");
            jdbcTemplate.update("INSERT INTO test_table (id, name) VALUES (1, 'TestUser')");
            String result = jdbcTemplate.queryForObject("SELECT name FROM test_table WHERE id = 1", String.class);
            return "H2 connection successful! Queried value: " + result;
        } catch (Exception e) {
            return "H2 connection failed: " + e.getMessage();
        }
    }
}
