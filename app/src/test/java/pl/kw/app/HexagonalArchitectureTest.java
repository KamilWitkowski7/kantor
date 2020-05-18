package pl.kw.app;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.util.Arrays;
import java.util.Set;

@AnalyzeClasses(packages = "pl.kw.app")
public class HexagonalArchitectureTest {
    private static final String ANY_PATH = "..";
    private static final String PATH_SEPARATOR = ".";

    private static final String ACCOUNT = "account";
    private static final Set<String> MODULES =
        Set.of(ACCOUNT);

    private static final String SPRING = "org.springframework";
    private static final String BOUNDARY_PRIMARY = "boundary.primary";
    private static final String CORE_APPLICATION = "core.application";
    private static final String CORE_DOMAIN = "core.domain";
    private static final String INFRASTRUCTURE = "infrastructure";
    private static final String CORE = "core";

    private static ArchRule module(String module, String modulePackage, String... otherModulePackages) {
        return noClasses()
            .that()
            .resideInAPackage(ANY_PATH + module + PATH_SEPARATOR + modulePackage + ANY_PATH)
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(otherModulePackagesPaths(module, otherModulePackages));
    }

    private static String[] otherModulePackagesPaths(String module, String... otherModulePackages) {
        return Arrays.stream(otherModulePackages)
            .map(omp -> ANY_PATH + module + PATH_SEPARATOR + omp + ANY_PATH)
            .toArray(String[]::new);
    }

    private static String[] otherModulePackagesPaths(String... otherModulePackages) {
        return Arrays.stream(otherModulePackages)
                .map(omp -> ANY_PATH + omp + ANY_PATH)
                .toArray(String[]::new);
    }

    private static ArchRule moduleWithExternalPackage(String module, String modulePackage, String... externalPackage) {
        return noClasses()
                .that()
                .resideInAPackage(ANY_PATH + module + PATH_SEPARATOR + modulePackage + ANY_PATH)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(otherModulePackagesPaths(externalPackage));
    }

    @ArchTest
    public static void boundaryPrimaryShouldNotDependOnOthers(JavaClasses classes) {
        MODULES.stream().map(m -> module(m, BOUNDARY_PRIMARY, CORE, INFRASTRUCTURE))
            .forEach(r -> r.check(classes));
    }

    @ArchTest
    public static void coreApplicationShouldNotDependOnInfrastructure(JavaClasses classes) {
        MODULES.stream().map(m -> module(m, CORE_APPLICATION, INFRASTRUCTURE))
            .forEach(r -> r.check(classes));
    }

    @ArchTest
    public static void domainShouldNotDependOnSpring(JavaClasses classes) {
        MODULES.stream().map(m -> moduleWithExternalPackage(m, CORE_DOMAIN, SPRING))
                .forEach(r -> r.check(classes));
    }
}
