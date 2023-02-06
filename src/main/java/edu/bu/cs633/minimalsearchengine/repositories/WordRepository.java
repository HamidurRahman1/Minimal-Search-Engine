package edu.bu.cs633.minimalsearchengine.repositories;

import edu.bu.cs633.minimalsearchengine.models.dao.Word;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends CrudRepository<Word, Integer> {
    @Override
    <S extends Word> Iterable<S> saveAll(Iterable<S> entities);
}
