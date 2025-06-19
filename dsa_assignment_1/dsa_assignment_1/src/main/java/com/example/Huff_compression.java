package com.example;
import java.util.*;
import java.util.Map.*;

public class Huff_compression {
    private static Map<Character, String> huffmanCodes = new HashMap<>();
    private static Map<Character, Integer> characterFrequencies = new HashMap<>();
    private static PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

    public String compress(String inputMessage) {
        String originalMessage = inputMessage;
        String encodedMessage = "";
        String decodedMessage = "";

        // Count the frequency of each character in the message
        calculateFrequencies(originalMessage);
        
        // Generate Huffman codes for the characters
        generateHuffmanCodes(originalMessage.length());
        
        // Print characters and their frequencies with improved formatting
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                 HUFFMAN ENCODING TABLE                |");
        System.out.println("+--------+----------------------------------------------+");
        System.out.println("| CHAR   | CODE                                         |");
        System.out.println("+--------+----------------------------------------------+");
        
        for (Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.printf("| %-6s | %-44s |\n", 
                             "'" + entry.getKey() + "'", entry.getValue());
        }
        
        System.out.println("+--------+----------------------------------------------+");

        // Encode the message using Huffman codes
        for (char character : originalMessage.toCharArray()) {
            encodedMessage += huffmanCodes.get(character);
        }

        // Print the encoded message with improved formatting
        System.out.println("\n[PROCESSING] Compressing message...");
        try { Thread.sleep(800); } catch (InterruptedException e) { }
        
        System.out.println("+-------------------------------------------------------+");
        
        decodedMessage = decodeMessage(priorityQueue.peek(), encodedMessage);
        
        // Clear data structures for next use
        huffmanCodes.clear();
        characterFrequencies.clear();
        priorityQueue.clear();
        
        return decodedMessage;
    }
    
    // Other methods remain the same
    private static void generateHuffmanCodes(int size) {
        for (Entry<Character, Integer> entry : characterFrequencies.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() != 1) {
            HuffmanNode leftNode = priorityQueue.poll();
            HuffmanNode rightNode = priorityQueue.poll();
            HuffmanNode parentNode = new HuffmanNode('$', leftNode.frequency + rightNode.frequency);
            parentNode.leftChild = leftNode;
            parentNode.rightChild = rightNode;
            priorityQueue.add(parentNode);
        }

        storeHuffmanCodes(priorityQueue.peek(), "");
    }
    
    private static void calculateFrequencies(String message) {
        for (char character : message.toCharArray()) {
            characterFrequencies.put(character, characterFrequencies.getOrDefault(character, 0) + 1);
        }
    }

    private static void storeHuffmanCodes(HuffmanNode rootNode, String code) {
        if (rootNode == null) {
            return;
        }

        if (rootNode.character != '$') {
            huffmanCodes.put(rootNode.character, code);
        }

        storeHuffmanCodes(rootNode.leftChild, code + "0");
        storeHuffmanCodes(rootNode.rightChild, code + "1");
    }

    private static String decodeMessage(HuffmanNode rootNode, String encodedString) {
        String decodedString = "";
        HuffmanNode currentNode = rootNode;
        int length = encodedString.length();

        for (int i = 0; i < length; i++) {
            if (encodedString.charAt(i) == '0') {
                currentNode = currentNode.leftChild;
            } else {
                currentNode = currentNode.rightChild;
            }
            
            if (currentNode.leftChild == null && currentNode.rightChild == null) {
                decodedString += currentNode.character;
                currentNode = rootNode;
            }
        }
        
        return decodedString;
    }
}

class HuffmanNode implements Comparable<HuffmanNode> {
    int frequency; // Frequency of the character
    char character; // Character
    HuffmanNode leftChild, rightChild; // Left and right child nodes
    
    // Constructor to initialize the node
    HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency; 
    }
    
    // Method to compare nodes based on frequency
    public int compareTo(HuffmanNode otherNode) {
        return this.frequency - otherNode.frequency;
    }
}