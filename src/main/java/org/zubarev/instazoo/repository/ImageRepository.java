package org.zubarev.instazoo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.zubarev.instazoo.entity.ImageModel;

import java.util.Optional;
@Repository
public interface ImageRepository extends CrudRepository<ImageModel,Long> {
    Optional<ImageModel> findByUserId(Long userId);
    Optional<ImageModel> findByPostId(Long postId);
}
