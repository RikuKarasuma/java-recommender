package org.musicshop.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.musicshop.service.FileProviderService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class FileProviderServiceImpl implements FileProviderService {

    @Override
    public String readEntireFile(final String path) {

        if (StringUtils.isBlank(path))
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
