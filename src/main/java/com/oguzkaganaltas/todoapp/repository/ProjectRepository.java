package com.oguzkaganaltas.todoapp.repository;

import com.oguzkaganaltas.todoapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query(value = "SELECT * FROM PROJECTS WHERE OWNER_ID = ?1",nativeQuery = true)
    List<Project> findProjectsByOwnerId(long ownerId);
    @Query(value = "SELECT * FROM PROJECTS WHERE OWNER_ID = ?1 AND PROJECT_ID = ?2",nativeQuery = true)
    Project findProjectByOwnerId(long ownerId, int projectId);

    @Modifying
    @Query(value = "DELETE FROM PROJECTS p WHERE p.OWNER_ID =:ownerId AND p.PROJECT_ID =:projectId",nativeQuery = true)
    void deleteProjectById(@Param("projectId") int projectId,@Param("ownerId") long ownerId);

}