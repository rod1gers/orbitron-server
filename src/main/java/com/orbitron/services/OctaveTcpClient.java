package com.orbitron.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.orbitron.dto.EngineDTO;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class OctaveTcpClient {
    // This class should connect to Octave via TCP

    // The Base Url of the hosting server for octave
    // These should be in the .env file
    private final String host = "localhost";
    private final int port = 5555;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final EngineSimulator engineSimulator;

    private volatile boolean listening = false;

    private Socket socket;
    private OutputStream out;
    private BufferedReader in;
    private InputStream inputStream;

    public OctaveTcpClient(EngineSimulator engineSimulator) {
        this.engineSimulator = engineSimulator;
    }

    public synchronized Socket init() {
        // if (socket != null && socket.isConnected() && !socket.isClosed()) {
        //     System.out.println("Already connected to Engine. Reusing socket");
        //     return socket;
        // }

        try {
            socket = new Socket(host, port);            
            out = socket.getOutputStream();
            inputStream = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("Connected to Octave at " + host + ":" + port);

            if (!listening) {
                listening = true;
                executor.submit(this::listenToOctave);
            }
            
            
            // String response = in.readLine();

            // System.out.println("Response is: " + response);

            return socket;
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to Octave", e);
        }
        
    }

    private void listenToOctave() {
        try {   
            String data;
            while (listening && (data = in.readLine()) != null) {
                System.out.println("Received from Octave: " + data);

                // Pass the data from octave to the engine simulator for
                // Cleaning and passing to frontend
                engineSimulator.sendEngineDataFromOctave(1, data);

            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading from Octave: " + e.getMessage());
        } finally {
            listening = false;
        }
    }

    public void connectToEngine() {
        // Send command to connect to engine (init method)
        init();
        
    }

    // Try sending out 'Hello From Rodgers' to Octave
    public void sendCommandToOctaveEngine() {
        
        try {
            out.write("second Hello from Rodgers".getBytes());
            out.flush();
            System.out.println("Message sent to octave");

        } catch( IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            listening = false;
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Socket closed.");
            }

        } catch (IOException ignored) {}
    }

    @PreDestroy
    public void shutdown() {
        closeConnection();
        executor.shutdownNow();
    }
    
}
