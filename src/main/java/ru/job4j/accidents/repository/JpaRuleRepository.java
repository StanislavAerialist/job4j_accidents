package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Set;

public interface JpaRuleRepository extends CrudRepository<Rule, Integer> {
    List<Rule> findAll();

    @Query("from Rule where id in :ids")
    Set<Rule> findByIds(@Param("ids") Set<Integer> rIds);
}
