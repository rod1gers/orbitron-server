package com.orbitron.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class OctaveTcpClient {
    // This class should connect to Octave via TCP

    // The Base Url of the hosting server for octave
    // These should be in the .env file
    private final String host = "localhost";
    private final int port = 5555;

    private Socket socket;
    private OutputStream out;
    private BufferedReader in;
    private InputStream inputStream;

    @PostConstruct
    public void init() {
        try {
            socket = new Socket(host, port);
            out = socket.getOutputStream();
            inputStream = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("Connected to Octave at " + host + ":" + port);
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to Octave", e);
        }
        
    }



    // Try sending out 'Hello From Rodgers' to Octave
    public void sendDataToOctave() {
        try {
            out.write("second Hello from Rodgers".getBytes());
            out.flush();
            System.out.println("Message sent to octave");

            String response = in.readLine();
            System.out.println("This is the response:" + response);
        } catch( IOException e) {
            e.printStackTrace();
        }
    }
    
}
