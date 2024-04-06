package swaggest.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "swaggest")
public class Swaggest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "swag_id", unique = true)
    private Long id;
    private String swaggestContent;
    private String user;
    private GenericDetails genericDetails;
}
