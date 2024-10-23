package fr.cleanarchitecture.esportsclash;

import org.springframework.boot.SpringApplication;

public class TestEsportsClashApplication {

    public static void main(String[] args) {
        SpringApplication.from(EsportsClashApplication::main).with(PostgreSQLTestConfiguration.class).run(args);
    }

}
