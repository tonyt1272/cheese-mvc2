package com.tonyt1272.cheesemvc2.models.data;

import com.tonyt1272.cheesemvc2.models.Cheese;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

/**
 * This is a Data Access Object (Dao) that allows us to access cheese
 * classes.  It is the interface by which we interact with the database.
 * This one stores Cheese objects and the keys are Integers
 */
@Repository//makes Spring boot aware that this is a repository
@Transactional//Specifies that all interface methods should be wrapped in a database transaction
public interface CheeseDao extends CrudRepository<Cheese,Integer> {

}
