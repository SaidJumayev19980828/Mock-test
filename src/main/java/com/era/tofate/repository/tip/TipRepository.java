package com.era.tofate.repository.tip;

import com.era.tofate.entities.tip.Tip;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {
    List<Tip> findAllByOrderBySequenceNumberAsc();
}
