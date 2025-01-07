package org.example.data.model.logistic;

import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.goods.Orders;
import org.example.data.model.goods.Status;
import org.example.data.model.user.Driver;

import java.time.LocalDate;

@Entity
@Data
public class Delivery {
    @Id
    private Long id;

    private String address;
    private LocalDate deliveryDate;
    private Status deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToOne
    private Orders orders;
}
