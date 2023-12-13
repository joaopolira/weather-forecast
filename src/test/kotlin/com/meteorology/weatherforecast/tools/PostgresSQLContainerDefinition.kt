package com.meteorology.weatherforecast.tools

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.containers.PostgreSQLContainer

interface PostgresSQLContainerDefinition {
    companion object {
        private var postgresContainerPort = 5432
        private var postgresLocalPort = 5432
        private val postgresContainer = PostgreSQLContainer("postgres:16")

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            postgresContainer
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true)
                .withCreateContainerCmdModifier { cmd ->
                    cmd.withHostConfig(
                        HostConfig().withPortBindings(
                            PortBinding(
                                Ports.Binding.bindPort(postgresLocalPort),
                                ExposedPort(postgresContainerPort)
                            )
                        )
                    )
                }
            postgresContainer.start()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            postgresContainer.stop()
        }
    }
}