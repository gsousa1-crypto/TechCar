package com.nunes.tech_car.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID; // Para gerar nomes de arquivo únicos

@Service
public class FileStorageService {

    // Define a pasta onde as imagens serão salvas
    private final Path rootLocation = Paths.get("uploads");

    public FileStorageService() {
        // Cria a pasta "uploads" se ela não existir
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de upload", e);
        }
    }

    /**
     * Salva o arquivo e retorna o caminho relativo para salvar no banco.
     */
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Falha ao salvar arquivo vazio.");
            }

            // Gera um nome de arquivo único para evitar conflitos
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            // Salva o arquivo na pasta "uploads"
            Path destinationFile = this.rootLocation.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), destinationFile);

            // Retorna o caminho que será salvo no banco (ex: /uploads/arquivo-unico.jpg)
            return "/uploads/" + uniqueFilename;

        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o arquivo.", e);
        }
    }
}