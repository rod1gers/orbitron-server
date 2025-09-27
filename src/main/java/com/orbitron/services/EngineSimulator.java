package com.orbitron.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.orbitron.dto.EngineDTO;

@Service
public class EngineSimulator {
    private final SimpMessagingTemplate messagingTemplate;

    public EngineSimulator(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // The Initial and incremental data here should be mended to fetch from
    // The live sensor data in the Engine (Octave Model)
    public void sendEngineDataFromOctave(int engineId, String data) {
        double value;

        // Extract the double out of the string, and pass it as n1
        System.out.println(data);

        // Pattern is a compiled string pattern. It takes a string pattern and returns
        // a compiled Pattern object
        Pattern pattern = Pattern.compile("([0-9]+\\.?[0-9]*)");
        // An Engine that applies a pattern to a string
        // Creates a Matcher object that will look for matches in the given string
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            // Extract value
            value = Double.parseDouble(matcher.group(1));
        } else {
            value = 0.0;
        }

        double n1 = value, n2 = 0, egt = 30, fuelFlow = 0;

        EngineDTO dto = new EngineDTO(n1, n2, egt, fuelFlow);
        messagingTemplate.convertAndSend("/topic/engine/" + engineId, dto);
        System.out.println(n1);

    }


    

}
