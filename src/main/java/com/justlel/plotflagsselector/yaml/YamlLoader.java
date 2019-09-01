package com.justlel.plotflagsselector.yaml;

import com.justlel.plotflagsselector.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

class YamlLoader extends YamlConfiguration {

    private final Main main;

    /**
     * Constructor of the YamlLoader class.
     *
     * @param main Instance of the {@link Main Main} class.
     */
    YamlLoader(Main main) {
        super();
        this.main = main;
    }

    /**
     * Load a configuration given its name.
     * If the file does not exist inside of the {@link JavaPlugin#getDataFolder() data folder}, then it's created.
     * If the configuration cannot be loaded correctly, an exception is thrown.
     *
     * @param filename String name of the configuration file to be loaded.
     * @throws IllegalArgumentException If the filename is wrong.
     * @throws IOException If the file cannot be loaded.
     * @throws InvalidConfigurationException If the configuration loaded is invalid.
     */
    final protected void loadYaml(String filename) throws IllegalArgumentException, IOException, InvalidConfigurationException {
        if (!filename.endsWith(".yml"))
            throw new IllegalArgumentException("Il file di config " + filename + " non è un file YAML!");
        if (main.getResource(filename) == null)
            throw new IllegalArgumentException("Il file " + filename + " non è presente nelle resources del plugin!");
        File customYaml = new File(main.getDataFolder(), filename);
        InputStream defaultYaml = main.getResource(filename);
        if (!customYaml.exists()) {
            if (!Files.isDirectory(main.getDataFolder().toPath())) {
                Files.createDirectory(main.getDataFolder().toPath());
            }
            Files.copy(defaultYaml, customYaml.toPath());
        }
        super.load(customYaml);
    }

    /**
     * Get an instance of the {@link Main Main} class.
     *
     * @return Instance of the {@link Main Main} class.
     */
    public Main getMain() {
        return main;
    }
}
