package no.hvl.dat109.slangespill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import no.hvl.dat109.slangespill.model.Rute;

@Repository
public interface RuteRepository extends JpaRepository<Rute, Integer> {
    // Trenger ingen kode her, Spring fikser alt!
}
