package com.bsf.model.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name = "from_id", foreignKey = @ForeignKey(name = "from_transaction_FK"))
  private Account from;
  @ManyToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name = "to_id", foreignKey = @ForeignKey(name = "to_transaction_FK"))
  private Account to;
  @Column(nullable = false)
  private BigInteger amount;
  @Column(nullable = false)
  private LocalDateTime timeStamp;
  @Column(nullable = false, unique = true)
  private String number;
}
