package com.orbitron.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
    public void startSpoolUp(Long engineId) {

        // List<String> angularVelocityLines = new ArrayList<>();
        // try {
        //     ProcessBuilder pb = new ProcessBuilder("omc", "runSimulation.mos");
            
        //     File resourcesDir = new File("src/main/resources");
        //     if (resourcesDir.exists()) {
        //         pb.directory(resourcesDir);
        //     }
            
        //     pb.redirectErrorStream(true);
        //     Process process = pb.start();

        //     BufferedReader reader = new BufferedReader(
        //         new InputStreamReader(process.getInputStream())
        //     );

        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         System.out.println(line); // Log all output
        //         if (line.contains("engineShaft.w")) {
        //             angularVelocityLines.add(line);
        //             System.out.println("ω found: " + line);
        //         }
        //     }

        //     process.waitFor();
        //     // return angularVelocityLines; 

        // } catch (Exception e) {
        //     e.printStackTrace();
        //     // return "Simulation failed: " + e.getMessage();
        // }


        new Thread(() -> {
            // n1 = %
            // n2 = %
            // egt = degrees celcius
            // fuelFlow = kg/s etc

            double n1 = 0, n2 = 0, egt = 30, fuelFlow = 0;

            while (n1 < 100) {
                n1 += 1;
                n2 += 0.9;
                egt += 200;
                fuelFlow += 150;

                EngineDTO dto = new EngineDTO(n1, n2, egt, fuelFlow);
                messagingTemplate.convertAndSend("/topic/engine/" + engineId, dto);
                System.out.println(n1);
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();

    }


    

}
