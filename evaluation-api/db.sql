
CREATE TABLE cycle (
                       cycle_id INT NOT NULL AUTO_INCREMENT,
                       name VARCHAR(255),
                       start_date DATETIME,
                       end_date DATETIME,
                       cycle_state ENUM('CLOSED', 'OPEN', 'PASSED') DEFAULT 'CLOSED',
                       PRIMARY KEY (cycle_id)
) ENGINE=InnoDB;


CREATE TABLE kpi (
                     kpi_id INT NOT NULL AUTO_INCREMENT,
                     name VARCHAR(255),
                     cycle_id INT,
                     PRIMARY KEY (kpi_id),
                     FOREIGN KEY (cycle_id) REFERENCES cycle(cycle_id)
) ENGINE=InnoDB;


CREATE TABLE objectives (
                            objective_id BIGINT NOT NULL,
                            title VARCHAR(255),
                            description VARCHAR(255),
                            assigned_user_id BIGINT NOT NULL,
                            deadline DATETIME,
                            cycle_id INT,
                            PRIMARY KEY (objective_id),
                            FOREIGN KEY (cycle_id) REFERENCES cycle(cycle_id)
) ENGINE=InnoDB;


CREATE TABLE ratings (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         feedback VARCHAR(255),
                         submitter_id BIGINT NOT NULL,
                         rated_person_id BIGINT NOT NULL,
                         kpi_id INT,
                         PRIMARY KEY (id),
                         FOREIGN KEY (kpi_id) REFERENCES kpi(kpi_id)
) ENGINE=InnoDB;