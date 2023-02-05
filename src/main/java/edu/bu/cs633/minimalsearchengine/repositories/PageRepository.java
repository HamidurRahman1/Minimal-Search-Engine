package edu.bu.cs633.minimalsearchengine.repositories;

import edu.bu.cs633.minimalsearchengine.models.dao.Page;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface PageRepository extends CrudRepository<Page, Integer> {

    @Override
    Set<Page> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"words"})
    Set<Page> findByUrlLikeIgnoreCase(String url);

    @Query(nativeQuery = true,
            value = "select page_id, title, url from pages where page_id in " +
                    "(select page_id from words where lower(word) in :words order by frequency desc)")
    Set<Page> getPagesByWords(@Param("words") Collection<String> words);
}
