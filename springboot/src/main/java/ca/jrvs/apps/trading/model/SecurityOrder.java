package ca.jrvs.apps.trading.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "security_order", schema = "public", catalog = "jrvstrading")
public class SecurityOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "ticker", referencedColumnName = "ticker")
    private Quote quote;
    @Column(name = "status")
    private String status;
    @Column(name = "size")
    private int size;
    @Column(name = "price")
    private Double price;
    @Column(name = "notes")
    private String notes;
}
