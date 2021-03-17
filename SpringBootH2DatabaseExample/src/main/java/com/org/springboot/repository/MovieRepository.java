package com.org.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.springboot.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
}