package net.devk.hc.patient;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import net.devk.hc.model.Patient;

@Repository
public interface PatientRepository extends EntityRepository<Patient, Long> {

	Patient findByName(String name);

}