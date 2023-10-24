package net.highskiesmc.hscore.data;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.SQLException;

public abstract class MySQLDatabase {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private HikariDataSource hikari;

    protected MySQLDatabase(@NonNull final ConfigurationSection DB_CONFIG) throws SQLException {
        Bukkit.getLogger().info("Validating database configuration...");

        this.validateConfig(DB_CONFIG);

        Bukkit.getLogger().info("Connecting to database " + this.database + "...");

        this.connect();
        this.tryCreateTables();

        Bukkit.getLogger().info("Connected!");
    }

    /**
     * Should return a static int. Is used in connect() to set a hikari setting.
     *
     * @return Maximum amount of connections
     */
    protected abstract int getMaxPoolSize();

    /**
     * Should return a static int. Is used in connect() to set a hikari setting.
     *
     * @return Minimum amount of pools to be kept open and waiting
     */
    protected abstract int getMinIdlePools();

    /**
     * Should return a static int. Is used in connect() to set a hikari setting.
     *
     * @return Max lifetime of every connection in the pool
     */
    protected abstract int getMaxLifetime();

    /**
     * Should execute "CREATE IF NOT EXISTS" for every database table
     * Executes automatically when instantiating the class
     */
    protected abstract void tryCreateTables() throws SQLException;

    private void validateConfig(@NonNull final ConfigurationSection DB_CONFIG) throws NullPointerException {
        if (!DB_CONFIG.isSet("host")) {
            throw new NullPointerException("Must define host in the ConfigurationSection");
        }
        if (!DB_CONFIG.isSet("port")) {
            throw new NullPointerException("Must define port in the ConfigurationSection");
        }
        if (!DB_CONFIG.isSet("database")) {
            throw new NullPointerException("Must define database in the ConfigurationSection");
        }

        if (!DB_CONFIG.isSet("username")) {
            throw new NullPointerException("Must define username in the ConfigurationSection");
        }

        this.host = DB_CONFIG.getString("host");
        this.port = DB_CONFIG.getInt("port");
        this.database = DB_CONFIG.getString("database");
        this.username = DB_CONFIG.getString("username");
        this.password = DB_CONFIG.isSet("password") ? DB_CONFIG.getString("password") : null;
    }

    /**
     * Attempts to establish a connection to the my-sql database
     *
     * @throws SQLException
     */
    private void connect() throws SQLException {
        this.hikari = new HikariDataSource();
        this.hikari.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        this.hikari.addDataSourceProperty("serverName", this.host);
        this.hikari.addDataSourceProperty("port", this.port);
        this.hikari.addDataSourceProperty("databaseName", this.database);
        this.hikari.addDataSourceProperty("user", this.username);
        this.hikari.addDataSourceProperty("password", this.password);
        this.hikari.setMaximumPoolSize(this.getMaxPoolSize());
        this.hikari.setMinimumIdle(this.getMinIdlePools());
        this.hikari.setMaxLifetime(this.getMaxLifetime());
        this.hikari.setConnectionTestQuery("SELECT 1");
    }

    public boolean isConnected() {
        return this.hikari != null;
    }

    public HikariDataSource getHikari() {
        return this.hikari;
    }

    /**
     * Terminates all connections in the pool. Should be run in the onDisable()
     */
    public void disconnect() {
        if (isConnected()) {
            this.hikari.close();
        }
    }
}


