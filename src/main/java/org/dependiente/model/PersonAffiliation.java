package org.dependiente.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person_affiliation")
public class PersonAffiliation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ─────────── Datos de la persona ───────────
    @Column(name = "tipo_doc", length = 2, nullable = false)
    private String docType;

    @Column(name = "id_persona", length = 15, nullable = false)
    private String personId;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDateTime birthDate;

    @Column(length = 60, nullable = false)
    private String departamento;

    @Column(length = 60, nullable = false)
    private String municipio;

    @Column(name = "id_departamento", nullable = false)
    private Integer departamentoId;

    @Column(name = "id_municipio", nullable = false)
    private Integer municipioId;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50)
    private String middleName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50)
    private String secondLastName;

    @Column(length = 120)
    private String emailPersona;

    private Integer occupationId;

    @Column(length = 120)
    private String occupation;

    @Column(length = 20)
    private String phonePersona;

    @Column(length = 1, nullable = false)
    private String sex;

    @Column(name = "estado_rl", length = 20)
    private String relationshipStatus;

    @Column(length = 30)
    private String employmentType;

    @Column(name = "fecha_inicio_vinc", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "fecha_fin_vinc")
    private LocalDateTime endDate;

    private Integer afp;

    @Column(length = 120)
    private String afpName;

    @Column(length = 10)
    private String eps;

    @Column(length = 120)
    private String epsName;

    @Column(length = 10)
    private String arlId;

    @Column(length = 120)
    private String arlName;

    private BigDecimal salary;

    @Column(length = 120)
    private String address;

    // ─────────── Datos de la empresa ───────────
    @Column(length = 2)
    private String companyDocType;

    @Column(length = 15)
    private String companyId;

    @Column(length = 120)
    private String companyName;

    @Column(length = 120)
    private String companyAddress;

    private Integer companyDeptId;

    @Column(length = 60)
    private String companyDept;

    private Integer companyMunId;

    @Column(length = 60)
    private String companyMun;

    @Column(length = 20)
    private String companyPhone;

    @Column(length = 120)
    private String companyEmail;

    // ─────────── Otros ───────────
    @Column(length = 1)
    private String zoneIndicator;

    private Integer economicActivityId;

    @Column(columnDefinition = "text")
    private String economicActivityName;

    private LocalDateTime effectiveAffiliationDate;

    @Column(length = 20)
    private String companyStatus;

    private Integer workCenterId;
    private Integer branchId;
    private Integer economicSectorId;
    private Integer linkedTypeId;
}
