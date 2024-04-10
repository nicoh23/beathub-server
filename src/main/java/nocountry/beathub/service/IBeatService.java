package nocountry.beathub.service;

import nocountry.beathub.model.Beat;

import java.util.List;

public interface IBeatService {
    List<Beat> getAllBeats();

    Beat saveBeat(Beat newBeat);
    
} 