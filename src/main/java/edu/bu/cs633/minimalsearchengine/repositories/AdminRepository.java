package edu.bu.cs633.minimalsearchengine.repositories;

import edu.bu.cs633.minimalsearchengine.models.dao.Admin;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {

    @Override
    Set<Admin> findAll();

    Admin findByUsernameIgnoreCase(String username);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "insert into indexing_histories (admin_id, page_id) values (:adminId, :pageId);")
    int updateIndexingHistory(@Param("adminId") Integer adminId, @Param("pageId") Integer pageId);
}
