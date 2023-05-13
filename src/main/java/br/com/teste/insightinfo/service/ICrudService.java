package br.com.teste.insightinfo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICrudService<T> {
	
	/**
     * Save a entity.
     *
     * @param entity the entity to save.
     * @return the persisted entity.
     */
	T save(T entity);
	
	/**
     * Updates a entity.
     *
     * @param entity the entity to update.
     * @return the persisted entity.
     */
	T update(T entity);
	
	/**
	 * Verify if exists an entity by given id.
	 * 
	 * @param id
	 * @return
	 */
	Boolean existsById(Long id);
	
	/**
     * Get all the entities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
	List<T> findAll();
	
	/**
     * Get the "id" entity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
	Optional<T> findOne(Long id);
	
	/**
	 * Find the entity by example
	 * 
	 * @param example
	 * @return
	 */
	Optional<T> findOne(T example);
	
	/**
     * Delete the "id" entity.
     *
     * @param id the id of the entity.
     */
	void delete(Long id);
}
