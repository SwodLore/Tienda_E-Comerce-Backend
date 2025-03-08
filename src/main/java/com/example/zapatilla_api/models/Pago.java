package com.example.zapatilla_api.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private String metodoPago; // Ejemplo: Tarjeta, PayPal, Transferencia

    @Column(nullable = false)
    private String estadoPago; // PENDIENTE, APROBADO, RECHAZADO

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Column(nullable = false)
    private double montoPagado;
}
