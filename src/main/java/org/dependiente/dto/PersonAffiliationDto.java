package org.dependiente.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.dependiente.model.PersonAffiliation}
 */
@Value
public class PersonAffiliationDto implements Serializable {
    Long id;
    String docType;
    String personId;
    LocalDateTime birthDate;
    String departamento;
    String municipio;
    Integer departamentoId;
    Integer municipioId;
    String firstName;
    String middleName;
    String lastName;
    String secondLastName;
    String emailPersona;
    Integer occupationId;
    String occupation;
    String phonePersona;
    String sex;
    String relationshipStatus;
    String employmentType;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer afp;
    String afpName;
    String eps;
    String epsName;
    String arlId;
    String arlName;
    BigDecimal salary;
    String address;
    String companyDocType;
    String companyId;
    String companyName;
    String companyAddress;
    Integer companyDeptId;
    String companyDept;
    Integer companyMunId;
    String companyMun;
    String companyPhone;
    String companyEmail;
    String zoneIndicator;
    Integer economicActivityId;
    String economicActivityName;
    LocalDateTime effectiveAffiliationDate;
    String companyStatus;
    Integer workCenterId;
    Integer branchId;
    Integer economicSectorId;
    Integer linkedTypeId;
}