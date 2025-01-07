package org.example.data.model.user;

import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.goods.Address;

@Data
@Entity
public class Buyer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email ;
    private String password ;
    private String contact ;


}
