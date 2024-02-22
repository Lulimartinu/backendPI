package com.dh.Xplorando.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="PRODUCTOS", uniqueConstraints = {@UniqueConstraint(columnNames = "CODIGOPRODUCTO")})
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="CODIGO")
    private int codigoProducto;
    @Column(name="NOMBRE")
    private String nombreProducto;
    @Column(name="DESCRIPCION")
    private String descripcionProducto;
    @Column(name="PRECIO")
    private double precioProducto;
    @Column(name="DIRECCION")
    private String direccion;

    @OneToMany(mappedBy ="producto", fetch = FetchType.LAZY)
    @JoinColumn(name="imagen_id", referencedColumnName = "id")
    private Imagen imagen;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id",referencedColumnName = "id")
    private Categoria categoria;

    //un producto tiene muchas img
    //Cuando se utiliza FetchType.LAZY, la colección de entidades asociada se cargará desde la base de datos solo cuando se acceda explícitamente a ella. Esto significa que, inicialmente, solo se cargarán los datos de la entidad principal, y la colección de entidades asociada permanecerá sin cargarse hasta que se realice una operación que la requiera. Esta estrategia es útil cuando no siempre necesitas la colección de forma inmediata y puede ayudar a mejorar el rendimiento en ciertos escenarios.
    //cascade = CascadeType.ALL




}
