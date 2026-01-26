package no.hvl.dat109.spring_stigespill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat109.spring_stigespill.repository.RuteRepository;
import no.hvl.dat109.spring_stigespill.repository.SpillRepository;

@Service
public class StigespillService {
	
	@Autowired
	private SpillRepository spillRepository;
	
	@Autowired
	private RuteRepository ruteRepository;
}
