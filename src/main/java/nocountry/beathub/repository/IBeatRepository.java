package nocountry.beathub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nocountry.beathub.model.Beat;

public interface IBeatRepository  extends JpaRepository<Beat, Long>  {
    
}
