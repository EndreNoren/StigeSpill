package no.hvl.dat109.spring_stigespill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import no.hvl.dat109.spring_stigespill.model.Rute;

@Repository
public interface RuteRepository extends JpaRepository<Rute, Integer> {
}