package com.example.moneymanager.repository;

import java.util.List;
import java.util.Optional;

import com.example.moneymanager.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>{

    //select * from tbl_Categories where profile_id =?
    List<CategoryEntity> findByProfileId(Long profileId);

    //select * from tbl_categories where id = ?
    Optional<CategoryEntity> findByIdAndProfileId(Long id,Long profileId);

    //select * from tbl_categories where type = ? and profile_id = ?
    List<CategoryEntity> findByTypeAndProfileId(String type,Long profileId);

    Boolean existsByNameAndProfileId(String name,Long profileId);

}
