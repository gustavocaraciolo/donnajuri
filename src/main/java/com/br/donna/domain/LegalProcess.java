package com.br.donna.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.br.donna.domain.enumeration.Status;

/**
 * A LegalProcess.
 */
@Entity
@Table(name = "legal_process")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LegalProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_number", nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "adversypart")
    private String adversypart;

    @ManyToMany
    private Set<User> user = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public LegalProcess number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Status getStatus() {
        return status;
    }

    public LegalProcess status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAdversypart() {
        return adversypart;
    }

    public LegalProcess adversypart(String adversypart) {
        this.adversypart = adversypart;
        return this;
    }

    public void setAdversypart(String adversypart) {
        this.adversypart = adversypart;
    }

    public Set<User> getUser() {
        return user;
    }

    public LegalProcess user(Set<User> user) {
        this.user = user;
        return this;
    }

    public LegalProcess addUser(User user) {
        this.user.add(user);
        user.getLegalProcesses().add(this);
        return this;
    }

    public LegalProcess removeUser(User user) {
        this.user.remove(user);
        user.getLegalProcesses().remove(this);
        return this;
    }

    public void setUser(Set<User> user) {
        this.user = user;
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
        LegalProcess legalProcess = (LegalProcess) o;
        if (legalProcess.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), legalProcess.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LegalProcess{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", adversypart='" + getAdversypart() + "'" +
            "}";
    }
}
