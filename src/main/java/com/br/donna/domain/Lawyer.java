package com.br.donna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lawyer.
 */
@Entity
@Table(name = "lawyer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lawyer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToMany(mappedBy = "lawyers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LegalProcess> legalProcesses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public Lawyer fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public Lawyer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Lawyer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<LegalProcess> getLegalProcesses() {
        return legalProcesses;
    }

    public Lawyer legalProcesses(Set<LegalProcess> legalProcesses) {
        this.legalProcesses = legalProcesses;
        return this;
    }

    public Lawyer addLegalProcess(LegalProcess legalProcess) {
        this.legalProcesses.add(legalProcess);
        legalProcess.getLawyers().add(this);
        return this;
    }

    public Lawyer removeLegalProcess(LegalProcess legalProcess) {
        this.legalProcesses.remove(legalProcess);
        legalProcess.getLawyers().remove(this);
        return this;
    }

    public void setLegalProcesses(Set<LegalProcess> legalProcesses) {
        this.legalProcesses = legalProcesses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lawyer lawyer = (Lawyer) o;
        if (lawyer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lawyer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lawyer{" +
            "id=" + getId() +
            ", fullname='" + getFullname() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
