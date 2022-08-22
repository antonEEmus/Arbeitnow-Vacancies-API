package core.basesyntax.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    @Column(name = "company_name")
    private String companyName;
    private String title;
    @Column(length = 1000000000)
    private String description;
    private Boolean remote;
    private String url;
    private String location;
    @Column(name = "created_at")
    private Long createdAt;
}
