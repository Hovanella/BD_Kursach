package com.example.backendapp.Repositories;

import com.example.backendapp.DTO.PlaylistDTO;
import com.example.backendapp.Entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query(value = "SELECT * from PLAYLISTS where PLAYLISTS.USER_ID=:userId",nativeQuery = true)
    Collection<Playlist> getUserPlaylists(Long userId);
}
