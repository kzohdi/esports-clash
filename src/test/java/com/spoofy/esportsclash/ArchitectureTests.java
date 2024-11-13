package com.spoofy.esportsclash;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTests {

    private final JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE);

    private static final String DOMAIN_PACKAGE = "..domain..";
    private static final String APPLICATION_PACKAGE = "..application..";
    private static final String INFRASTRUCTURE_PACKAGE = "..infrastructure..";
    private static final String BASE_PACKAGE = "com.spoofy.esportsclash";

    @Test
    void domainClassesShouldNotDependOnClassesOutsideDomain() {
        var rule = noClasses()
                .that()
                .resideInAPackage(DOMAIN_PACKAGE)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(APPLICATION_PACKAGE, INFRASTRUCTURE_PACKAGE);

        rule.check(classes);
    }

    @Test
    void applicationClassesShouldNotDependOnInfrastructureClasses() {
        var rule = noClasses()
                .that()
                .resideInAPackage(APPLICATION_PACKAGE)
                .should()
                .dependOnClassesThat()
                .resideInAPackage(INFRASTRUCTURE_PACKAGE);

        rule.check(classes);
    }
}
