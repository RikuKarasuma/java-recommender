package org.musicshop.service.impl;

import org.musicshop.service.FileProviderService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class FileProviderServiceImpl implements FileProviderService {

    @Override
    public String readEntireFile(final String path) {

        if (!StringUtils.hasLength(path))
            throw new IllegalArgumentException("ERROR: can't read null file path.");

        final var fileAsString = new StringBuilder();

        try (final BufferedReader br = java.nio.file.Files.newBufferedReader(Paths.get(path))) {
            var nextLine = "";

            while ((nextLine = br.readLine()) != null)
                fileAsString.append(nextLine);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileAsString.toString();
    }
}
