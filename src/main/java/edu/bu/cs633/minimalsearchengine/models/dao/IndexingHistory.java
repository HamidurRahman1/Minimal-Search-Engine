package edu.bu.cs633.minimalsearchengine.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "indexing_histories")
public class IndexingHistory implements Serializable {

    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Id
    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;

    public IndexingHistory() {
    }

    public IndexingHistory(Admin admin, Page page) {
        this.admin = admin;
        this.page = page;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        IndexingHistory that = (IndexingHistory) o;
        return Objects.equals(admin, that.admin)
                && Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admin, page);
    }
}
