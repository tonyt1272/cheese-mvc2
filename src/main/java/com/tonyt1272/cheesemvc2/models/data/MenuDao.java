package com.tonyt1272.cheesemvc2.models.data;

import com.tonyt1272.cheesemvc2.models.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository//makes Spring boot aware that this is a repository
@Transactional//Specifies that all interface methods should be wrapped in a database transaction
public interface MenuDao extends CrudRepository<Menu,Integer> {
}