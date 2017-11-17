package com.br.donna.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.br.donna.domain.enumeration.Status;

/**
 * A DTO for the LegalProcess entity.
 */
public class LegalProcessDTO implements Serializable {

    private Long id;

    @NotNull
    private String number;

    private Status status;

    private String adversypart;

    private Set<UserDTO> lawyers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAdversypart() {
        return adversypart;
    }

    public void setAdversypart(String adversypart) {
        this.adversypart = adversypart;
    }

    public Set<UserDTO> getLawyers() {
        return lawyers;
    }

    public void setLawyers(Set<UserDTO> lawyers) {
        this.lawyers = lawyers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LegalProcessDTO legalProcessDTO = (LegalProcessDTO) o;
        if(legalProcessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), legalProcessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LegalProcessDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", adversypart='" + getAdversypart() + "'" +
            "}";
    }
}
