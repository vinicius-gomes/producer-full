package com.b2wdigital.lets.juvenal.producer.resource.impl;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class ResourceStatusHealthCheck implements HealthCheck {

    @ConfigProperty(name = "Application-Name")
    String applicationName;

    @ConfigProperty(name = "Implementation-Version")
    String implementationVersion;

    @ConfigProperty(name = "buildNumber")
    String buildNumber;

    @ConfigProperty(name = "buildTimeStamp")
    String buildTimeStamp;

    @ConfigProperty(name = "Build-Jdk")
    String buildJDK;

    @ConfigProperty(name = "Created-By")
    String createdBy;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("Resource Status")
                .up()
                .withData("applicationName", applicationName)
                .withData("implementationVersion", implementationVersion)
                .withData("implementationBuild", buildNumber + "/" + buildTimeStamp)
                .withData("buildJDK", buildJDK)
                .withData("createdBy", createdBy)
                .build();
    }
}
