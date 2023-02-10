package edu.bu.cs633.minimalsearchengine.repositories;

import edu.bu.cs633.minimalsearchengine.models.dao.Page;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface PageRepository extends CrudRepository<Page, Integer> {

    @Override
    Set<Page> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"words"})
    Set<Page> findByUrlLikeIgnoreCase(String url);

    @Query(nativeQuery = true,
            value = "select p.page_id, p.title, p.url from pages p join words w on p.page_id = w.page_id " +
                    "where lower(w.word) in :words order by w.frequency desc")
    List<Page> getPagesByWords(@Param("words") Collection<String> words);
}
